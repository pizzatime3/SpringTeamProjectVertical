////////////////////////기업이름클릭시 뿌려줄정보//////////////
$(document).ready(function(){
    //let corpNm = $('#corpNm').val();//input 일때 .val
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

//            let html1 = "";
//            let html2 = "";
//            let html3 = "";
//            let html4 = "";
////
//            for(let i=0; i<2; i++){
//                html1 += '<span>'+corp.data[0].infopress[i]+'</span>';
//                html2 += '<span>'+corp.data[0].newstit[i]+'</span>';
//                html3 += '<span>'+corp.data[0].dscwrap[i]+'</span>';
//                html4 += '<img src="'+corp.data[0].newsimgnewsimg[i]+'">';
//            }
//
////            $("#infopress").html(corp.data[0].infopress);
////            $("#newstit").html(corp.data[0].newstit);
////            $("#dscwrap").html(corp.data[0].dscwrap);
//            $("#infopress").html(html1);
//            $("#newstit").html(html2);
//            $("#dscwrap").html(html3);
//            $("#newsimgnewsimg").html(html4);


            let html1 = "";

            for(let i=0; i<2; i++){
                html1 +=
                '<div class="article">'+
                '   <div class="companyinfo">'+
                '      <div class="img"><img src="'+corp.data[0].img+'"></div>'+
                '      <div class="company_name">' +
                '          <strong>'+corpNm+'</strong>' +
                '          <span>'+ corp.data[0].infopress[i] +'</span>' +
                '       </div>'+
                '   </div>' +
                '   <h4 class="summary">'+corp.data[0].dscwrap[i]+'</h4>'+
                '   <div class="link_area">' +
                '       <div class="fixed"><a href="' + corp.data[0].newstit_a[i] + '"><img id="newsimg" src="'+corp.data[0].newsimgnewsimg[i]+'"></a></div>'+
                '       <div class="desc"><p>' + corp.data[0].newstit[i] + '</p><p class="from">' + corp.data[0].infopress[i] + '</p></div>'+
                '   </div>'+

                '</div>';
            }

            $("#viewnews").html(html1);


        }
    })


    $.ajax({
        url : "/evaluation/list",
        data : {"firm" : corpNm, "title" : "전체 직군", "status" : "전체재직상태", "option" : "최신순"},
        success : function (list){
            $('#eno').val(list[0].eno);
            let total_star = 0;
            let career_star = 0;
            let balance_star = 0;
            let salary_star = 0;
            let culture_star = 0;
            let management_star = 0;
            let total_star_remainder;
            $('.review_item_inner .rating > #num').html(list[0].totalstar.toFixed(1));
            for (let i = 1; i <= list[0].totalstar; i++){
                $('.review_item_inner .rating .star #star'+i).css('color','#fc0')
            }
            $('.review_item_inner .one_line').html('"'+list[0].oneline+'"');
            let date = list[0].createdate.split("T");
            $('.review_item_inner .auth').html(list[0].ewriter + '&nbsp;' + list[0].eoccupational_group + '&nbsp;' + date[0]);
            $('.review_item_inner .paragraph .pros').html(list[0].pros)
            $('.review_item_inner .paragraph .cons').html(list[0].cons)
            for (let i = 0; i < list.length; i++){
                total_star += list[i].totalstar
                career_star += list[i].careerstar
                balance_star += list[i].balancestar
                salary_star += list[i].salarystar
                culture_star += list[i].culturestar
                management_star += list[i].managementstar
            }
            total_star = total_star / list.length;
            career_star = career_star / list.length;
            balance_star = balance_star / list.length;
            salary_star = salary_star / list.length;
            culture_star = culture_star / list.length;
            management_star = management_star / list.length;

            $('.rating_overall .rating_stars .rating_no .rate').html(total_star.toFixed(1));
            $('.rating_cate #career_overall').html(career_star.toFixed(1))
            $('.rating_cate #balance_overall').html(balance_star.toFixed(1))
            $('.rating_cate #salary_overall').html(salary_star.toFixed(1))
            $('.rating_cate #culture_overall').html(culture_star.toFixed(1))
            $('.rating_cate #management_overall').html(management_star.toFixed(1))
            $('.rating_overall .rating_stars .rating_no .count').html(list.length + "개 리뷰")
            $('#head_rating').html("별점 "+total_star.toFixed(1)+"("+list.length+"개 리뷰)")
            if (total_star == 1){
                $('.rating_overall .rating_stars .rating_no #overall_star1').css('color','#fc0')
            } else if (total_star > 1 && total_star <= 2){
                $('.rating_overall .rating_stars .rating_no #overall_star1').css('color','#fc0')
                total_star_remainder = Math.floor((total_star % 1) * 10)*10;
                console.log(total_star_remainder)
                $('.rating_overall .rating_stars .rating_no #overall_star2').css('background','linear-gradient(to right, #fc0 ' + total_star_remainder+'%, #ccc ' + total_star_remainder + '%)');
                $('.rating_overall .rating_stars .rating_no #overall_star2').css('-webkit-background-clip','text');
                $('.rating_overall .rating_stars .rating_no #overall_star2').css('-webkit-text-fill-color','transparent');
            } else if (total_star > 2 && total_star <= 3){
                $('.rating_overall .rating_stars .rating_no #overall_star1, #overall_star2').css('color','#fc0')
                total_star_remainder = Math.floor((total_star % 2) * 10)*10;
                $('.rating_overall .rating_stars .rating_no #overall_star3').css('background','linear-gradient(to right, #fc0 ' + total_star_remainder+'%, #ccc ' + total_star_remainder+ '%)');
                $('.rating_overall .rating_stars .rating_no #overall_star3').css('-webkit-background-clip','text');
                $('.rating_overall .rating_stars .rating_no #overall_star3').css('-webkit-text-fill-color','transparent');
            } else if (total_star > 3 && total_star <= 4){
                $('.rating_overall .rating_stars .rating_no #overall_star1, #overall_star2, #overall_star3').css('color','#fc0')
                total_star_remainder = Math.floor((total_star % 3) * 10)*10;
                $('.rating_overall .rating_stars .rating_no #overall_star4').css('background','linear-gradient(to right, #fc0 ' + total_star_remainder+'%, #ccc ' + total_star_remainder+ '%)');
                $('.rating_overall .rating_stars .rating_no #overall_star4').css('-webkit-background-clip','text');
                $('.rating_overall .rating_stars .rating_no #overall_star4').css('-webkit-text-fill-color','transparent');
            } else if (total_star > 4 && total_star <= 5){
                $('.rating_overall .rating_stars .rating_no #overall_star1, #overall_star2, #overall_star3, #overall_star4').css('color','#fc0')
                total_star_remainder = Math.floor((total_star % 4) * 10)*10;
                $('.rating_overall .rating_stars .rating_no #overall_star5').css('background','linear-gradient(to right, #fc0 ' + total_star_remainder+'%, #ccc ' + total_star_remainder+ '%)');
                $('.rating_overall .rating_stars .rating_no #overall_star5').css('-webkit-background-clip','text');
                $('.rating_overall .rating_stars .rating_no #overall_star5').css('-webkit-text-fill-color','transparent');
            } else if (total_star == 5){
                $('.rating_overall .rating_stars .rating_no #overall_star1, #overall_star2, #overall_star3, #overall_star4, #overall_star5').css('color','#fc0')
            }
            $('#helpcount').html("도움이돼요(" + list[0].helpcount + ")")
        }
    });

    //////////////////////review에서 view.html에 게시글일부출력 ajax//////////////
    $.ajax({
        url : "/board/viewboard",
        data : {"corpNm" : corpNm},
        success : function (viewboardlist){
        console.log(viewboardlist)
                let html = ''
            for (let i = 0; i < viewboardlist.length; i++){
                html +=
                    '<div id="viewboardjs" class="col-md-6">'+
                        '<ul>'+
                          '<li class="viewboardjsli"><span style="display:none;">'+viewboardlist[i].bno+'</span></li>'+
                          '<li id="viewboardtitle"><span>'+
                            '<a style="color: black; text-decoration : none;" href="/board/view/'+viewboardlist[i].bno+'">'+viewboardlist[i].btitle+'</a>'+
                          '</span></li>'+
                          '<li id="viewboardcontent"><span>'+viewboardlist[i].bcontent+'</span></li>'+
                          '<li class="viewboardjsli"><i class="bi bi-hearts"></i>&nbsp<span>'+viewboardlist[i].blike+'</span>&nbsp&nbsp</li>'+
                          '<li class="viewboardjsli"><i class="bi bi-eye"></i>&nbsp<span>'+viewboardlist[i].bview+'</span>&nbsp&nbsp</li>'+
                          '<li class="viewboardjsli"><i class="fa-solid fa-comment"></i>&nbsp<span>'+viewboardlist[i].replycount+'</span>&nbsp&nbsp</li>'+
                        '</ul>' +
                    '</div>';

            }

            $('#viewboard').html(html);
        }
    });
    /////////////////////review에서 view.html에 게시글일부출력 ajax END/////////////


})
////////////////////////기업이름클릭시 뿌려줄정보  END//////////////

