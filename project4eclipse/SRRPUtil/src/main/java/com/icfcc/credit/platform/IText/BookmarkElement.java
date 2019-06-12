package com.icfcc.credit.platform.IText;


import java.awt.Point;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.RenderingContext;

public class BookmarkElement
  implements ITextReplacedElement
{
  private Point _location = new Point(0, 0);
  private String _anchorName;

  public int getIntrinsicWidth()
  {
    return 0;
  }

  public int getIntrinsicHeight() {
    return 0;
  }

  public Point getLocation() {
    return this._location;
  }

  public void setLocation(int x, int y) {
    this._location = new Point(x, y);
  }

  public void detach(LayoutContext c) {
    c.removeBoxId(getAnchorName());
  }

  public String getAnchorName() {
    return this._anchorName;
  }

  public void setAnchorName(String anchorName) {
    this._anchorName = anchorName;
  }

  public boolean isRequiresInteractivePaint()
  {
    return false;
  }

  public void paint(RenderingContext c, ITextOutputDevice outputDevice, BlockBox box) {
  }

  public int getBaseline() {
    return 0;
  }

  public boolean hasBaseline() {
    return false;
  }
}