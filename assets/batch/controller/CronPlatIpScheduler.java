package ipmn.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.batch.service.CronPlatIpService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;

public class CronPlatIpScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronPlatIpScheduler.class);
	
	@Autowired
	private CronPlatIpService CronPlatIpService;
	
	@Scheduled(cron="0 20 03 * * *")
	public void CronPlatIpRun() {
	
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		CronResultVO resultvo = new CronResultVO();
		
		String _Container = IpmnGetProperties.getProperty("Batch");
		String _instence  = System.getProperty("env.servername");
		
		logger.debug("_Container : " + _Container);
		logger.debug("_instence  : " + _instence);
		
		/* 특정 컨테이너에서 수행되도록  설정 */
		if ( _Container.equals(_instence))
		{
		
		try {
		
			logger.info("==========  CRON PLATIP BATCH START  ==========");
			
			CronPlatIpService.CronPlatIpDelete();
			
			CronPlatIpService.getPlatIpJson();
			
			int totCnt= CronPlatIpService.CronPlatIpCnt();
			
			resultvo.setR_cnt(totCnt);
			resultvo.setR_cd("12");
			resultvo.setR_result("CRON PLATIP BATCH OK");
			
			CronPlatIpService.CronPlatIpResult(resultvo);
			
			logger.info("==========  CRON PLATIP BATCH OK  ==========");
			
			} catch (Exception e) {
				resultvo.setR_cnt(0);
				resultvo.setR_cd("12");
				resultvo.setR_result("CRON PLATIP BATCH ERROR");
				
				try {
					CronPlatIpService.CronPlatIpResult(resultvo);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				logger.info("==========  CRON PLATIP BATCH ERROR  ==========");
				
				e.printStackTrace();
			}
		
		}
	}

}