$(document).ready(function(){
	$('#brand').textbox({
		prompt:'请输入品牌'
	});
	$('#model').textbox({
		prompt:'请输入型号'
	});
	$('#voltage').numberspinner({
		prompt:'请输入工作电压'
	});
	$('#power').numberspinner({
		prompt:'请输入功率'
	});
	$('#anlb').numberspinner({
		prompt:'请输入折旧期限'
	});
	$('#direction').textbox({
		prompt:'请输入说明书',
		multiline:true
	});
	/*$('#notes').textbox({
		prompt:'请输入备注',
		multiline:true
	});*/
});