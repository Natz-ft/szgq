package com.icfcc.credit.platform.IText;

import java.util.List;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.RenderingContext;

public class TextAreaFormField extends AbstractFormField
{
  private static final String FIELD_TYPE = "TextArea";
  private static final int DEFAULT_ROWS = 7;
  private static final int DEFAULT_COLS = 25;
  private List _lines;

  public TextAreaFormField(LayoutContext c, BlockBox box, int cssWidth, int cssHeight)
  {
  }

  protected String getFieldType()
  {
    return "TextArea";
  }

  public void paint(RenderingContext c, ITextOutputDevice outputDevice, BlockBox box)
  {
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