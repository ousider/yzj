<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/production/ChangeLaborHouse.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/SelectPig.js?v=${param.randomnumber}"></script>
</head>
<body onKeyDown="keyBoardDown(event)" onKeyUp="keyBoardUp(event)">
	<div id="editWin" class="easyui-window windowStyle" data-options="title:'新增转产房',draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:false,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
					<input class="easyui-textbox" name="pigType" id="pigType" value="2"/>
					<input type="hidden" name="zcfxycbbFlag" id="zcfxycbbFlag" value="off"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="wd-com wd-3"><label class="label">单据日期:</label></div>
						<div class="wd-com wd-5"><input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入单据日期',required:false,validType:'ltCurrentDate',readonly:true" style="width:100%;height:32px"></div>
					</div>
				</div>
			</form>
			<jsp:include page="/jsp/EventListTableToolbar.jsp" />
			<div id="listTable"></div>
	</div>
	<div id="dlg-buttons" style="height:64px;">
		<div style="display:inline-block;font-size:14px;padding:0 10px;vertical-align:middle;">
			<span style="color:red;">哺乳母猪带奶转舍（不带小猪转）请到奶妈转舍页面操作</span><br>
		</div>
		<div style="display:inline-block;float:right;padding:15px;">
			<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn green">保存</button>
			<button type="button" onclick="onBtnReset()" class="winFooterBtn blue">重置</button>
		</div>
	</div>
	<jsp:include page="/jsp/SelectPig.jsp" />
	<jsp:include page="/jsp/EventWinFooter.jsp" />
	<jsp:include page="/jsp/EventImportWin.jsp" />
	<jsp:include page="/jsp/PreSaveRecordWin.jsp" />
</body>
</html>