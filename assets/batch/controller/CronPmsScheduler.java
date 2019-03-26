package ipmn.batch.controller;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.batch.service.CronPmsService;
import ipmn.batch.vo.CronPmsVO;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;


public class CronPmsScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronPmsScheduler.class);
	
	@Autowired
	private CronPmsService CronPmsService;
	
	private static final String PMS_SYSTEM_INTERNA  = "1";
	private static final String PMS_SYSTEM_EXTERNAL = "2";
	
	
	@Scheduled(cron="0 25 01 * * *")
	public void CronPmsRun() {
	
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		Connection conn= null;
		CronPmsVO vo = new CronPmsVO();
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
			
				conn = CronPmsService.doConnect(IpmnGetProperties.getProperty("PMS1.DriverClassName"),
												IpmnGetProperties.getProperty("PMS1.Url"),
												IpmnGetProperties.getProperty("PMS1.UserName"),
												IpmnGetProperties.getProperty("PMS1.Password"),
												conn);
				
				if (conn != null){
				
					logger.info("==========  CRON PMS1 BATCH START  ==========");
					
					vo.setGubun(PMS_SYSTEM_INTERNA);
					
					//기존 데이터 삭제
					CronPmsService.CronPmsDelete(vo);
					
					CronPmsService.getPmsSystemInfo(conn,PMS_SYSTEM_INTERNA);
					
					int totCnt= CronPmsService.CronPmsCnt();
					resultvo.setR_cnt(totCnt);
					resultvo.setR_cd("3");
					resultvo.setR_result("CRON PMS1 BATCH OK");
					CronPmsService.CronPmsResult(resultvo);

					logger.info("==========  CRON PMS1 BATCH OK  ==========");
					
					//자원 반납
					CronPmsService.dbEndConnect(conn);
				
				}else
				{
					resultvo.setR_cnt(0);
					resultvo.setR_cd("3");
					resultvo.setR_result("CRON PMS1 BATCH ERROR");
					CronPmsService.CronPmsResult(resultvo);

					logger.info("==========  CRON PMS1 BATCH ERROR  ==========");
				
				}
			
			}
			catch (Exception  e) {
				e.printStackTrace();
			}
			
			try
			{

				conn = CronPmsService.doConnect(IpmnGetProperties.getProperty("PMS2.DriverClassName"),
												IpmnGetProperties.getProperty("PMS2.Url"),
												IpmnGetProperties.getProperty("PMS2.UserName"),
												IpmnGetProperties.getProperty("PMS2.Password"),
												conn);
				
				if (conn != null){
				
					logger.info("==========  CRON PMS2 BATCH START  ==========");
				
					vo.setGubun(PMS_SYSTEM_EXTERNAL);
					
					//기존 데이터 삭제
					CronPmsService.CronPmsDelete(vo);
					
					CronPmsService.getPmsSystemInfo(conn,PMS_SYSTEM_EXTERNAL);
					
					int totCnt= CronPmsService.CronPmsCnt();
					resultvo.setR_cnt(totCnt);
					resultvo.setR_cd("4");
					resultvo.setR_result("CRON PMS2 BATCH OK");
					CronPmsService.CronPmsResult(resultvo);
					
					logger.info("==========  CRON PMS2 BATCH OK  ==========");

					//자원 반납
					CronPmsService.dbEndConnect(conn);
				
				}else
				{
					resultvo.setR_cnt(0);
					resultvo.setR_cd("4");
					resultvo.setR_result("CRON PMS2 BATCH ERROR");
					CronPmsService.CronPmsResult(resultvo);
				
					logger.info("==========  CRON PMS2 BATCH ERROR  ==========");
				}
			
			}
			catch (Exception  e) {
				e.printStackTrace();
			}
		
		}
	}
}