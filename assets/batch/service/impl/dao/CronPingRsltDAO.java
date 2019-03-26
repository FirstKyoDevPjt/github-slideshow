package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronPingRsltVO;
import ipmn.batch.vo.CronResultVO;

public interface  CronPingRsltDAO {
	
	public void CronPingRsltDelete() throws Exception;
	
	public List<CronPingRsltVO> CronPingRsltInsert() throws Exception;

	public int CronPingRsltCnt() throws Exception;

	public void CronPingRsltResult(CronResultVO vo) throws Exception;

}