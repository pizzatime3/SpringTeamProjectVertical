
myinfo();

// 내정보페이지에서 내정보(닉네임) 불러오기
function myinfo(){
    $.ajax({
        url : "/member/getinfo",
        success : function(info){
            $("#nickname").html(info.mname);
        } // success end
    }); // ajax end

} // 내정보 end

// 회원탈퇴 버튼 클릭시
function memberdelete(){

    $.ajax({
        url : "/member/delete",
        method : "DELETE",
        success : function (result){
            if(result){
                alert("회원탈퇴 성공");
                location.href = "/"; //////////////////////// 메인페이지로 이동
            }else{
                alert("회원탈퇴 실패");
            } // else end
        } // success end
    }); // ajax end

} // 회원탈퇴 end
