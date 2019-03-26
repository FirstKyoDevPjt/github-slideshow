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

import ipmn.batch.service.CronPmsService;
import ipmn.batch.vo.CronPmsVO;
import ipmn.batch.vo.CronResultVO;

@Service("CronPmsService")
public  class CronPmsServiceImpl implements CronPmsService   {

    private static final Logger logger = LoggerFactory.getLogger(CronPmsServiceImpl.class);
	
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
    
    public void CronPmsResult(CronResultVO vo) throws Exception{
    	
		logger.debug("======== CronBatchResult  start  =======");

    	sqlSession.insert("CronPms.CronPmsResult", vo);

    }
    
	public int CronPmsCnt() throws Exception {
		logger.debug("======== CronPmsCnt  start  =======");

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronPms.CronPmsCnt");
		return tCnt;
	}
	
	public void CronPmsDelete(CronPmsVO vo) throws Exception {

		sqlSession.delete("CronPms.CronPmsDelete", vo);
		
	}

	public void getPmsSystemInfo(Connection conn,String gubun) throws Exception{
		
		
    	String qry = "select * from vAgentList";
		Statement stmt = conn.createStatement();   
		   
		ResultSet rs = stmt.executeQuery(qry);
		
		CronPmsVO vo = new CronPmsVO();
		   
		while (rs.next()) {
			   			
			  vo.setGubun(gubun);
			  vo.setResourceguid(rs.getString(1));	
			  vo.setName(rs.getString(2));
			  vo.setMac_address(rs.getString(3));
		      vo.setIp_address(rs.getString(4));
		      vo.setOs_name(rs.getString(5));
		      vo.setSystem_type(rs.getString(6));
		      vo.setOs_revision(rs.getString(7));
		      vo.setCreateddate(rs.getString(8));
		      vo.setLast_inv(rs.getString(9));
		      vo.setProduct_version(rs.getString(10));
			
		      CronPmsInsert(vo);
		}
		
		rs.close();
		stmt.close();
	}

	private void CronPmsInsert(CronPmsVO vo) {

		sqlSession.insert("CronPms.CronPmsInsert", vo);
	}

}