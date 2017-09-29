<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/production/Wean.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/SelectPig.js?v=${param.randomnumber}"></script>
</head>
<body onKeyDown="keyBoardDown(event)" onKeyUp="keyBoardUp(event)">
	<div id="editWin" class="easyui-window windowStyle" data-options="title:'新增断奶',draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:false,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
					<input class="easyui-textbox" name="pigType" id="pigType" value="2"/>
					<input type="hidden" name="dnxycbbFlag" id="dnxycbbFlag" value="off"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="wd-com wd-3"><label class="label">单据日期:</label></div>
						<div class="wd-com wd-5"><input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入单据日期',required:false,validType:'ltCurrentDate',readonly:true" style="width:100%;height:32px"></div>
						<div class="wd-com wd-3"><label class="label">自动寄养死亡:</label></div>
						<div class="wd-com wd-5">
							<input type="checkbox" id="autoFosterDie" name="autoFosterDie" onchange="autoFosterDieFun(this)">
						</div>
					</div>
				</div>
			</form>
			<jsp:include page="/jsp/EventListTableToolbar.jsp" />
			<div id="listTable"></div>
	</div>
	<div id="dlg-buttons" style="height:65px;">
		<div style="height:20px;background-color:#DCEA60;padding:0 10px;text-align:right;">
				<span style="color:red;">合计：&nbsp;&nbsp;</span><span>断奶仔猪数：</span><span id="weanNumSum">0</span>&nbsp;&nbsp;<span>死亡仔猪数：</span><span id="dieNumSum">0</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span>断奶窝均重：</span><span id="weanWeightAvg">0</span>
				<span>窝均断奶数：</span><span id="weanNumAvg">0</span>
		</div>
		<div style="height:35px;text-align:right;padding:5px;">
			<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn green">保存</button>
			<button type="button" onclick="onBtnReset()" class="winFooterBtn blue">重置</button>
		</div>
	</div>
	<jsp:include page="/jsp/SelectPig.jsp" />
	<jsp:include page="/jsp/EventImportWin.jsp" />
	<jsp:include page="/jsp/PreSaveRecordWin.jsp" />
</body>
</html>