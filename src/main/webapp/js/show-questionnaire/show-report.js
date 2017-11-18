var viewQuestionnairesApp = angular.module('showReportApp', []);
viewQuestionnairesApp
    .controller('showReportController', ['$scope', '$http', function ($scope, $http) {
    }])
    .component('questionnaireReport', {
        template: "" +
        "<div ng-if='$ctrl.questionReports'>" +
        "   <h2>{{$ctrl.questionnaireTitle}}</h2>" +
        "   <ul ng-cloak class='ng-cloak reports'>" +
        "       <li class='report-box' ng-repeat='report in $ctrl.questionReports'>" +
        "           <question-report report-data='report' question-info='$ctrl.questionInfoMap[report.questionId]'></question-report>" +
        "       </li>" +
        "   </ul>" +
        "</div>" +
        "<div ng-if='$ctrl.notFound'>" +
        "   <h1>您访问的问卷报告走丢了...</h1>" +
        "</div>",
        bindings: {
            reportId: '=',
        },
        controller: ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
            var self = this;
            this.$postLink = function () {
                $http.get('/data/reports/' + self.reportId).then(function (response) {
                    if (!response.data || response.data.length == 0) {
                        self.notFound = true;
                        return;
                    }
                    self.questionReports = response.data.questionReports;
                    $http.get('/data/questionnaires/' + self.reportId).then(function (response) {
                        var data = response.data;
                        self.questionnaireTitle = data.titleCn;
                        self.questionInfoMap = {};
                        data.questions.forEach(q => self.questionInfoMap[q.id] = q);
                        $timeout(function () {
                            $scope.$broadcast('questionnaireReportReady')
                        });
                    }, function () {
                        alert("获取问卷信息失败！");
                    });
                }, function () {
                    alert('获取报告数据失败！');
                });


            };
        }],
    })
    .directive("questionReport", ['$timeout', function ($timeout) {
            function buildReportWithChoicesData(questionInfo, reportData) {
                var counts = reportData.counts;
                var choices = questionInfo.choices;
                var reports = [];
                for (var i in choices) {
                    reports[i] = {
                        label: choices[i].descriptionCn,
                        count: counts[choices[i].id],
                    };
                }
                return reports;
            }

            function buildIntegerData(reportData) {
                var counts = reportData.counts;
                var min = +Infinity, max = -Infinity;
                for (var n in counts) {
                    // if n is not a number, and k is a number, +n>k || +n<k === false
                    max = +n > max ? +n : max;
                    min = +n < min ? +n : min;
                }
                var numberOfBar = 7;
                var interval = (max - min + 1) / numberOfBar;

                var reports = [];
                for (var i = 0; i < numberOfBar; ++i) {
                    // used to identify the label for each bar. i.e. 34-45
                    reports[i] = {
                        label: '' + Math.floor(min + interval * i) + '-' + Math.floor(min + interval * (i + 1)),
                        count: 0,
                    };
                }
                for (var k in counts) {
                    var key = +k;
                    if (!(key > -Infinity)) {
                        continue;
                    }
                    var index = Math.floor((key - min) / interval);
                    console.log("The index is : " + index);
                    reports[index]['count'] += +counts[k];
                }
                return reports;
            }

            function buildReportData(questionInfo, reportData) {
                switch (questionInfo.type) {
                    case 'INTEGER':
                        return buildIntegerData(reportData);
                    case 'RADIO':
                    case 'CHECKBOX':
                        return buildReportWithChoicesData(questionInfo, reportData);
                    default:
                        return [];
                        break;
                }
            }

            function link(scope, element, attributes, controller, transcludeFn) {
                var process = function () {
                    var reportData = scope.reportData;
                    var questionInfo = scope.questionInfo;
                    console.log("The question info is: ")
                    console.log(questionInfo);
                    var reports = buildReportData(questionInfo, reportData);
                    console.log(reports);
                    var sData = reports.map(r => r.count / reportData.counts.total);
                    var xData = reports.map(r => r.label);
                    var chart = echarts.init(document.getElementById("" + reportData.questionId));
                    var option = {
                        title: {
                            text: questionInfo.titleCn,
                        },
                        tooltip: {},
                        legend: {
                            data: ['比例'],
                        },
                        xAxis: {
                            data: xData,
                        },
                        yAxis: {},
                        series: [{
                            name: '比例',
                            type: 'bar',
                            data: sData,
                        }]
                    };

                    chart.setOption(option);
                }
                scope.$on('questionnaireReportReady', function () {
                    $timeout(process);
                });
            }

            return {
                template: "<a href='#'></href><div id='{{reportData.questionId}}'></div></a>",
                scope: {
                    reportData: '=',
                    questionInfo: '=',
                },
                link: link,
            }
        }

        ]
    );