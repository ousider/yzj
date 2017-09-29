function dafengModelConfirm(){
	var dafengModelData = jAjax.submit('/supplyChian/searchDafengModelFlag.do')[0];
	$('#dafengModel').val(dafengModelData.dafengModel);
}
