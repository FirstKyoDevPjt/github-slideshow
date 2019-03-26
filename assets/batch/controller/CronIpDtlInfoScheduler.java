package ipmn.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import ipmn.batch.service.CronIpDtlInfoService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;

public class CronIpDtlInfoScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronIpDtlInfoScheduler.class);
	
	@Autowired
	private CronIpDtlInfoService CronIpDtlInfoService;
	
	@Scheduled(cron="0 20 05 * * *")
	public void CronIpDtlInfoRun() {
	
		CronResultVO resultvo = new CronResultVO();
		
		String _Container = IpmnGetProperties.getProperty("Batch");
		String _instence  = System.getProperty("env.servername");
		
//		String _instence  = "Container1";
		
		logger.debug("_Container : " + _Container);
		logger.debug("_instence  : " + _instence);
		
		/* 특정 컨테이너에서 수행되도록  설정 */
		
		if ( _Container.equals(_instence))
		{
		
			try	
			{
				logger.info("==========  CRON IPDTLINFO BATCH START  ==========");
				
				//기존 데이터 삭제
				CronIpDtlInfoService.CronIpDtlInfoDelete();
				
				CronIpDtlInfoService.CronIpDtlInfoInsert();
				
				int totCnt= CronIpDtlInfoService.CronIpDtlInfoCnt();
				resultvo.setR_cnt(totCnt);
				resultvo.setR_cd("11");
				resultvo.setR_result("CRON IPDTLINFO BATCH OK");
				CronIpDtlInfoService.CronIpDtlInfoResult(resultvo);
				
				logger.info("==========  CRON IPDTLINFO BATCH OK  ==========");
				
			} catch (Exception  e) {
				
				resultvo.setR_cnt(0);
				resultvo.setR_cd("11");
				resultvo.setR_result("CRON IPDTLINFO BATCH ERROR");
				
				try 
				{
				
					CronIpDtlInfoService.CronIpDtlInfoResult(resultvo);
					logger.info("==========  CRON IPDTLINFO BATCH ERROR  ==========");
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				e.printStackTrace();
			}
		
		}
	}
}
