package com.stonewuu.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.stonewuu.entity.BDInfo;
import com.stonewuu.entity.User;
import com.stonewuu.helper.HttpHelper;
import com.stonewuu.helper.TieBaSignHelper;
import com.stonewuu.service.BDInfoService;
import com.stonewuu.service.UserService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/bindInfo/")
public class BindInfoController extends BaseController {
	private static final Log log = LogFactory.getLog(BindInfoController.class);
	
	@Resource
	private TieBaSignHelper tbHelper;
	@Resource
	private HttpHelper httpHelper;
	@Resource
	private UserService userService;
	@Resource
	private BDInfoService bdInfoService;
	
	@RequestMapping(value = { "","index" }, method = RequestMethod.GET)
	public ModelAndView bindInfo(HttpServletRequest request) {
		ModelAndView view = super.getModel(request);
		User currentUser = userService.findByUserName(super.getCurrentUserName());
		view.addObject("current", currentUser);
		view.setViewName("/bindInfo");
		return view;
	}

	@RequestMapping(value = { "submitBduss" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<?,?> submitBduss(HttpServletRequest request) {
		ModelAndView view = super.getModel(request);
		Map<String, Object> map = view.getModel();
		String bduss = request.getParameter("bduss");
		Map<String, String> param = tbHelper.getProfileParam(bduss);
		String url = tbHelper.sign(param);
		JSONObject json = tbHelper.getTiebProfile(url);
		String errCode = (String) json.get("error_code");
		if("0".equals(errCode)){
			//登录成功
			JSONObject user = json.getJSONObject("user");
			String uid = (String) user.get("id");
			String name = (String) user.get("name");
			BDInfo bdinfo = new BDInfo();
			bdinfo.setBduss(bduss);
			bdinfo.setBdName(name);
			bdinfo.setUid(uid);
			User currentUser = userService.findByUserName(super.getCurrentUserName());
			bdinfo.setUser(currentUser);
			bdinfo = bdInfoService.create(bdinfo);
			if(bdinfo==null){
				map.put("status", false);
				map.put("msg", "绑定失败，该bduss所属百度账号已被绑定！");
			}else{
				map.put("status", true);
			}
		}else{
			//登录失败
			map.put("status", false);
			map.put("msg", "绑定失败，该bduss无效！");
		}
		return map;
	}
	
	@RequestMapping(value = { "unbindBduss" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<?,?> unbindBduss(HttpServletRequest request) {
		String infoId = request.getParameter("info_id");
		ModelAndView view = super.getModel(request);
		Map<String, Object> map = view.getModel();
		try {
			bdInfoService.delete(Long.parseLong(infoId));
			map.put("status", true);
		} catch (Exception e) {
			log.info("用户："+super.getCurrentUserName()+"解绑出现异常");
			map.put("status", false);
			map.put("msg", "解绑失败");
			e.printStackTrace();
		}
		return map;
	}
}
