<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/supplyChian/MaterialCount.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="editWin" class="easyui-window windowStyle" data-options="title:'物料期末盘点',draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:false,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input type="hidden" name="rowId" id="rowId"/>
				<input type="hidden" name="supplychianEventCode" id="supplychianEventCode"/>
			</div>
			<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>盘点信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">盘点日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入领用日期',required:true,value:getCurrentDate(),readonly:true" style="width:100%;height:32px">
							</div>
 							<div class="wd-com wd-3"><label class="label">盘点仓库:</label></div>
							<div class="wd-com wd-5"> 
								<input id="warehouseId" name="warehouseId" style="width:100%;height:32px">
							</div> 
						</div>
					</div>
			</div>
		</form>
		<div id="listTable"></div>
		<div id="dlg-buttons" style="height:35px;text-align:right;padding:5px;">
			<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn blue">保存</button>
			<button type="button" onclick="" style="visibility:hidden;" class="winFooterBtn green">取消</button>
		</div>
	</div>
</body>
</html>