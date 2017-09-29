<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/supplyChian/WarehouseSetting.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none">
		<input type="hidden" name="isPurchase" id="isPurchase" value="N"/>
	</div>
	<div id="tableToolbarList" style="margin-left:20px">
			<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
			<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
			<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button>
			<button type="button" onclick="onBtnForbidden()" class="tableToolbarBtn btn-middle">禁用</button>
			<button type="button" onclick="onBtnUse()" class="tableToolbarBtn btn-middle">启用</button>
			<button type="button" onclick="onBtnInitialize()" class="tableToolbarBtn btn-middle" style="width:120px;">库存初始化</button>
			<button type="button" onclick="onBtnDefaultHouse()" class="tableToolbarBtn btn-middle" style="width:120px;">默认仓库设置</button>
	</div>
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
			<form id="editForm" method="post" class="form-inline" style="height:100%;">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId"/>
					<input type="hidden" name="cussupType" value="S">
					<input class="easyui-textbox" name="status" id="status"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>仓库信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">仓库名称:</label></div>
							<div class="wd-com wd-5">
								<input id="warehouseName" class="easyui-textbox" name="warehouseName" data-options="prompt:'请输入仓库名称',required:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">仓库地址:</label></div>
							<div id="companyNameDiv" class="wd-com wd-5">
								<input id="warehouseAddress" class="easyui-textbox" name="warehouseAddress" data-options="prompt:'请输入仓库地址'" style="width:100%;height:35px">
							</div>
<!-- 							<div class="wd-com wd-3"><label class="label">排序号:</label></div> -->
<!-- 							<div class="wd-com wd-5"> -->
<!-- 								<input id="sortNbr" class="easyui-textbox" name="sortNbr" data-options="prompt:'请输入排序号',required:true,validType:'range[1,999]'" style="width:100%;height:35px"> -->
<!-- 							</div> -->
							<div class="wd-com wd-3"><label class="label">仓库类型:</label></div>
							<div class="wd-com wd-5">
								<input id="warehouseType" name="warehouseType" style="width:100%;height:35px">							
							</div>
							<div class="wd-com wd-3"><label class="label">物料种类:</label></div>
							<div class="wd-com wd-5">
								<input id="materialType" name="materialType" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">管理角色:</label></div>
							<div class="wd-com wd-5">
								<input id="operationRole" name="operationRole" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">仓库具体分类:</label></div>
							<div class="wd-com wd-5">
								<input id="warehouseCategory" name="warehouseCategory" style="width:100%;height:35px">
							</div>
						</div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span id="notes" class="arrowUp" onclick="upOrDown(this)"></span>备注</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-24">
								<input class="easyui-textbox" name="notes" data-options="prompt:'请输入备注',multiline:true" style="width:100%;height:70px">
							</div>
						</div>
					</div>
				</div>
			</form>
			<div id="listTableToolbar">
				<div class="wd-com wd-3">
					<label class="label">仓库名称:</label>
				</div>
				<input id="warehouseId" name=warehouseId style="margin-left: 0px; margin-right: 18px; padding-top: 0px; padding-bottom: 0px; height: 30px; line-height: 30px; width: 145px;">
				<button type="button" onclick="selectMaterial()" class="listTableToolbarBtn">选择物料</button>
				<button type="button" onclick="detailDelete()" class="listTableToolbarBtn">删除</button>
				<button type="button" onclick="detailClear()" class="listTableToolbarBtn">清空</button>
			</div>
			<div id="hidePanel" class="easyui-panel" data-options="closed:true,border:false" style="height:155%;width:100%">
				<div id="listTable" style="display:none"></div>
			</div>
	</div>
	
	<jsp:include page="/jsp/WinFooter.jsp" />
	<jsp:include page="SelectMaterial.jsp" />
</body>
</html>