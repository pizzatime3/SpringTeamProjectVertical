$(document).ready(function (){
    $.ajax({
        url: '/board/getcategorylist',
        success : function(clist){
            let html = '';
//        console.table(clist);
            for (let i = 0; i < clist.length; i++){
                html += '<option value="' + clist[i].cno+'">'+ clist[i].cname+'</option>';
            }
            $('#category').html(html);
        }
    })
})

function board_save(){
    let form = $("#saveform")[0];
    let formdata = new FormData(form);
    formdata.append("cno", $('#category').val())
    $.ajax({
        url : "/board/save",
        data : formdata,
        method : "POST",
        processData : false,
        contentType : false,
        success : function(re){
            if(re == true){
                alert("게시물이 작성 되었습니다.");
                location.href = "/board/list";
            }else{
                alert("게시물이 작성 되지 않았습니다.[관리자 문의]")
                location.href = "/board/list";
            }
        }
    });
}