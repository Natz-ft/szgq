package com.icfcc.credit.platform.IText;

import org.xhtmlrenderer.render.FSFont;

public class ITextFSFont
  implements FSFont
{
  private ITextFontResolver.FontDescription _font;
  private float _size;

  public ITextFSFont(ITextFontResolver.FontDescription font, float size)
  {
    this._font = font;
    this._size = size;
  }

  public float getSize2D() {
    return this._size;
  }

  public ITextFontResolver.FontDescription getFontDescription() {
    return this._font;
  }
}