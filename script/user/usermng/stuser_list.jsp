<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<form:form modelAttribute="userSearchVo" id="stuser_list_form" name="stuser_list_form">
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
			<th scope="col" rowspan="2" width="10%">
				<c:set var = "c_order_nm">USER_NM</c:set>
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
					사용자이름
				</a>
			</th>
			<th scope="col" rowspan="2" width="8%">
				<c:set var = "c_order_nm">USER_ID</c:set>
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
					사번(ID)
				</a>
			</th>
			<th scope="col" rowspan="2" width="13%">부서명</th>
			<th scope="col" rowspan="2" width="20%">
				<c:set var = "c_order_nm">USER_EMAIL</c:set>
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
					이메일
				</a>
			</th>
			<th scope="col" rowspan="2" width="10%">
				<c:set var = "c_order_nm">TEL_NO</c:set>
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
					핸드폰 번호
				</a>
			</th>
			<th scope="col" rowspan="2" width="10%">
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
					등록일
				</a>
			</th>
			<th scope="col" rowspan="2" width="10%">
				<c:set var = "c_order_nm">USE_FLAG</c:set>
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
					사용여부
				</a>
			</th>
			<th scope="col" rowspan="2" width="13%">
				<c:set var = "c_order_nm">USER_DIV</c:set>
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
					사용자 권한
				</a>
			</th>
			<th scope="col" rowspan="2" width="13%">비밀번호 초기화</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${stuser_list != null and fn:length(stuser_list)!=0}">
	        	<c:forEach items="${stuser_list}" var="item" varStatus="stuser_list_status">
					<tr>
					<c:set var="checkboxID" value="ip_check_ip${stuser_list_status.count }" />
						<td>
							<div class="checks small intable-td">
								<input type="checkbox" id="<c:out value="${checkboxID }"/>"
									user_id="<c:out value="${item.user_id}" default=""/>"
									user_nm="<c:out value="${item.user_nm}" default=""/>"
									index="<c:out value="${stuser_list_status.count}" default=""/>"/>
								<label for="<c:out value="${checkboxID }"/>"></label>
							</div>
						</td>
						<td><c:out value="${item.user_nm}" default=""/></td>
						<td><c:out value="${item.user_id}" default=""/></td>
						<td><c:out value="${item.dept_nm}" default=""/></td>
						<td><c:out value="${item.user_email}" default=""/></td>
						<td><c:out value="${item.tel_no}" default=""/></td>
						<td>
							<fmt:formatDate value="${item.sys_create_date}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							<c:choose>
								<c:when test="${item.use_flag != null and item.use_flag eq '1'}">
									미사용
								</c:when>
								<c:otherwise>
									사용
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<div class="form-group">
								<div class="dropdown w130"><!-- user_div -->
									<div class="select">
										<c:choose>
											<c:when test="${item.user_div eq '1' }"><c:set var="item_div_nm" value="일반사용자"/></c:when>
											<c:when test="${item.user_div eq '2' }"><c:set var="item_div_nm" value="서버운영자"/></c:when>
											<c:when test="${item.user_div eq '3' }"><c:set var="item_div_nm" value="보안/RM 담당자"/></c:when>
											<c:when test="${item.user_div eq '4' }"><c:set var="item_div_nm" value="NW담당자"/></c:when>
											<c:when test="${item.user_div eq '5' }"><c:set var="item_div_nm" value="시스템관리자"/></c:when>
										</c:choose>
										<span><c:out value="${item_div_nm }" default=""/></span><i class="fa fa-chevron-down"></i>
									</div>
									<input type="hidden" name="select_user_div"
										id="select_user_div<c:out value="${stuser_list_status.count}" default=""/>"
										value="<c:out value="${item.user_div }" default=""/>"
										user_id="<c:out value="${item.user_id}" default=""/>"
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
						<td>
							<div class="form-group">
								<div class="dropdown w130">
									<div class="select">
										<span>핸드폰</span> <i class="fa fa-chevron-down"></i>
									</div>
									<input type="hidden" name="select_pwd_reset_type"
										id="select_pwd_reset_type<c:out value="${stuser_list_status.count}" default=""/>"
										user_id="<c:out value="${item.user_id}" default=""/>"
										index="<c:out value="${stuser_list_status.count}" default=""/>"/>
									<ul class="dropdown-menu">
										<li value="phone">핸드폰</li>
										<!-- <li value="email">이메일</li> -->
									</ul>
								</div>
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:when>
       		<c:otherwise>
				<tr class="first">
		      		<td class="nodata" colspan="10">등록된 정보가 없습니다</td>
		 		</tr>
       		</c:otherwise>
       	</c:choose>
	</tbody>
</table>
