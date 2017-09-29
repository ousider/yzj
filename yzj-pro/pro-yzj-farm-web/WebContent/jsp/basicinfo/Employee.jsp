<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/basicinfo/Employee.js?v=${param.randomnumber}"></script>
</head>
<body>
<jsp:include page="/jsp/TableToolbar.jsp" />
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',closed:true,fit:true,footer:'#dlg-buttons'">
		<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
				</div>
				<table class="formTable">
					<colgroup width="15%"></colgroup>
					<colgroup width="35%"></colgroup>
					<colgroup width="15%"></colgroup>
					<colgroup width="35%"></colgroup>
					<tr>
						<td>产线代码:</td>
						<td><input class="easyui-textbox" name="businessCode" data-options="prompt:'请输入产线代码',required:true" style="width:100%;height:32px"></td>
						<td>产线名称:</td>
						<td><input class="easyui-textbox" name="lineName" data-options="prompt:'请输入产线名称',required:true" style="width:100%;height:32px"></td>
					</tr>
					<tr rowspn="3">
						<td>备注:</td>
						<td colspan=3><input class="easyui-textbox" name="notes" data-options="prompt:'请输入备注',multiline:true" style="width:100%;height:96px"></td>
					</tr>
				</table>
				
			</form>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>
