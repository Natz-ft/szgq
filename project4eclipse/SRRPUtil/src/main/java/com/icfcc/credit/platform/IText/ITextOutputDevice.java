package com.icfcc.credit.platform.IText;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfArray;
import com.lowagie.text.pdf.PdfBorderArray;
import com.lowagie.text.pdf.PdfBorderDictionary;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDestination;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfIndirectObject;
import com.lowagie.text.pdf.PdfIndirectReference;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfOutline;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfTextArray;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xhtmlrenderer.context.StyleReference;
import org.xhtmlrenderer.css.constants.IdentValue;
import org.xhtmlrenderer.css.parser.FSCMYKColor;
import org.xhtmlrenderer.css.parser.FSColor;
import org.xhtmlrenderer.css.parser.FSRGBColor;
import org.xhtmlrenderer.css.style.CssContext;
import org.xhtmlrenderer.css.style.derived.RectPropertySet;
import org.xhtmlrenderer.css.value.FontSpecification;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.NamespaceHandler;
import org.xhtmlrenderer.extend.OutputDevice;
import org.xhtmlrenderer.layout.Layer;
import org.xhtmlrenderer.layout.PaintingInfo;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.render.AbstractOutputDevice;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.Box;
import org.xhtmlrenderer.render.FSFont;
import org.xhtmlrenderer.render.InlineLayoutBox;
import org.xhtmlrenderer.render.InlineText;
import org.xhtmlrenderer.render.JustificationInfo;
import org.xhtmlrenderer.render.PageBox;
import org.xhtmlrenderer.render.RenderingContext;
import org.xhtmlrenderer.util.Configuration;
import org.xhtmlrenderer.util.XRLog;
import org.xhtmlrenderer.util.XRRuntimeException;

