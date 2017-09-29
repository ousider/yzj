package xn.pigfarm.service.production.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.data.StringUtil;
import xn.pigfarm.mapper.production.PpPigMapper;
import xn.pigfarm.mapper.production.SearchPigRemindMapper;
import xn.pigfarm.service.production.ISearchPigRemind;
import xn.pigfarm.util.constants.EventConstants;
import xn.pigfarm.util.constants.PigConstants;

@Service("searchPigRemind")
public class SearchPigRemindImpl extends BaseServiceImpl implements ISearchPigRemind {

    @Autowired
    SearchPigRemindMapper searchPigRemindMapper;
    
    @Autowired
    PpPigMapper ppPigMapper;
    
    public String searchPigErrMsgInfo(List<Map<String, String>> searchPigInfo) {
        String info = "";
        for (Map<String, String> maps : searchPigInfo) {
            info = maps.get("INFO");
        }
        return info;
    }

    // 查询找不到猪只提醒
    @Override
    public String searchPigRemind(String eventName, String earBrand,String pigIds) throws Exception {
        String searchRemind = "";
        if(StringUtil.isBlank(earBrand)){
            return null;
        }
        if(StringUtil.split(earBrand, ",").length==1){
        	earBrand = earBrand.replace("'", "");
        	earBrand = "'"+ earBrand + "'";
		}
		Map<String, String> map = new HashMap<>();
		map.put("earBrand", earBrand);
		map.put("farmId", String.valueOf(getFarmId()));
		
		pigIds = StringUtil.isBlank(pigIds)?null:pigIds;
		map.put("pigIds", pigIds);
		
		Map<String, String> searchPigByEarBrand2 = searchPigRemindMapper.searchPigByEarBrand(map);
		if(pigIds != null && searchPigByEarBrand2 != null && !searchPigByEarBrand2.isEmpty()){
		    String pigId = String.valueOf(searchPigByEarBrand2.get("ROW_ID"));
		    String[] split = pigIds.split(",");
		    
		    for(int i=0;i<split.length;i++){
		        if(pigId.equals(split[i])){
		            searchRemind = "第"+(i+1)+"行耳牌号重复!" ;
		        }
		    }
		}else{
    		switch (eventName) {
    		case EventConstants.CHANGE_EAR_BAND:
                map.put("eventName", "换耳号");
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigExists(map));
    			if ("Y".equals(searchRemind)) {
                    searchRemind = "该猪不能用于此操作，或请查看猪类别是否正确！";
    			}
    			break;
    		case EventConstants.BACKFAT:
                map.put("eventName", "背膘测定");
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigExists(map));
    			if ("Y".equals(searchRemind)) {
                    searchRemind = "该猪不能用于此操作！";
    			}
    			break;
    		case EventConstants.BOAR_RESERVE_TO_PRODUCT:
                map.put("eventName", "后备转生产");
                map.put("remind", "后备公猪");
    			map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_HBGZ));
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsTheMaterialAndThisPigClass(map));
    			// if(StringUtil.isBlank(searchRemind)){
    			// searchRemind =
    			// searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsThePigClass2(map));
    			// }
    			if (StringUtil.isBlank(searchRemind)) {
    				searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsThePigClass(map));
    			}
    			break;
    		case EventConstants.SEMEN:
                map.put("eventName", "采精");
                map.put("remind", "生产公猪");
    			map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_SCGZ));
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsThePigClass(map));
    			break;
    		case EventConstants.PREPUBERTAL_CHECK:
                map.put("eventName", "情期鉴定");
                map.put("remind", "后备母猪");
    			map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_HBMZ));
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsTheMaterialAndThisPigClass(map));
    			if (StringUtil.isBlank(searchRemind)) {
    				searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsThePigClass(map));
    			}
    			break;
    		case EventConstants.CHANGE_LABOR_HOUSE:
                map.put("eventName", " 转产房");
                map.put("remind", "怀孕母猪");
    			map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_RS));
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigToDeliveryHouseAge(map));
    			if (StringUtil.isBlank(searchRemind)) {
    				searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsThePigClass(map));
    			}
    			break;
    		case EventConstants.BREED_PIG_CHANGE_HOUSE:
    
                map.put("eventName", "种猪转舍");
                map.put("remind", "种猪");
    			map.put("pigClass", "'null'");
    			map.put("pigType", String.valueOf(PigConstants.PIG_TYPE_PORK));
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigMessageOfThePigType(map));
    			Map<String, String> searchPigByEarBrand = searchPigRemindMapper.searchPigByEarBrand(map);
    			
    			if("10".equals(String.valueOf(searchPigByEarBrand.get("PIG_CLASS")))){
    			    searchRemind = "【"+earBrand.replace("'", "")+"】猪只是哺乳母猪,不能做种猪转舍操作!";
    			}
    			break;
            case EventConstants.BREED:
                map.put("eventName", "配种");
                map.put("remind", "待配母猪");
                map.put("pigType", String.valueOf(PigConstants.PIG_TYPE_SOW));
                searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsThePigClass2we(map));
    			break;
    		case EventConstants.PREGNANCY_CHECK:
                map.put("eventName", "妊娠检查");
                map.put("remind", "怀孕母猪");
    			map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_RS));
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsThePigClass(map));
    			break;
    		case EventConstants.MISSCARRY:
                map.put("eventName", "流产");
                map.put("remind", "怀孕母猪");
    			map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_RS));
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsThePigClass(map));
    			break;
    		case EventConstants.DELIVERY:
                map.put("eventName", "分娩");
                map.put("remind", "怀孕母猪");
    			map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_RS));
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigDeliveryAge(map));
    			if (StringUtil.isBlank(searchRemind)) {
    				searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsThePigClass(map));
    			}
    			break;
    		case EventConstants.FOSTER:
                map.put("eventName", "寄养");
                map.put("remind", "哺乳母猪");
    			map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_BR));
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsThePigClass(map));
    			break;
    		case EventConstants.WEAN:
                map.put("eventName", "断奶");
                map.put("remind", "已分娩母猪");
    			map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_SOW_DN));
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsThePigClass(map));
    			break;
    		case EventConstants.BREED_PIG_OBSOLETE:
                map.put("eventName", "种猪淘汰申请");
                map.put("remind", "种猪");
    			map.put("pigClass", "'null'");
    			map.put("pigType", String.valueOf(PigConstants.PIG_TYPE_BOAR));
    			List<Map<String, String>> searchPigMessageOfThePigType = searchPigRemindMapper.searchPigMessageOfThePigType(map);
    			if(searchPigMessageOfThePigType.size() == 0){
    			    searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigMessageOfThePigType(map));
    			}else{
    			    map.put("INFO", earBrand+":该猪已申请淘汰,不能重复申请!");
    			    searchPigMessageOfThePigType.add(map);
    			    searchRemind = searchPigErrMsgInfo(searchPigMessageOfThePigType);
    			}
    			break;
    		case EventConstants.BREED_PIG_DIE:
                map.put("eventName", "种猪死亡");
                map.put("remind", "种猪");
    			map.put("pigClass", "'null'");
    			map.put("pigType", String.valueOf(PigConstants.PIG_TYPE_PORK));
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigMessageOfThePigType(map));
    			break;
    		case EventConstants.CHILD_PIG_DIE:
                map.put("eventName", "仔猪死亡");
                map.put("remind", "哺乳仔猪");
    			map.put("pigClass", "'" + PigConstants.PIG_CLASS_BRJZ + "'" + ",'" + PigConstants.PIG_CLASS_BRRZ + "'");
    			map.put("pigType", "'" + PigConstants.PIG_TYPE_BOAR + "'" + ",'" + PigConstants.PIG_TYPE_SOW + "'" + ",'"
    					+ PigConstants.PIG_TYPE_PORK + "'");
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigMessageOfThePigType(map));
    			break;
    		case EventConstants.GOOD_PIG_DIE:
                map.put("eventName", "商品猪死亡");
                map.put("remind", "商品猪");
    			map.put("pigClass", "'null'");
    			map.put("pigType", "'" + PigConstants.PIG_TYPE_BOAR + "'" + ",'" + PigConstants.PIG_TYPE_SOW + "'");
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigMessageOfThePigType(map));
    			break;
    		case EventConstants.CHANGE_HOUSE:
                map.put("eventName", "转舍");
    			searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigExists(map));
    			break;
    		case EventConstants.TO_CHILD_CARE:
    			break;
    		case EventConstants.TO_CHILD_FATTEN:
    			break;
    		case EventConstants.NURSE_CHANGE_HOUSE:
    		    map.put("eventName", "奶妈转舍");
                map.put("remind", "哺乳母猪");            
                map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_BR));
                searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsThePigClass(map));
    		    break;
    		case EventConstants.SEED_PIG:
    		    map.put("eventName", "留种");
                map.put("remind", "哺乳仔猪");            
                map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_BRJZ));
                map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_BRRZ));
    		    searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchPigIsThePigClass(map));
    		    break;
    		default:
    			break;
    		case EventConstants.GOOD_PIG_SELL:
    			break;
            // 暂时不用
    		case EventConstants.SEMEN_CHECK:
    			break;
    		case EventConstants.SALE_SEMEN:
                searchRemind = searchPigErrMsgInfo(searchPigRemindMapper.searchSemenExists(map));
    		    break;
    		}
        }
		if (StringUtil.isBlank(searchRemind)) {
            searchRemind = searchPigExists(map);
		}
		return searchRemind;
	}

    /* 判断猪只是否可用 */
	@Override
	public String searchPigExists(Map<String, String> map) throws Exception {
		List<Map<String, String>> searchPigExists = searchPigRemindMapper.searchPigExists(map);
		String info = "";
		for (Map<String, String> maps : searchPigExists) {
			info = maps.get("INFO");
			if (!"Y".equals(info)) {
				return info;
			}
		}
		return info;
	}

	@Override
	public String searchPigRemind(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		String earBrand = "'ff5525f56'";
		if (StringUtil.split(earBrand, ",").length == 1) {
			earBrand.replace("'", "");
			earBrand = "'" + earBrand + "'";
		}
		System.out.println(earBrand);

	}
}
