<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp"%>
<main>
	<div class="inner">
		<h2 class="subTitle">VIEW</h2>
		<div id="contents">
			<!-- 데이터 받아서 처리하기....forward방식으로 데이터 보여주기....  -->
			<div class="form">
				<table>
					<colgroup>
						<col style="width: 10%">
						<col style="width: 40%">
						<col style="width: 10%">
						<col style="width: 40%">
					</colgroup>
					<tbody>
						<tr>
							<th>NAME</th>
							<td class="left">${replyBoardDto.name }</td>
							<th>HIT</th>
							<td class="left">${replyBoardDto.hit }</td>
						</tr>
						<tr>
							<th>EMAIL</th>
							<td class="left">${replyBoardDto.email }</td>
							<th>DATE</th>
							<td class="left">${replyBoardDto.regDate }</td>

						</tr>
						<tr>
							<th>SUBJECT</th>
							<td colspan="3" class="left">${replyBoardDto.subject }</td>
						</tr>
						<tr>
							<th>CONTENTS</th>
							<td colspan="3" class="left">${replyBoardDto.contents }</td>
						</tr>
					</tbody>
				</table>
				<!-- 다음 글 이전 글 보기 -->
				<div class="prevAndNext">
					<div class="item">
						<span class="inner"><span class="material-icons">keyboard_arrow_up</span>PREV</span>
						<c:if test="${empty prevBoardDto }">
							<a href="">이전글 없음</a>
						</c:if>
						<a
							href="../board/View.do?no=${prevBoardDto.no }&num=${prevBoardDto.num}">${prevBoardDto.subject }</a>
					</div>
					<div class="item">
						<span class="inner"> <span class="material-icons">keyboard_arrow_down</span>NEXT
						</span>
						<c:if test="${empty nextBoardDto  }">
							<a href="">다음글 없음</a>
						</c:if>
						<a
							href="../board/View.do?no=${nextBoardDto.no }&num=${nextBoardDto.num}">${nextBoardDto.subject }</a>
					</div>
				</div>

				<!-- 삭제 수정이 내가 쓴 글에만 보이게... -->

				<div class="btns">
					<a href="../board/List.do" class="btn btnConfirm">글목록</a> <a
						href="../board/Write.do" class="btn btnConfirm">글쓰기</a> <a
						href="../board/ReplyWrite.do?no=${replyBoardDto.no }
					&reGroup=${replyBoardDto.reGroup }
					&reStep=${replyBoardDto.reStep }
					&reLevel=${replyBoardDto.reLevel }"
						class="btn btnConfirm">답글쓰기</a>
					<c:if test="${replyBoardDto.id == loggedId }">
						<a href="../board/Delete.do?no=${param.no }" class="btn btnConfirm">삭제</a>
						<a href="../board/Update.do?no=${param.no }" class="btn btnConfirm">수정</a>
					</c:if>

				</div>
			</div>
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

	// form을 통해서 보낼 수 있다. 
	// <a href="join_process.jsp?user_id=hulk">보내기</a>
	// ajax로 데이터 보낼 수 있다.

	$(".btnIdCheck").on("click", function(e) {
		//e.preventDefault();
		const user_id = $("#user_id").val();
		if (user_id === "") {
			alert("아이디를 입력해 주세요.");
		}
		$.ajax({
			url : "id_check02.jsp",
			type : "POST",
			data : {
				"user_id" : user_id
			},
			success : function(res) {
				console.log(res);
				if (res.count > 0) {
					alert("사용할 수 없는 아이디입니다.");
					$("#user_id").val("");
					$("#user_id").focus();
				} else {
					//alert("사용할 수 있는 아이디입니다.");
					const yes = confirm("사용가능한 아이디입니다. 사용하시겠어요");
					if (yes) {
						$("#user_id").attr("readonly", true);
						$("#user_id").addClass("readonly");
						idCheck = true;
					} else {
						$("#user_id").val("");
					}
				}
			}
		});
		return false;
	});

	/*
	$("#btnIdCheck").on("click",function(e){
		e.preventDefault();
		const user_id = $("#user_id").val();
		$.ajax({
			url:"id_check.jsp",
			type:"POST",
			data:{"user_id":user_id},
			success:function(res){
				console.log(res.count);
				if(res.count) {
					alert("사용할 수 없습니다.")
				}else {
					alert("사용할 수 있습니다.");
				}
			}
		});
		return false;
	});
	 */
	/*
	$(".btnConfirm").on("click",function(){
		if($("#user_id").val()=="") {
			alert("ID를 입력해 주세요");
			$("#user_id").focus();
			return false;
		} else if(korean.test($("#user_id").val())){
			alert("id에 한글을 쓸 수 없습니다.");
			$("#user_id").val("");
			$("#user_id").focus();
			return false;
		} else if($("#user_pw").val()=="") {
			alert("password를 입렫해 주세요");
			$("#user_pw").focus();
			return false;
		} else if($("#user_pw_confirm").val()=="") {
			alert("password 확인을 입렫해 주세요");
			$("#user_pw_confirm").focus();
			return false;
		} else if($("#user_pw").val()!==$("#user_pw_confirm").val()) {
			alert("패스워드가 맞지 않습니다.");
			$("#user_pw_confirm").focus();
			$("#user_pw_confirm").val("");
			return false;
		} else if($("#user_name").val()=="") {
			alert("name을 입렫해 주세요");
			$("#user_name").focus();
			return false;
		} else if($("#user_email").val()=="") {
			alert("email을 입력해 주세요");
			$("#user_email").focus();
			return false;
		} else if(!email.test($("#user_email").val())) {
			alert("이메일을 형식에 맞게 입력해 주세요.");
			$("#user_email").focus();
			return false;
		} else if($("#user_phone_middle").val()==="") {
			alert("전화번호를 입력하세요.");
			$("#user_phone_middle").focus();
			return false;
		} else if($("#user_phone_last").val()==="") {
			alert("전화번호를 입력하세요.");
			$("#user_phone_last").focus();
			return false;
		} else if($("#zipcode").val()=="") {
			alert("우편번호를 입렫해 주세요");
			$("#user_zipcode").focus();
			return false;
		} else if($("#user_address01").val()=="") {
			alert("주소를 입렫해 주세요");
			$("#user_address01").focus();
			return false;
		} else if(!idCheck) {
			alert("아이디 체크를 해주세요");
			return false;
		}
	});
	 */
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






