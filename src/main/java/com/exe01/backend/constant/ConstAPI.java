package com.exe01.backend.constant;

public class ConstAPI {

    public static class CozeAPI {
        public static final String CREATE_CHAT = "api/v1/coze/chat";
        public static final String UPLOAD_FILE = "api/v1/coze/file";
    }
    public static class AccountAPI {
        public static final String GET_ACCOUNT = "api/v1/account";
        public static final String GET_ACCOUNT_STATUS_TRUE = "api/v1/account/account-status-true";
        public static final String GET_ACCOUNT_BY_ID = "api/v1/account/";
        public static final String CREATE_ACCOUNT = "api/v1/account/create";
        public static final String CREATE_ACCOUNT_ADMIN = "api/v1/account/create-admin";
        public static final String CREATE_ACCOUNT_COMPANY = "api/v1/account/create-company";
        public static final String CREATE_ACCOUNT_MENTOR = "api/v1/account/create-mentor";
        public static final String UPDATE_ACCOUNT = "api/v1/account/update/";
        public static final String DELETE_ACCOUNT = "api/v1/account/delete/";
        public static final String CHANGE_STATUS_ACCOUNT = "api/v1/account/change-status/";
        public static final String UPLOAD_IMAGE_ACCOUNT = "api/v1/account/upload-image/";
        public static final String DOWNLOAD_IMAGE_ACCOUNT = "api/v1/account/download-image/";
        public static final String GET_ACCOUNT_MENTEE_INFO = "api/v1/account/account-mentee-info/";

        public static final String GET_ACCOUNT_FOR_ADMIN = "api/v1/account/account-for-admin";

        public static final String APPROVE_ACCOUNT = "api/v1/account/approve/";

        public static final String REJECT_ACCOUNT = "api/v1/account/reject/";

        public static final String GET_POINT = "api/v1/account/point/";

    }

    public static class AuthenticationAPI {
        public static final String LOGIN_WITH_PASSWORD_USERNAME = "api/v1/auth/login";
        public static final String LOGIN_WITH_GOOGLE = "api/v1/auth/login-google";
    }

    public static class StudentAPI {
        public static final String GET_STUDENT = "api/v1/student";
        public static final String GET_STUDENT_STATUS_TRUE = "api/v1/student/student-status-true";
        public static final String GET_STUDENT_BY_ID = "api/v1/student/";
        public static final String CREATE_STUDENT = "api/v1/student/create";
        public static final String UPDATE_STUDENT = "api/v1/student/update/";
        public static final String DELETE_STUDENT = "api/v1/student/delete/";
        public static final String CHANGE_STATUS_STUDENT = "api/v1/student/change-status/";
    }
    public static class CompanyAPI {
        public static final String GET_COMPANY = "api/v1/company";
        public static final String GET_COMPANY_STATUS_TRUE = "api/v1/company/company-status-true";
        public static final String GET_COMPANY_BY_SEARCH_SORT = "api/v1/company/search-sort";
        public static final String GET_COMPANY_BY_ID = "api/v1/company/";
        public static final String CREATE_COMPANY = "api/v1/company/create";
        public static final String UPDATE_COMPANY = "api/v1/company/update/";
        public static final String CHANGE_STATUS_COMPANY = "api/v1/company/change-status/";

        public static final String GET_COMPANY_STATUS_TRUE_WITHOUT_PAGING = "api/v1/company/company-status-true-without-paging";
    }

    public static class ApplicationAPI {
        public static final String GET_APPLICATION = "api/v1/application";
        public static final String GET_APPLICATION_BY_ID = "api/v1/application/";
        public static final String CREATE_APPLICATION = "api/v1/application/create";
        public static final String UPDATE_APPLICATION = "api/v1/application/update/";
        public static final String CHANGE_STATUS_APPLICATION = "api/v1/application/change-status/";
        public static final String GET_APPLICATION_BY_MENTOR_ID = "api/v1/application/mentor/";
        public static final String GET_APPLICATION_BY_MENTEE_ID = "api/v1/application/mentee/";
        public static final String APPROVE_APPLICATION = "api/v1/application/approve/";
        public static final String GET_APPLICATION_BY_MENTOR_ID_AND_STATUS_AND_SORT_BY_CREATED_DATE = "api/v1/application/mentor-current-campaign/";
        public static final String GET_APPLICATION_BY_STUDENT_ID_AND_STATUS_AND_SORT = "api/v1/application/student/";

        public static final String REJECT_APPLICATION = "api/v1/application/reject/";
    }

    public static class TransactionAPI {
        public static final String GET_TRANSACTION = "api/v1/transaction";
        public static final String GET_TRANSACTION_STATUS_TRUE = "api/v1/transaction/transaction-status-true";
        public static final String GET_TRANSACTION_BY_ID = "api/v1/transaction/";
        public static final String CREATE_TRANSACTION = "api/v1/transaction/create";
        public static final String GET_TRANSACTION_BY_ACCOUNT_ID_AND_SORT_BY_CREATED_DATE = "api/v1/transaction/account/";

        public static final String GET_TRANSACTION_FOR_ADMIN = "api/v1/transaction/admin";
    }

    public static class NotificationAPI
    {
        public static final String SEND_NOTIFITION = "api/v1/send-notification";
        public static final String NOTIFITION_TOKEN = "api/v1/notifcation-token";
    }

    public static class DashboardAPI {
        public static final String GET_DASHBOARD = "api/v1/dashboard";
    }

}
