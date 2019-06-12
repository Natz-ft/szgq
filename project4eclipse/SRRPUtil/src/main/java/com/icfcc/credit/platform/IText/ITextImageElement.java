package com.icfcc.credit.platform.IText;

import java.awt.Point;
import java.awt.Rectangle;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.RenderingContext;

public class ITextImageElement
  implements ITextReplacedElement
{
  private FSImage _image;
  private Point _location = new Point(0, 0);

  public ITextImageElement(FSImage image) {
    this._image = image;
  }

  public int getIntrinsicWidth() {
    return this._image.getWidth();
  }

  public int getIntrinsicHeight() {
    return this._image.getHeight();
  }

  public Point getLocation() {
    return this._location;
  }

  public void setLocation(int x, int y) {
    this._location = new Point(x, y);
  }

  public FSImage getImage() {
    return this._image;
  }

  public void detach(LayoutContext c)
  {
  }

  public boolean isRequiresInteractivePaint() {
    return false;
  }

  public void paint(RenderingContext c, ITextOutputDevice outputDevice, BlockBox box)
  {
    Rectangle contentBounds = box.getContentAreaEdge(box.getAbsX(), box.getAbsY(), c);
    ReplacedElement element = box.getReplacedElement();
    outputDevice.drawImage(((ITextImageElement)element).getImage(), contentBounds.x, contentBounds.y);
  }

  public int getBaseline()
  {
    return 0;
  }

  public boolean hasBaseline() {
    return false;
  }
}