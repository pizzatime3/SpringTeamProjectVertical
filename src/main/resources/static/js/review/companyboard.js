
////////////////////////게시글출력/////////////
$(document).ready(function (){
    let corpNm = $('#corpNm').html();
    $.ajax({
            url : "/review/getcorp",
            data : {"corpNm" : corpNm},
            success: function(corp){
                console.table(corp);
                $('#img').html('<img src="'+corp.data[0].img+'">' );//사진
                $('#homepage').html(corp.data[0].homepage);//홈페이지주소
                $('#base_address').html(corp.data[0].base_address);//기업기본주소
                $('#employees').html(corp.data[0].employees);//기업종업원수
                $('#establish_date').html(corp.data[0].establish_date);//기업설립일자
                $('#salary').html(corp.data[0].salary);//기업의 1인 평균 급여 금액
                $('.industry').html(corp.data[0].industry);
            }
        })

    $.ajax({
            url : "/board/companyboard",
            data : {"corpNm" : corpNm},
            success : function (companyboardlist){
            console.log(companyboardlist)
                    let html = ''
                for (let i = 0; i < companyboardlist.length; i++){
                    html +=
                        '<div id="companyboardjs">'+
                            '<ul>'+

                              '<li id="companyboardtitle"><span>'+
                                '<a style="color: black; text-decoration : none;" href="/board/view/'+companyboardlist[i].bno+'">'+companyboardlist[i].btitle+'</a>'+
                              '</span></li>'+
                              '<li id="companyboardcontent"><span>'+companyboardlist[i].bcontent+'</span></li>'+
                              '<li class="companyboardjsli"><i class="bi bi-hearts"></i>&nbsp<span>'+companyboardlist[i].blike+'</span>&nbsp&nbsp</li>'+
                              '<li class="companyboardjsli"><i class="bi bi-eye"></i>&nbsp<span>'+companyboardlist[i].bview+'</span>&nbsp&nbsp</li>'+
                              '<li class="companyboardjsli"><i class="fa-solid fa-comment"></i>&nbsp<span>'+companyboardlist[i].replycount+'</span>&nbsp&nbsp</li>'+
                              '<li class="companyboardjsli">&nbsp<span>'+companyboardlist[i].bindate+'</span>&nbsp&nbsp</li>'+
                            '</ul>' +
                        '</div>';

                }

                $('#companyboard').html(html);
            }
        });



})
////////////////////////게시글출력 END/////////////