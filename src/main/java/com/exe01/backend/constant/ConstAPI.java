package com.exe01.backend.constant;

public class ConstAPI {

    public static class UserAPI {
        public static final String CREATE_USER = "api/v1/user/create";
        public static final String LOGIN = "api/v1/user/login";
        public static final String LOGIN_GOOGLE = "api/v1/user/login-google";
        public static final String CHANGE_STATUS = "api/v1/user/status";
    }

    public static class InternshipProgram {
        public static final String CREATE_INTERNSHIP_PROGRAM = "api/v1/internship-program/create";
        public static final String TOP_3_INTERNSHIP_PROGRAM = "api/v1/internship-program/top_3";
        public static final String LIMIT_4_INTERNSHIP_PROGRAM = "api/v1/internship-program/limit_4";
        public static final String GET_INTERNSHIP_PROGRAMS_SEARCH_SORT = "api/v1/internship-program";
        public static final String GET_INTERNSHIP_PROGRAMS_DETAIL = "api/v1/internship-program/";
        public static final String GET_LAST_ACTIVITIES = "api/v1/internship-program/last-activities/";
        public static final String CHANGE_STATUS = "api/v1/internship-program/change-status/";
    }

    public static class BusinessAPI {
        public static final String GET_FEATURE_COMPANY = "api/v1/business/feature-company";
        public static final String CREATE_BUSINESS = "api/v1/business/create";
    }

    public static class SubscriptionAPI {
        public static final String CREATE_SUBSCRIPTION = "api/v1/subscription/create";
    }

    public static class UniStudentAPI {
        public static final String CREATE_UNI_STUDENT = "api/v1/uni-student/create";
    }

    public static class ApplicationAPI {
        public static final String CREATE_APPLICATION = "api/v1/application/create";
        public static final String CHANGE_STATUS = "api/v1/application/status";
    }

    public static class BlogAPI {
        public static final String CREATE_BLOG = "api/v1/blog/create";
        public static final String UPDATE_BLOG = "api/v1/blog/update/";
        public static final String GET_OUTSTANDING_BLOG = "api/v1/blog/outstanding";
        public static final String GET_BLOGS = "api/v1/blog";
    }

    public static class CozeAPI {
        public static final String CREATE_CHAT = "api/v1/coze/chat";
        public static final String UPLOAD_FILE = "api/v1/coze/file";

        public static final String CREATE_COVER_LETTER = "api/v1/coze/cover-letter";
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
