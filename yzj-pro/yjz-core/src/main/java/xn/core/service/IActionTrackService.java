package xn.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import xn.core.model.system.ActionTrackModel;

@Service
public interface IActionTrackService {

    /**
     * @Description: 来访信息表插入
     * @author zhangjs
     * @param ip
     * @param atm
     */
    public void saveGuestInfro(String ip, ActionTrackModel atm) throws Exception;

    /**
     * @Description: 修改密码
     * @author Administrator
     * @param rowId
     * @param password
     */
    public void eidtPassword(String password);

    /**
     * @Description: 检查QQ登录openID是否已经绑定账号
     * @author Cress
     * @param OpenId
     * @return
     */
    public List<Map<String, Object>> checkOpenId(String OpenId, String checkType) throws Exception;

    /**
     * @Description: 更新用户表QQOpenId
     * @author Cress
     * @param password
     * @throws Exception
     */
    public void updateOpenId(long userId, String oppenId, String bindType) throws Exception;
}
