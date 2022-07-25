package vertical.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vertical.domain.company.CompanyEntity;
import vertical.domain.company.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    // 메인페이지 회사목록 출력
    public JSONArray getcompanylist(){
        JSONArray jsonArray = new JSONArray();
        // 회사목록 엔티티 검색
        List<CompanyEntity> companyEntityList = companyRepository.findAll();

        for(CompanyEntity entity : companyEntityList){
            JSONObject object = new JSONObject();
            object.put("comname", entity.getComname() );
            object.put("comlogo", entity.getComlogo() );
            jsonArray.put(object);
        } // for end
        return jsonArray;
    }

    // 회사이름 검색
    public JSONObject getcompany(String comname){
        // 회사명 엔티티 검색
        Optional<CompanyEntity> companyoptional = companyRepository.findBycomname(comname);
        if(!companyoptional.isPresent()){return null;}
        CompanyEntity companyEntity = companyoptional.get();

        JSONObject object = new JSONObject();
        object.put("comname", companyEntity.getComname() );
        object.put("comlogo", companyEntity.getComlogo() );
        return object;
    }
}
