package xn.core.model.system;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 17:33:46
 *       表：SEQUENCE
 */
public class SequenceModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = 1312547261910952459L;

    // seqName
    private static final String D_SeqName="seqName";
	 //currentVal 
    private static final String D_CurrentVal="currentVal";
	 //incrementVal 
    private static final String D_IncrementVal="incrementVal";
	

	/**
     * 设置seqName
     * 
     * @param seq_name
     */
	public void setSeqName(String value) {
        set(D_SeqName,value);
    }
	
    /**
     * 获取seqName
     * 
     * @return seq_name
     */
    public String getSeqName() {
        return getString(D_SeqName);
    }

    /**
     * 设置currentVal
     * 
     * @param current_val
     */
	public void setCurrentVal(Long value) {
        set(D_CurrentVal,value);
    }
	
	/**
     * 获取currentVal
     * 
     * @return current_val
     */
    public Long getCurrentVal() {
        return getLong(D_CurrentVal);
    }

    /**
     * 设置incrementVal
     * 
     * @param increment_val
     */
	public void setIncrementVal(Long value) {
        set(D_IncrementVal,value);
    }
	
	/**
     * 获取incrementVal
     * 
     * @return increment_val
     */
    public Long getIncrementVal() {
        return getLong(D_IncrementVal);
    }
	
	
	@Override
	public List<String> getPropertes() {
	    if (super.getPropertes() == null || super.getPropertes().isEmpty()) {
	        setPropertes(D_SeqName);
	        setPropertes(D_CurrentVal);
	        setPropertes(D_IncrementVal);
	    }
	    return super.getPropertes();
	}

}




