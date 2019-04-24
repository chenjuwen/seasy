package com.seasy.core.encryptionpolicy;

import org.apache.commons.lang.StringUtils;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.seasy.core.SeasyConstants;
import com.seasy.core.util.PropertiesUtil;

public class DefaultEncryptionPolicy implements EncryptionPolicy {
	@Override
	public EncryptionResult encrypt(String password) {
		if(StringUtils.isEmpty(password)){
			password = PropertiesUtil.getInstance().getProperty(SeasyConstants.RESET_PASSWORD_VALUE, "123456");
		}
		
		byte[] salt = Digests.generateSalt(SeasyConstants.DIGESTS_SALT_SIZE);
		String saltValue = Encodes.encodeHex(salt);
		
		return encrypt(password, saltValue);
	}

	@Override
	public EncryptionResult encrypt(String password, String saltValue) {
		byte[] salt = Encodes.decodeHex(saltValue);
		byte[] passwordByte = Digests.sha1(password.getBytes(), salt, SeasyConstants.DIGESTS_SHA1_ITERATIONS);
		String passwordStr = Encodes.encodeHex(passwordByte);
		
		EncryptionResult result = new EncryptionResult(saltValue, passwordStr);
		
		return result;
	}

}
