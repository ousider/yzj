<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/basicinfo/PushMessage.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="tableToolbarList">
		<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
		<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看</button>
		<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button>
		<button type="button" onclick="onBtnCopyAdd()"class="tableToolbarBtn btn-middle">复制新增</button>
		<button type="button" onclick="onBtnDisableEnable()" class="tableToolbarBtn btn-middle">停用启用</button>
	</div>
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post">	
			<div style="display:none">
				<input class="easyui-textbox" name="rowId" id="rowId"/>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span id="pushMessageInfo" class="arrowUp" onclick="upOrDown(this)"></span>消息推送</div>
				<div class="fieldContent">
				<div class="widgetGroup">
					<div class="wd-com wd-3"><label class="label">消息代码:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="messageCode" data-options="required:true" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">消息标题:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="messageTitle"  data-options="required:true"  style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">消息类型:</label></div>
					<div class="wd-com wd-5"> 
						<input id="messageType" name="messageType" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">描述:</label></div>
					<div class="wd-com wd-21"> 
						<input class="easyui-textbox"  name="description" data-options="multiline:true" style="width:100%;height:70px">
					</div>
					<div class="wd-com wd-3"><label class="label">图片URL:</label></div>
					<div class="wd-com wd-21"> 
						<input class="easyui-textbox" id="picUrl" name="picUrl" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">内容URL:</label></div>
					<div class="wd-com wd-21"> 
						<input class="easyui-textbox" id="contentUrl" name="contentUrl" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">内容:</label></div>
					<div class="wd-com wd-21"> 
						<input class="easyui-textbox" id="content" name="content" data-options="multiline:true,required:true" style="width:100%;height:70px">
					</div>
				</div>
			</div>
			</div>
		</form>
		<div id="listTableToolbar">
		<button type="button" onclick="onBtnDetailAdd()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" onclick="detailDelete()" class="tableToolbarBtn btn-middle">删除</button>
		<button type="button" onclick="detailClear()" class="tableToolbarBtn btn-middle">清空</button>
		</div>
		<div id="listTable"></div>
	</div>
	<div id="chooseUserWin" class="rightSlipWin_780 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('chooseUserWin',780,true)"></span><span>选择人员</span>
		</div>
		<div class="rightSlipContent heightFiexd">
		<form id="chooseSpermForm" method="post">
			<div class="widgetGroup">
				<div class="wd-com wd-10"><label class="label">人员名称:</label></div>
				<div class="wd-com wd-10">
					<input class="easyui-textbox" id="employeeName" name="employeeName" style="width:100%;height:35px">
				</div>
			</div>
	 	</form>
		<div id="chooseUserTable"></div>
		 </div>
		<div class="rightSlipFooter">
			<button id="searchPigShowBy2" type="button" onclick="chooseUserSearch()" class="rightSlipBtn green">搜索</button>
			<button type="button" onclick="chooseUserSure()" class="rightSlipBtn blue">确定</button>
		</div>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>