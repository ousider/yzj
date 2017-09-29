<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/production/SemenInquiry.js?v=${param.randomnumber}"></script>
</head>
<body onKeyDown="keyBoardDown(event)" onKeyUp="keyBoardUp(event)">
	<div id="editWin" class="easyui-window windowStyle" data-options="title:'新增精液入库',draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:false,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
					<input class="easyui-textbox" name="pigType" id="pigType" value="3"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">单据日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入单据日期',required:false,validType:'ltCurrentDate',readonly:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">精液来源:</label></div>
							<div class="wd-com wd-5"><input class="easyui-textbox" id="semenOrigin" name="origin" style="width:100%;height:32px"></div>
							<div class="wd-com wd-3"><label class="label">销售单据:</label></div>
							<div class="wd-com wd-5"><input class="easyui-textbox" id="billId" name="billId" style="width:100%;height:32px"></div>
						</div>
					</div>
				</div>
			</form>
			<div id="toolbar">
				<input id="addNum" class="easyui-numberspinner" style="width:80px;height:26px;" data-options="value:1,required:true,validType:'range[1,999]'">
				<button id ="addDetail" type="button" onclick="detailAdd()" class="listTableToolbarBtn">新增</button>
				<button id ="deleteDetail" type="button" onclick="detailDelete()" class="listTableToolbarBtn">删除</button>
				<button id ="clearDetail" type="button" onclick="detailClear()" class="listTableToolbarBtn">清空</button>
			</div>
			<div id="listTable"></div>
	</div>
	<div id="dlg-buttons" style="height:45px;">
		<div style="height:35px;text-align:right;padding:5px;">
			<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn green">保存</button>
			<button type="button" onclick="onBtnReset()" class="winFooterBtn blue">重置</button>
		</div>
	</div>
	<jsp:include page="/jsp/EventImportWin.jsp" />
	<jsp:include page="/jsp/PreSaveRecordWin.jsp" />
</body>
</html>