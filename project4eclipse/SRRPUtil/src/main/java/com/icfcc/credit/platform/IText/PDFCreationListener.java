package com.icfcc.credit.platform.IText;

public abstract interface PDFCreationListener
{
  public abstract void preOpen(ITextRenderer paramITextRenderer);

  public abstract void preWrite(ITextRenderer paramITextRenderer, int paramInt);

  public abstract void onClose(ITextRenderer paramITextRenderer);
}