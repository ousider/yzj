

package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.BackfatView;
import xn.pigfarm.model.production.BillModel;
import xn.pigfarm.model.production.BreedView;
import xn.pigfarm.model.production.ChangeEarBandView;
import xn.pigfarm.model.production.ChangeHouseView;
import xn.pigfarm.model.production.ChangeSwineryView;
import xn.pigfarm.model.production.ChildDieView;
import xn.pigfarm.model.production.CreatePorkPigView;
import xn.pigfarm.model.production.DeliveryView;
import xn.pigfarm.model.production.FosterView;
import xn.pigfarm.model.production.LeaveView;
import xn.pigfarm.model.production.PigMoveInView;
import xn.pigfarm.model.production.PregnancyCheckView;
import xn.pigfarm.model.production.ReserveToProductView;
import xn.pigfarm.model.production.SemenView;
import xn.pigfarm.model.production.ToChildView;
import xn.pigfarm.model.production.WeanView;


/**
 * @Description: 表单
 * @author fangc
 * @date 2016年4月25日 下午12:35:03
 */
public interface PigEventMapper {
    // 猪只入场
    public void pigMoveIn(PigMoveInView pigMoveInView) throws Exception;

    // 换耳号
    public void changeEarBand(ChangeEarBandView changeEarBandView) throws Exception;

    // 背膘测定
    public void backfat(BackfatView backfatView) throws Exception;

    // 转舍
    public void changeHouse(ChangeHouseView changeHouseView) throws Exception;

    // 转群
    public void changeSwinery(ChangeSwineryView changeSwineryView) throws Exception;

    // 后备转生产
    public void reserveToProduct(ReserveToProductView reserveToProductView) throws Exception;

    // 采精
    public void semen(SemenView semenView) throws Exception;

    // 配种
    public void breed(BreedView breedView) throws Exception;

    // 妊娠检查
    public void pregnancyCheck(PregnancyCheckView pregnancyCheckView) throws Exception;

    // 分娩
    public void delivery(DeliveryView deliveryView) throws Exception;

    // 创建仔猪
    public void createPorkPig(CreatePorkPigView createPorkPigView) throws Exception;

    // 仔猪离场
    public void childPigDie(ChildDieView childDieView) throws Exception;

    // 仔猪离场
    public void foster(FosterView fosterView) throws Exception;

    // 断奶
    public void wean(WeanView weanView) throws Exception;

    // 转保育 转育肥
    public void toChild(ToChildView toChildView) throws Exception;

    // 离场
    public void leave(LeaveView leaveView) throws Exception;

    // 表单查询
    public List<BillModel> searchBillToPage(@Param("companyId") long companyId, @Param("eventCode") String eventCode) throws Exception;

    // 猪只入场查询
    public List<PigMoveInView> searchPigMoveInToList(@Param("billId") long billId) throws Exception;

    // 删除猪只
    public void deletePig(Map<String, Object> map) throws Exception;

}
