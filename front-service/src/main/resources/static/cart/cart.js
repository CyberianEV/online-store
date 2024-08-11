angular.module('store').controller('cartController', function ($scope, $http) {

    $scope.loadCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.createOrder = function () {
        $http.post('http://localhost:5555/core/api/v1/orders')
            .then(function successCallback(response) {
                $scope.loadCart();
            }, function errorCallback(response) {
                alert(response.data.message);
            });
    };

    $scope.clearCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.changeItemQuantity = function(productId, delta) {
        $http({
            url: 'http://localhost:5555/cart/api/v1/cart/change_quantity',
            method: 'GET',
            params: {
                productId: productId,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.loadCart();
});