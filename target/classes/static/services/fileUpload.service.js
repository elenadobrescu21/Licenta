angular.module("app").service('fileUpload', ['$http', function($http) {
	this.uploadFileToUrl = function(file, uploadUrl) {
	var fd = new FormData();
	fd.append('file', file);
	$http.post(uploadUrl, fd, {
		transformRequest: angular.identity,
		headers: {'Content-Type': undefined}
	})
	.success(function(){
		console.log("File uploaded successfully");
	})
	.error(function(){
		console.log("Couldn't upload file")
	});
}
}]);