<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/CdBcode.js?v=${param.randomnumber}"></script>
</head>
<body>
	<jsp:include page="/jsp/TableToolbar.jsp" />
	<div id="advancedSearch" onClick="leftSilpFun('cdBcodeAdSearch')"><span>高级查询</span></div>
	<div id="cdBcodeAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('cdBcodeAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">类型编码:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="typeCode" data-options="prompt:'请输入类型编码'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">业务编码名称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="bcodeName" data-options="prompt:'请输入业务编码名称'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">前缀:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="prifixCode" data-options="prompt:'请输入前缀'" style="width:100%;height:35px">
				</div>
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons',draggable:false" style="overflow-x:hidden;">
			<form id="editForm" method="post" class="form-inline">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
				</div>
				
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">业务编码名称:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" name="bcodeName" data-options="prompt:'请输入业务编码名称',required:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">编码类型:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" name="typeCode" data-options="prompt:'请输入编码类型',required:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">流水码长度:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" name="serialLength" data-options="prompt:'请输入流水码长度',required:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">编码级别:</label></div>
							<div class="wd-com wd-5">
								<input id="level" name="level" style="width:100%;height:32px">
							</div>
							<div class="wd-com wd-3"><label class="label">前缀:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" name="prifixCode" data-options="prompt:'请输入前缀'" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">是否时间:</label></div>
							<div class="wd-com wd-5">
								<input id="isUseBdate" name="isUseBdate" style="width:100%;height:32px">
							</div>
							<div class="wd-com wd-3"><label class="label">起始编码:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" name="limitNum" data-options="prompt:'请输入起始编码'" style="width:100%;height:35px">
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
			</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>
