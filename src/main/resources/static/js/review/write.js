$('#evaluation').on('click', function (){

    let evaluationform = $('#evaluationform')[0];
    let evaluationformdata = new FormData(evaluationform);
    let gradeform = $('#gradeform')[0];
    let gradeformdata = new FormData(gradeform);
    var data = $('#evaluationform').serialize();
    console.table(data);
    $.ajax({
        url : '/evaluation/write',
        method : 'POST',
        data : evaluationformdata,
        contentType : false,
        processData : false,
        success : function (re){
            if (re){
                alert("작성이 완료되었습니다.");
                history.go(-1);
            } else {
                alert("작성실패");
            }
        }
    })

})