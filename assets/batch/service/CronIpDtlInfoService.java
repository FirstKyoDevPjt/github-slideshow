package ipmn.batch.service;

import ipmn.batch.vo.CronResultVO;

public interface CronIpDtlInfoService {

	/* IpDtlInfo  */
	void CronIpDtlInfoDelete() throws Exception;
	void CronIpDtlInfoInsert() throws Exception;
	int CronIpDtlInfoCnt() throws Exception;
	void CronIpDtlInfoResult(CronResultVO vo) throws Exception;

}