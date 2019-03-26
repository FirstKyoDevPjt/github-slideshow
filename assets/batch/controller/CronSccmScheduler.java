package ipmn.batch.controller;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.batch.service.CronSccmService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;

public class CronSccmScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronSccmScheduler.class);
	
	@Autowired
	private CronSccmService CronSccmService;
	
	@Scheduled(cron="0 00 03 * * *")
	public void CronSccmRun() {
	
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
			
				conn = CronSccmService.doConnect(IpmnGetProperties.getProperty("SCCM.DriverClassName"),
													IpmnGetProperties.getProperty("SCCM.Url"),
													IpmnGetProperties.getProperty("SCCM.UserName"),
													IpmnGetProperties.getProperty("SCCM.Password"),
													conn);
				
				if (conn != null){
				
					logger.info("==========  CRON SCCM BATCH START  ==========");

					//기존 데이터 삭제
					CronSccmService.CronSccmDelete();
					
					//SCCM System 정보 가져와서 데이터 넣기
					CronSccmService.getSccmSystemInfo(conn);
					
					int totCnt= CronSccmService.CronSccmCnt();
					resultvo.setR_cnt(totCnt);
					resultvo.setR_cd("5");
					resultvo.setR_result("CRON SCCM BATCH OK");
					CronSccmService.CronSccmResult(resultvo);
					
					logger.info("==========  CRON SCCM BATCH OK  ==========");

					//자원 반납
					CronSccmService.dbEndConnect(conn);
				
				}else
				{
					resultvo.setR_cnt(0);
					resultvo.setR_cd("5");
					resultvo.setR_result("CRON SCCM BATCH ERROR");
					CronSccmService.CronSccmResult(resultvo);
				
					logger.info("==========  CRON SCCM BATCH ERROR  ==========");

				}
				
			}
			catch (Exception  e) {
				e.printStackTrace();
			}
		
		}
	}
}