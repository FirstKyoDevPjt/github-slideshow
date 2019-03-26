package ipmn.rest.apisub;

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
public class ApiSubController {
	@Autowired
	private ApiSubService ApiSubService;

	@RequestMapping(value = "/rest/apisub/{ip:.+}", method = RequestMethod.GET)
//	@RequestMapping(value = "/rest/honey", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @SuppressWarnings("unchecked")
	public String isRunning(@PathVariable("ip") String ip) {
//	public String isRunning() {

//		System.out.println("ip  :: " + "ApiSubController :: "  + ip );

		StringBuffer tTemp = new StringBuffer();

		try {
			String rIp = "";
			String rWrkGubun = "";
			String rSubnetbit = "";
			String rNote = "";
			String rStartaddr = "";
			String rEndaddr = "";
			String rNetmask = "";
			String rBroadcast = "";
			String rLowaddress = "";
			String rHighaddress = "";
			String rZonecd = "";
			String rHoneyIpAddr = "";
			String rIpZoneRegistrant = "";
			
      	    List<ApiSubVO>  vo = new ArrayList<ApiSubVO>(); 
      	  
			vo = ApiSubService.ApiSubInfo(ip);

			for(int i=0; i<vo.size(); i++){
				ApiSubVO Vo = vo.get(i) ;

				rIp = Vo.getIp();
				rWrkGubun = Vo.getWrkGubun();
				rSubnetbit = Vo.getSubnetbit();
				rNote = Vo.getNote();
				rStartaddr = Vo.getStartaddr();
				rEndaddr = Vo.getEndaddr();
				rNetmask = Vo.getNetmask();
				rBroadcast = Vo.getBroadcast();
				rLowaddress = Vo.getLowaddress();
				rHighaddress = Vo.getHighaddress();
				rZonecd = Vo.getZonecd();
				rHoneyIpAddr = Vo.getHoneyIpAddr();
				rIpZoneRegistrant = Vo.getIpZoneRegistrant();

				tTemp.append(rIp + "/"
						+ rWrkGubun + "/"
						+ rSubnetbit + "/"
						+ rNote + "/"
						+ rStartaddr + "/"
						+ rEndaddr + "/"
						+ rNetmask + "/"
						+ rBroadcast + "/"
						+ rLowaddress + "/"
						+ rHighaddress + "/"
						+ rZonecd + "/"
						+ rHoneyIpAddr + "/"
						+ rIpZoneRegistrant + "\n");
				
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