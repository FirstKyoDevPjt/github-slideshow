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
import ipmn.batch.service.CronElkSolidService;
import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronSolidVO;

public class CronElkSolidScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronElkSolidScheduler.class);

	@Autowired
	private CronElkSolidService CronElkSolidService;

//    public static void main(String[] args) {
    	@Scheduled(cron="0 40 03 * * *")
    	public void CronElkSolidRun() {

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
                .addIndex("asset_version_status")  // asset_version_status
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
				logger.info("==========  CRON ELK SOLID BATCH START  ==========");
				
				CronElkSolidService.CronSolidDelete();
//				CronSolidService.getSolidJson();
			}
            
            //처음 1만건을 읽을것임.
            for (int i = 0; i < hits.size(); i++) {
            	
              	CronSolidVO  vo= new CronSolidVO();
              	
                //logger.info(hits.get(i).toString());
                JsonObject obj = hits.get(i).getAsJsonObject();
                JsonObject src = obj.getAsJsonObject("_source");
//                String ip = src.get("IpAddress").getAsString();

        		vo.setIp(String.valueOf(src.get("IpAddress").getAsString()));	

        		JsonArray arrayIp = src.getAsJsonArray("IpAddressAll");
                
                vo.setIpall(StringUtils.join(arrayIp,",").replaceAll("\"", ""));
        		
                vo.setHostname(String.valueOf(src.get("HostName").getAsString()));	
        		vo.setOs(String.valueOf(src.get("CategoryType").getAsString()));	
        		vo.setOsdetail(String.valueOf(src.get("VersionOrUrl").getAsString()));	
        		vo.setSsostype(String.valueOf(src.get("CategoryName").getAsString()));	

    			CronElkSolidService.CronSolidInsert(vo);
                
                totalCount++;
            }

            //scroll id를 얻음. - 데이타를 모두(1만건이 넘는경우)읽을 경우 scroll id가 필요함.
            String scrollId = result.getJsonObject().get("_scroll_id").getAsString();

            // scroll id를 이용해서 다음 1만건을 읽음.
            for (int i = 1; i < 10000; i++) {
            	
              	CronSolidVO  vo= new CronSolidVO();
            	
                SearchScroll scroll = new SearchScroll.Builder(scrollId, "5m").build();
                result = client.execute(scroll);
                hits = result.getJsonObject().getAsJsonObject("hits").getAsJsonArray("hits");

                //만약 hits가 0이면 다 읽었음. break
                if (hits.size() == 0 || hits.isJsonNull()) {
                    logger.info("totalCount  = " + totalCount);
                    break;
                }

                // hits가 있다면 읽어들임.
                for (int k = 0; k < hits.size(); k++) {
                    logger.info(hits.get(k).toString()); // row의 전체
                    JsonObject obj = hits.get(k).getAsJsonObject();
                    JsonObject src = obj.getAsJsonObject("_source");
//                    String ip = src.get("IpAddress").getAsString();

            		vo.setIp(String.valueOf(src.get("IpAddress").getAsString()));	

            		JsonArray arrayIp = src.getAsJsonArray("IpAddressAll");
					
					vo.setIpall(StringUtils.join(arrayIp,",").replaceAll("\"", ""));
            		
					vo.setHostname(String.valueOf(src.get("HostName").getAsString()));	
            		vo.setOs(String.valueOf(src.get("CategoryType").getAsString()));	
            		vo.setOsdetail(String.valueOf(src.get("VersionOrUrl").getAsString()));	
            		vo.setSsostype(String.valueOf(src.get("CategoryName").getAsString()));	

        			CronElkSolidService.CronSolidInsert(vo);

                    totalCount++;
                }
                
                scrollId = result.getJsonObject().getAsJsonPrimitive("_scroll_id").getAsString();

            }
            
			int totCnt= CronElkSolidService.CronSolidCnt();
			
			resultvo.setR_cnt(totCnt);
			resultvo.setR_cd("14");
			resultvo.setR_result("CRON ELK SOLID BATCH OK");
			
			CronElkSolidService.CronSolidResult(resultvo);

			logger.info("==========  CRON ELK SOLID BATCH OK  ==========");


        } catch (Exception e) {

			resultvo.setR_cnt(0);
			resultvo.setR_cd("14");
			resultvo.setR_result("CRON ELK SOLID BATCH ERROR");
			
			CronElkSolidService.CronSolidResult(resultvo);

			logger.info("==========  CRON ELK SOLID BATCH ERROR  ==========");

        	e.printStackTrace();
        }
    }
}
