package xn.pigfarm.service.basicinfo;

import java.util.List;
import java.util.Map;

import xn.core.util.page.BasePageInfo;
import xn.pigfarm.model.basicinfo.BoarModel;
import xn.pigfarm.model.basicinfo.MaterialGroupModel;
import xn.pigfarm.model.basicinfo.MaterialModel;
import xn.pigfarm.model.basicinfo.PorkPigModel;
import xn.pigfarm.model.basicinfo.SowModel;

/**
 * @Description: 基础信息服务接口 物料主数据
 * @author fangc
 * @date 2016年5月3日 下午3:08:13
 */
public interface IMaterialService {
	
    /**
     * @Description:查询主数据列表 不分页
     * @author fangc
     * @throws Exception
     *             物料类别前三条与猪类别 值 相同 1：公猪 2：母猪 3：肉猪
     */
    public List<MaterialModel> searchMaterialToList(String materialType) throws Exception;

    /**
     * @Description:查询主数据列表 不分页
     * @author fangc
     * @throws Exception
     *             物料类别前三条与猪类别 值 相同 1：公猪 2：母猪 3：肉猪
     */
    public List<MaterialModel> searchBoarMaterialToList(String materialType, String rowId) throws Exception;

    /**
     * @Description:查询主数据列表 分页
     * @author fangc
     * @throws Exception
     */
    public BasePageInfo searchMaterialToPage(String materialType) throws Exception;

    /**
     * @Description: 编辑主数据
     * @author fangc
     * @param map
     * @throws Exception
     */
    public void editMaterial(Map<String, Object> map, String materialType) throws Exception;

    /**
     * @Description: 删除主数据
     * @author fangc
     * @param inputMap
     * @throws Exception
     */
    public void deleteMaterials(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询 物料组维护 不分页
     * @author guang
     * @return
     * @throws Exception
     */
    public List<MaterialGroupModel> searchMaterialGroupToList(String materialType) throws Exception;

    /**
     * @Description: 查询 物料组维护 分页
     * @author guang
     * @return
     * @throws Exception
     */
    public BasePageInfo searchMaterialGroupToPage() throws Exception;

    /**
     * @Description: 编辑 物料组维护
     * @author guang
     * @param cdMaterialGroupModel
     * @param lineList
     * @throws Exception
     */
    public void editMaterialGroup(MaterialGroupModel cdMaterialGroupModel, String groupList) throws Exception;

    /**
     * @Description: 删除 物料组维护
     * @author guang
     * @param ids
     * @throws Exception
     */
    public void deleteMaterialGroups(long[] ids) throws Exception;

    /**
     * @Description: 查询 公猪 不分页
     * @author guang
     * @return
     * @throws Exception
     */
    public List<BoarModel> searchBoarToList(String materialType) throws Exception;

    /**
     * @Description: 查询 公猪 分页
     * @author guang
     * @return
     * @throws Exception
     */
    public BasePageInfo searchBoarToPage() throws Exception;

    /**
     * @Description: 编辑 公猪
     * @author guang
     * @param cdMaterialGroupModel
     * @param lineList
     * @throws Exception
     */
    public void editBoar(BoarModel boarModel) throws Exception;

    /**
     * @Description: 删除 公猪
     * @author guang
     * @param ids
     * @throws Exception
     */
    public void deleteBoars(long[] ids) throws Exception;

    /**
     * @Description: 查询 肉猪 不分页
     * @author guang
     * @return
     * @throws Exception
     */
    public List<SowModel> searchSowToList(String materialType) throws Exception;

    /**
     * @Description: 查询 肉猪 分页
     * @author guang
     * @return
     * @throws Exception
     */
    public BasePageInfo searchSowToPage() throws Exception;

    /**
     * @Description: 编辑 肉猪
     * @author guang
     * @param cdMaterialGroupModel
     * @param lineList
     * @throws Exception
     */
    public void editSow(SowModel sowModel) throws Exception;

    /**
     * @Description: 删除 肉猪
     * @author guang
     * @param ids
     * @throws Exception
     */
    public void deleteSows(long[] ids) throws Exception;

    /**
     * @Description: 查询 肉猪 不分页
     * @author guang
     * @return
     * @throws Exception
     */
    public List<PorkPigModel> searchPorkPigToList(String materialType) throws Exception;

    /**
     * @Description: 查询 肉猪 分页
     * @author guang
     * @return
     * @throws Exception
     */
    public BasePageInfo searchPorkPigToPage() throws Exception;

    /**
     * @Description: 编辑 肉猪
     * @author guang
     * @param cdMaterialGroupModel
     * @param lineList
     * @throws Exception
     */
    public void editPorkPig(PorkPigModel porkPigModel) throws Exception;

    /**
     * @Description: 删除 肉猪
     * @author guang
     * @param ids
     * @throws Exception
     */
    public void deletePorkPigs(long[] ids) throws Exception;

    /**
     * @Description: 查询物料明细
     * @author Zhangjc
     * @param materialId
     * @param materialType
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchDetailMaterialToList(long materialId, String materialType) throws Exception;

    /**
     * 高级查询,根据物料代码,物料名称,物料类型
     * 
     * @author THL
     * @param map
     * @return BasePageInfo
     * @throws Exception
     */
    public BasePageInfo searchCdMaterialToPageForAdvancedSearch(Map<String, Object> map) throws Exception;

}
