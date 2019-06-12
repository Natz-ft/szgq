package com.icfcc.SRRPService.gataway.staticize;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class GataWayBaseStaticzeService {

	// private static String modelFolder = getBaseHome() + "model//";
	@Value("${mainOutPath}")
	private String mainOutPath;

	@Value("${portal.modelFolder}")
	private String modelFolder;

	public void makeStaticzeHtml(Map<String, Object> data, String modelFile,
			String outFile) throws Exception {
		Configuration cfg = new Configuration();
		// 设置模版路径（文件夹）
		cfg.setDirectoryForTemplateLoading(new File(modelFolder));
		// 获取模版（文件名）
		Template template = cfg.getTemplate(modelFile);
		template.setEncoding("UTF-8");
		// 输出html的全路径
		File afile = new File(mainOutPath + outFile);
		// 创建输出流
		// Writer out = new BufferedWriter(new OutputStreamWriter(
		// new FileOutputStream(afile)));
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(afile), "UTF-8"));
		// 渲染模版
		template.process(data, out);
	}
}
