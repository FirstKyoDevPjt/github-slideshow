package ipmn.batch.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ipmn.batch.service.CronSepService;
import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronSepVO;

@Service("CronSepService")
public  class CronSepServiceImpl implements CronSepService   {

    private static final Logger logger = LoggerFactory.getLogger(CronSepServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
    public Connection doConnect(String driver, String url, String id, String passwd, Connection conn) throws Exception{
    	    	 
		logger.debug("======== Connection  start  =======");

		Class.forName(driver);
		 conn = DriverManager.getConnection(url, id, passwd); 
		 		 
         return conn;
    }
    
    public void dbEndConnect(Connection conn) throws Exception{

		logger.debug("======== dbEndConnect  start  =======");
    	
        if(conn!=null){conn.close();}
    } 
    
    public void CronSepResult(CronResultVO vo) throws Exception{
    	
		logger.debug("======== CronBatchResult  start  =======");

		//    	CronTiamsDAO.insert("CronTiams.CronBatchResult", vo);
    	sqlSession.insert("CronSep.CronSepResult", vo);

    }
    
	@Override
	public int CronSepCnt() throws Exception {
		logger.debug("======== CronSepCnt  start  =======");

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronSep.CronSepCnt");
		return tCnt;
	}
	
    public void getSepSystemInfo(Connection conn) throws Exception{
		
		
		String qry = "SELECT TIME_STAMP, OPERATION_SYSTEM, COMPUTER_NAME, OS_LANG, COMPUTER_DESCRIPTION,"
				   + "SERVICE_PACK, PROCESSOR_TYPE, BIOS_VERSION, IP_ADDR1_TEXT, IP_ADDR2_TEXT, GATEWAY1_TEXT,"
				   + "GATEWAY2_TEXT, MAC_ADDR1, MAC_ADDR2, DNS_SERVER1_TEXT, DNS_SERVER2_TEXT,"
				   + "HARDWARE_KEY, CURRENT_LOGIN_USER, UUID FROM V_SEM_COMPUTER";
		
		Statement stmt = conn.createStatement();	
		ResultSet rs = stmt.executeQuery(qry);		

		CronSepVO vo = new CronSepVO();
		
	    while (rs.next()) 
	    {
	    	  
	    	  String time_stamp = rs.getString("TIME_STAMP");
	    	  String unixSeconds = (String) time_stamp .subSequence(0,10);
		   	
	    	  vo.setTime_stamp(UnixTimeStempConvert(Long.parseLong(unixSeconds)));
		      vo.setOperation_system(rs.getString("OPERATION_SYSTEM"));
		      vo.setComputer_name(rs.getString("COMPUTER_NAME"));		      
		      vo.setOs_lang(rs.getString("OS_LANG"));
		      vo.setComputer_description(rs.getString("COMPUTER_DESCRIPTION"));
		      vo.setService_pack(rs.getString("SERVICE_PACK"));
		      vo.setProcessor_type(rs.getString("PROCESSOR_TYPE"));
		      vo.setBios_version(rs.getString("BIOS_VERSION"));
		      vo.setIp_addr1(rs.getString("IP_ADDR1_TEXT"));
		      vo.setIp_addr2(rs.getString("IP_ADDR2_TEXT"));
		      vo.setGateway1(rs.getString("GATEWAY1_TEXT"));
		      vo.setGateway2(rs.getString("GATEWAY2_TEXT"));
		      vo.setMac_addr1(rs.getString("MAC_ADDR1"));
		      vo.setMac_addr2(rs.getString("MAC_ADDR2"));
		      vo.setDns_server1(rs.getString("DNS_SERVER1_TEXT"));
		      vo.setDns_server2(rs.getString("DNS_SERVER2_TEXT"));
		      vo.setHardware_key(rs.getString("HARDWARE_KEY"));
		      vo.setCurrent_login_user(rs.getString("CURRENT_LOGIN_USER"));
		      vo.setUuid(rs.getString("UUID"));
		      
		      CronSepInsert(vo);
		   
     }
	      
	    rs.close();
	    stmt.close();
	}

    private  String UnixTimeStempConvert(long unixSeconds) throws Exception{
    	
  	    Date date = new Date(unixSeconds*1000L);
  	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
  	    // GMT(그리니치 표준시 +9 시가 한국의 표준시
  	    sdf.setTimeZone(TimeZone.getTimeZone("GMT+9"));  	 
  	    
    	return sdf.format(date);
    }

    
	private void CronSepInsert(CronSepVO vo) {

		sqlSession.insert("CronSep.CronSepInsert", vo);
	}

	public void CronSepDelete() throws Exception {

		sqlSession.delete("CronSep.CronSepDelete");
		
	}

}