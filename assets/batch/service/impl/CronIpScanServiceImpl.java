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

import ipmn.batch.service.CronIpScanService;
import ipmn.batch.vo.CronIpScanVO;
import ipmn.batch.vo.CronResultVO;

@Service("CronIpScanService")
public  class CronIpScanServiceImpl implements CronIpScanService   {

    private static final Logger logger = LoggerFactory.getLogger(CronPlatIpServiceImpl.class);
	
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

    public void CronIpScanResult(CronResultVO vo){
    	
		logger.debug("======== CronBatchResult  start  =======");

		//    	CronTiamsDAO.insert("CronTiams.CronBatchResult", vo);
    	sqlSession.insert("CronIpScan.CronIpScanResult", vo);

    }

	public void getIpScanSystemInfo(Connection conn) throws Exception{
		
		
	    String qry =  "SELECT * FROM IPSCAN_View ";
		Statement stmt = conn.createStatement();   
		   
		ResultSet rs = stmt.executeQuery(qry);
		
		CronIpScanVO vo = new CronIpScanVO();

		while (rs.next()) {
			   			
			 
			 vo.setStr_ip(rs.getString("STR_IP"));
			 vo.setMac(rs.getString("MAC"));
			 vo.setProbe_id(rs.getString("PROBE_ID"));
			 vo.setHname(rs.getString("HNAME"));
			 vo.setUser_id_mac(rs.getString("USER_ID_MAC"));
			 vo.setUser_nm_mac(rs.getString("USER_NM_MAC"));
			 vo.setUser_sm_mac(rs.getString("USER_SM_MAC"));
			 vo.setOs_type(rs.getString("OS_TYPE"));
			 
			 CronIpScanInsert(vo);
		
		}
		
		rs.close();
		stmt.close();
	}
	
	
	public int CronIpScanCnt() throws Exception{
		
		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronIpScan.CronIpScanCnt");
		return tCnt;
			
	}
	
	public void CronIpScanInsert(CronIpScanVO vo) throws Exception{
		sqlSession.insert("CronIpScan.CronIpScanInsert", vo);
	}
	
	public void CronIpScanDelete() throws Exception{
		
		sqlSession.delete("CronIpScan.CronIpScanDelete");
	}

}