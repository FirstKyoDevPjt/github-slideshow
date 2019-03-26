package ipmn.rest.hioms;

import java.sql.Connection;

public interface HiOmsService {

	Connection doConnect(String driver, String url, String id, String passwd, Connection conn) throws Exception;
	void dbEndConnect(Connection conn) throws Exception;
	
	/* HIOMS  */
	void getHiOmsJson() throws Exception;

}