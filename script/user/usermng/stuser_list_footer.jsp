<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<ul class="bottom-btn-group-wrap fc">
	<li><button type="button" class="btn btn-dark" onclick="get_new_user_reg_page();" style="width: 140px;">신규 사용자 등록</button></li>
	<li class="align-right fc">
		<ul class="btn-group fc">
			<li><button type="button" class="btn btn-dark" onclick="stuser_set_user_div_before();">권한 적용</button></li>
			<li><button type="button" class="btn btn-dark" onclick="stuser_reset_pwd_before();">비밀번호 초기화</button></li>
			<li><button type="button" class="btn btn-dark" onclick="get_user_modify_page();">수정</button></li>
			<li><button type="button" class="btn btn-dark" onclick="stuser_remove_before();">삭제</button></li>
		</ul>
	</li>
</ul>
<script>
	//전체 선택 체크박스 선택시
	$("#ip_check_all_ip").change(function() {
		$("input[id*=ip_check_ip]").prop("checked", $(this).prop("checked"));
	});

	//개별 선택 체크박스 선택시
	$("input[id*=ip_check_ip]").change(function() {
		var check_val = $(this).prop("checked");
		var check_val_flag = check_val;
		if (check_val) {
			$("input[id*=ip_check_ip]").each(function(idx, element) {
				if (check_val != $(element).prop("checked"))
					check_val_flag = !check_val;
			});
		} else {
			$("#ip_check_all_ip").prop("checked", check_val_flag);
		}
		$("#ip_check_all_ip").prop("checked", check_val_flag);
	});
	
	//stuser_list_search_value
	$("#stuser_list_search_value").keyup(function(event){
    	if(event.keyCode==13||event.keyCode==108) {
    		stuser_list_search();
    	}
    });
	
</script>
