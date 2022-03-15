<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<main>
	<div class="inner">
		<h2 class="subTitle">Modify</h2>
		<div id="contents">
			<!-- 데이터 받아서 처리하기....forward방식으로 데이터 보여주기....  -->
			<form method="POST" action="WriteProcess.do" id="join" class="form">
				<table class="left">
					<colgroup>
						<col style="width: 20%">
						<col style="width: 80%">
					</colgroup>
					<tbody>
						<tr>
							<th>name <span class="required">*</span></th>
							<td><input type="text" name="name" id="user_name"
								placeholder="이름를 입력하세요." readonly value="${loggedName }" class="readonly"></td>
						</tr>
						<tr>
							<th>subject <span class="required">*</span></th>
							<td><input type="text" name="subject" id="user_subject"
								placeholder="제목을 입력하세요."></td>
						</tr>

						<tr>
							<th>email <span class="required">*</span></th>
							<td><input type="text" name="email" id="user_email"
								placeholder="메일을 입력하세요." readonly value="${loggedMember.email }" class="readonly"></td>
						</tr>
						<tr>
							<th>패스워드 <span class="required">*</span></th>
							<td><input type="password" name="password" id="user_pw"
								placeholder="비밀번호를 입력하세요."></td>
						</tr>
						<tr>
							<th>contents<span class="required">*</span></th>
							<td class="left"><textarea placeholder="내용을 입력하세요." name="contents"
									id="summernote"></textarea></td>
						</tr>
					</tbody>
				</table>
				<div class="btns">
					<button type="submit" class="btn btnConfirm">글쓰기</button>
					<button type="reset" class="btn btnCancel">취소</button>
				</div>
			</form>
		</div>
	</div>
</main>
<script>
	let idCheck = false;
	const korean = /[ㄱ-ㅎㅏ-ㅣ가-힣]/g;
	const email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;//이메일 정규식
	$(".btnZipcode").on("click", function() {
		postCode();
		return false;
	});

	
	$(".btnConfirm").on("click",function(){
		if($("#user_subject").val()=="") {
			alert("제목을 입력해 주세요");
			$("#user_subject").focus();
			return false;
		}  else if($("#user_pw").val()=="") {
			alert("password를 입렫해 주세요");
			$("#user_pw").focus();
			return false;
		} 
	});
	

	//summernote 적용
	$("#summernote").summernote({
		height : 300,
		callbacks : {
			onImageUpload : function(files) {
				for(let i=0;i<files.length;i++){
					uploadImage(files[i],this);
				}
			}
		}
	});
	//queryString  ===>       view.do?img=aaa&text=bbb   (get)
	// form 태그를 이용하는 방법   <form method="GET 또는 POST" action="넘길 페이지"></form>
	// FormData
	function uploadImage(file,editor) {
		const sendData = new FormData();
		sendData.append("summerNoteImage",file);  //uploadFile   <input type="file" name="uploadFile">
		$.ajax({
			url:"SummerNoteFileUpload.do",
			type:"POST",
			data:sendData,
			contentType:false, // 기본값은 application/x-www-form-urlencode임 false로 해야지만 multipart/form-data로 날아간다. 
			processData:false, // QueryString형태로 전달되는걸 막음... default는 true  
			dataType:"json",
			success:function(res) {
				console.log(res);
				$(editor).summernote("editor.insertImage",res.url);
			},
			error:function(){
				alert("파일 업로드 실패");
			}
		})
	}
</script>
<%@ include file="../include/footer.jsp"%>