public class ITextOutputDevice extends AbstractOutputDevice
  implements OutputDevice
{
  private static final int FILL = 1;
  private static final int STROKE = 2;
  private static final int CLIP = 3;
  private static AffineTransform IDENTITY = new AffineTransform();

  private static final BasicStroke STROKE_ONE = new BasicStroke(1.0F);

  private static final boolean ROUND_RECT_DIMENSIONS_DOWN = Configuration.isTrue("xr.pdf.round.rect.dimensions.down", false);
  private PdfContentByte _currentPage;
  private float _pageHeight;
  private ITextFSFont _font;
  private AffineTransform _transform = new AffineTransform();

  private Color _color = Color.BLACK;
  private Color _fillColor;
  private Color _strokeColor;
  private Stroke _stroke = null;
  private Stroke _originalStroke = null;
  private Stroke _oldStroke = null;
  private Area _clip;
  private SharedContext _sharedContext;
  private float _dotsPerPoint;
  private PdfWriter _writer;
  private Map _readerCache = new HashMap();
  private PdfDestination _defaultDestination;
  private List _bookmarks = new ArrayList();

  private List _metadata = new ArrayList();
  private Box _root;
  private int _startPageNo;
  private int _nextFormFieldIndex;
  private Set _linkTargetAreas;

  public ITextOutputDevice(float dotsPerPoint)
  {
    this._dotsPerPoint = dotsPerPoint;
  }

  public void setWriter(PdfWriter writer) {
    this._writer = writer;
  }

  public PdfWriter getWriter() {
    return this._writer;
  }

  public int getNextFormFieldIndex() {
    return ++this._nextFormFieldIndex;
  }

  public void initializePage(PdfContentByte currentPage, float height) {
    this._currentPage = currentPage;
    this._pageHeight = height;

    this._currentPage.saveState();

    this._transform = new AffineTransform();
    this._transform.scale(1.0D / this._dotsPerPoint, 1.0D / this._dotsPerPoint);

    this._stroke = transformStroke(STROKE_ONE);
    this._originalStroke = this._stroke;
    this._oldStroke = this._stroke;

    setStrokeDiff(this._stroke, null);

    if (this._defaultDestination == null) {
      this._defaultDestination = new PdfDestination(2, height);
      this._defaultDestination.addPage(this._writer.getPageReference(1));
    }

    this._linkTargetAreas = new HashSet();
  }

  public void finishPage() {
    this._currentPage.restoreState();
  }

  public void paintReplacedElement(RenderingContext c, BlockBox box) {
    ITextReplacedElement element = (ITextReplacedElement)box.getReplacedElement();
    element.paint(c, this, box);
  }

  public void paintBackground(RenderingContext c, Box box) {
    super.paintBackground(c, box);

    processLink(c, box);
  }

  private com.lowagie.text.Rectangle calcTotalLinkArea(RenderingContext c, Box box) {
    Box current = box;
    while (true) {
      Box prev = current.getPreviousSibling();
      if ((prev == null) || (prev.getElement() != box.getElement()))
      {
        break;
      }
      current = prev;
    }

    com.lowagie.text.Rectangle result = createLocalTargetArea(c, current, true);

    current = current.getNextSibling();
    while ((current != null) && (current.getElement() == box.getElement())) {
      result = add(result, createLocalTargetArea(c, current, true));

      current = current.getNextSibling();
    }

    return result;
  }

  private com.lowagie.text.Rectangle add(com.lowagie.text.Rectangle r1, com.lowagie.text.Rectangle r2) {
    float llx = Math.min(r1.getLeft(), r2.getLeft());
    float urx = Math.max(r1.getRight(), r2.getRight());
    float lly = Math.min(r1.getBottom(), r2.getBottom());
    float ury = Math.max(r1.getTop(), r2.getTop());

    return new com.lowagie.text.Rectangle(llx, lly, urx, ury);
  }

  private String createRectKey(com.lowagie.text.Rectangle rect) {
    return rect.getLeft() + ":" + rect.getBottom() + ":" + rect.getRight() + ":" + rect.getTop();
  }

  private com.lowagie.text.Rectangle checkLinkArea(RenderingContext c, Box box) {
    com.lowagie.text.Rectangle targetArea = calcTotalLinkArea(c, box);
    String key = createRectKey(targetArea);
    if (this._linkTargetAreas.contains(key)) {
      return null;
    }
    this._linkTargetAreas.add(key);
    return targetArea;
  }

  private void processLink(RenderingContext c, Box box) {
    Element elem = box.getElement();
    if (elem != null) {
      NamespaceHandler handler = this._sharedContext.getNamespaceHandler();
      String uri = handler.getLinkUri(elem);
      if (uri != null)
        if ((uri.length() > 1) && (uri.charAt(0) == '#')) {
          String anchor = uri.substring(1);
          Box target = this._sharedContext.getBoxById(anchor);
          if (target != null) {
            PdfDestination dest = createDestination(c, target);

            if (dest != null) {
              PdfAction action = new PdfAction();
              if (!"".equals(handler.getAttributeValue(elem, "onclick"))) {
                action = PdfAction.javaScript(handler.getAttributeValue(elem, "onclick"), this._writer);
              } else {
                action.put(PdfName.S, PdfName.GOTO);
                action.put(PdfName.D, dest);
              }

              com.lowagie.text.Rectangle targetArea = checkLinkArea(c, box);
              if (targetArea == null) {
                return;
              }

              targetArea.setBorder(0);
              targetArea.setBorderWidth(0.0F);

              PdfAnnotation annot = new PdfAnnotation(this._writer, targetArea.getLeft(), targetArea.getBottom(), targetArea.getRight(), targetArea.getTop(), action);

              annot.put(PdfName.SUBTYPE, PdfName.LINK);
              annot.setBorderStyle(new PdfBorderDictionary(0.0F, 0));
              annot.setBorder(new PdfBorderArray(0.0F, 0.0F, 0.0F));
              this._writer.addAnnotation(annot);
            }
          }
        } else if (uri.indexOf("://") != -1) {
          PdfAction action = new PdfAction(uri);

          com.lowagie.text.Rectangle targetArea = checkLinkArea(c, box);
          if (targetArea == null) {
            return;
          }
          PdfAnnotation annot = new PdfAnnotation(this._writer, targetArea.getLeft(), targetArea.getBottom(), targetArea.getRight(), targetArea.getTop(), action);

          annot.put(PdfName.SUBTYPE, PdfName.LINK);

          annot.setBorderStyle(new PdfBorderDictionary(0.0F, 0));
          annot.setBorder(new PdfBorderArray(0.0F, 0.0F, 0.0F));
          this._writer.addAnnotation(annot);
        }
    }
  }

  public com.lowagie.text.Rectangle createLocalTargetArea(RenderingContext c, Box box)
  {
    return createLocalTargetArea(c, box, false);
  }

  private com.lowagie.text.Rectangle createLocalTargetArea(RenderingContext c, Box box, boolean useAggregateBounds)
  {
    java.awt.Rectangle bounds;
    if ((useAggregateBounds) && (box.getPaintingInfo() != null))
      bounds = box.getPaintingInfo().getAggregateBounds();
    else {
      bounds = box.getContentAreaEdge(box.getAbsX(), box.getAbsY(), c);
    }

    Point2D docCorner = new Point2D.Double(bounds.x, bounds.y + bounds.height);
    Point2D pdfCorner = new Point2D.Double();
    this._transform.transform(docCorner, pdfCorner);
    pdfCorner.setLocation(pdfCorner.getX(), normalizeY((float)pdfCorner.getY()));

    com.lowagie.text.Rectangle result = new com.lowagie.text.Rectangle((float)pdfCorner.getX(), (float)pdfCorner.getY(), (float)pdfCorner.getX() + getDeviceLength(bounds.width), (float)pdfCorner.getY() + getDeviceLength(bounds.height));

    return result;
  }

  public com.lowagie.text.Rectangle createTargetArea(RenderingContext c, Box box) {
    PageBox current = c.getPage();
    boolean inCurrentPage = (box.getAbsY() > current.getTop()) && (box.getAbsY() < current.getBottom());

    if ((inCurrentPage) || (box.isContainedInMarginBox())) {
      return createLocalTargetArea(c, box);
    }
    java.awt.Rectangle bounds = box.getContentAreaEdge(box.getAbsX(), box.getAbsY(), c);
    PageBox page = this._root.getLayer().getPage(c, bounds.y);

    float bottom = getDeviceLength(page.getBottom() - (bounds.y + bounds.height) + page.getMarginBorderPadding(c, 4));

    float left = getDeviceLength(page.getMarginBorderPadding(c, 1) + bounds.x);

    com.lowagie.text.Rectangle result = new com.lowagie.text.Rectangle(left, bottom, left + getDeviceLength(bounds.width), bottom + getDeviceLength(bounds.height));

    return result;
  }

  public float getDeviceLength(float length)
  {
    return length / this._dotsPerPoint;
  }

  private PdfDestination createDestination(RenderingContext c, Box box) {
    PdfDestination result = null;

    PageBox page = this._root.getLayer().getPage(c, getPageRefY(box));
    if (page != null) {
      int distanceFromTop = page.getMarginBorderPadding(c, 3);
      distanceFromTop = (int)(distanceFromTop + (box.getAbsY() + box.getMargin(c).top() - page.getTop()));
      result = new PdfDestination(0, 0.0F, page.getHeight(c) / this._dotsPerPoint - distanceFromTop / this._dotsPerPoint, 0.0F);
      result.addPage(this._writer.getPageReference(this._startPageNo + page.getPageNo() + 1));
    }

    return result;
  }

  public void drawBorderLine(java.awt.Rectangle bounds, int side, int lineWidth, boolean solid) {
    float x = bounds.x;
    float y = bounds.y;
    float w = bounds.width;
    float h = bounds.height;

    float adj = solid ? lineWidth / 2.0F : 0.0F;
    float adj2 = lineWidth % 2 != 0 ? 0.5F : 0.0F;

    Line2D.Float line = null;

    if (side == 1) {
      line = new Line2D.Float(x + adj, y + lineWidth / 2 + adj2, x + w - adj, y + lineWidth / 2 + adj2);
    } else if (side == 2) {
      line = new Line2D.Float(x + lineWidth / 2 + adj2, y + adj, x + lineWidth / 2 + adj2, y + h - adj);
    } else if (side == 8) {
      float offset = lineWidth / 2;
      if (lineWidth % 2 != 0) {
        offset += 1.0F;
      }
      line = new Line2D.Float(x + w - offset + adj2, y + adj, x + w - offset + adj2, y + h - adj);
    } else if (side == 4) {
      float offset = lineWidth / 2;
      if (lineWidth % 2 != 0) {
        offset += 1.0F;
      }
      line = new Line2D.Float(x + adj, y + h - offset + adj2, x + w - adj, y + h - offset + adj2);
    }

    draw(line);
  }

  public void setColor(FSColor color) {
    if ((color instanceof FSRGBColor)) {
      FSRGBColor rgb = (FSRGBColor)color;
      this._color = new Color(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    } else if ((color instanceof FSCMYKColor)) {
      FSCMYKColor cmyk = (FSCMYKColor)color;
      this._color = new CMYKColor(cmyk.getCyan(), cmyk.getMagenta(), cmyk.getYellow(), cmyk.getBlack());
    } else {
      throw new RuntimeException("internal error: unsupported color class " + color.getClass().getName());
    }
  }

  private void draw(Shape s) {
    followPath(s, 2);
  }

  protected void drawLine(int x1, int y1, int x2, int y2) {
    Line2D line = new Line2D.Double(x1, y1, x2, y2);
    draw(line);
  }

  public void drawRect(int x, int y, int width, int height) {
    draw(new java.awt.Rectangle(x, y, width, height));
  }

  public void drawOval(int x, int y, int width, int height) {
    Ellipse2D oval = new Ellipse2D.Float(x, y, width, height);
    draw(oval);
  }

  public void fill(Shape s) {
    followPath(s, 1);
  }

  public void fillRect(int x, int y, int width, int height) {
    if (ROUND_RECT_DIMENSIONS_DOWN)
      fill(new java.awt.Rectangle(x, y, width - 1, height - 1));
    else
      fill(new java.awt.Rectangle(x, y, width, height));
  }

  public void fillOval(int x, int y, int width, int height)
  {
    Ellipse2D oval = new Ellipse2D.Float(x, y, width, height);
    fill(oval);
  }

  public void translate(double tx, double ty) {
    this._transform.translate(tx, ty);
  }



  public void setFont(FSFont font) {
    this._font = ((ITextFSFont)font);
  }

  private AffineTransform normalizeMatrix(AffineTransform current) {
    double[] mx = new double[6];
    AffineTransform result = new AffineTransform();
    result.getMatrix(mx);
    mx[3] = -1.0D;
    mx[5] = this._pageHeight;
    result = new AffineTransform(mx);
    result.concatenate(current);
    return result;
  }

  public void drawString(String s, float x, float y, JustificationInfo info) {
    if (Configuration.isTrue("xr.renderer.replace-missing-characters", false)) {
      s = replaceMissingCharacters(s);
    }
    if (s.length() == 0)
      return;
    PdfContentByte cb = this._currentPage;
    ensureFillColor();
    AffineTransform at = (AffineTransform)getTransform().clone();
    at.translate(x, y);
    AffineTransform inverse = normalizeMatrix(at);
    AffineTransform flipper = AffineTransform.getScaleInstance(1.0D, -1.0D);
    inverse.concatenate(flipper);
    inverse.scale(this._dotsPerPoint, this._dotsPerPoint);
    double[] mx = new double[6];
    inverse.getMatrix(mx);
    cb.beginText();

    boolean resetMode = false;
    ITextFontResolver.FontDescription desc = this._font.getFontDescription();
    float fontSize = this._font.getSize2D() / this._dotsPerPoint;
    cb.setFontAndSize(desc.getFont(), fontSize);
    float b = (float)mx[1];
    float c = (float)mx[2];
    FontSpecification fontSpec = getFontSpecification();
    if (fontSpec != null) {
      int need = ITextFontResolver.convertWeightToInt(fontSpec.fontWeight);
      int have = desc.getWeight();
      if (need > have) {
        cb.setTextRenderingMode(2);
        float lineWidth = fontSize * 0.04F;
        cb.setLineWidth(lineWidth);
        resetMode = true;
      }
      if ((fontSpec.fontStyle == IdentValue.ITALIC) && (desc.getStyle() != IdentValue.ITALIC)) {
        b = 0.0F;
        c = 0.21256F;
      }
    }
    cb.setTextMatrix((float)mx[0], b, c, (float)mx[3], (float)mx[4], (float)mx[5]);
    if (info == null) {
      cb.showText(s);
    } else {
      PdfTextArray array = makeJustificationArray(s, info);
      cb.showText(array);
    }
    if (resetMode) {
      cb.setTextRenderingMode(0);
      cb.setLineWidth(1.0F);
    }
    cb.endText();
  }

  private String replaceMissingCharacters(String string) {
    char[] charArr = string.toCharArray();
    char replacementCharacter = Configuration.valueAsChar("xr.renderer.missing-character-replacement", '#');

    if (!this._font.getFontDescription().getFont().charExists(replacementCharacter)) {
      XRLog.render(Level.INFO, "Missing replacement character [" + replacementCharacter + ":" + replacementCharacter + "]. No replacement will occur.");

      return string;
    }

    for (int i = 0; i < charArr.length; i++) {
      if ((charArr[i] == ' ') || (charArr[i] == ' ') || (charArr[i] == '　') || (this._font.getFontDescription().getFont().charExists(charArr[i])))
        continue;
      XRLog.render(Level.INFO, "Missing character [" + charArr[i] + ":" + charArr[i] + "] in string [" + string + "]. Replacing with '" + replacementCharacter + "'");

      charArr[i] = replacementCharacter;
    }

    return String.valueOf(charArr);
  }

  private PdfTextArray makeJustificationArray(String s, JustificationInfo info) {
    PdfTextArray array = new PdfTextArray();
    int len = s.length();
    for (int i = 0; i < len; i++) {
      char c = s.charAt(i);
      array.add(Character.toString(c));
      if (i == len - 1)
        continue;
      float offset;
      if ((c == ' ') || (c == ' ') || (c == '　'))
        offset = info.getSpaceAdjust();
      else {
        offset = info.getNonSpaceAdjust();
      }
      array.add(-offset / this._dotsPerPoint * 1000.0F / (this._font.getSize2D() / this._dotsPerPoint));
    }

    return array;
  }

  private AffineTransform getTransform() {
    return this._transform;
  }

  private void ensureFillColor() {
    if (!this._color.equals(this._fillColor)) {
      this._fillColor = this._color;
      this._currentPage.setColorFill(this._fillColor);
    }
  }

  private void ensureStrokeColor() {
    if (!this._color.equals(this._strokeColor)) {
      this._strokeColor = this._color;
      this._currentPage.setColorStroke(this._strokeColor);
    }
  }

  public PdfContentByte getCurrentPage() {
    return this._currentPage;
  }

  private void followPath(Shape s, int drawType) {
    PdfContentByte cb = this._currentPage;
    if (s == null) {
      return;
    }
    if ((drawType == 2) && 
      (!(this._stroke instanceof BasicStroke))) {
      s = this._stroke.createStrokedShape(s);
      followPath(s, 1);
      return;
    }

    if (drawType == 2) {
      setStrokeDiff(this._stroke, this._oldStroke);
      this._oldStroke = this._stroke;
      ensureStrokeColor();
    } else if (drawType == 1) {
      ensureFillColor();
    }
    PathIterator points;
    if (drawType == 3)
      points = s.getPathIterator(IDENTITY);
    else {
      points = s.getPathIterator(this._transform);
    }
    float[] coords = new float[6];
    int traces = 0;
    while (!points.isDone()) {
      traces++;
      int segtype = points.currentSegment(coords);
      normalizeY(coords);
      switch (segtype) {
      case 4:
        cb.closePath();
        break;
      case 3:
        cb.curveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
        break;
      case 1:
        cb.lineTo(coords[0], coords[1]);
        break;
      case 0:
        cb.moveTo(coords[0], coords[1]);
        break;
      case 2:
        cb.curveTo(coords[0], coords[1], coords[2], coords[3]);
      }

      points.next();
    }

    switch (drawType) {
    case 1:
      if (traces <= 0) break;
      if (points.getWindingRule() == 0)
        cb.eoFill();
      else
        cb.fill(); break;
    case 2:
      if (traces <= 0) break;
      cb.stroke(); break;
    default:
      if (traces == 0)
        cb.rectangle(0.0F, 0.0F, 0.0F, 0.0F);
      if (points.getWindingRule() == 0)
        cb.eoClip();
      else
        cb.clip();
      cb.newPath();
    }
  }

  private float normalizeY(float y) {
    return this._pageHeight - y;
  }

  private void normalizeY(float[] coords) {
    coords[1] = normalizeY(coords[1]);
    coords[3] = normalizeY(coords[3]);
    coords[5] = normalizeY(coords[5]);
  }

  private void setStrokeDiff(Stroke newStroke, Stroke oldStroke) {
    PdfContentByte cb = this._currentPage;
    if (newStroke == oldStroke)
      return;
    if (!(newStroke instanceof BasicStroke))
      return;
    BasicStroke nStroke = (BasicStroke)newStroke;
    boolean oldOk = oldStroke instanceof BasicStroke;
    BasicStroke oStroke = null;
    if (oldOk)
      oStroke = (BasicStroke)oldStroke;
    if ((!oldOk) || (nStroke.getLineWidth() != oStroke.getLineWidth()))
      cb.setLineWidth(nStroke.getLineWidth());
    if ((!oldOk) || (nStroke.getEndCap() != oStroke.getEndCap())) {
      switch (nStroke.getEndCap()) {
      case 0:
        cb.setLineCap(0);
        break;
      case 2:
        cb.setLineCap(2);
        break;
      default:
        cb.setLineCap(1);
      }
    }
    if ((!oldOk) || (nStroke.getLineJoin() != oStroke.getLineJoin())) {
      switch (nStroke.getLineJoin()) {
      case 0:
        cb.setLineJoin(0);
        break;
      case 2:
        cb.setLineJoin(2);
        break;
      default:
        cb.setLineJoin(1);
      }
    }
    if ((!oldOk) || (nStroke.getMiterLimit() != oStroke.getMiterLimit()))
      cb.setMiterLimit(nStroke.getMiterLimit());
    boolean makeDash;
    if (oldOk)
    {
      if (nStroke.getDashArray() != null)
      {
        if (nStroke.getDashPhase() != oStroke.getDashPhase()) {
          makeDash = true;
        }
        else
        {
          if (!Arrays.equals(nStroke.getDashArray(), oStroke.getDashArray()))
            makeDash = true;
          else
            makeDash = false;
        }
      }
      else
      {
        if (oStroke.getDashArray() != null)
          makeDash = true;
        else
          makeDash = false; 
      }
    } else {
      makeDash = true;
    }
    if (makeDash) {
      float[] dash = nStroke.getDashArray();
      if (dash == null) {
        cb.setLiteral("[]0 d\n");
      } else {
        cb.setLiteral('[');
        int lim = dash.length;
        for (int k = 0; k < lim; k++) {
          cb.setLiteral(dash[k]);
          cb.setLiteral(' ');
        }
        cb.setLiteral(']');
        cb.setLiteral(nStroke.getDashPhase());
        cb.setLiteral(" d\n");
      }
    }
  }

  public void setStroke(Stroke s) {
    this._originalStroke = s;
    this._stroke = transformStroke(s);
  }

  private Stroke transformStroke(Stroke stroke) {
    if (!(stroke instanceof BasicStroke))
      return stroke;
    BasicStroke st = (BasicStroke)stroke;
    float scale = (float)Math.sqrt(Math.abs(this._transform.getDeterminant()));
    float[] dash = st.getDashArray();
    if (dash != null) {
      for (int k = 0; k < dash.length; k++)
        dash[k] *= scale;
    }
    return new BasicStroke(st.getLineWidth() * scale, st.getEndCap(), st.getLineJoin(), st.getMiterLimit(), dash, st.getDashPhase() * scale);
  }

  public void clip(Shape s)
  {
    if (s != null) {
      s = this._transform.createTransformedShape(s);
      if (this._clip == null)
        this._clip = new Area(s);
      else
        this._clip.intersect(new Area(s));
      followPath(s, 3);
    } else {
      throw new XRRuntimeException("Shape is null, unexpected");
    }
  }

  public Shape getClip() {
    try {
      return this._transform.createInverse().createTransformedShape(this._clip); } catch (NoninvertibleTransformException e) {
    }
    return null;
  }

  public void setClip(Shape s)
  {
    PdfContentByte cb = this._currentPage;
    cb.restoreState();
    cb.saveState();
    if (s != null)
      s = this._transform.createTransformedShape(s);
    if (s == null) {
      this._clip = null;
    } else {
      this._clip = new Area(s);
      followPath(s, 3);
    }
    this._fillColor = null;
    this._strokeColor = null;
    this._oldStroke = null;
  }

  public Stroke getStroke() {
    return this._originalStroke;
  }

  public void drawImage(FSImage fsImage, int x, int y) {
    if ((fsImage instanceof PDFAsImage)) {
      drawPDFAsImage((PDFAsImage)fsImage, x, y);
    } else {
      Image image = ((ITextFSImage)fsImage).getImage();

      if ((fsImage.getHeight() <= 0) || (fsImage.getWidth() <= 0)) {
        return;
      }

      AffineTransform at = AffineTransform.getTranslateInstance(x, y);
      at.translate(0.0D, fsImage.getHeight());
      at.scale(fsImage.getWidth(), fsImage.getHeight());

      AffineTransform inverse = normalizeMatrix(this._transform);
      AffineTransform flipper = AffineTransform.getScaleInstance(1.0D, -1.0D);
      inverse.concatenate(at);
      inverse.concatenate(flipper);

      double[] mx = new double[6];
      inverse.getMatrix(mx);
      try
      {
        this._currentPage.addImage(image, (float)mx[0], (float)mx[1], (float)mx[2], (float)mx[3], (float)mx[4], (float)mx[5]);
      } catch (DocumentException e) {
        throw new XRRuntimeException(e.getMessage(), e);
      }
    }
  }

  private void drawPDFAsImage(PDFAsImage image, int x, int y) {
    URL url = image.getURL();
    PdfReader reader = null;
    try
    {
      reader = getReader(url);
    } catch (IOException e) {
      throw new XRRuntimeException("Could not load " + url + ": " + e.getMessage(), e);
    } catch (URISyntaxException e) {
      throw new XRRuntimeException("Could not load " + url + ": " + e.getMessage(), e);
    }

    PdfImportedPage page = getWriter().getImportedPage(reader, 1);

    AffineTransform at = AffineTransform.getTranslateInstance(x, y);
    at.translate(0.0D, image.getHeightAsFloat());
    at.scale(image.getWidthAsFloat(), image.getHeightAsFloat());

    AffineTransform inverse = normalizeMatrix(this._transform);
    AffineTransform flipper = AffineTransform.getScaleInstance(1.0D, -1.0D);
    inverse.concatenate(at);
    inverse.concatenate(flipper);

    double[] mx = new double[6];
    inverse.getMatrix(mx);

    mx[0] = image.scaleWidth();
    mx[3] = image.scaleHeight();

    this._currentPage.restoreState();
    this._currentPage.addTemplate(page, (float)mx[0], (float)mx[1], (float)mx[2], (float)mx[3], (float)mx[4], (float)mx[5]);
    this._currentPage.saveState();
  }

  public PdfReader getReader(URL url) throws IOException, URISyntaxException {
    URI uri = url.toURI();
    PdfReader result = (PdfReader)this._readerCache.get(uri);
    if (result == null) {
      result = new PdfReader(url);
      this._readerCache.put(uri, result);
    }
    return result;
  }

  public float getDotsPerPoint() {
    return this._dotsPerPoint;
  }

  public void start(Document doc) {
    loadBookmarks(doc);
    loadMetadata(doc);
  }

  public void finish(RenderingContext c, Box root) {
    writeOutline(c, root);
    writeNamedDestinations(c);
  }

  private void writeOutline(RenderingContext c, Box root) {
    if (this._bookmarks.size() > 0) {
      this._writer.setViewerPreferences(128);
      writeBookmarks(c, root, this._writer.getRootOutline(), this._bookmarks);
    }
  }

  private void writeBookmarks(RenderingContext c, Box root, PdfOutline parent, List bookmarks) {
    for (Iterator i = bookmarks.iterator(); i.hasNext(); ) {
      Bookmark bookmark = (Bookmark)i.next();
      writeBookmark(c, root, parent, bookmark);
    }
  }

  private void writeNamedDestinations(RenderingContext c) {
    Map idMap = getSharedContext().getIdMap();
    if ((idMap != null) && (!idMap.isEmpty())) {
      PdfArray dests = new PdfArray();
      try {
        Iterator it = idMap.entrySet().iterator();
        while (it.hasNext()) {
          Map.Entry entry = (Map.Entry)it.next();

          String anchorName = (String)entry.getKey();
          dests.add(new PdfString(anchorName, "UnicodeBig"));

          Box targetBox = (Box)entry.getValue();
          PdfDestination dest = createDestination(c, targetBox);
          if (dest != null) {
            PdfIndirectReference ref = this._writer.addToBody(dest).getIndirectReference();
            dests.add(ref);
          }
        }

        PdfDictionary nametree = new PdfDictionary();
        nametree.put(PdfName.NAMES, dests);
        PdfIndirectReference nameTreeRef = this._writer.addToBody(nametree).getIndirectReference();

        PdfDictionary names = new PdfDictionary();
        names.put(PdfName.DESTS, nameTreeRef);
        PdfIndirectReference destinationsRef = this._writer.addToBody(names).getIndirectReference();

        this._writer.getExtraCatalog().put(PdfName.NAMES, destinationsRef);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private int getPageRefY(Box box) {
    if ((box instanceof InlineLayoutBox)) {
      InlineLayoutBox iB = (InlineLayoutBox)box;
      return iB.getAbsY() + iB.getBaseline();
    }
    return box.getAbsY();
  }

  private void writeBookmark(RenderingContext c, Box root, PdfOutline parent, Bookmark bookmark)
  {
    String href = bookmark.getHRef();
    PdfDestination target = null;
    if ((href.length() > 0) && (href.charAt(0) == '#')) {
      Box box = this._sharedContext.getBoxById(href.substring(1));
      if (box != null) {
        PageBox page = root.getLayer().getPage(c, getPageRefY(box));
        int distanceFromTop = page.getMarginBorderPadding(c, 3);
        distanceFromTop += box.getAbsY() - page.getTop();
        target = new PdfDestination(0, 0.0F, normalizeY(distanceFromTop / this._dotsPerPoint), 0.0F);
        target.addPage(this._writer.getPageReference(this._startPageNo + page.getPageNo() + 1));
      }
    }
    if (target == null) {
      target = this._defaultDestination;
    }
    PdfOutline outline = new PdfOutline(parent, target, bookmark.getName());
    writeBookmarks(c, root, outline, bookmark.getChildren());
  }

  private void loadBookmarks(Document doc) {
    Element head = DOMUtil.getChild(doc.getDocumentElement(), "head");
    Iterator i;
    if (head != null) {
      Element bookmarks = DOMUtil.getChild(head, "bookmarks");
      if (bookmarks != null) {
        List l = DOMUtil.getChildren(bookmarks, "bookmark");
        if (l != null)
          for (i = l.iterator(); i.hasNext(); ) {
            Element e = (Element)i.next();
            loadBookmark(null, e);
          }
      }
    }
  }

  private void loadBookmark(Bookmark parent, Element bookmark)
  {
    Bookmark us = new Bookmark(bookmark.getAttribute("name"), bookmark.getAttribute("href"));
    if (parent == null)
      this._bookmarks.add(us);
    else {
      parent.addChild(us);
    }
    List l = DOMUtil.getChildren(bookmark, "bookmark");
    Iterator i;
    if (l != null)
      for (i = l.iterator(); i.hasNext(); ) {
        Element e = (Element)i.next();
        loadBookmark(us, e);
      }
  }

  public void addMetadata(String name, String value)
  {
    if ((name != null) && (value != null)) {
      Metadata m = new Metadata(name, value);
      this._metadata.add(m);
    }
  }

  public String getMetadataByName(String name)
  {
    if (name != null) {
      int i = 0; for (int len = this._metadata.size(); i < len; i++) {
        Metadata m = (Metadata)this._metadata.get(i);
        if ((m != null) && (m.getName().equalsIgnoreCase(name))) {
          return m.getContent();
        }
      }
    }
    return null;
  }

  public ArrayList getMetadataListByName(String name)
  {
    ArrayList result = new ArrayList();
    if (name != null) {
      int i = 0; for (int len = this._metadata.size(); i < len; i++) {
        Metadata m = (Metadata)this._metadata.get(i);
        if ((m != null) && (m.getName().equalsIgnoreCase(name))) {
          result.add(m.getContent());
        }
      }
    }
    return result;
  }

  private void loadMetadata(Document doc)
  {
    Element head = DOMUtil.getChild(doc.getDocumentElement(), "head");
    if (head != null) {
      List l = DOMUtil.getChildren(head, "meta");
      Iterator i;
      if (l != null) {
        for (i = l.iterator(); i.hasNext(); ) {
          Element e = (Element)i.next();
          String name = e.getAttribute("name");
          if (name != null) {
            String content = e.getAttribute("content");
            Metadata m = new Metadata(name, content);
            this._metadata.add(m);
          }
        }
      }

      String title = getMetadataByName("title");
      if (title == null) {
        Element t = DOMUtil.getChild(head, "title");
        if (t != null) {
          title = DOMUtil.getText(t).trim();
          Metadata m = new Metadata("title", title);
          this._metadata.add(m);
        }
      }
    }
  }

  public void setMetadata(String name, String value)
  {
    if (name != null) {
      boolean remove = value == null;
      int free = -1;
      int i = 0; for (int len = this._metadata.size(); i < len; i++) {
        Metadata m = (Metadata)this._metadata.get(i);
        if (m != null) {
          if (m.getName().equalsIgnoreCase(name))
            if (!remove) {
              remove = true;
              m.setContent(value);
            } else {
              this._metadata.set(i, null);
            }
        }
        else if (free == -1) {
          free = i;
        }
      }
      if (!remove) {
        Metadata m = new Metadata(name, value);
        if (free == -1)
          this._metadata.add(m);
        else
          this._metadata.set(free, m);
      }
    }
  }

  public SharedContext getSharedContext()
  {
    return this._sharedContext;
  }

  public void setSharedContext(SharedContext sharedContext) {
    this._sharedContext = sharedContext;
    sharedContext.getCss().setSupportCMYKColors(true);
  }

  public void setRoot(Box root) {
    this._root = root;
  }

  public int getStartPageNo() {
    return this._startPageNo;
  }

  public void setStartPageNo(int startPageNo) {
    this._startPageNo = startPageNo;
  }

  public void drawSelection(RenderingContext c, InlineText inlineText) {
    throw new UnsupportedOperationException();
  }

  public boolean isSupportsSelection() {
    return false;
  }

  public boolean isSupportsCMYKColors() {
    return true;
  }

  public List findPagePositionsByID(CssContext c, Pattern pattern) {
    Map idMap = this._sharedContext.getIdMap();
    if (idMap == null) {
      return Collections.EMPTY_LIST;
    }

    List result = new ArrayList();
    for (Iterator i = idMap.entrySet().iterator(); i.hasNext(); ) {
      Map.Entry entry = (Map.Entry)i.next();
      String id = (String)entry.getKey();
      if (pattern.matcher(id).find()) {
        Box box = (Box)entry.getValue();
        PagePosition pos = calcPDFPagePosition(c, id, box);
        if (pos != null) {
          result.add(pos);
        }
      }
    }

    Collections.sort(result, new Comparator() {
      public int compare(Object arg0, Object arg1) {
        PagePosition p1 = (PagePosition)arg0;
        PagePosition p2 = (PagePosition)arg1;
        return p1.getPageNo() - p2.getPageNo();
      }
    });
    return result;
  }

  private PagePosition calcPDFPagePosition(CssContext c, String id, Box box) {
    PageBox page = this._root.getLayer().getLastPage(c, box);
    if (page == null) {
      return null;
    }

    float x = box.getAbsX() + page.getMarginBorderPadding(c, 1);
    float y = page.getBottom() - (box.getAbsY() + box.getHeight()) + page.getMarginBorderPadding(c, 4);
    x /= this._dotsPerPoint;
    y /= this._dotsPerPoint;

    PagePosition result = new PagePosition();
    result.setId(id);
    result.setPageNo(page.getPageNo());
    result.setX(x);
    result.setY(y);
    result.setWidth(box.getEffectiveWidth() / this._dotsPerPoint);
    result.setHeight(box.getHeight() / this._dotsPerPoint);

    return result;
  }

  private static class Metadata
  {
    private String _name;
    private String _content;

    public Metadata(String name, String content)
    {
      this._name = name;
      this._content = content;
    }

    public String getContent() {
      return this._content;
    }

    public void setContent(String content) {
      this._content = content;
    }

    public String getName() {
      return this._name;
    }

    public void setName(String name) {
      this._name = name;
    }
  }

  private static class Bookmark
  {
    private String _name;
    private String _HRef;
    private List _children;

    public Bookmark()
    {
    }

    public Bookmark(String name, String href)
    {
      this._name = name;
      this._HRef = href;
    }

    public String getHRef() {
      return this._HRef;
    }

    public void setHRef(String href) {
      this._HRef = href;
    }

    public String getName() {
      return this._name;
    }

    public void setName(String name) {
      this._name = name;
    }

    public void addChild(Bookmark child) {
      if (this._children == null) {
        this._children = new ArrayList();
      }
      this._children.add(child);
    }

    public List getChildren() {
      return this._children == null ? Collections.EMPTY_LIST : this._children;
    }
  }

@Override
public Object getRenderingHint(Key key) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setRenderingHint(Key key, Object value) {
	// TODO Auto-generated method stub
	
}
}