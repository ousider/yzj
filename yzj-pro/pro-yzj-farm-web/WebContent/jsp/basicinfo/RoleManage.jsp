<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/basicinfo/RoleManage.js?v=${param.randomnumber}"></script>
</head>
<body>
	<jsp:include page="/jsp/TableToolbar.jsp" />
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post">
			<div style="display:none">
				<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
				<input type="hidden" id="companyId" name="companyId"> 
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>角色信息</div>
				<div class="fieldContent">
					<div class="wd-com wd-4"><label class="label">编号:</label></div>
					<div class="wd-com wd-8"> 
						<input class="easyui-textbox" name="businessCode" data-options="prompt:'系统自动分配编码',disabled:true"   style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-4"><label class="label">角色名称:</label></div>
					<div class="wd-com wd-8"> 
						<input class="easyui-textbox" name="roleName"  data-options="prompt:'请输入角色名称',required:true"  style="width:100%;height:32px">
					</div>
<!-- 					<div class="wd-com wd-4"><label class="label">直属公司:</label></div> -->
<!-- 					<div class="wd-com wd-8">  -->
<!-- 						<input  class="easyui-searchbox"  id="farmId" name="farmId" data-options="prompt:'系统自动分配编码',required:true"   style="width:100%;height:32px"> -->
<!-- 					</div> -->
					<div class="wd-com wd-4"><label class="label">角色类型:</label></div>
					<div class="wd-com wd-8"> 
						<input class="easyui-combobox" id="roleType" name="roleType"  data-options="prompt:'请选择角色类型',required:true"  style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-4"><label class="label">菜单模版:</label></div>
					<div class="wd-com wd-8"> 
						<input class="easyui-combobox" id="templateId" name="templateId" data-options="prompt:'请选择菜单模版',required:true"   style="width:100%;height:32px">
					</div>
				</div>
			</div>
			<br>
			<div id="listTable"></div>		
			<div id="toolbar">				
					<button type="button" onclick="detailEmpAdd()" class="listTableToolbarBtn">新增</button>
					<button type="button" onclick="detailEmpDelete()" class="listTableToolbarBtn">删除</button>
			</div>
		</form>		
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
	
	<div id="CompanyWin" class="easyui-window" data-options="closed:true,footer:'#CompanyBnt'" style="width:550px;height:450px;padding:0px">
		
		<div id="treeTable"></div>
		<div id="CompanyBnt" region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
			<a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="onCompanyId()">确认</a>
			<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="Myclose('CompanyWin')">关闭</a>
		</div>
	</div>
	
	
	<div id="MenuWin" class="easyui-window" data-options="closed:true,footer:'#MenuWinBnt'" style="width:550px;height:450px;padding:0px">
		
		<div id="MenuTree"></div>
		<div id="MenuWinBnt" region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
			<a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="onSetMenu.onMenuWin('MenuTree')">确认</a>
			<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="Myclose('MenuWin')">关闭</a>
		</div>
	</div>
	

	<div id="EmpLoyeeWin" class="easyui-window" data-options="closed:true,footer:'#EmpLoyeeBnt'" style="width:550px;height:450px;padding:0px">			
		<div id="EmpLoyeeTable"></div>
		<div id="EmpLoyeeBnt" region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
			<a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="onEmpLoyeeId()">确认</a>
			<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="Myclose('EmpLoyeeWin')">关闭</a>
		</div>
	</div>
	
</body>
</html>