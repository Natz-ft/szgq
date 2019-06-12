package com.icfcc.credit.platform.IText;

import org.xhtmlrenderer.render.FSFontMetrics;

public class ITextFSFontMetrics
  implements FSFontMetrics
{
  private float _ascent;
  private float _descent;
  private float _strikethroughOffset;
  private float _strikethroughThickness;
  private float _underlineOffset;
  private float _underlineThickness;

  public float getAscent()
  {
    return this._ascent;
  }

  public void setAscent(float ascent) {
    this._ascent = ascent;
  }
  public float getDescent() {
    return this._descent;
  }

  public void setDescent(float descent) {
    this._descent = descent;
  }

  public float getStrikethroughOffset() {
    return this._strikethroughOffset;
  }

  public void setStrikethroughOffset(float strikethroughOffset) {
    this._strikethroughOffset = strikethroughOffset;
  }

  public float getStrikethroughThickness() {
    return this._strikethroughThickness;
  }

  public void setStrikethroughThickness(float strikethroughThickness) {
    this._strikethroughThickness = strikethroughThickness;
  }

  public float getUnderlineOffset() {
    return this._underlineOffset;
  }

  public void setUnderlineOffset(float underlineOffset) {
    this._underlineOffset = underlineOffset;
  }

  public float getUnderlineThickness() {
    return this._underlineThickness;
  }

  public void setUnderlineThickness(float underlineThickness) {
    this._underlineThickness = underlineThickness;
  }
}