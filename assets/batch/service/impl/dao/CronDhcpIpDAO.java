package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronDhcpIpVO;
import ipmn.batch.vo.CronResultVO;

public interface  CronDhcpIpDAO {
	
	public void CronPlatIpDelete() throws Exception;
	
	public List<CronDhcpIpVO> CronPlatIpInsert() throws Exception;

	public int CronPlatIpCnt() throws Exception;

	public void CronPlatIpResult(CronResultVO vo) throws Exception;

}