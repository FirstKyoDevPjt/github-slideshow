package ipmn.batch.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ipmn.batch.service.CronTiamsService;
import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronTiamsVO;
import ipmn.common.util.IpmnGetProperties;

@Service("CronTiamsService")
public  class CronTiamsServiceImpl implements CronTiamsService   {

    private static final Logger logger = LoggerFactory.getLogger(CronTiamsServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
    public void CronTiamsResult(CronResultVO vo) throws Exception{
    	
		logger.debug("======== CronBatchResult  start  =======");

    	sqlSession.insert("CronTiams.CronTiamsResult", vo);

    }

	public int CronTiamsCnt() throws Exception {
		logger.debug("======== CronTiamsCnt  start  =======");

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronTiams.CronTiamsCnt");

		return tCnt;
	}

    
	public int getJSONLoopCount() throws Exception{

		logger.debug("======== getJSONLoopCount  start  =======");
		
		BufferedReader in = null;
		int cnt = 0;
		 
		logger.debug("  url  IpmnGetProperties :: " + IpmnGetProperties.getProperty("TiAMS.Chk.Url"));
		
		URL url = new URL(IpmnGetProperties.getProperty("TiAMS.Chk.Url"));
		
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		  
		con.setRequestMethod("GET");
		  
		con.setRequestProperty("systemId", IpmnGetProperties.getProperty("TiAMS.systemId"));
		con.setRequestProperty("key", IpmnGetProperties.getProperty("TiAMS.key"));
					
		// 정상 호출
		in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 
		String line; 
		StringBuffer response = new StringBuffer();
				
		while((line = in.readLine()) != null) {
			  
			logger.debug(" 정상 호출  data ok   :: ");
			
			response.append(line);												
		}	
				
		in.close();
				
		//Json파싱
		JSONParser jsonParser = new JSONParser();
				
		// JSON데이터를 넣어 JSON Object 로 만들어 준다.
		JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
				
		// 반복횟수 
		cnt= Integer.parseInt(String.valueOf(jsonObject.get("totalPage")));
		
		return cnt;
		 
	}
	
	 public JSONArray getTiamsJson(int cnt) throws Exception {

		logger.debug("======== getTiAMSJson  start  =======");
		
		BufferedReader in = null;
		JSONArray TiamsArray = null;	
		
		String apiURL = IpmnGetProperties.getProperty("TiAMS.Url")+String.valueOf(cnt);
		 
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
				
		con.setRequestMethod("GET");
		 
		con.setRequestProperty("systemId", IpmnGetProperties.getProperty("TiAMS.systemId"));
		con.setRequestProperty("key", IpmnGetProperties.getProperty("TiAMS.key"));
						
		// 정상 호출
		in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 
		String line; 
		StringBuffer response = new StringBuffer();
				
		while((line = in.readLine()) != null) { 
			response.append(line);
		}	
				
		JSONParser jsonParser = new JSONParser();
				
		// JSON데이터를 넣어 JSON Object 로 만들어 준다.
		JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
		TiamsArray = (JSONArray)jsonObject.get("dataSets");    
				
		in.close();
		return TiamsArray;
		
	 }
	 	 
	 public void executeTiamsArrayInsert(JSONArray TiamsArray) throws Exception{

			 CronTiamsVO vo = new  CronTiamsVO();
		     	
			 for(int i=0; i<TiamsArray.size(); i++){
	                 
				JSONObject 	dataObject = (JSONObject)TiamsArray.get(i);	
									
				vo.setAssetnosch(String.valueOf(dataObject.get("assetNoSch")));	
				vo.setAssetno(String.valueOf(dataObject.get("assetNo")));
				vo.setAssetnoold(String.valueOf(dataObject.get("assetNoOld")));						  	
				vo.setConfno(String.valueOf(dataObject.get("confNo")));							  
				vo.setAssetstatcd(String.valueOf(dataObject.get("assetStatCd")));	
				vo.setAssetstatnm(String.valueOf(dataObject.get("assetStatNm")));	
				vo.setModelnm(String.valueOf(dataObject.get("modelNm")));	
				vo.setMnftnm(String.valueOf(dataObject.get("mnftNm")));	
				vo.setSvcdomainno(String.valueOf(dataObject.get("svcDomainNo")));	
				vo.setSvcdomainnm(String.valueOf(dataObject.get("svcDomainNm")));	
				vo.setSvcno(String.valueOf(dataObject.get("svcNo")));	
				vo.setSvcnm(String.valueOf(dataObject.get("svcNm")));	
				vo.setOpdeptid(String.valueOf(dataObject.get("opDeptId")));	
				vo.setOpdeptnm(String.valueOf(dataObject.get("opDeptNm")));	
				vo.setBizdeptid(String.valueOf(dataObject.get("bizDeptId")));	
				vo.setBizdeptnm(String.valueOf(dataObject.get("bizDeptNm")));	
				vo.setSktinfrauserid(String.valueOf(dataObject.get("sktInfraUserId")));	
				vo.setSktinfrausernm(String.valueOf(dataObject.get("sktInfraUserNm")));	
				vo.setErpno(String.valueOf(dataObject.get("erpNo")));	
				vo.setErpacqsdt(String.valueOf(dataObject.get("erpAcqSdt")));	
				vo.setMainipaddr(String.valueOf(dataObject.get("mainIpAddr")));
				vo.setHostnm(String.valueOf(dataObject.get("hostNm")));
				vo.setDivlcd(String.valueOf(dataObject.get("divLCd")));
				vo.setDivlnm(String.valueOf(dataObject.get("divLNm")));
				vo.setDivmcd(String.valueOf(dataObject.get("divMCd")));
				vo.setDivmnm(String.valueOf(dataObject.get("divMNm")));
				vo.setDivscd(String.valueOf(dataObject.get("divSCd")));
				vo.setDivsnm(String.valueOf(dataObject.get("divSNm")));
									
				//데이터 insert 
				CronTiamsInsert(vo);			    
					
			}	
			
	}   
    
    public void CronTiamsInsert(CronTiamsVO vo) throws Exception{
    	
		logger.debug("======== CronTiamsInsert  start  =======");

		sqlSession.insert("CronTiams.CronTiamsInsert", vo);
    }
    
    public void CronTiamsDelete() throws Exception{
    	
		logger.debug("======== CronTiamsDelete  start  =======");

		sqlSession.delete("CronTiams.CronTiamsDelete");
    }
	    
}
