package com.icfcc.credit.platform.IText;

import com.lowagie.text.pdf.PdfAcroForm;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Point;
import org.w3c.dom.Element;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.RenderingContext;

public class EmptyReplacedElement extends AbstractFormField
{
  private static final String FIELD_TYPE = "Hidden";
  private int _width;
  private int _height;
  private Point _location = new Point(0, 0);

  public EmptyReplacedElement(int width, int height)
  {
    this._width = width;
    this._height = height;
  }

  public void paint(RenderingContext c, ITextOutputDevice outputDevice, BlockBox box)
  {
    PdfContentByte cb = outputDevice.getCurrentPage();

    PdfWriter writer = outputDevice.getWriter();

    PdfAcroForm acroForm = writer.getAcroForm();
    Element elem = box.getElement();
    String name = getFieldName(outputDevice, elem);
    String value = getValue(elem);
    acroForm.addHiddenField(name, value);
  }

  public int getIntrinsicWidth()
  {
    return this._width;
  }

  public int getIntrinsicHeight()
  {
    return this._height;
  }

  public Point getLocation()
  {
    return this._location;
  }

  public void setLocation(int x, int y)
  {
    this._location = new Point(0, 0);
  }

  protected String getFieldType()
  {
    return "Hidden";
  }

  public void detach(LayoutContext c)
  {
  }

  public boolean isRequiresInteractivePaint()
  {
    return false;
  }

  public boolean hasBaseline()
  {
    return false;
  }

  public int getBaseline()
  {
    return 0;
  }
}