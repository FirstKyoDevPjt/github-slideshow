package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronCmnCdVO;
import ipmn.batch.vo.CronPlatSubnetVO;
import ipmn.batch.vo.CronResultVO;

public interface  CronPlatSubnetDAO {
	
	public void CronPlatSubnetDelete() throws Exception;
	
	public List<CronPlatSubnetVO> CronPlatSubnetInsert() throws Exception;

	public void CronIpZoneCodeInsert() throws Exception;

	public int CronPlatSubnetCnt() throws Exception;

	public void CronPlatSubnetResult(CronResultVO vo) throws Exception;

	public int CronCmnCdCnt() throws Exception;
	public List<CronCmnCdVO> CronCmnCdInsert() throws Exception;

}