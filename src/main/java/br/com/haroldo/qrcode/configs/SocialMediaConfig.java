package br.com.haroldo.qrcode.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocialMediaConfig {

    private final Environment env;

    // Campos volateis para thread safety
    private volatile String baseUrlFacebook;
    private volatile String instagramUserId;
    private volatile String instagramAccessToken;

    public SocialMediaConfig(Environment env) {
        this.env = env;
        getBaseUrlFacebook();
        getInstagramUserId();
        getInstagramAccessToken();
    }

    // Getters com inicializacao lazy
    public String getBaseUrlFacebook() {
        if (baseUrlFacebook == null) {
            synchronized (this) {
                if (baseUrlFacebook == null) {
                    baseUrlFacebook = env.getProperty("app.base.url.facebook");
                    validateProperty(baseUrlFacebook, "app.base.url.facebook");
                }
            }
        }
        return baseUrlFacebook;
    }

    public String getInstagramUserId() {
        if (instagramUserId == null) {
            synchronized (this) {
                if (instagramUserId == null) {
                    instagramUserId = env.getProperty("app.instagram.user.id");
                    validateProperty(instagramUserId, "app.instagram.user.id");
                }
            }
        }
        return instagramUserId;
    }

    public String getInstagramAccessToken() {
        if (instagramAccessToken == null) {
            synchronized (this) {
                if (instagramAccessToken == null) {
                    instagramAccessToken = env.getProperty("app.instagram.access.token");
                    validateProperty(instagramAccessToken, "app.instagram.access.token");
                }
            }
        }
        return instagramAccessToken;
    }

    private void validateProperty(String value, String propertyName) {

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Property '" + propertyName + "' is required");
        }

        log.info("Propriedade " + propertyName + " carregada com sucesso com o valor: " + value);

    }

}