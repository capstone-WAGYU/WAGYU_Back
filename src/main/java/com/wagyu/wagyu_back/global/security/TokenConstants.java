package com.wagyu.wagyu_back.global.security;

public final class TokenConstants {
    private TokenConstants() {}

    public static final long ACCESS_TOKEN_VALIDITY = 1000L * 60 * 60;
    public static final long REFRESH_TOKEN_VALIDITY = 1000L * 60 * 60 * 24 * 7;
}