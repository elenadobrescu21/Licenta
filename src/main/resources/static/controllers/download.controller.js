'use strict';
angular.module('app')
    .controller('DownloadController', function ($scope, ServicePDF) {
        $scope.downloadPdf = function () {
            var fileName = "Date_examene_IS.pdf";
            var a = document.createElement("a");
            document.body.appendChild(a);
            a.style = "display: none";
            ServicePDF.downloadPdf().then(function (result) {
                var file = new Blob([result.data], {type: 'application/pdf'});
                var fileURL = window.URL.createObjectURL(file);
                a.href = fileURL;
                a.download = fileName;
                a.click();
            });
        };
})