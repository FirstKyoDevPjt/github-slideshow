package ipmn.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.batch.service.CronTangoService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;

public class CronTangoScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronTangoScheduler.class);
	
	@Autowired
	private CronTangoService CronTangoService;
	
	@Scheduled(cron="0 30 02 * * *")
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
				logger.info("==========  CRON TANGO BANDCD BATCH START  ==========");

//				CronTangoService.getSftpConnect();
				
				CronTangoService.CronTangoIpBandCdDelete();
				
				CronTangoService.CronTangoIpBandCdInsert();

				int totCnt = CronTangoService.CronTangoIpBandCdCnt();
				
				resultvo.setR_cnt(totCnt);
				resultvo.setR_cd("21");
				resultvo.setR_result("CRON TANGO BANDCD BATCH OK");
				
				CronTangoService.CronTangoResult(resultvo);
			
				logger.info("==========  CRON TANGO BANDCD BATCH OK  ==========");

			} catch (Exception e) {
				
				resultvo.setR_cnt(0);
				resultvo.setR_cd("21");
				resultvo.setR_result("CRON TANGO BANDCD BATCH ERROR");
				try {
					CronTangoService.CronTangoResult(resultvo);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				logger.info("==========  CRON TANGO BANDCD BATCH ERROR  ==========");

				e.printStackTrace();
			}
			
			try 
			{
				logger.info("==========  CRON TANGO INFO BATCH START  ==========");

				CronTangoService.CronTangoIpInfoDelete();
				
				CronTangoService.CronTangoIpInfoInsert();
				
				int totCnt = CronTangoService.CronTangoIpInfoCnt();
				
				resultvo.setR_cnt(totCnt);
				resultvo.setR_cd("22");
				resultvo.setR_result("CRON TANGO INFO BATCH OK");
				
				CronTangoService.CronTangoResult(resultvo);
			
				logger.info("==========  CRON TANGO INFO BATCH OK  ==========");

			} catch (Exception e) {
				
				resultvo.setR_cnt(0);
				resultvo.setR_cd("22");
				resultvo.setR_result("CRON TANGO INFO BATCH ERROR");
				try {
					CronTangoService.CronTangoResult(resultvo);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				logger.info("==========  CRON TANGO INFO BATCH ERROR  ==========");

				e.printStackTrace();
			}

			try 
			{
				logger.info("==========  CRON TANGO BAND BATCH START  ==========");

				CronTangoService.CronTangoIpBandDelete();
				
				CronTangoService.CronTangoIpBandInsert();
				
				CronTangoService.CronIpZoneCodeInsert();

				int totCnt = CronTangoService.CronTangoIpBandCnt();
				
				resultvo.setR_cnt(totCnt);
				resultvo.setR_cd("23");
				resultvo.setR_result("CRON TANGO BAND BATCH OK");

				CronTangoService.CronTangoResult(resultvo);

				logger.info("==========  CRON TANGO BAND BATCH OK  ==========");

			} catch (Exception e) {
				
				resultvo.setR_cnt(0);
				resultvo.setR_cd("23");
				resultvo.setR_result("CRON TANGO BAND BATCH ERROR");
				try {
					CronTangoService.CronTangoResult(resultvo);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				logger.info("==========  CRON TANGO BAND BATCH ERROR  ==========");

				e.printStackTrace();
			}
			
		}
	}

}