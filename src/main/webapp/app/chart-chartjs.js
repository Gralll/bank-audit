/**
 * @Package: Ultra Admin HTML Theme
 * @Since: Ultra 1.0
 * This file is part of Ultra Admin Theme HTML package.
 */


jQuery(function($) {

    'use strict';

    var ULTRA_SETTINGS = window.ULTRA_SETTINGS || {};

    /*--------------------------------
        Chart Js Chart
     --------------------------------*/
    ULTRA_SETTINGS.chartJS = function() {





        /*Bar Chart*/
        var randomScalingFactor = function() {
            return Math.round(Math.random() * 100)
        };

        var barChartData = {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            datasets: [{
                fillColor: "rgba(31,181,172,1)",
                strokeColor: "rgba(31,181,172,0.8)",
                highlightFill: "rgba(31,181,172,0.8)",
                highlightStroke: "rgba(31,181,172,1)",
                data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]
            }, {
                fillColor: "rgba(153,114,181,1.0)",
                strokeColor: "rgba(153,114,181,0.8)",
                highlightFill: "rgba(153,114,181,0.8)",
                highlightStroke: "rgba(153,114,181,1.0)",
                data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]
            }]

        }

        var ctxb = document.getElementById("bar-chartjs").getContext("2d");
        window.myBar = new Chart(ctxb).Bar(barChartData, {
            responsive: true
        });


        /*Line Chart*/

        var randomScalingFactor = function() {
            return Math.round(Math.random() * 100)
        };
        var lineChartData = {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            datasets: [{
                label: "My First dataset",
                fillColor: "rgba(31,181,172,0.5)",
                strokeColor: "rgba(31,181,172,1)",
                pointColor: "rgba(31,181,172,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(31,181,172,1)",
                data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]
            }, {
                label: "My Second dataset",
                fillColor: "rgba(153,114,181,0.5)",
                strokeColor: "rgba(153,114,181,1.0)",
                pointColor: "rgba(153,114,181,1.0)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(153,114,181,1.0)",
                data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]
            }]

        }

        var ctx = document.getElementById("line-chartjs").getContext("2d");
        window.myLine = new Chart(ctx).Line(lineChartData, {
            responsive: true
        });




        /*PIE Chart*/


        var pieData = [{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "Orange"
            }, {
                value: 150,
                color: "rgba(31,181,172,1)",
                highlight: "rgba(31,181,172,0.8)",
                label: "Primary"
            }, {
                value: 50,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "Yellow"
            }, {
                value: 120,
                color: "rgba(153,114,181,1.0)",
                highlight: "rgba(153,114,181,0.8)",
                label: "Purple"
            }

        ];

        var ctx = document.getElementById("pie-chartjs").getContext("2d");
        window.myPie = new Chart(ctx).Pie(pieData);




        /* Donut Chart*/

        var doughnutData = [{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "1"
            }, {
                value: 150,
                color: "rgba(31,181,172,1)",
                highlight: "rgba(31,181,172,0.8)",
                label: "Primary"
            }, {
                value: 50,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "Yellow"
            }, {
                value: 120,
                color: "rgba(153,114,181,1.0)",
                highlight: "rgba(153,114,181,0.8)",
                label: "Purple"
            }

        ];

        var ctxd = document.getElementById("donut-chartjs").getContext("2d");
        window.myDoughnut = new Chart(ctxd).Doughnut(doughnutData, {
            responsive: true
        });





        /*Polar Chart*/

        var polarData = [{
                value: 0.8,
                color: "#FDB45C",
                highlight: "#FFC870",	
                label: "M1"
            }, 
			{
                value: 0.95,
                color: "rgba(31,181,172,1)",
                highlight: "rgba(31,181,172,0.8)",
                label: "M2"
            },
			{
                value: 1,
                color: "rgba(31,181,172,1)",
                highlight: "rgba(31,181,172,0.8)",
                label: "M3"
            },
			{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M4"
            },
			{
                value: 0.8,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M5"
            },
			{
                value: 0.9,
                color: "rgba(31,181,172,1)",
                highlight: "rgba(31,181,172,0.8)",
                label: "M6"
            },
			{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M7"
            },
			{
                value: 0.87,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M8"
            },
			{
                value: 1,
                color: "rgba(31,181,172,1)",
                highlight: "rgba(31,181,172,0.8)",
                label: "M9"
            },
			{
                value: 1,
                color: "rgba(31,181,172,1)",
                highlight: "rgba(31,181,172,0.8)",
                label: "M10"
            },
			{
                value: 0.8,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M11"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.7,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M13"
            },
			{
                value: 0.6,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M14"
            },
			{
                value: 0.65,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M15"
            },
			{
                value: 0.75,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M16"
            },
			{
                value: 0.3,
                color: "red",
                highlight: "red",
                label: "M17"
            },
			{
                value: 0.6,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M18"
            },
			{
                value: 0.66,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M19"
            },
			{
                value: 0.69,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M20"
            },
			{
                value: 0.6,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M21"
            },
			{
                value: 0.55,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M22"
            },
			{
                value: 0.56,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M23"
            },
			{
                value: 0.7,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M24"
            },
			{
                value: 0.8,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M25"
            },
			{
                value: 0.75,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M26"
            },
			{
                value: 0.9,
                color: "rgba(31,181,172,1)",
                highlight: "rgba(31,181,172,0.8)",
                label: "M27"
            },
			{
                value: 0.45,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M28"
            },
			{
                value: 0.55,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M29"
            },
			{
                value: 0.8,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M30"
            },
			{
                value: 0.9,
                color: "rgba(31,181,172,1)",
                highlight: "rgba(31,181,172,0.8)",
                label: "M31"
            },
			{
                value: 0.65,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M32"
            },
			{
                value: 0.77,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M33"
            },
			{
                value: 0.95,
                color: "rgba(31,181,172,1)",
                highlight: "rgba(31,181,172,0.8)",
                label: "M34"
            }
        ];

        var ctxp = document.getElementById("polar-chartjs").getContext("2d");
        window.myPolarArea = new Chart(ctxp).PolarArea(polarData, {
            responsive: true,
			 scaleOverride: true,
        scaleStartValue: 0,
        scaleStepWidth: 0.05,
        scaleSteps: 20
        });


        /*Polar Chart*/

        var polarData2 = [{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M4"
            },
			{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M4"
            },
			{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M4"
            },
			{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M4"
            },
			{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M4"
            },
			{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M4"
            },
			{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M4"
            },
			{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M4"
            },
			{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M4"
            },
			{
                value: 0.76,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "M4"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.2,
                color: "red",
                highlight: "red",
                label: "M12"
            },
			{
                value: 0.45,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M28"
            },
			{
                value: 0.45,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M28"
            },
			{
                value: 0.45,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M28"
            },
			{
                value: 0.45,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M28"
            },
			{
                value: 0.45,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M28"
            },
			{
                value: 0.45,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M28"
            },
			{
                value: 0.45,
                color: "rgba(250,133,100,1.0)",
                highlight: "rgba(250,133,100,0.8)",
                label: "M28",
				legendTemplate: "asd"
            },
        ];

        var ctxp = document.getElementById("polar-chartjs2").getContext("2d");
        window.myPolarArea = new Chart(ctxp).PolarArea(polarData2, {
            responsive: true,
			 scaleOverride: true,
        scaleStartValue: 0,
        scaleStepWidth: 0.05,
        scaleSteps: 20
        });







        /*Radar Chart*/
        var radarChartData = {
            labels: ["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"],
            datasets: [{
                label: "My First dataset",
                fillColor: "rgba(31,181,172,0.4)",
                strokeColor: "rgba(31,181,172,1)",
                pointColor: "rgba(31,181,172,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(31,181,172,1)",
                data: [65, 59, 90, 81, 56, 55, 40]
            }, {
                label: "My Second dataset",
                fillColor: "rgba(153,114,181,0.4)",
                strokeColor: "rgba(153,114,181,1.0)",
                pointColor: "rgba(153,114,181,1.0)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(153,114,181,1.0)",
                data: [28, 48, 40, 19, 96, 27, 100]
            }]
        };

        window.myRadar = new Chart(document.getElementById("radar-chartjs").getContext("2d")).Radar(radarChartData, {
            responsive: true
        });


    };






    /******************************
     initialize respective scripts 
     *****************************/
    $(document).ready(function() {});

    $(window).resize(function() {});

    $(window).load(function() {
        ULTRA_SETTINGS.chartJS();
    });

});
