<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:if test="${loginUser.user_tb ne '1' }">
	<script>
		show_noti_modalPopup("","비밀번호 수정 권한이 없습니다.",function(){
			//history.back();
			jsMenuContents('/ipsearch/main.do');
		});
	</script>
</c:if>
<c:if test="${loginUser.user_tb eq '1' }">
	<nav>
		<ol class="path">
			<li class="path-item"><i class="fas fa-home"></i></li>
			<li class="path-item">사용자 관리</li>
			<li class="path-item current-page">비밀번호 변경</li>
		</ol>
	</nav>
	
	<ul class="titarticle">
		<li><span class="titlebar"></span></li>
		<li><h2>비밀번호 변경</h2></li>
	</ul>
	
	
	<!-- start dtab01 content -->
	<div id='dtab01'>
	
		<!-- start 입력폼  -->
		<div class="detail-wrap">
			<ul>
				<li>
					<ul class="detail-title mt-44 fc">
						<li><span class="detail-title-bar"></span></li>
						<li><h3>사용자 비밀번호 재설정</h3></li>
						<li style="padding: 10px 0px; font-size: 15px;">&nbsp;&nbsp;(<em class="" style="color:blue;">비밀번호 정책 : </em>영문자, 숫자, 특수문자를 혼합하여 9자이상 20자 이하로 입력하시오.)</li>
						<li class="dright">
							<p class="detail-btn-wrap">
								'<i class="fas fa-star"></i>'은 필수입력 항목입니다.
							</p>
						</li>
					</ul>
				</li>
				<li>
					<div class="dtable-wrap">
						<form:form id="change_pwd_form" name="change_pwd_form">
							<table class="dtable dtable-reg">
								<tr>
									<td width="20%">현재 비밀번호<i class="fas fa-star"></i></td>
									<td><input class="dtable-inputs" type="password" name="user_pwd" id="user_pwd" value="" placeholder="" /></td>
								</tr>
								<tr>
									<td width="20%">새 비밀번호<i class="fas fa-star"></i></td>
									<td><input class="dtable-inputs" type="password" name="new_user_pwd" id="new_user_pwd1" value="" placeholder="" /></td>
								</tr>
								<tr>
									<td width="20%">새 비밀번호 확인<i class="fas fa-star"></i></td>
									<td><input class="dtable-inputs" type="password" name="" id="new_user_pwd2" value="" placeholder="" /></td>
								</tr>
							</table>
						</form:form>
					</div>
				</li>
			</ul>
		</div>
		<!-- end 입력폼 -->
	
		<br/> <br/> <br/>
		<div class="btn-center-wrap">
			<ul class="bottom-btn-group-center fc">
				<li><button type="button" class="btn btn-dark btn-large" onclick="set_new_pwd();">비밀번호 변경</button></li>
				<li><button type="button" class="btn btn-dark btn-large" onclick="jsMenuContents('/ipsearch/main.do');">취소</button></li>
			</ul>
		</div>
	
	</div>
	<!-- end dtab01 content -->
	<script>
		$(document).ready(function() {
			$("#change_pwd_form").find("input").change(function(e){
				$(this).val($(this).val().trim());
			});
			<c:if test="${loginUser.init_pw_yn != null and loginUser.init_pw_yn eq '1' }">
				show_noti_modalPopup("","임시비밀번호 발급 상태입니다.</br>비밀번호 변경시에만 서비스 이용이 가능합니다.");
			</c:if>
		});
		
		function set_new_pwd() {
			var user_pwd = $("#user_pwd").val().trim();
			if(user_pwd.length==0) {
				show_noti_modalPopup("","현재 비밀번호를 입력해주세요.",function(){$("#user_pwd").focus();});
				return;
			}
			var new_user_pwd1 = $("#new_user_pwd1").val().trim();
			if(new_user_pwd1.length==0) {
				show_noti_modalPopup("","신규 비밀번호를 입력해주세요.",function(){$("#new_user_pwd1").focus();});
				return;
			}
			var new_user_pwd2 = $("#new_user_pwd2").val().trim();
			if(new_user_pwd2.length==0) {
				show_noti_modalPopup("","신규 비밀번호를 확인을 입력해주세요.",function(){$("#new_user_pwd2").focus();});
				return;
			}
			if(new_user_pwd1!=new_user_pwd2){
				show_noti_modalPopup("","바꿀 비밀번호가 일치하지 않습니다.",function(){$("#new_user_pwd1").focus();});
				return;
			}
			
			if(/^(?=.*?[A-Za-z]{1,})(?=(.*[\d]){1,})(?=(.*[\W]){1,})(?!.*\s).{9,20}$/.test(new_user_pwd1)==false) {
				show_noti_modalPopup("","바꿀 비밀번호는 영문자, 숫자, 특수문자 조합으로<br/>9자 이상 20자 이하가 되어야 합니다.",function(){$("#new_user_pwd1").focus();});
				return;
			}
			
			var targetURL = '/user/pwdchg/set_new_pwd.do';
			var data = $("#change_pwd_form").serialize();
			$.ajax({
		        type: "POST",
		        url: targetURL,
		        data: data,
		        timeout: 60000,
		        success: function (data) {
					//console.log('success',data);
					console.log('success',targetURL,data);
					if(data.success) {
						show_noti_modalPopup("비밀번호 수정 성공",data.ip_addr+" 비밀번호 수정 성공</br>다시 로그인 해주세요.",function(){
							location.href="/logout";
						});
						/* show_noti_modalPopup("비밀번호 수정 성공",data.ip_addr+" 비밀번호 수정 성공",function(){
							jsMenuContents('/ipsearch/main.do');
						}); */
					} else {
						show_noti_modalPopup("비밀번호 수정 실패",data.ip_addr+" : "+data.err_msg);
					}
		        },
		        error: function ( event, jqxhr, settings, thrownError ) {
					console.log('error',targetURL,jqxhr);
					
				}
			});
		}
	</script>
</c:if>