package com.icfcc.credit.platform.IText;

import com.lowagie.text.pdf.PdfTemplate;
import java.awt.Point;
import org.w3c.dom.Element;
import org.xhtmlrenderer.css.parser.FSCMYKColor;
import org.xhtmlrenderer.css.parser.FSColor;
import org.xhtmlrenderer.css.parser.FSRGBColor;
import org.xhtmlrenderer.css.style.CalculatedStyle;
import org.xhtmlrenderer.css.value.FontSpecification;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.util.Util;

public abstract class AbstractFormField
  implements ITextReplacedElement
{
  protected static final String DEFAULT_CHECKED_STATE = "Yes";
  protected static final String OFF_STATE = "Off";
  private static final float FONT_SIZE_ADJUSTMENT = 0.8F;
  private int _x;
  private int _y;
  private int _width;
  private int _height;
  private String _fieldName;

  protected abstract String getFieldType();

  protected int getX()
  {
    return this._x;
  }

  protected void setX(int x) {
    this._x = x;
  }

  protected int getY() {
    return this._y;
  }

  protected void setY(int y) {
    this._y = y;
  }

  protected int getWidth() {
    return this._width;
  }

  protected void setWidth(int width) {
    this._width = width;
  }

  protected int getHeight() {
    return this._height;
  }

  protected void setHeight(int height) {
    this._height = height;
  }

  protected String getFieldName(ITextOutputDevice outputDevice, Element e) {
    if (this._fieldName == null) {
      String result = e.getAttribute("name");

      if (Util.isNullOrEmpty(result)) {
        this._fieldName = (getFieldType() + outputDevice.getNextFormFieldIndex());
      }
      else {
        this._fieldName = result;
      }
    }

    return this._fieldName;
  }

  protected String getValue(Element e) {
    String result = e.getAttribute("value");

    if (Util.isNullOrEmpty(result)) {
      return "Yes";
    }
    return result;
  }

  protected boolean isChecked(Element e)
  {
    return !Util.isNullOrEmpty(e.getAttribute("checked"));
  }

  protected boolean isReadOnly(Element e) {
    return !Util.isNullOrEmpty(e.getAttribute("readonly"));
  }

  protected boolean isSelected(Element e) {
    return Util.isNullOrEmpty(e.getAttribute("selected"));
  }

  public void detach(LayoutContext c) {
  }

  public int getIntrinsicHeight() {
    return getHeight();
  }

  public int getIntrinsicWidth() {
    return getWidth();
  }

  public Point getLocation() {
    return new Point(getX(), getY());
  }

  public boolean isRequiresInteractivePaint()
  {
    return false;
  }

  public void setLocation(int x, int y) {
    setX(x);
    setY(y);
  }

  protected void initDimensions(LayoutContext c, BlockBox box, int cssWidth, int cssHeight)
  {
    if (cssWidth != -1) {
      setWidth(cssWidth);
    }
    else if (cssHeight != -1)
      setWidth(cssHeight);
    else {
      setWidth((int)(box.getStyle().getFont(c).size * 0.8F));
    }

    if (cssHeight != -1) {
      setHeight(cssHeight);
    }
    else if (cssWidth != -1)
      setHeight(cssWidth);
    else
      setHeight((int)(box.getStyle().getFont(c).size * 0.8F));
  }

  protected String spaces(int count)
  {
    StringBuffer result = new StringBuffer(count);
    for (int i = 0; i < count; i++) {
      result.append(' ');
    }
    return result.toString();
  }

  protected void setStrokeColor(PdfTemplate template, FSColor color)
  {
    if ((color instanceof FSRGBColor))
    {
      FSRGBColor rgb = (FSRGBColor)color;
      template.setRGBColorStroke(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    }
    else if ((color instanceof FSCMYKColor))
    {
      FSCMYKColor cmyk = (FSCMYKColor)color;
      template.setCMYKColorStroke((int)(cmyk.getCyan() * 255.0F), (int)(cmyk.getMagenta() * 255.0F), (int)(cmyk.getYellow() * 255.0F), (int)(cmyk.getBlack() * 255.0F));
    }
  }

  protected void setFillColor(PdfTemplate template, FSColor color)
  {
    if ((color instanceof FSRGBColor))
    {
      FSRGBColor rgb = (FSRGBColor)color;
      template.setRGBColorFill(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    }
    else if ((color instanceof FSCMYKColor))
    {
      FSCMYKColor cmyk = (FSCMYKColor)color;
      template.setCMYKColorFill((int)(cmyk.getCyan() * 255.0F), (int)(cmyk.getMagenta() * 255.0F), (int)(cmyk.getYellow() * 255.0F), (int)(cmyk.getBlack() * 255.0F));
    }
  }
}