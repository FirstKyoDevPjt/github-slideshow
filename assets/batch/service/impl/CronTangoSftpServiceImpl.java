package ipmn.batch.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonSyntaxException;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import ipmn.batch.service.CronTangoSftpService;
import ipmn.batch.vo.CronResultVO;
import ipmn.common.util.IpmnGetProperties;

@Service("CronTangoSftpService")
public  class CronTangoSftpServiceImpl implements CronTangoSftpService   {

    private static final Logger logger = LoggerFactory.getLogger(CronTangoSftpServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;
	
    public void CronTangoResult(CronResultVO vo) throws Exception{
    	
		logger.debug("======== CronTangoBatchResult  start  =======");

    	sqlSession.insert("CronTango.CronTangoResult", vo);

    }

	public void getSftpConnect() throws JSchException, JsonSyntaxException, IOException {

		logger.debug(" =========    sftp start  =============");
		
		SimpleDateFormat day = new SimpleDateFormat ("yyyyMMdd");

		Calendar cal = Calendar.getInstance();

		String dDay = day.format(cal.getTime());
		
		InputStream in = null;
	    FileOutputStream out = null;

		logger.debug ( dDay );

		// 1. JSch 객체를 생성한다.
		JSch jsch = new JSch();
		
		// 2. 세션 객체를 생성한다 (사용자 이름, 접속할 호스트, 포트를 인자로 준다.)
		session = jsch.getSession(IpmnGetProperties.getProperty("TANGO.UserName"), IpmnGetProperties.getProperty("TANGO.Url1"));
		
		// 3. 패스워드를 설정한다.
		session.setPassword(IpmnGetProperties.getProperty("TANGO.Password"));
		
		// 4. 세션과 관련된 정보를 설정한다.
		java.util.Properties config = new java.util.Properties();
		
		// 4-1. 호스트 정보를 검사하지 않는다.
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		
		try{
		
			//5. 접속한다.      
			session.connect();
			
			// 6. sftp 채널을 연다.
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			
			StringBuffer sb = new StringBuffer();
			String ftpDir = IpmnGetProperties.getProperty("TANGO.Home") + dDay;
			
			logger.debug (" IpmnGetProperties ::  " + IpmnGetProperties.getProperty("TANGO.Home") );
			logger.debug (" getSftpConnect ::  " + ftpDir );
			
//			일자별 로컬 디렉토리 생성
			new File( IpmnGetProperties.getProperty("TANGO.Local") + dDay).mkdir();
			
			channelSftp.cd(dDay);


		    Vector<ChannelSftp.LsEntry> vector = channelSftp.ls("IM_IP*.*");
		    for (Iterator it = vector.iterator(); it.hasNext();) {
		    
		      ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) it.next();
		      
		      if(!lsEntry.getAttrs().isDir()){
		      
		        channelSftp.cd(ftpDir);
		        in = channelSftp.get( lsEntry.getFilename() );
		        
		        out = new FileOutputStream(new File( IpmnGetProperties.getProperty("TANGO.Local") + dDay + "/" + lsEntry.getFilename()));
		        
		        int i;
		          while ((i = in.read()) != -1) {
		            out.write(i);
		          }
		        
		        logger.info(dDay + "/" + lsEntry.getFilename());
		      }else{

		      }
		    }
	
		}catch(SftpException e){
			e.printStackTrace();
		}		
	}

}
