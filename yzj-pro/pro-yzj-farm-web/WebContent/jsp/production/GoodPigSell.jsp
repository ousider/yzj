<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/production/GoodPigSell.js?v=${param.randomnumber}"></script>
</head>
<div style="display:none">
	<input type="hidden" name="farmId" id="farmId" value="${userDetail.farmId}"/>
	<input type="hidden" name="farmName" id="farmName" value="${userDetail.farmName}"/>
</div>
<body onKeyDown="keyBoardDown(event)" onKeyUp="keyBoardUp(event)">
	<div id="editWin" class="easyui-window windowStyle" data-options="title:'新增商品猪销售',draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:false,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
					<input class="easyui-textbox" name="pigType" id="pigType" value="3"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="wd-com wd-2"><label class="label">单据日期:</label></div>
						<div class="wd-com wd-5"><input id="billDate" class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入单据日期',required:true,validType:'ltCurrentDate'" style="width:100%;height:32px"></div>
						<div class="wd-com wd-3"><label class="label">销售类型:</label></div>
						<div class="wd-com wd-5"><input id="leaveReason" name="leaveReason" style="width:100%;height:32px"></div>
						<div class="wd-com wd-3"><label class="label">客户:</label></div>
						<div class="wd-com wd-5"><input id="showCustomerId" name="showCustomerId" style="width:100%;height:32px"></div>
						<div class="wd-com wd-2"><label class="label">费用:</label></div>
						<div class="wd-com wd-5"><input id="otherFee" name="otherFee" class="easyui-numberspinner" data-options="prompt:'请输入费用',precision:2,increment:0.1,onChange:otherFeeChange" style="width:100%;height:32px"></div>
						<div class="wd-com wd-3"><label class="label">货款差异:</label></div>
						<div class="wd-com wd-5"><input id="paymentDiff" name="paymentDiff" class="easyui-numberspinner" data-options="prompt:'请输入货款差异',precision:2,increment:0.1,onChange:paymentDiffChange" style="width:100%;height:32px"></div>
						<div class="wd-com wd-3"><label class="label">销售单类型:</label></div>
						<div class="wd-com wd-5"><input id="saleBillType" name="saleBillType" style="width:100%;height:32px"></div>
						<div class="wd-com wd-2"><label class="label">业务员:</label></div>
						<div class="wd-com wd-5"><input id="salesman" name="salesman" style="width:100%;height:32px"></div>
					</div>
				</div>
			</form>
			<div id="toolbar">
				<input id="addNum" class="easyui-numberspinner"  style="width:80px;height:26px;" data-options="value:1,required:true,validType:'range[1,999]'">
				<button id="addPig" type="button" onclick="detailAdd()" class="listTableToolbarBtn">新增</button>
				<button type="button" onclick="detailDelete()" class="listTableToolbarBtn">删除</button>
				<button type="button" onclick="detailClear()" class="listTableToolbarBtn">清空</button>
				<button type="button" onclick="importWinShow()" class="listTableToolbarBtn">导入</button>
				<button id="selectPig" type="button" onclick="selectBreedPig()" class="listTableToolbarBtn">选择种猪</button>
			</div>
			<div id="listTable"></div>
	</div>
	<div id="dlg-buttons" style="height:85px;">
		<div style="height:20px;background-color:#DCEA60;padding:0 10px;text-align:right;">
				<span style="color:red;">合计：&nbsp;&nbsp;</span><span>销售数量：</span><span id="sellNumSum">0</span>&nbsp;&nbsp;<span>总重：</span><span id="weightSum">0.00</span>&nbsp;&nbsp;<span>总金额：</span><span id="moneySum">0.00</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span style="color:red;">平均：&nbsp;&nbsp;</span><span>均重：</span><span id="weightAvg">0.00</span>&nbsp;&nbsp;<span>头均价格：</span><span id="moneyAvg">0.00</span>
		</div>
		<div style="display:inline-block;font-size:12px;padding:0 10px;vertical-align:middle;">
			<span>公式：</span><br>
			<span>①总金额=底重*头数*底重单价+超重*超重单价 ----</span><span style="color:red;">用公式①，只填写“头数、总重、底重、底重单价、超重、超重单价”，其他不填或填零；</span><br>
			<span>②总金额=头单价*头数+超重*超重单价----</span><span style="color:red;">用公式②，只填写“头单价、头数,超重,超重单价”，其他不填或填零；</span>	<br>
			<span>③总金额=总重*重量单价----</span><span style="color:red;">用公式③，只填写“总重、重量单价”，其他不填或填零</span>	
		</div>
		<div style="display:inline-block;float:right;padding:15px;">
			<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn green">保存</button>
			<button type="button" onclick="onBtnReset()" class="winFooterBtn blue">重置</button>
		</div>
	</div>
	<jsp:include page="/jsp/EventImportWin.jsp" />
	<div id="preSaveRecord" class="rightSlipWin_780 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('preSaveRecord',780,true)"></span><span>上一次录入数据</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<div class="wd-com wd-1"></div>
			<div class="wd-com wd-5">单据日期:&nbsp;&nbsp;<span id="headBillDate"></span></div>
			<div class="wd-com wd-6">销售类型:&nbsp;&nbsp;<span id="headLeaveReason"></span></div>
			<div class="wd-com wd-6">销售总重:&nbsp;&nbsp;<span id="headLeaveWeight"></span>KG</div>
			<div class="wd-com wd-6">客户:&nbsp;&nbsp;<span id="headCustomerName"></span></div>
			<div id="preSaveRecordTable"></div>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="rightSlipFun('preSaveRecord',780,true)" class="rightSlipBtn green">关闭</button>
		</div>
	</div>
	<div id="selectBreedPigWin" class="rightSlipWin_780 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('selectBreedPigWin',780)"></span><span>选择种猪</span>
		</div>
		<div class="rightSlipContent heightFiexd">
		<form id="selectPigSearchForm" method="post">
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>查询条件</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-4"><label class="label">耳牌号:</label></div>
						<div class="wd-com wd-8">
							<input id="earBrand" name="earBrand" class="easyui-textbox" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label">猪只状态:</label></div>
						<div class="wd-com wd-8">
							<input id="pigClassId" name="pigClassId" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label">品种:</label></div>
						<div class="wd-com wd-8">
							<input id="breedIds" name="breedIds" class="easyui-textbox" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label">猪只性别:</label></div>
						<div class="wd-com wd-8">
							<input id="sexName" name="sexName" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label">胎次:</label></div>
						<div class="wd-com wd-8">
							<input class="easyui-numberspinner" id="parity" name="parity" data-options="precision:0,increment:1,min:0,max:20" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label">猪舍:</label></div>
						<div class="wd-com wd-8">
							<input id="houseIds" name="houseIds" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label">批次:</label></div>
						<div class="wd-com wd-8">
							<input id="swineryIds" name="swineryIds" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label">留种猪:</label></div>
						<div class="wd-com wd-8">
							<input type="checkbox" id="goodPigFlag" name="goodPigFlag" checked="checked">
						</div>
					</div>
				</div>
			</div>
	 	</form>
			<div id="selectBreedPigTable"></div>
		 </div>
		<div class="rightSlipFooter">
			<button id="searchPigShowBy2" type="button" onclick="searchPigSearch()" class="rightSlipBtn green">搜索</button>
			<button type="button" onclick="selectBreedPigSure()" class="rightSlipBtn blue">确定</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn blue">重置</button>
			<!-- <button id="claseWinShowBy3" type="button" onclick="rightSlipFun('selectBreedPigWin',780)" class="rightSlipBtn green">关闭</button> -->
		</div>
	</div>
</body>
</html>