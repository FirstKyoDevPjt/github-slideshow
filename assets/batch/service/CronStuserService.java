package ipmn.batch.service;

import java.util.List;

import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronStuserVO;

public interface CronStuserService {

	/* STUSER  */
	void CronStuserDelete(CronStuserVO vo) throws Exception;
	List<CronStuserVO> CronStuserSelectList() throws Exception;
	void CronStuserUpdate(CronStuserVO vo) throws Exception;
	void CronIpDtlInfoResult(CronResultVO vo) throws Exception;

}