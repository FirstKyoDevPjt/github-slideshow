package ipmn.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.batch.service.CronDhcpIpService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;

public class CronDhcpIpScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronDhcpIpScheduler.class);
	
	@Autowired
	private CronDhcpIpService CronDhcpIpService;
	
	@Scheduled(cron="0 50 02 * * *")
	public void CronDhcpIpRun() {
	
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
		
			logger.info("==========  CRON DhcpIP BATCH START  ==========");
			
			CronDhcpIpService.CronDhcpIpDelete();
			
			CronDhcpIpService.getDhcpIpJson();
			
			int totCnt= CronDhcpIpService.CronDhcpIpCnt();
			
			resultvo.setR_cnt(totCnt);
			resultvo.setR_cd("20");
			resultvo.setR_result("CRON DHCPIP BATCH OK");
			
			CronDhcpIpService.CronDhcpIpResult(resultvo);
			
			logger.info("==========  CRON DHCPIP BATCH OK  ==========");
			
			} catch (Exception e) {
				resultvo.setR_cnt(0);
				resultvo.setR_cd("20");
				resultvo.setR_result("CRON DHCPIP BATCH ERROR");
				
				try {
					CronDhcpIpService.CronDhcpIpResult(resultvo);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				logger.info("==========  CRON DHCPIP BATCH ERROR  ==========");
				
				e.printStackTrace();
			}
		
		}
	}

}