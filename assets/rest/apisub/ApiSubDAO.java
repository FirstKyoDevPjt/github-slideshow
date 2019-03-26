package ipmn.rest.apisub;

import java.util.List;

public interface  ApiSubDAO {
	
	public List<ApiSubVO> ApiInfo(String ip) throws Exception;

}
