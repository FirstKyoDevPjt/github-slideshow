package ipmn.batch.service;

import ipmn.batch.vo.CronResultVO;

public interface CronPlatIpService {

	/* PlatIp  */
	void CronPlatIpDelete() throws Exception;
	void getPlatIpJson() throws Exception;
	int CronPlatIpCnt() throws Exception;
	void CronPlatIpResult(CronResultVO vo) throws Exception;

}