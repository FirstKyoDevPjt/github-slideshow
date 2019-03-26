package ipmn.rest.api;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("ApiService")
public  class ApiServiceImpl implements ApiService   {

    private static final Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);
	
	private ApiDAO ApiDAO;

	@Autowired
	private SqlSession sqlSession;
	
	public List<ApiVO> ApiInfo(String ip) throws Exception {

		List<ApiVO> vo = sqlSession.selectList("ApiInfo.ApiIpInfoSelect", ip);

		return vo; 
	}
    
}
