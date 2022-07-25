package vertical.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReviewService {

//    @Transactional
//    public JSONObject getlist() throws IOException{
//        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService/getCorpOutline"); /*URL*/
//        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D"); /*Service Key*/
//        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
//        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10000", "UTF-8")); /*한 페이지 결과 수*/
//        urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*결과형식(xml/json)*/
//        urlBuilder.append("&" + URLEncoder.encode("basDt","UTF-8") + "=" + URLEncoder.encode("20220601", "UTF-8")); /*작업 또는 거래의 기준이 되는 일자(년월일)*/
//        urlBuilder.append("&" + URLEncoder.encode("corpNm","UTF-8") + "=" + URLEncoder.encode("삼성", "UTF-8"));
////        urlBuilder.append("&" + URLEncoder.encode("crno","UTF-8") + "=" + URLEncoder.encode("1101115131886", "UTF-8"));
////        urlBuilder.append("&" + URLEncoder.encode("crno","UTF-8") + "=" + URLEncoder.encode("1615110011795", "UTF-8"));
//
//        URL url = new URL(urlBuilder.toString());
//        System.out.println(urlBuilder.toString());
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//        System.out.println("Response code: " + conn.getResponseCode());
//        BufferedReader rd;
//        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        } else {
//            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//        }
//        StringBuilder sb = new StringBuilder();
//        String result = "";
//        String line;
//        while ((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//        rd.close();
//        conn.disconnect();
//        result = sb.toString();
//
//        String urlBuilder2 = "https://api.odcloud.kr/api/15049592/v1/uddi:7851886d-fa5e-445d-bc92-ca6a6d1bac8c?page=1&perPage=2343&serviceKey=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D";
//        URL url2 = new URL(urlBuilder2);
//        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
//        conn2.setRequestMethod("GET");
//        conn2.setRequestProperty("Content-type", "application/json");
//        BufferedReader rd2;
//        if(conn2.getResponseCode() >= 200 && conn2.getResponseCode() <= 300){
//            rd2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
//        } else {
//            rd2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
//        }
//        StringBuilder sb2 = new StringBuilder();
//        String result2 = "";
//        String line2;
//        while ((line2 = rd2.readLine()) != null){
//            sb2.append(line2);
//        }
//        rd2.close();
//        conn2.disconnect();
//        result2 = sb2.toString();
//        System.out.println(urlBuilder2.toString());
//
//        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//        JSONArray jsonArray = new JSONArray();
//        JSONObject object = new JSONObject();
//
//
//        try {
//            if (result != null && result.trim().length() > 0){
//                JSONObject maindata = new JSONObject(result);
////                System.out.println(maindata);
//                JSONObject response = maindata.getJSONObject("response");
////                System.out.println(response);
//                JSONObject body = response.getJSONObject("body");
////                System.out.println(body);
//                JSONObject items = body.getJSONObject("items");
////                System.out.println(items);
//                JSONArray item = items.getJSONArray("item");
////                System.out.println(item);
//                JSONArray item2 = new JSONObject(result2).getJSONArray("data");
//
//                for (int i = 0; i < item.length(); i++){
//
//                    JSONObject data = item.getJSONObject(i);
//
//
//                    if (!data.getString("enpHmpgUrl").equals("") && !data.getString("enpEmpeCnt").equals("0") && !data.getString("enpPn1AvgSlryAmt").equals("0")){
//
//                        String name = data.getString("corpNm");
//                        String homepage = data.getString("enpHmpgUrl");
//
//                        String industry = "";
//
//                        for (int j = 0; j <item2.length(); j++){
//
//                            JSONObject data2 = item2.getJSONObject(j);
//
//                            if (data.getString("sicNm").equals(data2.getString("업종코드"))){
//                                industry = data2.getString("업종명");
//                            }
//
//                        }
//
//                        String base_address = data.getString("enpBsadr");
//                        SimpleDateFormat dateparser = new SimpleDateFormat("yyyyMMdd");
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
//
//                        Date date = dateparser.parse( data.getString("enpEstbDt"));
//
//                        String establish_date = simpleDateFormat.format(date);
//
//                        String employees = data.getString("enpEmpeCnt");
//                        String salary = data.getString("enpPn1AvgSlryAmt");
//
//
//                        JSONObject jsonObject = new JSONObject();
//
////                        map.put("name", name);
////                        map.put("homepage", homepage);
////                        map.put("industry", industry);
////                        map.put("base_address", base_address);
////                        map.put("establish_date", establish_date);
////                        map.put("employees", employees);
////                        map.put("salary", salary);
//                        jsonObject.put("name", name);
//                        jsonObject.put("homepage", homepage);
//                        jsonObject.put("industry", industry);
//                        jsonObject.put("base_address", base_address);
//                        jsonObject.put("establish_date", establish_date);
//                        jsonObject.put("employees", employees);
//                        jsonObject.put("salary", salary);
//                        jsonArray.put(jsonObject);
//
////                        list.add(map);
//                    }
//
//
//                    object.put("data", jsonArray);
//
//
//                }
//
//
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return object;
//    }

    ///////////////////////////+++++++++++++기업인기리스트출력++++++++++++++++++/////////////////////////
    public JSONArray getpopularlist() throws IOException {
        //1101115131886 (주)베스파
        //1615110011795 세메스 주식회사
        //1101110085450 현대자동차(주)
        //1101110769583 SK(주)
        //1101111707178 네이버(주)
        //1101110003543 (주)LG
        //1101110013774 (주)두산
        //1101111468754 (주)케이티
        //1301110006246  삼성전자(주)
        JSONArray jsonArray = new JSONArray();

        String apilist[] = {"http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService/getCorpOutline?serviceKey=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D&pageNo=1&numOfRows=1&resultType=JSON&basDt=20220601&crno=1101115131886",//(주)베스파
                            "http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService/getCorpOutline?serviceKey=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D&pageNo=1&numOfRows=1&resultType=JSON&basDt=20220601&crno=1615110011795",//세메스 주식회사
                            "http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService/getCorpOutline?serviceKey=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D&pageNo=1&numOfRows=1&resultType=JSON&basDt=20220601&crno=1101110085450",//현대자동차(주)
                            "http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService/getCorpOutline?serviceKey=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D&pageNo=1&numOfRows=1&resultType=JSON&basDt=20220601&crno=1101110769583",//SK(주)
                            "http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService/getCorpOutline?serviceKey=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D&pageNo=1&numOfRows=1&resultType=JSON&basDt=20220601&crno=1101111707178",//네이버(주)
                            "http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService/getCorpOutline?serviceKey=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D&pageNo=1&numOfRows=1&resultType=JSON&basDt=20220601&crno=1101110003543",//(주)LG
                            "http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService/getCorpOutline?serviceKey=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D&pageNo=1&numOfRows=1&resultType=JSON&basDt=20220601&crno=1101110013774",//(주)두산
                            "http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService/getCorpOutline?serviceKey=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D&pageNo=1&numOfRows=1&resultType=JSON&basDt=20220601&crno=1101111468754",//(주)케이티
                            "http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService/getCorpOutline?serviceKey=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D&pageNo=1&numOfRows=5&resultType=JSON&basDt=20220601&corpNm=%EC%82%BC%EC%84%B1%EC%A0%84%EC%9E%90"};//삼성전자(주)


        for (int a = 0; a < apilist.length; a++){
            URL url = new URL(apilist[a]);
//            System.out.println(url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String result = "";
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            result = sb.toString();

            try {
                if (result != null && result.trim().length() > 0){
                    JSONObject maindata = new JSONObject(result);

                    JSONObject response = maindata.getJSONObject("response");

                    JSONObject body = response.getJSONObject("body");

                    JSONObject items = body.getJSONObject("items");

                    JSONArray item = items.getJSONArray("item");

                    for (int i = 0; i < item.length(); i++){

                        JSONObject data = item.getJSONObject(i);

                        if (!data.getString("enpHmpgUrl").equals("") && !data.getString("enpEmpeCnt").equals("0") && !data.getString("enpPn1AvgSlryAmt").equals("0")){

                            String name = data.getString("corpNm");
                            String name2 = name.replace("(주)","").replace(" ","").replace("주식회사","");
                            String img = null;
                    ///////////////////////////////크롤링///////////////////////
//                            String ename = data.getString("corpEnsnNm");
//                            String ename2 = ename.split(" ")[0];

//                            String crawlingurl ="";
//                            Connection connection = null;
//                            Document document = null;
//
//                            try {
//                                 crawlingurl = "https://www.teamblind.com/kr/company/" + name2;
//                                 connection = Jsoup.connect(crawlingurl);
//                                 document = connection.get();
//
//
//                            }catch (Exception e){
//                                crawlingurl = "https://www.teamblind.com/kr/company/" + ename2;
//                                connection = Jsoup.connect(crawlingurl);
//                                 document = connection.get();
//                            }
//                            // 조건문 달아서 url이 404페이지일경우 대처
//
//                            Elements elements = document.getElementsByClass("img");
//                            Elements tags = elements.first().getElementsByTag("img");
//                            img = tags.attr("src");
                    ///////////////////////////////크롤링///////////////////////

                            JSONObject jsonObject = new JSONObject();
//                            jsonObject.put("img", img);
                            jsonObject.put("name", name2);
//                            jsonObject.put("ename", ename2);
//                            System.out.println("name : "+name2);
//                            System.out.println("ename : "+ename2);

                            jsonArray.put(jsonObject);
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return jsonArray;
    }
    ///////////////////////////+++++++++++++기업인기리스트출력 END++++++++++++++++++/////////////////////////


    ///////////////////////////+++++++++++++기업상세보기++++++++++++++++++/////////////////////////
    @Transactional
    public JSONObject getcorp(String corpNm) throws IOException{
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService/getCorpOutline"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*결과형식(xml/json)*/
        urlBuilder.append("&" + URLEncoder.encode("basDt","UTF-8") + "=" + URLEncoder.encode("20220601", "UTF-8")); /*작업 또는 거래의 기준이 되는 일자(년월일)*/
        urlBuilder.append("&" + URLEncoder.encode("corpNm","UTF-8") + "=" + URLEncoder.encode(corpNm, "UTF-8"));
//        urlBuilder.append("&" + URLEncoder.encode("crno","UTF-8") + "=" + URLEncoder.encode("1101115131886", "UTF-8"));
//        urlBuilder.append("&" + URLEncoder.encode("crno","UTF-8") + "=" + URLEncoder.encode("1615110011795", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
//        System.out.println(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String result = "";
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        result = sb.toString();

        String urlBuilder2 = "https://api.odcloud.kr/api/15049592/v1/uddi:7851886d-fa5e-445d-bc92-ca6a6d1bac8c?page=1&perPage=2343&serviceKey=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D";
        URL url2 = new URL(urlBuilder2);
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setRequestMethod("GET");
        conn2.setRequestProperty("Content-type", "application/json");
        BufferedReader rd2;
        if(conn2.getResponseCode() >= 200 && conn2.getResponseCode() <= 300){
            rd2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
        } else {
            rd2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
        }
        StringBuilder sb2 = new StringBuilder();
        String result2 = "";
        String line2;
        while ((line2 = rd2.readLine()) != null){
            sb2.append(line2);
        }
        rd2.close();
        conn2.disconnect();
        result2 = sb2.toString();
//        System.out.println(urlBuilder2.toString());

        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        JSONArray jsonArray = new JSONArray();
        JSONObject object = new JSONObject();

        try {
            if (result != null && result.trim().length() > 0){
                JSONObject maindata = new JSONObject(result);
//                System.out.println(maindata);
                JSONArray response = maindata.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
//                System.out.println(response);
                JSONArray item2 = new JSONObject(result2).getJSONArray("data");

                for (int i = 0; i < response.length(); i++){

                    JSONObject data = response.getJSONObject(i);

                    if (!data.getString("enpHmpgUrl").equals("") && !data.getString("enpEmpeCnt").equals("0") && !data.getString("enpPn1AvgSlryAmt").equals("0")){

                        String name = data.getString("corpNm");
                        String name2 = name.replace("(주)","").replace(" ","").replace("주식회사","");
                        String homepage = data.getString("enpHmpgUrl");
                        String img = null;



                        String ename = data.getString("corpEnsnNm");
                        String ename2 = ename.split(" ")[0];

                        String crawlingurl ="";
                        Connection connection = null;
                        Document document = null;

                        Connection newsconnection = null;//뉴스크롤링
                        Document newsdocument = null;//뉴스크롤링
                        String newsname = null;//뉴스크롤링하기위해 url에 이름넣을려고
                        try {
                            crawlingurl = "https://www.teamblind.com/kr/company/" + name2;
                            connection = Jsoup.connect(crawlingurl);
                            document = connection.get();
                            newsname = name2;

                        }catch (Exception e){
                            crawlingurl = "https://www.teamblind.com/kr/company/" + ename2;
                            connection = Jsoup.connect(crawlingurl);
                            document = connection.get();
                            newsname = ename2;
                        }

                        ///////////////////////////////////////뉴스크롤링///////////////////////////////
                        //String newsurl = "https://search.naver.com/search.naver?where=news&query="+newsname+"&sm=// tab_opt&sort=0&photo=0&field=0&pd=0&ds=&de=&docid=&related=0&mynews=0&office_type=0&office_section_code=0&news_office_checked=&nso=so%3Ar%2Cp%3Aall&is_sug_officeid=0";
                        String newsurl = "https://search.naver.com/search.naver?where=news&query="+newsname+"&sm=tab_opt&sort=0&photo=0&field=0&pd=0&ds=&de=&docid=&related=0&mynews=0&office_type=0&office_section_code=0&news_office_checked=&nso=so%3Ar%2Cp%3Aall&is_sug_officeid=0";

                        newsconnection = Jsoup.connect(newsurl);
                        newsdocument = newsconnection.get();


                        Elements newsinfo_press = newsdocument.getElementsByClass("info press");
                        Elements newsnews_tit = newsdocument.getElementsByClass("news_tit");
                        Elements dsc_wrap = newsdocument.getElementsByClass("dsc_wrap");

                        JSONArray infopressarray = new JSONArray();
                        for( int z = 0 ; z<2; z++ ){

                            infopressarray.put(newsinfo_press.get(z).toString().replace("언론사 선정",""));
//                            System.out.println("asdasd:   "+newsinfo_press.get(z).text());
//                            System.out.println("testinfopressarray"+infopressarray.get(z));
                        }
                        JSONArray newstitarray_a = new JSONArray();
                        JSONArray newstitarray = new JSONArray();
                        for (int z = 0; z < 2; z++){
                            newstitarray.put(newsnews_tit.get(z));
                        }
                        for( int z = 0 ; z<2; z++ ){
                            newstitarray_a.put(newsnews_tit.get(z).attr("href"));
//                            System.out.println("zxczxc:   "+newsnews_tit.get(z).text());
//                            System.out.println("testnewstitarray"+newstitarray.get(z));
                        }
                        JSONArray dscwraparray = new JSONArray();
                        for( int z = 0 ; z<2; z++ ){
                            dscwraparray.put(dsc_wrap.get(z).text());
//                            System.out.println("qweqwe:   "+dsc_wrap.get(z).text() );
//                            System.out.println("testdscwraparray"+dscwraparray.get(z));
                        }

                            //System.out.println("test"+newsarray.get(z));


//                        JSONObject info_press = new JSONObject();
//                        info_press.put("newsinfo",newsinfo_press.text());
//                        System.out.println("JSONObject : "+info_press);
//
//                        JSONArray info_array = new JSONArray();
//                        for (int a = 0; a <info_press.length(); a++){
//                            info_array.put(info_press);
//                        }
//                        System.out.println("JSONArray : " + info_array);

//                        Elements newsnews_tit = newsdocument.getElementsByClass("news_tit");
//                        JSONArray newstit = new JSONArray();
//                        for(int t =0; t<newsnews_tit.size(); t++){
//                            newstit.put(newsnews_tit);
//                        }
//
//                        String api_txt_lines_dsc_txt_wrap = newsdocument.getElementsByClass("dsc_wrap").text();
//                        JSONArray dscwrap = new JSONArray();
//                        for(int d = 0; d<api_txt_lines_dsc_txt_wrap.length(); d++){
//                            dscwrap.put(api_txt_lines_dsc_txt_wrap);
//                        }

                        Elements newsthumb = newsdocument.getElementsByClass("dsc_thumb");//뉴스사진
                        JSONArray newsimgnewsimg = new JSONArray();
                        for( int q = 0 ; q<newsthumb.size() ; q++ ){
                            Elements newsimg =  newsthumb.get(q).getElementsByTag("img");
                            newsimgnewsimg.put( newsimg.attr("src") );
                        }
                        //System.out.println(newsthumb.toString());
                        // 조건문 달아서 url이 404페이지일경우 대처
                        ///////////////////////////////////////뉴스크롤링end///////////////////////////////

                        Elements elements = document.getElementsByClass("img");//기업사진
                        Elements tags = elements.first().getElementsByTag("img");
                        img = tags.attr("src");

                        String industry = "";

                        for (int j = 0; j <item2.length(); j++){

                            JSONObject data2 = item2.getJSONObject(j);

                            if (data.getString("sicNm").equals(data2.getString("업종코드"))){
                                industry = data2.getString("업종명");
                            }
                        }

                        String[] base_address = data.getString("enpBsadr").split(" ");
                        SimpleDateFormat dateparser = new SimpleDateFormat("yyyyMMdd");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");

                        Date date = dateparser.parse( data.getString("enpEstbDt"));

                        String establish_date = simpleDateFormat.format(date);

                        String employees = data.getString("enpEmpeCnt");
                        DecimalFormat decimal = new DecimalFormat("#,###");
                        String salary = decimal.format(Integer.parseInt(data.getString("enpPn1AvgSlryAmt")));




                        JSONObject jsonObject = new JSONObject();
//                        map.put("name", name);
//                        map.put("homepage", homepage);
//                        map.put("industry", industry);
//                        map.put("base_address", base_address);
//                        map.put("establish_date", establish_date);
//                        map.put("employees", employees);
//                        map.put("salary", salary);
                        jsonObject.put("img", img);
                        jsonObject.put("name", name2);
                        jsonObject.put("homepage", homepage);
                        jsonObject.put("industry", industry);
                        jsonObject.put("base_address", base_address[0]+" "+base_address[1]);
                        jsonObject.put("establish_date", establish_date);
                        jsonObject.put("employees", employees);
                        jsonObject.put("salary", salary);
                        jsonObject.put("infopress", infopressarray);//뉴스크롤링 신문사이름
                        jsonObject.put("newstit", newstitarray);//뉴스제목
                        jsonObject.put("newstit_a", newstitarray_a);
                        jsonObject.put("dscwrap", dscwraparray);//뉴스내용
                        jsonObject.put("newsimgnewsimg", newsimgnewsimg);//뉴스사진
//                        System.out.println(info_press.toString());
                        jsonArray.put(jsonObject);
//                        list.add(map);
                    }
                    object.put("data", jsonArray);
                    //System.out.println("test"+newsarray);
                    //System.out.println("test"+jsonArray);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return object;
    }
    ///////////////////////////+++++++++++++기업상세보기 END ++++++++++++++++++/////////////////////////

    ///////////////////////////+++++++++++++기업리뷰에서검색++++++++++++++++++/////////////////////////
    @Transactional
    public JSONArray search(String corpNm) throws IOException{

        JSONArray jsonArray = new JSONArray();

        StringBuilder api = new StringBuilder("http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService/getCorpOutline?serviceKey=b1ZLQekIqF%2BgTinrA%2BLLxOLQV2pJtjZOOFhC0iKRgmUFqtzRMDdEFk1P9adyIB1c9cVyvk8suTg%2Btmz%2BF8kJGQ%3D%3D&pageNo=1&numOfRows=100&resultType=JSON&basDt=20220601");

        api.append("&" + URLEncoder.encode("corpNm","UTF-8") + "=" + URLEncoder.encode(corpNm, "UTF-8"));

        URL url = new URL(api.toString());
//        System.out.println(url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String result = "";
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        result = sb.toString();

        try {
            if (result != null && result.trim().length() > 0){
                JSONObject maindata = new JSONObject(result);

                JSONObject response = maindata.getJSONObject("response");

                JSONObject body = response.getJSONObject("body");

                JSONObject items = body.getJSONObject("items");

                JSONArray item = items.getJSONArray("item");

                for (int i = 0; i < item.length(); i++){

                    JSONObject data = item.getJSONObject(i);

                    if (!data.getString("enpHmpgUrl").equals("") && !data.getString("enpEmpeCnt").equals("0") && !data.getString("enpPn1AvgSlryAmt").equals("0")){

                        String name = data.getString("corpNm");
                        String name2 = name.replace("(주)","").replace(" ","").replace("주식회사","");
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("name", name2);

                        jsonArray.put(jsonObject);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return jsonArray;
    }
    ///////////////////////////+++++++++++++기업리뷰에서검색 END ++++++++++++++++++/////////////////////////

    /////////////////////////======채용공고 크롤링======////////////////
//    @Transactional


    /////////////////////======채용공고 크롤링 END======////////////////



}