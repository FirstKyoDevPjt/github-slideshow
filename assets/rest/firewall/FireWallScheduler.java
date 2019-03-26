package ipmn.rest.firewall;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ipmn.common.util.IpmnGetProperties;

public class FireWallScheduler {

	private static final Logger logger = LoggerFactory.getLogger(FireWallScheduler.class);
	
	@Autowired
	private FireWallService FireWallService;
	
	@Scheduled(cron="0 35 15 * * *")
	public void FirewallRun() {
	
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		Connection conn= null;
		String ip = "10.10.10.25";
		
		String _Container = IpmnGetProperties.getProperty("Batch");
		String _instence  = System.getProperty("env.servername");
		
//		String _instence  = "Container1";

		
		logger.info("_Container : " + _Container);
		logger.info("_instence  : " + _instence);
		
		/* 특정 컨테이너에서 수행되도록  설정 */
		
		if ( _Container.equals(_instence))
		{
		
			try
			{

				logger.info("DriverClassName : " + IpmnGetProperties.getProperty("FIRE.DriverClassName"));
				logger.info("Url             : " + IpmnGetProperties.getProperty("FIRE.Url"));
				logger.info("UserName        : " + IpmnGetProperties.getProperty("FIRE.UserName"));
				logger.info("Password        : " + IpmnGetProperties.getProperty("FIRE.Password"));

				
				conn = FireWallService.doConnect(IpmnGetProperties.getProperty("FIRE.DriverClassName"),
													IpmnGetProperties.getProperty("FIRE.Url"),
													IpmnGetProperties.getProperty("FIRE.UserName"),
													IpmnGetProperties.getProperty("FIRE.Password"),
													conn);
				
				if (conn != null){
				
//					System.out.println("==========  CRON FIRE WALL BATCH START  ==========");

					//FireWallService.GetFireWallInfo(conn, ip);
					
					//자원 반납
					FireWallService.dbEndConnect(conn);
				
				}
			
			}
			catch (Exception  e) {
				e.printStackTrace();
			}
		
		}
	}
}