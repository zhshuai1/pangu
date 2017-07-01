var questionnaireApp = angular.module('questionnaireApp', []);
questionnaireApp.component("questionnaire", {
    templateUrl:"template/questionnaire.template.html" ,
    controller: function QuestionnaireController($scope) {
        this.questions = [
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

