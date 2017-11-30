<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.Product" %>
<%@ page import="com.model2.mvc.common.Search" %>
<%@ page import="com.model2.mvc.common.Page"%>
<%@ page import="com.model2.mvc.common.util.CommonUtil"%> 
--%>

<%--
	String menu = (String)request.getAttribute("menu");
	List<Product> list= (List<Product>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");
	
	Search search = (Search)request.getAttribute("search");
	//==> null �� ""(nullString)���� ����
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
--%>

<html>
<head>
<title>��ǰ �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetUserList(currentPage, sorting){
	document.getElementById("currentPage").value = currentPage;
	document.getElementById("sorting").value = sorting;
	document.detailForm.submit();
}


</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/listProduct?menu=${ menu }" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01"> <c:if test= "${ menu.equals('manage') }"> ��ǰ ����</c:if>
					<c:if test= "${ menu.equals('search') }"> ��ǰ �����ȸ</c:if></td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>
	<input type="hidden" id="sorting" name="sorting" value=""/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
			<%-- /////////////////////// EL / JSTL �������� �ּ� ó�� ////////////////////////
				<option value="0" <%= (searchCondition.equals("0") ? "selected" : "")%>>ȸ��ID</option>
				<option value="1" <%= (searchCondition.equals("1") ? "selected" : "")%>>ȸ����</option>
				/////////////////////// EL / JSTL �������� �ּ� ó�� //////////////////////// --%>
				<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>��ǰ��ȣ</option>
				<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>��ǰ��</option>
				<option value="2"  ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>��ǰ����</option>
			</select>
			<%--<input type="text" name="searchKeyword" value="<%= searchKeyword %>"  class="ct_input_g" style="width:200px; height:14px" >--%>
			<input type="text" name="searchKeyword" 
						value="${! empty search.searchKeyword ? search.searchKeyword : ""}"  
						class="ct_input_g" style="width:200px; height:20px" > 
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="/images/ct_btnbg01.gif" width="17" height="23"></td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetUserList('1',null);">�˻�</a>
					</td>
					<td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >��ü  ${resultPage.totalCount} �Ǽ�, ���� ${ resultPage.currentPage } ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��<a href="javascript:fncGetUserList('1','prod_name');">
			<img src="/images/arrow.gif"></a></td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����<a href="javascript:fncGetUserList('1','price');">
			<img src="/images/arrow.gif"></a></td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��ǰ��ȣ<a href="javascript:fncGetUserList('1','prod_no');">
			<img src="/images/arrow.gif"></a></td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">����</td>
		<td class="ct_line02"></td>
			
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i+( pageSize )*(resultPage.currentPage-1) }</td>
			<td></td>
			
			<td align="left"><c:if test="${ empty product.proTranCode }"><c:if test="${ menu.equals('search') }"><a href="/product/getProduct?prodNo=${product.prodNo}&menu=${menu}">${product.prodName}</a></c:if>
							<c:if test="${ menu.equals('manage') }"><a href="/product/updateProductView?prodNo=${product.prodNo}&menu=${menu}">${product.prodName}</a></c:if></c:if> 
							<c:if test="${ !empty product.proTranCode }">${product.prodName}</c:if>
							</td>
			<td></td>
			<td align="left">${product.price}</td>
			<td></td>
			<td align="left">${product.prodNo}</td>
			<td></td>
			<td align="left"><c:if test="${ empty product.proTranCode }">�Ǹ���</c:if>
							<c:if test="${ !empty product.proTranCode && product.proTranCode.trim().equals('0') && menu.equals('manage')}">���� ���ſϷ� �����Դϴ�. 
							<a href="/purchase/updateTranCodeByProd?prodNo=${ product.prodNo }">����ϱ�</a></c:if>
							<c:if test="${ !empty product.proTranCode && product.proTranCode.trim().equals('0') && menu.equals('search')}">���� ���ſϷ� �����Դϴ�. 
							</c:if>
							<c:if test="${ !empty product.proTranCode && product.proTranCode.trim().equals('1') }">������Դϴ�.</c:if> 
							<c:if test="${ !empty product.proTranCode && product.proTranCode.trim().equals('2') }">��� ����</c:if> 
			</td>
			<td></td>
			<td align="left">��${product.eval}/5.0
							</td>
			
		</tr>
		<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value=""/>
			<jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->
</form>
</div>

</body>
</html>