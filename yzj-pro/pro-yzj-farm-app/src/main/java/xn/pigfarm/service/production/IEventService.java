package xn.pigfarm.service.production;

import xn.pigfarm.model.production.BillModel;

/**
 * @Description: 留种选种
 * @author yangy
 * @date 2016年10月10日 下午12:29:08
 */
public interface IEventService {
    
    /**
     * 留种
     * 
     * @author yangy
     * @param billModel
     * @param eventName
     * @param seedViewList
     * @throws Exception
     */
    public void editSeedPig(BillModel billModel, String eventName, String seedViewList) throws Exception;

    /**
     * 选种
     * 
     * @author yangy
     * @param billModel
     * @param eventName
     * @param breedViewList
     * @throws Exception
     */
    public void editSelectionPig(BillModel billModel, String eventName, String breedViewList) throws Exception;

}
