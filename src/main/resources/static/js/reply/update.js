reply_getreplylist();

function reply_getreplylist(){
    let rno = $('#rno').val();
    console.log(rno)
    $.ajax({
        url : "/reply/getreply",
        data : {"rno" : rno},
        success : function(getreply) {
            $("#rcontent").val(getreply.rcontent);
            let html = "";

            for(let i = 0; i < getreply.rimglist.length; i++){
                html +=
                '<img width="400" height="300" src="/files/reply/'+ getreply.rimglist[i]+'"> <br>';
            }
            html +=
            '<div class="delbackbtn"><button type="button" onclick="reply_delete('+rno+')">댓글 삭제</button>'+
            '<button><a href="/board/view/'+rno+'">이전으로</a></button></div>';
            $("#imgbox").html(html);
        }
    });
}

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