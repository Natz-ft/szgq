package com.icfcc.credit.platform.IText;

import com.lowagie.text.pdf.BaseFont;
import java.awt.Rectangle;
import org.xhtmlrenderer.extend.FSGlyphVector;
import org.xhtmlrenderer.extend.FontContext;
import org.xhtmlrenderer.extend.OutputDevice;
import org.xhtmlrenderer.extend.TextRenderer;
import org.xhtmlrenderer.render.FSFont;
import org.xhtmlrenderer.render.FSFontMetrics;
import org.xhtmlrenderer.render.JustificationInfo;

public class ITextTextRenderer
  implements TextRenderer
{
  private static float TEXT_MEASURING_DELTA = 0.01F;

  public void setup(FontContext context) {
  }

  public void drawString(OutputDevice outputDevice, String string, float x, float y) {
    ((ITextOutputDevice)outputDevice).drawString(string, x, y, null);
  }

  public void drawString(OutputDevice outputDevice, String string, float x, float y, JustificationInfo info)
  {
    ((ITextOutputDevice)outputDevice).drawString(string, x, y, info);
  }

  public FSFontMetrics getFSFontMetrics(FontContext context, FSFont font, String string) {
    ITextFontResolver.FontDescription descr = ((ITextFSFont)font).getFontDescription();
    BaseFont bf = descr.getFont();
    float size = font.getSize2D();
    ITextFSFontMetrics result = new ITextFSFontMetrics();
    result.setAscent(bf.getFontDescriptor(8, size));
    result.setDescent(-bf.getFontDescriptor(6, size));

    result.setStrikethroughOffset(-descr.getYStrikeoutPosition() / 1000.0F * size);
    if (descr.getYStrikeoutSize() != 0.0F)
      result.setStrikethroughThickness(descr.getYStrikeoutSize() / 1000.0F * size);
    else {
      result.setStrikethroughThickness(size / 12.0F);
    }

    result.setUnderlineOffset(-descr.getUnderlinePosition() / 1000.0F * size);
    result.setUnderlineThickness(descr.getUnderlineThickness() / 1000.0F * size);

    return result;
  }

  public int getWidth(FontContext context, FSFont font, String string) {
    BaseFont bf = ((ITextFSFont)font).getFontDescription().getFont();
    float result = bf.getWidthPoint(string, font.getSize2D());
    if (result - Math.floor(result) < TEXT_MEASURING_DELTA) {
      return (int)result;
    }
    return (int)Math.ceil(result);
  }

  public void setFontScale(float scale)
  {
  }

  public float getFontScale() {
    return 1.0F;
  }

  public void setSmoothingThreshold(float fontsize) {
  }

  public int getSmoothingLevel() {
    return 0;
  }

  public void setSmoothingLevel(int level) {
  }

  public Rectangle getGlyphBounds(OutputDevice outputDevice, FSFont font, FSGlyphVector fsGlyphVector, int index, float x, float y) {
    throw new UnsupportedOperationException();
  }

  public float[] getGlyphPositions(OutputDevice outputDevice, FSFont font, FSGlyphVector fsGlyphVector) {
    throw new UnsupportedOperationException();
  }

  public FSGlyphVector getGlyphVector(OutputDevice outputDevice, FSFont font, String string) {
    throw new UnsupportedOperationException();
  }

  public void drawGlyphVector(OutputDevice outputDevice, FSGlyphVector vector, float x, float y) {
    throw new UnsupportedOperationException();
  }
}