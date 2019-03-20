<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<form:form modelAttribute="deptSearchVo" id="new_stdept_list_form" name="new_stdept_list_form">
	<form:hidden path="dept_cd" search_id="dept_cd"/>
	<form:hidden path="dept_nm" search_id="dept_nm"/>
	<form:hidden path="search_id" search_id="search_id"/>
	<form:hidden path="search_value" search_id="search_value"/>
	<form:hidden path="page"/>
	<form:hidden path="perPageCnt"/>
	<form:hidden path="orderby_nm"/>
	<form:hidden path="desc"/>
</form:form>
<table class="table table-wrap board-list">
	<thead>
		<tr>
			<th scope="col" rowspan="2" width="1%">
				<div class="checks small intable-th">
					<input type="checkbox" id="ip_check2_all_ip">
					<label for="ip_check2_all_ip"></label>
				</div>
			</th>
			<th scope="col" rowspan="2" width="15%">
				<c:set var = "c_order_nm">DEPT_CD</c:set>
				<c:choose>
					<c:when test="${(pagingVo.orderby_nm eq c_order_nm and !pagingVo.b_desc) or pagingVo.orderby_nm eq ''}">
						<c:set var="desc_arg" value=",'TRUE'" />
					</c:when>
					<c:otherwise>
						<c:set var="desc_arg" value=",'FALSE'" />
					</c:otherwise>
				</c:choose>
				<c:set var="order_script">${pagingVo.pageMoveScriptNm}(1,null,'${c_order_nm }'${desc_arg });</c:set>
				<a href="javascript:<c:out value="${order_script}"/>">
					부서코드
				</a>
			</th>
			<th scope="col" rowspan="2" width="*">
				<c:set var = "c_order_nm">DEPT_NM</c:set>
				<c:choose>
					<c:when test="${pagingVo.orderby_nm eq c_order_nm and !pagingVo.b_desc}">
						<c:set var="desc_arg" value=",'TRUE'" />
					</c:when>
					<c:otherwise>
						<c:set var="desc_arg" value=",'FALSE'" />
					</c:otherwise>
				</c:choose>
				<c:set var="order_script">${pagingVo.pageMoveScriptNm}(1,null,'${c_order_nm }'${desc_arg });</c:set>
				<a href="javascript:<c:out value="${order_script}"/>">
					부서명
				</a>
			</th>
			<th scope="col" rowspan="2" width="15%">등록할 부서권한</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${new_stdept_list != null and fn:length(new_stdept_list)!=0}">
	        	<c:forEach items="${new_stdept_list}" var="item" varStatus="new_stdept_list_status">
					<tr>
					<c:set var="checkboxID" value="ip_check2_ip${new_stdept_list_status.count }" />
						<td>
							<div class="checks small intable-td">
								<input type="checkbox" id="<c:out value="${checkboxID }"/>"
									dept_cd="<c:out value="${item.dept_cd}" default=""/>"
									dept_nm="<c:out value="${item.dept_nm}" default=""/>"
									index="<c:out value="${new_stdept_list_status.count}" default=""/>"/>
								<label for="<c:out value="${checkboxID }"/>"></label>
							</div>
						</td>
						<td><c:out value="${item.dept_cd}" default=""/></td>
						<td><c:out value="${item.dept_nm}" default=""/></td>
						<td>
							<div class="form-group">
								<div class="dropdown w130"><!-- dept_lv_cd -->
									<div class="select">
										<span>보안/RM 담당자</span><i class="fa fa-chevron-down"></i>
									</div>
									<input type="hidden" name="select_dept_lv_cd"
										id="select_new_dept_lv_cd<c:out value="${new_stdept_list_status.count}" default=""/>"
										value="3"
										dept_cd="<c:out value="${item.dept_cd}" default=""/>"
										dept_nm="<c:out value="${item.dept_nm}" default=""/>"
										index="<c:out value="${new_stdept_list_status.count}" default=""/>"/>
									<ul class="dropdown-menu">
										<li val="1">일반사용자</li>
										<li val="2">서버운영자</li>
										<li val="3">보안/RM 담당자</li>
										<li val="4">NW담당자</li>
										<li val="5">시스템관리자</li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:when>
       		<c:otherwise>
				<tr class="first">
		      		<td class="nodata" colspan="3">등록된 정보가 없습니다</td>
		 		</tr>
       		</c:otherwise>
       	</c:choose>
	</tbody>
</table>
