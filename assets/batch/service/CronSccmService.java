package ipmn.batch.service;

import java.sql.Connection;

import ipmn.batch.vo.CronResultVO;

public interface CronSccmService {
	
	Connection doConnect(String driver, String url, String id, String passwd, Connection conn) throws Exception;
	void dbEndConnect(Connection conn) throws Exception;
	
	/*SCCM*/
	void CronSccmDelete() throws Exception;
	void getSccmSystemInfo(Connection conn) throws Exception;
	int CronSccmCnt() throws Exception;
	void CronSccmResult(CronResultVO vo) throws Exception;

}
