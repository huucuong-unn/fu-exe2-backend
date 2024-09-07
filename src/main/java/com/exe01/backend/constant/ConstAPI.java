package com.exe01.backend.constant;

public class ConstAPI {

    public static class UserAPI {
        public static final String CREATE_USER = "api/v1/user/create";
    }

    public static class BusinessAPI {
        public static final String GET_FEATURE_COMPANY = "api/v1/business/feature-company";
        public static final String CREATE_BUSINESS = "api/v1/business/create";
    }

    public static class RoleAPI {
        public static final String GET_ROLE = "api/v1/role";
        public static final String GET_ROLE_STATUS_TRUE = "api/v1/role/role-status-true";
        public static final String GET_ROLE_BY_ID = "api/v1/role/";
        public static final String CREATE_ROLE = "api/v1/role/create";
        public static final String UPDATE_ROLE = "api/v1/role/update/";
        public static final String DELETE_ROLE = "api/v1/role/delete/";
        public static final String CHANGE_STATUS_ROLE = "api/v1/role/change-status/";
    }

    public static class NotificationAPI
    {
        public static final String SEND_NOTIFITION = "api/v1/send-notification";
        public static final String NOTIFITION_TOKEN = "api/v1/notifcation-token";
    }

}
