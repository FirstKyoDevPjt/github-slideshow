package ipmn.rest.firewall;

import java.sql.Connection;
import java.util.List;

public interface FireWallService {

	Connection doConnect(String driver, String url, String id, String passwd, Connection conn) throws Exception;
	void dbEndConnect(Connection conn) throws Exception;
	
	/* FireWall Info  */
	public List<FireWallVO> GetFireWallInfo(String ip);

}