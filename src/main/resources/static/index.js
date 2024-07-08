angular.module('store', []).controller('indexController', function ($scope, $http) {
    $scope.fillTable = function () {
        $http.get('http://localhost:8190/store/api/v1/products')
            .then(function (response) {
                $scope.products = response.data;
                // console.log(response);
            });
    };

    $scope.deleteProduct = function (id) {
        $http.delete('http://localhost:8190/store/api/v1/products/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $scope.createNewProduct = function () {
        // console.log($scope.newProduct);
        $http.post('http://localhost:8190/store/api/v1/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    };

    $scope.addProductToCart = function (productId) {
        $http.get('http://localhost:8190/store/api/v1/cart/add/' + productId)
            .then(function (response) {
                $scope.fillCart();
            });
    };

    $scope.fillCart = function () {
        $http.get('http://localhost:8190/store/api/v1/cart')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.fillTable();
    $scope.fillCart();
});