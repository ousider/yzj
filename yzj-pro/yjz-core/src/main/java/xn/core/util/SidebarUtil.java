package xn.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import xn.core.model.system.MenuView;
import xn.core.util.data.StringUtil;
import xn.core.util.xml.XML;
import xn.core.util.xml.XMLElement;

public class SidebarUtil {

    private static Logger log = Logger.getLogger(SidebarUtil.class);

    public List<MenuView> loadSidebar() {

        List<MenuView> result = new ArrayList<>();
		try {
            String url = SidebarUtil.class.getResource("/").getFile();
            url = new File(url).getParent();
            url = new File(url).getParent();
            // 解析菜单XML文件
            XML xml = XML.getInstance(new File(url + "/Sidebar.xml"));
			XMLElement root = xml.getRootElement();

			//一级菜单
			for (XMLElement folder : root.getChildren()) {
			    String parentModuleName = folder.getAttributeValue("moduleName");
                Long ParentModuleId = Long.parseLong(folder.getAttributeValue("moduleId"));
			    
                // 一级菜单对象
                MenuView parentModel = new MenuView();
                parentModel.setModuleId(ParentModuleId);
                parentModel.setModuleName(parentModuleName);
                parentModel.setParentId(0l);
                parentModel.setLevelNum(1);
                result.add(parentModel);
			    
                // 二级菜单
				for (XMLElement menu : folder.getChildren()) {
                    String moduleUrl = menu.getAttributeValue("moduleUrl");
                    String moduleName = menu.getAttributeValue("moduleName");
                    int levelNum = Integer.parseInt(menu.getAttributeValue("levelNum"));
                    long parentId = Integer.parseInt(menu.getAttributeValue("parentModuleId"));
                    long moduleId = Integer.parseInt(menu.getAttributeValue("moduleId"));
                    String component = "";
                    if (StringUtil.isNonBlank(moduleUrl)) {
                        component = moduleUrl.substring(moduleUrl.lastIndexOf("/") + 1);
                        component = component.substring(0, component.lastIndexOf("."));
                    }

                    // 二级菜单对象
                    MenuView cdModel = new MenuView();
                    cdModel.setModuleId(moduleId);
                    cdModel.setModuleName(moduleName);
                    cdModel.setModuleUrl(moduleUrl);
                    cdModel.setLevelNum(levelNum);
                    cdModel.setParentId(parentId);
                    cdModel.setComponent(component);
                    result.add(cdModel);
				}
			}
		} catch (Exception e) {
            log.error(e.getMessage());
		}
        return result;
	}

    public static void main(String[] args) {
        SidebarUtil a= new SidebarUtil();
        a.loadSidebar();
    }
}