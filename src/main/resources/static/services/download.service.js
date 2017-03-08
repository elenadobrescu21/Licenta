angular.module('app')
    .factory('ServicePDF', function ($http) {
        return {
            downloadPdf: function () {
            return $http.get('/downloadPDF', { responseType: 'arraybuffer' }).then(function (response) {
                return response;
            });
        }
    };
});