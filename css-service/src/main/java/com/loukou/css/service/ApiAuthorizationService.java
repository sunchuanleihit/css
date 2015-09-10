package com.loukou.css.service;

import com.loukou.css.entity.ApiAuthorizationEntity;

public interface ApiAuthorizationService {
    public ApiAuthorizationEntity getApiAuthorizationByAppKey(String appKey);
}
