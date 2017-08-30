var viewQuestionnairesApp = angular.module("viewQuestionnairesApp", []);
viewQuestionnairesApp.controller("viewQuestionnairesController", ["$scope", "$http", function ($scope, $http) {
    $http.get('/questionnaires/').then(function (response) {
        $scope.questionnaires = response.data;
        console.log(response);
        $scope.hello = "asdfsdf";
    }, function () {
        console.log("failed.......");
    });
}]);