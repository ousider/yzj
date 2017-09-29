<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/basicinfo/RoleManageZ.js?v=${param.randomnumber}"></script>
</head>
<body>
	<jsp:include page="/jsp/TableToolbar.jsp" />
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'">
		<form id="editForm" method="post">
			<div style="display:none">
				<input class="easyui-textbox" name="rowId" id="rowId"/>
				<input type="hidden" id="companyId" name="companyId"> 
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>角色信息</div>
				<div class="fieldContent">
					<div class="wd-com wd-4"><label class="label">编号:</label></div>
					<div class="wd-com wd-8"> 
						<input class="easyui-textbox" name="businessCode" data-options="prompt:'系统自动分配编码',disabled:true"   style="width:90%;height:35px">
					</div>
					<div class="wd-com wd-4"><label class="label">角色名称:</label></div>
					<div class="wd-com wd-8"> 
						<input class="easyui-textbox" name="roleName"  data-options="prompt:'请输入角色名称',required:true,validType:'numOrLetterOrChineseOr_'"  style="width:90%;height:35px">
					</div>
					<div class="wd-com wd-4"><label class="label">角色类型:</label></div>
					<div class="wd-com wd-8"> 
						<input id="roleType" name="roleType" style="width:90%;height:35px">
					</div>
					<div class="wd-com wd-4"><label class="label">菜单模版:</label></div>
					<div class="wd-com wd-8"> 
						<input id="templateId" name="templateId" data-options="prompt:'请选择菜单模板'" style="width:90%;height:35px">
					</div>
					<div class="wd-com wd-4"><label class="label">采购类型:</label></div>
					<div class="wd-com wd-8"> 
						<input id="purchaseType" name="purchaseType" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-4"><label class="label">供应链价格显示:</label></div>
					<div class="wd-com wd-8"> 
						<input id="showPrice" name="showPrice" style="width:90%;height:32px">
					</div>
				</div>
			</div>
		</form>	
	</div>	
	<div id="menuLimit" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('menuLimit',390)"></span><span>权限设置</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			 <div id="MenuTree"></div>
		 </div>
		 <div class="rightSlipFooter">
		    <button type="button" onclick="onSetMenu.onMenuWin('MenuTree')" class="rightSlipBtn green">确定</button>
		</div>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>