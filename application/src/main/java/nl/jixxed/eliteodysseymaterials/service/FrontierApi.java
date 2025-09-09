package nl.jixxed.eliteodysseymaterials.service;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.oauth.OAuth20Service;

import java.io.OutputStream;

public class FrontierApi extends DefaultApi20 {
    public FrontierApi() {
    }

    private static class InstanceHolder {
        private static final FrontierApi INSTANCE = new FrontierApi();
    }

    public static FrontierApi instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://auth.frontierstore.net/token";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://auth.frontierstore.net/auth";
    }
}