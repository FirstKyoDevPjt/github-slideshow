package ipmn.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.batch.service.CronPingRsltService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;

public class CronPingRsltScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronPingRsltScheduler.class);
	
	@Autowired
	private CronPingRsltService CronPingRsltService;
	
	@Scheduled(cron="0 10 06 * * *")
	public void CronPlatIpRun() {
	
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		CronResultVO resultvo = new CronResultVO();
		
		String _Container = IpmnGetProperties.getProperty("Batch");
		String _instence  = System.getProperty("env.servername");

//		String _instence = "Container1";

		logger.debug("_Container : " + _Container);
		logger.debug("_instence  : " + _instence);
		
		/* 특정 컨테이너에서 수행되도록  설정 */
		if ( _Container.equals(_instence))
		{
		
		try {
		
			logger.info("==========  CRON PINGRSLT BATCH START  ==========");
			
			CronPingRsltService.CronPingRsltDelete();
			
			CronPingRsltService.getPingRsltInfo();
			
			int totCnt= CronPingRsltService.CronPingRsltCnt();
			
			resultvo.setR_cnt(totCnt);
			resultvo.setR_cd("16");
			resultvo.setR_result("CRON PINGRSLT BATCH OK");
			
			CronPingRsltService.CronPingRsltResult(resultvo);
			
			logger.info("==========  CRON PINGRSLT BATCH OK  ==========");
			
			} catch (Exception e) {
				resultvo.setR_cnt(0);
				resultvo.setR_cd("16");
				resultvo.setR_result("CRON PINGRSLT BATCH ERROR");
				
				try {
					CronPingRsltService.CronPingRsltResult(resultvo);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				logger.info("==========  CRON PINGRSLT BATCH ERROR  ==========");
				
				e.printStackTrace();
			}
		
		}
	}

}