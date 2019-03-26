package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronSecuAuditVO;

public interface  CronElkSecuAuditDAO {
	
	public List<CronSecuAuditVO> CronElkSecuAuditInsert() throws Exception;
	
	public void CronElkSecuAuditDelete() throws Exception;
	
	public void CronElkSecuAuditResult(CronResultVO vo) throws Exception;
	
	public int CronElkSecuAuditCnt() throws Exception;

}
