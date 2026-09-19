package org.ai.common.constants;

public final class SecurityConstants {

    private SecurityConstants() {}

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final long ACCESS_TOKEN_EXPIRATION =
            1000L * 60 * 60 * 24; // 24 hours
}
