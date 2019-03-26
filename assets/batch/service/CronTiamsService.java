package ipmn.batch.service;

import org.json.simple.JSONArray;

import ipmn.batch.vo.CronResultVO;

public interface CronTiamsService {

	/* Tiams  */
	void CronTiamsDelete() throws Exception;
	int getJSONLoopCount() throws Exception;
	JSONArray getTiamsJson(int cnt) throws Exception;
	void executeTiamsArrayInsert(JSONArray TiamsArray) throws Exception;
	int CronTiamsCnt() throws Exception;
	void CronTiamsResult(CronResultVO vo) throws Exception;
	
}
