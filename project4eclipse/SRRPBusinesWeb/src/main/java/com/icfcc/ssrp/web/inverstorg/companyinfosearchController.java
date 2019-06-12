package com.icfcc.ssrp.web.inverstorg;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.SRRPDao.jpa.entity.companyInfo.CapCompanyInfoVo;
import com.icfcc.SRRPDao.jpa.entity.companyInfo.CapCompanyInfoWsVo;
import com.icfcc.SRRPDao.jpa.entity.companyInfo.CapcompanyinfoBean;
import com.icfcc.SRRPService.creditscore.client.WSWebServiceClient;
import com.icfcc.credit.platform.util.JsonUtil;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.ssrp.util.ExcelUtil;
import com.icfcc.ssrp.web.SRRPBaseController;

@Slf4j
@Controller
@RequestMapping(value = "/companyserch")
public class companyinfosearchController extends SRRPBaseController {
	private static Logger _log = LoggerFactory
			.getLogger(companyinfosearchController.class);
	@Autowired
	private WSWebServiceClient webServices;
	/**
	 * 深度查询
	 * 
	 * @Title: toQuery
	 * @param request
	 * @param response
	 * @return String 返回类型
	 * 
	 */
	@RequestMapping(value = "/toQuery")
	public String toQuery(HttpServletRequest request,
			HttpServletResponse response) {
		CapcompanyinfoBean capcompanyinfoBean=new CapcompanyinfoBean();
		request.setAttribute("capcompanyinfoBean", capcompanyinfoBean);
		return "WEB-INF/views/enterprise/capcompanyinfosearchView";
	}
	/**
	 * 接口查询企业
	 * @param page
	 * @param infoSearch
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/query")
	public void query(PageBean page,CapCompanyInfoVo infoSearch,HttpServletRequest request,
			HttpServletResponse response) {
		try {
			initValue(request,infoSearch);//接收数组变量 ，如checkobx类型 
			//参数转json字符串
			String json = JSON.toJSONString(infoSearch,SerializerFeature.DisableCircularReferenceDetect);
			//调用增值系统筛选接口，返回结果
			String result =webServices.getCorpInfos(json, infoSearch.getPageNum(), infoSearch.getPageSize());
			//测试结果json
//			String result="{\"total\":10,\"data\":[{\"authflaginfo\":\"否\",\"corpcode\":\"\",\"corpid\":\"ff8080815404b34601541401d7c6686d\",\"corpname\":\"苏州汇德融房地产开发有限公司\",\"creditscore\":\"764\",\"financeauthflaginfo\":\"否\",\"id\":\"1\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"086928116\",\"corpid\":\"ff808081554923ee01554d967c010f58\",\"corpname\":\"苏南阳光城置业（苏州）有限公司\",\"creditscore\":\"753\",\"financeauthflaginfo\":\"否\",\"id\":\"2\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"73333841-2\",\"corpid\":\"2AEFC8FBAA537C52E053040812AC0DB0\",\"corpname\":\"莱克电气股份有限公司\",\"creditscore\":\"753\",\"financeauthflaginfo\":\"否\",\"id\":\"3\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"73225144-6\",\"corpid\":\"ff80808151f6c75d015210ad64e227bb\",\"corpname\":\"江苏澳洋科技股份有限公司\",\"creditscore\":\"752\",\"financeauthflaginfo\":\"是\",\"id\":\"4\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"71150203-1\",\"corpid\":\"ff8080814fd58f66014fda7646fd0e1a\",\"corpname\":\"优德精密工业（昆山）股份有限公司\",\"creditscore\":\"749\",\"financeauthflaginfo\":\"否\",\"id\":\"5\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"\",\"corpid\":\"deeabe1e51044bb4b305117109c01706\",\"corpname\":\"太仓远汇置业有限公司\",\"creditscore\":\"746\",\"financeauthflaginfo\":\"否\",\"id\":\"6\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"55026320-0\",\"corpid\":\"ff8080815167343401516be0e47e2a99\",\"corpname\":\"太仓荣南密封件科技有限公司\",\"creditscore\":\"745\",\"financeauthflaginfo\":\"是\",\"id\":\"7\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"39839612-0\",\"corpid\":\"2AEFC8FBAD4A7C52E053040812AC0DB0\",\"corpname\":\"金科集团苏州百俊房地产开发有限公司\",\"creditscore\":\"744\",\"financeauthflaginfo\":\"否\",\"id\":\"8\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"\",\"corpid\":\"ff808081542dfe4e01544c365446045a\",\"corpname\":\"苏州兆祥房地产开发有限公司\",\"creditscore\":\"743\",\"financeauthflaginfo\":\"否\",\"id\":\"9\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"08859365-5\",\"corpid\":\"ff8080814fd58f66014fd9dd65430b84\",\"corpname\":\"常熟招商熙城房地产有限公司\",\"creditscore\":\"743\",\"financeauthflaginfo\":\"否\",\"id\":\"10\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"}]}";
			Map<String, Object> map = JSON.parseObject(result);

			String totalStr=map.get("total").toString();//总记录数
			String data=map.get("data").toString();//每页的结果数据
			//转成对象，返回前台展示
			 List<CapCompanyInfoWsVo> CapCompanyInfoWsVoResults = (List<CapCompanyInfoWsVo>) JSON.parseArray(data, CapCompanyInfoWsVo.class);
			 page.setList(CapCompanyInfoWsVoResults);//装配page结果
				if (CollectionUtils.isNotEmpty(CapCompanyInfoWsVoResults)) {
					page.setList(CapCompanyInfoWsVoResults);
					// 设置总的条数
					Integer total = new Integer(String.valueOf(totalStr));
					page.setRecordCnt(total);
					if (StringUtils.isNotBlank(infoSearch.getPageNum())) {
						page.setMaxSize(Integer.parseInt(infoSearch.getPageNum()));
					}
					if (StringUtils.isNotBlank(infoSearch.getPageSize())) {
						page.setCurPage(Integer.parseInt(infoSearch.getPageSize()));
					}
					page.pageResult(CapCompanyInfoWsVoResults, page.getRecordCnt(),
							page.getMaxSize(), page.getCurPage());
				}
			this.writeJson(page, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/export")
	public void export(CapCompanyInfoVo infoSearch,HttpServletRequest request,
			HttpServletResponse response) {
		//获取数据
		initValue(request,infoSearch);//接收数组变量 ，如checkobx类型 
		 Map fileMap=new HashMap();
		try {
		//参数转json字符串
		String json = JSON.toJSONString(infoSearch,SerializerFeature.DisableCircularReferenceDetect);
		//调用增值系统筛选接口，返回结果
//		String result =webServices.getCorpInfos(json, infoSearch.getTotal(), "1");
//		result = "[" + result+ "]";
		//测试结果json
		String result="{\"total\":10,\"data\":[{\"authflaginfo\":\"否\",\"corpcode\":\"\",\"corpid\":\"ff8080815404b34601541401d7c6686d\",\"corpname\":\"苏州汇德融房地产开发有限公司\",\"creditscore\":\"764\",\"financeauthflaginfo\":\"否\",\"id\":\"1\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"086928116\",\"corpid\":\"ff808081554923ee01554d967c010f58\",\"corpname\":\"苏南阳光城置业（苏州）有限公司\",\"creditscore\":\"753\",\"financeauthflaginfo\":\"否\",\"id\":\"2\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"73333841-2\",\"corpid\":\"2AEFC8FBAA537C52E053040812AC0DB0\",\"corpname\":\"莱克电气股份有限公司\",\"creditscore\":\"753\",\"financeauthflaginfo\":\"否\",\"id\":\"3\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"73225144-6\",\"corpid\":\"ff80808151f6c75d015210ad64e227bb\",\"corpname\":\"江苏澳洋科技股份有限公司\",\"creditscore\":\"752\",\"financeauthflaginfo\":\"是\",\"id\":\"4\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"71150203-1\",\"corpid\":\"ff8080814fd58f66014fda7646fd0e1a\",\"corpname\":\"优德精密工业（昆山）股份有限公司\",\"creditscore\":\"749\",\"financeauthflaginfo\":\"否\",\"id\":\"5\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"\",\"corpid\":\"deeabe1e51044bb4b305117109c01706\",\"corpname\":\"太仓远汇置业有限公司\",\"creditscore\":\"746\",\"financeauthflaginfo\":\"否\",\"id\":\"6\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"55026320-0\",\"corpid\":\"ff8080815167343401516be0e47e2a99\",\"corpname\":\"太仓荣南密封件科技有限公司\",\"creditscore\":\"745\",\"financeauthflaginfo\":\"是\",\"id\":\"7\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"39839612-0\",\"corpid\":\"2AEFC8FBAD4A7C52E053040812AC0DB0\",\"corpname\":\"金科集团苏州百俊房地产开发有限公司\",\"creditscore\":\"744\",\"financeauthflaginfo\":\"否\",\"id\":\"8\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"\",\"corpid\":\"ff808081542dfe4e01544c365446045a\",\"corpname\":\"苏州兆祥房地产开发有限公司\",\"creditscore\":\"743\",\"financeauthflaginfo\":\"否\",\"id\":\"9\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"},{\"authflaginfo\":\"否\",\"corpcode\":\"08859365-5\",\"corpid\":\"ff8080814fd58f66014fd9dd65430b84\",\"corpname\":\"常熟招商熙城房地产有限公司\",\"creditscore\":\"743\",\"financeauthflaginfo\":\"否\",\"id\":\"10\",\"loanflaginfo\":\"否\",\"pjdate\":\"20180601\",\"xydji\":\"R1\"}]}";
		Map<String, Object> map = JSON.parseObject(result);
		String data=map.get("data").toString();//每页的结果数据
		//转成对象，返回前台展示
		 List<CapCompanyInfoWsVo> CapCompanyInfoWsVoResults = (List<CapCompanyInfoWsVo>) JSON.parseArray(data, CapCompanyInfoWsVo.class);
		//excel标题
		String[] title = {"序号","企业名称","信用分","信用等级","评价日期","是否有贷","是否授权","是否金融办授权"};
		//excel文件名
		String fileName =System.currentTimeMillis()+".xls";
		//sheet名
        String sheetName = "深度查询导出信息表";
        String [][] content = new String[CapCompanyInfoWsVoResults.size()][];
		for (int i = 0; i < CapCompanyInfoWsVoResults.size(); i++) {
			content[i] = new String[title.length];
        	CapCompanyInfoWsVo vo = CapCompanyInfoWsVoResults.get(i);
        	content[i][0] = vo.getId();
            content[i][1] = vo.getCorpname();
            content[i][2] = vo.getCreditscore();
            content[i][3] = vo.getXydjiStr();
            content[i][4] = vo.getPjdate();
            content[i][5] = vo.getLoanflaginfo();
            content[i][6] = vo.getAuthflaginfo();
            content[i][7] = vo.getFinanceauthflaginfo();
        }
      //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        boolean flag= ExcelUtil.popDownload(request,response,wb,fileName);
        fileMap.put("fileName", fileName);
		} catch (Exception e) {
			 fileMap.put("fileName", "error");
			e.printStackTrace();
		}
		  this.writeJson(fileMap, request, response);
	}
	
	
	@RequestMapping(value = "/downLoad")
	public void downLoad(CapCompanyInfoVo infoSearch,HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		   //初始化流
	       ByteArrayOutputStream os = new ByteArrayOutputStream();
	       BufferedInputStream bis = null;
	       BufferedOutputStream bos = null;
	       String fileName = infoSearch.getFileName();
    	   String filePath=request.getSession().getServletContext().getRealPath("/")+"static\\files\\popfile\\";//固定的文件路径以备下载使用
           File file = new File(filePath+fileName);
           FileInputStream inputStream = new FileInputStream(file);
           InputStream is=null;
	       try {
	            if(file.exists()){
	            	//表格定稿数据
	            	int len = 0;
	                byte [] buffer  = new byte[2048];
	                while((len = inputStream.read(buffer)) != -1)
	                {
	                   os.write(buffer, 0,  len);
	                }
	            	
	 	           byte[] content = os.toByteArray();
	 	          is = new ByteArrayInputStream(content);
	 	           // 设置response参数，可以打开下载页面
	 	           response.reset();
	 	           String encodedfileName = new String(fileName.getBytes("GBK"), "ISO8859-1");    
	 	           response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName +"\"");    
	 	           ServletOutputStream out = response.getOutputStream();
	 	           bis = new BufferedInputStream(is);
	 	           bos = new BufferedOutputStream(out);
	 	           byte[] buff = new byte[2048];
	 	           int bytesRead;
	 	           while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	 	               bos.write(buff, 0, bytesRead);
	 	           }  
		    	   }
	            
	       } catch (Exception e) {
	           e.printStackTrace();
	       } finally {
	    	   is.close();
	    	   inputStream.close();
	           //关闭流
	           if (bis != null)
	               bis.close();
	           if (bos != null)
	               bos.close();
	           if (os != null)
	               os.close();
	       }
	       if(file.exists()){
	    	   file.delete();
	       }
	}
}
