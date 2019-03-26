package ipmn.batch.service;

import java.sql.Connection;

import ipmn.batch.vo.CronResultVO;

public interface CronEscortService {
	
	Connection doConnect(String driver, String url, String id, String passwd, Connection conn) throws Exception;
	void dbEndConnect(Connection conn) throws Exception;

    /*Escort */
	void getEscortSystemInfo(Connection conn) throws Exception;
	int CronEscortCnt() throws Exception;
	void CronEscortDelete() throws Exception;
	void CronEscortResult(CronResultVO vo) throws Exception;

}