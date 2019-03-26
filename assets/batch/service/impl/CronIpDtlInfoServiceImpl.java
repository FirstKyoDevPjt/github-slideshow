package ipmn.batch.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ipmn.batch.service.CronIpDtlInfoService;
import ipmn.batch.vo.CronResultVO;

@Service("CronIpDtlInfoService")
public  class CronIpDtlInfoServiceImpl implements CronIpDtlInfoService   {

    private static final Logger logger = LoggerFactory.getLogger(CronIpDtlInfoServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
    public void CronIpDtlInfoResult(CronResultVO vo) throws Exception{
    	
		logger.debug("======== CronBatchResult  start  =======");

    	sqlSession.insert("CronIpDtlInfo.CronIpDtlInfoResult", vo);

    }

	public int CronIpDtlInfoCnt() throws Exception {
		logger.debug("======== CronIpDtlInfoCnt  start  =======");

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronIpDtlInfo.CronIpDtlInfoCnt");

		return tCnt;
	}

    public void CronIpDtlInfoInsert() throws Exception{
    	
		logger.debug("======== CronIpDtlInfoInsert  start  =======");

		sqlSession.insert("CronIpDtlInfo.CronIpDtlInfoInsert");
    }
    
    public void CronIpDtlInfoDelete() throws Exception{
    	
		logger.debug("======== CronIpDtlInfoDelete  start  =======");

		sqlSession.delete("CronIpDtlInfo.CronIpDtlInfoDelete");
    }
	    
}
