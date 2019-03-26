package ipmn.batch.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ipmn.batch.service.CronTangoService;
import ipmn.batch.vo.CronCmnCdVO;
import ipmn.batch.vo.CronResultVO;
import ipmn.batch.vo.CronTangoIpBandCdVO;
import ipmn.batch.vo.CronTangoIpBandVO;
import ipmn.batch.vo.CronTangoIpInfoVO;
import ipmn.common.util.IpmnGetProperties;
import ipmn.web.util.WebUtil;

@Service("CronTangoService")
public  class CronTangoServiceImpl implements CronTangoService   {

    private static final Logger logger = LoggerFactory.getLogger(CronTangoServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
    public Connection doConnect(String driver, String url, String id, String passwd, Connection conn) throws Exception{
    	    	 
		logger.debug("======== Connection  start  =======");
		
		Class.forName(driver);
		conn = DriverManager.getConnection(url, id, passwd); 
		 		 
		return conn;
    }
    
    public void dbEndConnect(Connection conn) throws Exception{

		logger.debug("======== dbEndConnect  start  =======");
    	
        if(conn!=null){conn.close();}
    } 

    
    public void CronTangoResult(CronResultVO vo) throws Exception{
    	
		logger.debug("======== CronTangoBatchResult  start  =======");

    	sqlSession.insert("CronTango.CronTangoResult", vo);

    }

    
    public int CronTangoIpInfoCnt() throws Exception {
		logger.debug("======== CronTangoIpInfoCnt  start  =======");

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronTango.CronTangoIpInfoCnt");

		return tCnt;
	}

    public int CronTangoIpBandCnt() throws Exception {
		logger.debug("======== CronTangoIpBandCnt  start  =======");

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronTango.CronTangoIpBandCnt");

		return tCnt;
	}

    public int CronTangoIpBandCdCnt() throws Exception {
		logger.debug("======== CronTangoIpBandCdCnt  start  =======");

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronTango.CronTangoIpBandCdCnt");

		return tCnt;
	}

	    public void CronTangoIpInfoInsert(CronTangoIpInfoVO ipinfovo) throws Exception{
	    	
			logger.debug("======== CronTangoIpInfoInsert  start  =======");

			sqlSession.insert("CronTango.CronTangoIpInfoInsert", ipinfovo);
	    }
	    
	    public void CronTangoIpBandInsert(CronTangoIpBandVO ipbandvo) throws Exception{
	    	
			logger.debug("======== CronTangoIpBandInsert  start  =======");

			sqlSession.insert("CronTango.CronTangoIpBandInsert", ipbandvo);
			
	    }
	    
	    public void CronTangoIpBandCdInsert(CronTangoIpBandCdVO ipbandcdvo) throws Exception{
	    	
			logger.debug("======== CronTangoIpBandCdInsert  start  =======");

			sqlSession.insert("CronTango.CronTangoIpBandCdInsert", ipbandcdvo);
	    }
	    
    public void CronTangoIpInfoDelete() throws Exception{
    	
		logger.debug("======== CronTangoIpInfoDelete  start  =======");

		sqlSession.delete("CronTango.CronTangoIpInfoDelete");
    }

    public void CronTangoIpBandDelete() throws Exception{
    	
		logger.debug("======== CronTangoIpBandDelete  start  =======");

		sqlSession.delete("CronTango.CronTangoIpBandDelete");
    }

    public void CronTangoIpBandCdDelete() throws Exception{
    	
		logger.debug("======== CronTangoIpBandCdDelete  start  =======");

		sqlSession.delete("CronTango.CronTangoIpBandCdDelete");
    }

    @Override
	public void CronTangoIpBandCdInsert() {

//    	String beforday = WebUtil.getBeforDateString() + "/IM_IP_COM_CD_INF_" + WebUtil.getBeforDateString() + "*.DAT";
		String beforday = WebUtil.getBeforDateString();
		String chkFileNm = IpmnGetProperties.getProperty("TANGO.IpBandCd") + beforday + ".fin";
		String localNm = "";
    	
		File dir = new File(IpmnGetProperties.getProperty("TANGO.Local") + beforday); 

		File[] fileList = dir.listFiles(); 

		for(int i = 0 ; i < fileList.length ; i++){

			File file = fileList[i]; 

			if(file.isFile()){

   // 파일이 있다면 파일 이름 출력

				int lastIndex = file.getName().lastIndexOf(".");
				 
		        String fileName = file.getName().substring(0, lastIndex);
		        String fileName1 = file.getName().substring(0, lastIndex - 4);
		        String extension = file.getName().substring(lastIndex + 1);
		        String fileName2 = fileName1 + "." + extension ;
		 
		        if(chkFileNm.equals(fileName2)){
		        	localNm = fileName + ".DAT";
		        	
		        }
		        
//				logger.debug("\t 파일 이름 = " + file.getName());

			}else if(file.isDirectory()){

//				logger.debug("디렉토리 이름 = " + file.getName());

   // 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
//					subDirList(file.getCanonicalPath().toString()); 

			}

		}
		
		try{
            
        	//파일 객체 생성
            FileInputStream file = new FileInputStream(dir + "/" +localNm);

        	InputStreamReader fileReader = new InputStreamReader(file, "UTF-8");
        	
    		logger.debug("======== CronTangoDelete file read  start  =======");
            
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";
            while((line = bufReader.readLine()) != null){
            	
            	String[] arrayLine = line.split("\\|");

            	logger.debug(line);

            	if(arrayLine.length > 0){
	            	CronTangoIpBandCdVO ipbandcdvo = new  CronTangoIpBandCdVO();

	            	for(int i=0;i<arrayLine.length;i++) {
	            		ipbandcdvo.setComGrpCdId(arrayLine[0]);
	            		ipbandcdvo.setComGrpCdNm(arrayLine[1]);	
	            		ipbandcdvo.setComCd(arrayLine[2]);
	            		ipbandcdvo.setComCdNm(arrayLine[3]);	

	    				if(arrayLine[4].equals("")){
	    					ipbandcdvo.setFrstRegDate(null);
	    				} else {
	    					ipbandcdvo.setFrstRegDate(arrayLine[4]);
	    				}
	    				if(arrayLine[5].equals("")){
	    					ipbandcdvo.setLastChgDate(null);
	    				} else {
	    					ipbandcdvo.setLastChgDate(arrayLine[5]);
	    				}

/*	            		ipbandcdvo.setFrstRegDate(arrayLine[4]);	
	            		ipbandcdvo.setLastChgDate(arrayLine[5]);*/
	            		
					}
            		try {
	            		if(!ipbandcdvo.getComGrpCdId().equals("COM_GRP_CD_ID")){

	            			CronTangoIpBandCdInsert(ipbandcdvo);
	            		}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	                logger.debug(line);
				}
        }
            
            //.readLine()은 끝에 개행문자를 읽지 않는다.            
            bufReader.close();
            
        }catch (FileNotFoundException e) {
            // TODO: handle exception
	        }catch(IOException e){
//	        	System.out.println(e);
	        	e.printStackTrace();

        }		
	}

	public void CronTangoIpInfoInsert() {

//    	String beforday = WebUtil.getDateString8() + "0700.DAT";
		String beforday = WebUtil.getBeforDateString();
		String chkFileNm = IpmnGetProperties.getProperty("TANGO.IpInfo") + beforday + ".fin";
		String localNm = "";
    	
		File dir = new File(IpmnGetProperties.getProperty("TANGO.Local") + beforday); 

		File[] fileList = dir.listFiles(); 

		for(int i = 0 ; i < fileList.length ; i++){

			File file = fileList[i]; 

			if(file.isFile()){

   // 파일이 있다면 파일 이름 출력

				int lastIndex = file.getName().lastIndexOf(".");
				 
		        String fileName = file.getName().substring(0, lastIndex);
		        String fileName1 = file.getName().substring(0, lastIndex - 4);
		        String extension = file.getName().substring(lastIndex + 1);
		 
		        if(chkFileNm.equals(fileName1 + "." + extension)){
		        	localNm = fileName + ".DAT";
		        }
		        
//				logger.debug("\t 파일 이름 = " + file.getName());

			}else if(file.isDirectory()){

//				logger.debug("디렉토리 이름 = " + file.getName());

   // 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
//					subDirList(file.getCanonicalPath().toString()); 

			}

		}
		
		try{
            
        	//파일 객체 생성
            FileInputStream file = new FileInputStream(dir + "/" +localNm);

        	InputStreamReader fileReader = new InputStreamReader(file, "UTF-8");
            
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";
            while((line = bufReader.readLine()) != null){
            	
            	String[] arrayLine = line.split("\\|");

            	logger.debug(line);

            	if(arrayLine.length > 0){
	            	CronTangoIpInfoVO ipinfovo = new  CronTangoIpInfoVO();

	            	for(int i=0;i<arrayLine.length;i++) {

	    				ipinfovo.setEqpId(arrayLine[0]);	
	    				ipinfovo.setEqpNm(arrayLine[1]);
	    				ipinfovo.setEqpHostNm(arrayLine[2]);						  	
	    				ipinfovo.setIfNm(arrayLine[3]);							  
	    				ipinfovo.setIpAddr(arrayLine[4]);	
	    				ipinfovo.setMacAddr(arrayLine[5]);
	    				ipinfovo.setJrdtTeamOrgId(arrayLine[6]);
	    				ipinfovo.setJrdtTeamOrgNm(arrayLine[7]);	
	    				ipinfovo.setOpTeamOrgId(arrayLine[8]);	
	    				ipinfovo.setOpTeamOrgNm(arrayLine[9]);	
	    				ipinfovo.setOpUserId(arrayLine[10]);	
	    				ipinfovo.setOpUserNm(arrayLine[11]);	
	    				ipinfovo.setIfRmk(arrayLine[12]);	
	    				ipinfovo.setIfOpStatNm(arrayLine[13]);
	    				
	    				if(arrayLine[14].equals("")){
	    					ipinfovo.setFrstRegDate(null);
	    				} else {
	    					ipinfovo.setFrstRegDate(arrayLine[14]);
	    				}
	    				if(arrayLine[15].equals("")){
	    					ipinfovo.setLastChgDate(null);
	    				} else {
	    					ipinfovo.setLastChgDate(arrayLine[15]);
	    				}
					}
            		try {
            			if(!ipinfovo.getEqpId().equals("EQP_ID")){
            				CronTangoIpInfoInsert(ipinfovo);
            			}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
//	                logger.debug(line);
				}
        }
            
            //.readLine()은 끝에 개행문자를 읽지 않는다.            
            bufReader.close();
            
        }catch (FileNotFoundException e) {
            // TODO: handle exception
	        }catch(IOException e){
//	        	System.out.println(e);
	        	e.printStackTrace();
        }		
	}
	
	public void CronTangoIpBandInsert() {

//    	String beforday = WebUtil.getDateString8() + "0700.DAT";

		String beforday = WebUtil.getBeforDateString();
		String chkFileNm = IpmnGetProperties.getProperty("TANGO.IpBand") + beforday + ".fin";
		String localNm = "";
    	
		File dir = new File(IpmnGetProperties.getProperty("TANGO.Local") + beforday); 

		File[] fileList = dir.listFiles(); 

		for(int i = 0 ; i < fileList.length ; i++){

			File file = fileList[i]; 

			if(file.isFile()){

   // 파일이 있다면 파일 이름 출력

				int lastIndex = file.getName().lastIndexOf(".");
				 
		        String fileName = file.getName().substring(0, lastIndex);
		        String fileName1 = file.getName().substring(0, lastIndex - 4);
		        String extension = file.getName().substring(lastIndex + 1);
		 
		        if(chkFileNm.equals(fileName1 + "." + extension)){
		        	localNm = fileName + ".DAT";
		        }
		        
//				logger.debug("\t 파일 이름 = " + file.getName());

			}else if(file.isDirectory()){

//				logger.debug("디렉토리 이름 = " + file.getName());

   // 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
//					subDirList(file.getCanonicalPath().toString()); 

			}

		}
		
		try{
            
        	//파일 객체 생성
            FileInputStream file = new FileInputStream(dir + "/" +localNm);

        	InputStreamReader fileReader = new InputStreamReader(file, "UTF-8");
            
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(fileReader);
            
            String line = "";
            while((line = bufReader.readLine()) != null){
            	
            	String[] arrayLine = line.split("\\|");

            	logger.debug(line);

            	if(arrayLine.length > 0){
	            	CronTangoIpBandVO ipbandvo = new  CronTangoIpBandVO();

	            	for(int i=0;i<arrayLine.length;i++) {

	    				ipbandvo.setAsgnSeq(arrayLine[0]);	
	    				ipbandvo.setUprAsgnSeq(arrayLine[1]);
	    				ipbandvo.setIpDivCd(arrayLine[2]);	
	    				ipbandvo.setIpRscDivCd(arrayLine[3]);	
	    				ipbandvo.setIpSrvcDivCd(arrayLine[4]);	
	    				ipbandvo.setIpMtsoDivCd(arrayLine[5]);	
	    				ipbandvo.setIpSystmDivCd(arrayLine[6]);	
	    				ipbandvo.setIpAddr(arrayLine[7]);
	    				ipbandvo.setBitVal(arrayLine[8]);
	    				ipbandvo.setAsgnNm(arrayLine[9]);
	    				ipbandvo.setUsePurpCtt(arrayLine[10]);
	    				ipbandvo.setPoolUseYn(arrayLine[11]);
	    				ipbandvo.setRegTypCd(arrayLine[12]);
	    				ipbandvo.setBcastipAddr(arrayLine[13]);
	    				ipbandvo.setStaIpNo(arrayLine[14]);
	    				ipbandvo.setEndIpNo(arrayLine[15]);
	    				ipbandvo.setQuotaIpCnt(arrayLine[16]);
	    				ipbandvo.setAllIpCnt(arrayLine[17]);

	    				if(arrayLine[18].equals("")){
	    					ipbandvo.setFrstRegDate(null);
	    				} else {
	    					ipbandvo.setFrstRegDate(arrayLine[18]);
	    				}
	    				if(arrayLine[19].equals("")){
	    					ipbandvo.setLastChgDate(null);
	    				} else {
	    					ipbandvo.setLastChgDate(arrayLine[19]);
	    				}
	    				
/*	    				ipbandvo.setFrstRegDate(arrayLine[18]);
	    				ipbandvo.setLastChgDate(arrayLine[19]);*/
	    									
					}
            		try {
            			if(!ipbandvo.getAsgnSeq().equals("ASGN_SEQ")){
	            			CronTangoIpBandInsert(ipbandvo);
	
	            			CronCmnCdVO cmncdvo = new  CronCmnCdVO();
	
	            			cmncdvo.setCmnCd(ipbandvo.getIpRscDivCd()+ ipbandvo.getIpSrvcDivCd());	
	            			cmncdvo.setCmnCd2(ipbandvo.getIpRscDivCd());  //코드명 찾기	
	            			cmncdvo.setCmnCd3(ipbandvo.getIpSrvcDivCd()); //코드명 찾기
	
	            			logger.debug("setCmnCd :: " + ipbandvo.getIpRscDivCd()+ ipbandvo.getIpSrvcDivCd());
	
	            			if(cmncdvo.getCmnCd() != null && !cmncdvo.getCmnCd().equals(""))	{
	            					
	            				int tCnt = CronCmnCdCnt(cmncdvo);
	            				
	            				if(tCnt == 0){
	            					CronCmnCdInsert(cmncdvo);
	            				}
	
	            			}
            			}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
//	                System.out.println(line);
				}
            }
            
            //.readLine()은 끝에 개행문자를 읽지 않는다.            
            bufReader.close();
            
        }catch (FileNotFoundException e) {
            // TODO: handle exception
	        }catch(IOException e){
//	            System.out.println(e);
	            e.printStackTrace();
        }		
	}
	
	public void CronIpZoneCodeInsert() throws Exception {

		sqlSession.insert("CronTango.CronIpZoneCodeInsert");
		
	}

	public int CronCmnCdCnt(CronCmnCdVO vo) throws Exception {

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronTango.CronCmnCdCnt", vo);
		
		return tCnt;
	}

	public void CronCmnCdInsert(CronCmnCdVO vo) throws Exception {

		sqlSession.insert("CronTango.CronCmnCdInsert", vo);
		
	}

	@Override
	public int CronCmnCdCnt() throws Exception {

		int tCnt = 0;
		tCnt = sqlSession.selectOne("CronTango.CronCmnCdCnt");

		return tCnt;
	}

}
