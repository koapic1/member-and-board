<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"  %>
    <main>
      <div class="inner">
	      <h2 class="subTitle">DELETE</h2>
		  <div id="contents">
		  	<!-- 데이터 받아서 처리하기....forward방식으로 데이터 보여주기....  -->
		  	<form method="POST" action="../member/DeleteProcess.do" id="join" class="form">
		  		<input type="hidden" name="id" value="${param.id }">
		  		<table style="width:500px; margin:auto">
		  			<tbody>
		  				<tr>
		  					<th>PASSWORD</th>
		  					<td><input type="password" name="password" placeholder="password"></td>		
		  				</tr>
		  			</tbody>
		  		</table>
		  		<div class="btns">
		  			<button type="submit" class="btn btnConfirm">회원탈퇴</button>
		  		</div>
		  	</form>
		  </div>      	
	  </div>
    </main>
    <script>
    
<%@ include file="../include/footer.jsp"  %>






