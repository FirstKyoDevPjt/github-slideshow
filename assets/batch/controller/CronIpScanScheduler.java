package ipmn.batch.controller;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.batch.service.CronIpScanService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;


public class CronIpScanScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronIpScanScheduler.class);
	
	@Autowired
	private CronIpScanService CronIpScanService;
	
	@Scheduled(cron="0 10 04 * * *")
	public void CronIpScanRun() {
	
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		Connection conn= null;
		CronResultVO resultvo = new CronResultVO();
		
		String _Container = IpmnGetProperties.getProperty("Batch");
		String _instence  = System.getProperty("env.servername");

		logger.debug("_Container : " + _Container);
		logger.debug("_instence  : " + _instence);

		/* 특정 컨테이너에서 수행되도록  설정 */
		
		if ( _Container.equals(_instence))
		{
		
			// 1. JDBC Driver 로딩
			try 
			{
			
				logger.info("==========  CRON IPSCAN BATCH START  ==========");
				
				conn = CronIpScanService.doConnect(IpmnGetProperties.getProperty("IPSCAN.DriverClassName"),
													IpmnGetProperties.getProperty("IPSCAN.Url"),
													IpmnGetProperties.getProperty("IPSCAN.UserName"),
													IpmnGetProperties.getProperty("IPSCAN.Password"),
													conn);
				
				if (conn != null){
				
					CronIpScanService.CronIpScanDelete();
					
					CronIpScanService.getIpScanSystemInfo(conn);
					
					int totCnt= CronIpScanService.CronIpScanCnt();
					
					resultvo.setR_cnt(totCnt);
					resultvo.setR_cd("2");
					resultvo.setR_result("CRON IPSCAN BATCH OK");
					CronIpScanService.CronIpScanResult(resultvo);

					logger.info("==========  CRON IPSCAN BATCH OK  ==========");
					
					//자원 반납
					CronIpScanService.dbEndConnect(conn);

				}else
				{
					resultvo.setR_cnt(0);
					resultvo.setR_cd("2");
					resultvo.setR_result("CRON IPSCAN BATCH ERROR");
					CronIpScanService.CronIpScanResult(resultvo);

					logger.info("==========  CRON IPSCAN BATCH ERROR  ==========");

				}
			} catch (Exception  e) {
				e.printStackTrace();
			}

		}
	}
}