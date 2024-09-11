angular.module('store').controller('storeController', function ($scope, $http, $localStorage) {

    $scope.loadProducts = function () {
        $http.get('http://localhost:5555/core/api/v1/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };

    $scope.addProductToCart = function (productId) {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.onlineStoreGuestCartId + '/add/' + productId)
            .then(function (response) {
            });
    };

    $scope.loadProducts();

});