package ipmn.rest.hioms;

public class HiOmsVO {
	
	private String  hi_oms_no;
	private String  reqUserId;
	private String  reqUserNm;
	private String  reqUserDept;
	private String  mngId;
	private String  mngNm;
	private String  mngDept;
	private String  apclId;
	private String  apclNm;
	private String  apclDept;
	
	private boolean success;
	private String  reqUser_resultMsg;
	private String  mngUser_resultMsg;
	private String  apclUser_resultMsg;
	private String  modal_resultMsg;
	public String getHi_oms_no() {
		return hi_oms_no;
	}
	public void setHi_oms_no(String hi_oms_no) {
		this.hi_oms_no = hi_oms_no;
	}
	public String getReqUserId() {
		return reqUserId;
	}
	public void setReqUserId(String reqUserId) {
		this.reqUserId = reqUserId;
	}
	public String getReqUserNm() {
		return reqUserNm;
	}
	public void setReqUserNm(String reqUserNm) {
		this.reqUserNm = reqUserNm;
	}
	public String getReqUserDept() {
		return reqUserDept;
	}
	public void setReqUserDept(String reqUserDept) {
		this.reqUserDept = reqUserDept;
	}
	public String getMngId() {
		return mngId;
	}
	public void setMngId(String mngId) {
		this.mngId = mngId;
	}
	public String getMngNm() {
		return mngNm;
	}
	public void setMngNm(String mngNm) {
		this.mngNm = mngNm;
	}
	public String getMngDept() {
		return mngDept;
	}
	public void setMngDept(String mngDept) {
		this.mngDept = mngDept;
	}
	public String getApclId() {
		return apclId;
	}
	public void setApclId(String apclId) {
		this.apclId = apclId;
	}
	public String getApclNm() {
		return apclNm;
	}
	public void setApclNm(String apclNm) {
		this.apclNm = apclNm;
	}
	public String getApclDept() {
		return apclDept;
	}
	public void setApclDept(String apclDept) {
		this.apclDept = apclDept;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getReqUser_resultMsg() {
		return reqUser_resultMsg;
	}
	public void setReqUser_resultMsg(String reqUser_resultMsg) {
		this.reqUser_resultMsg = reqUser_resultMsg;
	}
	public String getMngUser_resultMsg() {
		return mngUser_resultMsg;
	}
	public void setMngUser_resultMsg(String mngUser_resultMsg) {
		this.mngUser_resultMsg = mngUser_resultMsg;
	}
	public String getApclUser_resultMsg() {
		return apclUser_resultMsg;
	}
	public void setApclUser_resultMsg(String apclUser_resultMsg) {
		this.apclUser_resultMsg = apclUser_resultMsg;
	}
	public String getModal_resultMsg() {
		return modal_resultMsg;
	}
	public void setModal_resultMsg(String modal_resultMsg) {
		this.modal_resultMsg = modal_resultMsg;
	}
	@Override
	public String toString() {
		return "HiOmsVO [hi_oms_no=" + hi_oms_no + ", reqUserId=" + reqUserId + ", reqUserNm=" + reqUserNm
				+ ", reqUserDept=" + reqUserDept + ", mngId=" + mngId + ", mngNm=" + mngNm + ", mngDept=" + mngDept
				+ ", apclId=" + apclId + ", apclNm=" + apclNm + ", apclDept=" + apclDept + ", success=" + success
				+ ", reqUser_resultMsg=" + reqUser_resultMsg + ", mngUser_resultMsg=" + mngUser_resultMsg
				+ ", apclUser_resultMsg=" + apclUser_resultMsg + ", modal_resultMsg=" + modal_resultMsg + "]";
	}
	
	
}