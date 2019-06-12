package com.icfcc.credit.platform.IText;

import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfAppearance;
import com.lowagie.text.pdf.PdfBorderDictionary;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import org.xhtmlrenderer.css.parser.FSColor;
import org.xhtmlrenderer.css.style.CalculatedStyle;
import org.xhtmlrenderer.layout.Layer;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.Box;
import org.xhtmlrenderer.render.PageBox;
import org.xhtmlrenderer.render.RenderingContext;

public class RadioButtonFormField extends AbstractFormField
{
  private static final String FIELD_TYPE = "RadioButton";
  private ITextReplacedElementFactory _factory;
  private Box _box;

  protected String getFieldType()
  {
    return "RadioButton";
  }

  public RadioButtonFormField(ITextReplacedElementFactory factory, LayoutContext c, BlockBox box, int cssWidth, int cssHeight)
  {
    this._factory = factory;
    this._box = box;

    initDimensions(c, box, cssWidth, cssHeight);
  }

  public void paint(RenderingContext c, ITextOutputDevice outputDevice, BlockBox box) {
    String fieldName = getFieldName(outputDevice, box.getElement());
    List radioBoxes = this._factory.getRadioButtons(fieldName);

    if (radioBoxes == null)
    {
      return;
    }

    PdfContentByte cb = outputDevice.getCurrentPage();
    PdfWriter writer = outputDevice.getWriter();

    PdfFormField group = PdfFormField.createRadioButton(writer, true);
    group.setFieldName(fieldName);

    RadioButtonFormField checked = getChecked(radioBoxes);
    if (checked != null) {
      group.setValueAsString(getValue(checked.getBox().getElement()));
    }

    for (Iterator i = radioBoxes.iterator(); i.hasNext(); ) {
      RadioButtonFormField fieldElem = (RadioButtonFormField)i.next();

      createField(c, outputDevice, cb, writer, group, fieldElem, checked);
    }

    writer.addAnnotation(group);

    this._factory.remove(fieldName);
  }

  private RadioButtonFormField getChecked(List fields) {
    RadioButtonFormField result = null;
    for (Iterator i = fields.iterator(); i.hasNext(); ) {
      RadioButtonFormField f = (RadioButtonFormField)i.next();
      if (isChecked(f.getBox().getElement())) {
        result = f;
      }
    }

    return result;
  }

  private void createField(RenderingContext c, ITextOutputDevice outputDevice, PdfContentByte cb, PdfWriter writer, PdfFormField group, RadioButtonFormField fieldElem, RadioButtonFormField checked)
  {
    Box box = fieldElem.getBox();

    Element e = box.getElement();
    String onValue = getValue(e);

    float width = outputDevice.getDeviceLength(fieldElem.getWidth());
    float height = outputDevice.getDeviceLength(fieldElem.getHeight());

    PdfFormField field = PdfFormField.createEmpty(writer);

    FSColor color = box.getStyle().getColor();
    FSColor darker = box.getEffBackgroundColor(c).darkenColor();
    createAppearances(cb, field, onValue, width, height, true, color, darker);
    createAppearances(cb, field, onValue, width, height, false, color, darker);

    field.setWidget(outputDevice.createTargetArea(c, box), PdfAnnotation.HIGHLIGHT_INVERT);

    Rectangle bounds = box.getContentAreaEdge(box.getAbsX(), box.getAbsY(), c);
    PageBox page = c.getRootLayer().getPage(c, bounds.y);
    field.setPlaceInPage(page.getPageNo() + 1);

    field.setBorderStyle(new PdfBorderDictionary(0.0F, 0));

    field.setAppearanceState(fieldElem == checked ? onValue : "Off");

    if (isReadOnly(e)) {
      field.setFieldFlags(1);
    }

    group.addKid(field);
  }

  private void createAppearances(PdfContentByte cb, PdfFormField field, String onValue, float width, float height, boolean normal, FSColor color, FSColor darker)
  {
    PdfAppearance tpOff = cb.createAppearance(width, height);
    PdfAppearance tpOn = cb.createAppearance(width, height);

    float diameter = Math.min(width, height);

    setStrokeColor(tpOff, color);
    setStrokeColor(tpOn, color);

    if (!normal) {
      setStrokeColor(tpOff, darker);
      setStrokeColor(tpOn, darker);
    }

    float strokeWidth = Math.max(1.0F, reduce(diameter));

    tpOff.setLineWidth(strokeWidth);
    tpOn.setLineWidth(strokeWidth);

    tpOff.circle(width / 2.0F, height / 2.0F, diameter / 2.0F - strokeWidth / 2.0F);
    tpOn.circle(width / 2.0F, height / 2.0F, diameter / 2.0F - strokeWidth / 2.0F);

    if (!normal) {
      tpOff.fillStroke();
      tpOn.fillStroke();
    } else {
      tpOff.stroke();
      tpOn.stroke();
    }

    setFillColor(tpOn, color);
    if (!normal)
      tpOn.circle(width / 2.0F, height / 2.0F, diameter * 0.23F);
    else {
      tpOn.circle(width / 2.0F, height / 2.0F, diameter * 0.2F);
    }
    tpOn.fill();

    if (normal) {
      field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", tpOff);
      field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, onValue, tpOn);
    } else {
      field.setAppearance(PdfAnnotation.APPEARANCE_DOWN, "Off", tpOff);
      field.setAppearance(PdfAnnotation.APPEARANCE_DOWN, onValue, tpOn);
    }
  }

  private float reduce(float value) {
    return Math.min(value, Math.max(1.0F, 0.05F * value));
  }

  public void detach(LayoutContext c) {
    super.detach(c);

    this._factory.remove(this._box.getElement());
  }

  public Box getBox() {
    return this._box;
  }

  public int getBaseline() {
    return 0;
  }

  public boolean hasBaseline() {
    return false;
  }
}