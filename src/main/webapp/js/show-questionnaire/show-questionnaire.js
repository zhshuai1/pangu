var questionnaireApp = angular.module('questionnaireApp', []);
questionnaireApp.component("questionnaire", {
    templateUrl: "template/questionnaire.template.html",
    controller: ['$http', function ($http) {
        var self = this;
        $http.get('/questionnaires/2').then(function (response) {
            console.log(response.data.questions);
            self.questions = response.data.questions;
        });
    }],
});

