angular.module('receiptboxApp').config(['$locationProvider', '$routeProvider',
    function config($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('!');

        $routeProvider.
            when('/dashboard', {
                template: '<dashboard></dashboard>'
            }).
        otherwise('/');
    }
  ]);