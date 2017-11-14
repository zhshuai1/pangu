var viewQuestionnairesApp = angular.module("viewQuestionnairesApp", []);
viewQuestionnairesApp.controller("viewQuestionnairesController", ["$scope", "$http", function ($scope, $http) {
    $http.get('/data/questionnaires/').then(function (response) {
        $scope.questionnaires = response.data;
    }, function () {
        console.log("failed.......");
    });
}]);