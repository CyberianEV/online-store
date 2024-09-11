angular.module('store').controller('cartController', function ($scope, $http, $localStorage) {

    $scope.loadCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.onlineStoreGuestCartId)
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

    $scope.guestCreateOrder = function () {
        alert('Please log in to your account to place an order');
    };

    $scope.clearCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.onlineStoreGuestCartId + '/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.changeItemQuantity = function(productId, delta) {
        $http({
            url: 'http://localhost:5555/cart/api/v1/cart/' + $localStorage.onlineStoreGuestCartId + '/change_quantity',
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