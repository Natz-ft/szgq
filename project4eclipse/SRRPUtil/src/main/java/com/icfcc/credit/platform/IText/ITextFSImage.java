package com.icfcc.credit.platform.IText;

import com.lowagie.text.Image;
import org.xhtmlrenderer.extend.FSImage;

public class ITextFSImage
  implements FSImage, Cloneable
{
  private Image _image;

  public ITextFSImage(Image image)
  {
    this._image = image;
  }

  public int getWidth() {
    return (int)this._image.getPlainWidth();
  }

  public int getHeight() {
    return (int)this._image.getPlainHeight();
  }

  public void scale(int width, int height) {
    int targetWidth = width;
    int targetHeight = height;

    if (targetWidth == -1) {
      targetWidth = (int)(getWidth() * (targetHeight / getHeight()));
    }

    if (targetHeight == -1) {
      targetHeight = (int)(getHeight() * (targetWidth / getWidth()));
    }

    this._image.scaleAbsolute(targetWidth, targetHeight);
  }

  public Image getImage() {
    return this._image;
  }

  public Object clone() {
    return new ITextFSImage(Image.getInstance(this._image));
  }
}