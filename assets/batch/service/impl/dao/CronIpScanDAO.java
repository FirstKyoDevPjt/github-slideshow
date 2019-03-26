package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronIpScanVO;
import ipmn.batch.vo.CronResultVO;

public interface  CronIpScanDAO {
	
	public void CronIpScanDelete() throws Exception;
	
	public List<CronIpScanVO> CronIpScanInsert() throws Exception;

	public int CronIpScanCnt() throws Exception;

	public void CronIpScanResult(CronResultVO vo) throws Exception;

}