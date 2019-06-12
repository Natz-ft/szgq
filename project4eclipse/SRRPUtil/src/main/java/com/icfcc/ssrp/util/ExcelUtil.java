package com.icfcc.ssrp.util;

import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.session.InvestorAchievement;
import com.icfcc.ssrp.session.InvestorManageAchievement;
import com.icfcc.ssrp.session.RedisGetValue;

/**
 * 
 * @ClassName: ExcelUtil
 * @Description: TODO(解析Excel工具类)
 * @author hugt
 * @date 2018年4月8日 下午7:21:24
 *
 */
public class ExcelUtil {
	 /**
    *
    * @Description 弹出下载提示框
    * @author        <p style="color:#8e8e8e;font-family:微软雅黑;font-size=16px;font-weight:bold;">Cloud</p>
    * @date        <p style="color:#000;font-family:微软雅黑;font-size=16px;">2016-11-25下午1:25:51</p>
    * @param response    请求头信息
    * @param wb        表格
    * @param filePath    文件路径
    * @throws IOException
    */
   public static boolean popDownload(HttpServletRequest request,HttpServletResponse response, HSSFWorkbook wb, String fileName) throws IOException{
	    boolean flag = false ;
	   // 文件存放位置 保存于服务器
       String filePath = request.getSession().getServletContext().getRealPath("/")+"static\\files\\popfile\\";
       File files = new File(filePath);
       if (!files.exists()) {
    	   files.mkdirs(); // 创建文件
		}
       FileOutputStream fout =null;
       String path=filePath+fileName;
       try{
       // 新建一输出流
        fout = new FileOutputStream(path);
       // 存盘
       wb.write(fout);
       // 清空缓冲区
       fout.flush();
       // 结束关闭
       fout.close();
       flag = true;
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           //关闭流
           if (fout != null)
        	   fout.close();
       }
       return flag;
   }
	/**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }
	/**
	 * 
	 * @Title: getExcleSheet @Description: TODO(这里用一句话描述这个方法的作用) @param @param
	 * file @param @param sheetAt @param @return 参数说明 @return Sheet 返回类型 @throws
	 */
	public static Sheet getExcleSheet(CommonsMultipartFile file, int sheetAt) {
		Sheet sheet = null;
		try {
			String[] split = file.getOriginalFilename().split("\\."); // .是特殊字符，需要转义！！！！！
			Workbook wb;
			// 根据文件后缀（xls/xlsx）进行判断
			if ("xls".equals(split[1])) {
				wb = new HSSFWorkbook(file.getInputStream());
			} else if ("xlsx".equals(split[1])) {
				wb = new XSSFWorkbook(file.getInputStream());
			} else {
				throw new Exception("文件类型错误");
			}
			// 开始解析
			sheet = wb.getSheetAt(sheetAt); // 读取sheet 0
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sheet;
	}
	public static ResultBean checkDatasByrc(Map<Integer, String> beanpros,List<Object> results1, Sheet sheet,ResultBean rs,String sheetName) {
		String msg = "";
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			Set<Integer> set = beanpros.keySet();// 获取map的key结果集
			int firstRowIndex = sheet.getFirstRowNum() + 2; // 第一行是列名，所以不读
			int lastRowIndex = sheet.getLastRowNum();// 获取最后一行的数值
			for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
				Row row = sheet.getRow(rIndex);// 获得当前行
				if (row != null) {
					if (!isCellNull(set, row)) {
                        String flag="";
						// 循环对象的每一个属性，set值
						for (Integer key : set) {
							String value = "";
							Cell cell = row.getCell(key);// 获取当前单元格的值
							if (cell != null) {
								rs = new ResultBean(Constant.SUCCESSCODE, "00");
								switch (cell.getColumnIndex()) {

								case 0:// 字符串
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cell.getRichStringCellValue().toString();
										if (value.length() > 50) {
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第A列投资基金名称长度超过50位！");
											return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第A列投资基金名称内容不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第A列投资基金名称内容必须是字符串类型！");
										return rs;
									}
									break;
								case 1:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cell.getRichStringCellValue().toString();
										if (value.length() > 50) {
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第B列已投企业名称长度超过50位！");
											return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第B列已投企业名称内容不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第B列已投企业名称内容必须是字符串类型！");
										return rs;
									}
									break;
								case 2:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										String Stage = null;
										value = cell.getRichStringCellValue().toString();
										Stage=RedisGetValue.getCodeByValue(SRRPConstant.DD_INDUSTRY,value);
										if (Stage.equals("")) {
											Stage=RedisGetValue.getCodeByValue(SRRPConstant.DD_INDUSTRY_2,value);
											if (Stage.equals("")) {
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第C列已投企业行业填写有误,请参考填写说明中对投资行业的界定！");
												return rs;
											}
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第C列企业行业不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第C列企业行业必须是字符串类型！");
										return rs;
									}
									break;
								case 3:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										String Stage = null;
										value = cell.getRichStringCellValue().toString();
										Stage=RedisGetValue.getCodeByValueArea(SRRPConstant.DD_AREA,value);
										if (Stage.equals("")) {
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第D列已投企业所属区域填写有误,请参考填写说明中对区域的界定！");
												return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第D列已投企业所属区域不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第D列已投企业所属区域必须是字符串类型！");
										return rs;
									}
									break;
								case 4:// 字符串
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										if (!DateUtil.isCellDateFormatted(cell)) {// 如果是date类型
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第E列投资时间必须是时间格式！");
											return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第E列投资时间内容不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第E列投资时间必须是时间格式！");
										return rs;
									}
									break;
								case 5:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										if (DateUtil.isCellDateFormatted(cell)) {// 如果是date类型
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第F列投资金额必须是数值型！");
											return rs;
										}else{
											if(!isDoubleNum2(cell.getNumericCellValue())){
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第F列投资金额最多保留二位小数！");
												return rs;
											}
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第F列投资金额不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第F列投资金额必须是数值型！");
										return rs;
									}
									break;
								case 6:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cell.getRichStringCellValue().toString();
										if (!value.equals("人民币") && !value.equals("美元")) {
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第G列投资币种必须是美元或人民币！");
											return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第G列投资币种不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第G列投资币种必须是字符串类型！");
										return rs;
									}
									break;
								case 7:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										if (DateUtil.isCellDateFormatted(cell)) {// 如果是date类型
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第H列股权比例必须是数值型！");
											return rs;
										}else{
											if(!isDoubleNum2(cell.getNumericCellValue())){
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第H列股权比例最多保留二位小数！");
												return rs;
											}
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第H列股权比例不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第H列股权比例必须是数值型！");
										return rs;
									}
									break;
								case 8:// 字符串
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										String Stage = null;
										value = cell.getRichStringCellValue().toString();
										Stage=RedisGetValue.getCodeByValue(SRRPConstant.DD_FINACINGTURN,value);
										if (Stage.equals("")) {
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第I列投资轮次填写有误,请参考填写说明中对投资轮次的界定！");
												return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第I列投资轮次不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第I列投资轮次必须是字符串类型！");
										return rs;
									}
									break;
								case 9:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										String Stage = null;
										value = cell.getRichStringCellValue().toString();
										Stage=RedisGetValue.getCodeByValue(SRRPConstant.DD_INVESTMENT,value);
										if (Stage.equals("")) {
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第J列投资阶段填写有误,请参考填写说明中对投资阶段的界定！");
												return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第J列投资阶段不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第J列投资阶段必须是字符串类型！");
										return rs;
									}
									break;

								case 10:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										if (DateUtil.isCellDateFormatted(cell)) {// 如果是date类型
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第K列已投企业注册资本必须是数值型！");
											return rs;
										}else{
											if(!isDoubleNum2(cell.getNumericCellValue())){
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第K列已投企业注册资本最多保留二位小数！");
												return rs;
											}
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第K列已投企业注册资本不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第K列已投企业注册资本必须是数值型！");
										return rs;
									}
									break;
								case 11:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cell.getRichStringCellValue().toString();
										if (!value.equals("人民币") && !value.equals("美元")) {
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第L列已投企业注册资本币种必须是美元或人民币！");
											return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第L列已投企业注册资本币种不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第L列已投企业注册资本币种必须是字符串类型！");
										return rs;
									}
									break;
								case 12:
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cell.getRichStringCellValue().toString();
										if (!value.equals("是") && !value.equals("否")) {
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第M列是否退出必须是美元或人民币！");
											return rs;
										}else{
											if(value.equals("是")){
												flag="1";
											}else{
												flag="2";
											}
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第M列是否退出不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第M列是否退出必须是字符串类型！");
										return rs;
									}
									break;
									
								case 13:// 字符串
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										if (!DateUtil.isCellDateFormatted(cell)) {// 如果是date类型
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第N列退出时间必须是时间格式！");
											return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										if(flag.equals("1")){//是否退出：是  	退出时间不能为空
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第N列退出时间不能为空！");
											return rs;
										}else{
											break;
										}
										
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第N列退出时间必须是时间格式！");
										return rs;
									}
									break;
								case 14:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										if (DateUtil.isCellDateFormatted(cell)) {// 如果是date类型
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第O列回报率必须是数值型！");
											return rs;
										}else{
											if(!isDoubleNum2(cell.getNumericCellValue())){
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第O列回报率最多保留二位小数！");
												return rs;
											}
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										if(flag.equals("1")){//是否退出：是  	退出时间不能为空
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第O列回报率不能为空！");
											return rs;
										}else{
											break;
										}
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第O列回报率必须是数值型！");
										return rs;
									}
									break;
								default:// 数字
									break;

								}

							} 
						}

					}

				} else {
					System.out.println("row is null");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs = new ResultBean(Constant.ERRORCODE, "导入异常!");
			return rs;
		}

		return rs;
	}
	/**
	 * 校验管理业绩
	 */
	public static ResultBean checkManageDatasByrc(Map<Integer, String> beanpros,
			List<Object> results1, Sheet sheet,ResultBean rs,String sheetName) {
		String msg = "";
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			Set<Integer> set = beanpros.keySet();// 获取map的key结果集
			int firstRowIndex = sheet.getFirstRowNum() + 2; // 第一行是列名，所以不读
			int lastRowIndex = sheet.getLastRowNum();// 获取最后一行的数值
			for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
				Row row = sheet.getRow(rIndex);// 获得当前行
				if (row != null) {
					if (!isCellNull(set, row)) {
                        double manageCapitalMin = 0;
                        double manageCapitalMax = 0;
                        int areadyInevestNum = 0;
                        int inevestNum = 0;
						// 循环对象的每一个属性，set值
						for (Integer key : set) {
							String value = "";
							Cell cell = row.getCell(key);// 获取当前单元格的值
							if (cell != null) {
								
								rs = new ResultBean(Constant.SUCCESSCODE, "00");
								switch (cell.getColumnIndex()) {

								case 0:// 字符串
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cell.getRichStringCellValue().toString();
										if (value.length() > 50) {
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第A列管理的基金名称长度超过50位！");
											return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第A列管理的基金名称不能为空！");
										return rs;
									}else{
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第A列管理的基金名称必须是字符串类型！");
										return rs;
									}
									break;
								case 1:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										if (!DateUtil.isCellDateFormatted(cell)) {// 如果是date类型
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第B列注册时间必须是时间格式！");
											return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第B列注册时间不能为空！");
										return rs;
									} else if(cell.getCellType()==Cell.CELL_TYPE_STRING){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第B列注册时间必须为时间格式，请检查单元格格式！");
										return rs;
									}else{
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第B列注册时间必须为时间格式，请检查单元格格式！");
										return rs;
									}
									break;
								case 2:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cell.getRichStringCellValue().toString();
										if (value.length() > 10) {
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第C列注册地长度超过10位！");
											return rs;
										}else{//校验字典值
											if( RedisGetValue.getCodeByValueArea(SRRPConstant.DD_AREA,value.trim()).equals("")){
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第C列注册地填写有误,请参考填写说明中对区域的界定！");
												return rs;
											}
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE,"失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第C列注册地不能为空！");
										return rs;
									}else{
										rs = new ResultBean(Constant.ERRORCODE,"失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第C列注册地必须是字符串类型！");
										return rs;
									}
									break;
								case 3:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cell.getRichStringCellValue().toString();
										if (value.length() > 20) {
											rs = new ResultBean(Constant.ERRORCODE,"失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第D列托管机构长度超过20位！");
											return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE,"失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第D列托管机构必须不能为空！");
										return rs;
									}else{
										rs = new ResultBean(Constant.ERRORCODE,"失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第D列托管机构必须是字符串类型！");
										return rs;
									}
									break;
								case 4:// 字符串
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										if (DateUtil.isCellDateFormatted(cell)) {// 如果是date类型
											rs = new ResultBean(Constant.ERRORCODE,"失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第E列管理资金规模必须是数值型！");
											return rs;
										}else{
											manageCapitalMin=cell.getNumericCellValue();
											if(!isDoubleNum2(manageCapitalMin)){
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第F列管理资金规模最小值最多保留二位小数！");
												return rs;
											}
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第E列管理资金规模不能为空！");
										return rs;
									}else{
										rs = new ResultBean(Constant.ERRORCODE,"失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第E列管理资金规模必须是数值型！");
										return rs;
									}
									break;
								case 5:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										if (DateUtil.isCellDateFormatted(cell)) {// 如果是date类型
											rs = new ResultBean(Constant.ERRORCODE,"失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第F列管理资金规模必须是数值型！");
											return rs;
										}else{
											manageCapitalMax=cell.getNumericCellValue();
											if(!isDoubleNum2(manageCapitalMax)){
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第F列管理资金最大值最多保留二位小数！");
												return rs;
											}
											
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第F列单元格不能为空！");
										return rs;
									}else{
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第F列单元格必须是数值型！");
										return rs;
									}
									break;
								case 6:// 数字
									 if(Double.doubleToLongBits(manageCapitalMin) > Double.doubleToLongBits(manageCapitalMax)){
										    rs = new ResultBean(Constant.ERRORCODE,  "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第F列管理资金规模最大值应大于最小值！");
											return rs;
									 }
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cell.getRichStringCellValue().toString();
										if (!value.equals("人民币") && !value.equals("美元")) {
											rs = new ResultBean(Constant.ERRORCODE,  "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第G列管理资金规模币种必须是美元或人民币！");
											return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第G列管理资金规模币种不能为空！");
										return rs;
									}else{
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第G列管理资金规模币种必须是字符串类型！");
										return rs;
									}
									break;
								case 7:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cell.getRichStringCellValue().toString();
										if (value.length() > 6) {
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex+1) + "行第H列基金编号(中基协)长度超过6位！");
											return rs;
										}else{
											String regex = "^[Ss][A-Za-z0-9]{5}$";
											if(!match(regex, value)){
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第H列基金编号(中基协)必须以S开头！");
												return rs;
										    }
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第H列基金编号(中基协)不能为空！");
										return rs;
									}else{
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第H列基金编号(中基协)必须是字符串类型！");
										return rs;
									}
									break;
								case 8:// 字符串
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										String Stage = null;
										value = cell.getRichStringCellValue().toString();
										Stage=RedisGetValue.getCodeByValue(SRRPConstant.DD_SUBACTYPE,value);
										if (Stage.equals("")) {
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第I列基金类型单元格填写有误,请参考填写说明中对基金类型的界定！");
												return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第I列基金类型不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第I列基金类型必须是字符串类型！");
										return rs;
									}
									break;
								case 9:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cell.getRichStringCellValue().toString();
										value=value.replace("，", ",");
										String[] values = value.split(",");
										for (int i = 0; i < values.length; i++) {
											String Stage = null;
											 Stage =
											 RedisGetValue.getCodeByValue(SRRPConstant.DD_INDUSTRY,
											 values[i]);
											if (Stage.equals("")) {
												 Stage =RedisGetValue.getCodeByValue(SRRPConstant.DD_INDUSTRY_2,
												 values[i]);
												if (Stage.equals("")) {
													rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1)
															+ "行第J列投资行业填写有误,请参考填写说明中对投资行业的界定！");
													return rs;
												}
											}
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第J列投资行业不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第J列投资行业必须是字符串类型！");
										return rs;
									}
									break;

								case 10:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cell.getRichStringCellValue().toString();
										value=value.replace(",", "，");
										String[] values = value.split(",");
										for (int i = 0; i < values.length; i++) {
											String Stage = null;
											 Stage =
											 RedisGetValue.getCodeByValue(SRRPConstant.DD_INVESTMENT,
											 values[i]);
											if (Stage.equals("")) {
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1)
														+ "行第K列投资阶段填写有误,请参考填写说明中对投资阶段的界定！");
												
												return rs;

											}
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第K列投资阶段不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第K列投资阶段必须是字符串类型！");
										return rs;
									}
									break;

								case 11:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										if (DateUtil.isCellDateFormatted(cell)) {// 如果是date类型
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第L列投资项目数量必须是数值型！");
											return rs;
										}else{
											double num=cell.getNumericCellValue();
											String str=String.valueOf(num).replaceAll("\\d+\\.", "");
											if(!str.equals("0")){
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第L列投资项目数量必须是整数！");
												return rs;
											}else{
												String regex = "^[-\\+]?[\\d]*$";
												if(!match(regex, String.valueOf((new Double(num)).intValue()))){
													rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第L列投资项目数量必须是整数！");
													return rs;
											    }else{
							                         inevestNum = new Double(cell.getNumericCellValue()).intValue();
											    }
											}
											
										}
										
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第L列投资项目数量不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第L列投资项目数量必须是数值型！");
										return rs;
									}
									break;
								case 12:// 字符串
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										if (DateUtil.isCellDateFormatted(cell)) {// 如果是date类型
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第M列累计投资金额必须是数值型！");
											return rs;
										}else{
											if(!isDoubleNum2(cell.getNumericCellValue())){
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第M列累计投资金额最多保留二位小数！");
												return rs;
											}
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第M列累计投资金额不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第M列累计投资金额必须是数值型！");
										return rs;
									}
									break;
								case 13:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cell.getRichStringCellValue().toString();
										if (!value.equals("人民币") && !value.equals("美元")) {
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第N列累计投资金额币种必须是美元或人民币！");
											return rs;
										}
									} else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第N列累计投资金额币种不能为空！");
										return rs;
									}else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第N列累计投资金额币种必须是字符串类型！");
										return rs;
									}
									break;
								case 14:// 数字
									if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										if (DateUtil.isCellDateFormatted(cell)) {// 如果是date类型
											rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第O列实现退出项目数量必须是数值型！");
											return rs;
										}else{
											double num=cell.getNumericCellValue();
											String str=String.valueOf(num).replaceAll("\\d+\\.", "");
											if(!str.equals("0")){
												rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第O列实现退出项目数量必须是整数！");
												return rs;
											}else{
												String regex = "^[-\\+]?[\\d]*$";
												if(!match(regex, String.valueOf((new Double(num)).intValue()))){
													rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第O列实现退出项目数量必须是整数！");
													return rs;
											    }else{
											    	 areadyInevestNum = new Double(cell.getNumericCellValue()).intValue();
											    	 if(areadyInevestNum>inevestNum){
											    		 rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第O列实现退出项目数量必须小于投资项目数量！");
														 return rs;
											    	 }

											    	 
											    }
											}
										}
									}else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第O列实现退出项目数量不能为空！");
										return rs;
									} else {
										rs = new ResultBean(Constant.ERRORCODE, "失败原因："+sheetName+"文件的第" + (rIndex + 1) + "行第O列实现退出项目数量必须是数值型！");
										return rs;
									}
									break;
								default:// 数字
									break;

								}

							} 
						}

					}

				} else {
					System.out.println("row is null");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "导入失败";
			rs = new ResultBean(Constant.ERRORCODE, "导入异常!");
			return rs;
		}

		return rs;
	}
	 public static boolean isDoubleNum2(double num) {
	        /**判断num是两位小数*/
	     Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
	        Matcher match=pattern.matcher(String.valueOf(num));
	        if(match.matches()==false){ 
	            return false; 
	         }else{ 
	            return true; 
	         }
	       
	    }
	 private static boolean match(String regex, String str) {
		 Pattern pattern = Pattern.compile(regex);
		 Matcher matcher = pattern.matcher(str);
		 return matcher.matches();
		 }
	/**
	 * 
	 * @Title: getDatasByrc @Description:
	 * TODO(读取excel数据映射到指定bean中) @param @param beginRow 开始行 @param @param
	 * beanpros mapBEAN @param @param classPathName 映射class @param @param
	 * results 结果集 @param @param sheet @param @return 参数说明 @return String
	 * 返回类型 @throws
	 */
	public static String getDatasByrc(Map<Integer, String> beanpros, Class<?> clazz, List<Object> results,
			Sheet sheet) {
		String msg = "";
		try {
			Set<Integer> set = beanpros.keySet();// 获取map的key结果集
			int firstRowIndex = sheet.getFirstRowNum() + 2; // 第一行是列名，所以不读
			int lastRowIndex = sheet.getLastRowNum();// 获取最后一行的数值
			for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
				Row row = sheet.getRow(rIndex);// 获得当前行
				if (row != null) {
					Object obj = clazz.newInstance();
					// 循环对象的每一个属性，set值
					for (Integer key : set) {
						String value = "";
						Cell cell = row.getCell(key);// 获取当前单元格的值
						if (cell != null) {
							PropertyDescriptor pd = new PropertyDescriptor(beanpros.get(key), clazz);
							Method wM = pd.getWriteMethod();
							switch (cell.getCellType()) {// 判断单元格的数据的类型
							case Cell.CELL_TYPE_STRING:// 字符串
								value = cell.getRichStringCellValue().toString();
								wM.invoke(obj, value);// 解析的值映射到对象中
								break;
							case Cell.CELL_TYPE_NUMERIC:// 数字
								if (DateUtil.isCellDateFormatted(cell)) {// 如果是date类型
									Date d = cell.getDateCellValue();
									DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
									value = formater.format(d);
									Date date = formater.parse(value);
									Date sqlDate = new java.sql.Date(date.getTime());
									wM.invoke(obj, sqlDate);// 解析的值映射到对象中
									break;
								} else {
									DataFormatter dataFormatter = new DataFormatter();
									value = dataFormatter.formatCellValue(cell);
									double aa = Double.parseDouble(value);
									wM.invoke(obj, aa);// 解析的值映射到对象中
									break;
								}
							case Cell.CELL_TYPE_BLANK:
								break;
							case Cell.CELL_TYPE_FORMULA:
								value = String.valueOf(cell.getCellFormula());
								break;
							case Cell.CELL_TYPE_BOOLEAN:// boolean型值
								value = String.valueOf(cell.getBooleanCellValue());
								break;
							case Cell.CELL_TYPE_ERROR:
								value = String.valueOf(cell.getErrorCellValue());
								break;
							default:
								break;
							}

						} else {
//							System.out.println("Cell is null");
						}
					}
					if (!isCellNull(set, row)) {
						results.add(obj);// 每一行的数据添加到list中
					}
				} else {
//					System.out.println("row is null");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "导入失败";
		}

		return msg;
	}

	/**
	 * 
	 * @Title: isCellNull @Description: TODO(判断每一行的单元格是否都为空) @param @param
	 * set @param @param row @param @return 参数说明 @return boolean 返回类型 @throws
	 */
	public static boolean isCellNull(Set<Integer> set, Row row) {
		boolean falg = false;
		for (Integer key : set) {
			Cell cell = row.getCell(key);
			falg = false;
			if (cell == null) {
				falg = true;
			} else if (cell.getCellType() == 3) {
				falg = true;
			} else {

				return falg;
			}
		}
		return falg;
	}
	 /**

     * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行

     * @param file 读取数据的源Excel

     * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1

     * @return 读出的Excel中数据的内容

     * @throws FileNotFoundException

     * @throws IOException

     */

    public static String[][] getData(File file, int ignoreRows, int sheetNum, Workbook wb)
        throws FileNotFoundException, IOException {
        List<String[]> result = new ArrayList<String[]>();
        int rowSize = 0;
        Cell cell = null;
        Sheet st = wb.getSheetAt(sheetNum);
        // 第一行为标题，不取
        for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
            Row row = st.getRow(rowIndex);
            if (row == null) {
                continue;
            }
            int tempRowSize = row.getLastCellNum() + 1;
            if (tempRowSize > rowSize) {
                rowSize = tempRowSize;
            }

            String[] values = new String[rowSize];

            Arrays.fill(values, "");

            boolean hasValue = false;

            for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                String value = "";
                cell = row.getCell(columnIndex);
                if (cell != null) {
                    // 注意：一定要设成这个，否则可能会出现乱码
                    // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_STRING:
                            value = cell.getStringCellValue();
                            break;
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                Date date = cell.getDateCellValue();
                                if (date != null) {
                                    value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                } else {
                                    value = "";
                                }
                            } else {
                                value = new DecimalFormat("0").format(cell.getNumericCellValue());
                            }
                            break;
                        case HSSFCell.CELL_TYPE_FORMULA:
                            // 导入时如果为公式生成的数据则无值
                            if (!cell.getStringCellValue().equals("")) {
                                value = cell.getStringCellValue();
                            } else {
                                value = cell.getNumericCellValue() + "";
                            }
                            break;
                        case HSSFCell.CELL_TYPE_BLANK:
                            break;
                        case HSSFCell.CELL_TYPE_ERROR:
                            value = "";
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                            break;
                        default:
                            value = "";
                    }
                }
                if (columnIndex == 0 && value.trim().equals("")) {
                    break;
                }
                values[columnIndex] = rightTrim(value);
                hasValue = true;
            }
            if (hasValue) {
                result.add(values);
            }
        }
        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[])result.get(i);
        }
        return returnArray;
    }


    /**

     * 去掉字符串右边的空格

     * @param str 要处理的字符串

     * @return 处理后的字符串

     */

     public static String rightTrim(String str) {

       if (str == null) {

           return "";

       }

       int length = str.length();

       for (int i = length - 1; i >= 0; i--) {

           if (str.charAt(i) != 0x20) {

              break;

           }

           length--;

       }

       return str.substring(0, length);

    }
	public static void main(String arg[]) {// 测试
		// excel文件路径
		String excelPath = "E://投资机构业绩表模板1.xlsx";
		try {
			// String encoding = "GBK";
			File excel = new File(excelPath);
			if (excel.isFile() && excel.exists()) { // 判断文件是否存在

				String[] split = excel.getName().split("\\."); // .是特殊字符，需要转义！！！！！
				Workbook wb;
				// 根据文件后缀（xls/xlsx）进行判断
				if ("xls".equals(split[1])) {
					FileInputStream fis = new FileInputStream(excel); // 文件流对象
					wb = new HSSFWorkbook(fis);
				} else if ("xlsx".equals(split[1])) {
					FileInputStream fis = new FileInputStream(excel); // 文件流对象
					wb = new XSSFWorkbook(fis);
				} else {
					System.out.println("文件类型错误!");
					return;
				}
				// 开始解析
				Sheet sheet = wb.getSheetAt(0); // 读取sheet 0
				List<Object> results = new ArrayList<Object>();
				Map<Integer, String> beanpros = new HashMap<Integer, String>();
				beanpros.put(0, "fundName");
				beanpros.put(1, "fundRegistDate");
				beanpros.put(2, "registAddress");
				beanpros.put(3, "trusteeship");
				beanpros.put(4, "manageCapitalMin");
				beanpros.put(5, "manageCapitalMax");
				beanpros.put(6, "curn1");

				beanpros.put(7, "iccFilingNo");
				beanpros.put(8, "foundTypeDicCode");
				beanpros.put(9, "financeTradeDicCode");
				beanpros.put(10, "financeStageDicCode");
				beanpros.put(11, "investmentProjects");
				beanpros.put(12, "cumulativeInvestment");
				beanpros.put(13, "curn2");
				beanpros.put(14, "implementExitProject");
				Class clazz = Class.forName("com.icfcc.ssrp.session.InvestorManageAchievement");
//				ResultBean rs= ExcelUtil.checkManageDatasByrc( beanpros, results, sheet);
//				System.out.println(rs.getCode() + "=" + rs.getMsg());
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
