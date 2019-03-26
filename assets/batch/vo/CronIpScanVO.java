package ipmn.batch.vo;

public class CronIpScanVO {
		
	String str_ip;
	String mac;
	String probe_id;
	String hname;
	String user_id_mac;
	String user_nm_mac;
	String user_sm_mac;
	String os_type;
	
	public String getStr_ip() {
		return str_ip;
	}
	public void setStr_ip(String str_ip) {
		this.str_ip = str_ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getProbe_id() {
		return probe_id;
	}
	public void setProbe_id(String probe_id) {
		this.probe_id = probe_id;
	}
	public String getHname() {
		return hname;
	}
	public void setHname(String hname) {
		this.hname = hname;
	}
	public String getUser_id_mac() {
		return user_id_mac;
	}
	public void setUser_id_mac(String user_id_mac) {
		this.user_id_mac = user_id_mac;
	}
	public String getUser_nm_mac() {
		return user_nm_mac;
	}
	public void setUser_nm_mac(String user_nm_mac) {
		this.user_nm_mac = user_nm_mac;
	}
	public String getUser_sm_mac() {
		return user_sm_mac;
	}
	public void setUser_sm_mac(String user_sm_mac) {
		this.user_sm_mac = user_sm_mac;
	}
	public String getOs_type() {
		return os_type;
	}
	public void setOs_type(String os_type) {
		this.os_type = os_type;
	}
	
}
