package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronSolidVO;

public interface  CronElkSolidDAO {
	
	public List<CronSolidVO> CronSolidInsert() throws Exception;
	
	public void CronSolidDelete() throws Exception;
	
	public void CronSolidResult(CronResultVO vo) throws Exception;
	
	public int CronSolidCnt() throws Exception;

}
