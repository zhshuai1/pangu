function getFormData(selector) {
    var data = {};
    var arrayTypeData = [];
    $(selector).serializeArray().map(function (x) {
        var name = x.name;
        if ($('input[name=' + name + ']').attr('type') == 'checkbox') {
            if (!data[name]) {
                data[name] = [];
                arrayTypeData.push(name);
            }
            data[name].push(x.value);
        } else {
            data[name] = x.value;
        }
    });
    arrayTypeData.forEach(a => data[a] = JSON.stringify(data[a]));
    return data;
}

var questionnaireApp = angular.module('questionnaireApp', []);
questionnaireApp
    .controller('questionnaireController', ['$scope', function ($scope) {
        $scope.$on('ngRepeatFinished', function (ngRepeatFinishedEvent) {
            $("form").validator("destroy").validator();
        });
    }])
    .directive('onFinishRenderFilters', ['$timeout', function ($timeout) {
        return {
            restrict: 'A',
            link: function (scope, element, attr) {
                if (scope.$last === true) {
                    $timeout(function () {
                        scope.$emit('ngRepeatFinished');
                    });
                }
            }
        };
    }])
    .component('questionnaire', {
        templateUrl: "/template/questionnaire.template.html",
        bindings: {
            locale: '=',
            questionnaireId: '=',
            targetUrl: '=',
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
                $http.get('/data/questionnaires/' + self.questionnaireId).then(function (response) {
                    if (!response.data) {
                        // The requested questionnaire does not exist.
                        self.notFound = true;
                        return;
                    }
                    self.questions = response.data.questions;
                    self.questions.forEach(function (question) {
                        if (question.constraint) {
                            var constraint = JSON.parse(question.constraint);
                            question.constraint = constraint;
                            question.required = constraint.required;
                            if (constraint.pattern) {
                                question.pattern = constraint.pattern;
                            } else {
                                question.pattern = "^[^<>]*$";
                            }
                        } else {
                            question.constraint = {};
                        }

                    });
                    self.title = 'title' + self.locale;
                    self.description = 'description' + self.locale;
                });
            }
            this.onSubmit = function () {

                $(".sep-form").validator('validate');

                // This logic is due to bootstrap-validator does not support the validation for <select>
                var $selects = $(".sep-select[required]").toArray();
                console.log($selects);
                var valid = true;
                for (var i in $selects) {
                    var val = $($selects[i]).find("input[type=hidden]").val();
                    if (!val) {
                        $($selects[i]).addClass("errored-form-control");
                        valid = false;
                    } else {
                        $($selects[i]).removeClass("errored-form-control");
                    }
                }
                if (!valid || $("#btn-submit-questionnaire").hasClass("disabled")) {
                    return;
                }
                //currently the submit gather all form data with this function, will refactor will ng-model.
                var data = getFormData("#questionnaire-form");
                console.log(data);
                $.ajax({
                    url: self.targetUrl,
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify(data),
                    success: function (response) {
                        var errorCode = response.errorCode;
                        var extraData = response.extraData;
                        if (errorCode == "SUCCESS") {
                            // After the complete Action finished, will call a callback defined outside;
                            // This is not a good practice, just workaround to couple the angular and non-angular;
                            if (completeCallback) {
                                completeCallback(extraData);
                            }
                        } else if (errorCode == "USER_EXIST") {
                            alert("The username has been registered.");
                        } else if (errorCode == "INVALID_INPUT") {
                            alert("The data you fill in is invalid.");
                        } else if (errorCode == "NOT_LOGGIN") {
                            var redirectUrl = extraData.redirectUrl;
                            location.replace("/login?redirectUrl=" + encodeURIComponent(redirectUrl));
                        } else {
                            alert("Unknown issue occurs, please contact us: zh_ang_ok@yeah.net");
                        }

                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        console.log(XMLHttpRequest.status);
                        console.log(XMLHttpRequest.readyState);
                        console.log(textStatus);
                        console.log(errorThrown);
                        alert("Unknown issue occurs, please contact us: zh_ang_ok@yeah.net");
                    }
                });
                return false;
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
    .component("multiSelect", {
        template: "<div class='sep-select' ng-required='{{$ctrl.valueRequired}}'><input type='hidden' name='{{$ctrl.fieldName}}' value='{{$ctrl.value}}'>" +
        "   <div class='btn-group btn-group-vertical'>" +
        "       <select ng-repeat='data in $ctrl.data' class='btn btn-default' ng-model='data.selected' ng-change='$ctrl.update({{$index}})'>" +
        "           <option ng-repeat='candidate in data.candidates' value='{{candidate.id}}'>{{candidate.descriptionCn}}</option>" +
        "       </select>" +
        "   </div>" +
        "</div>",
        bindings: {
            root: '<',
            level: '<',
            locale: '<',
            fieldName: '<',
            valueRequired: '<',
        },
        controller: ['$http', function ($http) {
            var self = this;
            self.data = [];
            self.update = function (i) {
                if (self.level - 1 == i) {
                    self.value = self.data[i].selected;
                } else {
                    $http.get('/data/choices/' + self.data[i].selected).then(function (response) {
                        self.data[i + 1].candidates = response.data;
                    });
                    self.value = null;
                    self.data[i + 1].selected = "";
                    for (var k = i + 2; k < self.level; ++k) {
                        self.data[k].selected = "";
                        self.data[k].candidates = [];
                    }

                }
            }
            this.$postLink = function () {
                for (var i = 0; i < self.level; ++i) {
                    self.data.push({candidates: [], selected: ""});
                }
                $http.get('/data/choices/' + self.root).then(function (response) {
                    self.data[0].candidates = response.data;
                    self.data[0].selected = "";
                });
            }

        }],
    })
    .component("date", {
        template: "<div class='sep-select'><input type='hidden' name='{{$ctrl.fieldName}}' value='{{$ctrl.date}}'>" +
        "<div class='btn-group'><select class='btn btn-default' ng-model='$ctrl.selectedYear' ng-change='$ctrl.update()'><option ng-repeat='year in $ctrl.years' value='{{year}}'>{{year}}</option></select>" +
        "<select class='btn btn-default' ng-model='$ctrl.selectedMonth' ng-change='$ctrl.update()'><option ng-repeat='month in $ctrl.months' value='{{month}}'>{{month}}</option></select>" +
        "<select class='btn btn-default' ng-model='$ctrl.selectedDay' ng-change='$ctrl.update()'><option ng-repeat='day in $ctrl.days' value='{{day}}'>{{day}}</option></select></div></div>",
        bindings: {
            minYear: '<',
            maxYear: '<',
            defaultYear: '<',
            fieldName: '<',
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

            this.$postLink = function () {
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
                this.update();
            }

            this.update = function () {
                self.days = getDaysInMonth(self.selectedYear, self.selectedMonth);
                var date = new Date(self.selectedYear, +self.selectedMonth - 1, self.selectedDay);
                self.date = date.getTime();
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
