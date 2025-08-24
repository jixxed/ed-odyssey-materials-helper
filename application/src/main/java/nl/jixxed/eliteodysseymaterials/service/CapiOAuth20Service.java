package nl.jixxed.eliteodysseymaterials.service;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.oauth.AuthorizationUrlBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.pkce.PKCE;

import java.io.OutputStream;
import java.util.Random;

public class CapiOAuth20Service extends OAuth20Service {
    public static final String CLIENT_ID = Secrets.getOrDefault("ccid", "ccid");
    private final Random random = new Random();
    private final String secretState;
    private final AuthorizationUrlBuilder authorizationUrlBuilder;

    CapiOAuth20Service(final DefaultApi20 api, final String apiKey, final String apiSecret, final String callback, final String defaultScope, final String responseType, final OutputStream debugStream, final String userAgent, final HttpClientConfig httpClientConfig, final HttpClient httpClient) {
        super(api, apiKey, apiSecret, callback, defaultScope, responseType, debugStream, userAgent, httpClientConfig, httpClient);
        this.secretState = "secret" + this.random.nextInt(999_999);
        this.authorizationUrlBuilder = createAuthorizationUrlBuilder()
                .state(this.secretState)
                .initPKCE();
    }

    public AuthorizationUrlBuilder getAuthorizationUrlBuilder() {
        return this.authorizationUrlBuilder;
    }

    @Override
    protected OAuthRequest createRefreshTokenRequest(final String refreshToken, final String scope) {
        final OAuthRequest refreshTokenRequest = super.createRefreshTokenRequest(refreshToken, scope);
        refreshTokenRequest.addParameter(OAuthConstants.CLIENT_ID, CLIENT_ID);
        refreshTokenRequest.addParameter(OAuthConstants.STATE, this.secretState);
        final String pkceCodeVerifier = this.authorizationUrlBuilder.getPkce().getCodeVerifier();
        if (pkceCodeVerifier != null) {
            refreshTokenRequest.addParameter(PKCE.PKCE_CODE_VERIFIER_PARAM, pkceCodeVerifier);
        }
        return refreshTokenRequest;
    }
}
