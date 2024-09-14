angular.module('store').controller('signUpController', function ($scope, $http, $location) {
    $scope.tryToSignUp = function () {
        $http.post('http://localhost:5555/auth/register', $scope.userDetails)
            .then(function successCallback(response) {
                $scope.userDetails.username = null;
                $scope.userDetails.email = null;
                $scope.userDetails.password = null;
                $scope.userDetails.confPassword = null;

                $location.path('/');

                alert("You have successfully registered!");

            }, function errorCallback(response) {
                alert(response.data.message);
            });
    };
});