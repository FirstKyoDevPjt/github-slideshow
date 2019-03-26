package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronDnsReqVO;
import ipmn.batch.vo.CronResultVO;

public interface  CronDnsReqDAO {
	
	public int CronDnsReqCnt() throws Exception;

	public List<CronDnsReqVO> CronSepmInsert() throws Exception;

	public void CronDneReqDelete() throws Exception;

	public void CronDnsReqResult(CronResultVO vo) throws Exception;

}