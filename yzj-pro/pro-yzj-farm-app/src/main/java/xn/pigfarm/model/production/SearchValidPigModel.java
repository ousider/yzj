
package xn.pigfarm.model.production;

import java.util.Date;

import xn.core.model.BaseModel;

/**
 * 表：
 */
public class SearchValidPigModel extends BaseModel implements java.io.Serializable {
    private static final long serialVersionUID = 2949527334890319746L;

    // 查询类型： 1.模糊查询且分页（只有在输入耳号时执行） 2.条件查询 3.crtl + v 不分页
    private String queryType;

    // 模糊查询参数; ctrl + v 时将耳牌号以逗号分隔
    private String query;

    // 用于筛选已选定ID，多个ID之前用逗号隔开；用于 NOT IN做条件
    private String pigIds;

    // 猪场ID
    private Long farmId;

    // 产线
    private Long lineId;

    // 猪舍，多个用逗号隔开
    private String houseIds;

    // 猪栏，多个用逗号隔开
    private String pigpenIds;

    // 猪群，多个用逗号隔开
    private String swineryIds;

    // 猪类
    private String pigType;

    // 性别
    private String sex;

    // 品种
    private String breedIds;

    // 猪只状态，多个用逗号隔开
    private String pigClassIds;

    // 耳牌号
    private String earBrand;

    // 耳缺号
    private String earShort;

    // 最小日龄
    private Long minDateage;

    // 最大日龄
    private Long maxDateage;

    // 预留日期字段
    private Date date1;

    // 预留日期字段
    private Date date2;

    // 物料ID
    private String materialIds;

    // 最小重量
    private Double minWeight;

    // 最大重量
    private Double maxWeight;

    // 0是否为指定猪只 ,1 不指定猪只
    private String specifyPigs;

    // 选猪原则 1从大到小 ,2 从小到大
    private String choice;

    // 创建人
    private Long createId;

    // 起始页码
    private Long offset;

    // 每页显示记录数，为1时为所有
    private Long pagesize;

    // 事件名称
    private String eventName;

    // 耳缺号，耳牌号
    private String earCodeType;

    // 选种 是否商品猪
    private String goodPigFlag;

    // 胎次
    private Long parity;

    // 精液批号
    private String semenIds;

    // 公司mark
    private String companyMark;

    // 是否选择留种猪
    private String seedPigFlag;

    // 总数
    private Long totalCount;

    // 猪舍类型
    private String houseType;

    // 错误代码
    private String errorCode;

    // 错误信息
    private String errorMessage;

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getPigIds() {
        return pigIds;
    }

    public void setPigIds(String pigIds) {
        this.pigIds = pigIds;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getHouseIds() {
        return houseIds;
    }

    public void setHouseIds(String houseIds) {
        this.houseIds = houseIds;
    }

    public String getSwineryIds() {
        return swineryIds;
    }

    public void setSwineryIds(String swineryIds) {
        this.swineryIds = swineryIds;
    }

    public String getPigType() {
        return pigType;
    }

    public void setPigType(String pigType) {
        this.pigType = pigType;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBreedIds() {
        return breedIds;
    }

    public void setBreedIds(String breedIds) {
        this.breedIds = breedIds;
    }

    public String getPigClassIds() {
        return pigClassIds;
    }

    public void setPigClassIds(String pigClassIds) {
        this.pigClassIds = pigClassIds;
    }

    public String getEarBrand() {
        return earBrand;
    }

    public void setEarBrand(String earBrand) {
        this.earBrand = earBrand;
    }

    public Long getMinDateage() {
        return minDateage;
    }

    public void setMinDateage(Long minDateage) {
        this.minDateage = minDateage;
    }

    public Long getMaxDateage() {
        return maxDateage;
    }

    public void setMaxDateage(Long maxDateage) {
        this.maxDateage = maxDateage;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public String getMaterialIds() {
        return materialIds;
    }

    public void setMaterialIds(String materialIds) {
        this.materialIds = materialIds;
    }

    public Double getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(Double minWeight) {
        this.minWeight = minWeight;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getSpecifyPigs() {
        return specifyPigs;
    }

    public void setSpecifyPigs(String specifyPigs) {
        this.specifyPigs = specifyPigs;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getPagesize() {
        return pagesize;
    }

    public void setPagesize(Long pagesize) {
        this.pagesize = pagesize;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getEarShort() {
        return earShort;
    }

    public void setEarShort(String earShort) {
        this.earShort = earShort;
    }

    public String getPigpenIds() {
        return pigpenIds;
    }

    public void setPigpenIds(String pigpenIds) {
        this.pigpenIds = pigpenIds;
    }

    public String getEarCodeType() {
        return earCodeType;
    }

    public void setEarCodeType(String earCodeType) {
        this.earCodeType = earCodeType;
    }

    public String getGoodPigFlag() {
        return goodPigFlag;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public void setGoodPigFlag(String goodPigFlag) {
        this.goodPigFlag = goodPigFlag;
    }

    public Long getParity() {
        return parity;
    }

    public void setParity(Long parity) {
        this.parity = parity;
    }

    public String getSemenIds() {
        return semenIds;
    }

    public void setSemenIds(String semenIds) {
        this.semenIds = semenIds;
    }

    public String getCompanyMark() {
        return companyMark;
    }

    public void setCompanyMark(String companyMark) {
        this.companyMark = companyMark;
    }

    public String getSeedPigFlag() {
        return seedPigFlag;
    }

    public void setSeedPigFlag(String seedPigFlag) {
        this.seedPigFlag = seedPigFlag;
    }

}
