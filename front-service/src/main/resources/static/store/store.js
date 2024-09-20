angular.module('store').controller('storeController', function ($scope, $http, $localStorage) {

    $scope.loadProducts = function (page = 1) {
        $http({
            url: 'http://localhost:5555/core/api/v1/products',
            method: 'GET',
            params: {
                p: page
            }
        }).then(function (response) {
            $scope.productsPage = response.data;
            $scope.generatePageList($scope.productsPage.totalPages);
        });
    };

    $scope.addProductToCart = function (productId) {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.onlineStoreGuestCartId + '/add/' + productId)
            .then(function (response) {
            });
    };

    $scope.generatePageList = function (totalPages) {
        out = [];
        for (let i = 0; i < totalPages; i++) {
            out.push(i + 1);
        }
        $scope.pagesList = out;
    }

    $scope.loadProducts();

});