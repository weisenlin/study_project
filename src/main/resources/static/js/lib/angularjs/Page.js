/**
 * <p>
 * Description: mvc开发模式插件(数据页面双向绑定, 更新数据即更新页面)
 * </p>
 *  快速使用eq:
 *  1. 导入jquery.js/angularjs/以及本插件
 *  2. 在页面body标签内写入: style="display: none;" ng-app="Page" ng-controller="PageController"
 *  3. var page = Page({
            data: {
                // 页面双向绑定数据, 使用插件提供的page.setData({})方法set进去的数据可直接在页面使用
            },
            onShow: function() {
                // 页面初始化会插件会调用该函数, 建议将一些初始化代码放在该函数里
            },
            controllers: {
                // angularjs里的controller, 只要符合angularjs语法, 可以直接在页面使用
            }
        });
    注意:
        1. 数据更新变化建议用page.setData({}), 而不是直接操作data
        2. 关于异步请求数据, 插件支持使用jquery的异步, 但是在setData的时候要加入apply参数标识需要主动刷新页面数据, 即
            setData({
               order: order
            }, true);
            所以, 插件建议使用插件提供的page.ajax({}), 用法和jquery的ajax一样, 使用该方法正常setData即可

    utils: 关于页面三元运算符判空, 插件提供isEmpty()函数, 可判断对象或字符串是否为空
            eg: <img ng-src="{{isEmpty(spu.surface) ? '/img/default-goods.png' : spu.surface.url}}">
 * description:
 *
 * @author baolin
 * @date 2017-12-22 15:24
 **/
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
    var module = parameters.modules || [];
    var app = angular.module('Page', module);
    var scope;
    app.controller('PageController', function ($scope, $http) {
        page.$http = $http;
        scope = $scope;
        $scope.data = page.data;
        for (var name in page.data) {
            $scope[name] = page.data[name];
        }

        $scope.$watch('$viewContentLoaded', function () {
            page.onShow && typeof page.onShow === "function" && page.onShow();
            document.getElementsByTagName("body")[0].style.display = "block";
        });

        for (var key in page.controllers) {
            $scope[key] = page.controllers[key];
        }

        $scope.isEmpty = function (parameter) {
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
        page.scope = $scope;
    });

    page.app = app;

    app.filter("dateFormat", function($filter) {
        return function(input, format) {
            input = input + "";
            //从字符串 /Date(1448864369815)/ 得到时间戳 1448864369815
            var timestamp = Number(input.replace(/\/Date\((\d+)\)\//, "$1"));

            //转成指定格式
            return $filter("date")(timestamp, format);
        };
    });

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
        scope && (scope.data = page.data);
        for (var name in page.data) {
            scope && (scope[name] = page.data[name]);
        }
        apply === true && scope && scope.$apply();
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
                doFilter: function() {
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
                doFilter: function() {
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






