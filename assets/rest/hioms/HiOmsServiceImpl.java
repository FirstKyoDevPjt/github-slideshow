package ipmn.rest.hioms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ipmn.common.util.IpmnGetProperties;


@Service("HiOmsService")
public  class HiOmsServiceImpl implements HiOmsService   {

    private static final Logger logger = LoggerFactory.getLogger(HiOmsServiceImpl.class);
	
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
    
    public void getHiOmsJson() throws Exception {

		logger.info("======== getHiOmsJson  start  =======");

		 BufferedReader in = null;
		 JSONArray HiOmsArray = null;	
		 
		 String apiURL = IpmnGetProperties.getProperty("HiOms.Url");
				
		 URL url = new URL(apiURL);
		 HttpURLConnection con = (HttpURLConnection)url.openConnection();

		 con.setRequestMethod("GET");
		 con.setRequestProperty("AuthKey", IpmnGetProperties.getProperty("HiOms.AuthKey"));

		 // 정상 호출
		 in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 

			logger.info("======== getHiOmsJson  정상 호출  =======");
		 
		 String line; 
		 StringBuffer response = new StringBuffer();
				
		  while((line = in.readLine()) != null) { 
				logger.info("======== getHiOmsJson  while  =======");
			  response.append(line);
		  }	
				
		  in.close();

		  JSONParser jsonParser = new JSONParser();
				
		   // JSON데이터를 넣어 JSON Object 로 만들어 준다.
		  JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
		  HiOmsArray = (JSONArray)jsonObject.get("Data");    

		  
		  for(int i=0; i<HiOmsArray.size(); i++){
				 
			// 배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
			JSONObject HiOmsObject = (JSONObject) HiOmsArray.get(i);
			//자료저장
			executeHiOmsInsert(HiOmsObject);
		  }
		  
//	  return HiOmsArray;
		
	 }
	 	 
	 private HiOmsVO executeHiOmsInsert(JSONObject HiOmsObject) throws Exception {

		 logger.info("======== executeHiOmsArrayInsert  start  =======");

			 
			 HiOmsVO vo = new  HiOmsVO();
		     	
				// 배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출\
//				JSONObject 	dataObject = (JSONObject)HiOmsArray.get(i);	
									
				vo.setReqUserId(String.valueOf(HiOmsObject.get("IP 실제 연동 확인 후 컬럼 값 setting")));	
				vo.setReqUserNm(String.valueOf(HiOmsObject.get("IP")));	
				vo.setReqUserDept(String.valueOf(HiOmsObject.get("IP")));	
				vo.setMngId(String.valueOf(HiOmsObject.get("IP")));	
				vo.setMngNm(String.valueOf(HiOmsObject.get("IP")));	
				vo.setMngDept(String.valueOf(HiOmsObject.get("IP")));	
				vo.setApclId(String.valueOf(HiOmsObject.get("IP")));	
				vo.setApclNm(String.valueOf(HiOmsObject.get("IP")));	
				vo.setApclDept(String.valueOf(HiOmsObject.get("IP")));	

				logger.info("======== executeHiOmsArrayInsert  IP         =======" + String.valueOf(HiOmsObject.get("IP")));
				logger.info("======== executeHiOmsArrayInsert  MAC        =======" + String.valueOf(HiOmsObject.get("MAC")));
				logger.info("======== executeHiOmsArrayInsert  Hostname   =======" + String.valueOf(HiOmsObject.get("Hostname")));
				logger.info("======== executeHiOmsArrayInsert  SubnetName =======" + String.valueOf(HiOmsObject.get("SubnetName")));
				logger.info("======== executeHiOmsArrayInsert  manager    =======" + String.valueOf(HiOmsObject.get("Manager")));
				logger.info("======== executeHiOmsArrayInsert  mrgNo      =======" + String.valueOf(HiOmsObject.get("MgrNo")));

				return vo;

	}

}
