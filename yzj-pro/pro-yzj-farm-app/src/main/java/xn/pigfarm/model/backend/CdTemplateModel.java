
package xn.pigfarm.model.backend;

import java.io.Serializable;

import javax.persistence.Table;

import xn.core.model.BaseModel;

/**
 * Created Eclipse Java EE.
 * User:li.zhou
 * Date:2016-4-9
 * Time:14:03:09
 * 表：CdOtemplate
 */
@Table(name = "cd_o_template")
public class CdTemplateModel extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1966198007384186276L;
	
    /**
     * 模板名称
     */
	private String templateName;
	
    /**
     * 模板风格
     */
	private String templateStyle;


	/**
     * 设置模板名称
     * 
     * @param TEMPLATE_NAME - 模板名称
     */
	public void setTemplateName(String value) {
		this.templateName = value;
	}
	
    /**
     * 获取模板名称
     * 
     * @return TEMPLATE_NAME - 模板名称
     */
	public String getTemplateName() {
		return this.templateName;
	}
	public String getTemplateStyle() {
		return templateStyle;
	}
	public void setTemplateStyle(String templateStyle) {
		this.templateStyle = templateStyle;
	}
	

}




