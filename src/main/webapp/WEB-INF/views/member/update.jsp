<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<main>
	<div class="inner">
		<h2 class="subTitle">회원정보 수정</h2>
		<div id="contents">
			<!-- 데이터 받아서 처리하기....forward방식으로 데이터 보여주기....  -->
			<form method="POST" action="../member/UpdateProcess.do" id="join" class="form" enctype="multipart/form-data">
				<table class="left">
					<colgroup>
						<col style="width: 20%">
						<col style="width: 80%">
					</colgroup>
					<tbody>
						<tr>
							<th>프로필 <span class="required">*</span></th>
							<td>
								<div>
									<img src="${profile}" class="profile02">
								</div>
								<div class="fileBox" style="margin-top:20px;">
									<input type="text" class="fakeFile"	placeholder="업로드할 파일을 선택해 주세요."> 
									<label><input type="file" name="multipartFile"><span>파일찾기</span></label>
								</div>
							</td>
						</tr>
						<tr>
							<th>아이디 <span class="required">*</span></th>
							<td><input type="text" name="id" id="user_id"
								value="${loggedId }" readonly class="readonly"></td>
						</tr>
						<tr>
							<th>패스워드 <span class="required">*</span></th>
							<td><input type="password" name="password" id="user_pw"
								placeholder="비밀번호를 입력하세요."></td>
						</tr>
						<tr>
							<th>이름 <span class="required">*</span></th>
							<td><input type="text" name="name" id="user_name"
								value="${loggedName }" placeholder="이름을 입력하세요."></td>
						</tr>
						<tr>
							<th>이메일 <span class="required">*</span></th>
							<td><input type="text" name="email" id="user_email"
								placeholder="메일을 입력하세요."></td>
						</tr>
						<tr>
							<th>전화번호 <span class="required">*</span></th>
							<td class="left"><select name="phoneFirst" class="short">
									<option value="010" selected>010</option>
									<option value="011">011</option>
									<option value="017">017</option>
							</select> <input type="text" name="phoneMiddle" class="short"
								id="user_phone_middle" placeholder="전화번호를 입력하세요."> <input
								type="text" name="phoneLast" class="short" id="user_phone_last"
								placeholder="전화번호를 입력하세요."></td>
						</tr>
						<tr>
							<th>주소 <span class="required">*</span></th>
							<td class="left">
								<div>
									<input type="number" name="zipCode" placeholder="우편번호"
										class="short" id="zipcode" value="${loggedMember.zipCode }">
									<button class="btn btnZipcode">우편번호</button>
								</div>
								<div class="addrBox">
									<input type="text" name="address01" placeholder="기본주소"
										id="address01" value="${loggedMember.address }"> <span
										class="sticker">기본 주소</span>
								</div>
								<div class="addrBox">
									<input type="text" name="address02" placeholder="나머지 주소"
										id="address02"> <span class="sticker">나머지 주소</span>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="btns">
					<button type="submit" class="btn btnConfirm">수정</button>
					<button type="reset" class="btn btnCancel">취소</button>
				</div>
			</form>
		</div>
	</div>
</main>
<script>
	$(".btnZipcode").on("click", function() {
		postCode();
		return false;
	});

	$(".btnConfirm").on("click", function() {
		if ($("#user_pw").val() === "") {
			alert("비밀번호를 입력하세요.");
			$("#user_pw").focus();
			return false;
		} else if ($("#user_name").val() === "") {
			alert("이름을 입력하세요.");
			$("#user_name").focus();
			return false;
		} else if ($("#user_email").val() === "") {
			alert("이메일을 입력하세요.");
			$("#user_email").focus();
			return false;
		} else if ($("#user_phone_middle").val() === "") {
			alert("전화번호를 입력하세요.");
			$("#user_phone_middle").focus();
			return false;
		} else if ($("#user_phone_last").val() === "") {
			alert("전화번호를 입력하세요.");
			$("#user_phone_last").focus();
			return false;
		} else if ($("#zipcode").val() === "") {
			alert("우편번호를 입력하세요.");
			$("#zipcode").focus();
			return false;
		} else if ($("#address01").val() === "") {
			alert("주소를 입력하세요.");
			$("#address01").focus();
			return false;
		}
	});

	function postCode() {
		new daum.Postcode({
			oncomplete : function(data) {
				// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

				// 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
				// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
				let roadAddr = data.roadAddress; // 도로명 주소 변수
				let extraRoadAddr = ''; // 참고 항목 변수

				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraRoadAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraRoadAddr += (extraRoadAddr !== '' ? ', '
							+ data.buildingName : data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraRoadAddr !== '') {
					extraRoadAddr = ' (' + extraRoadAddr + ')';
				}

				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				document.getElementById("zipcode").value = data.zonecode;
				document.getElementById("address01").value = roadAddr;
			}
		}).open();
	}
</script>

<%@ include file="../include/footer.jsp"%>






