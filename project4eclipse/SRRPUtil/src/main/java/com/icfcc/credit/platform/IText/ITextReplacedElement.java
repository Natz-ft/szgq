package com.icfcc.credit.platform.IText;

import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.RenderingContext;

public abstract interface ITextReplacedElement extends ReplacedElement
{
  public abstract void paint(RenderingContext paramRenderingContext, ITextOutputDevice paramITextOutputDevice, BlockBox paramBlockBox);
}
