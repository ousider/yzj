<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/SysSetting.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div class="settingToolbar">
		<button type="button" id="btnSave" onclick="onBtnSaveSetting()" class="tableToolbarBtn btn-middle">保存</button>
		<button type="button" onclick="onBtnBackSetting()" class="tableToolbarBtn btn-middle">撤销</button>
		<button type="button" onclick="onBtnResetSetting()" class="tableToolbarBtn btn-middle">恢复默认</button>
		<button type="button" onclick="onBtnRefreshSYSCache()" class="tableToolbarBtn btn-middle" style="width:120px;">刷新系统缓存</button>
		<button type="button" onclick="onBtnRefreshPigCache()" class="tableToolbarBtn btn-middle" style="width:120px;">刷新猪只默认</button>
		<button type="button" onclick="onBtnRefreshMaterialInfoCache()" class="tableToolbarBtn btn-middle" style="width:120px;">刷新供应链缓存</button>
	</div>
	<div id="settingContent">
		<div id="settingGroup" class="settingClass fl">
		</div>
		<div class="classContent fl">
			<form id="settingFrom">
			</form>
		</div>
	</div>
</body>
</html>