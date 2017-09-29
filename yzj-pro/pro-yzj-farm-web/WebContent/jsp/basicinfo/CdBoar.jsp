<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="${base_url}/js/basicinfo/CdBoar.js?v=${param.randomnumber}"></script>
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>公猪信息</div>
	<div class="fieldContent">
	<div class="widgetGroup">
		<div class="wd-com wd-3"><label class="label">品种:</label></div>
		<div class="wd-com wd-5">
			<input id="breedId" name="breedId" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">品系:</label></div>
		<div class="wd-com wd-5">
			<input id="strain" name="strain" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">体况:</label></div>
		<div class="wd-com wd-5">
			<input id="bodyCondition" name="bodyCondition" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">毛色:</label></div>
		<div class="wd-com wd-5">
			<input id="color" name="color" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">右乳头数:</label></div>
		<div class="wd-com wd-5">
			<input id="rightTeatNum" name="rightTeatNum" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">左乳头数:</label></div>
		<div class="wd-com wd-5">
			<input id="leftTeatNum" name="leftTeatNum" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">利用年限:</label></div>
		<div class="wd-com wd-5">
			<input id="useYear" name="useYear" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">出生体重(kg):</label></div>
		<div class="wd-com wd-5">
			<input id="birthWeight" name="birthWeight" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">入场日龄:</label></div>
		<div class="wd-com wd-5">
			<input id="enterDayAge" name="enterDayAge" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">入场体重(kg):</label></div>
		<div class="wd-com wd-5">
			<input id="enterWeight" name="enterWeight" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">公母比:</label></div>
		<div class="wd-com wd-1" style="text-align:right;">
			<span>1:</span>
		</div>
		<div class="wd-com wd-4">
			<input id="sexRatio" name="sexRatio" style="width:100%;height:32px">
		</div>
		<!-- <div class="wd-com wd-3"><label class="label">调教日龄:</label></div>
		<div class="wd-com wd-5">
			<input id="teachDayAge" name="teachDayAge" style="width:100%;height:32px">
		</div> -->
		<!-- <div class="wd-com wd-3"><label class="label">调教区间:</label></div>
		<div class="wd-com wd-5">
			<input id="errorLimit"  name="errorLimit" style="width:100%;height:32px">
		</div> -->
		<!-- <div class="wd-com wd-3"><label class="label">转生产日龄:</label></div>
		<div class="wd-com wd-5">
			<input id="productionDayAge" name="productionDayAge" style="width:100%;height:32px">
		</div> -->
		<!-- <div class="wd-com wd-3"><label class="label">转生产日龄区间:</label></div> -->
		
		<div class="wd-com wd-3"><label class="label">转生产日龄:</label></div>
		<div class="wd-com wd-2">
			<input id="productionDayAge" name="productionDayAge" style="width:100%;height:32px">
			<!-- <input id="dayAgeSection" name="dayAgeSection" style="width:100%;height:32px"> -->
		</div>
		<div class="wd-com wd-1"><label class="label">±:</label></div>
		<div class="wd-com wd-2">
			<input id="dayAgeSection" name="dayAgeSection" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">淘汰日龄:</label></div>
		<div class="wd-com wd-5">
			<input id="eliminateDayAge" name="eliminateDayAge" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">总采精次数:</label></div>
		<div class="wd-com wd-5">
			<input id="collectionNum" name="collectionNum" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">总精液量(ml):</label></div>
		<div class="wd-com wd-5">
			<input id="spermQty" name="spermQty"  style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-3"><label class="label">预期总仔数:</label></div>
		<div class="wd-com wd-5">
			<input id="litterNum" name="litterNum" style="width:100%;height:32px">
		</div>
	</div>
	</div>
</div>
