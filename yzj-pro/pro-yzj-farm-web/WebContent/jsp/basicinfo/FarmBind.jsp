<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/basicinfo/FarmBind.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none">
		<input type="hidden" id="farmId" name="farmId" value="${userDetail.farmId}"> 
	</div>
	<div id="tableToolbar">
		<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" onclick="onBtnBindFarm()" class="tableToolbarBtn btn-middle">同步猪场</button>
		<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button>
	</div>
	<table id="table"></table>
	
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'">
		<form id="editForm" method="post">
			<div style="display:none">
				<input class="easyui-textbox" name="rowId" id="rowId"/>
				<input type="hidden" id="companyId" name="companyId"> 
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>账号信息</div>
				<div class="fieldContent">
					<div class="wd-com wd-3"><label class="label">公司编码:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="companyCode"  data-options="prompt:'请输入公司编码',required:true,validType:'numOrLetterOrChineseOr_'"  style="width:90%;height:35px">
					</div>
					<div class="wd-com wd-3"><label class="label">用户名:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="userName" data-options="prompt:'请输入用户名',required:true" style="width:90%;height:35px">
					</div>
					<div class="wd-com wd-3"><label class="label">密码:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="password" type="password" data-options="prompt:'请输入密码',required:true" style="width:90%;height:35px">
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
	<div id="bindFarmWin" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('bindFarmWin',390,true);"></span><span>同步猪场</span>
		</div>
		<div class="rightSlipContent heightFiexd" id="treeTableDiv">
			<div id="treeTable"></div>
        </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="saveBindFarm()" class="rightSlipBtn blue">确认</button>
			<button type="button" onclick="rightSlipFun('bindFarmWin',390,true);" class="rightSlipBtn green">取消</button>
		</div>
	</div>
</body>
</html>