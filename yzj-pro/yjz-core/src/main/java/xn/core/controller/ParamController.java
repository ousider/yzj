package xn.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xn.core.model.system.ButtonModel;
import xn.core.model.system.MenuView;
import xn.core.service.IParamService;
import xn.core.util.TreeUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.unityreturn.BuildReturnMapUtil;

/**
 * @Description:页面组件（下拉框等）取值控制层
 * @author Zhangjc
 * @date 2016年4月14日 上午9:10:02
 */
@Controller
@RequestMapping("/param")
public class ParamController extends BaseController {

    @Autowired
    private IParamService paramService;

    /**
     * @Description:查询菜单
     * @author
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchMenuByUserId.do")
    @ResponseBody
    public Map<String, Object> searchMenuByUserId(HttpServletRequest request) throws Exception {
        String menuType = getString("menuType");
        if (menuType == null) {
            menuType = "1";
        }
        Map<String, Object> map = paramService.searchMenuByUserId(menuType);
        List<MenuView> list = (List<MenuView>) map.get("menus");
        List<Map<String, Object>> menus = TreeUtil.getMenuTreeList(list);
        map.put("menus", menus);

        // SidebarUtil util = new SidebarUtil();
        // List<MenuView> list = util.loadSidebar();
        // map.put("menus", TreeUtil.getMenuTreeList(list));
        return getReturnMap(map);
    }
    
    /**
     * 查询菜单
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchMenuBobox.do")
    @ResponseBody
    public Map<String, Object> searchMenuBobox(HttpServletRequest request) throws Exception {

        return getReturnMap(paramService.searchMenuBobox());
    }

    /**
     * @Description:
     * @author
     * @param typeCode
     * @param linkValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchByTypeCode")
    @ResponseBody
    public List<Map<String, String>> searchByTypeCode(HttpServletRequest request) throws Exception {

        String typeCode = getString("typeCode");
        String linkValue = getString("linkValue");

        List<Map<String, String>> cdCodeList = CacheUtil.getComboCodeList(typeCode, linkValue);
        return cdCodeList;
    }

    /**
     * @Description: (根据用户Id查询有权限的按钮)
     * @author
     * @param moduleId
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchButtonByUserId")
    @ResponseBody
    public Map<String, Object> searchButtonByRole() throws Exception {

        List<ButtonModel> list = new ArrayList<ButtonModel>();
        String buttonCode[] = new String[] { "add", "edit", "view", "delete", "copAdd" };
        String buttonName[] = new String[] { "新增", "编辑", "查看", "删除", "复制新增" };
        String buttonFunName[] = new String[] { "onBtnAdd", "onBtnEdit", "onBtnView", "onBtnDelete", "onBtnCopyAdd" };
        String buttonIconCls[] = new String[] { "icon-add", "icon-edit", "icon-edit", "icon-remove", "icon-cut" };
        for (int i = 0; i < 5; i++) {
            ButtonModel button = new ButtonModel();
            button.setBtnCode(buttonCode[i]);
            button.setBtnName(buttonName[i]);
            button.setFunName(buttonFunName[i]);
            button.setIconCls(buttonIconCls[i]);
            list.add(button);
        }
        return BuildReturnMapUtil.getReturnMap(list);
    }

    @RequestMapping("/forwardIframe.do")
    @ResponseBody
    public ModelAndView forwardIframe(String url, String fontSize) throws Exception {

        Map<String, String> name = new HashMap<String, String>();
        name.put("FONT_SIZE", fontSize);
        ModelAndView view = new ModelAndView(url, name);
        return view;
    }

    @RequestMapping("/getSettingValueByCode.do")
    @ResponseBody
    public Map<String, Object> getSettingValueByCode(String settingCode) throws Exception {

        return BuildReturnMapUtil.getReturnMap(paramService.getSettingValueByCode(settingCode));
    }

    // /**
    // * @Description: 获取首页需要的信息
    // * @author Zhangjc
    // * @param settingCode
    // * @return
    // * @throws Exception
    // */
    // @RequestMapping("/searchMainInfo.do")
    // @ResponseBody
    // public Map<String, Object> searchMainInfo(HttpServletRequest request) throws Exception {
    //
    // String farmName = getUserDetail().getFarmName();
    // String employName = getUserDetail().getEmployName();
    // Map<String, Object> name = new HashMap<String, Object>();
    // name.put("farmName", farmName);
    // name.put("employName", employName);
    // Map<String, Object> result = getReturnMap();
    // result.putAll(name);
    // return result;
    // }
    /**
     * @Description: 刷新猪场缓存 所有猪场
     * @author zhangjs
     * @return
     * @throws Exception
     */
    @RequestMapping("/refreshCache.do")
    @ResponseBody
    public Map<String, Object> refreshCache() throws Exception {
        cacheRefresh(false);
        return getReturnMap();
    }

    /**
     * @Description: 刷新猪场缓存 所有猪场
     * @author zhangjs
     * @return
     * @throws Exception
     */
    @RequestMapping("/refreshBasic.do")
    @ResponseBody
    public Map<String, Object> refreshBasic() throws Exception {
        basicRefresh(false);
        return getReturnMap();
    }

    /**
     * @Description: 刷新猪场缓存 所有猪场
     * @author zhangjs
     * @return
     * @throws Exception
     */
    @RequestMapping("/refreshPigInfo.do")
    @ResponseBody
    public Map<String, Object> refreshPigInfo() throws Exception {
        pigInfoRefresh(false);
        return getReturnMap();
    }

    /**
     * @Description: 获取猪场周次信息
     * @author zhangjs
     * @return
     * @throws Exception
     */
    @RequestMapping("/getWeekInfo.do")
    @ResponseBody
    public Map<String, Object> getWeekInfo(HttpServletRequest request) throws Exception {
        
        return getReturnMap(paramService.getWeekInfo(getMap()));
    }

    /**
     * @Description: 获取新农+微信平台首页ID
     * @author Cress
     * @return
     * @throws Exception
     */
    @RequestMapping("/getXnplusWechatHomeModule.do")
    @ResponseBody
    public Map<String, Object> getXnplusWechatHomeModule() throws Exception {

        return getReturnMap(paramService.getXnplusWechatHomeModule());
    }

    @RequestMapping("/getXnplusWechatMenuListByType.do")
    @ResponseBody
    public Map<String, Object> getXnplusWechatMenuListByType(HttpServletRequest request) throws Exception {

        return getReturnMap(paramService.getXnplusWechatMenuListByType(getMap()));
    }
    /**
     * @Description: 更新提醒
     * @author hxj
     * @return
     * @throws Exception
     */
    @RequestMapping("/getUpdateContent")
    @ResponseBody
    public Map<String, Object> getUpdateContent() throws Exception {
        return getReturnMap(paramService.getUpdateContent());

    }    
}
