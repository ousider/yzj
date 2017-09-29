<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<link href="${base_url}/lib/jQuery/plugins/viewer.css?v=${webVersion}" rel="stylesheet">
	<script src="${base_url}/lib/jquery-easyui-1.4.5/jquery.edatagrid.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/lib/jQuery/plugins/viewer.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/basicinfo/GroupHrCompanyClientele.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none">
		<input type="hidden" id=base_url value="${base_url}"/>
	</div>
	<div id="tableToolbar">
		<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
		<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看</button>
		<button type="button" onclick="editCustomerAndSupplierToFarms()" class="tableToolbarBtn btn-middle">同步</button>				
		<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button>
	</div>
	<div id="advancedSearch" onClick="leftSilpFun('hrCompanyClienteleAdSearch')"><span>高级查询</span></div>
	<div id="hrCompanyClienteleAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('hrCompanyClienteleAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">客户全称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="companyName" data-options="prompt:'请输入供应商全称'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">客户简称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="sortName" data-options="prompt:'请输入供应商简称'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">创建时间:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-datebox" name="createDate" data-options="prompt:'请输入创建时间'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">创建人:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="createrName" data-options="prompt:'请输入创建人'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">最近变更时间:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-datebox" name="changeDate" data-options="prompt:'请输入最近变更时间'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">变更人:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="changeName" data-options="prompt:'请输入变更人'" style="width:100%;height:35px">
				</div>				
				<input type="hidden" name="cussupType" value="C" />
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
			<form id="editForm" method="post" class="form-inline" style="height:100%;">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
					<input type="hidden" name="cussupType" value="C">
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基本信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">创建类型:</label></div>
							<div class="wd-com wd-5">
								<input id="createType" class="easyui-combobox"  name="createType"  style="width:100%;height:35px">							
							</div>
							<div class="wd-com wd-3"><label class="label">客户全称:</label></div>
							<div id="companyNameDiv" class="wd-com wd-5">
								<input id="cussupId" name="cussupId" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">客户简称:</label></div>
							<div class="wd-com wd-5">
								<input id="sortName" class="easyui-textbox" name="sortName" data-options="prompt:'请输入客户简称',required:true,validType:'chinese'" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">客户类型:</label></div>
							<div class="wd-com wd-5">
								<input id="customerType" class="easyui-textbox" name="customerType" data-options="prompt:'请选择客户类型',required:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">母猪规模:</label></div>
							<div class="wd-com wd-4">
								<input id="sowSize" class="easyui-textbox" name="sowSize" data-options="prompt:'请输入母猪规模'" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-1"><label class="label">头</label></div>
							<div class="wd-com wd-3"><label class="label">客户等级:</label></div>
							<div class="wd-com wd-5">
								<input id="customerLevel" class="easyui-textbox" name="customerLevel" data-options="prompt:'请选择客户等级'" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">上级客户:</label></div>
							<div class="wd-com wd-5">
								<input id="customerParent" name="customerParent" data-options="prompt:'请选择上级客户'" style="width:100%;height:35px">
							</div>
				<div style="display:none">							
							<div class="wd-com wd-3"><label class="label">经销/直销:</label></div>
							<div class="wd-com wd-5">
								<input id="sellType" class="easyui-textbox" name="sellType" data-options="prompt:'请选择经销/直销'" style="width:100%;height:35px">
							</div>															
							<div class="wd-com wd-3"><label class="label">销售管理大区:</label></div>
							<div class="wd-com wd-5">
								<input id="sellDivision" class="easyui-textbox" name="sellDivision" data-options="prompt:'请选择销售管理大区'" style="width:100%;height:35px">
							</div>	
							<div class="wd-com wd-3"><label class="label">销售管理单元:</label></div>
							<div class="wd-com wd-5">
								<input id="sellArea" class="easyui-textbox" name="sellArea" data-options="prompt:'请选择销售管理单元'" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">财务管理单元:</label></div>
							<div class="wd-com wd-5">
								<input id="financeArea" class="easyui-textbox" name="financeArea" data-options="prompt:'请选择财务管理单元'" style="width:100%;height:35px">
							</div>							
							<div class="wd-com wd-3"><label class="label">销售业务员:</label></div>
							<div class="wd-com wd-5">
								<input id="salesman" class="easyui-textbox" name="salesman" data-options="prompt:'请选择销售业务员'" style="width:100%;height:35px">
							</div>
				</div>							
						</div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>收款结算</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">结算货币:</label></div>
							<div class="wd-com wd-5">
								<input id="tradeCurrency" class="easyui-textbox" name="tradeCurrency" data-options="prompt:'请选择结算货币',required:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">收款条件:</label></div>
							<div class="wd-com wd-5">
								<input id="collectCondition" class="easyui-textbox" name="collectCondition" data-options="prompt:'请选择收款条件',required:true" style="width:100%;height:35px">
							</div>	
							<div class="wd-com wd-3"><label class="label">收款账期:</label></div>
							<div class="wd-com wd-5">
								<input id="collectDays" class="easyui-textbox" name="collectDays" data-options="prompt:'请选择收款账期',required:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">信用额度:</label></div>
							<div class="wd-com wd-4">
								<input id="creditLimit" class="easyui-textbox" name="creditLimit" data-options="prompt:'请输入信用额度',required:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-1"><label class="label">元</label></div>							
							<div class="wd-com wd-3"><label class="label">每月固定结算日:</label></div>
							<div class="wd-com wd-4">
								<input id="fixedBalanceDay" class="easyui-textbox" name="fixedBalanceDay" data-options="prompt:'请输入每月固定结算日'" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-1"><label class="label">日</label></div>							
							<div class="wd-com wd-3"><label class="label">是否采用结算单:</label></div>
							<div class="wd-com wd-5">
								<input id="isSaleAccount" class="easyui-textbox" name="isSaleAccount" data-options="prompt:'请选择是否采用结算单',required:true" style="width:100%;height:35px">
							</div>																																						
						</div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>地址信息</div>
					<div class="fieldContent">
							<div class="wd-com wd-3"><label class="label">国家:</label></div>
							<div class="wd-com wd-5">
								<input id="country" class="easyui-textbox" name="country" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">省:</label></div>
							<div class="wd-com wd-5">
								<input id="province" class="easyui-textbox" name="province" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">市:</label></div>
							<div class="wd-com wd-5">
								<input id="city" class="easyui-textbox" name="city" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">县:</label></div>
							<div class="wd-com wd-5">
								<input id="county" class="easyui-textbox" name="county" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">公司地址:</label></div>
							<div class="wd-com wd-13">
								<input id="companyAddress" class="easyui-textbox" name="companyAddress" data-options="prompt:'公司地址',required:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">邮编:</label></div>
							<div class="wd-com wd-5">
								<input id="postcode" class="easyui-textbox" name="postcode" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">经度:</label></div>
							<div class="wd-com wd-5">
								<input id="longitude" class="easyui-numberspinner" name="longitude" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">纬度:</label></div>
							<div class="wd-com wd-5">
								<input id="latitude" class="easyui-numberspinner" name="latitude" style="width:100%;height:35px">
							</div>																																										
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>资质凭证</div>
					<div class="fieldContent">
						<div id="paperListTableToolbar">
							<button type="button" onclick="addDetailEditableList('paperListTable')" class="listTableToolbarBtn">新增</button>
							<button type="button" onclick="deleteDetailEditableList('paperListTable')" class="listTableToolbarBtn">删除</button>
						</div>
						<div id="paperListTable"></div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>开户信息</div>
					<div class="fieldContent">
						<div id="accountListTableToolbar">
							<button type="button" onclick="addDetailEditableList('accountListTable')" class="listTableToolbarBtn">新增</button>
							<button type="button" onclick="deleteDetailEditableList('accountListTable')" class="listTableToolbarBtn">删除</button>
						</div>
						<div id="accountListTable"></div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>联系方式</div>
					<div class="fieldContent">
						<div id="linkListTableToolbar">
							<button type="button" onclick="addDetailEditableList('linkListTable')" class="listTableToolbarBtn">新增</button>
							<button type="button" onclick="deleteDetailEditableList('linkListTable')" class="listTableToolbarBtn">删除</button>
						</div>
						<div id="linkListTable"></div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span id="changeContent" class="arrowUp" onclick="upOrDown(this)"></span>变更内容</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-24">
								<input class="easyui-textbox" name="changeContent" data-options="prompt:'请输入变更内容',multiline:true" style="width:100%;height:70px">
							</div>
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
				<%-- <jsp:include page="/jsp/ListTableToolbar.jsp" /> --%>
				<!-- <div id="listTable"></div> -->
			</form>
	</div>
	<div id="imgPreviewPanel" class="easyui-dialog" title="图片预览" style="width:700px;height:520px;"
			data-options="resizable:false,modal:true,closed:true">
		<div id="imgPreviewDIV" class="img-preveiw-div" style="width:700px;height:478px;">
			<ul id="imgPreviewUl">
<!-- 				<li style="display:none;"><img id="imgPreview" class="img-preveiw" alt="附件预览"/></li> -->
			</ul>
		</div>
	</div>
	<div class="rightSlipWin_780" id="changeHistory">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('changeHistory',780)"></span><span>变更历史</span>
		</div>
		 <table id="showChangeHistory"></table>		
	</div>		
	<jsp:include page="/jsp/WinFooter.jsp" />
	<div id="farmSelect" class="rightSlipWin_390 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('farmSelect',390)"></span><span>猪场选择</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<input id="selectedIndex" name="selectedIndex" type="hidden" />
			 <div id="farmTable"></div>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSelectFarm()" class="rightSlipBtn blue">确定</button>
			<button type="button" onclick="rightSlipFun('farmSelect',390)" class="rightSlipBtn green">取消</button>
		</div>
	</div>		
</body>
</html>