package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronStuserVO;

public interface CronStuserDAO {

	/* STUSER  */
	void CronStuserDelete(CronStuserVO vo) throws Exception;
	List<CronStuserVO> CronStuserSelectList() throws Exception;
	void CronStuserUpdate(CronStuserVO vo) throws Exception;
	void CronIpDtlInfoResult(CronResultVO vo) throws Exception;

}