////////////////////////채용공고클릭시 크롤링//////////////////////
//    function recruit(){
//        let corpNm = $('#search_corp').val();
//        $.ajax({
//            url:"/review/"
//        })
//    }
////////////////////////채용공고클릭시 크롤링 END//////////////////////
setTimeout(function (){
    var popover = new bootstrap.Popover(document.querySelector('#rate_popover'), {
        container: 'body',
        html : true,
        content: function (){
            return $('#PopoverContent').html();
        }
    })
},500);


var myDefaultAllowList = bootstrap.Tooltip.Default.allowList

// To allow table elements
myDefaultAllowList.label = []



var myPopoverTrigger = document.getElementById('rate_popover')

myPopoverTrigger.addEventListener('show.bs.popover', function () {
    let corpNm = $('#corpNm').html();
    var popover = $(this)

    $.ajax({
        url : "/evaluation/list",
        data : {"firm" : corpNm, "title" : "전체 직군", "status" : "전체재직상태", "option" : "최신순"},
        success : function (list){

            for (let i = 1; i <= list[0].careerstar; i++){
                $('.popover #career_star_wp #career_star'+i).css('color', '#fc0')
            }
            for (let i = 1; i <= list[0].balancestar; i++){
                $('.popover #balance_star_wp #balance_star'+i).css('color', '#fc0')
            }
            for (let i = 1; i <= list[0].salarystar; i++){
                $('.popover #salary_star_wp #salary_star'+i).css('color', '#fc0')
            }
            for (let i = 1; i <= list[0].culturestar; i++){
                $('.popover #culture_star_wp #culture_star'+i).css('color', '#fc0')
            }
            for (let i = 1; i <= list[0].managementstar; i++){
                $('.popover #management_star_wp #management_star'+i).css('color', '#fc0')
            }
        }
    })

});

function onhelp(email){
    console.log(email)
    $.ajax({
        url : '/review/onhelp',
        data : {'email' : email, 'eno' : $('#eno').val()},
        success : function (result){
            if(result){
                location.reload(true);
            } else {
                alert("로그인");
            }

        }
    })

}

