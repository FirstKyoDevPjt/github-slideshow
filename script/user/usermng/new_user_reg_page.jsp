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
			<li class="path-item current-page">신규 사용자 등록</li>
		</ol>
	</nav>
</div>

<ul class="titarticle">
	<li><span class="titlebar"></span></li>
	<li><h2>신규 사용자 등록</h2></li>
</ul>


<!-- start dtab01 content -->
<div id='dtab01'>

	<!-- start 입력폼  -->
	<div class="detail-wrap">
		<ul>
			<li>
				<ul class="detail-title mt-44 fc">
					<li><span class="detail-title-bar"></span></li>
					<li><h3>신규 사용자 정보 입력</h3></li>
					<li class="dright">
						<p class="detail-btn-wrap">'<i class="fas fa-star"></i>'은 필수입력 항목입니다.</p>
					</li>
				</ul>
			</li>
			<li>
				<div class="dtable-wrap">
					<form:form id="new_user_form" name="new_user_form">
					<table class="dtable dtable-reg">
						<tr>
							<td width="20%">사번(ID)<i class="fas fa-star"></i></td>
							<td>
								<input class="dtable-inputs" type="text" name="user_id" id="new_user_id" value="" placeholder="" style="width:750px;" />
								<button type="button" class="btn btn-xlarge" onclick="start_user_search_mapping();">사용자 정보 조회</button>
							</td>
						</tr>
						<tr>
							<td width="20%">비밀번호<i class="fas fa-star"></i></td>
							<td><input class="dtable-inputs" type="password" name="user_pwd" id="new_user_pwd" value=""/></td>
						</tr>
						<tr>
							<td width="20%">사용자이름<i class="fas fa-star"></i></td>
							<td><input class="dtable-inputs" type="text" name="user_nm" id="new_user_nm" user_info="user_nm" value="" placeholder="" /></td>
						</tr>
						<tr>
							<td width="20%">이메일<i class="fas fa-star"></i></td>
							<td><input class="dtable-inputs" type="email" name="user_email" id="new_user_email" user_info="user_email" value="" placeholder="" /></td>
						</tr>
						<tr>
							<td width="20%">핸드폰 번호<i class="fas fa-star"></i></td>
							<td><input class="dtable-inputs" type="text" name="tel_no" id="new_tel_no" value="" user_info="tel_no" placeholder="" /></td>
						</tr>
						<tr>
							<td width="20%">부서명<i class="fas fa-star"></i></td>
							<td>
								<input class="dtable-inputs" type="text" name="dept_nm" id="new_dept_nm" value="" placeholder=""  style="width:750px;" />
								<button type="button" class="btn btn-xlarge" onclick="start_dept_search_mapping();">부서 정보 조회</button>
							</td>
						</tr>
						<tr>
							<td width="20%">부서코드<i class="fas fa-star"></i></td>
							<td><input class="dtable-inputs" type="text" name="dept_cd" id="new_dept_cd" value="" placeholder="부서 정보를 조회하세요." readonly="readonly" /></td>
						</tr>
						<tr>
							<td width="20%">담당업무</td>
							<td><input class="dtable-inputs" type="text" name="pos" id="new_pos" value="" placeholder="" /></td>
						</tr>
						<tr>
							<td width="20%">직위</td>
							<td><input class="dtable-inputs" type="text" name="rank" id="new_rank" value="" placeholder="" /></td>
						</tr>
						<tr>
							<td width="20%">근무지</td>
							<td><input class="dtable-inputs" type="text" name="work_area" id="new_work_area" value="" placeholder="" /></td>
						</tr>
						<tr>
							<td width="20%">사용권한<i class="fas fa-star"></i></td>
							<td>
								<div class="dropdown-searchbox" id="new_user_div_dropdown">
									<div class="select">
										<span>일반사용자</span> <i class="fa fa-chevron-down"></i>
									</div>
									<input type="hidden" name="user_div" id="new_user_div" placeholder="사용 권한 선택" value="1">
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
							<td width="20%">담당매니저명</td>
							<td><input class="dtable-inputs" type="text" name="t_manager_nm" id="new_t_manager_nm" value="" placeholder="" /></td>
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
			<li><button type="button" class="btn btn-dark btn-large" onclick="add_new_user();">등록</button></li>
			<li><button type="button" class="btn btn-dark btn-large" onclick="history.back();">이전</button></li>
		</ul>
	</div>

</div>
<!-- end dtab01 content -->


