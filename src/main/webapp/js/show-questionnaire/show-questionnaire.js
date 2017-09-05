var questionnaireApp = angular.module('questionnaireApp', []);
questionnaireApp
    .controller('questionnaireController', ['$scope', function ($scope) {
        $scope.$on('ngRepeatFinished', function (ngRepeatFinishedEvent) {
            $("form").validator("destroy").validator();
        });
        $scope.onSubmit = function () {

            $(".sep-form").validator('validate');
            if ($(this).hasClass("disabled")) {
                return;
            }
            // This logic is due to bootstrap-validator does not support the validation for <select>
            var $selects = $(".sep-select").toArray();
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
            if (!valid) return;
            //currently the submit gather all form data with this function, will refactor will ng-model.
            var data = getFormData("#complete-info form");
            console.log(data);
            $.ajax({
                url: "/complete-info",
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(data),
                success: function (response) {
                    var errorCode = response.errorCode;

                    if (errorCode == "SUCCESS") {
                        // After the complete Action finished, will call a callback defined outside;
                        // This is not a good practice, just workaround to couple the angular and non-angular;
                        if (completeCallback) {
                            completeCallback();
                        }
                    } else if (errorCode == "USER_EXIST") {
                        alert("The username has been registered.");
                    } else if (errorCode == "INVALID_INPUT") {
                        alert("The data you fill in is invalid.");
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
                    if (!response.data) {
                        // The requested questionnaire does not exist.
                        self.notFound = true;
                        return;
                    }
                    self.questions = response.data.questions;
                    self.questions.forEach(function (question) {
                        if (question.constraint) {
                            var constraint = JSON.parse(question.constraint);
                            question.required = constraint.required;
                            if (constraint.pattern) {
                                question.pattern = constraint.pattern;
                            } else {
                                question.pattern = "^[^<>]*$";
                            }
                        }

                    });
                    self.title = 'title' + self.locale;
                    self.description = 'description' + self.locale;
                });
            }
        }],
    })
    .component("sepSelect", {
        template: "<select class='btn btn-default'>" +
        "<input type='hidden' value='{{$ctrl.defaultValue}}' name='{{$ctrl.questionId}}'" +
        "<option ng-repeat='choice in $ctrl.choices' value='{{choice.id}}'>{{choice[$ctrl.description]}}</option>" +
        "</select>",
        bindings: {
            choices: '=',
            questionId: '=',
            defaultValue: '',
        },
        controller: [function () {

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
    .component("address", {
        template: "<div class='sep-select'><input type='hidden' name='{{$ctrl.formName}}' value='{{$ctrl.selectedDistrict}}'>" +
        "<div class='btn-group btn-group-vertical'><select class='btn btn-default' ng-model='$ctrl.selectedCountry' ng-change='$ctrl.updateProvinces()'><option ng-repeat='country in $ctrl.countries' value='{{country.id}}'>{{country[$ctrl.description]}}</option></select>" +
        "<select class='btn btn-default' ng-model='$ctrl.selectedProvince' ng-change='$ctrl.updateCities()'><option ng-repeat='province in $ctrl.provinces' value='{{province.id}}'>{{province[$ctrl.description]}}</option></select>" +
        "<select class='btn btn-default' ng-model='$ctrl.selectedCity' ng-change='$ctrl.updateDistricts()'><option ng-repeat='city in $ctrl.cities' value='{{city.id}}'>{{city[$ctrl.description]}}</option></select>" +
        "<select class='btn btn-default' ng-model='$ctrl.selectedDistrict'><option ng-repeat='district in $ctrl.districts' value='{{district.id}}'>{{district[$ctrl.description]}}</option></select></div></div>",
        bindings: {
            root: '<',
            level: '<',
            locale: '<',
            formName: '<',
        },
        controller: ['$http', function ($http) {
            var self = this;

            this.updateProvinces = function () {
                $http.get('/choices/' + self.selectedCountry).then(function (response) {
                    self.provinces = response.data;
                    self.selectedCity = "";
                    self.districts = [];
                });
            }

            this.updateCities = function () {
                $http.get('/choices/' + self.selectedProvince).then(function (response) {
                    self.cities = response.data;
                    self.districts = [];
                });
            }
            this.updateDistricts = function () {
                $http.get('/choices/' + self.selectedCity).then(function (response) {
                    self.districts = response.data;
                });
            }

            this.$postLink = function () {
                var level = self.level;
                var root = self.root;
                $http.get('/choices/' + root).then(function (response) {
                    self.countries = response.data;
                });
                self.description = "description" + self.locale;
                self.selectedCountry = "100";
                this.updateProvinces();
            }
        }],
    })
    .component("date", {
        template: "<div class='sep-select'><input type='hidden' name='{{$ctrl.formName}}' value='{{$ctrl.date}}'>" +
        "<div class='btn-group'><select class='btn btn-default' ng-model='$ctrl.selectedYear' ng-change='$ctrl.update()'><option ng-repeat='year in $ctrl.years' value='{{year}}'>{{year}}</option></select>" +
        "<select class='btn btn-default' ng-model='$ctrl.selectedMonth' ng-change='$ctrl.update()'><option ng-repeat='month in $ctrl.months' value='{{month}}'>{{month}}</option></select>" +
        "<select class='btn btn-default' ng-model='$ctrl.selectedDay' ng-change='$ctrl.update()'><option ng-repeat='day in $ctrl.days' value='{{day}}'>{{day}}</option></select></div></div>",
        bindings: {
            minYear: '<',
            maxYear: '<',
            defaultYear: '<',
            formName: '<',
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
