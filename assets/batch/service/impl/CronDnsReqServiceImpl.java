package ipmn.batch.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ipmn.batch.service.CronDnsReqService;
import ipmn.batch.vo.CronDnsReqVO;
import ipmn.batch.vo.CronResultVO;

@Service("CronDnsReqService")
public  class CronDnsReqServiceImpl implements CronDnsReqService   {

    private static final Logger logger = LoggerFactory.getLogger(CronDnsReqServiceImpl.class);
	
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
    
    public void CronDnsReqResult(CronResultVO vo) throws Exception{
    	
		logger.debug("======== CronBatchResult  start  =======");

		//    	CronTiamsDAO.insert("CronTiams.CronBatchResult", vo);
    	sqlSession.insert("CronDnsReq.CronDnsReqResult", vo);

    }
    
	@Override
	public int CronDnsReqCnt() throws Exception {
		logger.debug("======== CronDnsReqCnt  start  =======");

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronDnsReq.CronDnsReqCnt");
		return tCnt;
	}
	
	
    public void getDnsReqSystemInfo(Connection conn) throws Exception{
		
		String qry = "SELECT * FROM SECUSKTPORTAL.V_DNS_HISTORY";
		
		Statement stmt = conn.createStatement();	

		ResultSet rs = stmt.executeQuery(qry);

		CronDnsReqVO vo = new CronDnsReqVO();
		
	    //ResultSet 객체를 통해 데이터 추출 (row by row)
	    while (rs.next()) 
	    {
	    	  
//	    	  String time_stamp = rs.getString("TIME_STAMP");
//	    	  String unixSeconds = (String) time_stamp .subSequence(0,10);
		   	
		      vo.setWfWjId(rs.getString("WF_WJ_ID"));
		      vo.setUserId(rs.getString("USER_ID"));
		      vo.setUserName(rs.getString("USER_NAME"));
		      vo.setApplyDate(rs.getString("APPLY_DATE"));
		      vo.setRegDatetime(rs.getString("REG_DATETIME"));
		      vo.setObjectCompany(rs.getString("OBJECT_COMPANY"));
		      vo.setObjectGroup(rs.getString("OBJECT_GROUP"));
		      vo.setObjectName(rs.getString("OBJECT_NAME"));
		      vo.setObjectNumber(rs.getString("OBJECT_NUMBER"));
		      vo.setServiceCate1(rs.getString("SERVICE_CATE1"));
		      vo.setServiceCate3(rs.getString("SERVICE_CATE3"));
		      vo.setServiceCate4(rs.getString("SERVICE_CATE4"));
		      vo.setReasonDetail(rs.getString("REASON_DETAIL"));
		      vo.setDomain(rs.getString("DOMAIN"));
		      vo.setIp(rs.getString("IP"));
		      vo.setDnsCheckDesc(rs.getString("DNS_CHECK_DESC"));
		      vo.setServiceName(rs.getString("SERVICE_NAME"));
		      vo.setServiceId(rs.getString("SERVICE_ID"));
		      vo.setServiceCheckIp(rs.getString("SERVICE_CHECK_IP"));
		      vo.setApprovalStatusDesc(rs.getString("APPROVAL_STATUS_DESC"));

		      CronDnsReqInsert(vo);
		   
     }
	      
	    rs.close();
	    stmt.close();
	}

/*    private  String UnixTimeStempConvert(long unixSeconds) throws Exception{
    	
  	    Date date = new Date(unixSeconds*1000L);
  	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
  	    // GMT(그리니치 표준시 +9 시가 한국의 표준시
  	    sdf.setTimeZone(TimeZone.getTimeZone("GMT+9"));  	 
  	    
    	return sdf.format(date);
    }*/

    
	private void CronDnsReqInsert(CronDnsReqVO vo) {

		sqlSession.insert("CronDnsReq.CronDnsReqInsert", vo);
	}

	public void CronDnsReqDelete() throws Exception {

		sqlSession.delete("CronDnsReq.CronDnsReqDelete");
		
	}

}