package com.icfcc.credit.platform.IText;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

public class SplitterTest
{
  public static void main(String[] args)
    throws Exception
  {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setNamespaceAware(true);
    factory.setValidating(false);

    XMLReader reader = factory.newSAXParser().getXMLReader();

    reader.setErrorHandler(new ErrorHandler() {
      public void error(SAXParseException exception) throws SAXException {
        throw exception;
      }

      public void fatalError(SAXParseException exception) throws SAXException {
        throw exception;
      }

      public void warning(SAXParseException exception) throws SAXException {
        throw exception;
      }
    });
    DocumentSplitter splitter = new DocumentSplitter();
    reader.setContentHandler(splitter);

    reader.parse(args[0]);

    for (Iterator i = splitter.getDocuments().iterator(); i.hasNext(); ) {
      Document doc = (Document)i.next();
      System.out.println(doc.getDocumentElement());
    }
  }
}