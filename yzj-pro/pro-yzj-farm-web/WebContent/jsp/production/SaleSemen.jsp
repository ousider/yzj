<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/production/SaleSemen.js?v=${param.randomnumber}"></script>
</head>
<body onKeyDown="keyBoardDown(event)" onKeyUp="keyBoardUp(event)">
	<div id="editWin" class="easyui-window windowStyle" data-options="title:'精液销售',draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:false,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
					<input class="easyui-textbox" name="pigType" id="pigType" value="3"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">单据日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入单据日期',required:false,validType:'ltCurrentDate',readonly:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">客户:</label></div>
							<div class="wd-com wd-5">
							<input id="customerId" name="customerId" style="width:100%;height:32px">
						</div>
						</div>
					</div>
				</div>
			</form>
			<div id="toolbar">
				<input id="addNum" class="easyui-numberspinner" style="width:80px;height:26px;" data-options="value:1,required:true,validType:'range[1,999]'">
				<button type="button" onclick="detailAdd()" class="listTableToolbarBtn">新增</button>
				<button type="button" onclick="detailDelete()" class="listTableToolbarBtn">删除</button>
				<button type="button" onclick="detailClear()" class="listTableToolbarBtn">清空</button>
				<button type="button" onclick="chooseSperm()" class="listTableToolbarBtn">选择精液</button>
				<button type="button" onclick="onBtnReset()" class="listTableToolbarBtn">重置</button> 
			</div>
			<div id="listTable"></div>
	</div>
	<!--选择精液  -->
	<div id="chooseSpermWin" class="rightSlipWin_780 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('chooseSpermWin',780,true)"></span><span>选择精液</span>
		</div>
		<div class="rightSlipContent heightFiexd">
		<form id="chooseSpermForm" method="post">
			<div class="widgetGroup">
				<div class="wd-com wd-4"><label class="label">公猪耳号:</label></div>
				<div class="wd-com wd-8">
					<input class="easyui-textbox" id="earBrand" name="earBrand" style="width:100%;height:35px">
				</div>
		 		<div class="wd-com wd-4"><label class="label">品种:</label></div>
				<div class="wd-com wd-8">
					<input class="easyui-textbox" id="breedName" name="breedName" style="width:100%;height:35px">
				</div>
		 		<div class="wd-com wd-4"><label class="label">精液批号:</label></div>
				<div class="wd-com wd-8">
					<input class="easyui-textbox" id="semenBatchNo" name="semenBatchNo"  style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-4"><label class="label">采精日期:</label></div>
				<div class="wd-com wd-8">
					<input class="easyui-datebox"  id="semenDate" name="semenDate" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-4"><label class="label">有效日期:</label></div>
				<div class="wd-com wd-8">
					<input class="easyui-datebox"  id="validDate" name="validDate" style="width:100%;height:35px">
				</div>
			</div>
	 	</form>
			<div id="chooseSpermTable"></div>
		 </div>
		<div class="rightSlipFooter">
			<button id="searchPigShowBy2" type="button" onclick="chooseSpermSearch()" class="rightSlipBtn green">搜索</button>
			<button type="button" id="selectSpermSure" onclick="chooseSpermSure()" class="rightSlipBtn blue">确定</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn blue">重置</button>
		</div>
	</div>
	<!--  -->
	<div id="dlg-buttons" style="height:65px;">
		<div style="height:35px;text-align:right;padding:5px;">
			<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn green">保存</button>
<!-- 			<button type="button" id="btnCancel" onclick="onBtnCancel()" class="winFooterBtn green">取消</button> -->
			<button type="button" onclick="onBtnCancel()" class="winFooterBtn green">取消</button>
<!-- 			<button type="button" onclick="onBtnReset()" class="winFooterBtn blue">重置</button> -->
		</div>
	</div>
	<div id="spermSelect" class="rightSlipWin_390 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('spermSelect',390,true)"></span><span>精液选择</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<input id="selectedIndex" name="selectedIndex" type="hidden" />
			<div id="spermTable"></div>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSelectSperm()" class="rightSlipBtn blue">确定</button>
			<button type="button" onclick="rightSlipFun('spermSelect',390,true)" class="rightSlipBtn green">取消</button>
		</div>
	</div>
	<jsp:include page="/jsp/EventImportWin.jsp" />
	<jsp:include page="/jsp/PreSaveRecordWin.jsp" />
</body>
</html>