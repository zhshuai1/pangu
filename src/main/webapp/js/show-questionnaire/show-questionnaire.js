var questionnaireApp = angular.module('questionnaireApp', []);
questionnaireApp
    .component('questionnaire', {
        templateUrl: "template/questionnaire.template.html",
        bindings: {
            locale: '=',
            questionnaireId: '=',
        },
        controller: ['$http', function ($http) {
            var self = this;
            // we must put the $http get operation in the $postLink life-cycle.
            // For angular, the initialization of a component should be:
            // Init child controller -> Init controller -|
            // Init child scope      -> Init scope      ---> bind the scope and controller

            // So if we just put the $http in the controller, it will called before angular binding qnid for the scope
            // and controller. We will get an undefined.
            this.$postLink = function () {
                $http.get('/questionnaires/' + self.questionnaireId).then(function (response) {
                    self.questions = response.data.questions;
                    self.title = 'title' + self.locale;
                    self.description = 'description' + self.locale;
                });
            }
        }],
    })

    // This component is not used yet.
    .component("timer", {
        template: "<span id='timer'>Left time: {{$ctrl.current}}</span>",
        bindings: {
            init: '=',
        },
        controller: ['$interval', function ($interval) {
            var self = this;
            this.$postLink = function () {
                self.current = self.init;
                $interval(function () {
                    console.log("The current value is:" + self.current);
                    self.current--;
                }, 1000, self.init);
            }
        }],
    })
    // I will implement address and date as components first, because they are complex enough. To keep consistency,
    // maybe I should implement all basic elements as components, such as radio, checkbox and so on.
    .component("address", {})
    .component("date", {
        template: "<select ng-model='$ctrl.selectedYear' ng-change='$ctrl.updateDays()'><option ng-repeat='year in $ctrl.years' value='{{year}}'>{{year}}</option></select>" +
        "<select ng-model='$ctrl.selectedMonth' ng-change='$ctrl.updateDays()'><option ng-repeat='month in $ctrl.months' value='{{month}}'>{{month}}</option></select>" +
        "<select ng-model='$ctrl.selectedDay'><option ng-repeat='day in $ctrl.days' value='{{day}}'>{{day}}</option></select>",
        bindings: {
            minYear: '<',
            maxYear: '<',
            defaultYear: '<',
        },
        controller: [function () {
            var self = this;

            function getDaysInMonth(year, month) {
                year = +year;
                month = +month;
                if ($.inArray(month, [1, 3, 5, 7, 8, 10, 12]) >= 0) {
                    return self.days31;
                } else if ($.inArray(month, [4, 6, 9, 11]) >= 0) {
                    return self.days31.slice(0, 30);
                } else if (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0)) {
                    return self.days31.slice(0, 28);
                } else {
                    return self.days31.slice(0, 29);
                }
            }

            this.$postLink = function ($scope) {
                self.years = [];
                self.months = [];
                self.days31 = [];
                for (var i = self.minYear; i <= self.maxYear; ++i) {
                    self.years.push(i);
                }
                for (var i = 1; i <= 12; ++i) {
                    self.months.push(i)
                }
                for (var i = 1; i <= 31; ++i) {
                    self.days31.push(i);
                }
                self.selectedYear = "" + self.defaultYear;
                self.selectedMonth = "1";
                self.selectedDay = "1";
                self.days = getDaysInMonth(self.selectedYear, self.selectedMonth);
            }

            this.updateDays = function () {
                self.days = getDaysInMonth(self.selectedYear, self.selectedMonth);
            }
        }],
    })
    .directive("test-directive", function () {
        return {
            require: 'ngModel',
            link: function (scope, ele, attr, ngModelCtrl) {

            }
        }
    });
