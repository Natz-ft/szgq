package com.icfcc.credit.platform.IText;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfAppearance;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;
import java.io.IOException;
import java.io.PrintStream;
import org.w3c.dom.Element;
import org.xhtmlrenderer.css.parser.FSColor;
import org.xhtmlrenderer.css.style.CalculatedStyle;
import org.xhtmlrenderer.extend.TextRenderer;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.FSFont;
import org.xhtmlrenderer.render.RenderingContext;
import org.xhtmlrenderer.util.Util;

public class TextFormField extends AbstractFormField
{
  private static final String FIELD_TYPE = "Text";
  private static final int DEFAULT_SIZE = 15;
  private int _baseline;

  public TextFormField(LayoutContext c, BlockBox box, int cssWidth, int cssHeight)
  {
    initDimensions(c, box, cssWidth, cssHeight);

    float fontSize = box.getStyle().getFSFont(c).getSize2D();

    this._baseline = (int)(getHeight() / 2 + fontSize * 0.3F);
  }

  protected void initDimensions(LayoutContext c, BlockBox box, int cssWidth, int cssHeight)
  {
    if (cssWidth != -1)
    {
      setWidth(cssWidth);
    }
    else
    {
      setWidth(c.getTextRenderer().getWidth(c.getFontContext(), box.getStyle().getFSFont(c), spaces(getSize(box.getElement()))));
    }

    if (cssHeight != -1)
    {
      setHeight(cssHeight);
    }
    else
    {
      setHeight((int)box.getStyle().getLineHeight(c));
    }
  }

  protected String getFieldType()
  {
    return "Text";
  }

  public void paint(RenderingContext c, ITextOutputDevice outputDevice, BlockBox box)
  {
    PdfWriter writer = outputDevice.getWriter();

    Element elem = box.getElement();

    Rectangle targetArea = outputDevice.createLocalTargetArea(c, box);
    TextField field = new TextField(writer, targetArea, getFieldName(outputDevice, elem));

    String value = getValue(elem);
    field.setText(value);
    try
    {
      PdfFormField formField = field.getTextField();
      createAppearance(c, outputDevice, box, formField, value);

      if (isReadOnly(elem))
      {
        formField.setFieldFlags(1);
      }
      writer.addAnnotation(formField);
    }
    catch (IOException ioe) {
      System.out.println(ioe);
    }
    catch (DocumentException de) {
      System.out.println(de);
    }
  }

  private void createAppearance(RenderingContext c, ITextOutputDevice outputDevice, BlockBox box, PdfFormField field, String value)
  {
    PdfWriter writer = outputDevice.getWriter();
    ITextFSFont font = (ITextFSFont)box.getStyle().getFSFont(c);

    PdfContentByte cb = writer.getDirectContent();

    float width = outputDevice.getDeviceLength(getWidth());
    float height = outputDevice.getDeviceLength(getHeight());
    float fontSize = outputDevice.getDeviceLength(font.getSize2D());

    PdfAppearance tp = cb.createAppearance(width, height);
    PdfAppearance tp2 = (PdfAppearance)tp.getDuplicate();
    tp2.setFontAndSize(font.getFontDescription().getFont(), fontSize);

    FSColor color = box.getStyle().getColor();
    setFillColor(tp2, color);

    field.setDefaultAppearanceString(tp2);
    tp.beginVariableText();
    tp.saveState();
    tp.beginText();
    tp.setFontAndSize(font.getFontDescription().getFont(), fontSize);
    setFillColor(tp, color);
    tp.setTextMatrix(0.0F, height / 2.0F - fontSize * 0.3F);
    tp.showText(value);
    tp.endText();
    tp.restoreState();
    tp.endVariableText();
    field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, tp);
  }

  private int getSize(Element elem)
  {
    String sSize = elem.getAttribute("size");
    if (Util.isNullOrEmpty(sSize))
    {
      return 15;
    }

    try
    {
      return Integer.parseInt(sSize.trim());
    } catch (NumberFormatException e) {
    }
    return 15;
  }

  private int getMaxLength(Element elem)
  {
    String sMaxLen = elem.getAttribute("maxlength");
    if (Util.isNullOrEmpty(sMaxLen))
    {
      return 0;
    }

    try
    {
      return Integer.parseInt(sMaxLen.trim());
    } catch (NumberFormatException e) {
    }
    return 0;
  }

  protected String getValue(Element e)
  {
    String result = e.getAttribute("value");
    if (Util.isNullOrEmpty(result))
    {
      return "";
    }

    return result;
  }

  public int getBaseline()
  {
    return this._baseline;
  }

  public boolean hasBaseline()
  {
    return true;
  }
}