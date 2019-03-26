package ipmn.batch.service;

import ipmn.batch.vo.CronCmnCdVO;
import ipmn.batch.vo.CronResultVO;

public interface CronPlatSubnetService {
	
	/* PlatIp  */
	void CronPlatSubnetDelete() throws Exception;
	void getPlatSubnetJson() throws Exception;
	int  CronPlatSubnetCnt() throws Exception;
	void CronPlatSubnetResult(CronResultVO vo) throws Exception;
	
	void CronIpZoneCodeInsert() throws Exception;
	
	void CronCmnCdInsert(CronCmnCdVO vo) throws Exception;
	
	int CronCmnCdCnt() throws Exception;

}