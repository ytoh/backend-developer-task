package cz.databreakers.example.developertask.domain.nearby;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

/**
 * @author hvizdos
 */
@Configuration
public class HereApiConfiguration {

    /**
     * @return a configured rest template that trusts 'places.cit.api.here.com'
     */
    @Bean
    public RestTemplate restTemplate(@Value("${here-api.trust-store}") Resource trustStore,
                                     @Value("${here-api.trust-store-password}") char[] trustStorePassword,
                                     @Value("${here-api.trust-store-type}") String trustStoreType) throws Exception {
        KeyStore truststore = KeyStore.getInstance(trustStoreType);
        truststore.load(trustStore.getInputStream(), trustStorePassword);

        SSLContext context = new SSLContextBuilder()
                .loadTrustMaterial(truststore, null)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(new SSLConnectionSocketFactory(context))
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);
    }
}
