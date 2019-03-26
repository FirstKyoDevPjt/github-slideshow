package ipmn.rest.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author 
 * @since 
 */
//@RestController
@Controller
public class ApiController {
	@Autowired
	private ApiService ApiService;

	@RequestMapping(value = "/rest/api/{ip:.+}", method = RequestMethod.GET)
//	@RequestMapping(value = "/rest/honey", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @SuppressWarnings("unchecked")
	public String isRunning(@PathVariable("ip") String ip) {
//	public String isRunning() {

//		System.out.println("ip  :: " + "ApiController :: "  + ip );
		
		StringBuffer tTemp = new StringBuffer();

		try {
			String rApclStyle = "";
			String rIpAddr = "";
			String rWorkGubun = "";
			String rWorkSubGubun = "";
			String rDomain = "";
			String rHostNm = "";
			String rServiceNm = "";
			String rMac = "";
			String rMngId = "";
			String rMngNm = "";
			String rDeptCd = "";
			String rSubMngId = "";
			String rSubMngNm = "";
			String rSubDeptCd = "";
			String rApclId = "";
			String rAplcNm = "";
			String rIpAssignedYn = "";
			String rIpAssignedDate = "";
			String rIpReturnDate = "";
			String rIpUseDesc = "";
			String rIpRegistrant = "";
			String rIpRegisterDate = "";
			
      	    List<ApiVO>  vo = new ArrayList<ApiVO>(); 
      	  
			vo = ApiService.ApiInfo(ip);

			for(int i=0; i<vo.size(); i++){
				ApiVO Vo = vo.get(i) ;

				if(Vo.getApclStyle() != null) {
					rApclStyle = Vo.getApclStyle();
				}
				
				rIpAddr = Vo.getIpAddr();
				rWorkGubun = Vo.getWorkGubun();
				rWorkSubGubun = Vo.getWorkSubGubun();
				rDomain = Vo.getDomain();
				rHostNm = Vo.getHostNm();
				rServiceNm = Vo.getServiceNm();
				rMac = Vo.getMac();
				rMngId = Vo.getMngId();
				rMngNm = Vo.getSubMngId();
				rDeptCd = Vo.getDeptCd();
				rSubMngId = Vo.getSubMngId();
				rSubMngNm = Vo.getSubMngNm();
				rSubDeptCd = Vo.getSubDeptCd();
				rApclId = Vo.getApclId();
				rAplcNm = Vo.getAplcNm();
				rIpAssignedYn = Vo.getIpAssignedYn();
				rIpAssignedDate = Vo.getIpAssignedDate();
				rIpReturnDate = Vo.getIpReturnDate();
				rIpUseDesc = Vo.getIpUseDesc();
				rIpRegistrant = Vo.getIpRegistrant();
				rIpRegisterDate = Vo.getIpRegisterDate();

				tTemp.append(rApclStyle + "/"	+ rIpAddr + "/"	+ rWorkGubun + "/"	+ rWorkSubGubun + "/"	+ rDomain + "/"	+ rHostNm + "/"	+ rServiceNm + "/"	+ rMac + "/"	+ rMngId + "/"	+ rMngNm + "/"	+ rDeptCd + "/"	+ rSubMngId + "/"	+ rSubMngNm + "/"	+ rSubDeptCd + "/"	+ rApclId + "/"	+ rAplcNm + "/"	+ rIpAssignedYn + "/"	+ rIpAssignedDate + "/"	+ rIpReturnDate + "/"	+ rIpUseDesc + "/"	+ rIpRegistrant + "/"	+ rIpRegisterDate + "\n");

//				System.out.println("rip  :: " + rIpAddr);
			 }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			System.out.println("ip  :: " + "error");
			e.printStackTrace();
		}
    	
//		System.out.println("tTemp  :: " + tTemp);
//        return "I'm Alive!";
        return tTemp.toString();
    }

}