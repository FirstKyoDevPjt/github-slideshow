package ipmn.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.batch.service.CronPlatSubnetService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;

public class CronPlatSubnetScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronPlatSubnetScheduler.class);
	
	@Autowired
	private CronPlatSubnetService CronPlatSubnetService;
	
	@Scheduled(cron="0 30 03 * * *")
	public void CronPlatSubnetRun() {
	
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		CronResultVO resultvo = new CronResultVO();
		
		String _Container = IpmnGetProperties.getProperty("Batch");
		String _instence  = System.getProperty("env.servername");
		
		logger.debug("_Container : " + _Container);
		logger.debug("_instence  : " + _instence);
		
		/* 특정 컨테이너에서 수행되도록  설정 */
		if ( _Container.equals(_instence))
		{
		
			try 
			{
			
				logger.info("==========  CRON PLATSUBNET BATCH START  ==========");

				CronPlatSubnetService.CronPlatSubnetDelete();
				
				CronPlatSubnetService.getPlatSubnetJson();
				
				int totCnt= CronPlatSubnetService.CronPlatSubnetCnt();
				
				resultvo.setR_cnt(totCnt);
				resultvo.setR_cd("13");
				resultvo.setR_result("CRON PLATSUBNET BATCH OK");
				
				CronPlatSubnetService.CronPlatSubnetResult(resultvo);
			
				CronPlatSubnetService.CronIpZoneCodeInsert();
				
				logger.info("==========  CRON PLATSUBNET BATCH OK  ==========");
			
			} catch (Exception e) {

				resultvo.setR_cnt(0);
				resultvo.setR_cd("13");
				resultvo.setR_result("CRON PLATSUBNET BATCH ERROR");
				
				try {

					CronPlatSubnetService.CronPlatSubnetResult(resultvo);
					logger.info("==========  CRON PLATSUBNET BATCH ERROR  ==========");

				} catch (Exception e1) {

					e1.printStackTrace();
				}
				
				e.printStackTrace();
			}
		
		}
	}

}