package ipmn.batch.service;

import ipmn.batch.vo.CronResultVO;

public interface CronPingRsltService {

	/* PingRslt  */
	void CronPingRsltDelete() throws Exception;
	void getPingRsltInfo() throws Exception;
	int CronPingRsltCnt() throws Exception;
	void CronPingRsltResult(CronResultVO vo) throws Exception;

}