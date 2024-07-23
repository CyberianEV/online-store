angular.module('store', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8190/store-core/api/v1';

    if ($localStorage.onlineStoreUser) {
        try {
            let jwt = $localStorage.onlineStoreUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is expired!");
                delete $localStorage.onlineStoreUser;
                $http.defaults.headers.common.Authorization = '';
            } else {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.onlineStoreUser.token;
            }
        } catch(e) {
        }
    };

    $scope.loadProducts = function () {
        $http.get(contextPath + '/products')
            .then(function (response) {
                $scope.products = response.data;
                // console.log(response);
            });
    };

    $scope.deleteProduct = function (id) {
        $http.delete(contextPath + '/products/' + id)
            .then(function (response) {
                $scope.loadProducts();
            });
    };

    $scope.createNewProduct = function () {
        // console.log($scope.newProduct);
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.loadProducts();
            });
    };

    $scope.addProductToCart = function (productId) {
        $http.get('http://localhost:8191/store-cart/api/v1/cart/add/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.loadCart = function () {
        $http.get('http://localhost:8191/store-cart/api/v1/cart')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.clearCart = function () {
        $http.get('http://localhost:8191/store-cart/api/v1/cart/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.changeItemQuantity = function(productId, delta) {
        $http({
            url: 'http://localhost:8191/store-cart/api/v1/cart/change_quantity',
            method: 'GET',
            params: {
                productId: productId,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.tryToLogin = function () {
        $http.post('http://localhost:8192/store-auth/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.jwToken) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.jwToken;
                    $localStorage.onlineStoreUser = {username: $scope.user.username, token: response.data.jwToken};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }

            }, function errorCallback(response) {
                alert(response.data.message);
            });
    };

    $scope.tryToLogout = function () {
        delete $localStorage.onlineStoreUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.onlineStoreUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.getUserInfo = function () {
        $http.get('http://localhost:8190/store-core/get_my_email')
            .then(function (response) {
                alert(response.data.userInfo);
            });
    };

    $scope.createOrder = function () {
        $http.post(contextPath + '/orders')
            .then(function successCallback(response) {
                $scope.loadCart();
            }, function errorCallback(response) {
                alert(response.data.message);
            });
    };

    $scope.loadProducts();
    $scope.loadCart();
});