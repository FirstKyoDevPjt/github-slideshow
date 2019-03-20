<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div id="stuser_main">
	<div class="main-wrap">
		<nav>
			<ol class="path">
				<li class="path-item"><i class="fas fa-home">home</i></li>
				<li class="path-item current-page">사용자 관리</li>
			</ol>
		</nav>
	</div>

	<form:form id="stuser_main" name="stuser_main"></form:form>

	<ul class="titarticle">
		<li><span class="titlebar"></span></li>
		<li><h2>사용자 관리</h2></li>
	</ul>
	
	<div id="stuser_list"></div>
	
</div>

<div id="stuser_detail"></div>

<script>
	$(document).ready(function(){
		start_stuser();
	});
	
	function start_stuser() {
		//document.stuser_main
		get_stuser_list(1,"stuser_main");
	}
	
	function get_stuser_list(pageNum, targetFormNm, orderby_nm, desc) {
		var orglist_formNm = "stuser_list_form";
		var addparam = "";
		if(pageNum==null)pageNum=1;
		if(targetFormNm == null) {
			targetFormNm = orglist_formNm;
			var formPerPageCnt = $("#"+targetFormNm+">[name=perPageCnt]").val();
			var labelPerPageCnt = $('#get_stuser_list_perPageCnt').val();
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
		var targetURL = '/user/usermng/stuser_list.do';
		$.ajax({
	        type: "POST",
	        url: targetURL,
	        data: targetForm.serialize()+addparam,
	        timeout: 60000,
	        success: function (data) {
				//console.log('success',data);
				console.log('success',targetURL);
				$("#stuser_list").html(data);
				bindForm();
				init();
	        },
	        error: function ( event, jqxhr, settings, thrownError ) {
				console.log('error',targetURL,jqxhr);
				$("#stuser_list").html('');
			}
		});
		
	}//get_stuser_list() end
	
	
	function stuser_list_search() {
		var search_value = $("#stuser_list_search_value").val().trim();
		$("#stuser_list_form").find("input[search_id]").val('');
		$("#stuser_list_form").find("input[search_id=search_value]").val(search_value);
		get_stuser_list(1);
	}
	
	//stuser_detail
	function show_stuser_detail() {
		$("#stuser_main").hide();
		$("#stuser_detail").show();
	}
	function hide_stuser_detail() {
		$("#stuser_main").show();
		$("#stuser_detail").hide();
	}
	
	//신규 사용자 버튼
	function get_new_user_reg_page() {
		var targetURL = '/user/usermng/new_user_reg_page.do';
		var data = {};
		$.ajax({
	        type: "POST",
	        url: targetURL,
	        data: data,
	        timeout: 60000,
	        success: function (data) {
				//console.log('success',data);
				console.log('success',targetURL);
				$("#stuser_main").hide();
				$("#stuser_detail").html(data).show();
				bindForm();
				init();
	        },
	        error: function ( event, jqxhr, settings, thrownError ) {
				console.log('error',targetURL,jqxhr);
				$("#stuser_detail").html('');
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
	
	
	var stuser_list = [];
	
	
	//사용자 삭제 버튼
	function stuser_remove_before() {
		stuser_list = [];
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
           		stuser_list.push(temp);
            }
        });
		if(stuser_list.length==0) {
			show_noti_modalPopup("","삭제 할 사용자를 선택해 주세요.");
		} else {
			//do_stuser_remove
			confirm_modalPopup("","삭제 하시겠습니까?",do_stuser_remove);
		}
	}
	
	function do_stuser_remove() {
		stuser_list = [];
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
           		stuser_list.push(temp);
            }
        });
		if(stuser_list.length==0) {
			show_noti_modalPopup("","삭제 할 사용자를 선택해 주세요.");
			return;
		} else {
			$.ajax({
		        type: "POST",
		        contentType: "application/json",
		        url: "/user/usermng/remove_user.do",
		        data: JSON.stringify({"user_list":stuser_list}),
		        timeout: 60000,
		        success: function (data) {
					console.log('success',data);
					if(data.ipChangeResultVoList == null) {
						show_noti_modalPopup("삭제 실패","처리 결과가 없습니다.");
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
						//show_noti_modalPopup("삭제 처리 결과",result_msg,start_stuser);
						show_noti_modalPopup("삭제 처리 결과",result_msg,start_stuser);
					}
		        },
		        error: function ( event, jqxhr, settings, thrownError ) {
					console.log('error',jqxhr);
					
				}
			});
		}
	}
	
	
	//사용자 권한 수정 버튼
	function stuser_set_user_div_before() {
		stuser_list = [];
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
           		stuser_list.push(temp);
            }
        });
		if(stuser_list.length==0) {
			show_noti_modalPopup("","권한 수정 할 사용자를 선택해 주세요.");
		} else {
			//do_stuser_remove
			confirm_modalPopup("","권한 수정을 하시겠습니까?",do_set_user_div_div);
		}
	}
	
	function do_set_user_div_div() {
		stuser_list = [];
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
           		stuser_list.push(temp);
            }
        });
		if(stuser_list.length==0) {
			show_noti_modalPopup("","권한 수정 할 사용자를 선택해 주세요.");
			return;
		} else {
			$.ajax({
		        type: "POST",
		        contentType: "application/json",
		        url: "/user/usermng/set_user_div.do",
		        data: JSON.stringify({"user_list":stuser_list}),
		        timeout: 60000,
		        success: function (data) {
					console.log('success',data);
					if(data.ipChangeResultVoList == null) {
						show_noti_modalPopup("권한 수정 실패","처리 결과가 없습니다.");
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
						//show_noti_modalPopup("권한 수정 결과",result_msg,start_stuser);
						show_noti_modalPopup("권한 수정 결과",result_msg,function(){
							get_stuser_list($("#stuser_list_form").find("input[name=page]").val());
						});
					}
		        },
		        error: function ( event, jqxhr, settings, thrownError ) {
					console.log('error',jqxhr);
					
				}
			});
		}
	}
	
	//사용자 비밀번호 초기화 버튼
	function stuser_reset_pwd_before() {
		stuser_list = [];
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
           		temp.pwd_reset_type = $("#select_pwd_reset_type"+$(element).attr("index")).val();
           		stuser_list.push(temp);
            }
        });
		if(stuser_list.length==0) {
			show_noti_modalPopup("","비밀번호를 초기화 할 사용자를 선택해 주세요.");
		} else {
			//do_stuser_remove
			confirm_modalPopup("","비밀번호를 초기화 하시겠습니까?",do_set_reset_pwd);
		}
	}
	
	function do_set_reset_pwd() {
		stuser_list = [];
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
           		temp.pwd_reset_type = $("#select_pwd_reset_type"+$(element).attr("index")).val();
           		stuser_list.push(temp);
            }
        });
		if(stuser_list.length==0) {
			show_noti_modalPopup("","비밀번호를 초기화 할 사용자를 선택해 주세요.");
			return;
		} else {
			$.ajax({
		        type: "POST",
		        contentType: "application/json",
		        url: "/user/usermng/set_reset_pwd.do",
		        data: JSON.stringify({"user_list":stuser_list}),
		        timeout: 60000,
		        success: function (data) {
					console.log('success',data);
					if(data.ipChangeResultVoList == null) {
						show_noti_modalPopup("비밀번호 초기화 실패","처리 결과가 없습니다.");
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
						//show_noti_modalPopup("비밀번호 초기화 결과",result_msg,start_stuser);
						show_noti_modalPopup("비밀번호 초기화 결과",result_msg,function(){
							get_stuser_list($("#stuser_list_form").find("input[name=page]").val());
						});
					}
		        },
		        error: function ( event, jqxhr, settings, thrownError ) {
					console.log('error',jqxhr);
					
				}
			});
		}
	}
	
	
	//사용자 수정 버튼
	function get_user_modify_page() {
		stuser_list = [];
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
           		stuser_list.push(temp);
            }
        });
		if(stuser_list.length==0) {
			show_noti_modalPopup("","수정 할 사용자를 선택해 주세요.");
		} else if(stuser_list.length>1) {
			show_noti_modalPopup("","수정 할 사용자는 하나만 선택해 주세요.");
		} else {
			var targetURL = '/user/usermng/user_modify_page.do';
			var data = {};
			data.user_id=stuser_list[0].user_id;
			data.user_nm=stuser_list[0].user_nm;
			$.ajax({
		        type: "POST",
		        url: targetURL,
		        data: data,
		        timeout: 60000,
		        success: function (data) {
					//console.log('success',data);
		        	console.log('success',targetURL);
					$("#stuser_main").hide();
					$("#stuser_detail").html(data).show();
					bindForm();
					init();
		        },
		        error: function ( event, jqxhr, settings, thrownError ) {
					console.log('error',targetURL,jqxhr);
					$("#stuser_detail").html('');
				}
			});
		}
	}
</script>
