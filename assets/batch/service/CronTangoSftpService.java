package ipmn.batch.service;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;
import com.jcraft.jsch.JSchException;

import ipmn.batch.vo.CronResultVO;

public interface CronTangoSftpService {

	void getSftpConnect() throws JSchException, JsonSyntaxException, IOException;
	void CronTangoResult(CronResultVO vo) throws Exception;

}