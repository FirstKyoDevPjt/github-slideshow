package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronEscortVO;
import ipmn.batch.vo.CronResultVO;

public interface  CronEscortDAO {
	
	public int CronEscortCnt() throws Exception;

	public List<CronEscortVO> CronEscortInsert() throws Exception;

	public void CronEscortDelete() throws Exception;

	public void CronEscortResult(CronResultVO vo) throws Exception;

}