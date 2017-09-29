<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="${base_url}/js/basicinfo/CdRawMaterial.js?v=${param.randomnumber}"></script>
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>原料信息</div>
	<div class="fieldContent">
	<div class="widgetGroup">
		<div class="wd-com wd-3"><label class="label">原料类型:</label></div>
		<div class="wd-com wd-5">
			<input id="rawMaterialType" name="rawMaterialType" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">保质期（天）:</label></div>
		<div class="wd-com wd-5">
			<input id="shelfLife" name="shelfLife" style="width:100%;height:32px">
		</div>
	</div>
	</div>
</div>