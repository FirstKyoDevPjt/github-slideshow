package ipmn.rest.api;

import java.util.List;

public interface  ApiDAO {
	
	public List<ApiVO> ApiInfo(String ip) throws Exception;

}
