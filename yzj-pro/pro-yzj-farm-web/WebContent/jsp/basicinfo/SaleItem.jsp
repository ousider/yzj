<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/basicinfo/SaleItem.js?v=${param.randomnumber}"></script>
</head>
<body>
	<table id="table"></table>
	<div id="tableToolbarList">
<!-- 		<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button> -->
		<button type="button" onclick="onBtnView()"class="tableToolbarBtn btn-middle">查看</button>
		<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
<!-- 		<button type="button" onclick="onBtnDelete()"class="tableToolbarBtn btn-middle">删除</button> -->
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
					<div class="wd-com wd-3"><label class="label">客户:</label></div>
					<div class="wd-com wd-5">
						<input class="easyui-textbox" name="customerName" data-options="prompt:'请输入分类代码',required:true" style="width:100%;height:35px">
					</div>
				</div>
			</div>
		</div>
		</form>
		<jsp:include page="/jsp/ListTableToolbar.jsp" />
		<div id="listTable"></div>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>