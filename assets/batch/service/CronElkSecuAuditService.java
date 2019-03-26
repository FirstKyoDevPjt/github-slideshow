package ipmn.batch.service;

import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronSecuAuditVO;

public interface CronElkSecuAuditService {

/*	Connection doConnect(String driver, String url, String id, String passwd, Connection conn) throws Exception;
	void dbEndConnect(Connection conn) throws Exception;*/
	
	/* Solid */
	void CronSecuAuditDelete() throws Exception;
//	void getSolidJson() throws Exception;
	int CronSecuAuditCnt()  throws Exception;
	void CronSecuAuditResult(CronResultVO resultvo);
	void CronSecuAuditInsert(CronSecuAuditVO vo) throws Exception;
	
}
