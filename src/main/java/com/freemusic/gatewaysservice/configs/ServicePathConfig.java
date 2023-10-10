package com.freemusic.gatewaysservice.configs;

import java.util.ArrayList;
import java.util.List;

public class ServicePathConfig {
    private List<String> publicPaths = new ArrayList<>();

    public List<String> getPublicPaths() {
        return publicPaths;
    }

    public void setPublicPaths(List<String> publicPaths) {
        this.publicPaths = publicPaths;
    }
}
