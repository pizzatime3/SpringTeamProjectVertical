let bno = 0;
board_getboard();

function board_getboard(){
    $.ajax({
        url : "/board/getboard" ,
        success : function(getboard){
            bno = getboard.bno;
        console.table(getboard.b_ino)
            $("#btitle").val( getboard.btitle);
            $("#bcontent").val(getboard.bcontent);
            let html = "";
            for(let i=0; i<getboard.bimglist.length; i++){
              html +=
               '<img onclick="bimgdelete('+getboard.b_ino[i]+')" width="400" height="300" src="/files/board/'+getboard.bimglist[i]+'"> <br>';
           }
           $("#imgbox").html(html);
        }
    });
}

function bimgdelete(b_ino){
    $.ajax({
        url : "/board/bimgdelete",
        data : {"b_ino" : b_ino},
        method : "delete",
        success : function(re){
            if(re == true){
                alert("이미지가 삭제 되었습니다.");
                location.reload();
            }else{
                alert("이미지가 삭제 되지 않았습니다.(관리자 문의)");
            }
        }
    });

}


function board_update(){
        let form = $("#updateform")[0];
        let formdata = new FormData(form);
        $.ajax({
            url : "/board/update",
            data : formdata,
            method : "put",
            processData : false,
            contentType : false,
            success : function(re){
                if(re == true){
                    alert("게시물이 수정되었습니다.");
                    location.href = "/board/view/"+bno;
                }else{
                    alert("게시물이 수정되지 않았습니다.[관리자 문의]");
                    location.href = "/board/list";
                }
            }
        })
}