<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div id="stdept_main">
	<div class="main-wrap">
		<nav>
			<ol class="path">
				<li class="path-item"><i class="fas fa-home">home</i></li>
				<li class="path-item">사용자 관리</li>
				<li class="path-item current-page">부서별 권한 관리</li>
			</ol>
		</nav>
	</div>

<ul class="titarticle">
	<li><span class="titlebar"></span></li>
	<li><h2>부서별 권한 관리</h2></li>
</ul>

	<form:form id="stdept_main_form" name="stdept_main_form"></form:form>

<div id="stdept_list"></div>
           
</div>

<div id="new_stdept"></div>

<script>
	$(document).ready(function(){
		start_stdept();
	});
	
	function start_stdept() {
		//document.stdept_main
		get_stdept_list(1,"stdept_main_form");
	}
	
	function get_stdept_list(pageNum, targetFormNm, orderby_nm, desc) {
		var orglist_formNm = "stdept_list_form";
		var addparam = "";
		if(pageNum==null)pageNum=1;
		if(targetFormNm == null) {
			targetFormNm = orglist_formNm;
			var formPerPageCnt = $("#"+targetFormNm+">[name=perPageCnt]").val();
			var labelPerPageCnt = $('#get_stdept_list_perPageCnt').val();
			if(formPerPageCnt!=labelPerPageCnt) {
				$("#"+targetFormNm+">[name=perPageCnt]").val(labelPerPageCnt);
				pageNum=1;
			}
			if(orderby_nm!=null){
				$("#"+targetFormNm+">[name=orderby_nm]").val(orderby_nm);
				if(desc!=null) $("#"+targetFormNm+">[name=desc]").val(desc);
				pageNum=1;
			}
			$("#"+targetFormNm+">[name=page]").val(pageNum);
			
		} else {
			var perPageCnt = $("#"+orglist_formNm+">[name=perPageCnt]").val();
			if(perPageCnt!=null) addparam = "&perPageCnt="+perPageCnt;
		}
		var targetForm = $("#"+targetFormNm);
		var targetURL = '/user/deptmng/stdept_list.do';
		$.ajax({
	        type: "POST",
	        url: targetURL,
	        data: targetForm.serialize()+addparam,
	        timeout: 60000,
	        success: function (data) {
				//console.log('success',data);
				console.log('success',targetURL);
				$("#stdept_list").html(data);
				bindForm();
				init();
	        },
	        error: function ( event, jqxhr, settings, thrownError ) {
				console.log('error',targetURL,jqxhr);
				$("#stdept_list").html('');
			}
		});
	}//get_stdept_list() end
	
	
	function stdept_list_search() {
		var search_id = $("#stdept_list_search_id").val();
		var search_value = $("#stdept_list_search_value").val().trim();
		if(!(search_id != null && search_id.length>0)) {
			if(search_value != null && search_value.length>0) {
				show_noti_modalPopup('','검색 조건을 선택해 주세요.');
				return;
			}
		} else if(!(search_value != null && search_value.length>0)) {
			show_noti_modalPopup('','검색어를 입력해 주세요.',function(){
				$("#stdept_list_search_value").focus();
			});
			return;
		}
		$("#stdept_list_form").find("input[search_id]").val('');
		if(search_id.length>0)
			$("#stdept_list_form").find("input[search_id="+search_id+"]").val(search_value);
		$("#stdept_list_form").find("input[search_id=search_id]").val(search_id);
		$("#stdept_list_form").find("input[search_id=search_value]").val(search_value);
		get_stdept_list(1);
	}
	
	
	//new_stdept
	function show_new_stdept() {
		$("#stdept_main").hide();
		$("#new_stdept").show();
	}
	function hide_new_stdept() {
		$("#stdept_main").show();
		$("#new_stdept").hide();
	}
	
	//신규 부서 등록 페이지
	function get_new_user_reg_page() {
		var targetURL = '/user/deptmng/new_stdept_main.do';
		var data = {};
		$.ajax({
	        type: "POST",
	        url: targetURL,
	        data: data,
	        timeout: 60000,
	        success: function (data) {
				//console.log('success',data);
				console.log('success',targetURL);
				$("#stdept_main").hide();
				$("#new_stdept").html(data).show();
				bindForm();
				init();
	        },
	        error: function ( event, jqxhr, settings, thrownError ) {
				console.log('error',targetURL,jqxhr);
				$("#new_stdept").html('');
			}
		});
	}
	
	window.onpopstate = function(event) {
		console.log("location: " + document.location + "\nstate: " + event.state+"\nHistory_state: "+history.state+"\nhistory.length: "+history.length,event);
		if(event.state!=null){
			history.replaceState(null,document.title,document.URL);
			try {
				eval(event.state);
			} catch(e) {
				console.log("onpopstate",e);
			}
			history.pushState(null,document.title,document.URL);
		} else history.back();
	};
	
	
	var stdept_list = [];
	
	//부서 삭제 버튼
	function remove_stdept_before() {
		stdept_list = [];
		$("input[id*=ip_check_ip]").each(function (idx, element) {
			var this_element = $(element);
			var element_checked = $(element).prop("checked");
			//var ip_me = $(element).attr("ip_me") == "Y";
			//var ip_dept = $(element).attr("ip_dept") == "Y";
			//if(ip_me)
			if(element_checked) {
				var temp = {}
				temp.user_id = $(element).attr("user_id");
				temp.user_nm = $(element).attr("user_nm");
				temp.user_div = $("#select_user_div"+$(element).attr("index")).val();
				stdept_list.push(temp);
			}
		});
		if(stdept_list.length==0) {
			show_noti_modalPopup("","삭제할 부서를 선택해 주세요.");
		} else {
			//do_stuser_remove
			confirm_modalPopup("","선택한 부서를 삭제 하시겠습니까?",remove_add_stdept);
		}
	}

	//부서 삭제
	function remove_add_stdept() {
		stdept_list = [];
		$("input[id*=ip_check_ip]").each(function (idx, element) {
			var this_element = $(element);
			var element_checked = $(element).prop("checked");
			//var ip_me = $(element).attr("ip_me") == "Y";
			//var ip_dept = $(element).attr("ip_dept") == "Y";
			//if(ip_me)
			if(element_checked) {
				var temp = {}
				temp.dept_cd = $(element).attr("dept_cd");
				temp.dept_nm = $(element).attr("dept_nm");
				stdept_list.push(temp);
			}
		});
		if(stdept_list.length==0) {
			show_noti_modalPopup("","삭제할 부서를 선택해 주세요.");
			return;
		} else {
			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: "/user/deptmng/remove_stdept.do",
				data: JSON.stringify({"dept_list":stdept_list}),
				timeout: 60000,
				success: function (data) {
					console.log('success',data);
					if(data.ipChangeResultVoList == null) {
						show_noti_modalPopup("부서 삭제 실패","처리 결과가 없습니다.");
					} else {
						var result_msg = "";
						result_msg+="총 요청 수 : "+data.requestCnt+" 건<br/>";
						if(data.errorCnt>0) {
							result_msg+="성공 수: "+data.successCnt+" 건<br/>";
							result_msg+="실패 수 : "+data.errorCnt+" 건<br/>";
							data.ipChangeResultVoList.forEach(function (item, index, array) {
								if(item.success == false) {
									result_msg+=item.ip_addr+" => "+item.err_msg+"<br/>";
								}
							});
						}
						show_noti_modalPopup("부서 삭제 결과",result_msg,start_stdept);
						/* show_noti_modalPopup("부서 삭제 결과",result_msg,function(){
							get_stdept_list($("#stdept_list_form").find("input[name=page]").val());
						}); */
					}
				},
				error: function ( event, jqxhr, settings, thrownError ) {
					console.log('error',jqxhr);
					
				}
			});
		}
	}
	
	
	
	//부서 권한 수정 버튼
	function set_stdept_level_before() {
		stdept_list = [];
		$("input[id*=ip_check_ip]").each(function (idx, element) {
			var this_element = $(element);
			var element_checked = $(element).prop("checked");
			//var ip_me = $(element).attr("ip_me") == "Y";
			//var ip_dept = $(element).attr("ip_dept") == "Y";
			//if(ip_me)
			if(element_checked) {
				var temp = {}
				temp.user_id = $(element).attr("user_id");
				temp.user_nm = $(element).attr("user_nm");
				temp.user_div = $("#select_user_div"+$(element).attr("index")).val();
				stdept_list.push(temp);
			}
		});
		if(stdept_list.length==0) {
			show_noti_modalPopup("","권한 수정할 부서를 선택해 주세요.");
		} else {
			//do_stuser_remove
			confirm_modalPopup("","선택한 부서를 권한 수정 하시겠습니까?",set_stdept_level_stdept);
		}
	}

	//부서 권한 수정
	function set_stdept_level_stdept() {
		stdept_list = [];
		$("input[id*=ip_check_ip]").each(function (idx, element) {
			var this_element = $(element);
			var element_checked = $(element).prop("checked");
			//var ip_me = $(element).attr("ip_me") == "Y";
			//var ip_dept = $(element).attr("ip_dept") == "Y";
			//if(ip_me)
			if(element_checked) {
				var temp = {}
				temp.dept_cd = $(element).attr("dept_cd");
				temp.dept_nm = $(element).attr("dept_nm");
				temp.dept_lv_cd = $("#select_dept_lv_cd"+$(element).attr("index")).val();
				stdept_list.push(temp);
			}
		});
		if(stdept_list.length==0) {
			show_noti_modalPopup("","권한 수정할 부서를 선택해 주세요.");
			return;
		} else {
			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: "/user/deptmng/set_stdept_level.do",
				data: JSON.stringify({"dept_list":stdept_list}),
				timeout: 60000,
				success: function (data) {
					console.log('success',data);
					if(data.ipChangeResultVoList == null) {
						show_noti_modalPopup("부서 권한 수정 실패","처리 결과가 없습니다.");
					} else {
						var result_msg = "";
						result_msg+="총 요청 수 : "+data.requestCnt+" 건<br/>";
						if(data.errorCnt>0) {
							result_msg+="성공 수: "+data.successCnt+" 건<br/>";
							result_msg+="실패 수 : "+data.errorCnt+" 건<br/>";
							data.ipChangeResultVoList.forEach(function (item, index, array) {
								if(item.success == false) {
									result_msg+=item.ip_addr+" => "+item.err_msg+"<br/>";
								}
							});
						}
						//show_noti_modalPopup("부서 권한 수정 결과",result_msg,start_stdept);
						show_noti_modalPopup("부서 권한 수정 결과",result_msg,function(){
							get_stdept_list($("#stdept_list_form").find("input[name=page]").val());
						});
					}
				},
				error: function ( event, jqxhr, settings, thrownError ) {
					console.log('error',jqxhr);
					
				}
			});
		}
	}
	
</script>