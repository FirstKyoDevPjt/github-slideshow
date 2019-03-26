package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronSepVO;

public interface  CronSepDAO {
	
	public int CronSepCnt() throws Exception;

	public List<CronSepVO> CronSepmInsert() throws Exception;

	public void CronSepDelete() throws Exception;

	public void CronSepResult(CronResultVO vo) throws Exception;

}