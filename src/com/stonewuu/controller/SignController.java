package com.stonewuu.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.stonewuu.entity.BDInfo;
import com.stonewuu.entity.User;
import com.stonewuu.helper.HttpHelper;
import com.stonewuu.helper.TieBaSignHelper;
import com.stonewuu.service.BDForumService;
import com.stonewuu.service.BDInfoService;
import com.stonewuu.service.UserService;

@Controller
@RequestMapping(value = "/sign/")
public class SignController extends BaseController {
	private static final Log log = LogFactory.getLog(SignController.class);
	
	@Resource
	private TieBaSignHelper tbHelper;
	@Resource
	private HttpHelper httpHelper;
	@Resource
	private UserService userService;
	@Resource
	private BDInfoService bdInfoService;
	@Resource
	private BDForumService bdForumService;

	@RequestMapping(value = { "","forumList" }, method = RequestMethod.GET)
	public ModelAndView forumList(HttpServletRequest request) {
		ModelAndView view = super.getModel(request);
		User currentUser = userService.findByUserName(super.getCurrentUserName());
		view.addObject("status", true);
		view.addObject("current", currentUser);
		List<BDInfo> bdinfo = currentUser.getBdInfo();
		if(bdinfo !=null && bdinfo.size()>0){
			view.addObject("bdInfo", currentUser.getBdInfo().get(0));
		}
		view.setViewName("/forumList");
		return view;
	}

	@RequestMapping(value = { "id_{id}" }, method = RequestMethod.GET)
	public ModelAndView bindInfo(@PathVariable("id") String id,HttpServletRequest request) {
		ModelAndView view = super.getModel(request);
		try {
			view.addObject("status", true);
			User currentUser = userService.findByUserName(super.getCurrentUserName());
			view.addObject("current", currentUser);
			List<BDInfo> bdinfo = currentUser.getBdInfo();
			BDInfo currentInfo = null;
			if(bdinfo !=null && bdinfo.size()>0){
				for(BDInfo info : bdinfo){
					if(info.getId().equals(Long.valueOf(id))){
						view.addObject("bdInfo", info);
						currentInfo = info;
						break;
					}
				}
			}
			if(currentInfo==null){
				view.addObject("status", false);
				view.addObject("msg", "未找到该用户哦，请不要做一些奇怪的操作~");
			}
		} catch (Exception e) {
			view.addObject("status", false);
			view.addObject("msg", "获取数据失败");
		}
		view.setViewName("/forumList");
		return view;
	}

	@RequestMapping(value = { "refreshForum" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<?, ?> refreshForum(HttpServletRequest request){
		ModelAndView view = super.getModel(request);
		Map<String, Object> map = view.getModel();
		String uid = request.getParameter("uid");
		if(StringUtils.isEmpty(uid)){
			map.put("status", false);
			map.put("msg", "刷新贴吧列表失败");
		}else{
			map.put("status", true);
			User currentUser = userService.findByUserName(super.getCurrentUserName());
			List<BDInfo> infos = currentUser.getBdInfo();
			BDInfo currentInfo = null;
			for(BDInfo info : infos){
				if(info.getUid().equals(uid)){
					currentInfo = info;
					break;
				}
			}
			if(currentInfo == null){
				map.put("status", false);
				map.put("msg", "该勿进行非法操作！");
			}else{
				try {
					boolean flag = bdForumService.refreshForumList(currentInfo);
					if(flag){
						map.put("status", true);
					}else{
						map.put("status", false);
						map.put("msg", "更新出现错误！");
					}
				} catch (Exception e) {
					map.put("status", false);
					map.put("msg", "更新出现错误！");
					log.error("更新用户："+currentUser.getName()+"的贴吧账号："+currentInfo.getBdName()+"时出现异常！",e);
				}
			}
		}
		return map;
	}

}
