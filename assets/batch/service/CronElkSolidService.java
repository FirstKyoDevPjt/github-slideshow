package ipmn.batch.service;

import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronSolidVO;

public interface CronElkSolidService {

	/* Solid */
	void CronSolidDelete() throws Exception;
	int CronSolidCnt()  throws Exception;
	void CronSolidResult(CronResultVO resultvo);
	void CronSolidInsert(CronSolidVO vo) throws Exception;
	
}
