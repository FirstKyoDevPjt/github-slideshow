package ipmn.batch.service;

import java.sql.Connection;

import ipmn.batch.vo.CronResultVO;

public interface CronSepService {
	
	Connection doConnect(String driver, String url, String id, String passwd, Connection conn) throws Exception;
	void dbEndConnect(Connection conn) throws Exception;
	
	/*SEP*/
	void CronSepDelete() throws Exception;
	void getSepSystemInfo(Connection conn) throws Exception;
	int CronSepCnt() throws Exception;
	void CronSepResult(CronResultVO vo) throws Exception;

}
