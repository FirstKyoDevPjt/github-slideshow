package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronTiamsVO;

public interface  CronTiamsDAO {
	
	public List<CronTiamsVO> CronTiamsInsert() throws Exception;
	
	public void CronTiamsDelete() throws Exception;
	
	public void CronTiamsResult(CronResultVO vo) throws Exception;
	
	public int CronTiamsCnt() throws Exception;

}
