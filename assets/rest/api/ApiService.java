package ipmn.rest.api;

import java.util.List;

public interface ApiService {

	public List<ApiVO> ApiInfo(String ip) throws Exception;

}