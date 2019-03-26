package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronSccmVO;

public interface  CronSccmDAO {
	
	public int CronSccmCnt() throws Exception;

	public List<CronSccmVO> CronSccmInsert() throws Exception;

	public void CronSccmDelete() throws Exception;

	public void CronSccmResult(CronResultVO vo) throws Exception;

}