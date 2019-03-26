package ipmn.rest.apisub;

import java.util.List;

public interface ApiSubService {

	public List<ApiSubVO> ApiSubInfo(String ip) throws Exception;

}