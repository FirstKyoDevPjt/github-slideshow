package ipmn.batch.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ipmn.batch.service.CronDhcpIpService;
import ipmn.batch.vo.CronDhcpIpVO;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;


@Service("CronDhcpIpService")
public  class CronDhcpIpServiceImpl implements CronDhcpIpService   {

    private static final Logger logger = LoggerFactory.getLogger(CronDhcpIpServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
    public Connection doConnect(String driver, String url, String id, String passwd, Connection conn) throws Exception{
    	    	 
		logger.info("======== Connection  start  =======");

		Class.forName(driver);
		 conn = DriverManager.getConnection(url, id, passwd); 
		 		 
         return conn;
    }
    
    public void dbEndConnect(Connection conn) throws Exception{

		logger.info("======== dbEndConnect  start  =======");
    	
        if(conn!=null){conn.close();}
    } 
    
    public void getDhcpIpJson() throws Exception {

			logger.info("======== getDhcpIpJson  start  =======");

		 BufferedReader in = null;
		 JSONArray DhcpIpArray = null;	
		 
		 String apiURL = IpmnGetProperties.getProperty("DHCPIP.Url1");
				
		 URL url = new URL(apiURL);
		 HttpURLConnection con = (HttpURLConnection)url.openConnection();

		 con.setRequestMethod("GET");
		 con.setRequestProperty("UserName", IpmnGetProperties.getProperty("DHCPIP.UserName"));

		 // 정상 호출
		 in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 

			logger.info("======== getDhcpIpJson  정상 호출  =======");
		 
		 String line; 
		 StringBuffer response = new StringBuffer();
				
		  while((line = in.readLine()) != null) { 
				logger.info("======== getDhcpIpJson  while  =======");
			  response.append(line);
		  }	
				
		  in.close();

		  JSONParser jsonParser = new JSONParser();
				
		   // JSON데이터를 넣어 JSON Object 로 만들어 준다.
		  JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
		  DhcpIpArray = (JSONArray)jsonObject.get("Data");    

		  
		  for(int i=0; i<DhcpIpArray.size(); i++){
				 
			// 배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
			JSONObject DhcpIpObject = (JSONObject) DhcpIpArray.get(i);
			//자료저장
			CronDhcpIpInsert(executeDhcpIpInsert(DhcpIpObject));
		  }
		  
//	  return DhcpIpArray;
		
	 }
	 	 
	 private CronDhcpIpVO executeDhcpIpInsert(JSONObject DhcpIpObject) throws Exception {

		 CronDhcpIpVO vo = new  CronDhcpIpVO();
	     	
		// 추후 컬럼 확인 하여 별경 할것...
		vo.setIpAddr(String.valueOf(DhcpIpObject.get("IP")));	
		vo.setNetwork(String.valueOf(DhcpIpObject.get("NETWORK")));						  	
		vo.setMac(String.valueOf(DhcpIpObject.get("MAC")));	
		vo.setReqNm(String.valueOf(DhcpIpObject.get("REQNM")));	
		vo.setRemark(String.valueOf(DhcpIpObject.get("REMARK")));	

		return vo;

	}

    public void CronDhcpIpInsert(CronDhcpIpVO vo) throws Exception{
    	
		logger.info("======== CronDhcpIpInsert  start  =======");

		sqlSession.insert("CronDhcpIp.CronDhcpIpInsert", vo);
    }

	@Override
	public void CronDhcpIpDelete() throws Exception {

		sqlSession.delete("CronDhcpIp.CronDhcpIpDelete");
		
	}

	@Override
	public int CronDhcpIpCnt() throws Exception {

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronDhcpIp.CronDhcpIpCnt");

		return tCnt;
	}

	@Override
	public void CronDhcpIpResult(CronResultVO vo) throws Exception {

		sqlSession.insert("CronDhcpIp.CronDhcpIpResult", vo);
		
	}
    
}
