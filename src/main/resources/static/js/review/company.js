////////////////////////기업이름클릭시 뿌려줄정보//////////////
$(document).ready(function(){
    //let corpNm = $('#corpNm').val();//input 일때 .val
    let corpNm = $('#corpNm').html();
    $.ajax({
        url : "/review/getcorp",
        data : {"corpNm" : corpNm},
        success: function(corp){
        console.table(corp);
            $('#homepage').html(corp.data[0].homepage);//홈페이지주소
            $('#base_address').html(corp.data[0].base_address);//기업기본주소
            $('#employees').html(corp.data[0].employees);//기업종업원수
            $('#establish_date').html(corp.data[0].establish_date);//기업설립일자
            $('#salary').html(corp.data[0].salary);//기업의 1인 평균 급여 금액
            $('.industry').html(corp.data[0].industry);
        }
    })
})
////////////////////////기업이름클릭시 뿌려줄정보  END//////////////