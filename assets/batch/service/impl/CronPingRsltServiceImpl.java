package ipmn.batch.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ipmn.batch.service.CronPingRsltService;
import ipmn.batch.vo.CronPingRsltVO;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;


@Service("CronPingRsltService")
public  class CronPingRsltServiceImpl implements CronPingRsltService   {

    private static final Logger logger = LoggerFactory.getLogger(CronPingRsltServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	public void getPingRsltInfo() throws Exception {

		SimpleDateFormat day = new SimpleDateFormat ( "yyyyMMdd" );

		Calendar cal = Calendar.getInstance();

		String dDay = day.format ( cal.getTime() );

		logger.debug("======== getPingRsltInfo  start  =======");

		 BufferedReader in = null;
//		 JSONArray PlatIpArray = null;	
		String Ip = "";
		String[] arrayIp = IpmnGetProperties.getProperty("HONEY.Url").split(":");
		if(arrayIp.length > 0){
			Ip = arrayIp[1].replaceAll("[^0-9.]", "");
		}
		 
		 String apiURL = IpmnGetProperties.getProperty("HONEY.Url") + Ip + "_" + dDay + ".txt";
				
		 URL url = new URL(apiURL);
		 HttpURLConnection con = (HttpURLConnection)url.openConnection();

		 // 정상 호출
		 in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 

			logger.debug("======== getPingRsltInfo  정상 호출  =======");
		 
		 String line; 
		 StringBuffer response = new StringBuffer();
				
		  while((line = in.readLine()) != null) { 
				logger.debug("======== getPingRsltInfo  while  =======");
			  //response.append(line);
				
            	String[] arrayLine = line.split(":");

            	if(arrayLine.length > 0){
    				CronPingRsltVO vo = new CronPingRsltVO();

	            	for(int i=0;i<arrayLine.length;i++) {

    					vo.setIpAddr(arrayLine[1].replaceAll("[^0-9.]", ""));
	            		vo.setResultDate(dDay);	
	            		
	            		if(arrayLine[2].trim().equals("Up")){
	            			vo.setResultCd("0");
	            		} else {
	            			vo.setResultCd("1");
	            		}
	            		
	            		vo.setContinuousDay("1");
	            		vo.setHoneyIpAddr(Ip);

					}
            		try {
            			CronPingRsltInsert(vo);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	                logger.info(line);
				}
				//자료저장
//				CronPingRsltInsert(vo);

				logger.debug("lead line length = "+line.length());
				logger.debug("lead line = "+line);
		  }	
				
		  in.close();
		
	 }
	 	 
    public void CronPingRsltInsert(CronPingRsltVO vo) throws Exception{
    	
		logger.debug("======== CronPlatIpInsert  start  =======");

		sqlSession.insert("CronPingRslt.CronPingRsltInsert", vo);
    }

	public void CronPingRsltDelete() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public int CronPingRsltCnt() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public void CronPingRsltResult(CronResultVO vo) throws Exception {

		sqlSession.insert("CronPingRslt.CronPingRsltResult", vo);
	
	}

    
}
