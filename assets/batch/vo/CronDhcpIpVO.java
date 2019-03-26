package ipmn.batch.vo;

public class CronDhcpIpVO {
	
	private String ipAddr;
	private String network;
	private String mac;
	private String reqNm;
	private String remark;
	
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getReqNm() {
		return reqNm;
	}
	public void setReqNm(String reqNm) {
		this.reqNm = reqNm;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
