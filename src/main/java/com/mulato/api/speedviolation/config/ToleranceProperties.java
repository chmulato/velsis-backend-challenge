package com.mulato.api.speedviolation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tolerance")
public class ToleranceProperties {

    private int fixed;
    private int percent;
    private int limitForPercent;

    public int getFixed() {
        return fixed;
    }

    public void setFixed(int fixed) {
        this.fixed = fixed;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getLimitForPercent() {
        return limitForPercent;
    }

    public void setLimitForPercent(int limitForPercent) {
        this.limitForPercent = limitForPercent;
    }
}
