package xn.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.mapper.base.IParamMapper;
import xn.core.mapper.portal.CoreUserMapper;
import xn.core.mapper.system.ActionTrackMapper;
import xn.core.mapper.system.IpAddressesMapper;
import xn.core.model.system.ActionTrackModel;
import xn.core.model.system.IpAddressesModel;
import xn.core.service.IActionTrackService;
import xn.core.util.AddressUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.time.TimeUtil;

@Service("ActionTrackService")
public class ActionTrackServiceImpl extends BaseServiceImpl implements IActionTrackService {

    @Autowired
    private ActionTrackMapper actionTrackMapper;

    @Autowired
    private CoreUserMapper coreUserMapper;

    @Autowired
    private IpAddressesMapper ipAddressesMapper;

    @Autowired
    private IParamMapper paramMapper;

    @Override
    public void saveGuestInfro(String ip, ActionTrackModel atm) throws Exception {
        
        // 先查询本地数据库获取地址相关信息
        IpAddressesModel model = ipAddressesMapper.searchById(ip);
        if (model != null) {
            atm.setCountry(model.getIpCountry());
            atm.setProvince(model.getIpProvince());
            atm.setCity(model.getIpCity());
            atm.setArea(model.getIpCounty());
        }
        // 没有再去淘宝那查询
        else {
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                model = new IpAddressesModel();
                model.setIp(ip);
                model.setDeletedFlag(CommonConstants.DELETED_FLAG);
                model.setStatus(CommonConstants.STATUS);
                model.setCreateId(getEmployeeId());
                model.setCreateTime(TimeUtil.getSysTimestamp());
                model.setIpCountry("本机访问ip");
                ipAddressesMapper.insert(model);
            } else {
                Map<String, String> addrmap = AddressUtil.getAddresses(ip, "utf-8");
                if (addrmap != null && !addrmap.isEmpty()) {
                    atm.setCountry(addrmap.get("country"));
                    atm.setProvince(addrmap.get("region"));
                    atm.setCity(addrmap.get("city"));
                    atm.setArea(addrmap.get("county"));

                    model = new IpAddressesModel();
                    model.setIp(ip);
                    model.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    model.setStatus(CommonConstants.STATUS);
                    model.setIpArea(addrmap.get("area"));
                    model.setIpCity(addrmap.get("city"));
                    model.setIpCountry(addrmap.get("country"));
                    model.setIpIsp(addrmap.get("isp"));
                    model.setIpProvince(addrmap.get("region"));
                    model.setIpCounty(addrmap.get("county"));
                    model.setCreateId(getEmployeeId());
                    model.setCreateTime(TimeUtil.getSysTimestamp());
                    ipAddressesMapper.insert(model);
                }
            }

        }
        actionTrackMapper.insert(atm);
    }

    @Override
    public void eidtPassword(String password) {
        coreUserMapper.editPassword(getUserId(), password);
    }

    @Override
    public List<Map<String, Object>> checkOpenId(String openId, String checkType) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT * FROM hr_m_user WHERE STATUS = '1' AND DELETED_FLAG = '0'");
        if (checkType.equals("QQ")) {
            sqlCon.addCondition(openId, " AND QQ_OPEN_ID = ?");
        }
        if (checkType.equals("wechat")) {
            sqlCon.addCondition(openId, " AND WECHAT_OPEN_ID = ?");
        }
        if (checkType.equals("QYwechat")) {
            sqlCon.addCondition(openId, " AND QY_OPEN_ID = ?");
        }
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        return list;
    }

    @Override
    public void updateOpenId(long userId, String oppenId, String bindType) throws Exception {
        if (bindType.equals("QQ")) {
            coreUserMapper.updateQQOpenId(userId, oppenId);
        }
        if (bindType.equals("wechat")) {
            coreUserMapper.updateWechatOpenId(userId, oppenId);
        }
        if (bindType.equals("QYwechat")) {
            coreUserMapper.updateQYWechatOpenId(userId, oppenId);
        }
    }
}
