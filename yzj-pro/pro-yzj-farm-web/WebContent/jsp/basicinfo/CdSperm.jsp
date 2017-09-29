<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="${base_url}/js/basicinfo/CdSperm.js?v=${param.randomnumber}"></script>
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>精液信息</div>
	<div class="fieldContent">
	<div class="widgetGroup">
		<div class="wd-com wd-3"><label class="label">对应公猪主数据:</label></div>
		<div class="wd-com wd-5">
			<input id="boarId" name="boarId" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">精子活力（%）:</label></div>
		<div class="wd-com wd-5">
			<input id="spermMotility" name="spermMotility" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">精子密度:</label></div>
		<div class="wd-com wd-5">
			<input id="spermDensity" name="spermDensity" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">畸形率（%）:</label></div>
		<div class="wd-com wd-5">
			<input id="abnormationRate" name="abnormationRate" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">凝聚度:</label></div>
		<div class="wd-com wd-5">
			<input id="cohesion" name="cohesion" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">颜色:</label></div>
		<div class="wd-com wd-5">
			<input id="color" name="color" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">保存温度（℃）:</label></div>
		<div class="wd-com wd-5">
			<input id="tstg" name="tstg" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">包装:</label></div>
		<div class="wd-com wd-5">
			<input id="pack" name="pack" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">保质期（天）:</label></div>
		<div class="wd-com wd-5">
			<input id="shelfLife" name="shelfLife" style="width:100%;height:32px">
		</div>
	</div>
	</div>
</div>