<script>
	history.replaceState('hide_stuser_detail(1)',document.title,document.URL);
	history.pushState('hide_stuser_detail(2)',document.title,document.URL);
	document.getElementById('main').scrollIntoView();
	
	$(document).ready(function() {
		$("#new_tel_no").mask("000-0009-0000");
		
		$("#new_user_id").change(function(e){
			$("input[user_info]").val('');
			var inputVal = $(this).val();
			$(this).val(inputVal.replace(/[^a-z0-9@.]/gi,'').toUpperCase());
		});
		$("#new_user_id").keyup(function(event){
			if (!(event.keyCode >=37 && event.keyCode<=40)) {
				var inputVal = $(this).val();
				$(this).val(inputVal.replace(/[^a-z0-9@.]/gi,'').toUpperCase());
			}
		});
		$("#new_dept_nm").change(function(e){
			$("#new_dept_cd").val('');
		});
		$("#new_user_form").find("input").change(function(e){
			$(this).val($(this).val().trim());
		});
		
	});
	
	function start_user_search_mapping() {
		var searchkeyword = $("#new_user_id").val().trim();
		if(searchkeyword.length > 0)
			start_search_person_modalPopup(user_search_mapping, searchkeyword);
		else
			start_search_person_modalPopup(user_search_mapping);
	}
	
	function user_search_mapping(result_val,result_search_result,result_json) {
		$("#new_user_id").val(result_val);
		$("#new_user_nm").val(result_json.hname);
		$("#new_user_email").val(result_json.email);
		$("#new_tel_no").val(result_json.movetelno);
		$("#new_dept_nm").val(result_json.deptnm);
		$("#new_dept_cd").val(result_json.indept);
	}
	
	function start_dept_search_mapping() {
		var searchkeyword = $("#new_dept_nm").val().trim();
		if(searchkeyword.length > 0)
			start_search_dept_modalPopup(user_dept_mapping, searchkeyword);
		else
			start_search_dept_modalPopup(user_dept_mapping);
	}
	
	//start_search_dept_modalPopup
	function user_dept_mapping(result_val,result_search_result,result_json) {
		$("#new_dept_nm").val(result_json.dept_nm);
		$("#new_dept_cd").val(result_json.dept_cd);
	}
	
	function add_new_user() {
		var new_user_id = $("#new_user_id").val().trim();
		if(new_user_id.length==0) {
			show_noti_modalPopup("","사번(ID)을 입력해주세요.",function(){$("#new_user_id").focus();});
			return;
		}
		var new_user_pwd = $("#new_user_pwd").val().trim();
		if(new_user_pwd.length==0) {
			show_noti_modalPopup("","사용자 비밀번호를 입력해주세요.",function(){$("#new_user_pwd").focus();});
			return;
		}
		if(new_user_pwd.length>0) {
			if(/^(?=.*?[A-Za-z]{1,})(?=(.*[\d]){1,})(?=(.*[\W]){1,})(?!.*\s).{9,20}$/.test(new_user_pwd)==false) {
				show_noti_modalPopup("","비밀번호는 영문자, 숫자, 특수문자 조합으로<br/>9자 이상 20자 이하가 되어야 합니다.",function(){$("#new_user_pwd").focus();});
				return;
			}
		}
		
		var new_user_nm = $("#new_user_nm").val().trim();
		if(new_user_nm.length==0) {
			show_noti_modalPopup("","사용자 이름을 입력해주세요.",function(){$("#new_user_nm").focus();});
			return;
		}
		var new_user_email = $("#new_user_email").val().trim();
		if(new_user_email.length==0) {
			show_noti_modalPopup("","사용자 이메일을 입력해주세요.",function(){$("#new_user_email").focus();});
			return;
		}
		var new_tel_no = $("#new_tel_no").val().trim();
		if(new_tel_no.length==0) {
			show_noti_modalPopup("","사용자 핸드폰 번호를 입력해주세요.",function(){$("#new_tel_no").focus();});
			return;
		}
		var new_dept_cd = $("#new_dept_cd").val().trim();
		if(new_dept_cd.length==0) {
			show_noti_modalPopup("","사용자 부서명을 입력해주세요.",function(){$("#new_dept_cd").focus();});
			return;
		}
		var new_user_div = $("#new_user_div").val().trim();
		if(new_user_div.length==0) {
			show_noti_modalPopup("","사용자 권한을 선택해 주세요.",function(){$("#new_user_div_dropdown").trigger('click')});
			return;
		}
		
		
		var targetURL = '/user/usermng/add_new_user.do';
		var data = $("#new_user_form").serialize();
		$.ajax({
	        type: "POST",
	        url: targetURL,
	        data: data,
	        timeout: 60000,
	        success: function (data) {
				//console.log('success',data);
				console.log('success',targetURL,data);
				if(data.success) {
					show_noti_modalPopup("사용자 등록 성공",data.ip_addr+" 등록 성공",function(){
						//get_stuser_list($("#stuser_list_form").find("input[name=page]").val());
						start_stuser();
						//history.back();
						get_new_user_reg_page();
					});
				} else {
					show_noti_modalPopup("사용자 등록 실패",data.ip_addr+" : "+data.err_msg);
				}
	        },
	        error: function ( event, jqxhr, settings, thrownError ) {
				console.log('error',targetURL,jqxhr);
				
			}
		});
	}
	
</script>