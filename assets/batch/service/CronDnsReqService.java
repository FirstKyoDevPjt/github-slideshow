package ipmn.batch.service;

import java.sql.Connection;

import ipmn.batch.vo.CronResultVO;

public interface CronDnsReqService {
	
	Connection doConnect(String driver, String url, String id, String passwd, Connection conn) throws Exception;
	void dbEndConnect(Connection conn) throws Exception;
	
	/*DNS REQUEST*/
	void CronDnsReqDelete() throws Exception;
	void getDnsReqSystemInfo(Connection conn) throws Exception;
	int CronDnsReqCnt() throws Exception;
	void CronDnsReqResult(CronResultVO vo) throws Exception;

}
