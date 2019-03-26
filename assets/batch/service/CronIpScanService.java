package ipmn.batch.service;

import java.sql.Connection;

import ipmn.batch.vo.CronResultVO;

public interface CronIpScanService {

	Connection doConnect(String driver, String url, String id, String passwd, Connection conn) throws Exception;
	void dbEndConnect(Connection conn) throws Exception;
	
	/*IPSCAN */
	void getIpScanSystemInfo(Connection conn) throws Exception;
	void CronIpScanDelete() throws Exception;
	int CronIpScanCnt()  throws Exception;
	void CronIpScanResult(CronResultVO resultvo);

}