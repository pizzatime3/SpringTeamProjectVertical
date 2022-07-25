mainboardlist();

function mainboardlist(){

    $.ajax({
        url : "/board/mainboardlist",
        success : function(object){
        console.log(object);

        let html = "";
        for(let i = 0; i < object.length; i++){
            if(i % 2 == 0){
                html +=
                        ' <div class="row">'+
                        '    <div class="col-md-6 categoryboardbox">'+
                        '       <span>'+object[i].cname+'</span>'+
                        '       <a class="space" href="/board/list/'+object[i].cno+'"> 더보기 > </a>'+
                        '       <div class="titleborder"></div>'+
                        '       <div id="categoryboardlist" class="row">';
                for(let j = 0; j < 5; j++){ ///////////////////////////////// j 개수 수정하기...
                   html +=     '    <div class="col-md-9"><a href="/board/view/'+object[i].boardlist[j].bno+'">'+object[i].boardlist[j].btitle+'</a></div>'+
                               '    <div class="col-md-3 bviewcount"><i class="bi bi-eye"></i>'+object[i].boardlist[j].bview+'</div>';
               } // for end
               html +=  '       </div>'+
                        '    </div>';
            }else{
                html +=
                        '    <div class="col-md-6 categoryboardbox">'+
                        '       <span>'+object[i].cname+'</span>'+
                        '       <a class="space" href="/board/list/'+object[i].cno+'"> 더보기 > </a>'+
                        '        <div class="titleborder"></div>'+
                        '       <div id="categoryboardlist" class="row">';
                for(let j = 0; j < 5; j++){ ///////////////////////////////// j 개수 수정하기...
                   html +=     '    <div class="col-md-9"><a href="/board/view/'+object[i].boardlist[j].bno+'">'+object[i].boardlist[j].btitle+'</a></div>'+
                               '    <div class="col-md-3 bviewcount"><i class="bi bi-eye"></i>'+object[i].boardlist[j].bview+'</div>';
               } // for end
               html +=  '       </div>'+
                        '    </div>'+
                        ' </div>';
            } // else end
        } // for end
        $("#boardlist").html(html);
        } // success end
    }); // ajax end
}

topicbest();
// 토픽베스트
function topicbest(){
    $.ajax({
        url : "/board/topicbest",
        success : function(object){
            console.log(object);

            let html = "";
            for(let i = 0; i < object.length; i++){
                html +=
                        '<div class="col-md-2" id="topiccategory"><a href="/board/view/'+object[i].bno+'"><span>'+object[i].cname+'</span></a></div>'+
                        '<div class="col-md-7"><a href="/board/view/'+object[i].bno+'">'+object[i].btitle+'</a></div>'+
                        '<div class="col-md-1 offset-1" id="likeicon"><i class="bi bi-hearts"></i>'+object[i].blike+'</div>'+
                        '<div class="col-md-1" id="replyicon"><i class="fa-solid fa-comment"></i>'+object[i].replycount+'</div>';
            } // for end
            $("#topiclist").html(html);
        } // success end
    }); // ajax end
}

getcompanylist();
// 회사출력
function getcompanylist(){
    $.ajax({
        url : "/company/companylist",
        success : function(companylist){
            console.log(companylist);

            let html = "";
            for(let i = 0; i < 10; i++){
                html +=
                   ' <div class="row">'+
                   '    <div class="col-md-3"><img src="'+companylist[i].comlogo+'" width="100%"></div>'+
                   '    <div class="col-md-9"><span>'+companylist[i].comname+'</span></div>'+
                   '</div>';
            } // for end
            $("#companylist").html(html);
        } // success end
    }); // ajax end
}

$(document).on('click', '#search' ,function(){
  $(this).parent().parent().find('a').attr('href','mainsearch/'+$('#searchinput1').val());
})