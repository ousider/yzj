<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/production/PigMoveIn.js?v=${param.randomnumber}" ></script>
</head>
<body onKeyDown="keyBoardDown(event)" onKeyUp="keyBoardUp(event)">
	<div id="editWin" class="easyui-window windowStyle" data-options="title:'新增猪只入场',draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:false,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="wd-com wd-2"><label class="label">猪类别:</label></div>
						<div class="wd-com wd-5"><div id="pigType"></div></div>
						<div class="wd-com wd-3"><label class="label">入场方式:</label></div>
						<div class="wd-com wd-5"><div id="enterType"></div></div>
						<div class="wd-com wd-3"><label class="label">单据日期:</label></div>
						<div class="wd-com wd-5"><input class="easyui-datebox" id="enterDate" name="enterDate" data-options="prompt:'请输入单据日期',required:true,validType:'ltCurrentDate'" style="width:100%;height:32px"></div>
						<div class="wd-com wd-2"></div>
						<div class="wd-com wd-5"><input id="isSell" name="isSell" type="checkBox" disabled onchange="isSellChange(this)">是否选择相关销售单据</div>
						<div class="wd-com wd-3"><label class="label">销售单据:</label></div>
						<div class="wd-com wd-5"><input id="billId" name="billId" style="width:100%;height:32px"></div>
						<div class="wd-com wd-8"></div>
					</div>
				</div>
				<div id="hideTable" class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>批次入场信息</div>
					<div class="fieldContent">
						<div class="wd-com wd-2"><label class="label">物料名称:</label></div>
						<div class="wd-com wd-5"><input id="materialId" name="materialId" style="width:100%;height:32px"></div>
						<div class="wd-com wd-3"><label class="label">猪只状态:</label></div>
						<div class="wd-com wd-5"><input id="pigClass" name="pigClass" style="width:100%;height:32px"></div>
						<div class="wd-com wd-3"><label class="label">供应商:</label></div>
						<div class="wd-com wd-5"><input id="supplierId" name="supplierId" style="width:100%;height:32px"></div>
						<div class="wd-com wd-2" style="display:none;"><label class="label">产线:</label></div>
						<div class="wd-com wd-5" style="display:none;"><input id="lineId" name="lineId" style="width:100%;height:32px"></div>
						<div class="wd-com wd-2"><label class="label">猪舍:</label></div>
						<div class="wd-com wd-5"><input id="houseId" name="houseId" style="width:100%;height:32px"></div>
						<div class="wd-com wd-3"><label class="label">猪只批次:</label></div>
						<div class="wd-com wd-5"><input id="swineryId" name="swineryId" style="width:100%;height:32px"></div>
						<div class="wd-com wd-3"><label class="label">数量:</label></div>
						<div class="wd-com wd-5"><input  id="totalNum" name="totalNum" class="easyui-numberspinner" data-options="prompt:'请输入数量',required:true" data-options="min:1" style="width:100%;height:32px"></div>
						<div class="wd-com wd-2"><label class="label">均重:</label></div>
						<div class="wd-com wd-5"><input  id="averageWeight" name="averageWeight" class="easyui-numberspinner" data-options="prompt:'请输入均重',precision:2,increment:0.1,min:0,max:500,suffix:'KG'"style="width:100%;height:32px"></div>
						<div class="wd-com wd-3"><label class="label">均价:</label></div>
						<div class="wd-com wd-5"><input  id="averagePrice" name="averagePrice" class="easyui-numberspinner" data-options="prompt:'请输入均价',precision:2,increment:0.1,min:0,max:100000,suffix:'元'"style="width:100%;height:32px"></div>
						<div class="wd-com wd-3"><label class="label">日龄:</label></div>
						<div class="wd-com wd-5"><input id="dayAge" name="dayAge" class="easyui-numberspinner" data-options="prompt:'出生日期=单据日期-日龄',required:true,min:1,max:2000" style="width:100%;height:32px"></div>
					</div>
				</div>
			</form>
			<div id="hidePanel" class="easyui-panel" data-options="closed:true,border:false" style="height:195%;width:100%">
				<div id="listTable" style="display:none"></div>
				<jsp:include page="/jsp/ListTableToolbarAndExcelIX.jsp" />
			</div>
			<div id="dlg-buttons" style="height:85px;">
				<div id="total-plan"style="height:20px;background-color:#DCEA60;padding:0 10px;text-align:right;">
						<span style="color:red;">合计：&nbsp;&nbsp;</span><span>数量：</span><span id="sellNumSum">0</span>&nbsp;&nbsp;<span>总重：</span><span id="weightSum">0.00</span>&nbsp;&nbsp;<span>总金额：</span><span id="moneySum">0.00</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<!-- <span style="color:red;">平均：&nbsp;&nbsp;</span><span>均重：</span><span id="weightAvg">0.00</span>&nbsp;&nbsp;<span>头均价格：</span><span id="moneyAvg">0.00</span> -->
				</div>
				<div style="height:35px;text-align:right;padding:5px;">
					<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn green">保存</button>
					<button type="button" onclick="onBtnReset()" class="winFooterBtn blue">重置</button>
				</div>
			</div>
	</div>
	<%-- <jsp:include page="/jsp/EventImportWin.jsp" /> --%>
	<jsp:include page="/jsp/PreSaveRecordWin.jsp" />
	<jsp:include page="/jsp/EventImportWin.jsp" />
</body>
</html>