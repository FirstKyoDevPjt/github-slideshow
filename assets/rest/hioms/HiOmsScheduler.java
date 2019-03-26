package ipmn.rest.hioms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.common.util.IpmnGetProperties;

public class HiOmsScheduler {

	private static final Logger logger = LoggerFactory.getLogger(HiOmsScheduler.class);
	
	@Autowired
	private HiOmsService HiOmsService;
	
	@Scheduled(cron="0 15 01 * * *")
	public void HiOmsRun() {
	
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		String _Container = IpmnGetProperties.getProperty("Batch");
		String _instence  = System.getProperty("env.servername");
		
		logger.info("_Container : " + _Container);
		logger.info("_instence  : " + _instence);
		
		/* 특정 컨테이너에서 수행되도록  설정 */
		if ( _Container.equals(_instence))
		{
		
		try {
		
//			System.out.println("==========  CRON HIOMS REST START  ==========");
			
			HiOmsService.getHiOmsJson();
			
//			System.out.println("==========  CRON HIOMS REST OK  ==========");
			
			} catch (Exception e) {

//				System.out.println("==========  CRON HIOMS REST ERROR  ==========");
				
				e.printStackTrace();
			}
		
		}
	}

}