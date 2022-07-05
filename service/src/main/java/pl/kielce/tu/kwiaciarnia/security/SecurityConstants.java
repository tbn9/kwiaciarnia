package pl.kielce.tu.kwiaciarnia.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String ROLES = "roles";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
}