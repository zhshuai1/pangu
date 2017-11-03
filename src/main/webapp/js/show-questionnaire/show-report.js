var viewQuestionnairesApp = angular.module("showReportApp", []);
viewQuestionnairesApp
    .controller("showReportController", ["$scope", "$http", function ($scope, $http) {

        $scope.questionReports = [
            {questionId: 1999, counts: {19999: 3}},
            {questionId: 2000, counts: {20: 3}},
            {questionId: 2001, counts: {20: 3}}];
    }])
    .directive('onFinishRenderFilters', ['$timeout', function ($timeout) {
        return {
            restrict: 'A',
            link: function (scope) {
                $timeout(function () {
                    scope.$broadcast('ngRepeatFinished');
                });

            }
        };
    }])
    .directive("report", ['$http', function ($http) {
            function link(scope, element, attributes, controller, transcludeFn) {
                scope.$on('ngRepeatFinished', function () {
                    var id = scope.reportData.questionId;
                    var myChart = echarts.init(document.getElementById("" + id));
                    var option = {
                        title: {
                            text: 'ECharts 入门示例'
                        },
                        tooltip: {},
                        legend: {
                            data: ['销量', '销售额']
                        },
                        xAxis: {
                            data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
                        },
                        yAxis: {},
                        series: [{
                            name: '销量',
                            type: 'bar',
                            data: [5, 20, 36, 10, 10, 20]
                        }, {
                            name: '销售额',
                            type: 'bar',
                            data: [5, 20, 36, 10, 10, 20]
                        },
                        ]
                    };

                    myChart.setOption(option);
                });
            }

            return {
                template: "<a href='#'></href><div id='{{reportData.questionId}}'></div></a>",
                scope: {
                    reportData: "=",
                },
                link: link,
            }
        }]
    );