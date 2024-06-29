package com.bachar.apigw.security;

public interface ApiKeyAuthorizationChecker {
    boolean isAuthorized(String apiKey, String application);
}
