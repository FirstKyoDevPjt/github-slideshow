package ipmn.rest.apisub;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("ApiSubService")
public  class ApiSubServiceImpl implements ApiSubService   {

    private static final Logger logger = LoggerFactory.getLogger(ApiSubServiceImpl.class);
	
	private ApiSubDAO ApiSubDAO;

	@Autowired
	private SqlSession sqlSession;
	
	public List<ApiSubVO> ApiSubInfo(String ip) throws Exception {

		List<ApiSubVO> vo = sqlSession.selectList("ApiInfo.ApiIpSubSelect", ip);

		return vo; 
	}

}
