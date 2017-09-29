<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="${base_url}/js/basicinfo/CdPpe.js?v=${param.randomnumber}"></script>
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>劳保用品信息</div>
	<div class="fieldContent">
	<div class="widgetGroup">
		<div class="wd-com wd-3"><label class="label">品牌:</label></div>
		<div class="wd-com wd-5">
			<input id="brand" name="brand" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">型号:</label></div>
		<div class="wd-com wd-5">
			<input id="model" name="model" style="width:100%;height:32px">
		</div>
	</div>
	</div>
</div>
<div class="collapseField">
	<div class="fieldTitle"><span id="description" class="arrowUp" onclick="upOrDown(this)"></span>说明书</div>
	<div class="fieldContent">
		<div class="widgetGroup">
			<div class="wd-com wd-24">
				<input id="direction" name="direction" style="width:100%;height:96px">
			</div>
		</div>
	</div>
</div>	