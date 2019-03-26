package ipmn.batch.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ipmn.batch.service.CronElkSolidService;
import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronSolidVO;


@Service("CronElkSolidService")
public  class CronElkSolidServiceImpl implements CronElkSolidService   {

    private static final Logger logger = LoggerFactory.getLogger(CronElkSolidServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
    public void CronSolidResult(CronResultVO vo){
    	
		logger.debug("======== CronBatchResult  start  =======");

    	sqlSession.insert("CronSolid.CronSolidResult", vo);

    }

	public int CronSolidCnt() throws Exception {
		logger.debug("======== CronSolidCnt  start  =======");

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronSolid.CronSolidCnt");
		return tCnt;
	}
    
    public void CronSolidInsert(CronSolidVO vo) throws Exception{
    	
		logger.debug("======== CronSolidInsert  start  =======");

		sqlSession.insert("CronSolid.CronSolidInsert", vo);
    }
    
    public void CronSolidDelete() throws Exception{
    	
		logger.debug("======== CronSolidDelete  start  =======");

		sqlSession.delete("CronSolid.CronSolidDelete");
    }

}
