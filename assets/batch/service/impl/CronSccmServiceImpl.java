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

import ipmn.batch.service.CronSccmService;
import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronSccmVO;

@Service("CronSccmService")
public  class CronSccmServiceImpl implements CronSccmService   {

    private static final Logger logger = LoggerFactory.getLogger(CronSccmServiceImpl.class);
	
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
    
    public void CronSccmResult(CronResultVO vo) throws Exception{
    	
		logger.debug("======== CronBatchResult  start  =======");

    	sqlSession.insert("CronSccm.CronSccmResult", vo);

    }
    
	@Override
	public int CronSccmCnt() throws Exception {
		logger.debug("======== CronSccmCnt  start  =======");

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronSccm.CronSccmCnt");
		return tCnt;
	}
	
    public void getSccmSystemInfo(Connection conn) throws Exception{
		
		
		   String qry = "select CONVERT(CHAR(19),UpdateTime,120) AS UpdateTime,GUID,Hostname,TagNo,EmpNo, "
			          + "UserName,DepartmentName,DepartmentCode,IP,Mac,OS,ServicePack, "
			          + "Manufacturer,Model,OS_Bit,BiosSerialNo,MachineType from vTSecu_SystemInfo";
	 	   
		    Statement stmt = conn.createStatement();   
			   
			ResultSet rs = stmt.executeQuery(qry);
			
			CronSccmVO vo = new CronSccmVO();
			
			while (rs.next()) {
				
				String strIp = rs.getString("IP");
				String[] arrayIp = strIp.split("/");
				
				String strMac = rs.getString("Mac");
				String[] arrayMac = strMac.split(",");

				if(arrayIp.length > 0){
					for(int i=0;i<arrayIp.length;i++) {
						   vo.setUpdatetime(rs.getString("UpdateTime"));
						   vo.setGuid(rs.getString("GUID"));
						   vo.setHostname(rs.getString("Hostname"));
						   vo.setTagno(rs.getString("TagNo"));
						   vo.setEmpno(rs.getString("EmpNo"));
						   vo.setUsername(rs.getString("UserName"));
						   vo.setDepartmentname(rs.getString("DepartmentName"));
						   vo.setDepartmentcode(rs.getString("departmentcode"));
						   
						   if(arrayIp[i] == null || arrayIp[i].length() == 0){ 
							   vo.setIp("");
						   } else {
							   vo.setIp(arrayIp[i]);
						   }
						   
						   if(arrayMac.length > i){ 
							   vo.setMac(arrayMac[i]);
						   } else {
							   vo.setMac("");
						   }
						   
						   vo.setOs(rs.getString("OS"));
						   vo.setServicepack(rs.getString("ServicePack"));
						   vo.setManufacturer(rs.getString("Manufacturer"));
						   vo.setModel(rs.getString("Model"));
						   vo.setOsbit(rs.getString("OS_Bit"));
						   vo.setBiosserialno(rs.getString("BiosSerialNo"));
						   vo.setMachinetype(rs.getString("MachineType"));

						   CronSccmInsert(vo);
						logger.debug(arrayIp[i]);
//						logger.debug(arrayMac[i]);
					}
					
				}	else{
					   vo.setUpdatetime(rs.getString("UpdateTime"));
					   vo.setGuid(rs.getString("GUID"));
					   vo.setHostname(rs.getString("Hostname"));
					   vo.setTagno(rs.getString("TagNo"));
					   vo.setEmpno(rs.getString("EmpNo"));
					   vo.setUsername(rs.getString("UserName"));
					   vo.setDepartmentname(rs.getString("DepartmentName"));
					   vo.setDepartmentcode(rs.getString("departmentcode"));
					   vo.setIp(rs.getString("IP"));
					   vo.setMac(rs.getString("Mac"));
					   vo.setOs(rs.getString("OS"));
					   vo.setServicepack(rs.getString("ServicePack"));
					   vo.setManufacturer(rs.getString("Manufacturer"));
					   vo.setModel(rs.getString("Model"));
					   vo.setOsbit(rs.getString("OS_Bit"));
					   vo.setBiosserialno(rs.getString("BiosSerialNo"));
					   vo.setMachinetype(rs.getString("MachineType"));

					   CronSccmInsert(vo);
				}

			}
			
			rs.close();
			stmt.close();
	}

	private void CronSccmInsert(CronSccmVO vo) {

		sqlSession.insert("CronSccm.CronSccmInsert", vo);
	}

	@Override
	public void CronSccmDelete() throws Exception {

		sqlSession.delete("CronSccm.CronSccmDelete");
		
	}

}