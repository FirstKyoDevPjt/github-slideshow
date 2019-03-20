<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<ul class="result-box">
	<c:choose>
		<c:when test="${deptResultVoList !=null and fn:length(deptResultVoList)!=0}">
			<li>
				부서코드 / 부서명
			</li>
			<c:forEach items="${deptResultVoList}" var="item" varStatus="ipsearch_list_status">
			<c:set var="item_result" value="${item.dept_cd } / ${item.dept_nm }"/>
			<c:set var="item_json" value='{\"dept_cd\":\"${item.dept_cd }\",\"dept_nm\":\"${item.dept_nm }\"}'/>
				<li><a href="<c:out value="javascript:dept_search_select('${item.dept_cd }','${item_result }','${item_json }');"/>">
					<c:out value="${item_result }"/>
				</a></li>
			</c:forEach>
			<li style="text-align: center;"><a href="<c:out value="javascript:hide_dept_search_keyword_resultbox();"/>">닫기</a></li>
		</c:when>
		<c:otherwise>
			<li style="text-align: center;"><a href="<c:out value="javascript:hide_dept_search_keyword_resultbox();"/>">검색 결과가 없습니다.</a></li>
			<li style="text-align: center;"><a href="<c:out value="javascript:hide_dept_search_keyword_resultbox();"/>">닫기</a></li>
		</c:otherwise>
	</c:choose>
</ul>
