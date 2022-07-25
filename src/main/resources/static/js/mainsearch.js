
companysearch();
// 회사 검색
function companysearch(){
    $.ajax({
        url : "/company/getcompany",
        data : {"comname" : $('#value1').val()},
        success : function(list){
            $("#logo").html('<img src="'+list.comlogo+'"width="60px">');
            $("#comname").html(list.comname);
        } // success end
    }); // ajax end

    $.ajax({
        url : "/evaluation/list",
        data : {"firm" : $('#value1').val(), "title" : "전체 직군", "status" : "전체재직상태", "option" : "최신순"},
        success : function(companylist){
        console.log(companylist);

            if(companylist == ""){ // 회사관련 검색이 아닐때

            }else{
                $("#comtitle").css("display","block");
                $("#companysearchbox").css("display","block");

                // 총점
                let total_star = 0;
                 for (let i = 0; i < companylist.length; i++){
                    total_star += companylist[i].totalstar
                 }
                 total_star = total_star / companylist.length;
                $("#totalstar").html('<label style="color : #ffcc00;">&#9733;</label>'+total_star.toFixed(1));


                // 첫번째 리뷰 별점
                for (let i = 1; i <= companylist[0].totalstar; i++){
                    $('.star #star'+i).css('color','#ffcc00');
                }
                // 첫번째 리뷰 내용
                $("#reviewtotal").html(companylist[0].totalstar.toFixed(1));
                $("#eoccupational_group").html(companylist[0].eoccupational_group);
                $("#oneline").html('"'+companylist[0].oneline+'"');
                $("#cons").html(companylist[0].cons);
            } // else end
        } // success end
    }); // ajax end

} // 회사검색 end








search();
function search(){
    /////////////////////////////////////////////////////////// value 값으로 가져오기
//    let corpNm = document.getElementById("search_corp").value;
    $.ajax({
        url : "/board/searchlist",
        data : {"value" : $('#value1').val()},
        success : function(boardlist){
            console.log(boardlist);

            let html = "";
            for(let i = 0; i < boardlist.length; i++){
                if(i % 2 == 0){
                    html +=
                            '<div class="row" id="searchrow">'+
                            '    <div class="col-md-6" id="searchlist1">'+
                            '        <div id="searchcname"><a href="/board/list/'+boardlist[i].cno+'">'+boardlist[i].cname+'</a></div>'+
                            '        <div id="searchbtitle"><a href="/board/view/'+boardlist[i].bno+'">'+boardlist[i].btitle+'</a></div>'+
                            '        <div id="searchbcontent"><a href="/board/view/'+boardlist[i].bno+'">'+boardlist[i].bcontent+'</a></div>'+
                            '        <div id="searchinfo"><a href="/board/view/'+boardlist[i].bno+'"><span>'+boardlist[i].mcom+'</span> · <span>'+boardlist[i].mname+'</span></a></div>'+
                            '        <div class="row" id="searchboard">'+
                            '           <div class="col-md-2"><a href="/board/view/'+boardlist[i].bno+'"><i class="bi bi-eye"></i>'+boardlist[i].bview+'</a></div>'+
                            '           <div class="col-md-2"><a href="/board/view/'+boardlist[i].bno+'"><i class="bi bi-hearts"></i>'+boardlist[i].blike+'</a></div>'+
                            '           <div class="col-md-2"><a href="/board/view/'+boardlist[i].bno+'"><i class="fa-solid fa-comment"></i>'+boardlist[i].replycount+'</a></div>'+
                            '           <div class="col-md-2 offset-4" id="searchbdate"><a href="/board/view/'+boardlist[i].bno+'">'+boardlist[i].bdate+'</a></div>'+
                            '        </div>'+
                            '    </div>';
                }else{
                    html +=
                            '    <div class="col-md-6" id="searchlist">'+
                            '        <div id="searchcname"><a href="/board/list/'+boardlist[i].cno+'">'+boardlist[i].cname+'</a></div>'+
                            '        <div id="searchbtitle"><a href="/board/view/'+boardlist[i].bno+'">'+boardlist[i].btitle+'</a></div>'+
                            '        <div id="searchbcontent"><a href="/board/view/'+boardlist[i].bno+'">'+boardlist[i].bcontent+'</a></div>'+
                            '        <div id="searchinfo"><a href="/board/view/'+boardlist[i].bno+'"><span>'+boardlist[i].mcom+'</span> · <span>'+boardlist[i].mname+'</span></a></div>'+
                            '        <div class="row" id="searchboard">'+
                            '           <div class="col-md-2"><a href="/board/view/'+boardlist[i].bno+'"><i class="bi bi-eye"></i>'+boardlist[i].bview+'</a></div>'+
                            '           <div class="col-md-2"><a href="/board/view/'+boardlist[i].bno+'"><i class="bi bi-hearts"></i>'+boardlist[i].blike+'</a></div>'+
                            '           <div class="col-md-2"><a href="/board/view/'+boardlist[i].bno+'"><i class="fa-solid fa-comment"></i>'+boardlist[i].replycount+'</a></div>'+
                            '           <div class="col-md-2 offset-4" id="searchbdate"><a href="/board/view/'+boardlist[i].bno+'">'+boardlist[i].bdate+'</a></div>'+
                            '        </div>'+
                            '    </div>'+
                            '</div>';
                } // else end
            } // for end
            $("#boardsearchlist").html(html);
        } // success end
    }); // ajax end
}