var questionnaireApp = angular.module('questionnaireApp', []);
questionnaireApp.component("questionnaire", {
    template: '<ol>' +
    '<li ng-repeat="question in questions">' +
    '<p>{{question.title}}</p>' +
    '<div ng-switch="question.type">' +
    '<div ng-switch-when="TEXT">' +
    '<input type="text">' +
    '</div>' +
    '<div ng-switch-when="RADIO">' +
    '<input type="radio">' +
    '</div>' +
    '<div ng-switch-when="CHECKBOX">' +
    '<input type="checkbox">' +
    '</div>' +
    '<div ng-switch-when="VALUE">' +
    '<input type="checkbox">' +
    '</div>' +
    '<div ng-switch-when="ADDRESS">' +
    '<input type="checkbox">' +
    '</div>' +
    '<div ng-switch-when="DATE">' +
    '<input type="text">' +
    '</div>' +
    '<div ng-switch-default>' +
    '<input type="password">' +
    '</div>' +
    '</div>' +
    '</li>' +
    '</ol>',
    controller: function QuestionnaireController($scope) {
        $scope.questions = [
            {
                title: "How old are you?",
                type: "TEXT",

            },
            {
                title: "What is your gender?",
                type: "RADIO"
            },
            {
                title: "What sports do you like?",
                type: "CHECKBOX"
            },
            {
                title: "When were you born?",
                type: "DATE"
            },
        ];
    },
});

