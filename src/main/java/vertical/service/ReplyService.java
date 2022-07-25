package vertical.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vertical.domain.board.BoardEntity;
import vertical.domain.board.BoardRepository;
import vertical.domain.bimage.BimageRepository;
import vertical.domain.reply.ReplyEntity;
import vertical.domain.reply.ReplyRepository;
import vertical.domain.rimage.RimageEntity;
import vertical.domain.rimage.RimageRepository;
import vertical.dto.ReplyDto;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReplyService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private RimageRepository rimageRepository;

    @Autowired
    private BimageRepository imageRepository;

    ////////////////////////////////////////////////// 댓글 서비스 ///////////////////////////////////////////

    public boolean save(ReplyDto replyDto){

        ReplyEntity replyEntity = replyDto.toentity();
        BoardEntity boardEntity = boardRepository.findById(replyDto.getBno()).get();
        replyEntity.setBoardEntity(boardEntity);
        replyRepository.save(replyEntity);

        String uuidfile = null;

        if(replyDto.getR_img().size() != 0 && ! replyDto.getR_img().get(0).getOriginalFilename().equals("") ){
            for(MultipartFile file : replyDto.getR_img()){
                UUID uuid = UUID.randomUUID(); // 랜덤 아이디 생성
                uuidfile = uuid.toString() +"_"+ file.getOriginalFilename().replaceAll("_" , "-"); // UUID와 파일명 구분 _ 사용 [만약에 파일며에 _ 존재하면 문재발생 -> 파일명 _ -> -]
                 String path = "C:\\Users\\504\\IdeaProjects\\springteam1\\build\\resources\\main\\static\\files\\reply\\";
               // String path = "C:\\Users\\504\\Desktop\\springteam1\\build\\resources\\main\\static\\files\\reply\\";

                String filepath = path + uuidfile;

                try {

                    file.transferTo(new File(filepath)); // 첨부파일 읽기모드 처리
                    RimageEntity rimageEntity = RimageEntity.builder() // 이미지 엔티티에 빌더를 사용하여 dto를 엔티티로 변환하여 해당 변수에 저장
                            .r_img(uuidfile)
                            .replyEntity(replyEntity)
                            .build();
                    rimageEntity.setReplyEntity(replyEntity); // 이미지 엔티티에 수정된 보드 엔티티의 bno를 저장
                    /*replyEntity.getImageEntityList().add(imageEntity); // 댓글엔티티에 이미지 엔티티를 저장*/ // ?? 이게 왜 있어야 되는지 몰라서 주석 처리 하고 다시 실행해 봤는데 잘 됌
                    rimageRepository.save(rimageEntity); // 이미지 엔티티 세이브
                }catch (Exception e){System.out.println("파일저장 실패 : " + e);}
            }
        }
        return true;
    }

    public JSONArray getreplylist(int bno){

        JSONArray jsonDATA = new JSONArray();

        // 1. 모든 엔티티 호출
        /*List<ReplyEntity> entitys = replyRepository.findAll();*/
        List<ReplyEntity> entitys = replyRepository.findBybno(bno);
        // 2. json으로 변환
        for(ReplyEntity replyEntity : entitys){
            // 3. object생성
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rno" , replyEntity.getRno());
            jsonObject.put("rindex", replyEntity.getRindex());
            jsonObject.put("rcontent" , replyEntity.getRcontent());
            jsonObject.put("rindate", replyEntity.getModifiedate().format(DateTimeFormatter.ofPattern("yy-MM-dd")));
            // 4. array 생성
            JSONArray jsonArray = new JSONArray();
            for(RimageEntity rimageEntity  : replyEntity.getRimageEntities()){
                jsonArray.put(  rimageEntity.getR_img() );
            }
            // 5. object에 array 담기
            jsonObject.put("rimglist" ,  jsonArray );

            jsonDATA.put( jsonObject );
        }
        return jsonDATA;
    }

    @Transactional
    public boolean update(ReplyDto replyDto){

        Optional<ReplyEntity> optional = replyRepository.findById(replyDto.getRno());
        ReplyEntity replyEntity = optional.get();
        replyEntity.setRcontent(replyDto.getRcontent());
        System.out.println("이미지사이즈 : " + replyDto.getR_img().size());
        System.out.println("이미지 : " + replyDto.getR_img().get(0).getOriginalFilename());

        String uuidfile = null;

        if(replyDto.getR_img().size() > 0 ){
            for(MultipartFile file : replyDto.getR_img()){
                UUID uuid = UUID.randomUUID(); // 랜덤 아이디 생성
                uuidfile = uuid.toString() +"_"+ file.getOriginalFilename().replaceAll("_" , "-");
                 String path = "C:\\Users\\504\\IdeaProjects\\springteam1\\build\\resources\\main\\static\\files\\reply\\";
//                String path = "C:\\Users\\user\\IdeaProjects\\springteam1\\build\\resources\\main\\static\\files\\reply\\";

                String filepath = path + uuidfile;
                System.out.println(filepath);

                try {
                    file.transferTo(new File(filepath));
                    RimageEntity rimageEntity = RimageEntity.builder()
                            .r_img(uuidfile)
                            .replyEntity(replyEntity)
                            .build();
                    rimageRepository.save(rimageEntity);
                }catch (Exception e){System.out.println("파일 저장 실패 : " + e);}

            }
        }
        System.out.println("수정완료");
        return true;
    }

    public JSONObject getreply(int rno){

        Optional<ReplyEntity> optional = replyRepository.findById(rno);
        ReplyEntity entity = optional.get();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();
        jsonObject.put("rcontent", entity.getRcontent());
        List<RimageEntity> rimageEntityList = rimageRepository.replydelete(rno);
        for(RimageEntity rimageEntity : rimageEntityList){
            jsonArray1.put(rimageEntity.getR_ino());
        }
        JSONArray jsonArray = new JSONArray();
        for(RimageEntity rimageEntity : entity.getRimageEntities()){
            jsonArray.put(rimageEntity.getR_img());
        }
        jsonObject.put("rimglist" , jsonArray);
        jsonObject.put("r_ino" , jsonArray1);
        return jsonObject;
    }

    public boolean delete(int rno){
        ReplyEntity replyEntity = replyRepository.findById(rno).get();
        replyRepository.delete(replyEntity);
        return true;
    }

    public boolean rimgdelete(int r_ino){
        RimageEntity rimageEntity = rimageRepository.findById(r_ino).get();
        rimageRepository.delete(rimageEntity);
        return true;
    }

    /////////////////////////////////////////////// 대댓글 서비스 ///////////////////////////////////////////////

    public boolean reresave( int rno , String rereplycontent ){
        // 대댓글 엔티티 준비물 [ rno=자동 , rcontent=인수 , rlikes=초기0 , rindex = 인수 , BoardEntity=인수 이용 ]
        //1. BoardEntity 찾기
        BoardEntity boardEntity = replyRepository.findById( rno ).get().getBoardEntity();

        ReplyEntity replyEntity = ReplyEntity.builder()
                .rcontent(rereplycontent)
                .rlikes( 0 )
                .rindex( rno )
                .boardEntity( boardEntity )
                .build();
        replyRepository.save( replyEntity );
        // 일단 이미지 제외 한 대댓글 인데~ 여기까지 생각해보고 질문 있으면 톡주세요~넵 감사합니다~!
        return true;
    }

}
