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

import com.stonewuu.entity.BDForum;
import com.stonewuu.entity.BDInfo;
import com.stonewuu.entity.User;
import com.stonewuu.helper.HttpHelper;
import com.stonewuu.helper.TieBaSignHelper;
import com.stonewuu.service.BDForumService;
import com.stonewuu.service.BDInfoService;
import com.stonewuu.service.UserService;
import com.stonewuu.task.SignUserTask;

import net.sf.json.JSONObject;

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
					Map<String, Object> result = bdForumService.refreshForumList(currentInfo,true);
					if((boolean) result.get("status")){
						map.put("status", true);
					}else{
						map.put("status", false);
						map.put("msg", result.get("msg"));
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

	/**
	 * 测试单独签到
	 * @Title: signSingleForum
	 * @Description: 测试单独签到
	 * @author stonewuu 2017年2月23日 下午11:27:43
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "signSingleForum" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<?, ?> signSingleForum(HttpServletRequest request){
		ModelAndView view = super.getModel(request);
		Map<String, Object> map = view.getModel();
		//贴吧用户ID
		String uid = request.getParameter("uid");
		//贴吧ID
		String forum_id = request.getParameter("forum_id");
		
		if(StringUtils.isEmpty(uid)||StringUtils.isEmpty(forum_id)){
			map.put("status", false);
			map.put("msg", "签到失败，缺少关键数据！");
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
				map.put("msg", "请不要做一些奇怪的操作~");
			}else{
				BDForum bdForum = bdForumService.findByBdidAndFid(currentInfo.getId().toString(), forum_id);
				if(bdForum==null){
					map.put("status", false);
					map.put("msg", "未找到需要签到的贴吧，请不要做一些奇怪的操作~");
				}
				//获取签到参数
				Map<String, String> paramMap = tbHelper.getSignMap(bdForum.getForumId(), bdForum.getForumKeyWord(), currentInfo.getBduss());
				//加密签到参数
				String url = tbHelper.sign(paramMap);
				//执行签到
				JSONObject json = tbHelper.doSignResult(url);
				//校验签到结果
				boolean flag = tbHelper.checkSignResult(json,currentInfo.getId().toString(), bdForum.getForumId());
				map.put("status", flag);
				map.put("msg", flag?"签到成功！":"签到失败！");
			}
			
		}
		return map;
	}
	
	@RequestMapping(value = { "signAllForum" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<?, ?> signAllForum(HttpServletRequest request){
		ModelAndView view = super.getModel(request);
		Map<String, Object> map = view.getModel();
		//贴吧用户ID
		String uid = request.getParameter("uid");
		
		if(StringUtils.isEmpty(uid)){
			map.put("status", false);
			map.put("msg", "签到失败，缺少关键数据！");
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
				map.put("msg", "请不要做一些奇怪的操作~");
			}else{
				SignUserTask task = new SignUserTask(currentInfo, bdForumService, tbHelper);
				new Thread(task).start();
				map.put("status", true);
				map.put("msg", "正在签到中，请稍候回来查看哦~");
			}
			
		}
		return map;
	}

}
