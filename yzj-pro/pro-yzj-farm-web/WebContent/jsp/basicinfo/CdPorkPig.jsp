<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="${base_url}/js/basicinfo/CdPorkPig.js?v=${param.randomnumber}"></script>
<div class="collapseField">
	<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>肉猪信息</div>
	<div class="fieldContent">
	<div class="widgetGroup">
		<div class="wd-com wd-3"><label class="label">品种:</label></div>
		<div class="wd-com wd-5">
			<input id="breedId" name="breedId" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">品系:</label></div>
		<div class="wd-com wd-5">
			<input id="strain" name="strain" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">体况:</label></div>
		<div class="wd-com wd-5">
			<input id="bodyCondition" name="bodyCondition" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">毛色:</label></div>
		<div class="wd-com wd-5">
			<input id="color" name="color" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">右乳头数:</label></div>
		<div class="wd-com wd-5">
			<input id="rightTeatNum" name="rightTeatNum" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">左乳头数:</label></div>
		<div class="wd-com wd-5">
			<input id="leftTeatNum" name="leftTeatNum" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">出生体重(kg):</label></div>
		<div class="wd-com wd-5">
			<input id="birthWeight" name="birthWeight" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">入场日龄:</label></div>
		<div class="wd-com wd-5">
			<input id="enterDayAge" name="enterDayAge" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">入场体重(kg):</label></div>
		<div class="wd-com wd-5">
			<input id="enterWeight" name="enterWeight" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">断奶日龄:</label></div>
		<div class="wd-com wd-5">
			<input id="mewDayAge" name="mewDayAge" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">断奶体重(kg):</label></div>
		<div class="wd-com wd-5">
			<input id="mewWeight" name="mewWeight" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">转保育日龄:</label></div>
		<div class="wd-com wd-5">
			<input id="childCareDayAge" name="childCareDayAge" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">转保育体重(kg):</label></div>
		<div class="wd-com wd-5">
			<input id="childCareWeight" name="childCareWeight" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">转育肥日龄:</label></div>
		<div class="wd-com wd-5">
			<input id="fattenDayAge" name="fattenDayAge" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">转育肥体重(kg):</label></div>
		<div class="wd-com wd-5">
			<input id="fattenWeight" name="fattenWeight" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">出售日龄:</label></div>
		<div class="wd-com wd-5">
			<input id="sellDayAge" name="sellDayAge" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">出售体重(kg):</label></div>
		<div class="wd-com wd-5">
			<input id="sellWeight" name="sellWeight" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">料肉比:</label></div>
		<div class="wd-com wd-5">
			<input id="frc" name="frc" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">父本:</label></div>
		<div class="wd-com wd-5">
			<input id="boarId" name="boarId" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">母本:</label></div>
		<div class="wd-com wd-5">
			<input id="sowId" name="sowId" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">种公猪主数据:</label></div>
		<div class="wd-com wd-5">
			<input id="stockBoarId" name="stockBoarId" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">种母猪主数据:</label></div>
		<div class="wd-com wd-5">
			<input id="broodSowId" name="broodSowId" style="width:100%;height:35px">
		</div>
		<div class="wd-com wd-3"><label class="label">是否选种:</label></div>
		<div class="wd-com wd-5">
			<div id="isSelect"></div>
		</div>
		<div class="wd-com wd-3"><label class="label">分娩区分性别:</label></div>
		<div class="wd-com wd-5">
			<div id="isDifSex"></div>
		</div>
		<div class="wd-com wd-3"><label class="label">批量留种:</label></div>
		<div class="wd-com wd-5">
			<div id="isSeed"></div>
		</div>
	</div>
	</div>
</div>