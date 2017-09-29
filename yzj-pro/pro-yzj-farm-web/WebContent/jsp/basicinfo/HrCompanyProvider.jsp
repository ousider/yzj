<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<link href="${base_url}/lib/jQuery/plugins/viewer.css?v=${webVersion}" rel="stylesheet">
	<script src="${base_url}/lib/jquery-easyui-1.4.5/jquery.edatagrid.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/lib/jQuery/plugins/viewer.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/basicinfo/HrCompanyProvider.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none">
		<input type="hidden" id=base_url value="${base_url}"/>
	</div>
	<div id="tableToolbar">
		<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看</button>
	</div>
	<div id="advancedSearch" onClick="leftSilpFun('hrCompanyProviderAdSearch')"><span>高级查询</span></div>
	<div id="hrCompanyProviderAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('hrCompanyProviderAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">供应商全称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="companyName" data-options="prompt:'请输入供应商全称'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">供应商简称:</label></div>
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
				<input type="hidden" name="cussupType" value="S" />
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
					<input type="hidden" name="cussupType" value="S">
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基本信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">创建类型:</label></div>
							<div class="wd-com wd-5">
								<input id="createType" name="createType" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">供应商全称:</label></div>
							<div id="companyNameDiv" class="wd-com wd-5">
								<input id="cussupId" name="cussupId" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">供应商简称:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" id="sortName" name="sortName" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">公司法人:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" id="legalPerson" name="legalPerson" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">供应商分类:</label></div>
							<div class="wd-com wd-5">
								<input id="supplierType" name="supplierType" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">业务伙伴类型:</label></div>
							<div class="wd-com wd-5"> 
								<input id="businessPartnerType" name="businessPartnerType" style="width:100%;height:35px">
							</div>
						</div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>付款结算</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">交易币种:</label></div>
							<div class="wd-com wd-5">
								<input id="tradeCurrency" name="tradeCurrency" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">付款条件:</label></div>
							<div class="wd-com wd-5">
								<input id="payCondition" name="payCondition" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">付款账期:</label></div>
							<div class="wd-com wd-5">
								<input id="payDays" name="payDays" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">信用额度:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" id="creditLimit" name="creditLimit" style="width:100%;height:35px">
							</div>
						</div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>地址信息</div>
					<div class="fieldContent">
							<div class="wd-com wd-3"><label class="label">国家:</label></div>
							<div class="wd-com wd-5">
								<input id="country" name="country" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">省:</label></div>
							<div class="wd-com wd-5">
								<input id="province" name="province" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">市:</label></div>
							<div class="wd-com wd-5">
								<input id="city" name="city" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">县:</label></div>
							<div class="wd-com wd-5">
								<input id="county" name="county" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">公司地址:</label></div>
							<div class="wd-com wd-13">
								<input class="easyui-textbox" id="companyAddress" name="companyAddress" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">邮编:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" id="postcode" name="postcode" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">经度:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-numberspinner" id="longitude" name="longitude" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">纬度:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-numberspinner" id="latitude" name="latitude" style="width:100%;height:35px">
							</div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>资质凭证</div>
					<div class="fieldContent">
						<div id="paperListTable"></div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>开户信息</div>
					<div class="fieldContent">
						<div id="accountListTable"></div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>联系方式</div>
					<div class="fieldContent">
						<div id="linkListTable"></div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span id="changeContent" class="arrowUp" onclick="upOrDown(this)"></span>变更内容</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-24">
								<input class="easyui-textbox" id="changeContentText" name="changeContent" data-options="multiline:true" style="width:100%;height:70px">
							</div>
						</div>
					</div>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span id="notes" class="arrowUp" onclick="upOrDown(this)"></span>备注</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-24">
								<input class="easyui-textbox"  id="notesText" name="notes" data-options="multiline:true" style="width:100%;height:70px">
							</div>
						</div>
					</div>
				</div>
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
	<div id="dlg-buttons" style="height:35px;text-align:right;padding:5px;">
		<button type="button" onclick="onBtnCancel()" class="winFooterBtn green">取消</button>
	</div>
</body>
</html>