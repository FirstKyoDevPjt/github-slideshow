package ipmn.batch.vo;

public class CronPlatSubnetVO {
	
	private String  subnet;
	private String  cidr;
	private String  netmask;
	private String  startIp;
	private String  endIp;
	private String  name;
	private String  classBand;

	public String getSubnet() {
		return subnet;
	}
	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}
	public String getCidr() {
		return cidr;
	}
	public void setCidr(String cidr) {
		this.cidr = cidr;
	}
	public String getNetmask() {
		return netmask;
	}
	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}
	public String getStartIp() {
		return startIp;
	}
	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}
	public String getEndIp() {
		return endIp;
	}
	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassBand() {
		return classBand;
	}
	public void setClassBand(String classBand) {
		this.classBand = classBand;
	}

	
	
}