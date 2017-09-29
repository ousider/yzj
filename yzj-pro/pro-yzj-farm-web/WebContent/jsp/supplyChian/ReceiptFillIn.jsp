<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<link href="${base_url}/lib/jQuery/plugins/viewer.css?v=${webVersion}" rel="stylesheet">
	
	<script src="${base_url}/lib/jquery-easyui-1.4.5/jquery.edatagrid.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/lib/jQuery/plugins/viewer.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/supplyChian/ReceiptFillIn.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none">
		<input type="hidden" id=base_url value="${base_url}"/>
	</div>
	<table id="table"></table>
	<div id="tableToolbarList">
			<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
<!-- 			<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button> -->
<!-- 			<button type="button" onclick="onBtnDelete()"class="tableToolbarBtn btn-middle">删除</button> -->
	</div>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">发票日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入单据日期',validType:'ltCurrentDate',required:true,editable:false" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"></div>
							<div class="wd-com wd-5">
							</div>
							<div class="wd-com wd-3"></div>
							<div class="wd-com wd-5">
							</div>
							<div class="wd-com wd-3"><label class="label">供应商:</label></div>
							<div class="wd-com wd-5">
								<input id="supplierId" name="supplierId" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">公司(法人)</label></div>
							<div class="wd-com wd-5">
								<input id="branchId" name="branchId" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">猪场:</label></div>
							<div class="wd-com wd-5">
								<input id="farmId" name="farmId" style="width:100%;height:35px">
							</div>
							<div style="display:none;">
								<div class="wd-com wd-3"><label class="label">发票物料总金额:</label></div>
								<div class="wd-com wd-5">
									<input class="easyui-numberbox" id="totalReceiptPrice" name="totalReceiptPrice" data-options="readonly:true,precision:2" style="width:100%;height:35px">
								</div>
							</div>
							<div class="wd-com wd-3"><label class="label">入库物料总金额:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-numberbox" id="totalMaterialPrice" name="totalMaterialPrice" data-options="readonly:true,precision:2" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">发票总金额:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-numberbox" id="totalReceiptPriceHaveRransportPrice" name="totalReceiptPriceHaveRransportPrice" data-options="readonly:true,precision:2" style="width:100%;height:35px">
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
			<div id="invoiceTableBar">
				<button type="button" id="invoiceTableAddBtn" onclick="invoiceDetailAdd()" class="listTableToolbarBtn">新增</button>
				<div style="display:inline-block">&nbsp;&nbsp;&nbsp;&nbsp;</div>
				<div style="display:inline-block;vertical-align: middle;">无发票勾选</div>
				<div style="display:inline-block;vertical-align: middle;"><input type="checkbox" id="noReceipt" name="noReceipt" value="Y" onChange="noReceiptChange(this);"/></div>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>发票信息</div>
				<div class="fieldContent" style="padding:0;">
					<table id="invoiceTable"></table>
				</div>
			</div>
			<div id="listTableTableToolbarList">
<!-- 				<button type="button" onclick="onBtnDelete()"class="tableToolbarBtn btn-middle">删除</button> -->
			</div>
			<div id="listTable"></div>
	</div>
	<div id="imgPreviewPanel" class="easyui-dialog" title="图片预览" style="width:700px;height:520px;"
			data-options="resizable:false,modal:true,closed:true">
		<div id="imgPreviewDIV" class="img-preveiw-div" style="width:700px;height:478px;">
			<ul id="imgPreviewUl">
<!-- 				<li style="display:none;"><img id="imgPreview" class="img-preveiw" alt="附件预览"/></li> -->
			</ul>
		</div>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>