package ipmn.rest.firewall;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.common.util.IpmnGetProperties;

@Service("FireWallService")
public class FireWallServiceImpl implements FireWallService   {

    private static final Logger logger = LoggerFactory.getLogger(FireWallServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String FIRE_DriverClassName = IpmnGetProperties.getProperty("FIRE.DriverClassName");
	private final String FIRE_Url = IpmnGetProperties.getProperty("FIRE.Url");
	private final String FIRE_UserName = IpmnGetProperties.getProperty("FIRE.UserName");
	private final String FIRE_Password = IpmnGetProperties.getProperty("FIRE.Password");
	
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
	
    public List<FireWallVO> GetFireWallInfo(String ip) {
		
    	//SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
    	List<FireWallVO> fireWallVO_List = new ArrayList<FireWallVO>();
    	
		Connection conn= null;
		Statement stmt = null;
		ResultSet rs = null;
		
		/* 특정 컨테이너에서 수행되도록  설정 */


		try {

			logger.debug("DriverClassName : " + FIRE_DriverClassName);
			logger.debug("Url             : " + FIRE_Url);
			logger.debug("UserName        : " + FIRE_UserName);
			logger.debug("Password        : " + FIRE_Password);


			conn = doConnect(FIRE_DriverClassName,
					FIRE_Url,
					FIRE_UserName,
					FIRE_Password,
					conn);

			if (conn != null){

				logger.debug("==========  CRON FIRE WALL BATCH START  ==========");


				String qry = "SELECT FW.RNO, FW.WF_WJ_ID, FW.MANAGER_ADMIN_ID, FW.REG_DATETIME, FW.SERVICE_CATE1, FW.SERVICE_CATE2, FW.SERVICE_CATE3,"
						+ " FW.SERVICE_CATE4, FW.REASON_DETAIL, FW.FILE_CATALOG_ID, FW.REQUEST_USER_ID, FW.APPROVAL_STATUS, FW.APPROVAL_STATUS_NM,"
						+ " FW.REQUEST_USER_NAME, FW.MANAGER_ADMIN_NAME FROM  ( SELECT WF_WJ_ID  FROM  SECUSKTPORTAL.V_SRCHOST_INFO"
						+ " WHERE  FIP_FULL_SRC ='" + ip + "' GROUP BY WF_WJ_ID   UNION   SELECT WF_WJ_ID   FROM  SECUSKTPORTAL.V_DESHOST_INFO"
						+ " WHERE  FIP_FULL_DES ='" + ip + "'  GROUP BY WF_WJ_ID  ) WF, SECUSKTPORTAL.V_FIREWALL_LIST FW WHERE FW.WF_WJ_ID = WF.WF_WJ_ID";
				
				
				//String qry = "SELECT FW.WF_WJ_ID FROM SECUSKTPORTAL.V_FIREWALL_LIST FW WHERE ROWNUM <5";

				
				logger.debug(" qry :: " + qry);

				
				stmt = conn.createStatement();	

				rs = stmt.executeQuery(qry);

				
		    	while (rs.next()) {
		    		FireWallVO vo = new FireWallVO();

		    		logger.debug("FW.WF_WJ_ID :: " + rs.getString("WF_WJ_ID"));
		    		logger.debug("WF_WJ_ID    :: " + rs.getString("WF_WJ_ID"));

		    		vo.setRno(rs.getString("RNO"));
		    		vo.setWfWjId(rs.getString("WF_WJ_ID"));
		    		vo.setManagerAdminId(rs.getString("MANAGER_ADMIN_ID"));
		    		vo.setRegDatetime(rs.getString("REG_DATETIME"));
		    		vo.setServiceCate1(rs.getString("SERVICE_CATE1"));
		    		vo.setServiceCate2(rs.getString("SERVICE_CATE2"));
		    		vo.setServiceCate3(rs.getString("SERVICE_CATE3"));
		    		vo.setServiceCate4(rs.getString("SERVICE_CATE4"));
		    		vo.setReasonDetail(rs.getString("REASON_DETAIL"));
		    		vo.setFileCatalogId(rs.getString("FILE_CATALOG_ID"));
		    		vo.setRequestUserId(rs.getString("REQUEST_USER_ID"));
		    		vo.setApprovalStatus(rs.getString("APPROVAL_STATUS"));
		    		vo.setApprovalStatus(rs.getString("APPROVAL_STATUS_NM"));
		    		vo.setRequestUserId(rs.getString("REQUEST_USER_NAME"));
		    		vo.setManagerAdminId(rs.getString("MANAGER_ADMIN_NAME"));

		    		fireWallVO_List.add(vo);
		    	}

			}//if end connection

		} catch (Exception  e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			try {
				if(stmt!=null) stmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				dbEndConnect(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return fireWallVO_List;
	}


}