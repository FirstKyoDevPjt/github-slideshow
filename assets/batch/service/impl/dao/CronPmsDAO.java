package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronPmsVO;
import ipmn.batch.vo.CronResultVO;

public interface  CronPmsDAO {
	
	public int CronPmsCnt() throws Exception;

	public List<CronPmsVO> CronPmsInsert() throws Exception;

	public void CronPmsDelete(CronPmsVO vo) throws Exception;

	public void CronPmsResult(CronResultVO vo) throws Exception;

}