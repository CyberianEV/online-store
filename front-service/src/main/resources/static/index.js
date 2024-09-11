(function () {
    angular
        .module('store', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .otherwise({
                redirectTo: '/'
            });
    };

    function run($rootScope, $http, $localStorage) {
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

        if ($localStorage.onlineStoreUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.onlineStoreUser.token;
        };

        if (!$localStorage.onlineStoreGuestCartId) {
            $http.get('http://localhost:5555/cart/api/v1/cart/generate_id')
                .then(function(response) {
                    $localStorage.onlineStoreGuestCartId = response.data.value;
                });
        };
    };
})();

angular.module('store').controller('indexController', function ($scope, $http, $location, $localStorage) {

    $scope.tryToLogin = function () {
        $http.post('http://localhost:5555/auth/authenticate', $scope.user)
            .then(function successCallback(response) {
                if (response.data.jwToken) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.jwToken;
                    $localStorage.onlineStoreUser = {username: $scope.user.username, token: response.data.jwToken};

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $location.path('/');
                }

            }, function errorCallback(response) {
                alert(response.data.message);
            });
    };

    $scope.tryToLogout = function () {
        delete $localStorage.onlineStoreUser;
        $http.defaults.headers.common.Authorization = '';
        $location.path('/');
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.onlineStoreUser) {
            return true;
        } else {
            return false;
        }
    };
});