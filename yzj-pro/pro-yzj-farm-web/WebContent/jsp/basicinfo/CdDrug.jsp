<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="${base_url}/js/basicinfo/CdDrug.js?v=${param.randomnumber}"></script>
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>药品信息</div>
	<div class="fieldContent">
	<div class="widgetGroup">
		<div class="wd-com wd-3"><label class="label">药品类型:</label></div>
		<div class="wd-com wd-5">
			<input id="drugType" name="drugType" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">保质期:</label></div>
		<div class="wd-com wd-5">
			<input id="shelflife" name="shelflife" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">包装:</label></div>
		<div class="wd-com wd-5">
			<input id="package" name="package" style="width:100%;height:32px">
		</div>
	</div>
	</div>
</div>
		
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>性状</div>
	<div class="fieldContent">
		<div class="widgetGroup">
			<div class="wd-com wd-24">
				<input id="apperance" name="apperance" style="width:100%;height:96px">
			</div>
		</div>
	</div>
</div>	
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>不良反应</div>
	<div class="fieldContent">
		<div class="widgetGroup">
			<div class="wd-com wd-24">
				<input id="adverseReactions" name="adverseReactions" style="width:100%;height:96px">
			</div>
		</div>
	</div>
</div>	
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>应用</div>
	<div class="fieldContent">
		<div class="widgetGroup">
			<div class="wd-com wd-24">
				<input id="application" name="application" style="width:100%;height:96px">
			</div>
		</div>
	</div>
</div>	

