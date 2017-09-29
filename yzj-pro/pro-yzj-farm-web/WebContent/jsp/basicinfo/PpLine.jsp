<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/basicinfo/PpLine.js?v=${param.randomnumber}"></script>
</head>
<body>
<jsp:include page="/jsp/TableToolbar.jsp" />
	<div id="advancedSearch" onClick="leftSilpFun('advancedSearchId')"><span>高级查询</span></div>
	<div id="advancedSearchId" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('advancedSearchId',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">产线代码:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="businessCode" data-options="prompt:'请输入产线代码'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">产线名称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="lineName" data-options="prompt:'请输入产线名称'" style="width:100%;height:35px">
				</div>
			 </form>
		 </div>
		 <div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch()" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
			<form id="editForm" method="post" class="form-inline">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div>
						<!-- <div class="wd-com wd-3"><label class="label">产线代码:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="businessCode" data-options="prompt:'请输入产线代码',required:true,validType:'numOrLetterOr_'" style="width:100%;height:32px">
						</div> -->
						<div class="wd-com wd-3"><label class="label">产线名称:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="lineName" data-options="prompt:'请输入产线名称',required:true,validType:'numOrLetterOrChineseOr_'" style="width:100%;height:32px">
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
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>
