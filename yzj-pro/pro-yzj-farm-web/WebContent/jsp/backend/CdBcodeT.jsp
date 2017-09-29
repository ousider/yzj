<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/CdBcodeT.js?v=${param.randomnumber}"></script>
</head>
<body>
	<jsp:include page="/jsp/TableToolbar.jsp" />
	<table id="table"></table>
		<div id="editWin" class="easyui-window windowStyle" data-options="inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons',draggable:false" style="overflow-x:hidden;">
			<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
				</div>
				<table class="formTable">
					<colgroup width="11.11%"></colgroup>
					<colgroup width="22.22%"></colgroup>
					<colgroup width="11.11%"></colgroup>
					<colgroup width="22.22%"></colgroup>
					<colgroup width="11.11%"></colgroup>
					<colgroup width="22.22%"></colgroup>
					<tr>
						<td>业务编码名称:</td>
						<td><input class="easyui-textbox" name="bcodeName" data-options="prompt:'请输入业务编码名称',required:true" style="width:100%;height:32px"></td>
						<td>前缀:</td>
						<td><input class="easyui-textbox" name="prifixCode" data-options="prompt:'请输入前缀',required:true" style="width:100%;height:32px"></td>
						<td>猪舍:</td>
						<td><input id="houseId" name="houseId" style="width:100%;height:32px"></td>
					</tr>
					<tr>
						<td>是否时间:</td>
						<td><input id="isUseBdate" name="isUseBdate" style="width:100%;height:32px"></td>
						<td>流水码长度:</td>
						<td><input class="easyui-textbox" name="serialLength" data-options="prompt:'请输入流水码长度',required:true" style="width:100%;height:32px"></td>
						<td>起始编码:</td>
						<td><input class="easyui-textbox" name="limitNum" data-options="prompt:'请输入起始编码',required:true" style="width:100%;height:32px"></td>
					</tr>
					<tr rowspn="3">
						<td>备注:</td>
						<td colspan=3><input class="easyui-textbox" name="notes" data-options="prompt:'请输入备注',multiline:true" style="width:100%;height:96px"></td>
					</tr>
				</table>
				<br>
			</form>
			</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>
