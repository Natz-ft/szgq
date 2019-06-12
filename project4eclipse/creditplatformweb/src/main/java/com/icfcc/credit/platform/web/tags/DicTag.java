package com.icfcc.credit.platform.web.tags;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.icfcc.credit.platform.service.DicWord;

public class DicTag extends SimpleTagSupport {

	private static final long serialVersionUID = 2145115389632724782L;

	private static Logger log = LoggerFactory.getLogger(DicTag.class);

	private String dicType = "";
	private String url="";
	private StringWriter sw = new StringWriter();

	public String getDicType() {
		return dicType;
	}

	public void setDicType(String dicType) {
		this.dicType = dicType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void doTag() throws JspException, IOException {
		Map<String,String> res=null;
		StringBuffer buffer=new StringBuffer();
		if (dicType != null && !"".equals(dicType)) {
			/* 从属性中使用消息 */
			JspWriter out = getJspContext().getOut();
			DicWord dw=DicWord.getDicWord();
			res=dw.getDicByType(dicType);
			for(String key:res.keySet()){
				buffer.append(url).append(":").append(key).append(",");
				
			}
			
			out.println(buffer.toString());
		} else {
			/* 从内容体中使用消息 */
			getJspBody().invoke(sw);
			getJspContext().getOut().println(sw.toString());
		}
	}

}
