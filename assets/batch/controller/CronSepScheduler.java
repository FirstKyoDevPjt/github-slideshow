package ipmn.batch.controller;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.batch.service.CronSepService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;

public class CronSepScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronSepScheduler.class);
	
	@Autowired
	private CronSepService CronSepService;
	
	@Scheduled(cron="0 30 04 * * *")
	public void CronSepRun() {
	
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
			
				conn = CronSepService.doConnect(IpmnGetProperties.getProperty("SEP.DriverClassName"),
												IpmnGetProperties.getProperty("SEP.Url"),
												IpmnGetProperties.getProperty("SEP.UserName"),
												IpmnGetProperties.getProperty("SEP.Password"),
												conn);
				
				if (conn != null){
				
					logger.info("==========  CRON SEP BATCH START  ==========");

					//기존 데이터 삭제
					CronSepService.CronSepDelete();
					
					//SCCM System 정보 가져와서 데이터 넣기
					CronSepService.getSepSystemInfo(conn);
					
					int totCnt= CronSepService.CronSepCnt();
					resultvo.setR_cnt(totCnt);
					resultvo.setR_cd("6");
					resultvo.setR_result("CRON SEP BATCH OK");
					CronSepService.CronSepResult(resultvo);
					
					logger.info("==========  CRON SEP BATCH OK  ==========");

					//자원 반납
					CronSepService.dbEndConnect(conn);
				
				}else
				{
					resultvo.setR_cnt(0);
					resultvo.setR_cd("6");
					resultvo.setR_result("CRON SEP BATCH ERROR");
					CronSepService.CronSepResult(resultvo);
				
					logger.info("==========  CRON SEP BATCH ERROR  ==========");

				}
			
			}
			catch (Exception  e) {
				e.printStackTrace();
			}
		
		}
	}
}