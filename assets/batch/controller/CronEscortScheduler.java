package ipmn.batch.controller;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.batch.service.CronEscortService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;

public class CronEscortScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronEscortScheduler.class);
	
	@Autowired
	private CronEscortService CronEscortService;
	
	@Scheduled(cron="0 00 04 * * *")
	public void CronEscortRun() {
	
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
		
			try
			{
			
				logger.info("==========  CRON ESCORT BATCH START  ==========");
				
				conn = CronEscortService.doConnect(IpmnGetProperties.getProperty("ESCORT.DriverClassName"),
													IpmnGetProperties.getProperty("ESCORT.Url"),
													IpmnGetProperties.getProperty("ESCORT.UserName"),
													IpmnGetProperties.getProperty("ESCORT.Password"),
													conn);
				
				if (conn != null){
				
					//기존 데이터 삭제
					CronEscortService.CronEscortDelete();
					
					//SEP System 정보 가져와서 데이터 넣기
					CronEscortService.getEscortSystemInfo(conn);
					
					int totCnt= CronEscortService.CronEscortCnt();
					resultvo.setR_cnt(totCnt);
					resultvo.setR_cd("1");
					resultvo.setR_result("CRON ESCORT BATCH OK");
					CronEscortService.CronEscortResult(resultvo);
					
					logger.info("==========  CRON ESCORT BATCH OK  ==========");
					
					//자원 반납
					CronEscortService.dbEndConnect(conn);
				
				}else
				{
					resultvo.setR_cnt(0);
					resultvo.setR_cd("1");
					resultvo.setR_result("CRON ESCORT BATCH ERROR");
					CronEscortService.CronEscortResult(resultvo);
					logger.info("==========  CRON ESCORT BATCH ERROR  ==========");
				
				}
			
			}
			
			catch (Exception  e) {
				e.printStackTrace();
			}
			
		}
	}
}
