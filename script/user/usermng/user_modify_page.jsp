<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="main-wrap">
	<nav>
		<ol class="path">
			<li class="path-item"><i class="fas fa-home"></i></li>
			<li class="path-item">사용자 관리</li>
			<li class="path-item current-page">사용자 수정</li>
		</ol>
	</nav>
</div>

<ul class="titarticle">
	<li><span class="titlebar"></span></li>
	<li><h2>사용자 수정</h2></li>
</ul>


<!-- start dtab01 content -->
<div id='dtab01'>

	<!-- start 입력폼  -->
	<div class="detail-wrap">
		<ul>
			<li>
				<ul class="detail-title mt-44 fc">
					<li><span class="detail-title-bar"></span></li>
					<li><h3>사용자 수정</h3></li>
					<li class="dright">
						<p class="detail-btn-wrap">'<i class="fas fa-star"></i>'은 필수입력 항목입니다.</p>
					</li>
				</ul>
			</li>
			<li>
				<div class="dtable-wrap">
					<form:form id="modify_user_form" name="modify_user_form">
					<table class="dtable dtable-reg">
						<tr>
							<td width="20%">사번(ID)<i class="fas fa-star"></i></td>
							<td>
								<input class="dtable-inputs" type="text" name="user_id" id="modify_user_id" 
									value="<c:out value="${userResultVo.user_id }" default=""/>" placeholder="" style="width:750px;" readonly="readonly"/>
								<button type="button" class="btn btn-xlarge" onclick="user_replace_mapping();">사용자 정보 새로고침</button>
							</td>
						</tr>
						<tr>
							<td width="20%">비밀번호</td>
							<td>
								<input class="dtable-inputs" type="password" name="user_pwd" id="modify_user_pwd"
									value="" placeholder="비밀번호로 입력시 해당 비밀번호로 변경됩니다." style="width:750px;" />
								<button type="button" class="btn btn-xlarge" onclick="user_modify_reset_pwd_btn();">비밀번호 초기화</button>
							</td>
						</tr>
						<tr>
							<td width="20%">사용자이름<i class="fas fa-star"></i></td>
							<td>
								<input class="dtable-inputs" type="text" name="user_nm" id="modify_user_nm" user_info="user_nm"
								value="<c:out value="${userResultVo.user_nm }" default=""/>" placeholder="" />
							</td>
						</tr>
						<tr>
							<td width="20%">이메일<i class="fas fa-star"></i></td>
							<td>
								<input class="dtable-inputs" type="email" name="user_email" id="modify_user_email" user_info="user_email" 
									value="<c:out value="${userResultVo.user_email }" default=""/>" placeholder="" />
							</td>
						</tr>
						<tr>
							<td width="20%">핸드폰 번호<i class="fas fa-star"></i></td>
							<td>
								<input class="dtable-inputs" type="text" name="tel_no" id="modify_tel_no"
									value="<c:out value="${userResultVo.tel_no }" default=""/>" user_info="tel_no" placeholder="" />
							</td>
						</tr>
						<tr>
							<td width="20%">부서명<i class="fas fa-star"></i></td>
							<td>
								<input class="dtable-inputs" type="text" name="dept_nm" id="modify_dept_nm" 
									value="<c:out value="${userResultVo.dept_nm }" default=""/>" placeholder=""  style="width:750px;" />
								<button type="button" class="btn btn-xlarge" onclick="start_dept_search_mapping();">부서 정보 조회</button>
							</td>
						</tr>
						<tr>
							<td width="20%">부서코드<i class="fas fa-star"></i></td>
							<td>
								<input class="dtable-inputs" type="text" name="dept_cd" id="modify_dept_cd" 
									value="<c:out value="${userResultVo.dept_cd }" default=""/>" placeholder="부서 정보를 조회하세요." readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td width="20%">담당업무</td>
							<td>
								<input class="dtable-inputs" type="text" name="pos" id="modify_pos" 
									value="" placeholder="<c:out value="${userResultVo.pos }" default=""/>" />
							</td>
						</tr>
						<tr>
							<td width="20%">직위</td>
							<td>
								<input class="dtable-inputs" type="text" name="rank" id="modify_rank" 
									value="<c:out value="${userResultVo.rank }" default=""/>" placeholder="" />
							</td>
						</tr>
						<tr>
							<td width="20%">근무지</td>
							<td>
								<input class="dtable-inputs" type="text" name="work_area" id="modify_work_area" 
									value="<c:out value="${userResultVo.work_area }" default=""/>" placeholder="" />
							</td>
						</tr>
						<tr>
							<td width="20%">사용권한<i class="fas fa-star"></i></td>
							<td>
								<div class="dropdown-searchbox" id="modify_user_div_dropdown">
									<c:choose>
										<c:when test="${userResultVo.user_div eq '1' }"><c:set var="item_div_nm" value="일반사용자"/></c:when>
										<c:when test="${userResultVo.user_div eq '2' }"><c:set var="item_div_nm" value="서버운영자"/></c:when>
										<c:when test="${userResultVo.user_div eq '3' }"><c:set var="item_div_nm" value="보안/RM 담당자"/></c:when>
										<c:when test="${userResultVo.user_div eq '4' }"><c:set var="item_div_nm" value="NW담당자"/></c:when>
										<c:when test="${userResultVo.user_div eq '5' }"><c:set var="item_div_nm" value="시스템관리자"/></c:when>
									</c:choose>									
									<div class="select">
										<span><c:out value="${item_div_nm }" default=""/></span><i class="fa fa-chevron-down"></i>
									</div>
									<input type="hidden" name="user_div" id="modify_user_div" placeholder="사용 권한 선택" 
										value="<c:out value="${userResultVo.user_div }" default=""/>">
									<ul class="dropdown-menu dropdown-large">
										<li val="1">일반사용자</li>
										<li val="2">서버운영자</li>
										<li val="3">보안/RM 담당자</li>
										<li val="4">NW담당자</li>
										<li val="5">시스템관리자</li>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td width="20%">사용여부<i class="fas fa-star"></i></td>
							<td>
								<div class="dropdown-searchbox" id="modify_use_flag_dropdown">
									<c:choose>
										<c:when test="${userResultVo.use_flag eq '1' }">
											<c:set var="item_use_flag" value="미사용"/>
										</c:when>
										<c:otherwise>
											<c:set var="item_use_flag" value="사용"/>
										</c:otherwise>										
									</c:choose>									
									<div class="select">
										<span><c:out value="${item_use_flag }" default=""/></span><i class="fa fa-chevron-down"></i>
									</div>
									<input type="hidden" name="use_flag" id="modify_use_flag" placeholder="사용 권한 선택" 
										value="<c:out value="${userResultVo.use_flag }" default=""/>">
									<ul class="dropdown-menu dropdown-large">
										<li val="0">사용</li>
										<li val="1">미사용</li>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td width="20%">담당매니저명</td>
							<td>
								<input class="dtable-inputs" type="text" name="t_manager_nm" id="modify_t_manager_nm" 
									value="<c:out value="${userResultVo.t_manager_nm }" default=""/>" placeholder="" />
							</td>
						</tr>
						<tr>
							<td width="20%">등록일</td>
							<td>
								<c:if test="${userResultVo.sys_create_date != null }">
									<fmt:formatDate value="${userResultVo.sys_create_date}" pattern="yyyy-MM-dd"/>
								</c:if>
							</td>
						</tr>
						<tr>
							<td width="20%">최종수정일</td>
							<td>
								<c:if test="${userResultVo.sys_update_date != null }">
									<fmt:formatDate value="${userResultVo.sys_update_date}" pattern="yyyy-MM-dd"/>
								</c:if>
								<%-- <c:if test="${userResultVo.sys_update_date == null }">
									<fmt:formatDate value="${userResultVo.sys_create_date}" pattern="yyyy-MM-dd"/>
								</c:if> --%>
							</td>
						</tr>
					</table>
					</form:form>
				</div>
			</li>
		</ul>
	</div>
	<!-- end 입력폼 -->

	<br/><br/><br/>
	<div class="btn-center-wrap">
		<ul class="bottom-btn-group-center fc">
			<li><button type="button" class="btn btn-dark btn-large" onclick="set_modify_user();">변경 등록</button></li>
			<li><button type="button" class="btn btn-dark btn-large" onclick="history.back();">취소</button></li>
		</ul>
	</div>

