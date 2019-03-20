<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<ul class="list-header fc">
	<li class="result-count-msg">
		<p class="state">
			총 <span><c:out value="${pagingVo.totalCount }" /></span> 건의 목록이 있습니다.
		</p>
	</li>
	<li class="btn-group">
		<c:if test="${pagingVo.showChagePerPageCnt }">
			<div class="form-group">
				<div class="dropdown">
					<div class="select">
						<span><c:out value="${pagingVo.perPageCnt}"/>개씩 보기</span> <i class="fa fa-chevron-down"></i>
					</div>
					<input type="hidden" name="perPageCnt" id="<c:out value="${pagingVo.pageMoveScriptNm}_perPageCnt"/>"
						value="<c:out value="${pagingVo.perPageCnt}"/>" 
						onchange="<c:out value="${pagingVo.pageMoveScriptNm}"/>(1);" />
					<ul class="dropdown-menu">
						<li val="10">10개씩 보기</li>
						<li val="20">20개씩 보기</li>
						<li val="30">30개씩 보기</li>
						<li val="50">50개씩 보기</li>
					</ul>
				</div>
			</div>
		</c:if>
		<div class="form-group">
			<p class="list-btn-search-wrap">
				<input type="text" name="stuser_list_search_value" id="stuser_list_search_value" class="search-input"
					value="<c:out value="${userSearchVo.search_value}" default=""/>"
					placeholder="이름/사번/이메일/핸드폰번호를 입력해 주세요." />
				<button type="submit" class="search-icon" onclick="stuser_list_search();">
					<i class="fa fa-search"></i>
				</button>
			</p>
		</div>
	</li>
</ul>
