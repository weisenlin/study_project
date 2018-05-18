/**
 * Description: 利用angularjs实现单页应用
 *
 * @author baolin
 * @date 2017-12-22 15:24
 **/
var app = angular.module('app', ['ngRoute']);
app.config(['$routeProvider', function ($routeProvider) {
    for (var i in pages) {
        var page = pages[i];
        $routeProvider.when(page.path, {
            templateUrl: page.url,
            controller: 'PageController'
        });
    }
    // $routeProvider.when("404", {
    //     template: "<h1>404</h1>"
    // });
    $routeProvider.otherwise({redirectTo: pages[0].path});
}]);

app.controller('AppController', function ($scope, $http) {
    $scope.$watch('$viewContentLoaded', function () {
        document.getElementsByTagName("body")[0].style.display = "block";
    });
});

var pageScope, pageHttp, options;
app.controller('PageController', function ($scope, $http, $routeParams) {
    pageScope = $scope;
    pageHttp = $http;
    options = $routeParams;
    app.ready && app.ready();
});

var Page = function (parameters) {
    var page = this;
    page.httpStatusCode = {
        OK: 200,
        BAD_REQUEST: 400,
        NOT_FOUND: 404,
        UNAUTHORIZED: 401,
        SERVER_ERROR: 500,
        FORBIDDEN: 403
    };

    for (var key in parameters) {
        this[key] = parameters[key];
    }
    page.$http = pageHttp;
    page.$scope = pageScope;
    page.options = options;
    pageScope.ctx = "/NstWebProject";

    pageScope.data = page.data;
    for (var name in page.data) {
        pageScope[name] = page.data[name];
    }

    pageScope.$watch('$viewContentLoaded', function () {
        page.onShow && typeof page.onShow === "function" && page.onShow();
        var pageDoc = document.getElementById("page")
        pageDoc && (pageDoc.style.display = "block");
    });

    for (var key in page.controllers) {
        pageScope[key] = page.controllers[key];
    }

    pageScope.isEmpty = function (parameter) {
        switch (typeof parameter) {
            case "object":
                var name;
                for (name in parameter) {
                    return false;
                }
                break;
            case "string":
                if (parameter || parameter.replace(/(^s*)|(s*$)/g, "").length > 0) {
                    return false;
                }
                break;
        }
        return true;
    };

    page.request = function () { // url参数
        var data, index;
        (function init() {
            data = [];
            index = {};
            var u = window.location.search.substr(1);
            if (u) {
                var parms = decodeURIComponent(u).split('&');
                for (var i = 0, len = parms.length; i < len; i++) {
                    if (parms[i]) {
                        var p = parms[i].split("=");
                        if (p.length === 1 || (p.length === 2 && p[1] === '')) {// p | p=
                            data.push(['']);
                            index[p[0]] = data.length - 1;
                        } else if (typeof(p[0]) === 'undefined' || p[0] === '') { // =c | =
                            data[0] = [p[1]];
                        } else if (typeof(index[p[0]]) === 'undefined') { // c=aaa
                            data.push([p[1]]);
                            index[p[0]] = data.length - 1;
                        } else {// c=aaa
                            data[index[p[0]]].push(p[1]);
                        }
                    }
                }
            }
        })();
        return {
            getParameter: function (nameOrIndex) { // 参数名或者参数次序
                try {
                    return (typeof(nameOrIndex) === 'number' ? data[nameOrIndex][0] : data[index[nameOrIndex]][0]);
                } catch (e) {
                }
            },
            getParameterValues: function (nameOrIndex) { // 参数名或者参数次序
                try {
                    return (typeof(nameOrIndex) === 'number' ? data[nameOrIndex] : data[index[nameOrIndex]]);
                } catch (e) {
                }
            },
            //是否含有parmName参数
            hasParm: function (parmName) {
                return typeof(parmName) === 'string' ? typeof(index[parmName]) !== 'undefined' : false;
            },
            getParameterMap: function () {
                var map = {};
                try {
                    for (var p in index) {
                        map[p] = data[index[p]];
                    }
                } catch (e) {
                }
                return map;
            }
        }
    }();

    page.setData = function (parameter, apply) {
        if (typeof parameter !== "object") {
            throw new TypeError("期望获得参数类型: object, 实际获得参数类型: " + typeof parameter);
        }
        for (var key in parameter) {
            page.data[key] = parameter[key];
        }
        pageScope && (pageScope.data = page.data);
        for (var name in page.data) {
            pageScope && (pageScope[name] = page.data[name]);
        }
        apply === true && pageScope && pageScope.$apply();
    };

    page.ajax = function (parameters) {
        if (!parameters) {
            throw TypeError("发起网络请求需要传参!");
        }
        if (typeof parameters !== "object") {
            throw TypeError("期望得到参数类型: object, 实际得到参数类型: " + typeof parameters);
        }
        var url = parameters.url,
            data = parameters.data,
            params = parameters.params,
            method = parameters.method || parameters.type || "GET",
            headers = parameters.headers,
            success = parameters.success,
            fail = parameters.fail || parameters.error;
        if (!url) {
            throw TypeError("你没有传url, url为必填参数!");
        }
        page.beforeAjax && typeof page.beforeAjax === "function" && page.beforeAjax();

        var config = {
            method: method,
            url: url
        };
        if (data) config.data = data;
        if (params) config.params = params;
        if (headers) config.headers = headers;

        page.$http(config).then(function successCallback(response) {
            var filterChain = {
                index: 0,
                filters: page.successFilters,
                doFilter: function () {
                    if (this.index >= filterChain.filters.length) {
                        typeof success === "function" && success(response.data);
                        return;
                    }
                    this.filters[this.index++].doFilter(response, filterChain)
                }
            };
            filterChain.doFilter();
        }, function errorCallback(response) {
            var filterChain = {
                index: 0,
                filters: page.failFilters,
                doFilter: function () {
                    if (this.index >= filterChain.filters.length) {
                        typeof fail === "function" && fail(response);
                        return;
                    }
                    this.filters[this.index++].doFilter(response, filterChain)
                }
            };
            filterChain.doFilter();
        });
    };

    return page;
};

Page.prototype.successFilters = [];

Page.prototype.addSuccessFilter = function (filters) {
    for (var i in filters) {
        Page.prototype.successFilters.push(filters[i]);
    }
};

var addSuccessFilter = function (filters) {
    for (var i in filters) {
        Page.prototype.successFilters.push(filters[i]);
    }
};

Page.prototype.failFilters = [];

Page.prototype.addFailFilter = function (filters) {
    for (var i in filters) {
        Page.prototype.failFilters.push(filters[i]);
    }
};

var addFailFilter = function (filters) {
    for (var i in filters) {
        Page.prototype.failFilters.push(filters[i]);
    }
};