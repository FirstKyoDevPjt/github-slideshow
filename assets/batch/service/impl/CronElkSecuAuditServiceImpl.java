package ipmn.batch.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ipmn.batch.service.CronElkSecuAuditService;
import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronSecuAuditVO;


@Service("CronElkSecuAuditService")
public  class CronElkSecuAuditServiceImpl implements CronElkSecuAuditService   {

    private static final Logger logger = LoggerFactory.getLogger(CronElkSecuAuditServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
    public void CronSecuAuditResult(CronResultVO vo){
    	
		logger.debug("======== CronBatchResult  start  =======");

    	sqlSession.insert("CronSecuAudit.CronSecuAuditResult", vo);

    }

	public int CronSecuAuditCnt() throws Exception {
		logger.debug("======== CronSecuAuditCnt  start  =======");

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronSecuAudit.CronSecuAuditCnt");
		return tCnt;
	}

    public void CronSecuAuditInsert(CronSecuAuditVO vo) throws Exception{
    	
		logger.debug("======== CronSolidInsert  start  =======");

		sqlSession.insert("CronSecuAudit.CronSecuAuditInsert", vo);
    }
    
    public void CronSecuAuditDelete() throws Exception{
    	
		logger.debug("======== CronSecuAuditDelete  start  =======");

		sqlSession.delete("CronSecuAudit.CronSecuAuditDelete");
    }

}
