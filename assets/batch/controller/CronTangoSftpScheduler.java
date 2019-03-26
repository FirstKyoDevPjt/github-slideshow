package ipmn.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.batch.service.CronTangoSftpService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;

public class CronTangoSftpScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronTangoSftpScheduler.class);
	
	@Autowired
	private CronTangoSftpService CronTangoSftpService;
	
	@Scheduled(cron="0 00 08 * * *")
	public void CronTangoRun() {
	
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
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
				logger.info("==========  CRON TANGO SFTP BATCH START  ==========");

				CronTangoSftpService.getSftpConnect();

				resultvo.setR_cnt(1);
				resultvo.setR_cd("24");
				resultvo.setR_result("CRON TANGO SFTP BATCH OK");
				
				CronTangoSftpService.CronTangoResult(resultvo);
			
				logger.info("==========  CRON TANGO SFTP BATCH OK  ==========");

			} catch (Exception e) {
				
				resultvo.setR_cnt(0);
				resultvo.setR_cd("24");
				resultvo.setR_result("CRON TANGO SFTP BATCH ERROR");
				try {
					CronTangoSftpService.CronTangoResult(resultvo);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				logger.info("==========  CRON TANGO BANDCD BATCH ERROR  ==========");

				e.printStackTrace();
			}
			
		}
	}

}