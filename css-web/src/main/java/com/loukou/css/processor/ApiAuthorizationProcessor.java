package com.loukou.css.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loukou.css.bo.CssBaseRes;
import com.loukou.css.bo.tools.MD5;
import com.loukou.css.entity.ApiAuthorizationEntity;
import com.loukou.css.service.ApiAuthorizationService;

@Service
public class ApiAuthorizationProcessor {

	@Autowired
	private ApiAuthorizationService apiAuthorizationService;
	
	public boolean VerificationApiAuthorization(String appKey,String verificationString,String timeStamp )
	{
		return VerificationApi(appKey,"",verificationString,timeStamp);
	}
	
	public boolean VerificationApiAuthorization(String appKey,String beforeEncryptionParam,String verificationString,String timeStamp )
	{
		return VerificationApi(appKey,beforeEncryptionParam,verificationString,timeStamp);
	}
	
	private boolean VerificationApi(String appKey,String beforeEncryptionParam,String verificationString,String timeStamp)
	{
		boolean isPass=false;
		ApiAuthorizationEntity apiAuthorizationEntity= apiAuthorizationService.getApiAuthorizationByAppKey(appKey);
		if(apiAuthorizationEntity!=null)
		{
			final String appKeyString="app_key=";
			final String callIdString="call_id=";
			String encryptionParam=beforeEncryptionParam+appKeyString+appKey+callIdString+timeStamp+apiAuthorizationEntity.getSecretKey();
		    String md5EncryptionParam=MD5.GetMD5Code(encryptionParam);
		    if(md5EncryptionParam.toLowerCase().equals(verificationString.toLowerCase()))
		    {
		    	isPass=true;
		    }
		}
		return isPass;
	}
}
