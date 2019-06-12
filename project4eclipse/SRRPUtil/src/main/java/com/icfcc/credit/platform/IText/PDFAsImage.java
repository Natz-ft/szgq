package com.icfcc.credit.platform.IText;

import java.net.URL;
import org.xhtmlrenderer.extend.FSImage;

public class PDFAsImage
  implements FSImage
{
  private URL _source;
  private float _width;
  private float _height;
  private float _unscaledWidth;
  private float _unscaledHeight;

  public PDFAsImage(URL source)
  {
    this._source = source;
  }

  public int getWidth() {
    return (int)this._width;
  }

  public int getHeight() {
    return (int)this._height;
  }

  public void scale(int width, int height) {
    float targetWidth = width;
    float targetHeight = height;

    if (width == -1) {
      targetWidth = getWidthAsFloat() * (targetHeight / getHeight());
    }

    if (height == -1) {
      targetHeight = getHeightAsFloat() * (targetWidth / getWidth());
    }

    this._width = targetWidth;
    this._height = targetHeight;
  }

  public URL getURL() {
    return this._source;
  }

  public void setInitialWidth(float width) {
    if (this._width == 0.0F) {
      this._width = width;
      this._unscaledWidth = width;
    }
  }

  public void setInitialHeight(float height) {
    if (this._height == 0.0F) {
      this._height = height;
      this._unscaledHeight = height;
    }
  }

  public float getWidthAsFloat() {
    return this._width;
  }

  public float getHeightAsFloat() {
    return this._height;
  }

  public float getUnscaledHeight() {
    return this._unscaledHeight;
  }

  public void setUnscaledHeight(float unscaledHeight) {
    this._unscaledHeight = unscaledHeight;
  }

  public float getUnscaledWidth() {
    return this._unscaledWidth;
  }

  public void setUnscaledWidth(float unscaledWidth) {
    this._unscaledWidth = unscaledWidth;
  }

  public float scaleHeight() {
    return this._height / this._unscaledHeight;
  }

  public float scaleWidth() {
    return this._width / this._unscaledWidth;
  }
}