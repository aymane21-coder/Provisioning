package org.app.provisioning360api.Security;


public interface SecurityParams {
    public static final String JWT_HEADER_NAME="Authorization";
    public static final String SECRET="ben@aymane.net";
    public static final long EXPIRATION=10*24*3600;//10 jours
    public static final String HEADER_PREFIX="Bearer ";
}
