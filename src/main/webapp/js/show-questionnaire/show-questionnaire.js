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
                });
            }

        }],

    });
