package ipmn.rest.honey;

import java.util.List;

public interface  HoneyDAO {
	
//    public List HoneyIpList() throws Exception;
	public List<HoneyVO> HoneyIpList(String ip) throws Exception;

}
