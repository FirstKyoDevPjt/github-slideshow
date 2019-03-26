package ipmn.batch.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ipmn.batch.service.CronStuserService;
import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronStuserVO;

@Service("CronStuserService")
public  class CronStuserServiceImpl implements CronStuserService   {

    private static final Logger logger = LoggerFactory.getLogger(CronStuserServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	public void CronStuserDelete(CronStuserVO vo) throws Exception {

		sqlSession.delete("CronStuser.CronStuserDelete", vo);
		
	}
	
	public List<CronStuserVO>  CronStuserSelectList() throws Exception {

		List<CronStuserVO> list = sqlSession.selectList("CronStuser.CronStuserSelect");

		return list; 
	}
	
	public void CronStuserUpdate(CronStuserVO vo) throws Exception {

		sqlSession.update("CronStuser.CronStuserUpdate", vo);
		
	}
	
	public void CronIpDtlInfoResult(CronResultVO vo) throws Exception {

		sqlSession.insert("CronStuser.CronStuserResult", vo);
		
	}
    
}
