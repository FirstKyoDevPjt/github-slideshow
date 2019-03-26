package ipmn.rest.honey;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("HoneyService")
public  class HoneyServiceImpl implements HoneyService   {

    private static final Logger logger = LoggerFactory.getLogger(HoneyServiceImpl.class);
	
	private HoneyDAO HoneyDAO;

	@Autowired
	private SqlSession sqlSession;
	
/*    public List HoneyIpList() throws Exception {
    	
   	 return sqlSession.selectList("CronHoney.CronHoneySelect");
   }*/

	public List<HoneyVO> HoneyIpList(String ip) throws Exception {

		List<HoneyVO> vo = sqlSession.selectList("CronHoney.CronHoneySelect", ip);

		return vo; 
	}
    
}
