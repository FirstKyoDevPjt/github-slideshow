package ipmn.batch.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import ipmn.batch.service.CronStuserService;
import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronStuserVO;
import ipmn.common.util.IpmnGetProperties;

public class CronStuserScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CronStuserScheduler.class);
	
	@Autowired
	private CronStuserService CronStuserService;

	@Scheduled(cron="0 10 06 * * *")
	public void CronStuserRun() {
	    
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
				logger.info("==========  CRON STUSER BATCH START  ==========");
				
				List<CronStuserVO> list = new ArrayList<CronStuserVO>();
				
				list = CronStuserService.CronStuserSelectList();
				
//				List<CronStuserVO> list = CronStuserService.CronStuserSelectList();
				
				for(int i=0; i<list.size(); i++){
					
					CronStuserVO vo = list.get(i);

					if(vo.getT_flag().equals("D")){

						vo.setEmpno(vo.getEmpno());
						CronStuserService.CronStuserDelete(vo);
						
					} else if(vo.getT_flag().equals("U")){
						
						vo.setEmpno(vo.getEmpno());
						vo.setTelno(vo.getMoveTelNo());
						vo.setEmail(vo.getEmail());
						
						CronStuserService.CronStuserUpdate(vo);
					
					} else {
						continue;
					}
				}
				
				resultvo.setR_cnt(list.size());
				resultvo.setR_cd("18");
				resultvo.setR_result("CRON STUSER BATCH OK");
				
				CronStuserService.CronIpDtlInfoResult(resultvo);
				
				logger.info("==========  CRON STUSER BATCH OK  ==========");
				
			} catch (Exception  e) {
				
				resultvo.setR_cnt(0);
				resultvo.setR_cd("18");
				resultvo.setR_result("CRON STUSER BATCH ERROR");
				
				try 
				{
				
					CronStuserService.CronIpDtlInfoResult(resultvo);
					logger.info("==========  CRON STUSER BATCH ERROR  ==========");
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				e.printStackTrace();
			}
		
		}
		
	}
}