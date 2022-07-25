///////////////페이지가 처음 열을때 게시물 출력 메소드 호출//////////////////////////
let orderval = 0; // 0 이면 정렬없다 , 1 이면 인기순 2 이면 추천순   [ 변수 이해 되나요??네]

board_list(  1 , 0, "" , "" );       //  cno , key , keyword
category_list( );
/////////////////////////////////////   전역변수  :  검색 내용 유지   ///////////////////////////////////////////////////////
let current_cno = 1; // 카테고리 선택 변수 [ 없을경우 1 = 자유게시판 ]
let current_page = 0;
let current_key = ""; // 현재 검색된 키 변수  [ 없을경우 공백 ]
let current_keyword = ""; // 현재 검색된 키워드 변수[ 없을경우 공백 ]
///////////////////////////////////////////////////////////

function orderevent( val ){ //조회수/추천수
    if( val == 1  ){  this.orderval = 1 ; board_list( this.current_cno , this.current_page , this.current_key ,this.current_keyword ); }
    if( val == 2 ){  this.orderval = 2 ; board_list( this.current_cno , this.current_page , this.current_key ,this.current_keyword ); }
}

function board_list(cno, page, key, keyword){ //검색 연계

    this.current_cno = cno;
    this.current_page = page;
    if(key != undefined) { this.current_key = key; } //만일 검색된 key가 없을 경우
    if(keyword != undefined) { this.current_keyword = keyword; } //만일 검색된 key가 없을 경우

    if( this.orderval == undefined) { this.orderval = 0; } //  만약에 정렬기준이 없으면 0 으로 초기화한다.

//    alert(  this.orderval  );

    $.ajax({
        url : "/board/getboardlist",
        data : {
         "cno" : this.current_cno,
         "key" : this.current_key,
         "keyword" : this.current_keyword,
          "page" :  this.current_page ,
          "orderval" : this.orderval  }, //검색, 카테고리 데이타 넘겨 받음
        method : "GET",
        success : function(boardlist){
        console.table(boardlist)
            let html =  '<br><button type="button" class="writebtn"><a href="/board/save" style="text-decoration:none; font-weight: bold; font-size:20px; color:white; margin-right:10px;">글쓰기</a></button><br>'+
            '<tr>'+
            '<th width="10%">번호</th><th width="60%" style="text-decoration:none;">제목</th>'+
            '<th width="10%">좋아요</th><th width="10%">조회수</th><th width="10%">작성일</th>'+
            '</tr>';
            //헤더

            //검색 결과 관련 코드/////////////////////////////////////
            if(boardlist.data.length == 0){ //검색 결과가 존재하지 않을 경우 나타낼 문구
                console.log(boardlist.data.length);
                html += '<tr>'+
                    '<td colspan="5">검색 결과가 존재하지 않습니다.</td>'+
                    '</tr>';
            } else { //존재할 경우
                for(let i=0; i<boardlist.data.length; i++){ //반복문 돌리기
                    html +=
                        '<tr>'+
                            '<td>'+boardlist.data[i].bno+'</td>'+ //테이블 번호
                            '<td><a style="color: black; text-decoration : none;" href="/board/view/'+boardlist.data[i].bno+'">'+boardlist.data[i].btitle+'</a></td>'+
                            '<td><i class="bi bi-hearts"></i>'+boardlist.data[i].blike+'</td>'+ //좋아요 수 (2 안에 코드 만들어 넣기)
                            '<td><i class="bi bi-eye"></i>'+boardlist.data[i].bview+'</td>'+ //조회수
                            '<td><i class="bi bi-clock"></i>'+boardlist.data[i].bindate+'</td>'+ //날짜
                        '</tr>';
                }
            }
            //페이징 생성할 경우 여기부터 기입
         let pagehtml = "";
            //이전 버튼////////////////
         if( page == 0 ){   // 현재 페이지가 첫페이지 이면
            pagehtml +=
             '<li class="page-item"> '+
                 '<button style="color:black;" class="page-link" onclick="board_list('+cno+','+ (page)  +')"> 이전 </button>'+  // 검색 없음
              '</li>';
         }else{  // 현재 페이지가 첫페이지가 아니면
             pagehtml +=
                '<li class="page-item"> '+
                    '<button style="color:black;" class="page-link" onclick="board_list('+cno+','+ (page-1)  +')"> 이전 </button>'+  // 검색 없음
                 '</li>';
          }
            //페이징 버튼////////////////
         for( let i = boardlist.startbtn ; i<=boardlist.endhtn ; i++ ){
            pagehtml +=
              '<li class="page-item"> '+
                '<button style="color:black;" class="page-link" onclick="board_list('+cno+','+(i-1)+')"> '+i+' </button>'+  // 검색 없음
              '</li>';
         }
            //다음 버튼////////////////
        if( page == boardlist.totalpages -1 ){ // 현재 페이지가 마지막 페이지이면
             pagehtml +=
                '<li class="page-item"> '+
                    '<button style="color:black;" class="page-link" onclick="board_list('+cno+','+ (page)  +')"> 다음 </button>'+  // 검색 없음
                 '</li>';
        }else{ // 아니면
             pagehtml +=
                '<li class="page-item"> '+
                    '<button style="color:black;" class="page-link" onclick="board_list('+cno+','+ (page+1)  +')"> 다음 </button>'+  // 검색 없음
                 '</li>';
        }
        //////////////////
            $("#boardtable").html(html); //테이블에 html 넣기
            $("#pagebtnbox").html( pagehtml); // 페이징 html 넣기
        }
    });
}

function category_list(){ //카테고리 호출
    $.ajax({
        url : "/board/getcategorylist",
        success : function(categorylist){
        let html = "";
        for(let i=0; i<categorylist.length; i++){
            html +=
                '<button class="border-0 bg-light" name="categorybtn" id="categorybtn" value="'+categorylist[i].cno+'">'+
                    '<b>'+categorylist[i].cname+'</b>'+
                '</button>';
            }
        $("#categorybox").html(html);
        }
    });
}


$(document).on("click", "#categorybtn", function(){
    console.log($(this).val());
    this.current_cno = $(this).val(); //현재 카테고리번호 변경
    board_list($(this).val(), 0, "", ""); //카테고리 버튼 눌렀을때 검색 초기화

    $.ajax({
        url : "/board/selectcategory",
        data : {"cno" : $(this).val()},
        success : function(re){
//                $("#boardtable").html(html); //테이블에 html 넣기

//            alert("서버에 카테고리 번호 저장");
        }
    });

    $('#categorybox button').css('color','#000000');
    $(this).css('color','#a3cca2');
})


///////////////////////////////////////////

//검색 버튼 눌렀을때
function search(){

    let key = $("#key").val(); //꺼내오기
    let keyword = $("#keyword").val();
    board_list(this.current_cno, 0, key, keyword);
}




