
myinfo();

// 내정보수정페이지에서 내정보(닉네임, 회사명) 불러오기
function myinfo(){
    $.ajax({
        url : "/member/getinfo",
        success : function(info){
            $('input[name=mname]').attr('value',info.mname);
            $('input[name=mcom]').attr('value',info.mcom);
        } // success end
    }); // ajax end

} // 내정보 end

// 닉네임 변경
$("#mname").keyup(function(key){

    $("#mnamecheck").html("닉네임 중복 확인 중입니다.");

    // 닉네임 글자수 체크
    let mnamelength = $("#mname").val().length;

    if(mnamelength == 0 || mnamelength == 10){
        $("#mnamecheck").html("닉네임은 10자 이내로 입력할 수 있습니다.");
    }else{ // 글자수가 10자 이내이면
        // 닉네임 수정 후(엔터)
        if (key.keyCode == 13) {
            let mname = $("#mname").val();
            // 닉네임 정규표현식
            let mnamej = /^[A-Za-z0-9가-힣]{0,10}$/;
            if(mnamej.test(mname)){ // 유효성 검사 통과
                $.ajax({
                    url : "/member/mnamecheck",
                    data : {"mname" : mname},
                    async: false,  // ajax의 결과를 기다리지 않고, 바로 하단의 코드가 실행되기때문에 동기처리
                    success : function(result){
                        if(result){ // 닉네임 중복이 없을경우
                            $("#mnamecheck").html("사용 가능한 닉네임입니다.");
                            // 이제수정하기
                            $.ajax({
                                url : "/member/update",
                                method : "PUT",
                                data : {"mname" : mname},
                                async: false,  // ajax의 결과를 기다리지 않고, 바로 하단의 코드가 실행되기때문에 동기처리
                                success : function(result){
                                    if(result){
                                        alert("닉네임 수정이 완료되었습니다.");
                                        location.href = "/member/info"; ////////////////////// 추후 메인으로 이동하기
                                    }else{
                                        alert("로그인 후 이용 가능합니다.");
                                    } // else end
                                } // success end
                            }); // ajax end
                        }else{
                            $("#mnamecheck").html("사용 불가능한 닉네임입니다.");
                        } // else end
                    } // success end
                }); // ajax end
            }else{
                $("#mnamecheck").html("닉네임을 형식에 맞게 입력해주세요");
            } // else end
        } // 닉네임입력 후 엔터 end
    } // else end
}); // 닉네임 변경 end

// 현재 비밀번호 입력 -> 비밀번호 일치여부 확인
$("#oldpw").keyup(function(key){
    $("#oldpwalert").html("비밀번호를 올바르게 입력해주세요.");
    if (key.keyCode == 13) { // 엔터를 누르면

        let oldpw = $("#oldpw").val();
        // 현재비밀번호 맞는지 확인완료
        $.ajax({
            url : "/member/passwordcheck",
            method : "POST",
            data : {"mpassword" : oldpw},
            async: false,  // ajax의 결과를 기다리지 않고, 바로 하단의 코드가 실행되기때문에 동기처리
            success : function(result){
                   if(result){
                        $("#oldpwalert").html("비밀번호가 일치합니다.");
                        alert("비밀번호 일치확인");
                        // 새 비밀번호, 새 비밀번호 확인 input 활성화
                        $("#newpw").attr("disabled", false);
                        $("#newpwcheck").attr("disabled", false);
                    }else{
                        alert("비밀번호 불일치");
                    } // else end
            } // success end
        }); // ajax end
    } // 엔터누르면 end
}); // 비밀번호 일치여부 확인 end


// 새 비밀번호 입력 시
$("#newpw").keyup(function(){
    $("#newpwalert").html("영문+숫자+특수문자 조합 8~32자로 입력해주세요.");

    let newpw = $("#newpw").val();
    // 비밀번호 정규표현식(영문자1개, 숫자1개, 특수문자 1개를 포함하여 8자 이상)
    let passwordj = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*?&]).{8,}$/;
    if(passwordj.test(newpw)){ // 정규표현식 조건에 충족하면
        $("#newpwalert").html("사용가능한 비밀번호입니다.");
    }
    // 새 비밀번호 확인
    $("#newpwcheck").keyup(function(key){
        $("#pwcheckalert").html("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
        if (key.keyCode == 13) { // 엔터를 누르면
            let newpwcheck = $("#newpwcheck").val();
            if(newpw == newpwcheck){ // 비밀번호가 서로 일치하면
               // 비밀번호 변경~
                $.ajax({
                    url : "/member/pwupdate",
                    method : "POST",
                    data : {"newpassword" : newpw},
                    async: false,  // ajax의 결과를 기다리지 않고, 바로 하단의 코드가 실행되기때문에 동기처리
                    success : function(result){
                        if(result){
                            alert("비밀번호 변경완료");
                            location.href = "/member/info"; ////////////////////// 추후 메인으로 이동하기
                        }
                    } // success end
                }); // ajax end
            } // if end
        } // if end
    }); // 새 비밀번호 확인입력 end
});