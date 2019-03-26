package ipmn.rest.honey;

import java.util.List;

public interface HoneyService {

	/* Honey  */
//	List HoneyIpList() throws Exception;
	public List<HoneyVO> HoneyIpList(String ip) throws Exception;
}