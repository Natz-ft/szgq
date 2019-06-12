package com.icfcc.SRRPService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.alibaba.druid.pool.DruidDataSource;
import com.icfcc.credit.platform.util.SRRPConstant;

@Component
public class ReportproductFunction   {
	@Resource(name = "dataSource1")
	public DruidDataSource ds;


	public void call(String timeId, String sql) throws SQLException {
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = ds.getConnection();
			cstm = con.prepareCall(sql); // 实例化对象cstm
			cstm.setString(1, timeId); // 存储过程输入参数
			cstm.execute(); // 执行存储过程
			System.out.println("执行完毕===report_total_index========");
			cstm.close();
			con.close();
		} finally {
			cstm.close();
			con.close();
		}

	}

	public void callIndexStatic(String timeId) throws Exception {
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = ds.getConnection();
			String sql = "{CALL report_total_index(?)}";
			cstm = con.prepareCall(sql); // 实例化对象cstm
			cstm.setString(1, timeId); // 存储过程输入参数
			cstm.execute(); // 执行存储过程
			System.out.println("执行完毕===report_total_index========");
			cstm.close();
			con.close();
		} finally {
			cstm.close();
			con.close();
		}

	}

	public void callArea(String timeId) throws Exception {
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = ds.getConnection();
			String sql = "{CALL report_area(?)}";
			cstm = con.prepareCall(sql); // 实例化对象cstm
			cstm.setString(1, timeId); // 存储过程输入参数
			cstm.execute(); // 执行存储过程
			System.out.println("执行完毕===report_area1========");
			cstm.close();
			con.close();
		} finally {
			cstm.close();
			con.close();
		}

	}

	public void callCompany(String timeId) throws Exception {
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = ds.getConnection();
			String sql = "{CALL report_company(?)}";
			cstm = con.prepareCall(sql); // 实例化对象cstm
			cstm.setString(1, timeId); // 存储过程输入参数
			cstm.execute(); // 执行存储过程
			System.out.println("执行完毕======report_company1=====");
		} finally {
			cstm.close();
			con.close();
		}
	}

	public void callIndustry(String timeId) throws Exception {
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = ds.getConnection();
			String sql = "{CALL report_industry(?)}";
			cstm = con.prepareCall(sql); // 实例化对象cstm
			cstm.setString(1, timeId); // 存储过程输入参数
			cstm.execute(); // 执行存储过程
			System.out.println("执行完毕=======report_industry1====");
		} finally {
			cstm.close();
			con.close();
		}
	}

	public void callInvestor(String timeId) throws Exception {
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = ds.getConnection();
			String sql = "{CALL report_investor(?)}";
			cstm = con.prepareCall(sql); // 实例化对象cstm
			cstm.setString(1, timeId); // 存储过程输入参数
			cstm.execute(); // 执行存储过程
			System.out.println("执行完毕===report_investor1========");
		} finally {
			cstm.close();
			con.close();
		}
	}

	public void callTotal(String timeId) throws Exception {
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = ds.getConnection();
			String sql = "{CALL report_total(?)}";
			cstm = con.prepareCall(sql); // 实例化对象cstm
			cstm.setString(1, timeId); // 存储过程输入参数
			cstm.execute(); // 执行存储过程
			System.out.println("执行完毕======report_total1=====");
		} finally {
			cstm.close();
			con.close();
		}
	}

	public void callFinacingturn(String timeId) throws Exception {
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = ds.getConnection();
			String sql = "{CALL report_finacingturn(?)}";
			cstm = con.prepareCall(sql); // 实例化对象cstm
			cstm.setString(1, timeId); // 存储过程输入参数
			cstm.execute(); // 执行存储过程
			System.out.println("执行完毕======report_finacingturn=====");
		} finally {
			cstm.close();
			con.close();
		}
	}

	public void callMonthly(String timeId) throws Exception {
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = ds.getConnection();
			String sql = "{CALL report_monthly(?)}";
			cstm = con.prepareCall(sql); // 实例化对象cstm
			cstm.setString(1, timeId); // 存储过程输入参数
			cstm.execute(); // 执行存储过程
			System.out.println("执行完毕======report_monthly=====");
		} finally {
			cstm.close();
			con.close();
		}
	}
}
