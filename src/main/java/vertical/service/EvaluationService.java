package vertical.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vertical.domain.evaluation.EvaluationEntity;
import vertical.domain.evaluation.EvaluationRepository;
import vertical.domain.evaluation.EvaluationhelpEntity;
import vertical.domain.evaluation.EvaluationhelpRepository;
import vertical.domain.grade.GradeEntity;
import vertical.domain.grade.GradeRepository;
import vertical.domain.member.MemberEntity;
import vertical.domain.member.MemberRepository;
import vertical.dto.EvaluationDto;
import vertical.dto.EvaluationhelpDto;
import vertical.dto.GradeDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EvaluationhelpRepository evaluationhelpRepository;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Transactional
    public boolean write(EvaluationDto evaluationDto, GradeDto gradeDto){

        // 회사 DB 생성후, 입력값 가져와서 대조 후 넣어야 할듯, 일단 그냥 넣기

        EvaluationEntity evaluationEntity = evaluationDto.toEntity();


        evaluationRepository.save(evaluationEntity);

        GradeEntity gradeEntity = gradeDto.toEntity();

        evaluationEntity.setGradeEntity(gradeEntity);
        gradeEntity.getEvaluationEntities().add(evaluationEntity);

        gradeRepository.save(gradeEntity);

        if(evaluationEntity.getEno() < 1){
            return false;
        }

        return true;

    }

    @Transactional
    public JSONArray getlist(String firm, String title, String status, String option){
        JSONArray jsonArray = new JSONArray();

        List<EvaluationEntity> evaluationEntityList = evaluationRepository.findByFirm(firm);

        if(title.equals("") && status.equals("") && option.equals("")){
            evaluationEntityList = evaluationRepository.findByFirm(firm);
        }

        if(title.equals("전체 직군") && status.equals("전체재직상태") && option.equals("최신순")) {
            evaluationEntityList = evaluationRepository.findByFirm(firm);
        } else if (!title.equals("전체 직군") && !status.equals("전체재직상태")){
            evaluationEntityList = evaluationRepository.findByEoccupational_group_AndEwriter(firm, title, status);
        } else if (!title.equals("전체 직군")){
            evaluationEntityList = evaluationRepository.findByEoccupational_group(firm,title);
        } else if (!status.equals("전체재직상태")){
            evaluationEntityList = evaluationRepository.findByEwriter(firm, status);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        long ldata;
        for (EvaluationEntity evaluationEntity : evaluationEntityList){
            JSONObject object = new JSONObject();
            try {
                Date date = simpleDateFormat.parse(evaluationEntity.getCreatedate().toString()) ;
                ldata = date.getTime();
                object.put("countdate", ldata);
            } catch (Exception e){
                e.printStackTrace();
            }
            object.put("eno",evaluationEntity.getEno());
            object.put("oneline", evaluationEntity.getEoneline());
            object.put("ewriter", evaluationEntity.getEwriter());
            object.put("eoccupational_group", evaluationEntity.getEoccupational_group());
            object.put("createdate", evaluationEntity.getCreatedate().toString().split("T")[0]);
            object.put("pros",evaluationEntity.getEpros());
            object.put("cons", evaluationEntity.getEcons());
            object.put("totalstar", evaluationEntity.getGradeEntity().getGtotal());
            object.put("careerstar",evaluationEntity.getGradeEntity().getGcareer());
            object.put("balancestar",evaluationEntity.getGradeEntity().getGbalance());
            object.put("culturestar", evaluationEntity.getGradeEntity().getGculture());
            object.put("managementstar", evaluationEntity.getGradeEntity().getGmanagement());
            object.put("salarystar", evaluationEntity.getGradeEntity().getGmanagement());
            object.put("helpcount",evaluationhelpRepository.gethelpcount(evaluationEntity.getEno()));
            jsonArray.put(object);
        }



        return jsonArray;

    }

    @Transactional
    public JSONObject getdetailstar(int eno){
        EvaluationEntity evaluationEntity = evaluationRepository.findById(eno).get();
        JSONObject object = new JSONObject();
        object.put("totalstar",evaluationEntity.getGradeEntity().getGtotal());
        object.put("careerstar",evaluationEntity.getGradeEntity().getGcareer());
        object.put("balancestar",evaluationEntity.getGradeEntity().getGbalance());
        object.put("salarystar",evaluationEntity.getGradeEntity().getGsalary());
        object.put("culturestar",evaluationEntity.getGradeEntity().getGculture());
        object.put("managementstar",evaluationEntity.getGradeEntity().getGmanagement());
        return object;
    }

    @Transactional
    public boolean onhelp(EvaluationhelpDto evaluationhelpDto){
        // 도움이 돼요 버튼을 누르면
            // DB 검사(eno & mno 입력받아 where and ) 후 없으면 save [둘다 일치]
            // 있으면 delete (도움이 돼요 취소)
            // mno? 인증정보?
            // 평가번호(eno)따져서 몇개인지 return
        EvaluationhelpEntity evaluationhelpEntity = evaluationhelpDto.toEntity();
        Optional<EvaluationhelpEntity> optional = evaluationhelpRepository.check(evaluationhelpEntity.getEno(),evaluationhelpEntity.getEmail());
        if (optional.isPresent()){
            evaluationhelpEntity = evaluationhelpRepository.getehno(evaluationhelpEntity.getEno(),evaluationhelpEntity.getEmail());
            evaluationhelpRepository.delete(evaluationhelpEntity);
        } else {
            evaluationhelpRepository.save(evaluationhelpEntity);
        }


        return true;
    }

}
