<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
<%@ page import="com.model2.mvc.service.domain.Purchase" %>
<% Purchase vo = (Purchase)request.getAttribute("vo"); %>
--%>
<html>
<head>
		<title>구매정보</title>
	</head>
<h2>다음과 같이 구매가 되었습니다.</h2>
	<body>

		<!-- Attribute border : 표 테두리 두께지정 -->
		<table border="1">
			<!-- tr : Table Row -->
			<tr>
				<td>물품번호</td>
				<td>${ purchase.purchaseProd.prodNo }
			</tr>

			<tr>
				<td>구매자아이디</td>
				<td>${ purchase.buyer.userId }</td>
			</tr>

			<!-- Attribute align : 확인요.. -->
			<tr>
				<td>구매방법</td>
				<td><c:if test="${ purchase.paymentOption.equals('1')}">
					현금구매</c:if>
					<c:if test="${ purchase.paymentOption.equals('2')}">
					신용구매</c:if>
				</td>
			</tr>

			<!-- Attribute align : 확인요.. -->
			<tr>
				<td>구매자이름</td>
				<td>${ purchase.receiverName }</td>
			</tr>
				<td>구매자연락처</td>
				<td>${ purchase.receiverPhone }</td>
			<tr>
				<td>구매자주소</td>
				<td>${ purchase.divyAddr }
			</tr>

			<tr>
				<td>구매요청사항</td>
				<td>${ purchase.divyRequest }</td>
			</tr>
			
			<tr>
				<td>배송희망일자</td>
				<td>${ purchase.divyDate }</td>
			</tr>

		</table>
	</body>
</html>