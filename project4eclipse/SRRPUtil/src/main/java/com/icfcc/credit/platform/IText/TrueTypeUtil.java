package com.icfcc.credit.platform.IText;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.xhtmlrenderer.css.constants.IdentValue;

public class TrueTypeUtil
{
  private static IdentValue guessStyle(BaseFont font)
  {
    String[][] names = font.getFullFontName();
    for (int i = 0; i < names.length; i++) {
      String[] name = names[i];
      String lower = name[3].toLowerCase();
      if (lower.indexOf("italic") != -1)
        return IdentValue.ITALIC;
      if (lower.indexOf("oblique") != -1) {
        return IdentValue.OBLIQUE;
      }
    }

    return IdentValue.NORMAL;
  }

  public static String[] getFamilyNames(BaseFont font) {
    String[][] names = font.getFamilyFontName();
    if (names.length == 1) {
      return new String[] { names[0][3] };
    }

    List result = new ArrayList();
    for (int k = 0; k < names.length; k++) {
      String[] name = names[k];
      if (((!name[0].equals("1")) || (!name[1].equals("0"))) && (!name[2].equals("1033")))
        continue;
      result.add(name[3]);
    }

    return (String[])(String[])result.toArray(new String[result.size()]);
  }

  private static Map extractTables(BaseFont font)
    throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
  {
    Class current = font.getClass();
    while (current != null) {
      if (current.getName().endsWith(".TrueTypeFont")) {
        Field f = current.getDeclaredField("tables");
        f.setAccessible(true);
        return (Map)f.get(font);
      }

      current = current.getSuperclass();
    }

    throw new NoSuchFieldException("Could not find tables field");
  }

  private static String getTTCName(String name) {
    int idx = name.toLowerCase().indexOf(".ttc,");
    if (idx < 0) {
      return name;
    }
    return name.substring(0, idx + 4);
  }

  public static void populateDescription(String path, BaseFont font, ITextFontResolver.FontDescription descr)
    throws IOException, NoSuchFieldException, IllegalAccessException, DocumentException
  {
    RandomAccessFileOrArray rf = null;
    try {
      rf = new RandomAccessFileOrArray(getTTCName(path));

      rf = populateDescription0(path, font, descr, rf);
    } finally {
      if (rf != null)
        try {
          rf.close();
        }
        catch (IOException e)
        {
        }
    }
  }

  public static void populateDescription(String path, byte[] contents, BaseFont font, ITextFontResolver.FontDescription descr) throws IOException, NoSuchFieldException, IllegalAccessException, DocumentException
  {
    RandomAccessFileOrArray rf = null;
    try {
      rf = new RandomAccessFileOrArray(contents);

      rf = populateDescription0(path, font, descr, rf);
    } finally {
      if (rf != null)
        try {
          rf.close();
        }
        catch (IOException e)
        {
        }
    }
  }

  private static RandomAccessFileOrArray populateDescription0(String path, BaseFont font, ITextFontResolver.FontDescription descr, RandomAccessFileOrArray rf)
    throws NoSuchFieldException, IllegalAccessException, DocumentException, IOException
  {
    Map tables = extractTables(font);

    descr.setStyle(guessStyle(font));

    int[] location = (int[])(int[])tables.get("OS/2");
    if (location == null) {
      throw new DocumentException("Table 'OS/2' does not exist in " + path);
    }
    rf.seek(location[0]);
    int want = 4;
    long got = rf.skip(want);
    if (got < want) {
      throw new DocumentException("Skip TT font weight, expect read " + want + " bytes, but only got " + got);
    }
    descr.setWeight(rf.readUnsignedShort());
    want = 20;
    got = rf.skip(want);
    if (got < want) {
      throw new DocumentException("Skip TT font strikeout, expect read " + want + " bytes, but only got " + got);
    }
    descr.setYStrikeoutSize(rf.readShort());
    descr.setYStrikeoutPosition(rf.readShort());

    location = (int[])(int[])tables.get("post");

    if (location != null) {
      rf.seek(location[0]);
      want = 8;
      got = rf.skip(want);
      if (got < want) {
        throw new DocumentException("Skip TT font underline, expect read " + want + " bytes, but only got " + got);
      }
      descr.setUnderlinePosition(rf.readShort());
      descr.setUnderlineThickness(rf.readShort());
    }

    rf.close();
    rf = null;
    return rf;
  }
}