</div>
<!-- end dtab01 content -->


<script>
	history.replaceState('hide_stuser_detail(1)',document.title,document.URL);
	history.pushState('hide_stuser_detail(2)',document.title,document.URL);
	document.getElementById('main').scrollIntoView();
	
	$(document).ready(function() {
		$("#modify_tel_no").mask("000-0009-0000");
		
		$("#modify_user_id").change(function(e){
			$("input[user_info]").val('');
			var inputVal = $(this).val();
			$(this).val(inputVal.replace(/[^a-z0-9@.]/gi,'').toUpperCase());
		});
		$("#modify_user_id").keyup(function(event){
			if (!(event.keyCode >=37 && event.keyCode<=40)) {
				var inputVal = $(this).val();
				$(this).val(inputVal.replace(/[^a-z0-9@.]/gi,'').toUpperCase());
			}
		});
		$("#modify_dept_nm").change(function(e){
			$("#modify_dept_cd").val('');
		});
		$("#modify_user_form").find("input").change(function(e){
			$(this).val($(this).val().trim());
		});
		
	});
	
	function user_replace_mapping() {
		var search_keyword = $("#modify_user_id").val().trim();
		var targetURL = '/user/usermng/searchperson0.do';
		var data = $("#modify_user_form").serialize();
		$.ajax({
	        type: "POST",
	        url: targetURL,
	        data: "search_keyword="+search_keyword,
	        timeout: 60000,
	        success: function (data) {
				//console.log('success',data);
				console.log('success',targetURL,data);
				if(data != null && data.empno != null && data.empno != "") {
					$("#modify_user_nm").val(data.hname);
					$("#modify_user_email").val(data.email);
					$("#modify_tel_no").val(data.movetelno);
					$("#modify_dept_nm").val(data.deptnm);
					$("#modify_dept_cd").val(data.indept);
				} else {
					show_noti_modalPopup("사용자 정보 조회 실패","인사테이블에 맞는 사용자가 없습니다.");
				}
	        },
	        error: function ( event, jqxhr, settings, thrownError ) {
				console.log('error',targetURL,jqxhr);
			}
		});
	}
	
	function start_dept_search_mapping() {
		var searchkeyword = $("#modify_dept_nm").val().trim();
		if(searchkeyword.length > 0)
			start_search_dept_modalPopup(user_dept_mapping, searchkeyword);
		else
			start_search_dept_modalPopup(user_dept_mapping);
	}
	
	//start_search_dept_modalPopup
	function user_dept_mapping(result_val,result_search_result,result_json) {
		$("#modify_dept_nm").val(result_json.dept_nm);
		$("#modify_dept_cd").val(result_json.dept_cd);
	}
	
	
	function set_modify_user() {
		var modify_user_id = $("#modify_user_id").val().trim();
		if(modify_user_id.length==0) {
			show_noti_modalPopup("","사번(ID)을 입력해주세요.",function(){$("#modify_user_id").focus();});
			return;
		}
		var modify_user_pwd = $("#modify_user_pwd").val().trim();
		if(modify_user_pwd.length>0) {
			if(/^(?=.*?[A-Za-z]{1,})(?=(.*[\d]){1,})(?=(.*[\W]){1,})(?!.*\s).{9,20}$/.test(modify_user_pwd)==false) {
				show_noti_modalPopup("","바꿀 비밀번호는 영문자, 숫자, 특수문자 조합으로<br/>9자 이상 20자 이하가 되어야 합니다.",function(){$("#modify_user_pwd").focus();});
				return;
			}
		}
		var modify_user_nm = $("#modify_user_nm").val().trim();
		if(modify_user_nm.length==0) {
			show_noti_modalPopup("","사용자 이름을 입력해주세요.",function(){$("#modify_user_nm").focus();});
			return;
		}
		var modify_user_email = $("#modify_user_email").val().trim();
		if(modify_user_email.length==0) {
			show_noti_modalPopup("","사용자 이메일을 입력해주세요.",function(){$("#modify_user_email").focus();});
			return;
		}
		var modify_tel_no = $("#modify_tel_no").val().trim();
		if(modify_tel_no.length==0) {
			show_noti_modalPopup("","사용자 이름을 입력해주세요.",function(){$("#modify_tel_no").focus();});
			return;
		}
		var modify_dept_cd = $("#modify_dept_cd").val().trim();
		if(modify_dept_cd.length==0) {
			show_noti_modalPopup("","사용자 부서명을 입력해주세요.",function(){$("#modify_dept_cd").focus();});
			return;
		}
		var modify_user_div = $("#modify_user_div").val().trim();
		if(modify_user_div.length==0) {
			show_noti_modalPopup("","사용자 권한을 선택해 주세요.",function(){$("#modify_user_div_dropdown").trigger('click')});
			return;
		}
		var modify_use_flag = $("#modify_use_flag").val().trim();
		if(modify_use_flag.length==0) {
			show_noti_modalPopup("","사용여부를 선택해 주세요.",function(){$("#modify_use_flag_dropdown").trigger('click')});
			return;
		}
		
		
		var targetURL = '/user/usermng/set_modify_user.do';
		var data = $("#modify_user_form").serialize();
		$.ajax({
	        type: "POST",
	        url: targetURL,
	        data: data,
	        timeout: 60000,
	        success: function (data) {
				//console.log('success',data);
				console.log('success',targetURL,data);
				if(data.success) {
					show_noti_modalPopup("사용자 수정 성공",data.ip_addr+" 수정 성공",function(){
						get_stuser_list($("#stuser_list_form").find("input[name=page]").val());
						//start_stuser();
						history.back();
					});
				} else {
					show_noti_modalPopup("사용자 수정 실패",data.ip_addr+" : "+data.err_msg);
				}
	        },
	        error: function ( event, jqxhr, settings, thrownError ) {
				console.log('error',targetURL,jqxhr);
				
			}
		});
	}
	
	function user_modify_reset_pwd_btn() {
		confirm_modalPopup("","비밀번호를 초기화 하시 겠습니까?",do_user_modify_reset_pwd);
	}
	
	
	function do_user_modify_reset_pwd() {
		var temp_user_list = [];
		var temp = {}
		temp.user_id = $("#modify_user_id").val().trim();
   		temp.user_nm = $("#modify_user_nm").val().trim();
   		temp.pwd_reset_type = "phone";
   		temp_user_list.push(temp);
		$.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/user/usermng/set_reset_pwd.do",
	        data: JSON.stringify({"user_list":temp_user_list}),
	        timeout: 60000,
	        success: function (data) {
				console.log('success',data);
				if(data.ipChangeResultVoList == null) {
					show_noti_modalPopup("비밀번호를 초기화 실패","처리 결과가 없습니다.");
				} else {
					var result_msg = "";
					if(data.requestCnt >0 && data.requestCnt == data.successCnt) {
						data.ipChangeResultVoList.forEach(function (item, index, array) {
						    result_msg+=item.ip_addr+" => 비밀번호 초기화 성공";
						});
					} else {
						data.ipChangeResultVoList.forEach(function (item, index, array) {
						    if(item.success == false) {
						    	result_msg+=item.ip_addr+" => "+item.err_msg+"<br/>";
						    }
						});
					}
					show_noti_modalPopup("비밀번호를 초기화 결과",result_msg,start_stuser);
				}
	        },
	        error: function ( event, jqxhr, settings, thrownError ) {
				console.log('error',jqxhr);
				
			}
		});
	}
	
</script>