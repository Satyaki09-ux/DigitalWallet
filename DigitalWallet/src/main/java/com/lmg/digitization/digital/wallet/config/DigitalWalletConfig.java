package com.lmg.digitization.digital.wallet.config;


import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.lmg.digitization.digital.wallet.properties.AppProperties;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
@EnableAsync
public class DigitalWalletConfig {


    @Autowired
    private AppProperties appProperties;


    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().description("List of all the APIs of Digital Wallet through Swagger UI")
                        .title("Digital-Wallet Rest API").license("LandmarkGroup").version("1.0.0").build())
                .select().apis(RequestHandlerSelectors.basePackage("com.lmg.digitization.digital.wallet.controller"))
                .paths(PathSelectors.any()).build();
    }


    @Bean
    @Profile("local")
    public RestTemplate restTemplateSSLdisable()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;


        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
                .build();


        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);


        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();


        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();


        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }


    @Bean
    @Profile("!local")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(8388608);
        return multipartResolver;
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(appProperties.getAppTimeZone()));
    }
}