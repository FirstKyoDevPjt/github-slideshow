package ipmn.batch.vo;

import org.apache.commons.lang3.StringUtils;

public class CronTangoIpBandVO {

	private String asgnSeq;
	private String uprAsgnSeq;
	private String ipDivCd;
	private String ipRscDivCd;
	private String ipSrvcDivCd;
	private String ipMtsoDivCd;
	private String ipSystmDivCd;
	private String ipAddr;
	private String bitVal;
	private String asgnNm;
	private String usePurpCtt;
	private String poolUseYn;
	private String regTypCd;
	private String bcastipAddr;
	private String staIpNo;
	private String endIpNo;
	private String quotaIpCnt;
	private String allIpCnt;
	private String frstRegDate;
	private String lastChgDate;
	 
	public String getAsgnSeq() {
		return asgnSeq;
	}
	public void setAsgnSeq(String asgnSeq) {
		this.asgnSeq = StringUtils.stripToNull(asgnSeq);
	}
	public String getUprAsgnSeq() {
		return uprAsgnSeq;
	}
	public void setUprAsgnSeq(String uprAsgnSeq) {
		this.uprAsgnSeq = StringUtils.stripToNull(uprAsgnSeq);
	}
	public String getIpDivCd() {
		return ipDivCd;
	}
	public void setIpDivCd(String ipDivCd) {
		this.ipDivCd = ipDivCd;
	}
	public String getIpRscDivCd() {
		return ipRscDivCd;
	}
	public void setIpRscDivCd(String ipRscDivCd) {
		this.ipRscDivCd = ipRscDivCd;
	}
	public String getIpSrvcDivCd() {
		return ipSrvcDivCd;
	}
	public void setIpSrvcDivCd(String ipSrvcDivCd) {
		this.ipSrvcDivCd = ipSrvcDivCd;
	}
	public String getIpMtsoDivCd() {
		return ipMtsoDivCd;
	}
	public void setIpMtsoDivCd(String ipMtsoDivCd) {
		this.ipMtsoDivCd = ipMtsoDivCd;
	}
	public String getIpSystmDivCd() {
		return ipSystmDivCd;
	}
	public void setIpSystmDivCd(String ipSystmDivCd) {
		this.ipSystmDivCd = ipSystmDivCd;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getBitVal() {
		return bitVal;
	}
	public void setBitVal(String bitVal) {
		this.bitVal = StringUtils.stripToNull(bitVal);
	}
	public String getAsgnNm() {
		return asgnNm;
	}
	public void setAsgnNm(String asgnNm) {
		this.asgnNm = asgnNm;
	}
	public String getUsePurpCtt() {
		return usePurpCtt;
	}
	public void setUsePurpCtt(String usePurpCtt) {
		this.usePurpCtt = usePurpCtt;
	}
	public String getPoolUseYn() {
		return poolUseYn;
	}
	public void setPoolUseYn(String poolUseYn) {
		this.poolUseYn = poolUseYn;
	}
	public String getRegTypCd() {
		return regTypCd;
	}
	public void setRegTypCd(String regTypCd) {
		this.regTypCd = regTypCd;
	}
	public String getBcastipAddr() {
		return bcastipAddr;
	}
	public void setBcastipAddr(String bcastipAddr) {
		this.bcastipAddr = bcastipAddr;
	}
	public String getStaIpNo() {
		return staIpNo;
	}
	public void setStaIpNo(String staIpNo) {
		this.staIpNo = StringUtils.stripToNull(staIpNo);
	}
	public String getEndIpNo() {
		return endIpNo;
	}
	public void setEndIpNo(String endIpNo) {
		this.endIpNo = StringUtils.stripToNull(endIpNo);
	}
	public String getQuotaIpCnt() {
		return quotaIpCnt;
	}
	public void setQuotaIpCnt(String quotaIpCnt) {
		this.quotaIpCnt = StringUtils.stripToNull(quotaIpCnt);
	}
	public String getAllIpCnt() {
		return allIpCnt;
	}
	public void setAllIpCnt(String allIpCnt) {
		this.allIpCnt = StringUtils.stripToNull(allIpCnt);
	}
	public String getFrstRegDate() {
		return frstRegDate;
	}
	public void setFrstRegDate(String frstRegDate) {
		this.frstRegDate = frstRegDate;
	}
	public String getLastChgDate() {
		return lastChgDate;
	}
	public void setLastChgDate(String lastChgDate) {
		this.lastChgDate = lastChgDate;
	}

}