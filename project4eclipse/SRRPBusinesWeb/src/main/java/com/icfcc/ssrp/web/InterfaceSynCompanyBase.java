package com.icfcc.ssrp.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.icfcc.SRRPDao.jpa.entity.companyInfo.CapCompanyInfoWsVo;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyInfoVo;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.inverstorg.TestTagService;
import com.icfcc.ssrp.util.AESUtil;
import com.icfcc.ssrp.util.ExcelUtil;
/**
 * 
* @ClassName: InterfaceSynCompanyBase
* @Description: TODO(只用于金服同步股权失败时，通过此类调用方法用于人工干预同步)
* @author 
* @date 2019年1月26日 下午7:21:22
 */
@RestController
public class InterfaceSynCompanyBase extends SRRPBaseController {
    @Autowired
    protected TestTagService userTagService;
    @Autowired
    private PlatformUserService userService;
    @Autowired
    private CompanyInfoService companyInfoService;
 
    
    
    /**
     * 
    * @Title: SynSzjfCompany
    * @Description: TODO(企业停用所有stopflag为空的企业)
    * @author  
    * @date  2019年1月26日 下午6:32:38
    * @param request
    * @param response
     */
    @RequestMapping(value = "/syn/updateStopflag")
    public void updateStopflag(HttpServletRequest request, HttpServletResponse response){
        Map<Object, Object> map = new HashMap<Object, Object>();
        try{    
            companyInfoService.updateAllStopFlag("1");
            map.put("result", 0);
            map.put("errmsg", "OK");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            map.put("result", 1);
            map.put("errmsg", e.getMessage());
            e.printStackTrace();
        }
        this.writeJson(map, request, response);
    }
    /**
     * 
    * @Title: SynSzjfCompany
    * @Description: TODO(调用股权接口接口同步企业到苏州金服库)
    * @author  
    * @date  2019年1月26日 下午6:32:38
    * @param request
    * @param response
     */
    @RequestMapping(value = "/syn/szjfInsert")
    public void SynSzjfCompany(HttpServletRequest request, HttpServletResponse response){
        Map<Object, Object> map = new HashMap<Object, Object>();
        try{    
            String inertdata="[{\"branchno\":\"320581\",\"code\":\"320581743710679\",\"codetype\":\"2\",\"contaccal\":\"18962309076\",\"contacname\":\"贺艳桃\",\"legalcal\":\"15851585852\",\"legalname\":\"张家明\",\"name\":\"常熟长发装饰材料有限公司\",\"status\":\"2\",\"user_id\":\"320581743710679\"},{\"branchno\":\"320585\",\"code\":\"32058500020160307018\",\"codetype\":\"2\",\"contaccal\":\"13862291629\",\"contacname\":\"何汉朝\",\"legalcal\":\"13915790938\",\"legalname\":\"戴霞林\",\"name\":\"苏州椿盟智能科技有限公司\",\"status\":\"2\",\"user_id\":\"32058500020160307018\"},{\"branchno\":\"320582\",\"code\":\"320582770544813\",\"codetype\":\"2\",\"contaccal\":\"15151569885\",\"contacname\":\"顾鋯芬\",\"legalcal\":\"13606228798\",\"legalname\":\"顾向阳\",\"name\":\"张家港市鑫阳氨纶纱有限公司\",\"status\":\"2\",\"user_id\":\"320582770544813\"},{\"branchno\":\"320582\",\"code\":\"320582672022579\",\"codetype\":\"2\",\"contaccal\":\"15895675636\",\"contacname\":\"赖慧慧\",\"legalcal\":\"15950965151\",\"legalname\":\"尹议\",\"name\":\"张家港乾盛橡胶制品有限公司\",\"status\":\"2\",\"user_id\":\"320582672022579\"},{\"branchno\":\"320585\",\"code\":\"320585302282103\",\"codetype\":\"2\",\"contaccal\":\"13814570941\",\"contacname\":\"陈惠\",\"legalcal\":\"13814570941\",\"legalname\":\"蔡建\",\"name\":\"太仓百诺纳米科技有限公司（弃用）\",\"status\":\"2\",\"user_id\":\"320585302282103\"},{\"branchno\":\"320506\",\"code\":\"320506NA000011X\",\"codetype\":\"2\",\"contaccal\":\"13915550875\",\"contacname\":\"顾建如\",\"legalcal\":\"13901540327\",\"legalname\":\"蒋林根\",\"name\":\"苏州市吴中区金庭镇林屋碧螺春茶叶专业合作社\",\"status\":\"2\",\"user_id\":\"320506NA000011X\"},{\"branchno\":\"320582\",\"code\":\"320582000057825\",\"codetype\":\"2\",\"contaccal\":\"18962282837\",\"contacname\":\"许忠红\",\"legalcal\":\"13328030820\",\"legalname\":\"彭仁清\",\"name\":\"张家港市全盛汽车部件有限公司\",\"status\":\"2\",\"user_id\":\"320582000057825\"},{\"branchno\":\"320506\",\"code\":\"320506000275256\",\"codetype\":\"2\",\"contaccal\":\"13218109760\",\"contacname\":\"童春妹\",\"legalcal\":\"13912620660\",\"legalname\":\"余德山\",\"name\":\"苏州创轩激光科技有限公司\",\"status\":\"2\",\"user_id\":\"320506000275256\"},{\"branchno\":\"320582\",\"code\":\"320582601198659\",\"codetype\":\"2\",\"contaccal\":\"15895691933\",\"contacname\":\"陶笑\",\"legalcal\":\"13511655888\",\"legalname\":\"曾顺德\",\"name\":\"张家港市金港镇南沙明德名贵木材商贸中心\",\"status\":\"2\",\"user_id\":\"320582601198659\"},{\"branchno\":\"320582\",\"code\":\"320582601264044\",\"codetype\":\"2\",\"contaccal\":\"15895691933\",\"contacname\":\"陶笑\",\"legalcal\":\"13151195900\",\"legalname\":\"周清雄\",\"name\":\"张家港市金港镇南沙明德锯板厂\",\"status\":\"2\",\"user_id\":\"320582601264044\"},{\"branchno\":\"320582\",\"code\":\"320582NA000597X\",\"codetype\":\"2\",\"contaccal\":\"18921965101\",\"contacname\":\"苏云飞\",\"legalcal\":\"13862265738\",\"legalname\":\"姚亚彬\",\"name\":\"张家港市乐余镇汇丰源果蔬专业合作社\",\"status\":\"2\",\"user_id\":\"320582NA000597X\"},{\"branchno\":\"320599\",\"code\":\"320594000339382\",\"codetype\":\"2\",\"contaccal\":\"15051559945\",\"contacname\":\"雷芳昌\",\"legalcal\":\"15051559945\",\"legalname\":\"雷芳昌\",\"name\":\"苏州奥拉动漫科技有限公司\",\"status\":\"2\",\"user_id\":\"320594000339382\"},{\"branchno\":\"320582\",\"code\":\"320582000188099\",\"codetype\":\"2\",\"contaccal\":\"15051715187\",\"contacname\":\"吴洁\",\"legalcal\":\"13962280656\",\"legalname\":\"吴洁\",\"name\":\"张家港市嘉鹏工具有限公司\",\"status\":\"2\",\"user_id\":\"320582000188099\"},{\"branchno\":\"320508\",\"code\":\"320502000085901\",\"codetype\":\"2\",\"contaccal\":\"15950012310\",\"contacname\":\"经先生\",\"legalcal\":\"13771989888\",\"legalname\":\"纪艳华\",\"name\":\"苏州市泰富荣合食品有限公司\",\"status\":\"2\",\"user_id\":\"320502000085901\"},{\"branchno\":\"320599\",\"code\":\"320594000456090\",\"codetype\":\"2\",\"contaccal\":\"13306132610\",\"contacname\":\"周亮星\",\"legalcal\":\"13306132610\",\"legalname\":\"周庆富\",\"name\":\"捷尔特国际货运代理（苏州）有限公司\",\"status\":\"2\",\"user_id\":\"320594000456090\"},{\"branchno\":\"320583\",\"code\":\"913205833238143670\",\"codetype\":\"2\",\"contaccal\":\"13906263264\",\"contacname\":\"李节红\",\"legalcal\":\"13906263264\",\"legalname\":\"李节红\",\"name\":\"昆山瑞隆昌精密模具有限公司\",\"status\":\"2\",\"user_id\":\"913205833238143670\"},{\"branchno\":\"320584\",\"code\":\"91320509561799617Q\",\"codetype\":\"2\",\"contaccal\":\"13506253510\",\"contacname\":\"张洪林\",\"legalcal\":\"13506253510\",\"legalname\":\"张洪林\",\"name\":\"吴江市昊田鞋业有限公司\",\"status\":\"2\",\"user_id\":\"91320509561799617Q\"},{\"branchno\":\"320581\",\"code\":\"91320581571366903F\",\"codetype\":\"2\",\"contaccal\":\"13962389535\",\"contacname\":\"邵建群\",\"legalcal\":\"13962389535\",\"legalname\":\"邵建群\",\"name\":\"常熟市萱航纺织品有限公司\",\"status\":\"2\",\"user_id\":\"91320581571366903F\"},{\"branchno\":\"320582\",\"code\":\"913205927279914237\",\"codetype\":\"2\",\"contaccal\":\"15962398872\",\"contacname\":\"宋勤华\",\"legalcal\":\"13906243707\",\"legalname\":\"徐邹东\",\"name\":\"张家港保税区裕佳国际贸易有限公司\",\"status\":\"2\",\"user_id\":\"913205927279914237\"},{\"branchno\":\"320505\",\"code\":\"91320505354539898U\",\"codetype\":\"2\",\"contaccal\":\"13862063676\",\"contacname\":\"黄永\",\"legalcal\":\"13915504388\",\"legalname\":\"唐春贵\",\"name\":\"江苏火米互动科技有限公司\",\"status\":\"2\",\"user_id\":\"91320505354539898U\"},{\"branchno\":\"320582\",\"code\":\"91320582346130048Q\",\"codetype\":\"2\",\"contaccal\":\"18662131668\",\"contacname\":\"顾贤平\",\"legalcal\":\"18662131668\",\"legalname\":\"顾贤平\",\"name\":\"苏州弋格玛贸易有限公司\",\"status\":\"2\",\"user_id\":\"91320582346130048Q\"},{\"branchno\":\"320583\",\"code\":\"913205833311836145\",\"codetype\":\"2\",\"contaccal\":\"18962367894\",\"contacname\":\"朱小辉\",\"legalcal\":\"13764473355\",\"legalname\":\"朱小辉\",\"name\":\"昆山辉海模具有限公司\",\"status\":\"2\",\"user_id\":\"913205833311836145\"},{\"branchno\":\"320599\",\"code\":\"913205943025299037\",\"codetype\":\"2\",\"contaccal\":\"15950059471\",\"contacname\":\"王立平\",\"legalcal\":\"13913522816\",\"legalname\":\"闻陶\",\"name\":\"江苏阅衡智能科技有限公司\",\"status\":\"2\",\"user_id\":\"913205943025299037\"},{\"branchno\":\"320584\",\"code\":\"9132050955587262X5\",\"codetype\":\"2\",\"contaccal\":\"13587938666\",\"contacname\":\"朱岚岚\",\"legalcal\":\"13706720505\",\"legalname\":\"沈建华\",\"name\":\"吴江南联电子有限公司\",\"status\":\"2\",\"user_id\":\"9132050955587262X5\"},{\"branchno\":\"320581\",\"code\":\"913205817786759656\",\"codetype\":\"2\",\"contaccal\":\"13951191635\",\"contacname\":\"蒋方敏\",\"legalcal\":\"13812953585\",\"legalname\":\"陈超\",\"name\":\"苏州贝捷环保设备有限公司\",\"status\":\"2\",\"user_id\":\"913205817786759656\"},{\"branchno\":\"320583\",\"code\":\"91320583338830845P\",\"codetype\":\"2\",\"contaccal\":\"13818832838\",\"contacname\":\"杨明\",\"legalcal\":\"13818832838\",\"legalname\":\"杨明\",\"name\":\"昆山明易汽车饰件有限公司\",\"status\":\"2\",\"user_id\":\"91320583338830845P\"},{\"branchno\":\"320583\",\"code\":\"91320583302284010G\",\"codetype\":\"2\",\"contaccal\":\"15921567310\",\"contacname\":\"肖云华\",\"legalcal\":\"15921567310\",\"legalname\":\"肖云华\",\"name\":\"昆山肖记模具有限公司\",\"status\":\"2\",\"user_id\":\"91320583302284010G\"},{\"branchno\":\"320505\",\"code\":\"913205053137224189\",\"codetype\":\"2\",\"contaccal\":\"13962101093\",\"contacname\":\"孙冰\",\"legalcal\":\"13962101093\",\"legalname\":\"孙冰\",\"name\":\"江苏木润水利科技有限公司\",\"status\":\"2\",\"user_id\":\"913205053137224189\"},{\"branchno\":\"320507\",\"code\":\"91320507741323392A\",\"codetype\":\"2\",\"contaccal\":\"13962147918\",\"contacname\":\"邢兴荣\",\"legalcal\":\"13962147918\",\"legalname\":\"邢兴荣\",\"name\":\"苏州市相城区蠡口名门家具有限公司\",\"status\":\"2\",\"user_id\":\"91320507741323392A\"},{\"branchno\":\"320506\",\"code\":\"91320506593983005Q\",\"codetype\":\"2\",\"contaccal\":\"13616251869\",\"contacname\":\"田红梅\",\"legalcal\":\"13906136349\",\"legalname\":\"何朝阳\",\"name\":\"苏州天奥体育科技有限公司\",\"status\":\"2\",\"user_id\":\"91320506593983005Q\"},{\"branchno\":\"320584\",\"code\":\"91320509752743480P\",\"codetype\":\"2\",\"contaccal\":\"13706259016\",\"contacname\":\"尚洪\",\"legalcal\":\"13706259016\",\"legalname\":\"尚洪\",\"name\":\"吴江市昌盛煤炭有限公司\",\"status\":\"2\",\"user_id\":\"91320509752743480P\"},{\"branchno\":\"320584\",\"code\":\"9132050967441834X2\",\"codetype\":\"2\",\"contaccal\":\"13701555593\",\"contacname\":\"徐克霞\",\"legalcal\":\"13506251066\",\"legalname\":\"陈雪龙\",\"name\":\"苏州市森叶纺织品有限公司\",\"status\":\"2\",\"user_id\":\"9132050967441834X2\"},{\"branchno\":\"320584\",\"code\":\"91320509718622511W\",\"codetype\":\"2\",\"contaccal\":\"13776151555\",\"contacname\":\"费建荣\",\"legalcal\":\"13776151555\",\"legalname\":\"费建荣\",\"name\":\"吴江市晓刚彩板制造有限公司\",\"status\":\"2\",\"user_id\":\"91320509718622511W\"},{\"branchno\":\"320584\",\"code\":\"9132050973651563XE\",\"codetype\":\"2\",\"contaccal\":\"13706256579\",\"contacname\":\"董小奎\",\"legalcal\":\"13706256579\",\"legalname\":\"董小奎\",\"name\":\"苏州懂诚面料科研有限公司\",\"status\":\"2\",\"user_id\":\"9132050973651563XE\"},{\"branchno\":\"320584\",\"code\":\"91320509757964442L\",\"codetype\":\"2\",\"contaccal\":\"13606250099\",\"contacname\":\"金燕飞\",\"legalcal\":\"13606250099\",\"legalname\":\"金燕飞\",\"name\":\"吴?鹚栈煊邢薰?\",\"status\":\"2\",\"user_id\":\"91320509757964442L\"},{\"branchno\":\"320584\",\"code\":\"913205096608208993\",\"codetype\":\"2\",\"contaccal\":\"13656255776\",\"contacname\":\"杨建华\",\"legalcal\":\"13656255776\",\"legalname\":\"杨建华\",\"name\":\"吴江福耀纺织品有限公司\",\"status\":\"2\",\"user_id\":\"913205096608208993\"},{\"branchno\":\"320584\",\"code\":\"91320509681113149C\",\"codetype\":\"2\",\"contaccal\":\"13906250355\",\"contacname\":\"钱栋良\",\"legalcal\":\"13906250355\",\"legalname\":\"钱栋良\",\"name\":\"吴江市鑫联良纺织有限公司\",\"status\":\"2\",\"user_id\":\"91320509681113149C\"},{\"branchno\":\"320584\",\"code\":\"91320509727265018C\",\"codetype\":\"2\",\"contaccal\":\"13906254579\",\"contacname\":\"沈桂泉\",\"legalcal\":\"13906254579\",\"legalname\":\"沈雪林\",\"name\":\"苏州亿仕佳型钢科技有限公司\",\"status\":\"2\",\"user_id\":\"91320509727265018C\"},{\"branchno\":\"320584\",\"code\":\"913205097500162986\",\"codetype\":\"2\",\"contaccal\":\"13901555557\",\"contacname\":\"钱红英\",\"legalcal\":\"13901555557\",\"legalname\":\"钱红英\",\"name\":\"吴江市华捷煤建物贸有限公司\",\"status\":\"2\",\"user_id\":\"913205097500162986\"},{\"branchno\":\"320584\",\"code\":\"913205096902559749\",\"codetype\":\"2\",\"contaccal\":\"18962553468\",\"contacname\":\"刘翠霞\",\"legalcal\":\"18962553468\",\"legalname\":\"刘翠霞\",\"name\":\"吴江市嘉裕纺织有限公司\",\"status\":\"2\",\"user_id\":\"913205096902559749\"},{\"branchno\":\"320584\",\"code\":\"9132050974068644X1\",\"codetype\":\"2\",\"contaccal\":\"13901559700\",\"contacname\":\"董震\",\"legalcal\":\"13901559700\",\"legalname\":\"董震\",\"name\":\"吴江市华盛电子服饰有限公司\",\"status\":\"2\",\"user_id\":\"9132050974068644X1\"},{\"branchno\":\"320584\",\"code\":\"91320509660074099L\",\"codetype\":\"2\",\"contaccal\":\"13706254846\",\"contacname\":\"肖伟民\",\"legalcal\":\"15962588716\",\"legalname\":\"肖伟民\",\"name\":\"吴江鑫隆锦绣喷气织造有限公司\",\"status\":\"2\",\"user_id\":\"91320509660074099L\"},{\"branchno\":\"320599\",\"code\":\"91320000792304028X\",\"codetype\":\"2\",\"contaccal\":\"15950180440\",\"contacname\":\"尹朋程\",\"legalcal\":\"18912795067\",\"legalname\":\"徐伟\",\"name\":\"江苏神彩科技股份有限公司\",\"status\":\"2\",\"user_id\":\"91320000792304028X\"},{\"branchno\":\"320584\",\"code\":\"9132050969786772X7\",\"codetype\":\"2\",\"contaccal\":\"13906255951\",\"contacname\":\"沈菊\",\"legalcal\":\"13906255951\",\"legalname\":\"沈菊\",\"name\":\"苏州帛乐纺织有限公司\",\"status\":\"2\",\"user_id\":\"9132050969786772X7\"},{\"branchno\":\"320582\",\"code\":\"913205926993770970\",\"codetype\":\"2\",\"contaccal\":\"13901567128\",\"contacname\":\"吴正球\",\"legalcal\":\"13901567128\",\"legalname\":\"吴正球\",\"name\":\"张家港保税区源恒国际贸易有限公司\",\"status\":\"2\",\"user_id\":\"913205926993770970\"},{\"branchno\":\"320507\",\"code\":\"91320500776444231L\",\"codetype\":\"2\",\"contaccal\":\"13405062415\",\"contacname\":\"贺飞亚\",\"legalcal\":\"13812773333\",\"legalname\":\"贺飞亚\",\"name\":\"苏州汇弘电器有限公司\",\"status\":\"2\",\"user_id\":\"91320500776444231L\"},{\"branchno\":\"320507\",\"code\":\"91320507663297837M\",\"codetype\":\"2\",\"contaccal\":\"13338696635\",\"contacname\":\"冯明\",\"legalcal\":\"13338696635\",\"legalname\":\"冯明\",\"name\":\"苏州市景成纸业有限公司\",\"status\":\"2\",\"user_id\":\"91320507663297837M\"},{\"branchno\":\"320584\",\"code\":\"913205092512996680\",\"codetype\":\"2\",\"contaccal\":\"13906257079\",\"contacname\":\"傅行宏\",\"legalcal\":\"13906257079\",\"legalname\":\"傅行宏\",\"name\":\"苏州市泰山经贸发展有限公司\",\"status\":\"2\",\"user_id\":\"913205092512996680\"},{\"branchno\":\"320507\",\"code\":\"91320507X322813107\",\"codetype\":\"2\",\"contaccal\":\"13906201215\",\"contacname\":\"张祥元\",\"legalcal\":\"13906201215\",\"legalname\":\"张祥元\",\"name\":\"江苏张氏航空精塑有限公司\",\"status\":\"2\",\"user_id\":\"91320507X322813107\"},{\"branchno\":\"320507\",\"code\":\"913205006081993538\",\"codetype\":\"2\",\"contaccal\":\"15262481112\",\"contacname\":\"田卫锋\",\"legalcal\":\"13706211593\",\"legalname\":\"陈锦魁\",\"name\":\"苏州沙特卡铸造有限公司\",\"status\":\"2\",\"user_id\":\"913205006081993538\"},{\"branchno\":\"320584\",\"code\":\"91320509735707453A\",\"codetype\":\"2\",\"contaccal\":\"13606259371\",\"contacname\":\"史国强\",\"legalcal\":\"13606259371\",\"legalname\":\"史国强\",\"name\":\"吴江祥尔润纺织有限公司\",\"status\":\"2\",\"user_id\":\"91320509735707453A\"},{\"branchno\":\"320584\",\"code\":\"913205095593103093\",\"codetype\":\"2\",\"contaccal\":\"13962558388\",\"contacname\":\"周寅\",\"legalcal\":\"13962558388\",\"legalname\":\"周寅\",\"name\":\"吴江市顺泰纺织有限公司\",\"status\":\"2\",\"user_id\":\"913205095593103093\"},{\"branchno\":\"320584\",\"code\":\"913205097953640692\",\"codetype\":\"2\",\"contaccal\":\"13801556555\",\"contacname\":\"薛强\",\"legalcal\":\"13801556555\",\"legalname\":\"薛强\",\"name\":\"吴江市龙宇精密五金机械有限公司\",\"status\":\"2\",\"user_id\":\"913205097953640692\"},{\"branchno\":\"320584\",\"code\":\"913205097584921765\",\"codetype\":\"2\",\"contaccal\":\"13806254971\",\"contacname\":\"杨培丰\",\"legalcal\":\"13806254971\",\"legalname\":\"杨培丰\",\"name\":\"吴江临翔钢构净化有限公司\",\"status\":\"2\",\"user_id\":\"913205097584921765\"},{\"branchno\":\"320584\",\"code\":\"913205095781123755\",\"codetype\":\"2\",\"contaccal\":\"13205163123\",\"contacname\":\"陈火根\",\"legalcal\":\"13205163123\",\"legalname\":\"陈火根\",\"name\":\"吴江昊丽文纺织有限公司\",\"status\":\"2\",\"user_id\":\"913205095781123755\"},{\"branchno\":\"320585\",\"code\":\"91320585573847647R\",\"codetype\":\"2\",\"contaccal\":\"18936159120\",\"contacname\":\"王叶超\",\"legalcal\":\"18936159120\",\"legalname\":\"王叶超\",\"name\":\"太仓琪威金属制品有限公司\",\"status\":\"2\",\"user_id\":\"91320585573847647R\"},{\"branchno\":\"320507\",\"code\":\"91320507573775903H\",\"codetype\":\"2\",\"contaccal\":\"13739171956\",\"contacname\":\"黄立刚\",\"legalcal\":\"13739171956\",\"legalname\":\"黄立刚\",\"name\":\"苏州科尔珀恩机械科技有限公司\",\"status\":\"2\",\"user_id\":\"91320507573775903H\"},{\"branchno\":\"320581\",\"code\":\"91320581585547457C\",\"codetype\":\"2\",\"contaccal\":\"13915663131\",\"contacname\":\"王正平\",\"legalcal\":\"13915663131\",\"legalname\":\"王正平\",\"name\":\"苏州市韩铭金属制品有限公司\",\"status\":\"2\",\"user_id\":\"91320581585547457C\"},{\"branchno\":\"320582\",\"code\":\"91320582718543899R\",\"codetype\":\"2\",\"contaccal\":\"13584423279\",\"contacname\":\"顾建新\",\"legalcal\":\"13906245356\",\"legalname\":\"顾建达\",\"name\":\"张家港市银达塑料有限公司\",\"status\":\"2\",\"user_id\":\"91320582718543899R\"},{\"branchno\":\"320582\",\"code\":\"91320582693372383T\",\"codetype\":\"2\",\"contaccal\":\"13812988688\",\"contacname\":\"庞蕊\",\"legalcal\":\"13901729197\",\"legalname\":\"蒋伟\",\"name\":\"张家港市旭骏机械部件有限公司\",\"status\":\"2\",\"user_id\":\"91320582693372383T\"},{\"branchno\":\"320599\",\"code\":\"91320594551191472U\",\"codetype\":\"2\",\"contaccal\":\"15306211291\",\"contacname\":\"邵莉莉\",\"legalcal\":\"15306211291\",\"legalname\":\"WOUTERFRANSBARENDRECHT\",\"name\":\"捷耐传动（苏州）有限公司\",\"status\":\"2\",\"user_id\":\"91320594551191472U\"},{\"branchno\":\"320582\",\"code\":\"9132058225153383XX\",\"codetype\":\"2\",\"contaccal\":\"13906249081\",\"contacname\":\"丁国平\",\"legalcal\":\"13906249081\",\"legalname\":\"丁国平\",\"name\":\"张家港市固达特种工具有限公司\",\"status\":\"2\",\"user_id\":\"9132058225153383XX\"},{\"branchno\":\"320582\",\"code\":\"91320582593969289N\",\"codetype\":\"2\",\"contaccal\":\"13914908828\",\"contacname\":\"陶叶\",\"legalcal\":\"13914908828\",\"legalname\":\"陶叶\",\"name\":\"张家港市瑞轩贸易有限公司\",\"status\":\"2\",\"user_id\":\"91320582593969289N\"},{\"branchno\":\"320584\",\"code\":\"91320509MA1MB48E5Q\",\"codetype\":\"2\",\"contaccal\":\"18013738232\",\"contacname\":\"周萌波\",\"legalcal\":\"18013738232\",\"legalname\":\"周萌波\",\"name\":\"苏州重阳源电子科技有限公司\",\"status\":\"2\",\"user_id\":\"91320509MA1MB48E5Q\"},{\"branchno\":\"320505\",\"code\":\"91320505MA1MAPMD8J\",\"codetype\":\"2\",\"contaccal\":\"15062567130\",\"contacname\":\"陶成然\",\"legalcal\":\"15062567130\",\"legalname\":\"陶成然\",\"name\":\"苏州梅克兰供应链管理有限公司\",\"status\":\"2\",\"user_id\":\"91320505MA1MAPMD8J\"},{\"branchno\":\"320507\",\"code\":\"91320507MA1MCYX159\",\"codetype\":\"2\",\"contaccal\":\"18550527285\",\"contacname\":\"陆志豪\",\"legalcal\":\"13814958332\",\"legalname\":\"吴徐昊\",\"name\":\"苏州昊天远景金属制品有限公司\",\"status\":\"2\",\"user_id\":\"91320507MA1MCYX159\"},{\"branchno\":\"320507\",\"code\":\"9132050774373630XQ\",\"codetype\":\"2\",\"contaccal\":\"13806207293\",\"contacname\":\"曹振华\",\"legalcal\":\"13806207293\",\"legalname\":\"曹振华\",\"name\":\"苏州康博电子有限公司\",\"status\":\"2\",\"user_id\":\"9132050774373630XQ\"},{\"branchno\":\"320585\",\"code\":\"913205853137019592\",\"codetype\":\"2\",\"contaccal\":\"15365321095\",\"contacname\":\"赵中英\",\"legalcal\":\"18606223118\",\"legalname\":\"赵中英\",\"name\":\"苏州德宝凯迪新材料有限公司\",\"status\":\"2\",\"user_id\":\"913205853137019592\"},{\"branchno\":\"320599\",\"code\":\"913205943022606682\",\"codetype\":\"2\",\"contaccal\":\"13306210599\",\"contacname\":\"梁艳\",\"legalcal\":\"13306210599\",\"legalname\":\"徐丹夫\",\"name\":\"苏州世纪福软件科技有限公司\",\"status\":\"2\",\"user_id\":\"913205943022606682\"},{\"branchno\":\"320506\",\"code\":\"91320506660842801K\",\"codetype\":\"2\",\"contaccal\":\"17601579877\",\"contacname\":\"傅莉雯\",\"legalcal\":\"13913152006\",\"legalname\":\"徐建\",\"name\":\"苏州鼎锐机电商贸有限公司\",\"status\":\"2\",\"user_id\":\"91320506660842801K\"},{\"branchno\":\"320506\",\"code\":\"91320506553845767H\",\"codetype\":\"2\",\"contaccal\":\"18020253807\",\"contacname\":\"钱丛刚\",\"legalcal\":\"18020253807\",\"legalname\":\"钱丛刚\",\"name\":\"苏州艾斯凯自动化设备有限公司\",\"status\":\"2\",\"user_id\":\"91320506553845767H\"},{\"branchno\":\"320582\",\"code\":\"91320582MA1MAY6JXN\",\"codetype\":\"2\",\"contaccal\":\"13506222776\",\"contacname\":\"丁利平\",\"legalcal\":\"13506222776\",\"legalname\":\"丁利平\",\"name\":\"张家港市恒宇饮料机械有限公司\",\"status\":\"2\",\"user_id\":\"91320582MA1MAY6JXN\"},{\"branchno\":\"320507\",\"code\":\"91320507MA1MBPD69M\",\"codetype\":\"2\",\"contaccal\":\"13646225261\",\"contacname\":\"韩留\",\"legalcal\":\"13806215742\",\"legalname\":\"钟群明\",\"name\":\"苏州隆威能体育用品有限公司\",\"status\":\"2\",\"user_id\":\"91320507MA1MBPD69M\"},{\"branchno\":\"320505\",\"code\":\"91320505MA1MKR503Y\",\"codetype\":\"2\",\"contaccal\":\"13914029315\",\"contacname\":\"吕秀兰\",\"legalcal\":\"13701548390\",\"legalname\":\"江惠兴\",\"name\":\"苏州兆科信通电子有限公司\",\"status\":\"2\",\"user_id\":\"91320505MA1MKR503Y\"},{\"branchno\":\"320582\",\"code\":\"91320582323533223U\",\"codetype\":\"2\",\"contaccal\":\"13601565623\",\"contacname\":\"肖铁良\",\"legalcal\":\"13601565623\",\"legalname\":\"肖铁良\",\"name\":\"张家港正汇汽车配件有限公司\",\"status\":\"2\",\"user_id\":\"91320582323533223U\"},{\"branchno\":\"320583\",\"code\":\"91320583MA1MKM4971\",\"codetype\":\"2\",\"contaccal\":\"13405182536\",\"contacname\":\"赵书英\",\"legalcal\":\"18936115873\",\"legalname\":\"宋生宏\",\"name\":\"昆山百澜电子有限公司\",\"status\":\"2\",\"user_id\":\"91320583MA1MKM4971\"},{\"branchno\":\"320505\",\"code\":\"91320508678981623B\",\"codetype\":\"2\",\"contaccal\":\"18626157200\",\"contacname\":\"罗怡婧\",\"legalcal\":\"69169991\",\"legalname\":\"王凌波\",\"name\":\"江苏淼升轨道科技有限公司\",\"status\":\"2\",\"user_id\":\"91320508678981623B\"},{\"branchno\":\"320583\",\"code\":\"91320583330860660L\",\"codetype\":\"2\",\"contaccal\":\"18917603622\",\"contacname\":\"朱秀芳\",\"legalcal\":\"18917603622\",\"legalname\":\"陈钢\",\"name\":\"苏州和庚丽塑胶科技有限公司\",\"status\":\"2\",\"user_id\":\"91320583330860660L\"},{\"branchno\":\"320506\",\"code\":\"91320506783359027A\",\"codetype\":\"2\",\"contaccal\":\"13073377332\",\"contacname\":\"周素菊\",\"legalcal\":\"18015555000\",\"legalname\":\"林焕城\",\"name\":\"苏州德机自动化科技有限公司\",\"status\":\"2\",\"user_id\":\"91320506783359027A\"},{\"branchno\":\"320583\",\"code\":\"91320583579501831P\",\"codetype\":\"2\",\"contaccal\":\"13812888266\",\"contacname\":\"吴晓金\",\"legalcal\":\"13812888266\",\"legalname\":\"聂得劲\",\"name\":\"昆山睿力得自动化技术有限公司\",\"status\":\"2\",\"user_id\":\"91320583579501831P\"},{\"branchno\":\"320506\",\"code\":\"913205067605159472\",\"codetype\":\"2\",\"contaccal\":\"15952406261\",\"contacname\":\"袁静\",\"legalcal\":\"15952406261\",\"legalname\":\"蒋元生\",\"name\":\"苏州华洋汽车销售服务有限公司\",\"status\":\"2\",\"user_id\":\"913205067605159472\"},{\"branchno\":\"320585\",\"code\":\"91320585MA1MFB8K5H\",\"codetype\":\"2\",\"contaccal\":\"18913760965\",\"contacname\":\"马述伟\",\"legalcal\":\"18913760965\",\"legalname\":\"马述伟\",\"name\":\"疆合材料科技（苏州）有限公司\",\"status\":\"2\",\"user_id\":\"91320585MA1MFB8K5H\"},{\"branchno\":\"320507\",\"code\":\"91320594MA1MKGER1H\",\"codetype\":\"2\",\"contaccal\":\"13372170400\",\"contacname\":\"季立\",\"legalcal\":\"13606135503\",\"legalname\":\"季立\",\"name\":\"苏州爱开客信息技术有限公司\",\"status\":\"2\",\"user_id\":\"91320594MA1MKGER1H\"},{\"branchno\":\"320505\",\"code\":\"08222218-6\",\"codetype\":\"1\",\"contaccal\":\"18000000000\",\"contacname\":\"李安旭\",\"legalcal\":\"17610072635\",\"legalname\":\"不会修改\",\"name\":\"注册企业用户测试修改企业名称\",\"status\":\"2\",\"user_id\":\"08222218-6\"}]";
            List<CompanyInfoVo> CompanyInfoVoResults = (List<CompanyInfoVo>) JSON.parseArray(inertdata, CompanyInfoVo.class);
            for(CompanyInfoVo companyRestVo:CompanyInfoVoResults ){
                Map maps = userService.getCompanyInfo(companyRestVo);
                System.out.println("调用同步接口结束，返回结果:" + maps.get("validStatus"));
            }
            map.put("result", 0);
            map.put("errmsg", "OK");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            map.put("result", 1);
            map.put("errmsg", e.getMessage());
            e.printStackTrace();
        }
        this.writeJson(map, request, response);
    }
    /**
     * 
    * @Title: updateCompany
    * @Description: TODO(调用股权接口更新企业信息)
    * @author  
    * @date  2019年1月26日 下午6:44:24
    * @param request
    * @param response
     */
    @RequestMapping(value = "/syn/szjfUpdate")
    public void updateCompany(HttpServletRequest request, HttpServletResponse response){
        Map<Object, Object> map = new HashMap<Object, Object>();
        try{    
          String updatedata="[{\"code\":\"91320594732512984U\",\"name\":\"苏州市金来达科技有限公司\",\"oldCode\":\"91320594732512984U\",\"status\":\"2\",\"user_id\":\"91320594732512984U\"},{\"code\":\"913205827863355497\",\"name\":\"水立通建设（江苏）有限公司\",\"oldCode\":\"913205827863355497\",\"status\":\"2\",\"user_id\":\"913205827863355497\"},{\"code\":\"913205097673868341\",\"name\":\"吴江诚益机械铸造厂\",\"oldCode\":\"913205097673868341\",\"status\":\"2\",\"user_id\":\"913205097673868341\"},{\"code\":\"913205822515638876\",\"name\":\"张家港市鸿运机械制造有限公司\",\"oldCode\":\"913205822515638876\",\"status\":\"2\",\"user_id\":\"913205822515638876\"},{\"code\":\"66577487-9\",\"name\":\"江苏丰泰涂装技术有限公司\",\"oldCode\":\"66577487-9\",\"status\":\"2\",\"user_id\":\"66577487-9\"},{\"code\":\"74371896-1\",\"name\":\"吴江申通企业发展有限公司\",\"oldCode\":\"913205097437189616\",\"status\":\"2\",\"user_id\":\"74371896-1\"}]";
            List<CompanyInfoVo> CompanyInfoVoResults = (List<CompanyInfoVo>) JSON.parseArray(updatedata, CompanyInfoVo.class);
            for(CompanyInfoVo companyRestVo:CompanyInfoVoResults ){
                Map maps = userService.updateCompanyInfo(companyRestVo);
                System.out.println("调用更新接口结束，返回结果:" + maps.get("validStatus"));
            }
            map.put("result", 0);
            map.put("errmsg", "OK");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            map.put("result", 1);
            map.put("errmsg", e.getMessage());
            e.printStackTrace();
        }
        this.writeJson(map, request, response);
    }
   
