package com.icfcc.credit.platform.service.system;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.icfcc.credit.platform.jpa.entity.system.PlatformButton;
import com.icfcc.credit.platform.jpa.entity.system.PlatformRoleButton;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUserRole;
import com.icfcc.credit.platform.jpa.repository.system.PlatformButtonDao;
import com.icfcc.credit.platform.jpa.repository.system.PlatformRoleButtonDao;
import com.icfcc.credit.platform.jpa.repository.system.PlatformUserRoleDao;
import com.icfcc.credit.platform.util.cache.CacheEvict;
import com.icfcc.credit.platform.util.cache.Cacheable;
import com.icfcc.credit.platform.util.jpa.PageUtil;

/**
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 * 按钮服务
 * 
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformUploadService {
	private static Logger log = LoggerFactory.getLogger(PlatformUploadService.class);
	@Value("${file.upload.path}")
	public String path;
	
	public   Map<String,Object> updateFile(MultipartFile file,String type,String id ) throws IOException{
		String imgPath=path+type;
		 Map<String,Object> data = new  HashMap<String,Object>();
		 System.out.println("上传路径=================="+imgPath);
		 String name = file.getOriginalFilename();//上传文件的真实路径
		 String suffixName= name.substring(name.lastIndexOf("."));
		 if("".equals(id)||id ==null  ) {
			 id=Integer.toHexString(new Random().nextInt());//自定义随机数作为文件名
		 }
		 //String hash =Integer.toHexString(new Random().nextInt());//自定义随机数作为文件名
		 String  fileName=id+suffixName;
		 File tempFile=new File(imgPath,fileName);
		 if(!tempFile.getParentFile().getParentFile().exists()) {
			 tempFile.getParentFile().getParentFile().mkdir();
		 }
		 if(!tempFile.getParentFile().exists()) {
			 tempFile.getParentFile().mkdir();
		 }
		 if(!tempFile.exists()) {
			 tempFile.delete();
		 }
		 tempFile.createNewFile();
		 file.transferTo(tempFile);
		 data.put("src", imgPath+tempFile.getName());
         data.put("title", "图片");
         data.put("uplodfileName", name);
		 return data;
	}

}
