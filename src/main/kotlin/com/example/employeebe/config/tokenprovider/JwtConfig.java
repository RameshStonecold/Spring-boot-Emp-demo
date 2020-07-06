package com.example.employeebe.config.tokenprovider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class JwtConfig {
    @Value("${security.jwt.uri:/auth/**}")
    private String Uri = "/auth/**";

    @Value("${security.jwt.header:Authorization}")
    private String header = "Authorization";

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix = "Bearer";

    @Value("${security.jwt.expiration:#{24*60*60}}")
    private int expiration = 24*60*60;

    @Value("${security.jwt.secret:JwtSecretKey}")
    private String secret = "JwtSecretKey";

    //getters only
	public String getUri() {
		return Uri;
	}

	public String getHeader() {
		return header;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getExpiration() {
		return expiration;
	}

	public String getSecret() {
		return secret;
	}
}