    public static String readExcell(String filePath) throws FileNotFoundException, IOException { 
        File file = new File(filePath);
        String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
        Workbook wb;
        if ("xls".equals(suffix)) {
            wb = new HSSFWorkbook(new FileInputStream( file));
        } else if ("xlsx".equals(suffix)) {
            wb = new XSSFWorkbook(new FileInputStream( file));
        } else {
            System.out.println("文件类型错误");
            return "文件类型错误";
        }
        int sheetNum=0;//要解析excle文件的当前sheet
        String[][] result;  
        String data="";
        try {
        result = ExcelUtil.getData(file, 1, sheetNum,wb);
        int rowLength = result.length;
        List<CompanyInfoVo> list =new ArrayList<CompanyInfoVo>();
        for(int i=0;i<rowLength;i++) {
            CompanyInfoVo companyRestVo = new CompanyInfoVo();
                if(!StringUtils.isEmpty(result[i][0])){//企业名称
                    companyRestVo.setName(result[i][0]);
                }
                if(!StringUtils.isEmpty(result[i][1])){//企业证件号
                    companyRestVo.setUser_id(result[i][1]);
                    companyRestVo.setCode(result[i][1]);
                }
                if(!StringUtils.isEmpty(result[i][2])){//企业证件类型
                    companyRestVo.setCodetype(result[i][2]);
                }
                if(!StringUtils.isEmpty(result[i][3])){//法人代表
    
                    companyRestVo.setLegalname(result[i][3]);
                }
                if(!StringUtils.isEmpty(result[i][4])){//法人代表联系方式
                    companyRestVo.setLegalcal(result[i][4]);
               }
                if(!StringUtils.isEmpty(result[i][5])){//联系人姓名
                    companyRestVo.setContacname(result[i][5]);
                }
                if(!StringUtils.isEmpty(result[i][6])){//联系人手机号
                    companyRestVo.setContaccal(result[i][6]);
                }
                if(!StringUtils.isEmpty(result[i][7])){//所属区域
                    companyRestVo.setBranchno(result[i][7]);
                }
                if(!StringUtils.isEmpty(companyRestVo)){
                    companyRestVo.setStatus("2");
                    list.add(companyRestVo);
                }

        }
        data=JSON.toJSONString(list);
        }catch(Exception e) {
            e.printStackTrace();
        }
      
       return data;
    }
    //将对象转json Main方法，输出json字符串作为参数将用于调用以上同步接口方法，同步企业信息
    public static void main(String arg[]){
        //1.批量企业，解析excel
        String filePath="E:/Desktop/苏州股权融资服务平台/9.其他文档/生产数据文档/同步股权库企业名单.xlsx";
        try {
        String dataJson=readExcell(filePath);
        System.out.println("解析后的字符串："+dataJson);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
}
