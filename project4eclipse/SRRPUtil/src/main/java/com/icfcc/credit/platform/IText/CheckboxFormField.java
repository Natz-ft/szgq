package com.icfcc.credit.platform.IText;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RadioCheckField;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintStream;
import org.w3c.dom.Element;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.RenderingContext;

public class CheckboxFormField extends AbstractFormField
{
  private static final String FIELD_TYPE = "Checkbox";

  public CheckboxFormField(LayoutContext c, BlockBox box, int cssWidth, int cssHeight)
  {
    initDimensions(c, box, cssWidth, cssHeight);
  }

  protected String getFieldType()
  {
    return "Checkbox";
  }

  public void paint(RenderingContext c, ITextOutputDevice outputDevice, BlockBox box)
  {
    PdfContentByte cb = outputDevice.getCurrentPage();

    PdfWriter writer = outputDevice.getWriter();
    Element elm = box.getElement();

    Rectangle targetArea = outputDevice.createLocalTargetArea(c, box);
    String onValue = getValue(elm);

    RadioCheckField field = new RadioCheckField(writer, targetArea, getFieldName(outputDevice, elm), onValue);

    field.setChecked(isChecked(elm));
    field.setCheckType(1);
    field.setBorderStyle(0);

    field.setBorderColor(Color.black);

    field.setBorderWidth(1.0F);
    try
    {
      PdfFormField formField = field.getCheckField();
      if (isReadOnly(elm))
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

  public int getBaseline()
  {
    return 0;
  }

  public boolean hasBaseline()
  {
    return false;
  }
}
