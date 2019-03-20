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
		<c:when test="${personInfoResultList !=null and fn:length(personInfoResultList)!=0}">
		<%-- and !(fn:length(PersonInfoResultList)==1 and PersonInfoResultList[0].hname eq loginUser.name) --%>
			<li>
				이름 / 사번 / Email / 부서
			</li>
			<c:forEach items="${personInfoResultList}" var="item" varStatus="ipsearch_list_status">
			<c:set var="item_result" value="${item.hname } / ${item.empno } / ${item.email } / ${item.deptnm }"/>
			<c:set var="item_json" value='{\"empno\":\"${item.empno }\",\"hname\":\"${item.hname }\",\"indept\":\"${item.indept }\",\"deptnm\":\"${item.deptnm }\",\"email\":\"${item.email }\"}'/>
			<sec:authorize access="hasAnyRole('ROLE_USER_3','ROLE_USER_4','ROLE_USER_5')">
				<c:set var="item_json" value='{\"empno\":\"${item.empno }\",\"hname\":\"${item.hname }\",\"indept\":\"${item.indept }\",\"deptnm\":\"${item.deptnm }\",\"sosok\":\"${item.sosok }\",\"tsosok\":\"${item.tsosok }\",\"movetelno\":\"${item.movetelno }\",\"email\":\"${item.email }\"}'/>
			</sec:authorize>
				<li><a href="<c:out value="javascript:person_search_select('${item.empno }','${item_result }','${item_json }');"/>">
					<c:out value="${item_result }"/>
				</a></li>
			</c:forEach>
			<li style="text-align: center;"><a href="<c:out value="javascript:hide_person_search_keyword_resultbox();"/>">닫기</a></li>
		</c:when>
		<c:otherwise>
			<li style="text-align: center;"><a href="<c:out value="javascript:hide_person_search_keyword_resultbox();"/>">검색 결과가 없습니다.</a></li>
			<li style="text-align: center;"><a href="<c:out value="javascript:hide_person_search_keyword_resultbox();"/>">닫기</a></li>
		</c:otherwise>
	</c:choose>
</ul>
