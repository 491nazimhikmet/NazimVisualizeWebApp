var app = angular.module('myApp', []);

app.constant("appConfig",{
	"baseUrl" : "",
	"baseImagePathUrl":"/NazimVisualWebApp/resources/createdImages/"
});

app.factory("BaseAPI",[ '$http', '$q','appConfig', function ($http, $q,appConfig) {
    return {

        callServlet: function (servletName,paramaters) {

        	Object.toparams = function ObjecttoParams(obj) {
			    var p = [];
			    for (var key in obj) {
			        p.push(key + '=' + encodeURIComponent(obj[key]));
			    }
			    return p.join('&');
			};
          
            var rootURL= appConfig.baseUrl;

            var deferred = $q.defer();
            
            var resultURL =rootURL+servletName;
            console.log("URL:"+resultURL);


            var req = {
                method: 'POST',
             
                url:resultURL,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                },
                data: Object.toparams(paramaters)
            };

          
          
            $http(req).
                success(function (data, status, headers, config) {
                     
                    deferred.resolve(data); 
                 

                }).
                error(function (err, status) { 

                    bootbox.alert(err, function() {
                      location.reload();
                    });
                    
                    deferred.reject(err);
                });

            return deferred.promise;                     
         
        }

    };
}]);

app.service('sharedProperties', function () {
        var searchText = "";

        return {
            getSearchText: function () {
                return searchText;
            },
            setSearchText: function(value) {
                searchText = value;
            }
        };
    });