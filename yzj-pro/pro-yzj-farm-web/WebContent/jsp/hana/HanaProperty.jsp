<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/hana/HanaProperty.js?v=${param.randomnumber}"></script>
</head>
<body>
	
	<div id="hanaPropertyTableToolbar">
		<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
		<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看</button>
		<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button>
<!-- 		<button type="button" onclick="onBtnCopyAdd()"class="tableToolbarBtn btn-middle">复制新增</button> -->
		<button type="button" onclick="onBtnAddDbAndFarm()"class="tableToolbarBtn btn-middle" style="width:120px;">数据源和猪场</button>
	</div>
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input type="hidden" name="rowId" id="rowId" value="0"/>
			</div>
			<div id="dbInfo">
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="wd-com wd-3"><label class="label">数据源名称:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" id="beanName" name="beanName" data-options="required:true,onChange:textChange" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">数据源地址及端口号:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" id="ipAndPort" name="ipAndPort" data-options="required:true,onChange:textChange" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">DB名:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" id="dbName" name="dbName" data-options="required:true,onChange:textChange" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">数据库账号:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" id="dbUserName" name="dbUserName" data-options="required:true,onChange:textChange" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">密码:</label></div>
						<div class="wd-com wd-5"> 
							<input class="easyui-textbox" id="dbPassword" name="dbPassword" data-options="type:'password',required:true,onChange:textChange" style="width:100%;height:35px">
						</div>
					</div>
				</div>
			</div>
			<div id="dbAndFarmInfo">
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="wd-com wd-3"><label class="label">数据源名称:</label></div>
						<div class="wd-com wd-5">
							<input id="beanNameSelect" name="beanNameSelect" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">猪场:</label></div>
						<div class="wd-com wd-5">
							<input id="farmIds" name="farmIds" style="width:100%;height:32px">
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div id="dbAndFarm" class="rightSlipWin_390 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('dbAndFarm',390)"></span><span>猪场</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<input id="dbAndFarmTable"/>
		 </div>
	</div>
	<div id="dlg-buttons" style="height:35px;text-align:right;padding:5px;">
		<button type="button" id="testButton" onclick="testDbConnect()" class="tableToolbarBtn btn-middle">连接测试</button>
		<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn blue">保存</button>
		<button type="button" onclick="onBtnCancel()" class="winFooterBtn green">取消</button>
	</div>
</body>
</html>
