package com.freemusic.gatewaysservice.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "public-services")
public class ServicePathProperties {
    private ServicePathConfig auth;
    private ServicePathConfig musicLibrary;
    private ServicePathConfig elasticsearchService;
    private ServicePathConfig historyService;

    public ServicePathConfig getAuth() {
        return auth;
    }

    public void setAuth(ServicePathConfig auth) {
        this.auth = auth;
    }

    public ServicePathConfig getMusicLibrary() {
        return musicLibrary;
    }

    public void setMusicLibrary(ServicePathConfig musicLibrary) {
        this.musicLibrary = musicLibrary;
    }

    public ServicePathConfig getElasticsearchService() {
        return elasticsearchService;
    }

    public void setElasticsearchService(ServicePathConfig elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }

    public ServicePathConfig getHistoryService() {
        return historyService;
    }

    public void setHistoryService(ServicePathConfig historyService) {
        this.historyService = historyService;
    }


}

