var photo={
	UploadPicDropzone:'',
	doit:function(url){
		$('#photosubmit').dialog("open");
		$('#photosubmit').dialog("center");
		if (typeof(this.UploadPicDropzone) == "string"){
			this.UploadPicDropzone = new Dropzone("form#upload-pic-dropzone",
				{
					url:url,
					method: "post",
					paramName: "file", // The name that will be used to transfer the file
					maxFilesize: 0.5, // MB
					dictDefaultMessage: '将文件拖拽至此区域进行上传（或点击此区域）',
					dictInvalidFileType: '文件类型不支持',
					dictFileTooBig: '文件过大，最大大小{{maxFilesize}}',
					dictResponseError: '{{statusCode}}:上传失败，请重试',
					acceptedFiles: 'image/jpeg',
					previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n    <div class=\"dz-filename\" style=\"display: none;\"><span data-dz-name></span></div>\n    <div class=\"dz-size\" style=\"display: none;\" data-dz-size></div>\n    <img data-dz-thumbnail />\n  </div>\n  <div class=\"dz-progress\"><span class=\"dz-upload\" data-dz-uploadprogress></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>",
				}
			);
		}
		if (typeof(this.UploadPicDropzone) != "string") {
			this.UploadPicDropzone.options.url = url;
			$('#upload-pic-dropzone-message').html('选择图片上传');
			this.UploadPicDropzone.on("sending", function(file) {
				$('#upload-pic-dropzone-message').html('上传中……');
				this.removeAllFiles();
			});
			this.UploadPicDropzone.on("error", function(file, message ,xhr) {
				if (! typeof(xhr) == "undefined")
					$('#upload-pic-dropzone-message').html(xhr.response);
				else
					$('#upload-pic-dropzone-message').html("图片大小不能超过0.5M,文件格式为jpg");
				this.removeAllFiles();
			});
			this.UploadPicDropzone.on("success", function(file, message ,xhr) {
				$('#upload-pic-dropzone-message').html('上传成功');
				this.removeAllFiles();
			});
		}
		// $('#photosubmit').dialog("close");
	}
}