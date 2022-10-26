package com.rserenity.hrproject.security.auth;

public enum ApplicationUserPermission {
    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_WRITE("employee:write"),
    DAYOFF_READ("dayoff:read"),
    DAYOFF_WRITE("dayoff:write");

    private final String permission;

    ApplicationUserPermission(String permission){
        this.permission = permission;
    }


    public String getPermission() {
        return permission;
    }
}
