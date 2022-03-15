<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<main>
	<div class="inner">
		<h2 class="subTitle">MEMBER INFO</h2>
		<div id="contents">
			<div class="tableBox">
				<table class="left">
					<colgroup>
						<col style="width: 150px">
						<col>
					</colgroup>
					<tbody>
					<tr>
						<th>PROFILE</th>
							<td><img src="${memberDto.thumb }" class="profile02"></td>
						</tr>
						<tr>
							<th>ID</th>
							<td>${memberDto.id }</td>
						</tr>
						<tr>
							<th>NAME</th>
							<td>${memberDto.name }</td>
						</tr>
						<tr>
							<th>E-MAIL</th>
							<td>${memberDto.email }</td>
						</tr>
						<tr>
							<th>PHONE</th>
							<td>${memberDto.phone }</td>
						</tr>
						<tr>
							<th>ZIP-CODE</th>
							<td>${memberDto.zipCode }</td>
						</tr>
						<tr>
							<th>ADDRESS</th>
							<td>${memberDto.address }</td>
						</tr>
					</tbody>
				</table>
				<div class="btns">
					<a href="../member/Update.do?id=${loggedId }&name=${loggedName }" class="btn btnConfirm">회원정보 수정</a>
					<a href="../member/Delete.do?id=${loggedId }" class="btn btnCancel">회원탈퇴</a>
				</div>
			</div>
		</div>
	</div>
</main>

<%@ include file="../include/footer.jsp"%>

