board_getboard();
reply_getreplylist();



function board_getboard(){
    let html = "";
    $.ajax({
        url : "/board/getboard" ,
        success : function(getboard){
        console.log(getboard.islike);
            html +=
                '<div class="container" id="viewbox"><div class="view_category">'+getboard.cname+'</div>'+
                '<h4><b>'+getboard.btitle+'</b></h4>';

            html +=
            '<div class="view_company">회사명</div>'+
            '<div class="row" id="view_info"><div class="col-md-2"><i class="bi bi-clock"></i>'+getboard.bindate+'</div>'+
            '<div class="col-md-1"><i class="bi bi-eye"></i>'+getboard.bview+'</div>'+ //조회수
            '<div class="col-md-1"><i class="bi bi-chat-dots"></i>'+getboard.rno+'</div></div>'+ //댓글수
            '<hr><div class="view_content">'+getboard.bcontent+'</div>'; //내용

               for(let i=0; i<getboard.bimglist.length; i++){
                       html +=
                        '<img width="50%" height="50%" src="/files/board/'+getboard.bimglist[i]+'"> <br>';
                }
                /*'<div class="view_company">회사명</div>'+*/
    //          '<div><i class="bi bi-clock-fill"></i>'+getboard.bmodate+'</div>'+ //수정시각
            if(getboard.islike == 0){
                 html +=
                '<div class="row"><div class="col-md-1"><button id="change" type="button" onclick="likesave('+getboard.bno+')"><i class="bi bi-suit-heart-fill" id="heart" style="padding: 0"> </i></button></div>'; // 빈하
                //'<div class="row"><div class="col-md-1"><button id="change" type="button" onclick="likesave('+getboard.bno+')"><i class="bi bi-suit-heart"></i></button></div>'+ // 채워진하트
            } else if (getboard.islike == 1){
                 html +=
                //'<div class="row"><div class="col-md-1"><button id="change" type="button" onclick="likesave('+getboard.bno+')"><i class="bi bi-suit-heart-fill" id="heart"></i></button></div>'+ // 빈하
                '<div class="row"><div class="col-md-1"><button id="change" type="button" onclick="likesave('+getboard.bno+')"><i class="bi bi-suit-heart" id="heart" style="padding: 0"></i></button></div>'; // 채워진하트
            }


                html +=
            '<div class="col-md-1"><i class="bi bi-chat-dots"></i>'+getboard.rno+'</div><br>'+ //댓글수
            '<div class="col-md-7"></div>'+
            '<div class="col-md-3"><button type="button" onclick="javascript:history.go(-1);">뒤로가기</button>'+
            '<button type="button"><a href="/board/update/'+getboard.bno+'" style="text-decoration: none; color: black;">수정</a></button>'+
            '<button onclick="board_delete('+getboard.bno+')">삭제</button></div></div><hr>';
        $("#boardview").html(html);
        }
    });
}

let getlikesave = [];


function likesave(bno){
    $.ajax({
        url : "/board/likesave",
        data : {"bno" : bno },
        method : "POST",
        success : function(re){
        getlikesave = re;
            if(re == true){
                alert("좋아요");
                $('#heart').replaceWith('<i class="bi bi-suit-heart-fill" id="heart"  style="padding: 0"></i>');
            }else{
                alert("좋아요 취소");
                $('#heart').replaceWith('<i class="bi bi-suit-heart" id="heart"  style="padding: 0"></i>');
            }

        }
    });
}

function reply_getreplylist(){

    let html = "";

    $.ajax({

        url : "/reply/getreplylist",

        success : function(getreplylist){

            console.log( getreplylist );
            for(let i = 0; i < getreplylist.length; i++){
                if(  getreplylist[i].rindex == 0 ){


                    html +=
                    '<div class="replyviewmain"><span>'+getreplylist[i].rcontent+'</span>';
                    for(let j = 0; j < getreplylist[i].rimglist.length; j++){
                        html +=
                            '<br><img width="40%" src="/files/reply/'+getreplylist[i].rimglist[j]+'">';
                        }
                    html += '</div>'+
                '<div class="chkreply"><i class="bi bi-clock"></i>'+getreplylist[i].rindate+''+
                '<button id="reply_update'+getreplylist[i].rno+'" value="'+getreplylist[i].rno+'"> 수정 </button></div>'+

                '<p class="btn2call"><button class="btn2" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample2" aria-expanded="false" aria-controls="collapseExample2">'+
                '답글 달기 </button></p>'+
                '<div class="collapse" id="collapseExample2"><div class="card card-body">'+
                '답글 작성하기<br><input type="text" class="rereplyconinput" name="rereplycontent" id="rereplycontent'+getreplylist[i].rno+'">'+
                '<button class="btn3" onclick="rereplywrite('+getreplylist[i].rno+')"> 작성 </button></div></div>';

                    for(let j = 0; j < getreplylist.length; j++){
                        if( getreplylist[i].rno == getreplylist[j].rindex  ){
                            html +=
                            '<div class="rerecontentdiv"><span>'+getreplylist[j].rcontent+'</span><br>'+
                            '<button id="rereply_update'+getreplylist[j].rno+'" value="'+getreplylist[j].rno+'"> 답글 수정 </button></div>';


                        }
                    }
                }
//                html += '<hr>';

            }
           $("#replyview").append(html);
        }
    });
}


