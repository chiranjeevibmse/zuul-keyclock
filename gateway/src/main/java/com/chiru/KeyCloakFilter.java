package com.chiru;

import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

class KeycloakFilterRoute extends ZuulFilter {

private static final String AUTHORIZATION_HEADER = "authorization";

@Override
public String filterType() {
    return "route";
}

@Override
public int filterOrder() {
    return 0;
}

@Override
public boolean shouldFilter() {
    return true;
}

@Override
public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    if (ctx.getRequest().getHeader(AUTHORIZATION_HEADER) == null) {
        this.addKeycloakTokenToHeader(ctx);
    }
    return null;
}

private void addKeycloakTokenToHeader(RequestContext ctx) {
    RefreshableKeycloakSecurityContext securityContext = this.getRefreshableKeycloakSecurityContext(ctx);
    if (securityContext != null) {
        ctx.addZuulRequestHeader(AUTHORIZATION_HEADER, this.buildBearerToken(securityContext));
    }
}

private RefreshableKeycloakSecurityContext getRefreshableKeycloakSecurityContext(RequestContext ctx) {
    if (ctx.getRequest().getUserPrincipal() instanceof KeycloakAuthenticationToken) {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) ctx.getRequest().getUserPrincipal();
        return (RefreshableKeycloakSecurityContext) token.getCredentials();
    }
    return null;
}

private String buildBearerToken(RefreshableKeycloakSecurityContext securityContext) {
    return "Bearer " + securityContext.getTokenString();
}
}