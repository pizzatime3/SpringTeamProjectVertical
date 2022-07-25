
// 회원이메일 중복체크
function emailcheck(){
    let memail = $("#memail").val();
    let memailaddress = $("#memailaddress").val();

    if(memail == "" || memailaddress == ""){ // 이메일, 주소 둘 중 하나라도 입력을 안하면
        $("#emailcheck").html("이메일주소 모두 입력해주세요");
    }else{ // 이메일, 주소 둘 다 입력하면
        // 이메일 중복체크
        let email = memail + "@" + memailaddress;

        $.ajax({
            url : "/member/emailcheck",
            data : {"email" : email},
            success : function(result){
            alert(result);
                if(result==1){
                    $("#emailcheck").html("사용중인 이메일입니다.");
                }else if(result==2){
                     $("#emailcheck").html("가입불가능한 회사입니다.[관리자에게 문의]");
                }else{
                    $("#emailcheck").html("사용가능한 이메일입니다.");
                    $("#emailhidden").val(email);
                    $("#sendemail").html(email+"으로 인증메일이 발송되었습니다.");
                    $("#step1").css("display","none"); // 이메일 입력란 숨기기
                    $("#step2").css("display","block"); // 인증번호 입력란 보이기
                } // else e3nd
            } // success end
        }); // ajax end

    } // else end
} // 회원이메일 중복체크 end

index = 0;
// 이메일 인증번호 확인
function randomcheck(){

    $.ajax({
           url : "/member/randomcheck",
           data : {"randomnum" : $("#randomnum").val(), "email" : $("#emailhidden").val()},
           success : function(result){
                if(result){
                    alert("인증번호 확인완료");
                    $("#step2").css("display","none"); // 인증번호 입력란 숨기기
                    $("#pwinput").css("display","block");

                }else{
                    alert("인증번호 불일치로 이메일 인증이 실패하였습니다.["+(index+1)+"회/5회]");
                    index++;
                    if(index >= 5){
                        alert("인증번호 5회이상 불일치로 회원가입이 제한되었습니다.[고객센터에 문의]");
                        location.href ="/member/login" /////////////////////////// 메인페이지로 이동하기(추후 수정)
                    } // if end
                } // else end
           } // success end
    }); // ajax end
} // 이메일 인증번호 확인 end

$("#mpassword").keyup( function(){

        let mpassword = $("#mpassword").val();


        // 비밀번호 정규표현식(영문자1개, 숫자1개, 특수문자 1개를 포함하여 8자 이상)
        let passwordj = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*?&]).{8,}$/;
            //"^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,}";

          if(!passwordj.test(mpassword)){
                 $("#passwordcheck").html("※영문자1개, 숫자1개, 특수문자 1개를 포함하여 8자 이상이어야 합니다.");
          }else{
               $("#passwordcheck").html("");
               $("#mpassword2").keyup( function(){
                    let mpassword2 = $("#mpassword2").val();
                    if(mpassword == mpassword2){
                        $("#passwordcheck").html("비밀번호가 일치합니다.");
                    }else{
                        $("#passwordcheck").html("비밀번호가 서로 다릅니다.");
                    } // else end
                }); // password2 keyup end
          } // else end

}); // 비밀번호입력란  keyup end

// 회원가입 버튼 클릭
function signup(){
    let mpassword = $("#mpassword").val();

    $.ajax({
        url : "/member/signup",
        method : "POST",
        data : {"memail" : $("#emailhidden").val(), "mpassword" : mpassword},
        success: function(result){
            if(result){
                alert("회원가입 성공");
                location.href = "/member/login";
            }else{
                 alert("회원가입실패[관리자에게 문의]");
            } // else end
        } // success end
    }); // ajax end
} // 회원가입 end

getcompanylist();
// 회사이메일 입력(1단계) 회사목록 출력
function getcompanylist(){
    $.ajax({
            url : "/company/companylist",
            success : function(companylist){
                console.log(companylist);

                let html =
                            '<div class="carousel-inner">'+
                            '    <div class="carousel-item active">'+
                            '       <div class="row" id="companybox">'+
                            '           <div class="col-md-4"><img src="'+companylist[0].comlogo+'" class="d-block w-100" width="100%"></div>'+
                            '           <div class="col-md-8" id="comname"><span>'+companylist[0].comname+'</span></div>'+
                            '       </div>'+
                            '    </div>';
                for(let i = 1; i < 10; i++){
                    html +=
                            '    <div class="carousel-item">'+
                            '       <div class="row" id="companybox">'+
                            '           <div class="col-md-4"><img src="'+companylist[i].comlogo+'" class="d-block w-100" width="100%"></div>'+
                            '           <div class="col-md-8" id="comname"><span>'+companylist[i].comname+'</span></div>'+
                            '       </div>'+
                            '    </div>';
                } // for end
                html +=  ' </div>';
                $("#companycarousel").html(html);
            } // success end
    }); // ajax end
}

