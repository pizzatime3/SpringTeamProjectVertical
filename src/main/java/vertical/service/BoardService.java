package vertical.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vertical.domain.bimage.BimageEntity;
import vertical.domain.bimage.BimageRepository;
import vertical.domain.board.BoardEntity;
import vertical.domain.board.BoardRepository;
import vertical.domain.category.CategoryEntity;
import vertical.domain.category.CategoryRepository;
import vertical.domain.likes.LikesEntity;
import vertical.domain.likes.LikesRepository;
import vertical.domain.member.MemberEntity;
import vertical.domain.member.MemberRepository;
import vertical.domain.reply.ReplyRepository;
import vertical.dto.BoardDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardService {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private BimageRepository bimageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReplyRepository replyRepository;


    // 게시물 저장 메소드
    @Transactional
    public boolean save(BoardDto boardDto) {
        ////////////카테고리 값을 기억하고 호출
        //1.선택된 카테고리 번호 호출
        int cno = boardDto.getCno();
        System.out.println(cno);
        //2.카테고리 번호의 엔티티 찾기
        Optional<CategoryEntity> optional = categoryRepository.findById(cno);
        //3.찾기 확인
        CategoryEntity categoryEntity = null;
        if(optional.isPresent()){
            categoryEntity = optional.get();
        } else {
//            System.out.println("카테고리 미선택");
        }

        BoardEntity boardEntity = boardDto.toentity();

        boardRepository.save(boardEntity); //boardrepository에 boardentity 값을 저장

        boardEntity.setCategoryEntity( categoryEntity   );
        categoryEntity.getBoardEntityList().add(    boardEntity    );
        //board와 category의 1대n을 서로 연결시켜주기 (많은쪽을 add로 추가)
        ///////////////////////////////////////////

/////////////////////////////////////////// 회원 엔티티에 게시물 연결(7/10 최으뜸) //////////////////////////////////////////////
        // 인증된 이메일 가져오기
        String memail = SecurityContextHolder.getContext().getAuthentication().getName();
        // DB에서 해당이메일로 엔티티검색
        Optional<MemberEntity> memberoptional = memberRepository.findBymemail(memail);
        // 게시물 엔티티에 작성자 추가
        boardEntity.setMemberEntity(memberoptional.get() );
        // 회원 엔티티에 게시물 연결
        memberoptional.get().getBoardEntityList().add(boardEntity);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        String uuidfile = null;
        System.out.println("사이즈 : "+boardDto.getB_img().size());

        if(boardDto.getB_img().size() > 0 && !boardDto.getB_img().get(0).getOriginalFilename().equals("") ) {
            for(MultipartFile file : boardDto.getB_img()){
                UUID uuid = UUID.randomUUID(); // 랜덤 아이디 생성
                uuidfile = uuid.toString() +"_"+ file.getOriginalFilename().replaceAll("_" , "-"); // UUID와 파일명 구분 _ 사용 [만약에 파일며에 _ 존재하면 문재발생 -> 파일명 _ -> -]
                 String path = "C:\\Users\\user\\IdeaProjects\\springteam1\\build\\resources\\main\\static\\files\\board\\";
//                String path = "C:\\Users\\504\\Desktop\\springteam1\\build\\resources\\main\\static\\files\\board\\";

                String filepath = path + uuidfile;

                try {
                    file.transferTo(new File(filepath)); // 첨부파일 읽기모드 처리
                    BimageEntity bimageEntity = BimageEntity.builder()
                            .b_img(uuidfile)
                            .boardEntity(boardEntity)
                            .build();
                    bimageRepository.save(bimageEntity);
                    boardEntity.getBimageEntityList().add(bimageEntity);
                    System.out.println(boardEntity.getBimageEntityList().get(0));
                }catch (Exception e){System.out.println("파일저장 실패 : " + e);}
            }
        }
        return true;
    }

    // 게시물 수정 메소드
    @Transactional
    public boolean update(BoardDto boardDto) {

        Optional<BoardEntity> optional = boardRepository.findById(boardDto.getBno());
        BoardEntity boardEntity = optional.get();
        boardEntity.setBtitle(boardDto.getBtitle());
        boardEntity.setBcontent(boardDto.getBcontent());

/*        BoardEntity boardEntity = boardDto.toentity();
        boardRepository.save(boardEntity);*/

        String uuidfile = null;

        if(boardDto.getB_img().size() > 0 && !boardDto.getB_img().get(0).getOriginalFilename().equals("") ) {
            System.out.println(boardDto.getB_img());
            for(MultipartFile file : boardDto.getB_img()){
                UUID uuid = UUID.randomUUID(); // 랜덤 아이디 생성
                uuidfile = uuid.toString() +"_"+ file.getOriginalFilename().replaceAll("_" , "-"); // UUID와 파일명 구분 _ 사용 [만약에 파일명에 _ 존재하면 문재발생 -> 파일명 _ -> -]
                String path = "C:\\Users\\user\\IdeaProjects\\springteam1\\build\\resources\\main\\static\\files\\board\\"; // 파일 경로 지정 // ("user.dir") : 현재 디렉토리
//                String path = "C:\\Users\\504\\Desktop\\springteam1\\build\\resources\\main\\static\\files\\board\\";

                String filepath = path + uuidfile;

                try {
                    file.transferTo(new File(filepath)); // 첨부파일 읽기모드 처리
                    BimageEntity bimageEntity = BimageEntity.builder()
                            .b_img(uuidfile)
                            .boardEntity(boardEntity)
                            .build();
                    bimageRepository.save(bimageEntity);
                    boardEntity.getBimageEntityList().add(bimageEntity);
                    System.out.println(boardEntity.getBimageEntityList().get(0));
                }catch (Exception e){System.out.println("파일저장 실패 : " + e);}
            }
        }
        return true;
    }




    // 게시물 출력 메소드
    public JSONObject getboardlist( int cno, String key, String keyword, int page , int orderval ){
        //정렬 기준 orderval이 넘어온다.


        JSONObject object = new JSONObject();
        //페이지 제외
        //        Page<BoardEntity> boardEntities = null ; // 선언만


        Page<BoardEntity> boardEntities = null;
        // Pageable : 페이지처리 관련 인테페이스
        // PageRequest : 페이징처리 관련 클래스
        // PageRequest.of(  page , size ) : 페이징처리  설정
        // page = "현재페이지"   [ 0부터 시작 ]
        // size = "현재페이지에 보여줄 게시물수"
        // sort = "정렬기준"  [   Sort.by( Sort.Direction.DESC , "정렬필드명" )   ]

        Pageable pageable  = null;
        // 1. 만약에 정렬기준이 없으면
        if( orderval == 0  )pageable = PageRequest.of(page, 5, Sort.by( Sort.Direction.DESC, "bno"));
        // 2. 만약에 정렬기준이 좋아요 이면                                                          // 여기가 정렬 되는 필드명이 들어갑니다
        if( orderval == 1  )pageable = PageRequest.of(page, 5, Sort.by( Sort.Direction.DESC, "blike"));
        // 3. 만약에 정렬기준이 조회수 이면
        if( orderval == 2  )pageable = PageRequest.of(page, 5, Sort.by( Sort.Direction.DESC, "bview"));


        //검색
        if(key.equals("btitle")){
            boardEntities = boardRepository.findBybtitle(cno, keyword, pageable);
        } else if(key.equals("bcontent")){
            boardEntities = boardRepository.findBybcontent(cno, keyword, pageable);
        } else {  //검색이 없으면
            boardEntities = boardRepository.findBybtitle(cno, keyword, pageable);
        }

        int btncount = 5; //총 페이징 버튼 개수
        int startbtn = ( page/ btncount) * btncount + 1; //현재페이지/표시할버튼수*표시할버튼수+1
        int endbtn = startbtn + btncount -1; //시작버튼+표시할버튼수-1
        if(endbtn > boardEntities.getTotalPages()) endbtn = boardEntities.getTotalPages();

        JSONArray jsonArray = new JSONArray();

        for (BoardEntity entity : boardEntities) {
            System.out.println("BNO : " + entity.getBno());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bno", entity.getBno());
            jsonObject.put("btitle", entity.getBtitle());
            jsonObject.put("blike", likesRepository.likecount(entity.getBno()));
            jsonObject.put("bview", entity.getBview());
            jsonObject.put("bindate", entity.getCreatedate().format(DateTimeFormatter.ofPattern("yy-MM-dd")));
            jsonArray.put(jsonObject);
        }

        //js보낼 구성
        object.put("startbtn" , startbtn); //시작
        object.put("endbtn", endbtn); //끝
        object.put("totalpages", boardEntities.getTotalPages()); //전체 페이지수
        object.put("data", jsonArray); //리스트 추가

        System.out.println(object);



        return object;
    }
//


    // 게시물 개별 조회 메소드
    @Transactional
    public JSONObject getboard(int bno){
        String memail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<MemberEntity> moptional = memberRepository.findBymemail(memail);
        int mno = moptional.get().getMno();
        if (!moptional.isPresent()){
            return null;
        }

        Optional<LikesEntity> likeoptional= likesRepository.liekscheack(bno, mno);

        //////
        //조회수 증가 처리[기준:ip /24시간]
        String ip = request.getRemoteAddr(); //사용자의 ip가져오기

        Optional<BoardEntity> optional = boardRepository.findById(bno);
        BoardEntity entity = optional.get();


            //세션 호출
            Object com = request.getSession().getAttribute(ip+bno);
            if(com == null){ //만일 세션이 있으면
                //ip와 bno 합쳐서 세션(서버 내 저장소) 부여
                request.getSession().setAttribute(ip+bno , 1);
                request.getSession().setMaxInactiveInterval(60*60*24); //세션 허용시간[초단위]
                //조회수 증가 처리
                entity.setBview(entity.getBview()+1);
            }
        /////

        JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray1 = new JSONArray();
        jsonObject.put("bno" , entity.getBno());
        jsonObject.put("cname",entity.getCategoryEntity().getCname());
        jsonObject.put("mno" , entity.getMemberEntity().getMno());
        jsonObject.put("btitle" , entity.getBtitle());
        jsonObject.put("bcontent" , entity.getBcontent());
        jsonObject.put("bview" , entity.getBview());
        jsonObject.put("blike" , entity.getBlike());
        List<BimageEntity> bimageEntityList = bimageRepository.findBybino(bno);
        for(BimageEntity entity1 : bimageEntityList){
            jsonArray1.put(entity1.getB_ino());
        }
        jsonObject.put("rno" , replyRepository.replycount(entity.getBno()));
        jsonObject.put("bindate" , entity.getCreatedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        jsonObject.put("bmodate" , entity.getModifiedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        if(likeoptional.isPresent()){
            jsonObject.put("islike","0");
        } else {
            jsonObject.put("islike","1");
        }
        System.out.println(jsonObject);

        JSONArray jsonArray = new JSONArray();
        for(BimageEntity bimageEntity : entity.getBimageEntityList()){
            jsonArray.put(bimageEntity.getB_img());
        }

        jsonObject.put("bimglist" , jsonArray);
        jsonObject.put("b_ino",jsonArray1);
        return jsonObject;
    }


    public boolean bimgdelete(int b_ino){
        BimageEntity bimageEntity = bimageRepository.findById(b_ino).get();
        bimageRepository.delete(bimageEntity);
        return true;
    }



    // 게시물 삭제 메소드
    public boolean delete(int bno){
        BoardEntity boardEntity = boardRepository.findById(bno).get();
        boardRepository.delete(boardEntity);
        return true;
    }

/////////////////////////////////////////////////////////////////////////////////////

    //카테고리 메소드
    public JSONArray getcategorylist(){
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        for(CategoryEntity entity : categoryEntityList){

            JSONObject object = new JSONObject();
            object.put("cno", entity.getCno());
            object.put("cname",entity.getCname());
            jsonArray.put(object);
        }
        return jsonArray;
    }

///////////////////////////////////// 메인페이지 카테고리별 게시판 호출 ////////////////////////////////
    public JSONArray mainboarlist(){

        // 카테고리번호로 엔티티검색
        List<CategoryEntity> categorylist = categoryRepository.findAll();

        JSONArray jsonArray = new JSONArray(); // 1. 최상위 리스트 선언

            for(int i = 0; i < categorylist.size(); i++) {

                JSONObject indexobject = new JSONObject();  // 2. 리스트에 들어가는 내용물 객체 선언
                /*  내용물 구성
                        cno : 1 ,
                        cname : 여행 .
                        boardlist : {  "게시물번호", "게시물제목", "좋아요수", "댓글수", "조회수" }
                 */
                indexobject.put( "cno" , categorylist.get(i).getCno() );  //  2. cno : 1 ,
                indexobject.put( "cname" , categorylist.get(i).getCname()); //  2. cname : 여행
                // cno 가져오기
                int cno = categorylist.get(i).getCno();

                JSONArray boardlist = new JSONArray(); // 3. boardlist 에 들어가는 리스트 선언
                // 카테고리 별로 조회수 높은순서로 게시판 엔티티 검색
                List<BoardEntity> boardentity = boardRepository.findBybview(cno);

                for(BoardEntity entity :  boardentity ){

                    JSONObject boardobject = new JSONObject();

                    boardobject.put("bno", entity.getBno() );           // "게시물번호"
                    boardobject.put("btitle", entity.getBtitle() );     //  "게시물제목"
                    boardobject.put("bview", entity.getBview() );       // "조회수"
                    boardobject.put("blike", likesRepository.likecount(entity.getBno()) );       // "좋아요수"
                    boardobject.put("replycount", entity.getReplyEntities().size() );  //    "댓글수"

                    boardlist.put( boardobject );           // 반복문 당  게시물 객체 생성
                }

                indexobject.put( "boardlist" , boardlist );  // 2 . boardlist : {  "게시물번호", "게시물제목", "좋아요수", "댓글수", "조회수" }

                jsonArray.put( indexobject  ); // 1. 최상위 리스트 에  indexobject 저장

            }
        return jsonArray;  // 1. 최상위 리스트 반환
    } // 메인페이지 카테고리별 게시판 호출 end

    // 메인페이지 토픽베스트 게시판 호출
    public JSONArray topicbest(){
        // jsonarray 선언
        JSONArray jsonArray = new JSONArray();
        // 전체게시판 조회수 높은 순서로 엔티티 검색
        List<BoardEntity> boardEntityList = boardRepository.findBybview();

        for(BoardEntity entity : boardEntityList){
            JSONObject object = new JSONObject();
            object.put("cname", entity.getCategoryEntity().getCname() );
            object.put("bno", entity.getBno() );
            object.put("btitle", entity.getBtitle() );
            object.put("blike", entity.getBlike() );
            object.put("replycount", entity.getReplyEntities().size() );
            jsonArray.put(object);
        } // for end
        return jsonArray;
    } // 메인페이지 토픽베스트 게시판 호출 end

    // 메인페이지 검색내용 찾기
    public JSONArray getsearchlist(String value){
        JSONArray jsonArray = new JSONArray();
        // 게시판에 검색내용이 들어간 제목, 내용 찾기
        List<BoardEntity> boardEntityList = boardRepository.findBysearch(value);

        for(BoardEntity entity : boardEntityList){
            JSONObject object = new JSONObject();
            object.put("cno", entity.getCategoryEntity().getCno() );
            object.put("cname", entity.getCategoryEntity().getCname() );
            object.put("bno", entity.getBno() );
            object.put("btitle", entity.getBtitle() );
            object.put("bcontent", entity.getBcontent() );
            object.put("mname", entity.getMemberEntity().getMname() );
            object.put("mcom", entity.getMemberEntity().getMcom() );
            object.put("bview", entity.getBview() );
            object.put("blike", entity.getBlike() );
            object.put("bdate", entity.getCreatedate().format(DateTimeFormatter.ofPattern("MM.dd")) );
            object.put("replycount", entity.getReplyEntities().size() );
            jsonArray.put(object);
        } // for end
        return jsonArray;
   }




    @Transactional
    public boolean likesave(int bno){

        // 1. 회원번호 찾기 , 로그인된 아이디
        String memail = SecurityContextHolder.getContext().getAuthentication().getName();
        // DB에서 해당이메일로 엔티티검색
        Optional<MemberEntity> memberoptional = memberRepository.findBymemail(memail);
                    // 만약에 isPresent() : Optional 객체가 null 체크 [ true : nullx  false:nullio ]
        if( !memberoptional.isPresent() ){ return false; }
            // memberoptional.isPresent() : 내용물이 있으면 TRUE
            // !memberoptional.isPresent() : 내용물이 있으면 FALSE

        // 2. 게시물 번호 찾기
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(bno);


        if( !boardEntityOptional.isPresent() ){ return false; }


        /* Native쿼리에서 검색했을경우 */

        Optional<LikesEntity> likecheck  = likesRepository.liekscheack(  bno , memberoptional.get().getMno() );
        if( likecheck.isPresent() ){
            BoardEntity boardEntity = boardEntityOptional.get();
            boardEntity.setBlike(boardEntity.getBlike()-1);
            boardRepository.save(boardEntity);
            likesRepository.delete( likecheck.get() );
            return false;
        } else {
            BoardEntity boardEntity = boardEntityOptional.get();
            boardEntity.setBlike(boardEntity.getBlike()+1);
            boardRepository.save(boardEntity);
        }

            /*  자바에서 검색했을경우

        List<LikesEntity> likesEntities = likesRepository.findAll();
        for( LikesEntity entity : likesEntities ){
            if( entity.getBoardEntity().getBno() == bno && entity.getMemberEntity().getMno() == memberoptional.get().getMno() ){
                likesRepository.delete( entity );
                return false;
            }
        }

             */
            // 3. 좋아요 엔티티 생성
            LikesEntity likesEntity = new LikesEntity();

        // 4. 좋아요 엔티티에 회원번호 게시물번호 넣기
        likesEntity.setBoardEntity(boardEntityOptional.get());
        likesEntity.setMemberEntity(memberoptional.get());

        // 5. 좋아요 엔티티 저장
        likesRepository.save(likesEntity);

        return true;
    }



    //////////////////review에서 view.html에 게시글일부출력//////////////
    public JSONArray viewboard(String corpNm){


        List<BoardEntity> boardList = boardRepository.findByviewboard(corpNm);

        JSONArray boardlist = new JSONArray();
        for(int i = 0 ; i<boardList.size(); i++){

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("bno", boardList.get(i).getBno());
            jsonObject.put("btitle", boardList.get(i).getBtitle());
            jsonObject.put("bcontent", boardList.get(i).getBcontent());
            jsonObject.put("bview", boardList.get(i).getBview());
            jsonObject.put("blike" , boardList.get(i).getBlike());
            jsonObject.put("replycount" , boardList.get(i).getReplyEntities().size());

            boardlist.put(jsonObject);
        }
        return boardlist;
    }
    /////////////////review에서 view.html에 게시글일부출력 END////////////

    //////////////////review에서 companyboard.html에 게시글전체출력//////////////
    public JSONArray companyboard(String corpNm){


        List<BoardEntity> boardList = boardRepository.findBycompanyboard(corpNm);

        JSONArray boardlist = new JSONArray();
        for(int i = 0 ; i<boardList.size(); i++){

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("bno", boardList.get(i).getBno());
            jsonObject.put("btitle", boardList.get(i).getBtitle());
            jsonObject.put("bcontent", boardList.get(i).getBcontent());
            jsonObject.put("bview", boardList.get(i).getBview());
            jsonObject.put("blike" , boardList.get(i).getBlike());
            jsonObject.put("replycount" , boardList.get(i).getReplyEntities().size());
            jsonObject.put("bindate" , boardList.get(i).getCreatedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            boardlist.put(jsonObject);
        }
        return boardlist;
    }
    /////////////////review에서 companyboard.html에 게시글전체출력 END////////////


}
