package ipmn.batch.service;

import java.sql.Connection;

import ipmn.batch.vo.CronPmsVO;
import ipmn.batch.vo.CronResultVO;

public interface CronPmsService {
	
	Connection doConnect(String driver, String url, String id, String passwd, Connection conn) throws Exception;
	void dbEndConnect(Connection conn) throws Exception;
	
	/*PMS*/
	void CronPmsDelete(CronPmsVO vo) throws Exception;
	void getPmsSystemInfo(Connection conn,String gubun) throws Exception;
	int CronPmsCnt() throws Exception;
	void CronPmsResult(CronResultVO vo) throws Exception;
	
}
