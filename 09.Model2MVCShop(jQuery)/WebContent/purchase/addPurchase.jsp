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
		<title>��������</title>
	</head>
<h2>������ ���� ���Ű� �Ǿ����ϴ�.</h2>
	<body>

		<!-- Attribute border : ǥ �׵θ� �β����� -->
		<table border="1">
			<!-- tr : Table Row -->
			<tr>
				<td>��ǰ��ȣ</td>
				<td>${ purchase.purchaseProd.prodNo }
			</tr>

			<tr>
				<td>�����ھ��̵�</td>
				<td>${ purchase.buyer.userId }</td>
			</tr>

			<!-- Attribute align : Ȯ�ο�.. -->
			<tr>
				<td>���Ź��</td>
				<td><c:if test="${ purchase.paymentOption.equals('1')}">
					���ݱ���</c:if>
					<c:if test="${ purchase.paymentOption.equals('2')}">
					�ſ뱸��</c:if>
				</td>
			</tr>

			<!-- Attribute align : Ȯ�ο�.. -->
			<tr>
				<td>�������̸�</td>
				<td>${ purchase.receiverName }</td>
			</tr>
				<td>�����ڿ���ó</td>
				<td>${ purchase.receiverPhone }</td>
			<tr>
				<td>�������ּ�</td>
				<td>${ purchase.divyAddr }
			</tr>

			<tr>
				<td>���ſ�û����</td>
				<td>${ purchase.divyRequest }</td>
			</tr>
			
			<tr>
				<td>����������</td>
				<td>${ purchase.divyDate }</td>
			</tr>

		</table>
	</body>
</html>