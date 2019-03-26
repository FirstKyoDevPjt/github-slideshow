package ipmn.batch.controller;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.batch.service.CronDnsReqService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;

public class CronDnsReqScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronDnsReqScheduler.class);
	
	@Autowired
	private CronDnsReqService CronDnsReqService;
	
	@Scheduled(cron="0 20 04 * * *")
	public void CronDnsReqRun() {
	
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
				conn = CronDnsReqService.doConnect(IpmnGetProperties.getProperty("DNS.DriverClassName"),
													IpmnGetProperties.getProperty("DNS.Url"),
													IpmnGetProperties.getProperty("DNS.UserName"),
													IpmnGetProperties.getProperty("DNS.Password"),
													conn);
				
				if (conn != null){
				
					logger.info("==========  CRON DNS REQUEST BATCH START  ==========");

					//기존 데이터 삭제
					CronDnsReqService.CronDnsReqDelete();
					
					//SCCM System 정보 가져와서 데이터 넣기
					CronDnsReqService.getDnsReqSystemInfo(conn);
					
					int totCnt= CronDnsReqService.CronDnsReqCnt();
					resultvo.setR_cnt(totCnt);
					resultvo.setR_cd("15");
					resultvo.setR_result("CRON DNS REQUEST BATCH OK");
					CronDnsReqService.CronDnsReqResult(resultvo);
					
					logger.info("==========  CRON DNS REQUEST BATCH OK  ==========");

					//자원 반납
					CronDnsReqService.dbEndConnect(conn);
				
				}else
				{
					resultvo.setR_cnt(0);
					resultvo.setR_cd("15");
					resultvo.setR_result("CRON DNS REQUEST BATCH ERROR");
					CronDnsReqService.CronDnsReqResult(resultvo);
				
					logger.info("==========  CRON DNS REQUEST BATCH ERROR  ==========");

				}
			
			}
			catch (Exception  e) {
				e.printStackTrace();
			}
		
		}
	}
}