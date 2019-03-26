package ipmn.batch.controller;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.batch.service.CronTiamsService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;

public class CronTiamsScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronTiamsScheduler.class);
	
	@Autowired
	private CronTiamsService CronTiamsService;
	
	@Scheduled(cron="0 50 04 * * *")
	public void CronTiamsRun() {
	
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
			
				int cnt = CronTiamsService.getJSONLoopCount();
				
				logger.debug("getJSONLoopCount :: " + cnt);
				
				if (cnt!=0)
				{
				
					logger.info("==========  CRON TIAMS BATCH START  ==========");

					CronTiamsService.CronTiamsDelete();
					
					for(int i=1 ; i <= cnt; i++)
					{
						JSONArray TiAMSArray= CronTiamsService.getTiamsJson(i);
						CronTiamsService.executeTiamsArrayInsert(TiAMSArray);
					}
					
					int totCnt = CronTiamsService.CronTiamsCnt();
					
					resultvo.setR_cnt(totCnt);
					resultvo.setR_cd("7");
					resultvo.setR_result("CRON TIAMS BATCH OK");
					
					CronTiamsService.CronTiamsResult(resultvo);
				
					logger.info("==========  CRON TIAMS BATCH OK  ==========");

				}else{
				
					resultvo.setR_cnt(0);
					resultvo.setR_cd("7");
					resultvo.setR_result("CRON TIAMS BATCH ERROR");
					CronTiamsService.CronTiamsResult(resultvo);

					logger.info("==========  CRON TIAMS BATCH ERROR  ==========");
				
				}
			
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		
		}
	}

}