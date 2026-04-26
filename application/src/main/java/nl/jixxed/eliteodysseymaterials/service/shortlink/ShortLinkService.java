/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.shortlink;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.helper.DnsHelper;
import nl.jixxed.eliteodysseymaterials.service.Secrets;
import nl.jixxed.eliteodysseymaterials.service.VersionService;

import javax.naming.NamingException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Slf4j
public class ShortLinkService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String requestShortLink(String edomhUrl) {
        ShortLinkRequest shortLinkRequest = new ShortLinkRequest();
        shortLinkRequest.setUrl(edomhUrl);
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            final String data = OBJECT_MAPPER.writeValueAsString(shortLinkRequest);
            final String domainName = DnsHelper.resolveCname(Secrets.getOrDefault("shortlink.host", "localhost"));
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://" + domainName + "/api/links"))
                    .header("User-Agent", VersionService.getUserAgent())
                    .header("X-API-Key", Secrets.getOrDefault("shortlink.api.key", "none"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();
            final HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ShortLinkResponse shortLinkResponse = OBJECT_MAPPER.readValue(send.body(), ShortLinkResponse.class);
            return shortLinkResponse.getShortUrl();
        } catch (InterruptedException | NamingException | IOException e) {
            log.error("Error requesting short link for url {}", edomhUrl, e);
            return edomhUrl;
        }
    }
}
