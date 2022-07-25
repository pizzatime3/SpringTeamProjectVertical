//$(document).ready(function (){
//alert("123");
//
//    $.ajax({
//        url : "/review/getlist",
//        method : "GET",
//        success : function (reviewlist){
//        console.table(reviewlist);
//
//            let html = '<tr>\n' +
//                '            <th>회사명</th>\n' +
//                '        </tr>'
//
//            for (let i = 0; i < reviewlist.data.length; i++){
//                html +=
//                    '<tr>'+
//                    '<td><a href="/review/'+reviewlist.data[i].name+'">'+reviewlist.data[i].name+'<a></td> '+
//                    '</tr>';
//            }
//            $('#reviewtable').html(html);
//
//        }
//    })
//})
/////////////////////======인기기업출력=====//////////////
/*$(document).ready(function (){
    $.ajax({
        url : "/review/getpopularlist",
        method : "GET",
        success : function (popularlist){
                let html = ""
            for (let i = 0; i < popularlist.length; i++){
               html +=
                      '<div class="col">'+
                        '<div class="card">'+
                            '<div class="card-body" style="display : grid; grid-template-columns: 70px 1fr;">'+
                                '<div class="col-md-3">'+
                                    '<a href="/review/company/'+popularlist[i].name+'"><img style="width:70px;" src="'+popularlist[i].img+'"></a>'+
                                '</div>'+

                                '<div class="col-md-9">'+
                                    '<ul style="list-style:none;">'+
                                        '<li>'+
                                            '<a style="font-size:20px; color:black; text-decoration:none" href="/review/company/'+popularlist[i].name+'">'+popularlist[i].name+'</a>'+
                                        '</li>'+
                                        '<li>'+
                                            '<span>별점</span>'+
                                        '</li>'+
                                        '<li>'+
                                             '<span style="color:#37acc9;">리뷰 게시물 연봉 채용</span>'+
                                        '</li>'+
                                    '</ul>'+
                                '</div>'+

                            '</div>'+
                         '</div>'+
                      '</div>';
            }
            $('#popularlist').html(html);
        }
    })
})*/
$(document).ready(function (){
    $.ajax({
        url : "/review/getpopularlist",
        method : "GET",
        success : function (popularlist){
                let html = ""
            for (let i = 0; i < popularlist.length; i++){
               html +=
                      '<div class="col">'+
                        '<div class="card">'+
                            '<div class="card-body" style="display : grid; grid-template-columns: 70px 1fr;">'+
                                '<div class="col-md-3">'+

                                    '<a href="/review/company/'+popularlist[i].name+'"><img style="width:70px;" src="/img/company-'+popularlist[i].name+'_logo.jpg"></a>'+

                                '</div>'+

                                '<div class="col-md-9">'+
                                    '<ul style="list-style:none;">'+
                                        '<li>'+
                                            '<a style="font-size:20px; color:black; text-decoration:none" href="/review/company/'+popularlist[i].name+'">'+popularlist[i].name+'</a>'+
                                        '</li>'+
                                        '<li>'+
                                            '<span></span>'+
                                        '</li>'+
                                        '<li style="padding-top:8px;">'+
                                             '<a style="text-decoration:none" href="/review/companyreview/'+popularlist[i].name+'"><span style="color:#37acc9;">리뷰&nbsp&nbsp</span></a>'+
                                             '<a style="text-decoration:none" href="/review/companyboard/'+popularlist[i].name+'"><span style="color:#37acc9;">게시글</span></a>'+
                                        '</li>'+
                                    '</ul>'+
                                '</div>'+

                            '</div>'+
                         '</div>'+
                      '</div>';


            }
            $('#popularlist').html(html);
        }
    });

})




/////////////////////======인기기업출력  END=====//////////////

/////////////////////======기업검색출력=====//////////////
//function search(){
//    let corpNm = $('#search_corp').val();
//    $.ajax({
//        url : "/review/search",
//        method : "GET",
//        data : {"corpNm" : corpNm},
//        success : function (reviewlist){
//
//            console.log(reviewlist)
//            let html = '<tr>\n' +
//                '            <th>회사명</th>\n' +
//                '        </tr>'
//
//            for (let i = 0; i < reviewlist.length; i++){
//                html +=
//                    '<tr>'+
//                    '<td><a href="/review/company/'+reviewlist[i].name+'">'+reviewlist[i].name+'<a></td> '+
//                    '</tr>';
//            }
//            $('#reviewtable').html(html);
//        }
//    })
//}

$("#search_corp").keyup(function(){//*******----keyup으로기업검색출력----********
    search();
})

function search(){
//    $("#searchbox").css("border","1px solid black");
//    $("#search_corp").css("border","0.5px solid white");
    let corpNm = document.getElementById("search_corp").value;
    $.ajax({
        url : "/review/search",
        method : "GET",
        data : {"corpNm" : corpNm},
        success : function (reviewlist){

            console.log(reviewlist)
            let html = ''

            for (let i = 0; i < reviewlist.length; i++){
                html +=
                    '<a class="search_atag" href="/review/company/'+reviewlist[i].name+'">'+
                    '<li class="search_li">'+
                    reviewlist[i].name
                    +'</li>'+
                    '<a>';

            }
            console.log($(this).html());
            $("#reviewtable").css('display', 'block');
            $('#reviewtable').html(html);

        }
    });
}


/////////////////////======기업검색출력  END=====//////////////

$(document).ready(function(){
    $(".wrap").mouseleave(function(){
    $("#reviewtable").css('display', 'none');
//        $("#reviewtable").html("");
    });
    $(".wrap").mouseenter(function(){
        search();
    });
//    $("#reviewtable1").mouseleave(function(){
//            $("#reviewtable").html("");
//    });

});

//let focuscheck = true;
//$('#search_corp').focusout(function() {
////    if(focuscheck==true){
//        $("#reviewtable").css('visibility', 'hidden');
////    }
////    focuscheck = true;
//});
//
//$('#reviewtable li').click(function() {
//    console.log($(this).val())
//});
//
//
//$('#search_corp').focusin(function() {
//$("#reviewtable").css('display', 'block');
//    search();
//});

//////////////////////////////

//$('#search_corp').focusout(function() {
//    setTimeout(function() {
//         $("#reviewtable").html("");
//    }, 100);
//});
//
//$('#search_corp').focusin(function() {
//    search();
//});

//$('#search_corp').click(function() {
//    console.log($(this).parent().html());
//     $.ajax({
//            url : "/review/search",
//            method : "GET",
//            data : {"corpNm" : corpNm},
//            success : function (reviewlist){
//
//                console.log(reviewlist)
//                let html = ''
//
//                for (let i = 0; i < reviewlist.length; i++){
//                    html +=
//
//                        '<li class="search_li">'+
//                        '<a class="search_atag" href="/review/company/'+reviewlist[i].name+'">'+reviewlist[i].name+'<a>'+
//                        '</li>';
//
//                }
//                console.log($(this).html());
//
//                $('#reviewtable').html(html);
//
//            }
//        });
//})