package com.stonewuu.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.stonewuu.entity.BDForum;
import com.stonewuu.entity.BDInfo;
import com.stonewuu.service.BDForumService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class TieBaSignHelper {
	private static final Log log = LogFactory.getLog(TieBaSignHelper.class);
	
	@Resource
	private HttpHelper httpHelper;
	@Resource
	private BDForumService forumService;

	/**
	 * 
	 * @Title: getForumParam
	 * @Description: 获取用户信息需要的参数
	 * @author stonewuu 2017年1月11日 下午6:01:57
	 *
	 * @return
	 */
	public Map<String, String> getProfileParam(String BDUSS) {
		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		paramMap.put("bdusstoken", BDUSS);
		return paramMap;
	}

	/**
	 *  获取关注的贴吧列表时需要的参数
	 * @Title: getForumHLevelParam
	 * @Description: 获取关注的贴吧列表时需要的参数
	 * @author stonewuu 2017年1月11日 下午6:01:57
	 *
	 * @return
	 */
	public Map<String, String> getForumParam(String BDUSS, String page_no, String page_size) {
		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		paramMap.put("BDUSS", BDUSS);
		paramMap.put("page_no", page_no);
		paramMap.put("page_size", page_size);

		return paramMap;
	}
	/**
	 *  获取关注的大于七级贴吧列表时需要的参数
	 * @Title: getForumHLevelParam
	 * @Description: 获取关注的大于七级贴吧列表时需要的参数
	 * @author stonewuu 2017年1月11日 下午6:01:57
	 *
	 * @return
	 */
	public Map<String, String> getForumHLevelParam(String BDUSS, String user_id) {
		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		paramMap.put("BDUSS", BDUSS);
		paramMap.put("user_id", user_id);

		return paramMap;
	}

	/**
	 * 
	 * @Title: getSignMap
	 * @Description: 获取签到时需要的参数
	 * @author stonewuu 2017年1月11日 下午6:01:22
	 *
	 * @param fid
	 * @param kw
	 * @param BDUSS
	 * @return
	 */
	public Map<String, String> getSignMap(String fid, String kw, String BDUSS) {
		String tbs = getTbs(BDUSS);
		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		paramMap.put("BDUSS", BDUSS);
		paramMap.put("fid", fid);
		paramMap.put("kw", kw);
		paramMap.put("tbs", tbs);

		return paramMap;
	}

	/**
	 * 
	 * @Title: getBetchSignMap
	 * @Description: 获取批量签到贴吧的参数
	 * @author stonewuu 2017年2月23日 下午9:36:41
	 *
	 * @param forum_ids
	 * @param kw
	 * @param BDUSS
	 * @param user_id
	 * @return
	 */
	public Map<String, String> getBetchSignMap(String forum_ids, String BDUSS, String user_id) {
		// 获取TBS
		String tbs = getTbs(BDUSS);
		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		paramMap.put("BDUSS", BDUSS);
		paramMap.put("forum_ids", forum_ids);
		paramMap.put("tbs", tbs);
		paramMap.put("user_id", user_id);

		return paramMap;
	}

	/**
	 * 获取tbs
	 * @Title: getTbs
	 * @Description: 获取tbs
	 * @author stonewuu 2017年3月4日 下午6:03:27
	 *
	 * @param BDUSS
	 * @return
	 */
	public String getTbs(String BDUSS) {
		String tbsResponse = httpHelper.sendPost("http://tieba.baidu.com/dc/common/tbs", "BDUSS="+BDUSS,"BDUSS="+BDUSS);
		JSONObject json = JSONObject.fromObject(tbsResponse);
		String tbs = json.get("tbs").toString();
		return tbs;
	}

	/**
	 * @Title: sign
	 * @Description: 根据参数生成sign值以及请求参数
	 * @author stonewuu 2017年1月11日 下午6:03:16
	 *
	 * @param paramMap
	 * @return
	 */
	public String sign(Map<String, String> paramMap) {
		StringBuffer requestStr = new StringBuffer();
		StringBuffer signStr = new StringBuffer();
		Set<String> keySet = paramMap.keySet();
		for (String key : keySet) {
			String value = paramMap.get(key);
			try {
				requestStr.append(key).append("=").append(value).append("&");
				signStr.append(key).append("=").append(URLDecoder.decode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
			}
		}
		signStr.append("tiebaclient!!!");
		String signResult = MD5(signStr.toString());
		requestStr.append("sign=" + signResult);
		return requestStr.toString();
	}

	/**
	 * 
	 * @Title: MD5
	 * @Description: 进行MD5加密
	 * @author stonewuu 2017年1月11日 下午6:03:01
	 *
	 * @param source
	 * @return
	 */
	public String MD5(String source) {
		String result = "";
		try {
			MessageDigest md5;
			md5 = MessageDigest.getInstance("MD5");
			md5.update(source.toString().getBytes());
			byte b[] = md5.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return result;
	}

	/**
	 * 获取贴吧关注的大于七级的吧请求
	 * @Title: getTiebaHLevelForumList
	 * @Description: 获取贴吧关注的大于七级的吧请求
	 * @author stonewuu 2017年1月21日 上午3:38:45
	 *
	 * @param param
	 * @return
	 */
	public JSONObject getTiebaHLevelForumList(String param) {
		String response = httpHelper.sendPost("http://c.tieba.baidu.com/c/f/forum/getforumlist", param);
		JSONObject json = JSONObject.fromObject(response);
		return json;
	}
	
	/**
	 * 获取贴吧关注的所有吧请求
	 * @Title: getTiebaAllForumList
	 * @Description: 获取贴吧关注的所有吧请求
	 * @author stonewuu 2017年3月4日 下午6:01:03
	 *
	 * @param param
	 * @return
	 */
	public JSONObject getTiebaAllForumList(String param) {
		String response = httpHelper.sendPost("http://c.tieba.baidu.com/c/f/forum/like", param);
		JSONObject json = JSONObject.fromObject(response);
		return json;
	}

	/**
	 * 
	 * @Title: getTiebProfile
	 * @Description: 获取用户信息请求
	 * @author stonewuu 2017年1月21日 上午3:38:58
	 *
	 * @param param
	 * @return
	 */
	public JSONObject getTiebProfile(String param) {
		String response = httpHelper.sendPost("http://c.tieba.baidu.com/c/s/login", param);
		JSONObject json = JSONObject.fromObject(response);
		return json;
	}

	/**
	 * 
	 * @Title: getTiebProfile
	 * @Description: 签到单个吧
	 * @author stonewuu 2017年1月21日 上午3:39:33
	 *
	 * @param param
	 * @param bduss
	 * @return
	 */
	public JSONObject doSignResult(String param) {
		String response = httpHelper.sendPost("http://c.tieba.baidu.com/c/c/forum/sign", param);
		JSONObject json = JSONObject.fromObject(response);
		return json;
	}

	/**
	 * 批量签到7级(包含)以上的吧
	 * 
	 * @Title: doBetchSignResult
	 * @Description: TODO<尽量简短描述其作用>
	 * @author stonewuu 2017年2月23日 下午9:30:03
	 *
	 * @param param
	 * @return
	 */
	public JSONObject doBetchSignResult(String param) {
		String response = httpHelper.sendPost("http://c.tieba.baidu.com/c/c/forum/msign", param);
		JSONObject json = JSONObject.fromObject(response);
		return json;
	}

	/**
	 * 校验一键签到结果
	 * 
	 * @Title: checkBetchSignResult
	 * @Description: 校验一键签到结果
	 * @author stonewuu 2017年2月23日 下午9:47:44
	 *
	 * @param json
	 */
	public boolean checkBetchSignResult(JSONObject json, BDInfo bdinfo) {
		String error_code = (String) json.get("error_code");
		if ("0".equals(error_code)) {
			JSONObject error = json.getJSONObject("error");
			//签到成功或者已签到
			if ("0".equals(error.get("err_no"))) {
				// 签到成功
				JSONArray infoArray = json.getJSONArray("info");
				for (int i = 0; i < infoArray.size(); i++) {
					JSONObject info = infoArray.getJSONObject(i);
					String forum_id = (String) info.get("forum_id");
					//本次签到增加的经验
					Long cur_score = Long.valueOf((String) info.get("cur_score"));
					BDForum forum = forumService.findByBdidAndFid(bdinfo.getId().toString(), forum_id);
					forum.setSigned(true);
					//增加经验
					forum.setExp(forum.getExp() + cur_score);
					forumService.update(forum);
				}
				log.info("用户 \""+bdinfo.getUser().getName()+"\" 的百度账号 \""+bdinfo.getBdName()+"\" 一键签到成功！");
				return true;
			}else{
				//已签到
				if("340011".equals(error.get("errno").toString())){
					log.info("用户 \""+bdinfo.getUser().getName()+"\" 的百度账号 \""+bdinfo.getBdName()+"\" 已经一键签到过！");
				}
			}
		} else {
			// 签到失败 TODO
		}
		return false;
	}

	/**
	 * 校验单独签到结果
	 * @Title: checkSignResult
	 * @Description: 校验单独签到结果
	 * @author stonewuu 2017年2月23日 下午10:32:55
	 *
	 * @param json 需要校验的json
	 * @param bdid 百度用户ID
	 * @param fid 贴吧ID
	 */
	public boolean checkSignResult(JSONObject json, BDInfo bdinfo, String fid) {
		String error_code = (String) json.get("error_code");
		if ("0".equals(error_code)) {
			// 签到成功
			JSONObject info = json.getJSONObject("user_info");
			if(!info.isNullObject()){
				BDForum forum = forumService.findByBdidAndFid(bdinfo.getId().toString(), fid);
				//本次签到增加的经验
				Long plusExp = Long.valueOf(info.get("sign_bonus_point").toString());
				//升级所需经验
				Long levelup_score = Long.valueOf(info.get("levelup_score").toString());
				forum.setSigned(true);
				forum.setExp(forum.getExp() + plusExp);
				if (forum.getExp() > levelup_score) {
					// 如果经验满了就升级
					forum.setLevel(forum.getLevel() + 1);
				}
				forumService.update(forum);
				log.info("用户 \""+bdinfo.getUser().getName()+"\" 的百度账号 \""+bdinfo.getBdName()+"\" 签到贴吧 \""+forum.getForumName()+"\"成功");
				return true;
			}else{
				JSONObject error = json.getJSONObject("error");
				log.error(error);
			}
		} else {
			// 签到失败 TODO
			if("160002".equals(error_code)){
				//已经签到过了
				BDForum forum = forumService.findByBdidAndFid(bdinfo.getId().toString(), fid);
				forum.setSigned(true);
				forumService.update(forum);
				log.info("用户 \""+bdinfo.getUser().getName()+"\" 的百度账号 \""+bdinfo.getBdName()+"\" 已签到过贴吧 \""+forum.getForumName()+"\"");
				return true;
			}
		}
		return false;
	}
}
