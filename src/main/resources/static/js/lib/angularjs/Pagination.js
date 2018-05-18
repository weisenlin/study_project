/**
 * description:
 *
 * @author baolin
 * @date 2017-12-20 15:09
 **/
app.directive('nstPagination', ['$http', function ($http) {
    return {
        restrict: 'EA',
        template: '<footer class="panel-footer">' +
        '   <div class="row">' +
        '       <div class="col-sm-11 text-center text-center-xs">' +
        '           显示' +
        '           <select ng-change="toPage(1, pagination.size, true)" ng-model="pagination.size">' +
        '               <option ng-repeat="size in pagination.sizes">{{size}}</option>' +
        '           </select>' +
        '           条' +
        '           &nbsp;&nbsp;&nbsp;&nbsp;<small class="text-muted inline m-t-sm m-b-sm">共 {{pagination.totalRecord}} 条  / 第 {{pagination.currentPage}} 页  / 共 {{pagination.totalPage}} 页</small>&nbsp;&nbsp;&nbsp;&nbsp;' +
        '           <ul class="pagination pagination-sm m-t-none m-b-none">' +

        '               <li class="{{pagination.currentPage <= 1 ? \'disabled\' : \'\'}}">' +
        '                   <a ng-click="toPage(1, pagination.size)" href="javascript:;" aria-label="Previous">' +
        '                       <span aria-hidden="true">«</span>' +
        '                       <span class="sr-only">Previous</span>' +
        '                   </a>' +
        '               </li>' +
        '               <li class="{{pagination.currentPage <= 1 ? \'disabled\' : \'\'}}">' +
        '                    <a ng-click="toPage(pagination.currentPage - 1, pagination.size)" href="javascript:;" aria-label="Previous">' +
        '                       <span aria-hidden="true">«</span>' +
        '                       <span class="sr-only">Previous</span>' +
        '                   </a>' +
        '               </li>' +
        '               <li class="{{pagination.currentPage == page ? \'active\' : \'\'}}" ng-repeat="page in pagination.pageBar">' +
        '                   <a ng-click="toPage(page, pagination.size)" href="#">{{page}}</a>' +
        '               </li>' +
        '               <li class="{{pagination.currentPage >= pagination.totalPage ? \'disabled\' : \'\'}}">' +
        '                   <a ng-click="toPage(pagination.currentPage + 1, pagination.size)" href="#" aria-label="Next">' +
        '                       <span aria-hidden="true">»</span>' +
        '                       <span class="sr-only">Next</span>' +
        '                   </a>' +
        '               </li>' +
        '               <li class="{{pagination.currentPage >= pagination.totalPage ? \'disabled\' : \'\'}}">' +
        '                   <a ng-click="toPage(pagination.totalPage, pagination.size)" href="#" aria-label="Next">' +
        '                       <span aria-hidden="true">»</span>' +
        '                       <span class="sr-only">Next</span>' +
        '                   </a>' +
        '               </li>' +
        '               <select ng-change="toPage(pagination.currentPage, pagination.size, true)" ng-model="pagination.currentPage">' +
        '                   <option ng-repeat="page in pagination.pages">{{page}}</option>' +
        '               </select>' +
        '           </ul>' +
        '       </div>' +
        '   </div>' +
        '</footer>',
        replace: true,
        scope: false,
        link: function (scope) {
            scope.toPage = toPage;
            scope.pageSearch = function (json) {
                if (json) {
                    var params = scope.pageConfig.params || {};
                    for (var key in json) {
                        params[key] = json[key];
                    }
                }
                toPage(1, 20, true);
            };
            toPage(1, 20, true);
            function toPage(page, size, force) {
                var pagination = scope.pagination;
                if (pagination && !force) {
                    // 当前页不管, page小于等于0不管， page大于总页数不管
                    if (pagination.currentPage === page || page <= 0 || page > pagination.totalPage) return;
                }
                var config = scope.pageConfig;
                var url = config.url;
                var params = config.params || {};
                params["page"] = page;
                params["size"] = size;
                $http({
                    method: 'GET',
                    url: url,
                    params: params
                }).then(function successCallback(response) {
                    var pagination = response.data.pagination;
                    var pages = [];
                    for (var i = 1; i <= pagination.totalPage; i++) {
                        pages.push(i);
                    }
                    if (!pagination.sizes.contains(size)) {
                        for (var i in pagination.sizes) {
                            if (pagination.sizes[i] >= size) {
                                pagination.sizes.insert(i, size);
                                break;
                            }
                        }
                    }
                    pagination.pages = pages;
                    scope.pagination = pagination;
                }, function errorCallback(response) {
                    // 请求失败执行代码
                });
            }
        }
    };
}]);