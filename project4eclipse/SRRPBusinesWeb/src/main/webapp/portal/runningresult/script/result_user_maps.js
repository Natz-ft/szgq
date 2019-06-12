

function loadUserInit(){
	 var statistics_user_data;//数据
	$.ajax({
        type: "get",//请求方式
        url: "result_user.json",//url地址
        cache: false,//清楚缓存
        async: false,
        success: function (data) {
            statistics_user_data = data;
            loadUserData(statistics_user_data);
        }
    });
}
function loadUserData(statistics_user_data){
	if($.isEmptyObject(statistics_user_data)!=true){
  	  //平台用户--企业统计
      //苏州汇总
      document.getElementById('suzhou_user_company_data').innerHTML = statistics_user_data.company;
      //平台用户--机构统计
      //苏州汇总
      document.getElementById('suzhou_user_org_data').innerHTML = statistics_user_data.org;
      //外地机构汇总
      document.getElementById('other_user_org_data').innerHTML = statistics_user_data.otherOrg;
      document.getElementById('user_total_date').innerHTML = statistics_user_data.endTotalDate;
      
    //平台用户--机构用户统计
      //苏州汇总
//      document.getElementById('suzhou_user_org_user_data').innerHTML = statistics_user_data.orguser;
  	 if($.isEmptyObject(statistics_user_data.data[0])!=true){
  		    //张家港市zhangJiaGang
  		    document.getElementById('zhangJiaGang_user_company_data').innerHTML = statistics_user_data.data[0].zhangJiaGang;
  		    //常熟市changShu
  		    document.getElementById('changShu_user_company_data').innerHTML = statistics_user_data.data[0].changShu;
  		    //太仓市taiCang
  		    document.getElementById('taiCang_user_company_data').innerHTML = statistics_user_data.data[0].taiCang;
  		    //昆山市kunShan
  		    document.getElementById('kunShan_user_company_data').innerHTML = statistics_user_data.data[0].kunShan;
  		    //工业园区gongYeYuan
  		    document.getElementById('gongYeYuan_user_company_data').innerHTML = statistics_user_data.data[0].gongYeYuan;
  		    //相城区xiangCheng
  		    document.getElementById('xiangCheng_user_company_data').innerHTML = statistics_user_data.data[0].xiangCheng;
  		    //姑苏区guSu
  		    document.getElementById('guSu_user_company_data').innerHTML = statistics_user_data.data[0].guSu;
  		    //高新区gaoXin
  		    document.getElementById('gaoXin_user_company_data').innerHTML = statistics_user_data.data[0].gaoXin;
  		    //吴中区wuZhong
  		    document.getElementById('wuZhong_user_company_data').innerHTML = statistics_user_data.data[0].wuZhong;
  		    //吴江市wuJiang
  		    document.getElementById('wuJiang_user_company_data').innerHTML = statistics_user_data.data[0].wuJiang;
  	 }else{
  		//张家港市zhangJiaGang
		    document.getElementById('zhangJiaGang_user_company_data').innerHTML = "0";
		    //常熟市changShu
		    document.getElementById('changShu_user_company_data').innerHTML  = "0";
		    //太仓市taiCang
		    document.getElementById('taiCang_user_company_data').innerHTML  = "0";
		    //昆山市kunShan
		    document.getElementById('kunShan_user_company_data').innerHTML  = "0";
		    //工业园区gongYeYuan
		    document.getElementById('gongYeYuan_user_company_data').innerHTML  = "0";
		    //相城区xiangCheng
		    document.getElementById('xiangCheng_user_company_data').innerHTML = "0";
		    //姑苏区guSu
		    document.getElementById('guSu_user_company_data').innerHTML = "0";
		    //高新区gaoXin
		    document.getElementById('gaoXin_user_company_data').innerHTML = "0";
		    //吴中区wuZhong
		    document.getElementById('wuZhong_user_company_data').innerHTML =  "0";
		    //吴江市wuJiang
		    document.getElementById('wuJiang_user_company_data').innerHTML = "0";
  	 }
  	 if($.isEmptyObject(statistics_user_data.data[1])!=true){
  		 //张家港市zhangJiaGang
  		 document.getElementById('zhangJiaGang_user_org_data').innerHTML = statistics_user_data.data[1].zhangJiaGang;
  		    //常熟市changShu
  		    document.getElementById('changShu_user_org_data').innerHTML = statistics_user_data.data[1].changShu;
  		    //太仓市taiCang
  		    document.getElementById('taiCang_user_org_data').innerHTML = statistics_user_data.data[1].taiCang;
  		    //昆山市kunShan
  		    document.getElementById('kunShan_user_org_data').innerHTML = statistics_user_data.data[1].kunShan;
  		    //工业园区gongYeYuan
  		    document.getElementById('gongYeYuan_user_org_data').innerHTML = statistics_user_data.data[1].gongYeYuan;
  		    //相城区xiangCheng
  		    document.getElementById('xiangCheng_user_org_data').innerHTML = statistics_user_data.data[1].xiangCheng;
  		    //姑苏区guSu
  		    document.getElementById('guSu_user_org_data').innerHTML = statistics_user_data.data[1].guSu;
  		    //高新区gaoXin
  		    document.getElementById('gaoXin_user_org_data').innerHTML = statistics_user_data.data[1].gaoXin;
  		    //吴中区wuZhong
  		    document.getElementById('wuZhong_user_org_data').innerHTML = statistics_user_data.data[1].wuZhong;
  		    //吴江市wuJiang
  		    document.getElementById('wuJiang_user_org_data').innerHTML = statistics_user_data.data[1].wuJiang;
  	 }else{
  		//张家港市zhangJiaGang
		    document.getElementById('zhangJiaGang_user_org_data').innerHTML = "0";
		    //常熟市changShu
		    document.getElementById('changShu_user_org_data').innerHTML = "0";
		    //太仓市taiCang
		    document.getElementById('taiCang_user_org_data').innerHTML = "0";
		    //昆山市kunShan
		    document.getElementById('kunShan_user_org_data').innerHTML ="0";
		    //工业园区gongYeYuan
		    document.getElementById('gongYeYuan_user_org_data').innerHTML = "0";
		    //相城区xiangCheng
		    document.getElementById('xiangCheng_user_org_data').innerHTML = "0";
		    //姑苏区guSu
		    document.getElementById('guSu_user_org_data').innerHTML = "0";
		    //高新区gaoXin
		    document.getElementById('gaoXin_user_org_data').innerHTML = "0";
		    //吴中区wuZhong
		    document.getElementById('wuZhong_user_org_data').innerHTML = "0";
		    //吴江市wuJiang
		    document.getElementById('wuJiang_user_org_data').innerHTML = "0";
  	 }

  }
}
