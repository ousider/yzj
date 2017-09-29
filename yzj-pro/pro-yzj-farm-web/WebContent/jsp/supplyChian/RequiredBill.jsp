<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/supplyChian/RequiredBill.js?v=${param.randomnumber}"></script>
</head>
<body>
	<table id="table"></table>
	<div id="tableToolbarList">
<!-- 			<button type="button" onclick="onBtnAddPigRequire()" class="tableToolbarBtn btn-middle" style="width:120px;">新增猪只需求单</button> -->
			<button type="button" onclick="onBtnChangeCreateBillType()" class="tableToolbarBtn btn-middle" style="width:120px;">新增物料需求单</button>
			<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">修改</button>
			<button type="button" onclick="onBtnScrap()" class="tableToolbarBtn btn-middle">作废</button>
			<button type="button" onclick="onBtnSubmit()" class="tableToolbarBtn btn-middle">提交</button>
			<button type="button" onclick="onBtnCopyAdd()" class="tableToolbarBtn btn-middle">复制新增</button>
<!-- 			<button type="button" onclick="onBtnComplete()" class="tableToolbarBtn btn-middle">完成</button> -->
	</div>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input type="hidden" name="rowId" id="rowId" value="0"/>
				<input type="hidden" id="billType" name="billType" value="0"/>
				<input type="hidden" name="applyUnitId" value="${userDetail.farmId}"/>
<%-- 				<input type="hidden" id="farmName" name="farmName" value="${userDetail.farmName}"/> --%>
				<input type="hidden" id="copyId" name="copyId" value=""/>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>需求信息</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">单据日期:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入单据日期',required:true,editable:false" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">申请单位:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="applyUnitName" data-options="value:'${userDetail.farmName}',disabled:true" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">核算单位:</label></div>
						<div class="wd-com wd-5">
							<input id="accountsUnitId" name="accountsUnitId" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">申报类型:</label></div>
						<div class="wd-com wd-5">
							<input id="applyType" name="applyType" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">需求类型:</label></div>
						<div class="wd-com wd-5">
							<input id="requireType" name="requireType" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">紧急程度:</label></div>
						<div class="wd-com wd-5">
							<input id="emergencyType" name="emergencyType" style="width:100%;height:32px">
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
			<button type="button" onclick="selectMaterial()" class="listTableToolbarBtn">选择物料</button>
			<button type="button" onclick="detailDelete()" class="listTableToolbarBtn">删除</button>
		</div>
		<div id="listTable"></div>
	</div>
	<div id="scrapReasonWin" class="easyui-dialog" title="作废原因" style="width:300px;height:150px;"
			data-options="resizable:false,modal:true,closed:true">
		<div class="widgetGroup" style="padding:20px 0 0 0;">
			<div class="wd-com wd-6"><label class="label" style="font-size:12px !important;">作废原因:</label></div>
			<div class="wd-com wd-16">
				<input class="easyui-textbox" id="scrapReason" name="scrapReason" data-options="prompt:'请输入作废原因'" style="width:100%;height:32px">
			</div>
		</div>
		<div class="dialog-button messager-button" style="width:290px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "scrapReasonEnter()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "dialogCancel('scrapReasonWin')">取消</a>
		</div>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
	<jsp:include page="SelectMaterial.jsp" />
</body>
</html>
