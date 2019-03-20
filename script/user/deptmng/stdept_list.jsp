<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<form:form modelAttribute="deptSearchVo" id="stdept_list_form" name="stdept_list_form">
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
					<input type="checkbox" id="ip_check_all_ip">
					<label for="ip_check_all_ip"></label>
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
			<th scope="col" rowspan="2" width="15%">
				<c:set var = "c_order_nm">SYS_CREATE_DATE</c:set>
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
					생성일자
				</a>
			</th>
			<th scope="col" rowspan="2" width="15%">
				<c:set var = "c_order_nm">SYS_UPDATE_DATE</c:set>
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
					수정일자
				</a>
			</th>
			<th scope="col" rowspan="2" width="15%">
				<c:set var = "c_order_nm">DEPT_LV_CD</c:set>
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
					부서 권한
				</a>
			</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${stdept_list != null and fn:length(stdept_list)!=0}">
	        	<c:forEach items="${stdept_list}" var="item" varStatus="stdept_list_status">
					<tr>
					<c:set var="checkboxID" value="ip_check_ip${stdept_list_status.count }" />
						<td>
							<div class="checks small intable-td">
								<input type="checkbox" id="<c:out value="${checkboxID }"/>"
									dept_cd="<c:out value="${item.dept_cd}" default=""/>"
									dept_nm="<c:out value="${item.dept_nm}" default=""/>"
									index="<c:out value="${stdept_list_status.count}" default=""/>"/>
								<label for="<c:out value="${checkboxID }"/>"></label>
							</div>
						</td>
						<td><c:out value="${item.dept_cd}" default=""/></td>
						<td><c:out value="${item.dept_nm}" default=""/></td>
						<td>
							<fmt:formatDate value="${item.sys_create_date}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							<c:if test="${item.sys_update_date != null}">
								<fmt:formatDate value="${item.sys_update_date}" pattern="yyyy-MM-dd"/>
							</c:if>
						</td>
						<td>
							<div class="form-group">
								<div class="dropdown w130"><!-- dept_lv_cd -->
									<div class="select">
										<c:choose>
											<c:when test="${item.dept_lv_cd eq '1' }"><c:set var="item_div_nm" value="일반사용자"/></c:when>
											<c:when test="${item.dept_lv_cd eq '2' }"><c:set var="item_div_nm" value="서버운영자"/></c:when>
											<c:when test="${item.dept_lv_cd eq '3' }"><c:set var="item_div_nm" value="보안/RM 담당자"/></c:when>
											<c:when test="${item.dept_lv_cd eq '4' }"><c:set var="item_div_nm" value="NW담당자"/></c:when>
											<c:when test="${item.dept_lv_cd eq '5' }"><c:set var="item_div_nm" value="시스템관리자"/></c:when>
										</c:choose>
										<span><c:out value="${item_div_nm }" default=""/></span><i class="fa fa-chevron-down"></i>
									</div>
									<input type="hidden" name="select_dept_lv_cd"
										id="select_dept_lv_cd<c:out value="${stdept_list_status.count}" default=""/>"
										value="<c:out value="${item.dept_lv_cd }" default=""/>"
										dept_cd="<c:out value="${item.dept_cd}" default=""/>"
										dept_nm="<c:out value="${item.dept_nm}" default=""/>"
										index="<c:out value="${stuser_list_status.count}" default=""/>"/>
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
		      		<td class="nodata" colspan="6">등록된 정보가 없습니다</td>
		 		</tr>
       		</c:otherwise>
       	</c:choose>
	</tbody>
</table>
