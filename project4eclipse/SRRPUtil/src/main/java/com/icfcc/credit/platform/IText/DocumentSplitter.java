package com.icfcc.credit.platform.IText;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.SAXEventRecorder;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class DocumentSplitter
  implements ContentHandler
{
  private static final String HEAD_ELEMENT_NAME = "head";
  private List _processingInstructions;
  private SAXEventRecorder _head;
  private boolean _inHead;
  private int _depth;
  private boolean _needNewNSScope;
  private NamespaceScope _currentNSScope;
  private boolean _needNSScopePop;
  private Locator _locator;
  private TransformerHandler _handler;
  private boolean _inDocument;
  private List _documents;
  private boolean _replayedHead;

  public DocumentSplitter()
  {
    this._processingInstructions = new LinkedList();
    this._head = new SAXEventRecorder();
    this._inHead = false;

    this._depth = 0;

    this._needNewNSScope = false;
    this._currentNSScope = new NamespaceScope();

    this._inDocument = false;

    this._documents = new LinkedList();

    this._replayedHead = false;
  }
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (this._inHead)
      this._head.characters(ch, start, length);
    else if (this._inDocument)
      this._handler.characters(ch, start, length);
  }

  public void endDocument() throws SAXException
  {
  }

  public void endPrefixMapping(String prefix) throws SAXException {
    if (this._inHead)
      this._head.endPrefixMapping(prefix);
    else if (this._inDocument)
      this._handler.endPrefixMapping(prefix);
    else
      this._needNSScopePop = true;
  }

  public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException
  {
    if (this._inHead)
      this._head.ignorableWhitespace(ch, start, length);
    else if (this._inDocument)
      this._handler.ignorableWhitespace(ch, start, length);
  }

  public void processingInstruction(String target, String data) throws SAXException
  {
    this._processingInstructions.add(new ProcessingInstruction(target, data));
  }

  public void setDocumentLocator(Locator locator)
  {
    this._locator = locator;
  }

  public void skippedEntity(String name) throws SAXException {
    if (this._inHead)
      this._head.skippedEntity(name);
    else if (this._inDocument)
      this._handler.skippedEntity(name);
  }

  public void startDocument() throws SAXException
  {
  }

  public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
    if (this._inHead) {
      this._head.startElement(uri, localName, qName, atts);
    } else if (this._inDocument) {
      if ((this._depth == 2) && (!this._replayedHead)) {
        if ("head".equalsIgnoreCase(qName)) {
          this._handler.startElement(uri, localName, qName, atts);
          this._head.replay(this._handler);
        } else {
          this._handler.startElement("", "head", "head", new AttributesImpl());
          this._head.replay(this._handler);
          this._handler.endElement("", "head", "head");

          this._handler.startElement(uri, localName, qName, atts);
        }

        this._replayedHead = true;
      } else {
        this._handler.startElement(uri, localName, qName, atts);
      }
    } else {
      if (this._needNewNSScope) {
        this._needNewNSScope = false;
        this._currentNSScope = new NamespaceScope(this._currentNSScope);
      }

      if (this._depth == 1) {
        if ("head".equalsIgnoreCase(qName)) {
          this._inHead = true;
          this._currentNSScope.replay(this._head, true);
        } else {
          try {
            this._inDocument = true;
            this._replayedHead = false;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);

            Document doc = factory.newDocumentBuilder().newDocument();
            this._documents.add(doc);
            this._handler = ((SAXTransformerFactory)SAXTransformerFactory.newInstance()).newTransformerHandler();

            this._handler.setResult(new DOMResult(doc));

            this._handler.startDocument();
            this._handler.setDocumentLocator(this._locator);
            for (Iterator i = this._processingInstructions.iterator(); i.hasNext(); ) {
              ProcessingInstruction pI = (ProcessingInstruction)i.next();
              this._handler.processingInstruction(pI.getTarget(), pI.getData());
            }

            this._currentNSScope.replay(this._handler, true);
            this._handler.startElement(uri, localName, qName, atts);
          } catch (ParserConfigurationException e) {
            throw new SAXException(e.getMessage(), e);
          } catch (TransformerConfigurationException e) {
            throw new SAXException(e.getMessage(), e);
          }
        }
      }
    }

    this._depth += 1;
  }

  public void endElement(String uri, String localName, String qName) throws SAXException {
    this._depth -= 1;

    if (this._needNSScopePop) {
      this._needNSScopePop = false;
      this._currentNSScope = this._currentNSScope.getParent();
    }

    if (this._inHead) {
      if (this._depth == 1) {
        this._currentNSScope.replay(this._head, false);
        this._inHead = false;
      } else {
        this._head.endElement(uri, localName, qName);
      }
    } else if (this._inDocument)
      if (this._depth == 1) {
        this._currentNSScope.replay(this._handler, false);
        this._handler.endElement(uri, localName, qName);
        this._handler.endDocument();
        this._inDocument = false;
      } else {
        this._handler.endElement(uri, localName, qName);
      }
  }

  public void startPrefixMapping(String prefix, String uri) throws SAXException
  {
    if (this._inHead) {
      this._head.startPrefixMapping(prefix, uri);
    } else if (this._inDocument) {
      this._handler.startPrefixMapping(prefix, uri);
    } else {
      this._needNewNSScope = true;
      this._currentNSScope.addNamespace(new Namespace(prefix, uri));
    }
  }

  public List getDocuments() {
    return this._documents;
  }

  private static class ProcessingInstruction
  {
    private String _target;
    private String _data;

    public ProcessingInstruction(String target, String data)
    {
      this._target = target;
      this._data = data;
    }

    public String getData() {
      return this._data;
    }

    public String getTarget() {
      return this._target;
    }
  }

  private static final class NamespaceScope
  {
    private NamespaceScope _parent;
    private List _namespaces = new LinkedList();

    public NamespaceScope() {
    }

    public NamespaceScope(NamespaceScope parent) {
      this._parent = parent;
    }

    public void addNamespace(DocumentSplitter.Namespace namespace) {
      this._namespaces.add(namespace);
    }

    public void replay(ContentHandler contentHandler, boolean start) throws SAXException {
      replay(contentHandler, new HashSet(), start);
    }

    private void replay(ContentHandler contentHandler, Set seen, boolean start) throws SAXException
    {
      for (Iterator i = this._namespaces.iterator(); i.hasNext(); ) {
        DocumentSplitter.Namespace ns = (DocumentSplitter.Namespace)i.next();

        if (!seen.contains(ns.getPrefix())) {
          seen.add(ns.getPrefix());
          if (start)
            contentHandler.startPrefixMapping(ns.getPrefix(), ns.getUri());
          else {
            contentHandler.endPrefixMapping(ns.getPrefix());
          }
        }
      }

      if (this._parent != null)
        this._parent.replay(contentHandler, seen, start);
    }

    public NamespaceScope getParent()
    {
      return this._parent;
    }
  }

  private static final class Namespace
  {
    private String _prefix;
    private String _uri;

    public Namespace(String prefix, String uri)
    {
      this._prefix = prefix;
      this._uri = uri;
    }

    public String getPrefix() {
      return this._prefix;
    }

    public String getUri() {
      return this._uri;
    }
  }
}