
getmyboard();

function getmyboard(){
    $.ajax({
        url : "/member/getmyboard",
        success : function(myboard){
            let html = "";
            if(myboard == ""){
                html +=
                '            <div id="nocontentbox">'+
                '            <div class="col-md-2" id="iconbox"><img src="/img/member-myboard2.png" width="50px;"></div>'+
                '            <div>아직 작성한 글이 없습니다.</div>'+
                '            </div>';
            }else{

                for(let i = 0; i < myboard.length; i++){
                    html +=
                            '<div class="row" id="contentbox"> '+
                            '            <div class="col-md-2"><img src="/img/member-myboard.png" width="50px;"></div>'+
                            '            <div class="col-md-10">'+
                            '                <div>'+myboard[i].btitle+'</div>'+
                            '                <div>회원님이 "'+myboard[i].cname+'"에 게시물을 등록했습니다.</div>'+
                            '                <div>'+myboard[i].bdate+'</div>'+
                            '            </div>'+
                            '</div>';
                } // for end
            } // else end

            $("#myboard").html(html);
            $("#myboard").scrollTop($("#myboard")[0].scrollHeight);
        } // success end
    }); // ajax end
}
