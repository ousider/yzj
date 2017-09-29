<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/hana/SendToHanaSap.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/BaseFunction.js?v=${param.randomnumber}"></script>
</head>
<body  style="width:100%;height:100%;">
	<div id="toolbar01">
<!-- 		<button type="button" onclick="doAdd()" class="tableToolbarBtn btn-middle">新增</button> -->
		<button type="button" id="btnSaveNew" onclick="doSaveNew()" class="tableToolbarBtn btn-middle">再次上传</button>
		<button type="button" id="btnView" onclick="searchBtnView()" class="wd-2 tableToolbarBtn btn-middle">查看明细数据</button>
		<button type="button" id="btnViewSummary" onclick="searchBtnViewSummary()" class="wd-2 tableToolbarBtn btn-middle">查看汇总数据</button>
		
		<button type="button" id="btnToSapDetail" onclick="editToSapDetail()" class="wd-2 tableToolbarBtn btn-middle">生成明细数据</button>
		<button type="button" id="btnToSapSummeryDetail" onclick="editToSapSummaryDetail()" class="wd-2 tableToolbarBtn btn-middle">生成汇总数据</button>
<!-- 		<button type="button" onclick="onBtnSAPInsert()" class="tableToolbarBtn btn-middle" style="width:120px;">上传财务系统</button> -->
	</div>
	<div id="table"></div>
	<div id="showCommitPanel" class="easyui-dialog" title="本次上传SAP财务信息-明细" style="width:100%;height:100%;"
		data-options="resizable:false,modal:true,closed:true,inline:true,left:'0px',top:'0px'">
		<div>
			<div class="wd-com wd-3"><label class="label">存栏日报:<span id="listTable2Text">商品猪</span></label></div>
			<div style="text-align:right;display:inline;margin:0 0 0 70%">
				<button type="button" id="btnView" onclick="search9997SP()" class="tableToolbarBtn btn-middle" >商品猪</button>
				<button type="button" id="btnView" onclick="search9997HB()" class="tableToolbarBtn btn-middle">后备猪</button>
			</div>
		</div>
		<div id="listTable2"></div>
		<div>
			<div class="wd-com wd-3"><label class="label">存栏变动:<span id="listTable3Text">商品猪</span></label></div>
			<div style="text-align:right;display:inline;margin:0 0 0 70%">
				<button type="button" id="btnView" onclick="search9998SP()" class="tableToolbarBtn btn-middle" >商品猪</button>
				<button type="button" id="btnView" onclick="search9998HB()" class="tableToolbarBtn btn-middle">后备猪</button>
			</div>
		</div>
		<div id="listTable3"></div>
		<div id="listTable4"></div>
		<div id="listTable5"></div>
		<div class="dialog-button messager-button" style="width:290px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "editToSapData()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "dialogCancel('showCommitPanel')">取消</a>
		</div>
	</div>
	<div id="showSummeryPanel" class="easyui-dialog" title="本次上传SAP财务信息-汇总" style="width:100%;height:100%;"
		data-options="resizable:false,modal:true,closed:true,inline:true,left:'0px',top:'0px'">
		<div>
			<table class="sapTable" border="1" cellpadding="0" cellspacing="0" bordercolor="#dcdbdc" width="100%">
			    <thead>
			    <tr class="tilBold">
			        <td>序号</td>
			        <td>项目</td>
			        <td>期初数量</td>
			        <td>期初金额</td>
			        <td>本月增加数量</td>
			        <td>增加金额</td>
			        <td>本月减少数量</td>
			        <td>减少金额</td>
			        <td>期末数量</td>
			        <td>期末金额</td>
			        <td>其他</td>
			    </tr>
			    </thead>
			    <tbody>
			    <tr>
			        <td></td>
			        <td colspan="10" class="tilBold">生产数据</td>
			    </tr>
			    <tr id="sapTableTr1" class="collect"></tr>
			    <tr id="sapTableTr2" class="collect"></tr>
			    <tr id="sapTableTr3" class="collect"></tr>
			    <tr id="sapTableTr4" class="collect"></tr>
			    <tr id="sapTableTr5" class="collect"></tr>
			    <tr id="sapTableTr6" class="collect"></tr>
			    <tr id="sapTableTr7" class="collect"></tr>
			    <tr id="sapTableTr8" class="collect"></tr>
			    <tr id="sapTableTr9" class="collect"></tr>
			    <tr>
			        <td></td>
			        <td colspan="10" class="tilBold">生产数据汇总(内转只有本月增加)</td>
			    </tr>
			    <tr id="sapTableTr10" class="collect"></tr>
			    <tr id="sapTableTr11" class="collect"></tr>
			    <tr id="sapTableTr12" class="collect"></tr>
			    <tr id="sapTableTr13" class="collect"></tr>
			    <tr id="sapTableTr14" class="collect"></tr>
			    <tr id="sapTableTr15" class="collect"></tr>
			    <tr id="sapTableTr16" class="collect"></tr>
			    <tr id="sapTableTr17" class="collect"></tr>
			    <tr id="sapTableTr18" class="collect"></tr>
			    <tr id="sapTableTr19" class="collect"></tr>
			    <tr id="sapTableTr20" class="collect"></tr>
			    <tr id="sapTableTr21" class="collect"></tr>
			    <tr>
			        <td></td>
			        <td colspan="10" class="tilBold">销售</td>
			    </tr>
			    <tr id="sapTableTr22" class="collect"></tr>
			    <tr id="sapTableTr23" class="collect"></tr>
			    <tr id="sapTableTr24" class="collect"></tr>
			    <tr id="sapTableTr25" class="collect"></tr>
			    <tr>
			        <td></td>
			        <td colspan="10" class="tilBold">供应链数据</td>
			    </tr>
			    <tr id="sapTableTr26" class="collect"></tr>
			    <tr id="sapTableTr27" class="collect"></tr>
			    <tr id="sapTableTr28" class="collect"></tr>
			    <tr id="sapTableTr29" class="collect"></tr>
			    <tr>
			        <td></td>
			        <td colspan="10" class="tilBold">猪只采购</td>
			    </tr>
			    <tr id="sapTableTr30" class="collect"></tr>
			    <tr id="sapTableTr31" class="collect"></tr>
			    <tr id="sapTableTr32" class="collect"></tr>
			    </tbody>
			</table>
		</div>
		<div class="dialog-button messager-button" style="width:290px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "editToSapSummaryData()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "dialogCancel('showSummeryPanel')">取消</a>
		</div>
	</div>
<%-- 	<jsp:include page="/jsp/SendHanaMtcData.jsp" /> --%>
</body>
</html>