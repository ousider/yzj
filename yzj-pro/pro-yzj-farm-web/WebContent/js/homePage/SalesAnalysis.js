$(document).ready(function(){
	getCurentDate();
	scPictureChange();
	$('.search-bar').bind('click',function(e){
		openWindowMask({z_index:3});
		$('.search-win').css('display','block');
	});
	searchLinkClick();
	selectPicClick();
	initSaleHomePage();
	customerAddressMapInit();
});
//获取当前日期信息
function getCurentDate(){
	var date = new Date();
	var dateString = date.getFullYear()+'年'+extra(date.getMonth()+1)+'月'+extra(date.getDate())+'日'+'<br>';
	var calendar = showCal();
	var weekArry = new Array('星期日','星期一','星期二','星期三','星期四','星期五','星期六');  
	$('.time-date').html(dateString+'农历'+calendar+'&nbsp;&nbsp;&nbsp;'+weekArry[date.getDay()]);
	$('.time-hour').html(extra(date.getHours())+':'+extra(date.getMinutes()));
	setTimeout("getCurentDate()",60000);
}
//数字补全
function extra(num){
	if(num < 10){
		return '0'+num;
	}else{
		return num;
	}
}
//客户地图
function customerAddressMapInit(){
	var myChart = echarts.init(document.getElementById('customerAddressMap'));
	$.get(basicUrl+"/portal/searchCompanyAddreed.do").done(function (data) {
		var AddressData = eval('('+data+')').rows;
		console.log(AddressData);
		var geoCoordMap = {},company = [],customer = [],farm = [];
		$.each(AddressData,function(i,item){
			var companyName = item.sortName == undefined || item.sortName == null ? item.companyName : item.sortName;
			geoCoordMap[companyName] = [item.longitude,item.latitude];
			if(item.companyClass == '1'){
				company.push({
					name:companyName,
					value:1
				});
			}
			if(item.companyClass == '2'){
				farm.push({
					name:companyName,
					value:1
				});
			}
			if(item.companyClass == '3'){
				customer.push({
					name:companyName,
					value:1
				});
			}
		});
		var convertData = function (data) {
		    var res = [];
		    for (var i = 0; i < data.length; i++) {
		        var geoCoord = geoCoordMap[data[i].name];
		        if (geoCoord) {
		            res.push({
		                name: data[i].name,
		                value: geoCoord.concat(data[i].value)
		            });
		        }
		    }
		    return res;
		};
		myChart.setOption({
			backgroundColor: '#aac7e0',
		    title: {
		       show:false
		    },
		    tooltip : {
		        trigger: 'item'
		    },
		    legend: {
		        orient: 'vertical',
		        y: 'bottom',
		        x:'right',
		        data:['集团','猪场','客户'],
		        textStyle: {
		            color: '#fff'
		        }
		    },
		    geo: {
		        map: 'china',
		        label: {
		            emphasis: {
		                show: false
		            }
		        },
		        roam: true,
		        itemStyle: {
		            normal: {
		                areaColor: '#517cac',
		                borderColor: '#aac7e0'
		            },
		            emphasis: {
		                areaColor: '#5b86b5'
		            }
		        }
		    },
		    series : [
		              {
		                  name: '集团',
		                  type: 'scatter',
		                  coordinateSystem: 'geo',
		                  data: convertData(company),
		                  symbolSize:8,
		                  label: {
		                      normal: {
		                          formatter: '{b}',
		                          position: 'right',
		                          show: false
		                      },
		                      emphasis: {
		                          show: true
		                      }
		                  },
		                  itemStyle: {
		                      normal: {
		                          color: '#d82d28'
		                      }
		                  }
		              },{
		                  name: '猪场',
		                  type: 'scatter',
		                  coordinateSystem: 'geo',
		                  data: convertData(farm),
		                  symbolSize:8,
		                  label: {
		                      normal: {
		                          formatter: '{b}',
		                          position: 'right',
		                          show: false
		                      },
		                      emphasis: {
		                          show: true
		                      }
		                  },
		                  itemStyle: {
		                      normal: {
		                          color: '#59940d'
		                      }
		                  }
		              },{
		                  name: '客户',
		                  type: 'scatter',
		                  coordinateSystem: 'geo',
		                  data: convertData(customer),
		                  symbolSize:8,
		                  label: {
		                      normal: {
		                          formatter: '{b}',
		                          position: 'right',
		                          show: false
		                      },
		                      emphasis: {
		                          show: true
		                      }
		                  },
		                  itemStyle: {
		                      normal: {
		                          color: '#ddb926'
		                      }
		                  }
		              }
		    ]
		});
	})
}
//销售图表切换
function scPictureChange(){
	$('.salse-pic').css('display','none');
	$('.search-bar').css('display','none');
	var icons = $('.sc-icon-com');
	$.each(icons,function(i,icon){
		$(icon).bind('click',function(e){
			$.each(icons,function(j,item){
				$(item).toggleClass("selected",false);
			});
			if($(this).hasClass('sales-portal')){
				$('.search-bar').css('display','none');
			}else{
				$('.search-bar').css('display','block');
			}
			$(this).addClass('selected');
			var className = this.className.split(" ")[1];
			$('#'+className).css('display','block');
			$('#'+className).siblings('.salse-pic,.sales-content').css('display','none');
			if(this.dataset.first == 'true'){
				//销售分析
				if(className == 'sales-analysis'){
					groupGoodPigSaleInit();
				}
				//盈利能力
				if(className == 'profitability'){
					imperfectPigSaleChangeInit();
				}
				//客户分析
				if(className == 'customer-manage'){
					salePriceTop10CustomerInit();
				}
			}
			if(this.dataset.first){
				this.dataset.first = 'false';
			}
		})
	})
}
//查询取消
function salesSearchCancel(){
	$('.window-mask').remove();
	$('.search-win').css('display','none');
}
//查询确定
function salesSearchSure(){
	salesSearchCancel();
	var alist = $('.search-win-content a');
	var searchDisplayHtml = '';
	$.each(alist,function(i,a){
		if($(a).hasClass('selected')){
			searchDisplayHtml+='<span data-value="'+a.dataset.value+'">'+$(a).html()+'</span>'
		}
	});
	var picList = $('.salse-pic');
	$.each(picList,function(i,p){
		if($(p).css('display') == 'block'){
			$(p).find('.display-search').html(searchDisplayHtml);
		}
	})
}
//查询<a>点击事件
function searchLinkClick(){
	var alist = $('.search-win-content a');
	$.each(alist,function(i,a){
		$(a).bind('click',function(e){
			$(this).toggleClass('selected');
			$(this).siblings('a').toggleClass('selected',false);
		})
	});
}
//选择图表li点击事件
function selectPicClick(){
	var liList = $('.pic-title-list>ul>li');
	$.each(liList,function(i,li){
		$(li).bind('click',function(e){
			if(!$(this).hasClass('selected')){
				$(this).toggleClass('selected');
				$(this).siblings('li').toggleClass('selected',false);
				var picId = this.dataset.picname;
				$('#'+picId).css('display','block');
				$('#'+picId).siblings('div').css('display','none');
				if(this.dataset.first == 'true'){
					if(picId == 'groupFarmSalePrice'){
						groupFarmSalePriceInit();
					}
					if(picId == 'goodPigSaleType'){
						goodPigSaleTypeInit();
					}
					if(picId == 'bigCustomerSaleCollection'){
						bigCustomerSaleCollectionInit();
					}
				}
				if(this.dataset.first){
					this.dataset.first = 'false';
				}
			}
		})
	});
}
function initSaleHomePage(){
	jAjax.submit("/portal/searchGroupSsalesOfRealTimeStatistics.do",null,function(response){
		$('#saleNumThisMonth').html(response.saleNumThisMonth);
		$('#saleNumThisDay').html(response.saleNumThisDay);
		$('#saleNumLastWeek').html(response.saleNumLastWeek);
		$('#saleNumLastMonth').html(response.saleNumLastMonth);
		if(response.saleProbability < 0){
			$('#saleProbability').prevAll('.trend-icon').addClass('red-down');
			$('#saleProbability').html((0-response.saleProbability).toFixed(2)+'%');
		}else{
			$('#saleProbability').prevAll('.trend-icon').addClass('green-up');
			$('#saleProbability').html((response.saleProbability).toFixed(2)+'%');
		}
		$('#marketingThisMonth').html(response.marketingThisMonth);
		$('#marketingThisDay').html(response.marketingThisDay);
		$('#marketingLasWeek').html(response.marketingLasWeek);
		$('#marketingLastMonth').html(response.marketingLastMonth);
		if(response.marketingProbability < 0){
			$('#marketingProbability').prevAll('.trend-icon').addClass('red-down');
			$('#marketingProbability').html((0-response.marketingProbability).toFixed(2)+'%');
		}else{
			$('#marketingProbability').prevAll('.trend-icon').addClass('green-up');
			$('#marketingProbability').html((response.marketingProbability).toFixed(2)+'%');
		}
		$('#companyThisMonth').html(response.companyThisMonth);
		$('#companyThisDay').html(response.companyThisDay);
		$('#companyLastWeek').html(response.companyLastWeek);
		$('#companyLastMonth').html(response.companyLastMonth);
		if(response.companyProbability < 0){
			$('#companyProbability').prevAll('.trend-icon').addClass('red-down');
			$('#companyProbability').html((0-response.companyProbability).toFixed(2)+'%');
		}else{
			$('#companyProbability').prevAll('.trend-icon').addClass('green-up');
			$('#companyProbability').html(strToFloat(response.companyProbability).toFixed(2)+'%');
		}
		$('#saleNumThisYear').html((strToFloat(response.saleNumThisYear)/1000).toFixed(2));
		$('#marketingTotalPriceThisYear').html((strToFloat(response.marketingTotalPriceThisYear)/10000).toFixed(2));
		$('#companyYear').html(response.companyYear);
	},null,null,true);
}
function groupGoodPigSaleInit(){
	var myChart = echarts.init(document.getElementById('groupGoodPigSale'));
	$.get(basicUrl+"/portal/searchFarmSalePig.do").done(function (data) {
		var saleData = eval('('+data+')').rows;
		var xAxis = [],SALE_NUM = [];
		$.each(saleData,function(i,item){
			xAxis.push(item.COMPANY_NAME);
			SALE_NUM.push({
				value:item.SALE_NUM,
				name:item.COMPANY_NAME
			});
		});
		myChart.setOption({
			title : {
		        text: '集团商品猪上市销售情况',
		        x:'center'
		    },
			tooltip : {
				trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		    	 orient: 'vertical',
		         left: 'left',
		         data: xAxis
		    },
		    series : [
	              {
            	  	name: '销售情况',
	  	            type: 'pie',
	  	            radius : '75%',
	  	            center: ['50%', '60%'],
	  	            data:SALE_NUM,
	  	            itemStyle: {
	  	              normal:{ 
	  	                  label:{ 
	  	                    show: true, 
	  	                    formatter: '{b} : {c} ({d}%)' 
	  	                  }, 
	  	                  labelLine :{show:true} 
	  	                } ,
	  	                emphasis: {
	  	                    shadowBlur: 10,
	  	                    shadowOffsetX: 0,
	  	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	  	                }
	  	            }
	              }
		    ]
		});
	})
}
function imperfectPigSaleChangeInit(){
	var myChart = echarts.init(document.getElementById('imperfectPigSaleChange'));
	$.get(basicUrl+"/portal/searchImperfectPigSaleChange.do").done(function (data) {
		var changeData = eval('('+data+')').rows;
		var xAxis = [],BY_QTY = [],YF_QTY = [];
		for(var i = changeData.length -1 ; i > 0 ; i --){
			var startDate = new Date(changeData[i].START_DATE);
			xAxis.push((startDate.getMonth()+1)+'月');
			BY_QTY.push(changeData[i].BY_QTY);
			YF_QTY.push(changeData[i].YF_QTY);
		}
		myChart.setOption({
			title : {
		        text: '残次猪销售变动情况',
		        x:'center'
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		    	 top: 'bottom',
		    	 data:['保育','育肥']
		    },
		    xAxis:  {
		        type: 'category',
		        boundaryGap: false,
		        data: xAxis
		    },
		    yAxis: {
		        type: 'value',
		        name:'销售数量',
		        axisLabel: {
	                 formatter: '{value} 头'
	             }
		    },
		    series : [
				{
				    name:'保育',
				    type:'line',
				    data:BY_QTY,
				    itemStyle : { normal: {label : {show: true}}}
				},
				{
		            name:'育肥',
		            type:'line',
		            data:YF_QTY,
		            itemStyle : { normal: {label : {show: true}}}
		        }
		    ]
		});
	})
}
function groupFarmSalePriceInit(){
	var myChart = echarts.init(document.getElementById('groupFarmSalePrice'));
	$.get(basicUrl+"/portal/searchFarmSalePig.do").done(function (data) {
		var salePriceData = eval('('+data+')').rows;
		var xAxis = [],SALE_PRICE_FZ = [],SALE_PRICE_MZ = [];
		$.each(salePriceData,function(i,item){
			xAxis.push(item.SORT_NAME != null && item.SORT_NAME != undefined ? item.SORT_NAME :item.COMPANY_NAME);
			SALE_PRICE_FZ.push((strToFloat(item.SALE_PRICE_FZ)/1000).toFixed(2));
			SALE_PRICE_MZ.push((strToFloat(item.SALE_PRICE_MZ)/1000).toFixed(2));
		});
		myChart.setOption({
			title : {
		        text: '集团场区销售额',
		        x:'center'
		    },
			tooltip : {
				trigger: 'axis'
		    },
		    legend: {
		    	 top: 'bottom',
		         data: ['苗猪销售额','肉猪销售额']
		    },
		    xAxis:  {
		        type: 'category',
		        data: xAxis
		    },
		    yAxis: {
		        type: 'value',
		        name:'万元'
		    },
		    series : [
				{
				    name:'苗猪销售额',
				    type:'bar',
				    data:SALE_PRICE_MZ,
				    itemStyle : { normal: {label : {show: true,position:'top'}}}
				},
				{
				    name:'肉猪销售额',
				    type:'bar',
				    data:SALE_PRICE_FZ,
				    itemStyle : { normal: {label : {show: true,position: [0, -25]}}}
				}
		    ]
		});
	})
}
function goodPigSaleTypeInit(){
	var myChart = echarts.init(document.getElementById('goodPigSaleType'));
	$.get(basicUrl+"/portal/searchFarmSalePig.do").done(function (data) {
		var saleTypeData = eval('('+data+')').rows;
		var xAxis = [],SALE_NUM_FZ = [],SALE_NUM_MZ = [],AVG_SALE_WEIGHT_FZ = [],AVG_SALE_WEIGHT_MZ = [];
		$.each(saleTypeData,function(i,item){
			xAxis.push(item.SORT_NAME != null && item.SORT_NAME != undefined ? item.SORT_NAME :item.COMPANY_NAME);
			SALE_NUM_FZ.push(item.SALE_NUM_FZ);
			SALE_NUM_MZ.push(item.SALE_NUM_MZ);
			AVG_SALE_WEIGHT_FZ.push(item.AVG_SALE_WEIGHT_FZ == undefined ? 0 :(strToFloat(item.AVG_SALE_WEIGHT_FZ)).toFixed(2));
			AVG_SALE_WEIGHT_MZ.push(item.AVG_SALE_WEIGHT_MZ == undefined ? 0 :(strToFloat(item.AVG_SALE_WEIGHT_MZ)).toFixed(2));
		});
		myChart.setOption({
			title : {
		        text: '商品猪销售品类',
		        x:'center'
		    },
			tooltip : {
				trigger: 'axis'
		    },
		    legend: {
		    	 top: 'bottom',
		         data: ['上市苗猪数','上市肉猪数','苗猪均重','肉猪均重']
		    },
		    xAxis:  {
		        type: 'category',
		        data: xAxis
		    },
		    yAxis: [
		    	 {
		             type: 'value',
		             name: '销售数量',
		             axisLabel: {
		                 formatter: '{value} 头'
		             }
		         },
		         {
		             type: 'value',
		             name: '销售均重',
		             splitLine:{
		            	show:false 
		             },
		             axisLabel: {
		                 formatter: '{value} KG'
		             }
		         }
		   ],
		    series : [
				{
				    name:'上市苗猪数',
				    type:'bar',
				    data:SALE_NUM_MZ,
				    itemStyle : { normal: {label : {show: true,position:'top'}}}
				},
				{
				    name:'上市肉猪数',
				    type:'bar',
				    data:SALE_NUM_FZ,
				    itemStyle : { normal: {label : {show: true,position: [0, -25]}}}
				},
				{
				    name:'苗猪均重',
				    type:'line',
				    yAxisIndex: 1,
				    data:AVG_SALE_WEIGHT_MZ,
				    itemStyle : { normal: {label : {show: true,position: [0, -25]}}}
				},
				{
				    name:'肉猪均重',
				    type:'line',
				    yAxisIndex: 1,
				    data:AVG_SALE_WEIGHT_FZ,
				    itemStyle : { normal: {label : {show: true,position: [0, -25]}}}
				}
		    ]
		});
	})
}
function salePriceTop10CustomerInit(){
	var myChart = echarts.init(document.getElementById('salePriceTop10Customer'));
	myChart.showLoading('default');
	$.get(basicUrl+"/portal/searchCustomerAnalysis.do").done(function (data) {
		var customerData = eval('('+data+')').rows.list;
		var xAxis = [],SALE_NUM = [];
		$.each(customerData,function(i,item){
			if(i < 10){
				xAxis.push(item.COMPANY_NAME);
				SALE_NUM.push({
					value:item.TOTAL_PRICE,
					name:item.COMPANY_NAME
				});
			}else{
				return false;
			}
		});
		myChart.hideLoading();
		myChart.setOption({
			title : {
		        text: '十大客户排名-销售金额',
		        x:'center'
		    },
			tooltip : {
				trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		    	 orient: 'vertical',
		         left: 'left',
		         data: xAxis
		    },
		    series : [
	              {
            	  	name: '销售情况',
	  	            type: 'pie',
	  	            radius : '75%',
	  	            center: ['50%', '60%'],
	  	            data:SALE_NUM,
	  	            itemStyle: {
	  	              normal:{ 
	  	                  label:{ 
	  	                    show: true, 
	  	                    formatter: '{b} : {c} ({d}%)' 
	  	                  }, 
	  	                  labelLine :{show:true} 
	  	                } ,
	  	                emphasis: {
	  	                    shadowBlur: 10,
	  	                    shadowOffsetX: 0,
	  	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	  	                }
	  	            }
	              }
		    ]
		});
	})
}
function bigCustomerSaleCollectionInit(){
	var myChart = echarts.init(document.getElementById('bigCustomerSaleCollection'));
	myChart.showLoading('default');
	$.get(basicUrl+"/portal/searchCustomerAnalysis.do").done(function (data) {
		var customerData = eval('('+data+')').rows.list;
		var xAxis = [],TOTAL_PRICE = [],SALE_INCOME = [],RECEIVABLES_INTERVAL = [];
		$.each(customerData,function(i,item){
			if(i < 10){
				xAxis.push(item.COMPANY_NAME);
				TOTAL_PRICE.push(strToFloat((item.TOTAL_PRICE)/1000).toFixed(2));
				SALE_INCOME.push(strToFloat((item.SALE_INCOME)/1000).toFixed(2));
				RECEIVABLES_INTERVAL.push(strToFloat((item.RECEIVABLES_INTERVAL)/1000).toFixed(2));
			}else{
				return false;
			}
		});
		myChart.hideLoading();
		myChart.setOption({
			title : {
		        text: '大客户销售与收款',
		        x:'center'
		    },
			tooltip : {
				trigger: 'axis'
		    },
		    legend: {
		    	 left: 'right',
		    	 orient: 'vertical',
		         data: ['销售金额','销售收入','应收货款']
		    },
		    xAxis:  {
		        type: 'category',
		        data: xAxis,
		        axisLabel:{
		        	interval:0,
		        	rotate:20
		        }
		    },
		    yAxis: {
		        type: 'value',
		        name:'万元'
		    },
		    series : [
				{
				    name:'销售金额',
				    type:'bar',
				    data:TOTAL_PRICE
				},
				{
				    name:'销售收入',
				    type:'bar',
				    data:TOTAL_PRICE
				},
				{
				    name:'应收货款',
				    type:'bar',
				    data:RECEIVABLES_INTERVAL
				}
		    ]
		});
	})
}
function strToFloat(str,isEmpty){
	isEmpty = isEmpty == null ? true : isEmpty;
	if(isEmpty){
		return parseFloat(str == '' ? 0 : str);
	}else{
		return parseFloat(str);
	}
}