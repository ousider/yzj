<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/jsp/Head.jsp" />
		<script src="${base_url}/js/backend/CdSetting.js?v=${param.randomnumber}"></script>
	</head>
	<body>
	<jsp:include page="/jsp/TableToolbar.jsp" />
	<div id="advancedSearch" onClick="leftSilpFun('pigHouseAdSearch')"><span>高级查询</span></div>
	<div id="pigHouseAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('pigHouseAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">系统模块:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="settingModule" name="settingModule" data-options="prompt:'请输入系统模块'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">设置项名称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="settingName" name="settingName" data-options="prompt:'请输入设置项名称'" style="width:100%;height:35px">
				</div>
				
				<div class="wd-com wd-8"><label class="label">设置项代码:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="settingCode" name="settingCode" data-options="prompt:'请输入设置项代码'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">设置值:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="settingValue" name="settingValue" data-options="prompt:'请输入设置值'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">显示类型:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="sowType" name="sowType" data-options="prompt:'请输入显示类型'" style="width:100%;height:35px">
				</div>
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
		<table id="table"></table>
		<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
			<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="wd-com wd-3"><label class="label">类别:</label></div>
						<div class="wd-com wd-5">
							<input id="clickCompanyType" name="farmType" data-options="prompt:'类别',required:true" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">分组代码:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="settingModule" data-options="prompt:'请输入分组代码',required:true" style="width:100%;height:35px" />
						</div>
						<div class="wd-com wd-3"><label class="label">设置项代码:</label></div>
						<div class="wd-com wd-5" >
							<input class="easyui-textbox" name="settingCode" data-options="prompt:'请输入设置项代码',required:true" style="width:100%;height:35px" />
						</div>
						<div class="wd-com wd-3" ><label class="label">设置项代码:</label></div>	
						<div class="wd-com wd-5" >
							<input class="easyui-textbox" name="groupName" data-options="prompt:'请输分组名称'" style="width:100%;height:35px" />
						</div>	
						<div class="wd-com wd-3" ><label class="label">设置项:</label></div>	
						<div class="wd-com wd-5" >
							<input class="easyui-textbox" name="settingName" data-options="prompt:'请输设置项'" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"  ><label class="label">默认值:</label></div>
						<div  class="wd-com wd-5"  >
							<input class="easyui-textbox" name="settingValue" data-options="prompt:'请输入默认值'" style="width:100%;height:35px" />
						</div>
						<div class="wd-com wd-3" ><label class="label">显示类型:</label></div>
						<div class="wd-com wd-5">
							<input id="clickSowType" name="sowType" data-options="prompt:'请输入显示类型',required:true"  style="width:100%;height:35px">
						</div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>提示说明</div>
					<div class="fieldContent">
						<div class="wd-com wd-3"><label class="label">提示说明:</label></div>
							<div class="wd-com wd-21">
								<input class="easyui-textbox" name="memo" data-options="prompt:'请输入提示说明'" style="width:100%;height:70px">
							</div>
					</div>
				</div>
			</form>
			<%-- <jsp:include page="/jsp/ListTableToolbar.jsp" />
			<div id="listTable"></div> --%>
		</div>
		<jsp:include page="/jsp/WinFooter.jsp" />
	</body>
</html>