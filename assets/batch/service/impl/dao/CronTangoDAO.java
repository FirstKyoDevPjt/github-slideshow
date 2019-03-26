package ipmn.batch.service.impl.dao;

import java.util.List;

import ipmn.batch.vo.CronCmnCdVO;
import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronTangoIpBandCdVO;
import ipmn.batch.vo.CronTangoIpBandVO;
import ipmn.batch.vo.CronTangoIpInfoVO;

public interface  CronTangoDAO {
	
	public List<CronTangoIpInfoVO> CronTangoIpInfoInsert() throws Exception;
	public List<CronTangoIpBandVO> CronTangoIpBandInsert() throws Exception;
	public List<CronTangoIpBandCdVO> CronTangoIpBandCdInsert() throws Exception;
	
	public void CronTangoIpInfoDelete() throws Exception;
	public void CronTangoIpBandDelete() throws Exception;
	public void CronTangoIpBandCdDelete() throws Exception;
	
	public void CronTangoResult(CronResultVO vo) throws Exception;
	
	public int CronTangoIpInfoCnt() throws Exception;
	public int CronTangoIpBandCnt() throws Exception;
	public int CronTangoIpBandCdCnt() throws Exception;
	
	public void CronIpZoneCodeInsert() throws Exception;

	public int CronCmnCdCnt() throws Exception;
	public List<CronCmnCdVO> CronCmnCdInsert() throws Exception;

}
