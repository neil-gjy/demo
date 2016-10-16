$(function () {
	
	// 指数数据 2016-07-26
	$('#cntHD').html(cntHD);
	$('#rectifyNumber ').html(rectifyNumber/cntHD*100 + '%' );
	
	
	// 数据列表
	var grid_data = 
	[ 
		{id:"1",type:"操作隐患",description:"description",isRectify:"是", sdate:"2016-06-03"},
		{id:"2",type:"消防隐患",description:"Long text ",isRectify:"是",sdate:"2016-06-03"},
		{id:"3",type:"用电隐患",description:"description3",isRectify:"是",sdate:"2016-06-03"},
		{id:"4",type:"职业卫生隐患",description:"description",isRectify:"否",sdate:"2016-06-03"},
		{id:"5",type:"特种设备隐患",description:"description2",isRectify:"是",sdate:"2016-06-03"},
		{id:"6",type:"特种设备隐患",description:"description3",isRectify:"否", sdate:"2016-06-03"},
		{id:"7",type:"用电隐患",description:"description",isRectify:"是",sdate:"2016-06-03"},
		{id:"8",type:"用电隐患",description:"description2",isRectify:"是",sdate:"2016-06-03"},
		{id:"9",type:"特种设备隐患 Printer",description:"description3",isRectify:"否", sdate:"2016-06-03"},
		{id:"10",type:"Desktop Computer",description:"description",isRectify:"是", sdate:"2016-06-03"},
		{id:"11",type:"Laptop",description:"Long text ",isRectify:"是",sdate:"2016-06-03"},
		{id:"06",type:"LCD Monitor",description:"description3",isRectify:"是",sdate:"2016-06-03"},
		{id:"13",type:"Speakers",description:"description",isRectify:"否",sdate:"2016-06-03"},
		{id:"14",type:"Laser Printer",description:"description2",isRectify:"是",sdate:"2016-06-03"},
	];
	
	$("#list").jqGrid({
		/*url : baseUrl + "/emergency/emergencyRole/loadList",
		datatype : "json",*/
		data: grid_data,
		datatype: "local",
		mtype : "POST",
		styleUI : 'Bootstrap',
		colNames : [ "编号", "隐患类型", "隐患内容", "发现时间", "已整改" ],
		colModel : [ {
			name : "id",
			index : "id",
			width : 150,
			align : "left",
			key : true,
			hidden : true
		}, {
			name : "type",
			index : "type",
			width : 150,
			align : "left"
		}, {
			name : "description",
			width : 100,
			align : "left"
		}, {
			name : "sdate",
			index : "sdate",
			width : 100,
			align : "left"
			//hidden : true
		}, {
			name : "isRectify",
			width : 100,
			align : "left"
		} ],
		pager : "#pager",
		rowNum : 15,
		sortname : "name",
		sortorder : "asc",
		viewrecords : true,
		gridview : true,
		autoencode : true,
		multiselect : false,
		autowidth : true,
		height : "220px"
	});
	
	
	// 填充报警信息列表
	var warn_data = 
	[ 
		{id:"1",type:"重大隐患",description:"description", sdate:"2016-06-03"},
		{id:"2",type:"隐患排查频率过低",description:"Long text ",sdate:"2016-06-04"},
		{id:"3",type:"隐患排查覆盖率过低",description:"description3",sdate:"2016-06-07"},
		{id:"4",type:"隐患整改超期数超标",description:"description",sdate:"2016-06-08"},
		{id:"5",type:"重大隐患",description:"description2",sdate:"2016-06-15"},
		{id:"6",type:"隐患整改超期数超标",description:"description3", sdate:"2016-06-16"},
		{id:"7",type:"隐患整改超期数超标",description:"description",sdate:"2016-06-20"},
		{id:"8",type:"重大隐患",description:"description2",sdate:"2016-06-21"},
		{id:"9",type:"隐患排查覆盖率过低",description:"description3", sdate:"2016-06-22"}
	];
	
	$("#warnList").jqGrid({
		/*url : baseUrl + "/emergency/emergencyRole/loadList",
		datatype : "json",*/
		data: warn_data,
		datatype: "local",
		mtype : "POST",
		styleUI : 'Bootstrap',
		colNames : [ "标记为已处理", "编号", "报警类型", "报警内容", "报警时间" ],
		colModel : [ 
         {
        	 name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false,
        	 editable: true,edittype:"checkbox",editoptions: {value:"Yes:No"}
		},{
			name : "id",
			index : "id",
			width : 150,
			align : "left",
			key : true,
			hidden : true
		}, {
			name : "type",
			index : "type",
			width : 150,
			align : "left"
		}, {
			name : "description",
			width : 100,
			align : "left"
		}, {
			name : "sdate",
			index : "sdate",
			width : 100,
			align : "left"
			//hidden : true
		} ],
		pager : "#warnPager",
		rowNum : 15,
		sortname : "name",
		sortorder : "asc",
		viewrecords : true,
		gridview : true,
		autoencode : true,
		multiselect : false,
		//autowidth : true,
		onSelectRow: editRow,
		height : "100%",
		width : 500
	});
	
	var lastSelection;
	function editRow(id) {
         if (id && id !== lastSelection) {
             var grid = $("#warnList");
             grid.jqGrid('restoreRow',lastSelection);
             grid.jqGrid('editRow',id, {keys:true, focusField: 1});
             lastSelection = id;
         }
     }
	
	$('#btnWarn').click(function(){
		$('#warnModal').modal();
	});
	
	
	
	
	
    // 填充图表
	
    var lineUrl = baseUrl + "/hiddenDanger/pileChart";
    //lineChart.showLoading();
	$.ajax({
		type : "post",
		url : lineUrl,
		dataType : "json",
		success : function(data) {
			fillPileChart(data);
		}
	});
	
	function fillPileChart(data){
		var lineChart = echarts.init(document.getElementById('lineEchart'));
		lineChart.setOption({
				title: {
	    	        text: data.title
	    	    },
	    	    toolbox: {
	    	        feature: {
	    	            dataView: {show: true, readOnly: false},
	    	            magicType: {show: true, type: ['line', 'bar']},
	    	            restore: {show: true},
	    	            saveAsImage: {show: true}
	    	        }
	    	    },
	    	    tooltip : {
	    	        trigger: 'axis'
	    	    },
	    	    legend: {
	    	        data:['石化板块','集装箱板块','杂货板块','散货板块']
	    	    },
	    	    grid: {
	    	        left: '3%',
	    	        right: '4%',
	    	        bottom: '3%',
	    	        containLabel: true
	    	    },
	    	    xAxis : data.xAxis,
	    	    yAxis : data.yAxis,
	    	    //legend: data.legend,
	    	    series : data.series
	    	              
		});
		
		lineChart.on('click', function (params) {
		    console.log(params);
		});
	}
	
	
    
    // PieChart
    var pieChart = echarts.init(document.getElementById('pieEchart'));

    // 指定图表的配置项和数据
    pieOption = {
    	    tooltip: {
    	        trigger: 'item',
    	        formatter: "{a} <br/>{b}: {c} ({d}%)"
    	    },
    	    legend: {
    	        orient: 'vertical',
    	        x: 'left',
    	        data:['基础管理','现场管理','人员','管理','用电','油气','危化品','设备','其他']
    	    },
    	    series: [
    	        {
    	            name:'一级要素',
    	            type:'pie',
    	            selectedMode: 'single',
    	            radius: [0, '30%'],

    	            label: {
    	                normal: {
    	                    position: 'inner'
    	                }
    	            },
    	            labelLine: {
    	                normal: {
    	                    show: false
    	                }
    	            },
    	            data:[
    	                {value:335, name:'基础管理'},
    	                {value:679, name:'现场管理'},
    	                //{value:1548, name:'搜索引擎'}
    	            ]
    	        },
    	        {
    	            name:'二级要素',
    	            type:'pie',
    	            radius: ['40%', '55%'],

    	            data:[
    	                {value:335, name:'人员'},
    	                {value:310, name:'管理'},
    	                {value:234, name:'用电'},
    	                {value:135, name:'油气'},
    	                {value:1048, name:'危化品'},
    	                {value:251, name:'设备'},
    	                {value:147, name:'车辆'},
    	                {value:102, name:'其他'}
    	            ]
    	        },
    	        {
    	            name:'三级要素',
    	            type:'pie',
    	            radius: ['60%', '70%'],

    	            data:[
    	                {value:335, name:'人员'},
    	                {value:310, name:'管理'},
    	                {value:234, name:'用电'},
    	                {value:135, name:'油气'},
    	                {value:1048, name:'危化品'},
    	                {value:251, name:'设备'},
    	                {value:147, name:'车辆'},
    	                {value:102, name:'其他'}
    	            ]
    	        }
    	    ]
    	};


    // 使用刚指定的配置项和数据显示图表。
    pieChart.setOption(pieOption);
    
    
    var lineUrl = baseUrl + "/hiddenDanger/pieChart";
    
	$.ajax({
		type : "post",
		url : lineUrl,
		dataType : "json",
		success : function(data) {
			fillPieChart(data);
		}
	});
    
    function fillPieChart(data){
		var pieChart = echarts.init(document.getElementById('pieEchart'));
		pieOption = ({
			 	tooltip: {
	    	        trigger: 'item',
	    	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    	    },
	    	    legend: {
	    	        orient: 'vertical',
	    	        x: 'left',
	    	        data:['基础管理','现场管理','教育培训','应急管理','特种设备基础管理','应急职业卫生基础管理','其他基础管理',
	    	              '从业人员操作行为','消防安全','用电安全','辅助动力系统','其他现场管理','教育培训','应急管理','特种设备基础管理','应急职业卫生基础管理','其他基础管理',
	    	              '从业人员操作行为','消防安全','用电安全','辅助动力系统','其他现场管理']
	    	    },
	    	    series: data.series
	    	});
		pieChart.setOption(pieOption);
		
	}
    
    // 集团趋势柱状图（数据测试） 2016-07-26
    var lineUrl = baseUrl + "/hiddenDanger/groupTrendChart";
    
    $.ajax({
		type : "post",
		url : lineUrl,
		dataType : "json",
		success : function(data) {
			fillGroupTrendChart(data);
		}
	});
    
    function fillGroupTrendChart(data){
    	console.log(data);
    	var barTrendChart = echarts.init(document.getElementById('barGroupTrend'));
        
        barOption = {
        	    tooltip: {
        	        trigger: 'axis'
        	    },
        	    toolbox: {
        	        feature: {
        	            dataView: {show: true, readOnly: false},
        	            magicType: {show: true, type: ['line', 'bar']},
        	            restore: {show: true},
        	            saveAsImage: {show: true}
        	        }
        	    },
        	    legend: {
        	        data:['隐患数','整改数','整改率']
        	    },
        	    xAxis: [
        	        {
        	            type: 'category',
        	            data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
        	        }
        	    ],
        	    yAxis: [
        	        {
        	            type: 'value',
        	            name: '隐患数/整改数',
        	            min: 0,
        	            max: 1000,
        	            interval: 50,
        	            axisLabel: {
        	                formatter: '{value} '
        	            }
        	        },
        	        {
        	            type: 'value',
        	            name: '整改率',
        	            min: 0,
        	            max: 100,
        	            interval: 5,
        	            axisLabel: {
        	                formatter: '{value} %'
        	            }
        	        }
        	    ],
        	    series: [
        	        {
        	            name:'隐患数',
        	            type:'bar',
        	            data:data.series[0].data
        	        },
        	        {
        	            name:'整改数',
        	            type:'bar',
        	            data:data.series[1].data
        	        },
        	        {
        	            name:'整改率',
        	            type:'line',
        	            yAxisIndex: 1,
        	            data:data.series[2].data
        	        }
        	    ]
        	};

        // 使用刚指定的配置项和数据显示图表。
        barTrendChart.setOption(barOption);
    }
    
    
    // 柱状图(集团)
    var barChart = echarts.init(document.getElementById('barGroup'));
    
    barOption = {
    	    tooltip: {
    	        trigger: 'axis'
    	    },
    	    toolbox: {
    	        feature: {
    	            dataView: {show: true, readOnly: false},
    	            magicType: {show: true, type: ['line', 'bar']},
    	            restore: {show: true},
    	            saveAsImage: {show: true}
    	        }
    	    },
    	    legend: {
    	        data:['隐患数','整改数','整改率']
    	    },
    	    xAxis: [
    	        {
    	            type: 'category',
    	            data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
    	        }
    	    ],
    	    yAxis: [
    	        {
    	            type: 'value',
    	            name: '隐患数/整改数',
    	            min: 0,
    	            max: 1000,
    	            interval: 50,
    	            axisLabel: {
    	                formatter: '{value} '
    	            }
    	        },
    	        {
    	            type: 'value',
    	            name: '整改率',
    	            min: 0,
    	            max: 100,
    	            interval: 5,
    	            axisLabel: {
    	                formatter: '{value} %'
    	            }
    	        }
    	    ],
    	    series: [
    	        {
    	            name:'隐患数',
    	            type:'bar',
    	            data:[300, 280, 160, 400, 100, 233, 250, 100, 70, 120, 100, 90]
    	        },
    	        {
    	            name:'整改数',
    	            type:'bar',
    	            data:[270, 250, 120, 350, 89, 190, 200, 90, 60, 100, 88, 88]
    	        },
    	        {
    	            name:'整改率',
    	            type:'line',
    	            yAxisIndex: 1,
    	            data:[90, 89.2, 75, 87.5, 89, 81.5, 80, 90, 85.7, 83.3, 88, 98]
    	        }
    	    ]
    	};

    // 使用刚指定的配置项和数据显示图表。
    barChart.setOption(barOption);
    
    
    // 隐患整改情况（公司）
    var barCompany = echarts.init(document.getElementById('barCompany'));
    
    companyOption = {
        tooltip : {
            trigger: 'item'
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        legend: {
            data:['Growth', '隐患数', '整改数'],
            itemGap: 5
        },
        grid: {
            top: '12%',
            left: '1%',
            right: '10%',
            containLabel: true
        },
        xAxis: [
            {
                type : 'category',
                data : ['一公司','二公司','四公司','五公司','石化码头','太平洋','五洲国际','煤码头','焦炭码头','中化石化',
                        'LNG','远航矿石']
            }
        ],
        yAxis: [
            {
                type : 'value',
                name : '隐患数/整改数',
                axisLabel: {
                    formatter: function (a) {
                        /*a = +a;
                        return isFinite(a)
                            ? echarts.format.addCommas(+a / 1000)
                            : '';*/
                    }
                }
            }
        ],
        dataZoom: [
            {
                show: true,
                start: 94,
                end: 100,
                handleSize: 8
            },
            {
                type: 'inside',
                start: 94,
                end: 100
            },
            {
                show: true,
                yAxisIndex: 0,
                filterMode: 'empty',
                width: 12,
                height: '70%',
                handleSize: 8,
                showDataShadow: false,
                left: '93%'
            }
        ],
        series : [
            {
                name: '隐患数',
                type: 'bar',
                data: [300, 280, 160, 400, 100, 233, 250, 100, 70, 120, 100, 90]
            },
            {
                name: '整改数',
                type: 'bar',
                data: [270, 250, 120, 350, 89, 190, 200, 90, 60, 100, 88, 88]
            }
        ]
    };

    barCompany.setOption(companyOption);
    
    barCompany.on('dblclick', function (params) {
    	var href = baseUrl + "/companyHazardsChecking/dashboard/" + params.name;
    	$.gohref(href);
    	//window.open(href);
        //console.log(params.name);
    });
    
    
    
    
    // 饼状图（分版块）
    var plateChart = echarts.init(document.getElementById('plateEchart'));
    
    plateOption = {
	    title : {
	        text: '隐患分模块比例图',
	        //subtext: '纯属虚构',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    toolbox : {
	    	show : true,
	    	feature : {  
	            restore : {
	            	show: true,
	            	onclick:function(option1) {//点击事件,这里的option1是chart的option信息    
                        alert('1');//这里可以加入自己的处理代码，切换不同的图形    
                        }    
	            }  
	    	}
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: ['石化板块','集装箱板块','杂货板块','散货板块']
	    },
	    series : [
	        {
	            name: '板块',
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
	                {value:335, name:'石化板块'},
	                {value:310, name:'集装箱板块'},
	                {value:234, name:'杂货板块'},
	                {value:135, name:'散货板块'}
	            ],
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};

    plateChart.setOption(plateOption);
    
    plateChart.on('click', function (params) {
    	
    	var correctiveChart = echarts.init(document.getElementById('correctiveEchart'));
        
    	if(params.name=='石化板块'){
	        correctiveOption = {
	        		title : {
	        	        text: '石化板块',
	        	        //subtext: '纯属虚构'
	        	    },
	        	    tooltip : {
	        	        trigger: 'axis'
	        	    },
	        	    legend: {
	        	        data:['隐患数','整改数']
	        	    },
	        	    toolbox: {
	        	        show : true,
	        	        feature : {
	        	            dataView : {show: true, readOnly: false},
	        	            magicType : {show: true, type: ['line', 'bar']},
	        	            restore : {show: true},
	        	            saveAsImage : {show: true}
	        	        }
	        	    },
	        	    calculable : true,
	        	    xAxis : [
	        	        {
	        	            type : 'category',
	        	            data : ['石化码头','中化石化','圣翰石化','实华']
	        	        }
	        	    ],
	        	    yAxis : [
	        	        {
	        	            type : 'value'
	        	        }
	        	    ],
	        	    series : [
	        	        {
	        	            name:'隐患数',
	        	            type:'bar',
	        	            data:[55, 20, 39, 15],
	        	            markPoint : {
	        	                data : [
	        	                    {type : 'max', name: '最大值'},
	        	                    {type : 'min', name: '最小值'}
	        	                ]
	        	            },
	        	            markLine : {
	        	                data : [
	        	                    {type : 'average', name: '平均值'}
	        	                ]
	        	            }
	        	        },
	        	        {
	        	            name:'整改数',
	        	            type:'bar',
	        	            data:[50, 15, 30, 12],
	        	            markPoint : {
	        	            	data : [
	            	                    {type : 'max', name: '最大值'},
	            	                    {type : 'min', name: '最小值'}
	            	                ]
	        	            },
	        	            markLine : {
	        	                data : [
	        	                    {type : 'average', name : '平均值'}
	        	                ]
	        	            }
	        	        }
	        	    ]
	        	};
    	}
    	else{
    		correctiveOption = {
	        		title : {
	        	        text: '集装箱板块',
	        	        //subtext: '纯属虚构'
	        	    },
	        	    tooltip : {
	        	        trigger: 'axis'
	        	    },
	        	    legend: {
	        	        data:['隐患数','整改数']
	        	    },
	        	    toolbox: {
	        	        show : true,
	        	        feature : {
	        	            dataView : {show: true, readOnly: false},
	        	            magicType : {show: true, type: ['line', 'bar']},
	        	            restore : {show: true},
	        	            saveAsImage : {show: true}
	        	        }
	        	    },
	        	    calculable : true,
	        	    xAxis : [
	        	        {
	        	            type : 'category',
	        	            data : ['集装箱','太平洋','欧亚国际','五洲国际','联盟国际']
	        	        }
	        	    ],
	        	    yAxis : [
	        	        {
	        	            type : 'value'
	        	        }
	        	    ],
	        	    series : [
	        	        {
	        	            name:'隐患数',
	        	            type:'bar',
	        	            data:[9, 20, 14, 15,7],
	        	            markPoint : {
	        	                data : [
	        	                    {type : 'max', name: '最大值'},
	        	                    {type : 'min', name: '最小值'}
	        	                ]
	        	            },
	        	            markLine : {
	        	                data : [
	        	                    {type : 'average', name: '平均值'}
	        	                ]
	        	            }
	        	        },
	        	        {
	        	            name:'整改数',
	        	            type:'bar',
	        	            data:[8, 12, 10, 12, 3],
	        	            markPoint : {
	        	            	data : [
	            	                    {type : 'max', name: '最大值'},
	            	                    {type : 'min', name: '最小值'}
	            	                ]
	        	            },
	        	            markLine : {
	        	                data : [
	        	                    {type : 'average', name : '平均值'}
	        	                ]
	        	            }
	        	        }
	        	    ]
	        	};
    	}

        // 使用刚指定的配置项和数据显示图表。
        correctiveChart.setOption(correctiveOption);
        
        correctiveChart.on('click', function (params) {
        	
        	$('#timelineModal').modal();
        	
    	    // 公司隐患/整改趋势图
    	    var trendChart = echarts.init(document.getElementById('trendEchart'));
    	    
    	    trendOption = {
    		   /* title: {
    		        text: '公司隐患/整改趋势图',
    		        //subtext: '纯属虚构'
    		    },*/
    		    tooltip: {
    		        trigger: 'axis'
    		    },
    		    legend: {
    		        data:['隐患数','整改数','整改率']
    		    },
    		    toolbox: {
    		        show: true,
    		        feature: {
    		            dataZoom: {},
    		            dataView: {readOnly: false},
    		            magicType: {type: ['line', 'bar']},
    		            restore: {},
    		            saveAsImage: {}
    		        }
    		    },
    		    xAxis:  {
    		        type: 'category',
    		        boundaryGap: false,
    		        data: ["一月", "二月", "三月", "四月", "五月", "六月", "七月"]
    		    },
    		    yAxis: {
    		        type: 'value',
    		        axisLabel: {
    		            formatter: '{value}'
    		        }
    		    },
    		    series: [
    		        {
    		            name:'隐患数',
    		            type:'line',
    		            data:[30, 15, 21, 13, 12, 13, 10],
    		            markPoint: {
    		                data: [
    		                    {type: 'max', name: '最大值'},
    		                    {type: 'min', name: '最小值'}
    		                ]
    		            },
    		            markLine: {
    		                data: [
    		                    {type: 'average', name: '平均值'}
    		                ]
    		            }
    		        },
    		        {
    		            name:'整改数',
    		            type:'line',
    		            data:[28, 10, 2, 10, 3, 10, 9],
    	            	markPoint : {
    		            	data : [
    	    	                    {type : 'max', name: '最大值'},
    	    	                    {type : 'min', name: '最小值'}
    	    	                ]
    		            },
    		            markLine: {
    		                data: [
    		                    {type: 'average', name: '平均值'},
    		                    [{
    		                        symbol: 'arrow',
    		                        label: {
    		                            normal: {
    		                                formatter: '最大值'
    		                            }
    		                        },
    		                        type: 'max',
    		                        name: '最大值'
    		                    }, {
    		                        symbol: 'circle',
    		                        x: '60%',
    		                        y: '50%'
    		                    }]
    		                ]
    		            }
    		        }
    		    ]
    		};
    	    
    	    trendChart.setOption(trendOption);
        });
    });
    
    
    // 整改情况(集团AND分版块)
    var correctiveChart = echarts.init(document.getElementById('correctiveEchart'));
    
    correctiveOption = {
    		title : {
    	        text: '隐患整改情况',
    	        //subtext: '纯属虚构'
    	    },
    	    tooltip : {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	        data:['隐患数','整改数']
    	    },
    	    toolbox: {
    	        show : true,
    	        feature : {
    	            dataView : {show: true, readOnly: false},
    	            magicType : {show: true, type: ['line', 'bar']},
    	            restore : {show: true},
    	            saveAsImage : {show: true}
    	        }
    	    },
    	    calculable : true,
    	    xAxis : [
    	        {
    	            type : 'category',
    	            data : ['石化板块','集装箱板块','杂货板块','散货板块']
    	        }
    	    ],
    	    yAxis : [
    	        {
    	            type : 'value'
    	        }
    	    ],
    	    series : [
    	        {
    	            name:'隐患数',
    	            type:'bar',
    	            data:[335, 310, 234, 135],
    	            markPoint : {
    	                data : [
    	                    {type : 'max', name: '最大值'},
    	                    {type : 'min', name: '最小值'}
    	                ]
    	            },
    	            markLine : {
    	                data : [
    	                    {type : 'average', name: '平均值'}
    	                ]
    	            }
    	        },
    	        {
    	            name:'整改数',
    	            type:'bar',
    	            data:[300, 250, 204, 105],
    	            markPoint : {
    	            	data : [
        	                    {type : 'max', name: '最大值'},
        	                    {type : 'min', name: '最小值'}
        	                ]
    	            },
    	            markLine : {
    	                data : [
    	                    {type : 'average', name : '平均值'}
    	                ]
    	            }
    	        }
    	    ]
    	};

    // 使用刚指定的配置项和数据显示图表。
    correctiveChart.setOption(correctiveOption);
    
    
    correctiveChart.on('click', function (params) {
    	
    	$('#timelineModal').modal();
    	
	    // 公司隐患/整改趋势图
	    var trendChart = echarts.init(document.getElementById('trendEchart'));
	    
	    trendOption = {
		   /* title: {
		        text: '公司隐患/整改趋势图',
		        //subtext: '纯属虚构'
		    },*/
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['隐患数','整改数','整改率']
		    },
		    toolbox: {
		        show: true,
		        feature: {
		            dataZoom: {},
		            dataView: {readOnly: false},
		            magicType: {type: ['line', 'bar']},
		            restore: {},
		            saveAsImage: {}
		        }
		    },
		    xAxis:  {
		        type: 'category',
		        boundaryGap: false,
		        data: ["一月", "二月", "三月", "四月", "五月", "六月", "七月"]
		    },
		    yAxis: {
		        type: 'value',
		        axisLabel: {
		            formatter: '{value}'
		        }
		    },
		    series: [
		        {
		            name:'隐患数',
		            type:'line',
		            data:[30, 15, 21, 13, 12, 13, 10],
		            markPoint: {
		                data: [
		                    {type: 'max', name: '最大值'},
		                    {type: 'min', name: '最小值'}
		                ]
		            },
		            markLine: {
		                data: [
		                    {type: 'average', name: '平均值'}
		                ]
		            }
		        },
		        {
		            name:'整改数',
		            type:'line',
		            data:[28, 10, 2, 10, 3, 10, 9],
	            	markPoint : {
		            	data : [
	    	                    {type : 'max', name: '最大值'},
	    	                    {type : 'min', name: '最小值'}
	    	                ]
		            },
		            markLine: {
		                data: [
		                    {type: 'average', name: '平均值'},
		                    [{
		                        symbol: 'arrow',
		                        label: {
		                            normal: {
		                                formatter: '最大值'
		                            }
		                        },
		                        type: 'max',
		                        name: '最大值'
		                    }, {
		                        symbol: 'circle',
		                        x: '60%',
		                        y: '50%'
		                    }]
		                ]
		            }
		        }
		    ]
		};
	    
	    trendChart.setOption(trendOption);
    });
    
    
    
    
    var coverChart = echarts.init(document.getElementById('coverEchart'));
    
    coverOption = {
	    /*title: {
	        text: '自'
	    },*/
	    legend: {
	        data: ['排查指标','实际排查情况']
	    },
	    tooltip: { 
	    	trigger: 'item',
        	},
	    radar: [
	        {
	            indicator: [
	                { text: '指标一' },
	                { text: '指标二' },
	                { text: '指标三' },
	                { text: '指标四' },
	                { text: '指标五' }
	            ],
	            center: ['25%', '50%'],
	            radius: 120,
	            startAngle: 90,
	            splitNumber: 4,
	            shape: 'circle',
	            name: {
	                formatter:'【{value}】',
	                textStyle: {
	                    color:'#72ACD1'
	                }
	            },
	            splitArea: {
	                areaStyle: {
	                    color: ['rgba(114, 172, 209, 0.2)',
	                    'rgba(114, 172, 209, 0.4)', 'rgba(114, 172, 209, 0.6)',
	                    'rgba(114, 172, 209, 0.8)', 'rgba(114, 172, 209, 1)'],
	                    shadowColor: 'rgba(0, 0, 0, 0.3)',
	                    shadowBlur: 10
	                }
	            },
	            axisLine: {
	                lineStyle: {
	                    color: 'rgba(255, 255, 255, 0.5)'
	                }
	            },
	            splitLine: {
	                lineStyle: {
	                    color: 'rgba(255, 255, 255, 0.5)'
	                }
	            }
	        },
	    ],
	    series: [
	        {
	            name: '',
	            type: 'radar',
	            itemStyle: {
	                emphasis: {
	                    // color: 各异,
	                    lineStyle: {
	                        width: 4
	                    }
	                }
	            },
	            data: [
	                {
	                    value: [100, 80, 30, 20, 150],
	                    name: '排查指标',
	                    symbol: 'rect',
	                    symbolSize: 5,
	                    lineStyle: {
	                        normal: {
	                            type: 'dashed'
	                        }
	                    }
	                },
	                {
	                    value: [60, 100, 10, 40, 100],
	                    name: '实际排查情况',
	                    areaStyle: {
	                        normal: {
	                            color: 'rgba(255, 255, 255, 0.5)'
	                        }
	                    }
	                }
	            ]
	        }
	    ]
	    
	}
    
    coverChart.setOption(coverOption);
    
    
    
    
    
  });