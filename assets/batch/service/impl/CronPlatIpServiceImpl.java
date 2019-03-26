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

import ipmn.batch.service.CronPlatIpService;
import ipmn.batch.vo.CronPlatIpVO;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;


@Service("CronPlatIpService")
public  class CronPlatIpServiceImpl implements CronPlatIpService   {

    private static final Logger logger = LoggerFactory.getLogger(CronPlatIpServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
    public void getPlatIpJson() throws Exception {

		logger.debug("======== getPlatIpJson  start  =======");

		 BufferedReader in = null;
		 JSONArray PlatIpArray = null;	
		 
		 String apiURL = IpmnGetProperties.getProperty("PlatIp.Url");
				
		 URL url = new URL(apiURL);
		 HttpURLConnection con = (HttpURLConnection)url.openConnection();

		 con.setRequestMethod("GET");
		 con.setRequestProperty("AuthKey", IpmnGetProperties.getProperty("PlatIp.AuthKey"));

		 // 정상 호출
		 in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 

		 String line; 
		 StringBuffer response = new StringBuffer();
				
		  while((line = in.readLine()) != null) { 
			  response.append(line);
		  }	
				
		  in.close();

		  JSONParser jsonParser = new JSONParser();
				
		   // JSON데이터를 넣어 JSON Object 로 만들어 준다.
		  JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
		  PlatIpArray = (JSONArray)jsonObject.get("Data");    

		  
		  for(int i=0; i<PlatIpArray.size(); i++){
				 
			// 배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
			JSONObject PlatIpObject = (JSONObject) PlatIpArray.get(i);
			//자료저장
			CronPlatIpInsert(executePlatIpInsert(PlatIpObject));
		  }
		  
	 }
	 	 
	 private CronPlatIpVO executePlatIpInsert(JSONObject platIpObject) throws Exception {

		 CronPlatIpVO vo = new  CronPlatIpVO();
	     	
		vo.setIp(String.valueOf(platIpObject.get("IP")));	
		vo.setMac(String.valueOf(platIpObject.get("MAC")));	
		vo.setHostName(String.valueOf(platIpObject.get("Hostname")));						  	
		vo.setSubnetName(String.valueOf(platIpObject.get("SubnetName")));							  
		vo.setManager(String.valueOf(platIpObject.get("Manager")));	
		vo.setMgrNo(String.valueOf(platIpObject.get("MgrNo")));	

		return vo;

	}

    public void CronPlatIpInsert(CronPlatIpVO vo) throws Exception{
    	
		logger.debug("======== CronPlatIpInsert  start  =======");

		sqlSession.insert("CronPlatIp.CronPlatIpInsert", vo);
    }

	@Override
	public void CronPlatIpDelete() throws Exception {

		sqlSession.delete("CronPlatIp.CronPlatIpDelete");
		
	}

	@Override
	public int CronPlatIpCnt() throws Exception {

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronPlatIp.CronPlatIpCnt");

		return tCnt;
	}

	@Override
	public void CronPlatIpResult(CronResultVO vo) throws Exception {

		sqlSession.insert("CronPlatIp.CronPlatIpResult", vo);
		
	}
    
}
