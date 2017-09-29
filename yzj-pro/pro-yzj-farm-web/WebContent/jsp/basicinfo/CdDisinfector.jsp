<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="${base_url}/js/basicinfo/CdDisinfector.js?v=${param.randomnumber}"></script>
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>消毒剂信息</div>
	<div class="fieldContent">
	<div class="widgetGroup">
		<div class="wd-com wd-3"><label class="label">型号:</label></div>
		<div class="wd-com wd-5">
			<input id="disinfectorModel" name="disinfectorModel" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">保质期:</label></div>
		<div class="wd-com wd-5">
			<input id="shelflife" name="shelflife" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">包装:</label></div>
		<div class="wd-com wd-5">
			<input id="pack" name="pack" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">类别:</label></div>
		<div class="wd-com wd-5">
			<input id="disinfectorType" name="disinfectorType" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">细菌繁殖体:</label></div>
		<div class="wd-com wd-5">
			<input id="xjfzt" name="xjfzt" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">分支杆菌:</label></div>
		<div class="wd-com wd-5">
			<input id="fzgj" name="fzgj" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">真菌:</label></div>
		<div class="wd-com wd-5">
			<input id="zj" name="zj" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">包膜病毒:</label></div>
		<div class="wd-com wd-5">
			<input id="bmbd" name="bmbd" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">非包膜病毒:</label></div>
		<div class="wd-com wd-5">
			<input id="fbmbd" name="fbmbd" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">孢子:</label></div>
		<div class="wd-com wd-5">
			<input id="bz" name="bz" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">有机物存在时的效果:</label></div>
		<div class="wd-com wd-5">
			<input id="yjw" name="yjw" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">硬水存在时的效果:</label></div>
		<div class="wd-com wd-5">
			<input id="ys" name="ys" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">肥皂洗涤剂存在效果:</label></div>
		<div class="wd-com wd-5">
			<input id="xdj" name="xdj" style="width:100%;height:32px">
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
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>作用机理</div>
	<div class="fieldContent">
		<div class="widgetGroup">
			<div class="wd-com wd-24">
				<input id="effect" name="effect" style="width:100%;height:96px">
			</div>
		</div>
	</div>
</div>	
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>优点</div>
	<div class="fieldContent">
		<div class="widgetGroup">
			<div class="wd-com wd-24">
				<input id="advantage" name="advantage" style="width:100%;height:96px">
			</div>
		</div>
	</div>
</div>	
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>缺点</div>
	<div class="fieldContent">
		<div class="widgetGroup">
			<div class="wd-com wd-24">
				<input id="disadvantage" name="disadvantage" style="width:100%;height:96px">
			</div>
		</div>
	</div>
</div>	
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>使用方法</div>
	<div class="fieldContent">
		<div class="widgetGroup">
			<div class="wd-com wd-24">
				<input id="usageDosage" name="usageDosage" style="width:100%;height:96px">
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