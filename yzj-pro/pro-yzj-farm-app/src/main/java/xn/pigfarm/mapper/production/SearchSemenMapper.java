

package xn.pigfarm.mapper.production;

import java.util.List;

import xn.pigfarm.model.production.SearchSemenModel;
import xn.pigfarm.model.production.ValidSemenModel;


/**
 * @Description: 表单
 * @author fangc
 * @date 2016年4月25日 下午12:35:03
 */
public interface SearchSemenMapper {

    public List<ValidSemenModel> searchSemenToList(SearchSemenModel searchSemenModel) throws Exception;


}
