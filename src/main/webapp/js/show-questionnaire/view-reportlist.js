var viewReportsApp = angular.module('viewQuestionnaireReportsApp', []);
viewReportsApp.controller('viewQuestionnaireReportsController', ['$http', '$scope', function ($http, $scope) {
    $http.get('/data/reports?pageNumber=0&pageSize=10').then(function (response) {
        // Though here we call it reports, in fact the response data is questoinnaires.
        $scope.reports = response.data;
    }, function () {
        alert("获取服务器数据失败，请稍后重试！");
    });
}]);