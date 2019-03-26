package ipmn.rest.honey;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author 
 * @since 
 */
//@RestController
@Controller
public class HoneyController {
	@Autowired
	private HoneyService HoneyService;

//	@RequestMapping(value = "/rest/honey/{ip:.+}", method = RequestMethod.GET)
	@RequestMapping(value = "/rest/honey", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @SuppressWarnings("unchecked")
//	public String isRunning(@PathVariable("ip") String ip) {
	public String isRunning() {

	    HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
	    String tIp = req.getHeader("X-FORWARDED-FOR");
	    if (tIp == null)
	    	tIp = req.getRemoteAddr();
		
//		System.out.println("ip  :: " + "HoneyController :: "  + tIp );

		StringBuffer tTemp = new StringBuffer();

		try {
			String rip = "";
			String rsub = "";
			
      	    List<HoneyVO>  vo = new ArrayList<HoneyVO>(); 
      	  
			vo = HoneyService.HoneyIpList(tIp);

			for(int i=0; i<vo.size(); i++){
				HoneyVO Vo = vo.get(i) ;

				rip = Vo.getIp();
				rsub = Vo.getSubnetbit();

				tTemp.append(rip + "/" + rsub + "\n");
							
//				System.out.println("rip  :: " + rip);
//				System.out.println("rsub  :: " + rsub);

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