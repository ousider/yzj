<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/lib/jquery-easyui-1.4.5/jquery.edatagrid.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/production/SaleAccount.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="tableToolbarList">
		<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
<!-- 		<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button> -->
<!-- 		<button type="button" onclick="onBtnDelete()"class="tableToolbarBtn btn-middle">删除</button> -->
		<button type="button" onclick="onBtnView()"class="tableToolbarBtn btn-middle">查看</button>
	</div>
	<table id="table"></table>

	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
	<form id="editForm" method="post" style="position:relative">
		<div style="display:none">
			<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
		</div>
		<div id="upFiexDiv" style="overflow-y: auto;">
			<div class="collapseField">
			<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
			<div class="fieldContent">
				<div class="widgetGroup">
					<div class="wd-com wd-2"><label class="label">客户名称:</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-textbox" id = "customerId" name="customerId" data-options="prompt:'请选择客户',required:true" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-2"><label class="label">结算日期:</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-datebox" id = "saleAccountDate" name="saleAccountDate" data-options="prompt:'请选择结算日期',required:true,validType:'ltCurrentDate',editable:false" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-2"><label class="label">销售单据号:</label></div>
					<div class="wd-com wd-4">
						<input id = "saleBillId" name="saleBillId" data-options="prompt:'请选择销售单据',required:true" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-1" id="checkboxDiv">&nbsp;&nbsp;<input type="checkbox" onchange="changeBillIdUrl(this)" /></div>
					<div class="wd-com wd-1"><label class="label">总头数:</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-textbox" id = "totalNum" name="totalNum" data-options="readonly:true,disabled:true,precision:2,increment:0.1,min:0,suffix:'元'"style="width:90%;height:32px">
					</div>

					<div class="wd-com wd-2"><label class="label">猪场名称:</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-textbox" id = "farmName" name="farmName" data-options="required:true,disabled:true" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-2"><label class="label">码单号:</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-textbox" id = 'businessCode' name="businessCode" data-options="" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-2"><label class="label">销售日期</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-datebox" id = "saleDate" name="saleDate" data-options="disabled:true" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-1" ></div>
					<div class="wd-com wd-1"><label class="label">总重量:</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-textbox" id = "totalWeight" name="totalWeight" data-options="readonly:true,precision:2,increment:0.1,min:0,suffix:'KG'"style="width:90%;height:32px">
					</div>
				</div>
			</div>
		</div>
		<div class="collapseField">
			<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span><span>进场信息</span></div>
			<div class="fieldContent">
				<div class="widgetGroup">
					<div class="wd-com wd-2"><label class="label">毛猪数量:</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-numberspinner" id = "pigNum" name="pigNum" data-options="prompt:'请输入毛猪头数',required:true,min:1,onChange:pigNumChange" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-2"><label class="label">毛猪重:</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-numberspinner" id = "pigWeight" name="pigWeight" data-options="prompt:'请输入毛猪重',precision:2,required:true,increment:0.1,min:0,suffix:'KG',onChange:pigWeightChange" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-2"><label class="label">毛猪均重:</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-textbox" id = "pigAvgWeight" name="pigAvgWeight" data-options="disabled:true,precision:2,increment:0.1,min:0,suffix:'KG'" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-1" ></div>
					<div class="wd-com wd-1"><label class="label">挂牌价:</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-numberspinner" id = "nominalPrice" name="nominalPrice" data-options="prompt:'请输入挂牌价',required:true,precision:2,increment:0.1,min:0,max:100,onChange:nominalPriceChange"style="width:90%;height:32px">
					</div>
				</div>
			</div>
		</div>
		<div class="collapseField">
			<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span><span>屠宰数据</span></div>
			<div class="fieldContent">		
				<div class="widgetGroup">
					<div class="wd-com wd-2"><label class="label">胴体总重:</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-numberspinner" id = "carcassTotalWeight" name="carcassTotalWeight" data-options="prompt:'请输入胴体总重',required:true,precision:2,increment:0.1,min:0,suffix:'KG',onChange:carcassTotalWeightChange" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-2"><label class="label">出肉率:</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-textbox" id = "meatYield" name="meatYield" data-options="precision:2,disabled:true,increment:0.1,min:0,max:100,suffix:'%',onChange:meatYieldChange" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-2"><label class="label">原膘比例:</label></div>
					<div class="wd-com wd-4">
						<input class="easyui-numberspinner" id = "fatRate" name="fatRate" data-options="prompt:'请输入原膘比例',required:true,precision:2,increment:0.1,min:0,max:100,onChange:fatRateChange" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-1" ></div>
				</div>
			</div>
		</div>
		<div id="accountBasisField" style="display:none" class="collapseField" style="padding-bottom:10px;">
			<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span><span>结算依据</span></div>
			<div class="fieldContent">
				<table id="accountTable" class="com-table">
					<thead>
						<tr>
							<th>项目</th>
							<th>标准</th>
							<th>差额</th>
							<th>奖惩标准</th>
							<th>惩罚结果</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>出肉率</td>
							<td>71.5</td>
							<td>0</td>
							<td>0.2</td>
							<td>0</td>
						</tr>
						<tr>
							<td>原膘比例</td>
							<td>50.0</td>
							<td>0</td>
							<td>0.02</td>
							<td>0</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div id="listTableTZField" style="display:none" class="fieldTitle">屠宰质量</div>
		<!-- listTableTZ的生成在JS中实现 -->
 		<!--<div id="listTableTZ"></div> -->
		<div class="fieldTitle">扣款明细</div>
		<div id="listTable"></div>
		</div>
		
		
		<div class="collapseField">
			<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span><span>结算信息</span></div>
			<div class="fieldContent">			
				<div class="wd-com wd-2"><label class="label">结算依据:</label></div>
				<div class="wd-com wd-4">
					<input class="easyui-numberspinner" id = "accountBasis" name="accountBasis" data-options="required:true,precision:2,onChange:accountBasisChange" style="width:90%;height:32px">
				</div>
				<div class="wd-com wd-2"><label class="label">合计扣款:</label></div>
				<div class="wd-com wd-4">
					<input class="easyui-numberspinner" id = "totalChargebacks" name="totalItemPrice" data-options="required:true,precision:2,onChange:totalChargebacksChange" style="width:90%;height:32px">
				</div>
				<div class="wd-com wd-2"><label class="label">结算总金额:</label></div>
				<div class="wd-com wd-4">
					<input class="easyui-numberspinner" id = "accountPrice" name="totalAccountPrice" data-options="required:true,precision:2,onChange:accountPriceChange" style="width:90%;height:32px">
				</div>
				<div class="wd-com wd-2"><label class="label">结算价:</label></div>
				<div class="wd-com wd-4">
					<input class="easyui-numberspinner" id = "totalAccount" name="totalAccount" data-options="required:true,precision:2" style="width:90%;height:32px">
				</div>
			</div>
		</div>
	</form>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>