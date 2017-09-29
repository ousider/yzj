package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;

import xn.core.mapper.base.IMapper;

public interface SearchPigRemindMapper extends IMapper {

    /**
     * @Description: 查询猪是否在猪场
     * @author 程彬
     * @param eventName
     * @param earBrand
     * @return
     */
    public List<Map<String, String>> searchPigExists(Map<String, String> map);

    /**
     * @Description: 查询猪提醒
     * @author 程彬
     * @param eventName
     * @param earBrand
     * @return
     */
    public List<Map<String, String>> searchPigRemind(Map<String, String> map);
    

    /**
     * @Description: 根据猪只状态判断是否可用(种猪)
     * @param map
     * @return
     */
    public List<Map<String, String>> searchPigIsThePigClass(Map<String, String> map);

    /**
     * @Description: 判断猪只是否是种猪
     * @param map
     * @return
     */
    public List<Map<String, String>> searchPigMessageOfThePigType(Map<String, String> map);
    
    /**
     * @Description: 判断该猪是否已经申请淘汰 
     * @param map
     * @return
     */
    public List<Map<String, String>> searchPigMessageOfTableObsolete(Map<String, String> map);

//    /**
    // * @Description: 根据猪只状态及日龄判断是否可转生产
//     * @param map
//     * @return
//     */
//    public List<Map<String, String>> searchPigIsThePigClass2(Map<String, String> map);

    /**
     * @Description: 根据猪只状态及日龄判断是否可转产房
     * @param map
     * @return
     */
    public List<Map<String, String>> searchPigToDeliveryHouseAge(Map<String, String> map);

    /**
     * @Description: 根据猪 主数据和状态 判断是否可转生产
     * @param map
     * @return
     */
	public List<Map<String, String>> searchPigIsTheMaterialAndThisPigClass(Map<String, String> map);
	
	/**
     * @Description: 根据 猪只状态及主数据日龄 判断是否可配种
     * @param map
     * @return
     */
	public List<Map<String, String>> searchPigIsThePigClass2we(Map<String, String> map);

    /**
     * @Description: 根据 猪只状态及主数据日龄 判断是否可分娩
     * @param map
     * @return
     */
    public List<Map<String, String>> searchPigDeliveryAge(Map<String, String> map);
    
    /**
     * @Description: 查询精液批次
     * @param map
     * @return
     */
    public List<Map<String, String>> searchSemenExists(Map<String, String> map);
    
    /**
     * @Description: 查询猪只基本信息
     * @param map
     * @return
     */
    public Map<String, String> searchPigByEarBrand(Map<String, String> map);

}
