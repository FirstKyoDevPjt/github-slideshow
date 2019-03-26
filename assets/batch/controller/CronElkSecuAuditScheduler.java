package ipmn.batch.controller;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchScroll;
import io.searchbox.params.Parameters;
import ipmn.batch.service.CronElkSecuAuditService;
import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronSecuAuditVO;

public class CronElkSecuAuditScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronElkSecuAuditScheduler.class);

	@Autowired
	private CronElkSecuAuditService CronElkSecuAuditService;

    	@Scheduled(cron="0 40 05 * * *")
    	public void CronElkSecuAuditRun() {

   		CronResultVO resultvo = new CronResultVO();
    		
        JestClientFactory factory = new JestClientFactory();

        // 접속정보 설정.
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://150.206.14.237:9200")
                .defaultCredentials("ip_manage", "dkdlvlmanage2019")
                .multiThreaded(true)
                .defaultMaxTotalConnectionPerRoute(2)
                .maxTotalConnection(1)
                .build());

        JestClient client = factory.getObject();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 한번에 10000건씩 가져온다.(10000보다 높은수를 설정하면 오류가 발생합니다.
        searchSourceBuilder.query(QueryBuilders.matchAllQuery()).size(10000);

        //검색조건 및 인덱스 지정.
        Search search = new Search.Builder(searchSourceBuilder.toString())
              .addIndex("asset_audit_item_status_all")  // asset_version_status
                .addType("SDL")
                //.addSort(new Sort("code"))
                .setParameter(Parameters.SCROLL, "5m")
                .build();
        try {

            int totalCount = 0; //종 건수

            //검색 실행
            JestResult result = client.execute(search);

            // hits 부분이 실제 검색결과가 들어있음.
            JsonArray hits = result.getJsonObject().getAsJsonObject("hits").getAsJsonArray("hits");

			if (hits.size() > 0){
				logger.info("==========  CRON ELK SECU AUDIT BATCH START  ==========");
				
				CronElkSecuAuditService.CronSecuAuditDelete();
			}
		
            //처음 1만건을 읽을것임.
            for (int i = 0; i < hits.size(); i++) {
            	
            	CronSecuAuditVO  vo= new CronSecuAuditVO();
              	
//                logger.info(hits.get(i).toString());
                JsonObject obj = hits.get(i).getAsJsonObject();
                JsonObject src = obj.getAsJsonObject("_source");

                if(src.getAsJsonObject().get("prj_id").isJsonNull()) {
                	vo.setPrjId("");
                } else {
                    vo.setPrjId(String.valueOf(StringUtils.stripToEmpty(src.get("prj_id").getAsString())));
                }

                if(src.getAsJsonObject().get("data_type").isJsonNull()) {
                	vo.setDataType("");
                } else {
                    vo.setDataType(String.valueOf(StringUtils.stripToEmpty(src.get("data_type").getAsString())));
                }
                
                if(src.getAsJsonObject().get("obj_complete_date").isJsonNull()) {
                	vo.setObjCompleteDate("");
                } else {
                    vo.setObjCompleteDate(String.valueOf(StringUtils.stripToEmpty(src.get("obj_complete_date").getAsString())));
                }
                
                if(src.getAsJsonObject().get("ip").isJsonNull()) {
                	vo.setIp("");
                } else {
                    vo.setIp(String.valueOf(StringUtils.stripToEmpty(src.get("ip").getAsString())));
                }
                
                if(src.getAsJsonObject().get("host").isJsonNull()) {
                	vo.setHost("");
                } else {
                    vo.setHost(String.valueOf(StringUtils.stripToEmpty(src.get("host").getAsString())));
                }

                if(src.getAsJsonObject().get("url").isJsonNull()) {
                	vo.setUrl("");
                } else {
                	vo.setUrl(String.valueOf(StringUtils.stripToEmpty(src.get("url").getAsString())));
                }

                if(src.getAsJsonObject().get("url_state").isJsonNull()) {
                	vo.setUrlState("");
                } else {
                    vo.setUrlState(String.valueOf(StringUtils.stripToEmpty(src.get("url_state").getAsString())));
                }
                
                if(src.getAsJsonObject().get("opr1_emp_no").isJsonNull()) {
                	vo.setOpr1EmpNo("");
                } else {
                    vo.setOpr1EmpNo(String.valueOf(StringUtils.stripToEmpty(src.get("opr1_emp_no").getAsString())));
                }

                if(src.getAsJsonObject().get("opr2_emp_no").isJsonNull()) {
                	vo.setOpr2EmpNo("");
                } else {
                    vo.setOpr2EmpNo(String.valueOf(StringUtils.stripToEmpty(src.get("opr2_emp_no").getAsString())));
                }
                
                if(src.getAsJsonObject().get("tiams").isJsonNull()) {
                	vo.setTiams("");
                } else {
                    vo.setTiams(String.valueOf(StringUtils.stripToEmpty(src.get("tiams").getAsString())));
                }
                
                if(src.getAsJsonObject().get("prj_name").isJsonNull()) {
                	vo.setPrjName("");
                } else {
                    vo.setPrjName(String.valueOf(StringUtils.stripToEmpty(src.get("prj_name").getAsString())));
                }
                
                if(src.getAsJsonObject().get("users").isJsonNull()) {
                	vo.setUsers("");
                } else {
                    JsonArray arrayUser = src.getAsJsonArray("users");
                    vo.setUsers(StringUtils.join(arrayUser).replaceAll("\"", ""));
                    vo.setUsers(vo.getUsers().replaceAll("\\[", ""));
                    vo.setUsers(vo.getUsers().replaceAll("\\]", ""));
                }

        		CronElkSecuAuditService.CronSecuAuditInsert(vo);
                
                totalCount++;
            }
            
            //scroll id를 얻음. - 데이타를 모두(1만건이 넘는경우)읽을 경우 scroll id가 필요함.
            String scrollId = result.getJsonObject().get("_scroll_id").getAsString();

            // scroll id를 이용해서 다음 1만건을 읽음.
            for (int i = 1; i < 10000; i++) {
            	
            	CronSecuAuditVO  vo= new CronSecuAuditVO();
            	
                SearchScroll scroll = new SearchScroll.Builder(scrollId, "5m").build();
                result = client.execute(scroll);
                hits = result.getJsonObject().getAsJsonObject("hits").getAsJsonArray("hits");

                //만약 hits가 0이면 다 읽었음. break
                if (hits.size() == 0 || hits.isJsonNull()) {
//                    logger.info("totalCount  = " + totalCount);
                    break;
                }

                // hits가 있다면 읽어들임.
                for (int k = 0; k < hits.size(); k++) {
//                    logger.info(hits.get(k).toString()); // row의 전체
                    JsonObject obj = hits.get(k).getAsJsonObject();
                    JsonObject src = obj.getAsJsonObject("_source");

                if(src.getAsJsonObject().get("prj_id").isJsonNull()) {
                	vo.setPrjId("");
                } else {
                    vo.setPrjId(String.valueOf(StringUtils.stripToEmpty(src.get("prj_id").getAsString())));
                }
                
                if(src.getAsJsonObject().get("data_type").isJsonNull()) {
                	vo.setDataType("");
                } else {
                    vo.setDataType(String.valueOf(StringUtils.stripToEmpty(src.get("data_type").getAsString())));
                }
                
                if(src.getAsJsonObject().get("obj_complete_date").isJsonNull()) {
                	vo.setObjCompleteDate("");
                } else {
                    vo.setObjCompleteDate(String.valueOf(StringUtils.stripToEmpty(src.get("obj_complete_date").getAsString())));
                }
                
                if(src.getAsJsonObject().get("ip").isJsonNull()) {
                	vo.setIp("");
                } else {
                    vo.setIp(String.valueOf(StringUtils.stripToEmpty(src.get("ip").getAsString())));
                }
                
                if(src.getAsJsonObject().get("host").isJsonNull()) {
                	vo.setHost("");
                } else {
                    vo.setHost(String.valueOf(StringUtils.stripToEmpty(src.get("host").getAsString())));
                }

                if(src.getAsJsonObject().get("url").isJsonNull()) {
                	vo.setUrl("");
                } else {
                	vo.setUrl(String.valueOf(StringUtils.stripToEmpty(src.get("url").getAsString())));
                }

                if(src.getAsJsonObject().get("url_state").isJsonNull()) {
                	vo.setUrlState("");
                } else {
                    vo.setUrlState(String.valueOf(StringUtils.stripToEmpty(src.get("url_state").getAsString())));
                }

                if(src.getAsJsonObject().get("opr1_emp_no").isJsonNull()) {
                	vo.setOpr1EmpNo("");
                } else {
                    vo.setOpr1EmpNo(String.valueOf(StringUtils.stripToEmpty(src.get("opr1_emp_no").getAsString())));
                }

                if(src.getAsJsonObject().get("opr2_emp_no").isJsonNull()) {
                	vo.setOpr2EmpNo("");
                } else {
                    vo.setOpr2EmpNo(String.valueOf(StringUtils.stripToEmpty(src.get("opr2_emp_no").getAsString())));
                }
                
                if(src.getAsJsonObject().get("tiams").isJsonNull()) {
                	vo.setTiams("");
                } else {
                    vo.setTiams(String.valueOf(StringUtils.stripToEmpty(src.get("tiams").getAsString())));
                }
                
                if(src.getAsJsonObject().get("prj_name").isJsonNull()) {
                	vo.setPrjName("");
                } else {
                    vo.setPrjName(String.valueOf(StringUtils.stripToEmpty(src.get("prj_name").getAsString())));
                }
                
                if(src.getAsJsonObject().get("users").isJsonNull()) {
                	vo.setUsers("");
                } else {
                    JsonArray arrayUser = src.getAsJsonArray("users");
                    vo.setUsers(StringUtils.join(arrayUser).replaceAll("\"", ""));
                    vo.setUsers(vo.getUsers().replaceAll("\\[", ""));
                    vo.setUsers(vo.getUsers().replaceAll("\\]", ""));
                }

            		CronElkSecuAuditService.CronSecuAuditInsert(vo);

                    totalCount++;
                }

                scrollId = result.getJsonObject().getAsJsonPrimitive("_scroll_id").getAsString();

            }
            
			int totCnt= CronElkSecuAuditService.CronSecuAuditCnt();
			
			resultvo.setR_cnt(totCnt);
			resultvo.setR_cd("17");
			resultvo.setR_result("CRON ELK SECU AUDIT BATCH OK");
			
			CronElkSecuAuditService.CronSecuAuditResult(resultvo);

			logger.info("==========  CRON ELK SECU AUDIT BATCH OK  ==========" );

        } catch (Exception e) {

			resultvo.setR_cnt(0);
			resultvo.setR_cd("17");
			resultvo.setR_result("CRON ELK SECU AUDIT BATCH ERROE");
			
			CronElkSecuAuditService.CronSecuAuditResult(resultvo);

			logger.info("==========  CRON ELK SECU AUDIT BATCH ERROE  ==========" );

			e.printStackTrace();
        }
    }
}
