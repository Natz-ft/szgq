package com.icfcc.credit.platform.IText;

import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.resource.ImageResource;
import org.xhtmlrenderer.swing.NaiveUserAgent;
import org.xhtmlrenderer.util.XRLog;

public class ITextUserAgent extends NaiveUserAgent
{
  private static final int IMAGE_CACHE_CAPACITY = 32;
  private SharedContext _sharedContext;
  private final ITextOutputDevice _outputDevice;

  public ITextUserAgent(ITextOutputDevice outputDevice)
  {
    super(32);
    this._outputDevice = outputDevice;
  }

  private byte[] readStream(InputStream is) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream(is.available());
    byte[] buf = new byte[10240];
    int i;
    while ((i = is.read(buf)) != -1) {
      out.write(buf, 0, i);
    }
    out.close();
    return out.toByteArray();
  }

  public ImageResource getImageResource(String uri) {
    ImageResource resource = null;
    uri = resolveURI(uri);
    resource = (ImageResource)this._imageCache.get(uri);
    if (resource == null) {
      InputStream is = resolveAndOpenStream(uri);
      if (is != null) {
        try {
          URL url = new URL(uri);
          if ((url.getPath() != null) && (url.getPath().toLowerCase().endsWith(".pdf")))
          {
            PdfReader reader = this._outputDevice.getReader(url);
            PDFAsImage image = new PDFAsImage(url);
            Rectangle rect = reader.getPageSizeWithRotation(1);
            image.setInitialWidth(rect.getWidth() * this._outputDevice.getDotsPerPoint());
            image.setInitialHeight(rect.getHeight() * this._outputDevice.getDotsPerPoint());
            resource = new ImageResource(uri, image);
          } else {
            Image image = Image.getInstance(readStream(is));
            scaleToOutputResolution(image);
            resource = new ImageResource(uri, new ITextFSImage(image));
          }
          this._imageCache.put(uri, resource);
        } catch (Exception e) {
          XRLog.exception("Can't read image file; unexpected problem for URI '" + uri + "'", e);
        } finally {
          try {
            is.close();
          }
          catch (IOException e)
          {
          }
        }
      }
    }
    if (resource != null)
      resource = new ImageResource(resource.getImageUri(), (FSImage)((ITextFSImage)resource.getImage()).clone());
    else {
      resource = new ImageResource(uri, null);
    }

    return resource;
  }

  private void scaleToOutputResolution(Image image) {
    float factor = this._sharedContext.getDotsPerPixel();
    image.scaleAbsolute(image.getPlainWidth() * factor, image.getPlainHeight() * factor);
  }

  public SharedContext getSharedContext() {
    return this._sharedContext;
  }

  public void setSharedContext(SharedContext sharedContext) {
    this._sharedContext = sharedContext;
  }
}