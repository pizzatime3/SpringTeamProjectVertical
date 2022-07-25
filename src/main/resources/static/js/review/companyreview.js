let title="전체 직군";
let status="전체재직상태";
let option="최신순";
getlist(title, status, option);

$(document).ready(function (){

    let corpNm = $('#corpNm').html();
    $.ajax({
        url : "/review/getcorp",
        data : {"corpNm" : corpNm},
        success: function(corp){
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
        url: "/evaluation/list",
        data: {"firm": corpNm, "title": title, "status": status, "option": option},
        async: false,
        success: function (list) {
            let total_star = 0;
            let career_star = 0;
            let balance_star = 0;
            let salary_star = 0;
            let culture_star = 0;
            let management_star = 0;
            var star = [0, 0, 0, 0, 0];
            let total_star_remainder;
            for (let i = 0; i < list.length; i++){
                console.log(list[i].totalstar)
                if(list[i].totalstar == 1){
                    star[0]++
                } else if (list[i].totalstar == 2){
                    star[1]++
                } else if (list[i].totalstar == 3){
                    star[2]++
                } else if (list[i].totalstar == 4){
                    star[3]++
                } else if (list[i].totalstar == 5){
                    star[4]++
                }
            }
            console.table(star)

            for (let i = 0; i < star.length; i++){
                star[i] = (star[i] / list.length)*100;
                $('.rating_bar #star'+(i+1)).css('width',star[i]+'%');
            }

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

            $('#review_overall .rating_overall .rating_stars .rating_no .rate').html(total_star.toFixed(1));
            $('.rating_cate #career_overall').html(career_star.toFixed(1))
            $('.rating_cate #balance_overall').html(balance_star.toFixed(1))
            $('.rating_cate #salary_overall').html(salary_star.toFixed(1))
            $('.rating_cate #culture_overall').html(culture_star.toFixed(1))
            $('.rating_cate #management_overall').html(management_star.toFixed(1))
            $('.rating_overall .rating_stars .rating_no .count').html(list.length + "개 리뷰")
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
        }
    })

})



$('.form_wrap.title').on('select2:select', function (e){
    title = e.params.data.text;
    getlist(title, status, option);
    console.table(title, status);
})

$('.form_wrap.status').on('select2:select', function (e){
    status = e.params.data.text;
    getlist(title, status, option);
    console.table(title, status);
})

$('.form_wrap.option').on('select2:select', function (e){
    option = e.params.data.text;
    getlist(title, status, option);

})

function latest(a, b) {
    if(a.countdate == b.countdate){ return 0} return  a.countdate > b.countdate ? -1 : 1;
}

function last(a, b) {
    if(a.countdate == b.countdate){ return 0} return  a.countdate > b.countdate ? 1 : -1;
}

function highratesort(a, b) {
    if(a.totalstar == b.totalstar){ return 0} return  a.totalstar > b.totalstar ? -1 : 1;
}

function rowratesort(a, b) {
    if(a.totalstar == b.totalstar){ return 0} return  a.totalstar > b.totalstar ? 1 : -1;
}

function helpsort(a, b) {
    if(a.helpcount == b.helpcount){ return 0} return  a.helpcount > b.helpcount ? -1 : 1;
}


function getlist(title, status, option){
    let authentication = $('#authentication').val();
    let corpNm = $('#corpNm').html();
    $.ajax({
        url : "/evaluation/list",
        data : {"firm" : corpNm, "title" : title, "status" : status, "option" : option},
        async : false,
        success : function (list){

            if (option == "평점높은순"){
                list.sort(highratesort);
            } else if (option == "평점낮은순"){
                list.sort(rowratesort);
            } else if(option == "최신순"){
                list.sort(latest);
            } else if (option == "오래된순"){
                list.sort(last);
            } else if(option == "추천순"){
                list.sort(helpsort);
            }

            let html = '';

            for(let i = 0; i < list.length; i++){

                html += '<div>\n' +
                    '                            <div class="review_item" id="review_item'+list[i].eno+'">\n' +
                    '\n' +
                    '\n' +
                    '                                <div class="review_item_inner">\n' +
                    '\n' +
                    '                                    <input type="text" id="eno" style="display: none" value="'+list[i].eno+'">\n' +
                    '\n' +
                    '                                    <div class="rating">\n' +
                    '\n' +
                    '                                        <span id="num">' + list[i].totalstar.toFixed(1) + '</span>\n' +
                    '\n' +
                    '                                        <div class="star">\n' +
                    '\n' +
                    '                                            <span id="1-stars-total" name="gtotal" value="1"></span>\n' +
                    '                                            <label for="1-stars-total" class="star" id="star1">&#9733;</label>\n' +
                    '                                            <span id="2-stars-total" name="gtotal" value="2"></span>\n' +
                    '                                            <label for="2-stars-total" class="star" id="star2">&#9733;</label>\n' +
                    '                                            <span id="3-stars-total" name="gtotal" value="3"></span>\n' +
                    '                                            <label for="3-stars-total" class="star" id="star3">&#9733;</label>\n' +
                    '                                            <span id="4-stars-total" name="gtotal" value="4"></span>\n' +
                    '                                            <label for="4-stars-total" class="star" id="star4">&#9733;</label>\n' +
                    '                                            <span id="5-star-total" name="gtotal" value="5"></span>\n' +
                    '                                            <label for="5-star-total" class="star" id="star5">&#9733;</label>\n' +
                    '\n' +
                    '                                            <button type="button" class="btn btn-secondary" data-bs-container="body" data-bs-toggle="popover" data-bs-placement="bottom" id="rate_popover'+list[i].eno+'">\n' +
                    '                                                Popover on bottom\n' +
                    '                                            </button>\n' +
                    '\n' +
                    '                                        </div>\n' +
                    '<div id="PopoverContent' + list[i].eno +'" style="visibility: hidden;" class="PopoverContent">\n' +
                    '\n' +
                    '    <div id="popover-body">\n' +
                    '\n' +
                    '        <div class="rating_wp">\n' +
                    '\n' +
                    '            <div id="career_star_wp">\n' +
                    '\n' +
                    '                <span id="1-stars-career" name="gtotal" value="1"></span>\n' +
                    '                <label for="1-stars-career" class="star1" id="career_star1">&#9733;</label>\n' +
                    '                <span id="2-stars-career" name="gtotal" value="2"></span>\n' +
                    '                <label for="2-stars-career" class="star1" id="career_star2">&#9733;</label>\n' +
                    '                <span id="3-stars-career" name="gtotal" value="3"></span>\n' +
                    '                <label for="3-stars-career" class="star1" id="career_star3">&#9733;</label>\n' +
                    '                <span id="4-stars-career" name="gtotal" value="4"></span>\n' +
                    '                <label for="4-stars-career" class="star1" id="career_star4">&#9733;</label>\n' +
                    '                <span id="5-star-career" name="gtotal" value="5"></span>\n' +
                    '                <label for="5-star-career" class="star1"  id="career_star5">&#9733;</label>\n' +
                    '\n' +
                    '            </div>\n' +
                    '\n' +
                    '            <span class="rating_item">커리어 향상</span>\n' +
                    '\n' +
                    '        </div>\n' +
                    '\n' +
                    '        <div class="rating_wp">\n' +
                    '\n' +
                    '            <div id="balance_star_wp">\n' +
                    '\n' +
                    '                <span id="1-stars-balance" name="gtotal" value="1"></span>\n' +
                    '                <label for="1-stars-balance" class="star" id="balance_star1">&#9733;</label>\n' +
                    '                <span id="2-stars-balance" name="gtotal" value="2"></span>\n' +
                    '                <label for="2-stars-balance" class="star" id="balance_star2">&#9733;</label>\n' +
                    '                <span id="3-stars-balance" name="gtotal" value="3"></span>\n' +
                    '                <label for="3-stars-balance" class="star" id="balance_star3">&#9733;</label>\n' +
                    '                <span id="4-stars-balance" name="gtotal" value="4"></span>\n' +
                    '                <label for="4-stars-balance" class="star" id="balance_star4">&#9733;</label>\n' +
                    '                <span id="5-star-balance" name="gtotal" value="5"></span>\n' +
                    '                <label for="5-star-balance" class="star" id="balance_star5">&#9733;</label>\n' +
                    '\n' +
                    '            </div>\n' +
                    '\n' +
                    '            <span class="rating_item">업무와 삶의 균형</span>\n' +
                    '\n' +
                    '        </div>\n' +
                    '\n' +
                    '        <div class="rating_wp">\n' +
                    '\n' +
                    '            <div id="salary_star_wp">\n' +
                    '\n' +
                    '                <span id="1-stars-salary" name="gtotal" value="1"></span>\n' +
                    '                <label for="1-stars-salary" class="star" id="salary_star1">&#9733;</label>\n' +
                    '                <span id="2-stars-salary" name="gtotal" value="2"></span>\n' +
                    '                <label for="2-stars-salary" class="star" id="salary_star2">&#9733;</label>\n' +
                    '                <span id="3-stars-salary" name="gtotal" value="3"></span>\n' +
                    '                <label for="3-stars-salary" class="star" id="salary_star3">&#9733;</label>\n' +
                    '                <span id="4-stars-salary" name="gtotal" value="4"></span>\n' +
                    '                <label for="4-stars-salary" class="star" id="salary_star4">&#9733;</label>\n' +
                    '                <span id="5-star-salary" name="gtotal" value="5"></span>\n' +
                    '                <label for="5-star-salary" class="star" id="salary_star5">&#9733;</label>\n' +
                    '\n' +
                    '            </div>\n' +
                    '\n' +
                    '            <span class="rating_item">급여 및 복지</span>\n' +
                    '\n' +
                    '        </div>\n' +
                    '\n' +
                    '        <div class="rating_wp">\n' +
                    '\n' +
                    '            <div id="culture_star_wp">\n' +
                    '\n' +
                    '                <span id="1-stars-culture" name="gtotal" value="1"></span>\n' +
                    '                <label for="1-stars-culture" class="star" id="culture_star1">&#9733;</label>\n' +
                    '                <span id="2-stars-culture" name="gtotal" value="2"></span>\n' +
                    '                <label for="2-stars-culture" class="star" id="culture_star2">&#9733;</label>\n' +
                    '                <span id="3-stars-culture" name="gtotal" value="3"></span>\n' +
                    '                <label for="3-stars-culture" class="star" id="culture_star3">&#9733;</label>\n' +
                    '                <span id="4-stars-culture" name="gtotal" value="4"></span>\n' +
                    '                <label for="4-stars-culture" class="star" id="culture_star4">&#9733;</label>\n' +
                    '                <span id="5-star-culture" name="gtotal" value="5"></span>\n' +
                    '                <label for="5-star-culture" class="star" id="culture_star5">&#9733;</label>\n' +
                    '\n' +
                    '            </div>\n' +
                    '\n' +
                    '            <span class="rating_item">사내 문화</span>\n' +
                    '\n' +
                    '        </div>\n' +
                    '\n' +
                    '        <div class="rating_wp">\n' +
                    '\n' +
                    '            <div id="management_star_wp">\n' +
                    '\n' +
                    '                <span id="1-stars-management" name="gtotal" value="1"></span>\n' +
                    '                <label for="1-stars-management" class="star" id="management_star1">&#9733;</label>\n' +
                    '                <span id="2-stars-management" name="gtotal" value="2"></span>\n' +
                    '                <label for="2-stars-management" class="star" id="management_star2">&#9733;</label>\n' +
                    '                <span id="3-stars-management" name="gtotal" value="3"></span>\n' +
                    '                <label for="3-stars-management" class="star" id="management_star3">&#9733;</label>\n' +
                    '                <span id="4-stars-management" name="gtotal" value="4"></span>\n' +
                    '                <label for="4-stars-management" class="star" id="management_star4">&#9733;</label>\n' +
                    '                <span id="5-star-management" name="gtotal" value="5"></span>\n' +
                    '                <label for="5-star-management" class="star" id="management_star5">&#9733;</label>\n' +
                    '\n' +
                    '            </div>\n' +
                    '\n' +
                    '            <span class="rating_item">경영진</span>\n' +
                    '\n' +
                    '        </div>\n' +
                    '\n' +
                    '    </div>\n' +
                    '\n' +
                    '</div>'+
                    '\n' +
                    '\n' +
                    '                                    </div>\n' +
                    '\n' +
                    '                                    <div style="margin-left: 20px">\n' +
                    '\n' +
                    '                                        <div class="one_line">' + list[i].oneline + '</div>\n' +
                    '\n' +
                    '                                        <div class="auth">' + list[i].ewriter + '&nbsp;' + list[i].eoccupational_group + '&nbsp;' + list[i].createdate + '</div>\n' +
                    '\n' +
                    '                                        <div>\n' +
                    '\n' +
                    '                                            <div class="paragraph">\n' +
                    '\n' +
                    '                                                <p>\n' +
                    '                                                    <strong>장점</strong>\n' +
                    '                                                    <span class="pros">' + list[i].pros + '</span>\n' +
                    '                                                </p>\n' +
                    '\n' +
                    '                                                <p>\n' +
                    '                                                    <strong>단점</strong>\n' +
                    '                                                    <span class="cons">' + list[i].pros + '</span>\n' +
                    '                                                </p>\n' +
                    '                                            </div>\n' +
                    '\n' +
                    '                                            <div class="fnc">\n' +
                    '                                                <button type="button" class="btn_help" onclick="onhelp(\''+authentication+'\')" id="helpcount">도움이돼요('+list[i].helpcount+')</button>\n' +
                    '                                                <div class="share">\n' +
                    '                                                    <label>\n' +
                    '\n' +
                    '                                                        <span tabindex="0" data-link="#share-facebook">\n' +
                    '                                                          <a class="btn_fb">\n' +
                    '                                                            <i class="blind">Share on Facebook</i>\n' +
                    '                                                          </a>\n' +
                    '                                                        </span>\n' +
                    '\n' +
                    '                                                        <span tabindex="0" data-link="#share-twitter">\n' +
                    '                                                          <a class="btn_tw">\n' +
                    '                                                            <i class="blind">Share on Twitter</i>\n' +
                    '                                                          </a>\n' +
                    '                                                        </span>\n' +
                    '\n' +
                    '                                                    </label>\n' +
                    '\n' +
                    '                                                    <span class="cover">\n' +
                    '                                                        <a class="btn_url">\n' +
                    '                                                            <i class="blind">Share URL</i>\n' +
                    '                                                        </a>\n' +
                    '                                                    </span>\n' +
                    '                                                </div>\n' +
                    '                                            </div>\n' +
                    '                                        </div>\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </div>'
                $('.review_all').html(html);

                setTimeout(function (){
                    for (let j = 1; j <= list[i].totalstar; j++){
                        $('#review_item'+list[i].eno+' .review_item_inner .rating .star #star'+j).css('color','#fc0');
                    }

                    var popover = new bootstrap.Popover(document.querySelector('#rate_popover'+list[i].eno), {
                        container: 'body',
                        html : true,
                        content: function (){
                            return $('#PopoverContent'+list[i].eno).html();
                        }
                    })

                },200)

            }
        }
    })
}

$( '.form_wrap select' ).select2( {
    dir:'auto',
    theme: "bootstrap-5",
    width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
    placeholder: $( this ).data( 'placeholder' ),
    minimumResultsForSearch: Infinity
} );

$(function (){
    var myDefaultAllowList = bootstrap.Tooltip.Default.allowList

    myDefaultAllowList.label = [];

    // popover = new bootstrap.Popover(document.querySelector('[data-bs-toggle="popover"]'), {
    //     container: 'body',
    //     html : true,
    //     content: function (){
    //         return $('.PopoverContent').html();
    //     }
    // })
})



$(document).on('click', '.review_item .review_item_inner .rating .star button', function (){

    console.log("popover")

    let eno = $(this).parent().parent().parent().find('#eno').val();

    $.ajax({
        url : "/evaluation/detaillist",
        data : {"eno":eno},
        success : function (detaillist){
            for (let i = 1; i <= detaillist.careerstar; i++){
                $('.popover #career_star_wp #career_star'+i).css('color', '#fc0')
            }
            for (let i = 1; i <= detaillist.balancestar; i++){
                $('.popover #balance_star_wp #balance_star'+i).css('color', '#fc0')
            }
            for (let i = 1; i <= detaillist.salarystar; i++){
                $('.popover #salary_star_wp #salary_star'+i).css('color', '#fc0')
            }
            for (let i = 1; i <= detaillist.culturestar; i++){
                $('.popover #culture_star_wp #culture_star'+i).css('color', '#fc0')
            }
            for (let i = 1; i <= detaillist.managementstar; i++){
                $('.popover #management_star_wp #management_star'+i).css('color', '#fc0')
            }

        }
    })



    // popover.hide()

    // myPopoverTrigger.addEventListener('show.bs.popover', function () {
    //
    // });




})

$(document).on('dblclick', '.review_item .review_item_inner .rating .star button', function (){
    popover.hide()
})

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

