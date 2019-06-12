package com.icfcc.credit.platform.IText;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMUtil
{
  public static Element getChild(Element parent, String name)
  {
    NodeList children = parent.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node n = children.item(i);
      if (n.getNodeType() == 1) {
        Element elem = (Element)n;
        if (elem.getTagName().equals(name)) {
          return elem;
        }
      }
    }
    return null;
  }

  public static List getChildren(Element parent, String name) {
    List result = new ArrayList();
    NodeList children = parent.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node n = children.item(i);
      if (n.getNodeType() == 1) {
        Element elem = (Element)n;
        if (elem.getTagName().equals(name)) {
          result.add(elem);
        }
      }
    }
    return result.size() == 0 ? null : result;
  }

  public static String getText(Element parent)
  {
    StringBuilder sb = new StringBuilder();
    getText(parent, sb);
    return sb.toString();
  }

  public static void getText(Element parent, StringBuilder sb)
  {
    NodeList children = parent.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node n = children.item(i);
      if (n.getNodeType() == 1)
        getText((Element)n, sb);
      else if (n.getNodeType() == 3)
        sb.append(n.getNodeValue());
    }
  }
}
