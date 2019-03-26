package ipmn.batch.service;

import ipmn.batch.vo.CronCmnCdVO;
import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronTangoIpBandCdVO;
import ipmn.batch.vo.CronTangoIpBandVO;
import ipmn.batch.vo.CronTangoIpInfoVO;

public interface CronTangoService {

	/* Tango IpInfp, IpBand, IpBandCd  */
	void CronTangoIpInfoDelete() throws Exception;
	void CronTangoIpBandDelete() throws Exception;
	void CronTangoIpBandCdDelete() throws Exception;

	void CronTangoIpInfoInsert(CronTangoIpInfoVO vo) throws Exception;
	void CronTangoIpBandInsert(CronTangoIpBandVO vo) throws Exception;
	void CronTangoIpBandCdInsert(CronTangoIpBandCdVO vo) throws Exception;

	int CronTangoIpInfoCnt() throws Exception;
	int CronTangoIpBandCnt() throws Exception;
	int CronTangoIpBandCdCnt() throws Exception;

	void CronTangoResult(CronResultVO vo) throws Exception;

	void CronTangoIpBandCdInsert();
	void CronTangoIpBandInsert();
	void CronTangoIpInfoInsert();
	
	void CronIpZoneCodeInsert() throws Exception;
	
	void CronCmnCdInsert(CronCmnCdVO vo) throws Exception;
	
	int CronCmnCdCnt() throws Exception;
//	void getSftpConnect() throws JSchException, JsonSyntaxException, IOException;
	
}