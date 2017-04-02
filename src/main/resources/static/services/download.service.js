angular.module('app')
    .factory('ServicePDF', function ($http) {
        return {
            downloadPdf: function (id) {
            return $http.get('/downloadPDF/'+id, { responseType: 'arraybuffer' }).then(function (response) {
                return response;
            });
        }
    };
});