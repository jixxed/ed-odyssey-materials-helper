/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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

    @Override
    public OAuth20Service createService(final String apiKey, final String apiSecret, final String callback, final String defaultScope,
                                        final String responseType, final OutputStream debugStream, final String userAgent, final HttpClientConfig httpClientConfig,
                                        final HttpClient httpClient) {
        return new CapiOAuth20Service(this, apiKey, apiSecret, callback, defaultScope, responseType, debugStream, userAgent,
                httpClientConfig, httpClient);
    }
}