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

import ipmn.batch.service.CronEscortService;
import ipmn.batch.vo.CronEscortVO;
import ipmn.batch.vo.CronResultVO;


@Service("CronEscortService")
public  class CronEscortServiceImpl implements CronEscortService   {

    private static final Logger logger = LoggerFactory.getLogger(CronEscortServiceImpl.class);
	
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
    
    public void CronEscortResult(CronResultVO vo) throws Exception{
    	
		logger.debug("======== CronEscortResult  start  =======");

		//    	CronTiamsDAO.insert("CronTiams.CronBatchResult", vo);
    	sqlSession.insert("CronEscort.CronEscortResult", vo);

    }
    
	public int CronEscortCnt() throws Exception {
		logger.debug("======== CronEscortCnt  start  =======");

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronEscort.CronEscortCnt");
		return tCnt;
	}

   public void getEscortSystemInfo(Connection conn) throws Exception{
		
		
 	    String qry = "Select * From dbo.Escort_view";
	   
		Statement stmt = conn.createStatement();   
		   
		ResultSet rs = stmt.executeQuery(qry);
		
		CronEscortVO vo = new CronEscortVO();
		
		// ResultSet 객체를 통해 데이터 추출 (row by row)
		while (rs.next()) {
			   			
			 
			  vo.setSerial(rs.getString("SERIAL"));
			  vo.setName(rs.getString("NAME"));
			  vo.setSdeptnm(rs.getString("SDEPTNM"));
			  vo.setIp_addr(rs.getString("IP_ADDR"));
			  vo.setComputer_name(rs.getString("COMPUTER_NAME"));
			  vo.setPc_gubun(rs.getString("PC_GUBUN"));
			  vo.setOs(rs.getString("OS"));
			  vo.setLatest_date(rs.getString("LATEST_DATE"));			
					
			  CronEscortInsert(vo);
		}
		
		rs.close();
		stmt.close();
	}

	public void CronEscortInsert(CronEscortVO vo) throws Exception{
		sqlSession.insert("CronEscort.CronEscortInsert", vo);
	}
	
	public void CronEscortDelete() throws Exception{
		
		sqlSession.delete("CronEscort.CronEscortDelete");			
	}
	
}