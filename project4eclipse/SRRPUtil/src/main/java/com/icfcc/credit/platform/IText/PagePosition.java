package com.icfcc.credit.platform.IText;

public class PagePosition
{
  private String _id;
  private int _pageNo;
  private float _x;
  private float _width;
  private float _y;
  private float _height;

  public int getPageNo()
  {
    return this._pageNo;
  }

  public void setPageNo(int pageNo) {
    this._pageNo = pageNo;
  }

  public float getX() {
    return this._x;
  }

  public void setX(float x) {
    this._x = x;
  }

  public float getWidth() {
    return this._width;
  }

  public void setWidth(float width) {
    this._width = width;
  }

  public float getY() {
    return this._y;
  }

  public void setY(float y) {
    this._y = y;
  }

  public float getHeight() {
    return this._height;
  }

  public void setHeight(float height) {
    this._height = height;
  }

  public String getId() {
    return this._id;
  }

  public void setId(String id) {
    this._id = id;
  }
}