package ipmn.batch.service;

import ipmn.batch.vo.CronResultVO;

public interface CronDhcpIpService {

	/* DhcpIp  */
	void CronDhcpIpDelete() throws Exception;
	void getDhcpIpJson() throws Exception;
	int CronDhcpIpCnt() throws Exception;
	void CronDhcpIpResult(CronResultVO vo) throws Exception;

}