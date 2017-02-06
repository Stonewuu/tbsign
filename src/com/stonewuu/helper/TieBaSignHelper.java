package com.stonewuu.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

@Component
public class TieBaSignHelper {
	@Resource
	private HttpHelper httpHelper;

	/**
	 * 
	 * @Title: getForumParam
	 * @Description: 获取用户信息需要的参数
	 * @author stonewuu 2017年1月11日 下午6:01:57
	 *
	 * @return
	 */
	public  Map<String,String> getProfileParam(String BDUSS){
		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		paramMap.put("bdusstoken", BDUSS);
//		paramMap.put("_client_id","wappc_1484940824312_108");
//		paramMap.put("_client_type","2");
//		paramMap.put("_client_version","6.2.2");
//		paramMap.put("_phone_imei","860046034358632");
//		paramMap.put("cuid","1BM12JF81ECDA24492D644A31231463B|236853430640068|com.baidu.tieba6.2.2");
//		paramMap.put("from","tieba");
//		paramMap.put("has_plist","1");
//		paramMap.put("model","Mix");
//		paramMap.put("need_post_count","1");
//		paramMap.put("pn","1");
//		paramMap.put("rn","20");
//		paramMap.put("stErrorNums","0");
//		paramMap.put("stMethod","1");
//		paramMap.put("stMode","1");
//		paramMap.put("stSize","5513");
//		paramMap.put("stTime","136");
//		paramMap.put("stTimesNum","0");
//		paramMap.put("st_type","null");
//		paramMap.put("timestamp","1484940941655");
//		paramMap.put("uid","590916732");
		return paramMap;
	}
	/**
	 * 
	 * @Title: getForumParam
	 * @Description: 获取关注的贴吧列表时需要的参数
	 * @author stonewuu 2017年1月11日 下午6:01:57
	 *
	 * @return
	 */
	public  Map<String,String> getForumParam(String BDUSS){
		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		paramMap.put("BDUSS", BDUSS);
		
		//非必要参数
		// paramMap.put("_client_id", "wappc_1482228876009_403");
		// paramMap.put("_client_type", "2");
		// paramMap.put("_client_version", "6.2.2");
		// paramMap.put("_phone_imei", "860046034358624");
		// paramMap.put("cuid",
		// "1BM12JF81ECDA24492D644A31231463B%7C236853430640068%7Ccom.baidu.tieba6.2.2");
		// paramMap.put("from", "tieba");
		// paramMap.put("like_forum", "1");
		// paramMap.put("model", "ONEPLUS+A3000");
		// paramMap.put("recommend", "0");
		// paramMap.put("stErrorNums", "0");
		// paramMap.put("stMethod", "1");
		// paramMap.put("stMode", "1");
		// paramMap.put("stSize", "239");
		// paramMap.put("stTime", "312");
		// paramMap.put("stTimesNum", "0");
		// paramMap.put("timestamp", "1482229148036");
		// paramMap.put("topic", "0");
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
	public Map<String,String> getSignMap(String fid,String kw,String BDUSS){
		//获取TBS
		String tbsResponse = httpHelper.sendPost("http://tieba.baidu.com/dc/common/tbs", "");
		JSONObject json = JSONObject.fromObject(tbsResponse);
		String tbs = json.get("tbs").toString();
		
		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		paramMap.put("BDUSS", BDUSS);
		paramMap.put("fid", fid);
		paramMap.put("kw", kw);
		paramMap.put("tbs", tbs);

		//非必要参数
		// paramMap.put("_client_id", "wappc_1482228876009_403");
		// paramMap.put("_client_type", "2");
		// paramMap.put("_client_version", "6.2.2");
		// paramMap.put("_phone_imei", "860046034358624");
		// paramMap.put("cuid",
		// "1BM12JF81ECDA24492D644A31231463B%7C236853430640068%7Ccom.baidu.tieba6.2.2");
		// paramMap.put("from", "tieba");
		// paramMap.put("model", "ONEPLUS+A3000");
		// paramMap.put("stErrorNums", "0");
		// paramMap.put("stMethod", "1");
		// paramMap.put("stMode", "1");
		// paramMap.put("stSize", "239");
		// paramMap.put("stTime", "312");
		// paramMap.put("stTimesNum", "0");
		// paramMap.put("timestamp", "1482229014867");
		
		return paramMap;
	}
	

	/**
	 * @Title: sign
	 * @Description: 根据参数生成sign值以及请求参数
	 * @author stonewuu 2017年1月11日 下午6:03:16
	 *
	 * @param paramMap
	 * @return
	 */
	public String sign(Map<String,String> paramMap){
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
	public String MD5(String source){
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
	 * 
	 * @Title: getTiebaForumList
	 * @Description: 获取贴吧关注的吧请求
	 * @author stonewuu 2017年1月21日 上午3:38:45
	 *
	 * @param param
	 * @param bduss
	 * @return
	 */
	public JSONObject getTiebaForumList(String param){
		String response = httpHelper.sendPost("http://c.tieba.baidu.com/c/f/forum/forumrecommend", param);
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
	 * @param bduss
	 * @return
	 */
	public JSONObject getTiebProfile(String param){
		String response = httpHelper.sendPost("http://c.tieba.baidu.com/c/s/login", param);
		JSONObject json = JSONObject.fromObject(response);
		return json;
	}
	
	/**
	 * 
	 * @Title: getTiebProfile
	 * @Description: 签到请求
	 * @author stonewuu 2017年1月21日 上午3:39:33
	 *
	 * @param param
	 * @param bduss
	 * @return
	 */
	public JSONObject doSignResult(String param){
		String response = httpHelper.sendPost("http://c.tieba.baidu.com/c/c/forum/sign", param);
		JSONObject json = JSONObject.fromObject(response);
		return json;
	}
}
