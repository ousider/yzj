<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/production/PregnancyCheck.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/SelectPig.js?v=${param.randomnumber}"></script>
</head>
<body onKeyDown="keyBoardDown(event)" onKeyUp="keyBoardUp(event)">
	<div id="editWin" class="easyui-window windowStyle" data-options="title:'新增妊娠检查',draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:false,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
					<input class="easyui-textbox" name="pigType" id="pigType" value="2"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="wd-com wd-3"><label class="label">单据日期:</label></div>
						<div class="wd-com wd-5"><input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入单据日期',required:false,readonly:true" style="width:100%;height:32px"></div>
					</div>
				</div>
			</form>
			<jsp:include page="/jsp/EventListTableToolbar.jsp" />
			<div id="listTable"></div>
	</div>
	<jsp:include page="/jsp/SelectPig.jsp" />
	<jsp:include page="/jsp/EventWinFooter.jsp" />
	<jsp:include page="/jsp/EventImportWin.jsp" />
	<jsp:include page="/jsp/PreSaveRecordWin.jsp" />
</body>
</html>