package xn.pigfarm.controller.wechat;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.core.util.unityreturn.BuildReturnMapUtil;
import xn.pigfarm.model.backend.CodeListModel;
import xn.pigfarm.model.basicinfo.HouseStatusModel;
import xn.pigfarm.service.wechat.IWechatHouseStatusEveryDayService;
import xn.pigfarm.util.constants.WechatConstants;

/**
 * @Description: 微信端猪舍每日状态相关
 * @author CB
 * @date 2017年8月28日 下午1:36:25
 */
@Component
@Controller
@RequestMapping("/wechatHouseStatusEveryDay")
public class WechatHouseStatusEveryDayController extends BaseController {

    @Autowired
    IWechatHouseStatusEveryDayService wechatHouseStatusEveryDay;

    /**
     * @Description: 根据登录人员 获取对应的猪舍及明细信息--用于编辑页面
     * @author CB
     * @param request()
     * @param response
     * @return
     */
    @RequestMapping("/getHouseList")
    @ResponseBody
    public Map<String, Object> getHouse(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = getMap();
        List<Map<String, Object>> house = wechatHouseStatusEveryDay.gethouse(map);
        return BuildReturnMapUtil.getReturnMap(house);
    }

    /**
     * @Description: 数据录入
     * @author CB
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/editInsertToWechatHouseStatus")
    @ResponseBody
    public Map<String, Object> editInsertToWechatHouseStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> map = getMap();
        List<HouseStatusModel> insertToWechatHouseStatus = wechatHouseStatusEveryDay.editInsertToWechatHouseStatus(map);
        cacheRefresh();
        return BuildReturnMapUtil.getReturnMap(insertToWechatHouseStatus);
    }

    /**
     * @Description: 获取对应7天的数据
     * @author CB
     * @param request(houseId,startDate,endDate)
     * @return
     * @throws Exception
     */
    @RequestMapping("/getHouse7DayByHouseIdAndDate")
    @ResponseBody
    public Map<String, Object> getHouse7DayByHouseIdAndDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = getMap();
        List<Map<String, Object>> house7DayByHouseIdAndDate = wechatHouseStatusEveryDay.getHouse7DayByHouseIdAndDate(map);
        return BuildReturnMapUtil.getReturnMap(house7DayByHouseIdAndDate);
    }

    /**
     * @Description: 猪舍明细查询 -- 每日情况
     * @author CB
     * @param request(companyMark<非本厂必传>,,myFarm<本厂:true,其他:false>,houseId<查询全场则不传>,collectDate,(page,pageSize <分页时同时传参>))
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchWechatHouseStatusByHouseIdAndDate")
    @ResponseBody
    public Map<String, Object> searchWechatHouseStatusByHouseIdAndDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = getMap();
        List<Map<String, Object>> wechatHouseStatusByHouseIdAndDate = wechatHouseStatusEveryDay.searchWechatHouseStatusByHouseIdAndDate(map);
        return BuildReturnMapUtil.getReturnMap(wechatHouseStatusByHouseIdAndDate);
    }

    /**
     * @Description: 查询猪舍状态
     * @author CB
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchWechatHouseStatus")
    @ResponseBody
    public Map<String, Object> searchWechatHouseStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<CodeListModel> wechatHouseStatus = wechatHouseStatusEveryDay.searchWechatHouseFinal(WechatConstants.WECHAT_SWINERY_HEALTH);
        return BuildReturnMapUtil.getReturnMap(wechatHouseStatus);
    }

    /**
     * @Description: 查询猪群健康状况
     * @author CB
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchWechatSwineryHealth")
    @ResponseBody
    public Map<String, Object> searchWechatSwineryHealth(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<CodeListModel> wechatSwineryHealth = wechatHouseStatusEveryDay.searchWechatHouseFinal(WechatConstants.WECHAT_HOUSE_STATUS);
        return BuildReturnMapUtil.getReturnMap(wechatSwineryHealth);
    }

}
