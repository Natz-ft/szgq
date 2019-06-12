package com.icfcc.credit.platform.IText;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xhtmlrenderer.css.constants.CSSName;
import org.xhtmlrenderer.css.constants.IdentValue;
import org.xhtmlrenderer.css.sheet.FontFaceRule;
import org.xhtmlrenderer.css.style.CalculatedStyle;
import org.xhtmlrenderer.css.style.FSDerivedValue;
import org.xhtmlrenderer.css.value.FontSpecification;
import org.xhtmlrenderer.extend.FontResolver;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.render.FSFont;
import org.xhtmlrenderer.util.XRLog;
import org.xhtmlrenderer.util.XRRuntimeException;

public class ITextFontResolver
  implements FontResolver
{
  private Map _fontFamilies = createInitialFontMap();
  private Map _fontCache = new HashMap();
  private final SharedContext _sharedContext;
  private static final String[][] cjkFonts = { { "STSong-Light-H", "STSong-Light", "UniGB-UCS2-H" }, { "STSong-Light-V", "STSong-Light", "UniGB-UCS2-V" }, { "STSongStd-Light-H", "STSongStd-Light", "UniGB-UCS2-H" }, { "STSongStd-Light-V", "STSongStd-Light", "UniGB-UCS2-V" }, { "MHei-Medium-H", "MHei-Medium", "UniCNS-UCS2-H" }, { "MHei-Medium-V", "MHei-Medium", "UniCNS-UCS2-V" }, { "MSung-Light-H", "MSung-Light", "UniCNS-UCS2-H" }, { "MSung-Light-V", "MSung-Light", "UniCNS-UCS2-V" }, { "MSungStd-Light-H", "MSungStd-Light", "UniCNS-UCS2-H" }, { "MSungStd-Light-V", "MSungStd-Light", "UniCNS-UCS2-V" }, { "HeiseiMin-W3-H", "HeiseiMin-W3", "UniJIS-UCS2-H" }, { "HeiseiMin-W3-V", "HeiseiMin-W3", "UniJIS-UCS2-V" }, { "HeiseiKakuGo-W5-H", "HeiseiKakuGo-W5", "UniJIS-UCS2-H" }, { "HeiseiKakuGo-W5-V", "HeiseiKakuGo-W5", "UniJIS-UCS2-V" }, { "KozMinPro-Regular-H", "KozMinPro-Regular", "UniJIS-UCS2-HW-H" }, { "KozMinPro-Regular-V", "KozMinPro-Regular", "UniJIS-UCS2-HW-V" }, { "HYGoThic-Medium-H", "HYGoThic-Medium", "UniKS-UCS2-H" }, { "HYGoThic-Medium-V", "HYGoThic-Medium", "UniKS-UCS2-V" }, { "HYSMyeongJo-Medium-H", "HYSMyeongJo-Medium", "UniKS-UCS2-H" }, { "HYSMyeongJo-Medium-V", "HYSMyeongJo-Medium", "UniKS-UCS2-V" }, { "HYSMyeongJoStd-Medium-H", "HYSMyeongJoStd-Medium", "UniKS-UCS2-H" }, { "HYSMyeongJoStd-Medium-V", "HYSMyeongJoStd-Medium", "UniKS-UCS2-V" } };

  public ITextFontResolver(SharedContext sharedContext)
  {
    this._sharedContext = sharedContext;
  }

  public static Set getDistinctFontFamilyNames(String path, String encoding, boolean embedded)
  {
    BaseFont font = null;
    try {
      font = BaseFont.createFont(path, encoding, embedded);
      String[] fontFamilyNames = TrueTypeUtil.getFamilyNames(font);
      Set distinct = new HashSet();
      for (int i = 0; i < fontFamilyNames.length; i++) {
        distinct.add(fontFamilyNames[i]);
      }
      return distinct;
    } catch (DocumentException e) {
      throw new RuntimeException(e); } catch (IOException e) {
    	    throw new RuntimeException(e);
    }
  }

  public FSFont resolveFont(SharedContext renderingContext, FontSpecification spec)
  {
    return resolveFont(renderingContext, spec.families, spec.size, spec.fontWeight, spec.fontStyle, spec.variant);
  }

  public void flushCache() {
    this._fontFamilies = createInitialFontMap();
    this._fontCache = new HashMap();
  }

  public void flushFontFaceFonts() {
    this._fontCache = new HashMap();

    for (Iterator i = this._fontFamilies.values().iterator(); i.hasNext(); ) {
      FontFamily family = (FontFamily)i.next();
      for (Iterator j = family.getFontDescriptions().iterator(); j.hasNext(); ) {
        FontDescription d = (FontDescription)j.next();
        if (d.isFromFontFace()) {
          j.remove();
        }
      }
      if (family.getFontDescriptions().size() == 0)
        i.remove();
    }
  }

  public void importFontFaces(List fontFaces)
  {
    for (Iterator i = fontFaces.iterator(); i.hasNext(); ) {
      FontFaceRule rule = (FontFaceRule)i.next();
      CalculatedStyle style = rule.getCalculatedStyle();

      FSDerivedValue src = style.valueByName(CSSName.SRC);
      if (src == IdentValue.NONE)
      {
        continue;
      }
      byte[] font1 = this._sharedContext.getUac().getBinaryResource(src.asString());
      if (font1 == null) {
        XRLog.exception("Could not load font " + src.asString());
        continue;
      }

      byte[] font2 = null;
      FSDerivedValue metricsSrc = style.valueByName(CSSName.FS_FONT_METRIC_SRC);
      if (metricsSrc != IdentValue.NONE) {
        font2 = this._sharedContext.getUac().getBinaryResource(metricsSrc.asString());
        if (font2 == null) {
          XRLog.exception("Could not load font metric data " + src.asString());
          continue;
        }
      }

      if (font2 != null) {
        byte[] t = font1;
        font1 = font2;
        font2 = t;
      }

      boolean embedded = style.isIdent(CSSName.FS_PDF_FONT_EMBED, IdentValue.EMBED);

      String encoding = style.getStringProperty(CSSName.FS_PDF_FONT_ENCODING);

      String fontFamily = null;
      if (rule.hasFontFamily())
        fontFamily = style.valueByName(CSSName.FONT_FAMILY).asString();
      try
      {
        addFontFaceFont(fontFamily, src.asString(), encoding, embedded, font1, font2);
      } catch (DocumentException e) {
        XRLog.exception("Could not load font " + src.asString(), e);
        continue;
      } catch (IOException e) {
        XRLog.exception("Could not load font " + src.asString(), e);
      }
    }
  }

  public void addFontDirectory(String dir, boolean embedded) throws DocumentException, IOException
  {
    File f = new File(dir);
    if (f.isDirectory()) {
      File[] files = f.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
          String lower = name.toLowerCase();
          return (lower.endsWith(".otf")) || (lower.endsWith(".ttf"));
        }
      });
      for (int i = 0; i < files.length; i++)
        addFont(files[i].getAbsolutePath(), embedded);
    }
  }

  public void addFont(String path, boolean embedded)
    throws DocumentException, IOException
  {
    addFont(path, "Cp1252", embedded);
  }

  public void addFont(String path, String encoding, boolean embedded) throws DocumentException, IOException
  {
    addFont(path, encoding, embedded, null);
  }

  public void addFont(String path, String encoding, boolean embedded, String pathToPFB) throws DocumentException, IOException
  {
    addFont(path, null, encoding, embedded, pathToPFB);
  }

  public void addFont(String path, String fontFamilyNameOverride, String encoding, boolean embedded, String pathToPFB)
    throws DocumentException, IOException
  {
    String lower = path.toLowerCase();
    if ((lower.endsWith(".otf")) || (lower.endsWith(".ttf")) || (lower.indexOf(".ttc,") != -1)) {
      BaseFont font = BaseFont.createFont(path, encoding, embedded);
      String[] fontFamilyNames;
      if (fontFamilyNameOverride != null)
        fontFamilyNames = new String[] { fontFamilyNameOverride };
      else {
        fontFamilyNames = TrueTypeUtil.getFamilyNames(font);
      }

      for (int i = 0; i < fontFamilyNames.length; i++) {
        String fontFamilyName = fontFamilyNames[i];
        FontFamily fontFamily = getFontFamily(fontFamilyName);

        FontDescription descr = new FontDescription(font);
        try {
          TrueTypeUtil.populateDescription(path, font, descr);
        } catch (Exception e) {
          throw new XRRuntimeException(e.getMessage(), e);
        }

        fontFamily.addFontDescription(descr);
      }
    } else if (lower.endsWith(".ttc")) {
      String[] names = BaseFont.enumerateTTCNames(path);
      for (int i = 0; i < names.length; i++)
        addFont(path + "," + i, fontFamilyNameOverride, encoding, embedded, null);
    }
    else if ((lower.endsWith(".afm")) || (lower.endsWith(".pfm"))) {
      if ((embedded) && (pathToPFB == null)) {
        throw new IOException("When embedding a font, path to PFB/PFA file must be specified");
      }

      BaseFont font = BaseFont.createFont(path, encoding, embedded, false, null, readFile(pathToPFB));
      String fontFamilyName;
      if (fontFamilyNameOverride != null)
        fontFamilyName = fontFamilyNameOverride;
      else {
        fontFamilyName = font.getFamilyFontName()[0][3];
      }

      FontFamily fontFamily = getFontFamily(fontFamilyName);

      FontDescription descr = new FontDescription(font);

      fontFamily.addFontDescription(descr);
    } else {
      throw new IOException("Unsupported font type");
    }
  }

  private void addFontFaceFont(String fontFamilyNameOverride, String uri, String encoding, boolean embedded, byte[] afmttf, byte[] pfb)
    throws DocumentException, IOException
  {
    String lower = uri.toLowerCase();
    if ((lower.endsWith(".otf")) || (lower.endsWith(".ttf")) || (lower.indexOf(".ttc,") != -1)) {
      BaseFont font = BaseFont.createFont(uri, encoding, embedded, false, afmttf, pfb);
      String[] fontFamilyNames;
      if (fontFamilyNameOverride != null)
        fontFamilyNames = new String[] { fontFamilyNameOverride };
      else {
        fontFamilyNames = TrueTypeUtil.getFamilyNames(font);
      }

      for (int i = 0; i < fontFamilyNames.length; i++) {
        FontFamily fontFamily = getFontFamily(fontFamilyNames[i]);

        FontDescription descr = new FontDescription(font);
        try {
          TrueTypeUtil.populateDescription(uri, afmttf, font, descr);
        } catch (Exception e) {
          throw new XRRuntimeException(e.getMessage(), e);
        }

        descr.setFromFontFace(true);

        fontFamily.addFontDescription(descr);
      }
    } else if ((lower.endsWith(".afm")) || (lower.endsWith(".pfm")) || (lower.endsWith(".pfb")) || (lower.endsWith(".pfa"))) {
      if ((embedded) && (pfb == null)) {
        throw new IOException("When embedding a font, path to PFB/PFA file must be specified");
      }

      String name = uri.substring(0, uri.length() - 4) + ".afm";
      BaseFont font = BaseFont.createFont(name, encoding, embedded, false, afmttf, pfb);

      String fontFamilyName = font.getFamilyFontName()[0][3];
      FontFamily fontFamily = getFontFamily(fontFamilyName);

      FontDescription descr = new FontDescription(font);
      descr.setFromFontFace(true);

      fontFamily.addFontDescription(descr);
    } else {
      throw new IOException("Unsupported font type");
    }
  }

  private byte[] readFile(String path) throws IOException {
    File f = new File(path);
    if (f.exists()) {
      ByteArrayOutputStream result = new ByteArrayOutputStream((int)f.length());
      InputStream is = null;
      try {
        is = new FileInputStream(path);
        byte[] buf = new byte[10240];
        int i;
        while ((i = is.read(buf)) != -1) {
          result.write(buf, 0, i);
        }
        is.close();
        is = null;

        byte[] arrayOfByte1 = result.toByteArray();
        return arrayOfByte1;
      }
      finally
      {
        if (is != null)
          try {
            is.close();
          }
          catch (IOException e)
          {
          }
      }
    }
    throw new IOException("File " + path + " does not exist or is not accessible");
  }

  public FontFamily getFontFamily(String fontFamilyName)
  {
    FontFamily fontFamily = (FontFamily)this._fontFamilies.get(fontFamilyName);
    if (fontFamily == null) {
      fontFamily = new FontFamily();
      fontFamily.setName(fontFamilyName);
      this._fontFamilies.put(fontFamilyName, fontFamily);
    }
    return fontFamily;
  }

  private FSFont resolveFont(SharedContext ctx, String[] families, float size, IdentValue weight, IdentValue style, IdentValue variant) {
    if ((style != IdentValue.NORMAL) && (style != IdentValue.OBLIQUE) && (style != IdentValue.ITALIC))
    {
      style = IdentValue.NORMAL;
    }
    if (families != null) {
      for (int i = 0; i < families.length; i++) {
        FSFont font = resolveFont(ctx, families[i], size, weight, style, variant);
        if (font != null) {
          return font;
        }
      }
    }

    return resolveFont(ctx, "Serif", size, weight, style, variant);
  }

  private String normalizeFontFamily(String fontFamily) {
    String result = fontFamily;

    if (result.startsWith("\"")) {
      result = result.substring(1);
    }
    if (result.endsWith("\"")) {
      result = result.substring(0, result.length() - 1);
    }

    if (result.equalsIgnoreCase("serif")) {
      result = "Serif";
    }
    else if (result.equalsIgnoreCase("sans-serif")) {
      result = "SansSerif";
    }
    else if (result.equalsIgnoreCase("monospace")) {
      result = "Monospaced";
    }

    return result;
  }

  private FSFont resolveFont(SharedContext ctx, String fontFamily, float size, IdentValue weight, IdentValue style, IdentValue variant) {
    String normalizedFontFamily = normalizeFontFamily(fontFamily);

    String cacheKey = getHashName(normalizedFontFamily, weight, style);
    FontDescription result = (FontDescription)this._fontCache.get(cacheKey);
    if (result != null) {
      return new ITextFSFont(result, size);
    }

    FontFamily family = (FontFamily)this._fontFamilies.get(normalizedFontFamily);
    if (family != null) {
      result = family.match(convertWeightToInt(weight), style);
      if (result != null) {
        this._fontCache.put(cacheKey, result);
        return new ITextFSFont(result, size);
      }
    }

    return null;
  }

  public static int convertWeightToInt(IdentValue weight) {
    if (weight == IdentValue.NORMAL)
      return 400;
    if (weight == IdentValue.BOLD)
      return 700;
    if (weight == IdentValue.FONT_WEIGHT_100)
      return 100;
    if (weight == IdentValue.FONT_WEIGHT_200)
      return 200;
    if (weight == IdentValue.FONT_WEIGHT_300)
      return 300;
    if (weight == IdentValue.FONT_WEIGHT_400)
      return 400;
    if (weight == IdentValue.FONT_WEIGHT_500)
      return 500;
    if (weight == IdentValue.FONT_WEIGHT_600)
      return 600;
    if (weight == IdentValue.FONT_WEIGHT_700)
      return 700;
    if (weight == IdentValue.FONT_WEIGHT_800)
      return 800;
    if (weight == IdentValue.FONT_WEIGHT_900)
      return 900;
    if (weight == IdentValue.LIGHTER)
    {
      return 400;
    }if (weight == IdentValue.BOLDER)
    {
      return 700;
    }
    throw new IllegalArgumentException();
  }

  protected static String getHashName(String name, IdentValue weight, IdentValue style)
  {
    return name + "-" + weight + "-" + style;
  }

  private static Map createInitialFontMap() {
    HashMap result = new HashMap();
    try
    {
      addCourier(result);
      addTimes(result);
      addHelvetica(result);
      addSymbol(result);
      addZapfDingbats(result);

      if (ITextFontResolver.class.getClassLoader().getResource("com/lowagie/text/pdf/fonts/cjkfonts.properties") != null)
        addCJKFonts(result);
    }
    catch (DocumentException e) {
      throw new RuntimeException(e.getMessage(), e);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    return result;
  }

  private static BaseFont createFont(String name) throws DocumentException, IOException {
    return createFont(name, "winansi", true);
  }

  private static BaseFont createFont(String name, String encoding, boolean embedded) throws DocumentException, IOException {
    return BaseFont.createFont(name, encoding, embedded);
  }

  private static void addCourier(HashMap result) throws DocumentException, IOException {
    FontFamily courier = new FontFamily();
    courier.setName("Courier");

    courier.addFontDescription(new FontDescription(createFont("Courier-BoldOblique"), IdentValue.OBLIQUE, 700));

    courier.addFontDescription(new FontDescription(createFont("Courier-Oblique"), IdentValue.OBLIQUE, 400));

    courier.addFontDescription(new FontDescription(createFont("Courier-Bold"), IdentValue.NORMAL, 700));

    courier.addFontDescription(new FontDescription(createFont("Courier"), IdentValue.NORMAL, 400));

    result.put("DialogInput", courier);
    result.put("Monospaced", courier);
    result.put("Courier", courier);
  }

  private static void addTimes(HashMap result) throws DocumentException, IOException {
    FontFamily times = new FontFamily();
    times.setName("Times");

    times.addFontDescription(new FontDescription(createFont("Times-BoldItalic"), IdentValue.ITALIC, 700));

    times.addFontDescription(new FontDescription(createFont("Times-Italic"), IdentValue.ITALIC, 400));

    times.addFontDescription(new FontDescription(createFont("Times-Bold"), IdentValue.NORMAL, 700));

    times.addFontDescription(new FontDescription(createFont("Times-Roman"), IdentValue.NORMAL, 400));

    result.put("Serif", times);
    result.put("TimesRoman", times);
  }

  private static void addHelvetica(HashMap result) throws DocumentException, IOException {
    FontFamily helvetica = new FontFamily();
    helvetica.setName("Helvetica");

    helvetica.addFontDescription(new FontDescription(createFont("Helvetica-BoldOblique"), IdentValue.OBLIQUE, 700));

    helvetica.addFontDescription(new FontDescription(createFont("Helvetica-Oblique"), IdentValue.OBLIQUE, 400));

    helvetica.addFontDescription(new FontDescription(createFont("Helvetica-Bold"), IdentValue.NORMAL, 700));

    helvetica.addFontDescription(new FontDescription(createFont("Helvetica"), IdentValue.NORMAL, 400));

    result.put("Dialog", helvetica);
    result.put("SansSerif", helvetica);
    result.put("Helvetica", helvetica);
  }

  private static void addSymbol(Map result) throws DocumentException, IOException {
    FontFamily fontFamily = new FontFamily();
    fontFamily.setName("Symbol");

    fontFamily.addFontDescription(new FontDescription(createFont("Symbol", "Cp1252", false), IdentValue.NORMAL, 400));

    result.put("Symbol", fontFamily);
  }

  private static void addZapfDingbats(Map result) throws DocumentException, IOException {
    FontFamily fontFamily = new FontFamily();
    fontFamily.setName("ZapfDingbats");

    fontFamily.addFontDescription(new FontDescription(createFont("ZapfDingbats", "Cp1252", false), IdentValue.NORMAL, 400));

    result.put("ZapfDingbats", fontFamily);
  }

  private static void addCJKFonts(Map fontFamilyMap)
    throws DocumentException, IOException
  {
    for (int i = 0; i < cjkFonts.length; i++) {
      String fontFamilyName = cjkFonts[i][0];
      String fontName = cjkFonts[i][1];
      String encoding = cjkFonts[i][2];

      addCJKFont(fontFamilyName, fontName, encoding, fontFamilyMap);
    }
  }

  private static void addCJKFont(String fontFamilyName, String fontName, String encoding, Map fontFamilyMap) throws DocumentException, IOException {
    FontFamily fontFamily = new FontFamily();
    fontFamily.setName(fontFamilyName);

    fontFamily.addFontDescription(new FontDescription(createFont(fontName + ",BoldItalic", encoding, false), IdentValue.OBLIQUE, 700));
    fontFamily.addFontDescription(new FontDescription(createFont(fontName + ",Italic", encoding, false), IdentValue.OBLIQUE, 400));
    fontFamily.addFontDescription(new FontDescription(createFont(fontName + ",Bold", encoding, false), IdentValue.NORMAL, 700));
    fontFamily.addFontDescription(new FontDescription(createFont(fontName, encoding, false), IdentValue.NORMAL, 400));

    fontFamilyMap.put(fontFamilyName, fontFamily);
  }

  public static class FontDescription
  {
    private IdentValue _style;
    private int _weight;
    private BaseFont _font;
    private float _underlinePosition;
    private float _underlineThickness;
    private float _yStrikeoutSize;
    private float _yStrikeoutPosition;
    private boolean _isFromFontFace;

    public FontDescription()
    {
    }

    public FontDescription(BaseFont font)
    {
      this(font, IdentValue.NORMAL, 400);
    }

    public FontDescription(BaseFont font, IdentValue style, int weight) {
      this._font = font;
      this._style = style;
      this._weight = weight;
      setMetricDefaults();
    }

    public BaseFont getFont() {
      return this._font;
    }

    public void setFont(BaseFont font) {
      this._font = font;
    }

    public int getWeight() {
      return this._weight;
    }

    public void setWeight(int weight) {
      this._weight = weight;
    }

    public IdentValue getStyle() {
      return this._style;
    }

    public void setStyle(IdentValue style) {
      this._style = style;
    }

    public float getUnderlinePosition()
    {
      return this._underlinePosition;
    }

    public void setUnderlinePosition(float underlinePosition)
    {
      this._underlinePosition = underlinePosition;
    }

    public float getUnderlineThickness() {
      return this._underlineThickness;
    }

    public void setUnderlineThickness(float underlineThickness) {
      this._underlineThickness = underlineThickness;
    }

    public float getYStrikeoutPosition() {
      return this._yStrikeoutPosition;
    }

    public void setYStrikeoutPosition(float strikeoutPosition) {
      this._yStrikeoutPosition = strikeoutPosition;
    }

    public float getYStrikeoutSize() {
      return this._yStrikeoutSize;
    }

    public void setYStrikeoutSize(float strikeoutSize) {
      this._yStrikeoutSize = strikeoutSize;
    }

    private void setMetricDefaults() {
      this._underlinePosition = -50.0F;
      this._underlineThickness = 50.0F;

      int[] box = this._font.getCharBBox(120);
      if (box != null) {
        this._yStrikeoutPosition = (box[3] / 2 + 50);
        this._yStrikeoutSize = 100.0F;
      }
      else {
        this._yStrikeoutPosition = (this._font.getFontDescriptor(8, 1000.0F) / 3.0F);
      }
    }

    public boolean isFromFontFace() {
      return this._isFromFontFace;
    }

    public void setFromFontFace(boolean isFromFontFace) {
      this._isFromFontFace = isFromFontFace;
    }
  }

  private static class FontFamily
  {
    private String _name;
    private List _fontDescriptions;
    private static final int SM_EXACT = 1;
    private static final int SM_LIGHTER_OR_DARKER = 2;
    private static final int SM_DARKER_OR_LIGHTER = 3;

    public List getFontDescriptions()
    {
      return this._fontDescriptions;
    }

    public void addFontDescription(ITextFontResolver.FontDescription descr) {
      if (this._fontDescriptions == null) {
        this._fontDescriptions = new ArrayList();
      }
      this._fontDescriptions.add(descr);
      Collections.sort(this._fontDescriptions, new Comparator()
      {
        public int compare(Object o1, Object o2) {
          ITextFontResolver.FontDescription f1 = (ITextFontResolver.FontDescription)o1;
          ITextFontResolver.FontDescription f2 = (ITextFontResolver.FontDescription)o2;
          return f1.getWeight() - f2.getWeight();
        } } );
    }

    public String getName() {
      return this._name;
    }

    public void setName(String name) {
      this._name = name;
    }

    public ITextFontResolver.FontDescription match(int desiredWeight, IdentValue style) {
      if (this._fontDescriptions == null) {
        throw new RuntimeException("fontDescriptions is null");
      }

      List candidates = new ArrayList();

      for (Iterator i = this._fontDescriptions.iterator(); i.hasNext(); ) {
        ITextFontResolver.FontDescription description = (ITextFontResolver.FontDescription)i.next();

        if (description.getStyle() == style) {
          candidates.add(description);
        }
      }

      if (candidates.size() == 0) {
        if (style == IdentValue.ITALIC)
          return match(desiredWeight, IdentValue.OBLIQUE);
        if (style == IdentValue.OBLIQUE) {
          return match(desiredWeight, IdentValue.NORMAL);
        }
        candidates.addAll(this._fontDescriptions);
      }

      ITextFontResolver.FontDescription[] matches = (ITextFontResolver.FontDescription[])(ITextFontResolver.FontDescription[])candidates.toArray(new ITextFontResolver.FontDescription[candidates.size()]);

      ITextFontResolver.FontDescription result = findByWeight(matches, desiredWeight, 1);

      if (result != null) {
        return result;
      }
      if (desiredWeight <= 500) {
        return findByWeight(matches, desiredWeight, 2);
      }
      return findByWeight(matches, desiredWeight, 3);
    }

    private ITextFontResolver.FontDescription findByWeight(ITextFontResolver.FontDescription[] matches, int desiredWeight, int searchMode)
    {
      if (searchMode == 1) {
        for (int i = 0; i < matches.length; i++) {
          ITextFontResolver.FontDescription descr = matches[i];
          if (descr.getWeight() == desiredWeight) {
            return descr;
          }
        }
        return null;
      }if (searchMode == 2) {
        int offset = 0;
        ITextFontResolver.FontDescription descr = null;
        for (offset = 0; offset < matches.length; offset++) {
          descr = matches[offset];
          if (descr.getWeight() > desiredWeight)
          {
            break;
          }
        }
        if ((offset > 0) && (descr.getWeight() > desiredWeight)) {
          return matches[(offset - 1)];
        }
        return descr;
      }

      if (searchMode == 3) {
        int offset = 0;
        ITextFontResolver.FontDescription descr = null;
        for (offset = matches.length - 1; offset >= 0; offset--) {
          descr = matches[offset];
          if (descr.getWeight() < desiredWeight)
          {
            break;
          }
        }
        if ((offset != matches.length - 1) && (descr.getWeight() < desiredWeight)) {
          return matches[(offset + 1)];
        }
        return descr;
      }

      return null;
    }
  }
}