package ipmn.batch.service.impl.dao;

import ipmn.batch.vo.CronResultVO;

public interface  CronIpDtlInfoDAO {
	
	public void CronTiamsInsert() throws Exception;
	
	public void CronIpDtlInfoDelete() throws Exception;
	
	public void CronIpDtlInfoResult(CronResultVO vo) throws Exception;
	
	public int CronIpDtlInfoCnt() throws Exception;

}