function rereplywrite(  rno  ){
    //  1. 댓글번호는 인수로 받는다.
    // 2. 대댓글 내용을 가져온다. ~
    let rereplycontent =  $("#rereplycontent"+rno).val();
    // 3. 아작트로 비동기 통신를 한다. [ 댓글번호와 내용을 전달한다. ]
    $.ajax({
            url : "/reply/reresave" ,
            data : { "rno" : rno , "rereplycontent" : rereplycontent } ,
            method : "POST",
            success : function(re){
                alert("답글이 작성되었습니다.");
                location.reload();
            }
        });
    // 4. 응답을 받는다
}

function reply_save(){
    let form = $("#saveform")[0];
    let formdata = new FormData(form);
    console.log(form);
    $.ajax({
        url : "/reply/save" ,
        data : formdata ,
        method : "POST",
        processData : false,
        contentType : false,
        success : function(re){
            alert("댓글이 작성되었습니다.");
            location.reload();
        }
    });
}

function board_delete(bno){
    $.ajax({
        url : "/board/delete",
        data : {"bno" : bno} ,
        method : "delete",
        success : function(re){
            if(re == true){
                alert("게시물이 삭제 되었습니다.");
                location.href = "/board/list";
            }else{
                alert("게시물이 삭제되지 않았습니다.[관리자 문의]");
                location.href = "/board/list";
            }
        }
    });
}




/////////////////////////////////////////////////////////////////
//댓글 수정 - 삭제
function reply_update(){
    let form = $("#updateform")[0];
    let formdata = new FormData(form);
    $.ajax({
        url : "/reply/update",
        data : formdata,
        method : "put",
        processData : false,
        contentType : false,
        success : function(re){
            if(re == true){
                alert("댓글이 수정되었습니다.");
                location.href = "/board/list";
            }else{
                alert("댓글이 수정되지 않았습니다. [관리자 문의]");
                location.href = "/board/list";
            }
        }
    });
}

function reply_delete(rno){
alert(rno);
    $.ajax({
        url : "/reply/delete",
        data : {"rno" : rno},
        method : "delete",
        success : function(bno){
            if(bno != 0){
                alert("댓글이 삭제되었습니다.");
                location.href = "/board/view/"+bno;
            }else if(bno == 0){
                alert("댓글이 삭제되지 않았습니다.[관리자 문의]");
                location.href = "/board/view/"+bno;
            }else{
                alert("알 수 없는 행동입니다.");
            }
        }
    });
}

$(document).on('click', '#replyview .chkreply button', function(){
    $('#exampleModal').modal('show');
    let rno = $(this).val();
    let html = "";
    $.ajax({
        url : "/reply/getreply",
        data : {"rno" : $(this).val()},
        success : function(getreply) {
        console.log(getreply.rimglist[0]);
        for(let i = 0; i < getreply.rimglist.length; i++){
            html +=
            '<button type="button" value="'+getreply.r_ino[i]+'">'+getreply.rimglist[i]+'</button> <br>';
        }
            $("#replyimgbox").html(html);
            $("#rcontent").val(getreply.rcontent);
            $("#rno").val(rno);
        }
    })
})

$(document).on('click', '#exampleModal .modal-content .modal-body #updateform #replyimgbox button', function(){
let r_ino = 0;
r_ino = $(this).val();



    $.ajax({
        url : "/reply/rimgdelete",
        data : {"r_ino" : r_ino},
        method : "delete",
        success : function(re){
            if(re == true){
                alert("이미지가 삭제 되었습니다.");
                console.log($(this).html())
                console.log($(this).parent().html())

            }else{
                alert("이미지가 삭제되지 않았습니다.(관리자 문의)");
            }
        }
    });
    $(this).remove();
})


$(document).on('click', '.rerecontentdiv button', function(){
    $('#exampleModal').modal('show');
    let rno = $(this).val()
    $.ajax({
         url : "/reply/getreply",
        data : {"rno" : $(this).val()},
        success : function(getreply) {
            $("#rcontent").val(getreply.rcontent);
            $("#rno").val(rno)
        }
    })
})