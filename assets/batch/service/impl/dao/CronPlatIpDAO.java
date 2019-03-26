package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronPlatIpVO;
import ipmn.batch.vo.CronResultVO;

public interface  CronPlatIpDAO {
	
	public void CronPlatIpDelete() throws Exception;
	
	public List<CronPlatIpVO> CronPlatIpInsert() throws Exception;

	public int CronPlatIpCnt() throws Exception;

	public void CronPlatIpResult(CronResultVO vo) throws Exception;

}