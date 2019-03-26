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

import ipmn.batch.service.CronPlatSubnetService;
import ipmn.batch.vo.CronCmnCdVO;
import ipmn.batch.vo.CronPlatSubnetVO;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;


@Service("CronPlatSubnetService")
public  class CronPlatSubnetServiceImpl implements CronPlatSubnetService   {

    private static final Logger logger = LoggerFactory.getLogger(CronPlatSubnetServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
    public void getPlatSubnetJson() throws Exception {

			logger.debug("======== getPlatSubnetJson  start  =======");

		 BufferedReader in = null;
		 JSONArray PlatSubnetArray = null;	
		 
		 String apiURL = IpmnGetProperties.getProperty("PlatSubnet.Url");
				
		 URL url = new URL(apiURL);
		 HttpURLConnection con = (HttpURLConnection)url.openConnection();

		 con.setRequestMethod("GET");
		 con.setRequestProperty("AuthKey", IpmnGetProperties.getProperty("PlatSubnet.AuthKey"));

		 // 정상 호출
		 in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 

			logger.debug("======== getPlatSubnetJson  정상 호출  =======");
		 
		 String line; 
		 StringBuffer response = new StringBuffer();
				
		  while((line = in.readLine()) != null) { 
			  response.append(line);
		  }	
				
		  in.close();

		  JSONParser jsonParser = new JSONParser();
				
		   // JSON데이터를 넣어 JSON Object 로 만들어 준다.
		  JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
		  PlatSubnetArray = (JSONArray)jsonObject.get("Data");    

		  
		  for(int i=0; i<PlatSubnetArray.size(); i++){
				 
			// 배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
			JSONObject PlatSubnetObject = (JSONObject) PlatSubnetArray.get(i);
			//자료저장
			CronPlatSubnetInsert(executePlatSubnetInsert(PlatSubnetObject));
		  }
		  
	 }
	 	 
	 private CronPlatSubnetVO executePlatSubnetInsert(JSONObject PlatSubnetObject) throws Exception {

		 CronPlatSubnetVO vo = new  CronPlatSubnetVO();
	     	
		vo.setSubnet(String.valueOf(PlatSubnetObject.get("Subnet")));	
		vo.setCidr(String.valueOf(PlatSubnetObject.get("Cidr")));	
		vo.setName(String.valueOf(PlatSubnetObject.get("Name")));						  	
		vo.setClassBand(String.valueOf(PlatSubnetObject.get("Class")));							  
		vo.setNetmask(String.valueOf(PlatSubnetObject.get("Netmask")));	
		vo.setStartIp(String.valueOf(PlatSubnetObject.get("Start IP")));	
		vo.setEndIp(String.valueOf(PlatSubnetObject.get("End IP")));	

		return vo;

	}

    
    public void CronPlatSubnetInsert(CronPlatSubnetVO vo) throws Exception{
    	
		logger.debug("======== CronPlatSubnetInsert  start  =======");

		sqlSession.insert("CronPlatSubnet.CronPlatSubnetInsert", vo);
		
		CronCmnCdVO cmncdvo = new  CronCmnCdVO();

		cmncdvo.setCmnNm(vo.getClassBand());	

		logger.debug("setCmnNm :: " + vo.getClassBand());

		if(cmncdvo.getCmnNm() != null && !cmncdvo.getCmnNm().equals(""))	{
				
			int tCnt = CronCmnCdCnt(cmncdvo);
			
			if(tCnt == 0){
				CronCmnCdInsert(cmncdvo);
			}

		}

    }

	@Override
	public void CronPlatSubnetDelete() throws Exception {

		sqlSession.delete("CronPlatSubnet.CronPlatSubnetDelete");
		
	}

	@Override
	public int CronPlatSubnetCnt() throws Exception {

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronPlatSubnet.CronPlatSubnetCnt");

		return tCnt;
	}

	@Override
	public void CronPlatSubnetResult(CronResultVO vo) throws Exception {

		sqlSession.insert("CronPlatSubnet.CronPlatSubnetResult", vo);
		
	}

	@Override
	public void CronIpZoneCodeInsert() throws Exception {

		sqlSession.insert("CronPlatSubnet.CronIpZoneCodeInsert");
		
	}

	public void CronCmnCdInsert(CronCmnCdVO vo) throws Exception {

		sqlSession.insert("CronPlatSubnet.CronCmnCdInsert", vo);
		
	}

	@Override
	public int CronCmnCdCnt() throws Exception {
		
		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronPlatSubnet.CronCmnCdCnt");

		return tCnt;
	}
	
	public int CronCmnCdCnt(CronCmnCdVO vo) throws Exception {

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronPlatSubnet.CronCmnCdCnt", vo);
		
		return tCnt;
	}
	
}
