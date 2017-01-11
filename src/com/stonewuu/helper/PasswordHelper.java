package com.stonewuu.helper;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import com.stonewuu.entity.User;

/**
 * @ClassName: PasswordHelper
 * @Description: 密码帮助类
 * @author stonewuu 2017年1月11日 下午4:00:23
 *
 */
@Component
public class PasswordHelper {
	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private String algorithmName = "md5";//加密方式
	private final int hashIterations = 2;

	public void encryptPassword(User user) {
		user.setSalt(randomNumberGenerator.nextBytes().toHex());
		String newPassword = new SimpleHash(algorithmName, user.getPassword(),
				ByteSource.Util.bytes(user.getSalt()), hashIterations).toHex();
		user.setPassword(newPassword);
	}
}
