$(".fileBox input[type='file']").on("change", function() {
	const fileName = $(this).val(); // file에 있는 내용을 복사해서
	console.log(fileName);
	$(this).parent().siblings(".fakeFile").val(fileName);
});