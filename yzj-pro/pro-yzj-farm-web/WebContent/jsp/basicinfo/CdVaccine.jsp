<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="${base_url}/js/basicinfo/CdVaccine.js?v=${param.randomnumber}"></script>
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>疫苗信息</div>
	<div class="fieldContent">
	<div class="widgetGroup">
		<div class="wd-com wd-3"><label class="label">疫苗种类:</label></div>
		<div class="wd-com wd-5">
			<input id="vaccineType" name="vaccineType" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">主要成分与含量:</label></div>
		<div class="wd-com wd-5">
			<input id="mainContent" name="mainContent" style="width:100%;height:32px">
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
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>作用与用途</div>
	<div class="fieldContent">
		<div class="widgetGroup">
			<div class="wd-com wd-24">
				<input id="effect" name="effect" style="width:100%;height:96px">
			</div>
		</div>
	</div>
</div>	
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>用法与用量</div>
	<div class="fieldContent">
		<div class="widgetGroup">
			<div class="wd-com wd-24">
				<input id="usageDosage" name="usageDosage" style="width:100%;height:96px">
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
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>注意事项</div>
	<div class="fieldContent">
		<div class="widgetGroup">
			<div class="wd-com wd-24">
				<input id="notice" name="notice" style="width:100%;height:96px">
			</div>
		</div>
	</div>
</div>	

		