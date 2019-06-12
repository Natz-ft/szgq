package com.icfcc.credit.platform.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;  
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;  
import freemarker.core.ParseException;  
import freemarker.template.MalformedTemplateNameException;  
import freemarker.template.TemplateException;  
import freemarker.template.TemplateNotFoundException;  
   
/** 
 * PDF生成工具类 
 * @author Goofy <a href="http://www.xdemo.org">http://www.xdemo.org</a> 
 * 
 */  
public class PdfUtils {  
	@Value("${templatesName}")
	public String templatesName;
	
	@Value("${ftlName}")
	public String ftlName;
    public static void main(String[] args) {  
        try {  
//        	convert2Html("C:/Users/huguo/Desktop/templates.docx", "E://", "123.html");
        	Map<Object, Object> o=new HashMap<Object, Object>();  
            //存入一个集合  
            List<String> list = new ArrayList<String>();  
            list.add("小明");  
            list.add("张三");  
            list.add("李四");  
            o.put("name", "胡国涛");  
            o.put("code", "1");
            o.put("checkedResource", "2");  
            o.put("name", "胡国涛");  
            o.put("nameList", list);  
    		System.out.println("开始生成================");
            //存入一个集合  
            String path=PdfHelper.getPath();  
               System.out.println(path);
            generateToFile(path+"com/icfcc", "investwarrant_templates.ftl","", o, "E:/temp"+(int)(Math.random()*100000)+".pdf");  
               
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
   
    }  
       
    /** 
     * 生成PDF到文件 
     * @param ftlPath 模板文件路径（不含文件名） 
     * @param ftlName 模板文件吗（不含路径） 
     * @param imageDiskPath 图片的磁盘路径 
     * @param data 数据 
     * @param outputFile 目标文件（全路径名称） 
     * @throws Exception 
     */  
    public static void generateToFile(String ftlPath,String ftlName,String imageDiskPath,Object data,String outputFile) throws Exception {  
    	String path="F:/workspace/SRRPBusinesWeb/model/"; 
    	System.out.println("path===================="+path);
    	ftlPath=path;
        String html=PdfHelper.getPdfContent(ftlPath, ftlName, data);  
        OutputStream out = null;  
//        FileOutputStream out = new ByteArrayOutputStream();  
        ITextRenderer render = null;  
        out = new FileOutputStream(outputFile);  
        render = PdfHelper.getRender();  
        render.setDocumentFromString(html);  
        if(imageDiskPath!=null&&!imageDiskPath.equals("")){  
            //html中如果有图片，图片的路径则使用这里设置的路径的相对路径，这个是作为根路径  
            render.getSharedContext().setBaseURL("file:/"+imageDiskPath);  
        }  
        render.layout();  
        render.createPDF(out);  
        render.finishPDF();  
        render = null;  
        out.close();  
    }  
    public static ByteArrayOutputStream createNewPDF(String ftlPath,String ftlName,Object data) throws Exception {  
        String html=PdfHelper.getPdfContent(ftlPath, ftlName, data);  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        com.icfcc.credit.platform.IText.ITextRenderer render = null;  
        String path=StringUtils.substringBefore(ftlPath, "WEB-INF");
        path=path+"static/script/";
        System.out.println("path========="+path);
        render = PdfHelper.getNewRender(path);  
        render.setDocumentFromString(html);  
        render.layout();  
        render.createPDF(out);  
        render.finishPDF();  
        render = null;  
        out.close();  
        return out; 
    }    
    public static ByteArrayOutputStream createPDF(String ftlPath,String ftlName,Object data) throws Exception {  
        String html=PdfHelper.getPdfContent(ftlPath, ftlName, data);  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        ITextRenderer render = null;  
        String path=StringUtils.substringBefore(ftlPath, "WEB-INF");
        path=path+"static/script/";
        System.out.println("path========="+path);
        render = PdfHelper.getRender(path);  
        render.setDocumentFromString(html);  
        render.layout();  
        render.createPDF(out);  
        render.finishPDF();  
        render = null;  
        out.close();  
        return out; 
    }    
    
    /** 
     * 生成PDF到输出流中（ServletOutputStream用于下载PDF） 
     * @param ftlPath ftl模板文件的路径（不含文件名） 
     * @param ftlName ftl模板文件的名称（不含路径） 
     * @param imageDiskPath 如果PDF中要求图片，那么需要传入图片所在位置的磁盘路径 
     * @param data 输入到FTL中的数据 
     * @param response HttpServletResponse 
     * @return 
     * @throws TemplateNotFoundException 
     * @throws MalformedTemplateNameException 
     * @throws ParseException 
     * @throws IOException 
     * @throws TemplateException 
     * @throws DocumentException 
     */  
    public static OutputStream generateToServletOutputStream(String ftlPath,String ftlName,String imageDiskPath,Object data,HttpServletResponse response) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException, DocumentException{  
        String html=PdfHelper.getPdfContent(ftlPath, ftlName, data);  
        OutputStream out = null;  
        ITextRenderer render = null;  
        out = response.getOutputStream();  
        render = PdfHelper.getRender();  
        render.setDocumentFromString(html);  
        if(imageDiskPath!=null&&!imageDiskPath.equals("")){  
            //html中如果有图片，图片的路径则使用这里设置的路径的相对路径，这个是作为根路径  
            render.getSharedContext().setBaseURL("file:/"+imageDiskPath);  
        }  
        render.layout();  
        render.createPDF(out);  
        render.finishPDF();  
        render = null;  
        return out;  
    }  
    /**  
    * 客户端下载pdf  
    * @author  fengruiqi  
    * 创建时间 2017年2月4日 下午7:05:01  
    * @param response  
    * @param bytes  
    * @param filename  
    */  
   public static void renderPdf(HttpServletResponse response, final byte[] bytes, final String filename) {    
       setFileDownloadHeader(response, filename, ".pdf");    
       if (null != bytes) {    
           try {    
               response.getOutputStream().write(bytes);    
               response.getOutputStream().flush();    
           } catch (IOException e) {    
               throw new IllegalArgumentException(e);    
           }    
       }    
   }    
     
  
     
   /**  
    *  设置让浏览器弹出下载对话框的Header  
    * @author  fengruiqi  
    * 创建时间 2017年2月4日 下午6:58:44  
    * @param response  
    * @param fileName  
    * @param fileType  
    */  
   public static void setFileDownloadHeader(HttpServletResponse response, String fileName, String fileType) {    
       try {    
           // 中文文件名支持    
           String encodedfileName = new String(fileName.getBytes("GBK"), "ISO8859-1");    
           response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + fileType + "\"");    
       } catch (UnsupportedEncodingException e) {    
       }    
   }  
}  