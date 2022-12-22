package com.api.mobigenz_be.constants;

public final class Constant {
    private Constant() {
    }

    public static final class Role {
        public static final String Admin = "Admin";
        public static final String Employee = "Employee";
        public static final String Customer = "Customer";
    }

    public static final class OTP {
        public static final Long EXPIRED_TIME = 5 * 60 * 1000L;
    }

    public static final class Status {
        public static final String PENDING = "pending";
        public static final String APPROVED = "approved";
    }


    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final class Api {

        public static class Path {
            public static final String PREFIX = "/api";

            public static final String PUBLIC = PREFIX + "/public";
            public static final String ADMIN = PREFIX + "/admin";
            public static final String AUTH = PREFIX + "/auth";
            public static final String ACCOUNT =  "/account";
            public static final String USER = PREFIX + "/user";
            public static final String ON_BOARDING = PUBLIC + "/on-boarding";

            public static class Auth {
                public static final String LOGIN = "/login";
                public static final String REFRESH_TOKEN = "/refresh-token";
                public static final String CHECK_PHONE_NUMBER = "/check-phone-number";
                public static final String OTP = "/otp";
                public static final String OTP_REQUEST = OTP + "/request";
                public static final String OTP_VERIFY = OTP + "/verify";
            }

            public static class Account {
                public static final String REGISTER = "/register";
                public static final String CHANGE_PASSWORD = "/change-password";
                public static final String RESET_PASSWORD = "/reset-password";
                public static final String RESET_PASSWORD_INIT = RESET_PASSWORD + "/init";
                public static final String RESET_PASSWORD_FINISH = RESET_PASSWORD + "/finish";
            }

            public static class Admin {
                public static final String AUTH = ADMIN + "/auth";
            }
        }
    }

    public static class OrderStatus {
        public static final Integer EXCHANGE = -2;
        public static final Integer CANCEL_ORDER = -1;
        public static final Integer PENDING = 0;
        public static final Integer CONFIRMED = 1;
        public static final Integer PACKAGED = 3;
        public static final Integer DELIVERING = 4;
        public static final Integer COMPLETE = 5;
    }
//        -1	Hủy đơn hàng
//1	Chưa tiếp nhận
//2	Đã tiếp nhận
//3	Đã lấy hàng/Đã nhập kho
//4	Đã điều phối giao hàng/Đang giao hàng
//5	Đã giao hàng/Chưa đối soát
//6	Đã đối soát
//7	Không lấy được hàng
//8	Hoãn lấy hàng
//9	Không giao được hàng
//10	Delay giao hàng
//11	Đã đối soát công nợ trả hàng
//12	Đã điều phối lấy hàng/Đang lấy hàng
//13	Đơn hàng bồi hoàn
//20	Đang trả hàng (COD cầm hàng đi trả)
//21	Đã trả hàng (COD đã trả xong hàng)
//123	Shipper báo đã lấy hàng
//127	Shipper (nhân viên lấy/giao hàng) báo không lấy được hàng
//128	Shipper báo delay lấy hàng
//45	Shipper báo đã giao hàng
//49	Shipper báo không giao được giao hàng
//410	Shipper báo delay giao hàng

    public static class ValidationMessage {
        public static final String FIELD_IS_REQUIRED = "validation.mustNotBeNull";

        public static final String INVALID_PHONE_NUMBER = "validation.invalidPhoneNumber";
        public static final String INVALID_OTP = "validation.invalidOTP";
        public static final String INVALID_PASSWORD = "validation.invalidPassword";
        public static final String INVALID_USER_NAME = "validation.invalidUserName";
        public static final String INVALID_EMAIL = "validation.invalidEmail";
        public static final String INVALID_FIRST_NAME = "validation.invalidFirstName";
        public static final String INVALID_LAST_NAME = "validation.invalidLastName";
        public static final String INVALID_COUNTRY_CODE = "validation.invalidCountryCode";
        public static final String EXPIRED_OTP = "validation.expiredOTP";
        public static final String INVALID_DATE = "validation.invalidDate";
        public static final String UNVERIFIED_PHONE_NUMBER = "validation.unverifiedPhoneNumber";

        public static final String NOT_FOUND_RECEIVER = "validation.notFoundReceiver";
        public static final String NOT_FOUND_USER = "validation.notFoundUser";
        public static final String NOT_ACTIVATED_USER = "validation.notActivatedUser";
        public static final String NOT_FOUND_PET = "validation.notFoundPet";
        public static final String NOT_FOUND_NOTIFICATION = "validation.notFoundNotification";
        public static final String NOT_FOUND_TRACKER = "validation.notFoundTracker";
        public static final String CURRENT_PASSWORD_INVALID = "validation.invalidCurrentPassword";
        public static final String NOT_FOUND_BREED = "validation.notFoundBreed";
        public static final String NOT_FOUND_SAFE_ZONE = "validation.notFoundSafeZone";

        public static final String EXISTS_USER = "validation.existsUser";
        public static final String NOT_EXISTS_IMEI = "validation.notExistingImei";
        public static final String TOO_MANY_TIMES_GET_OTP = "validation.tooManyTimesGetOTP";
        public static final String TOO_SHORT_DURATION_BETWEEN_OTP_REQUESTS = "validation.tooShortDurationBetweenOtpRequests";

        public static final String INVALID_MIN_VALUE = "validation.invalidMinValue";
        public static final String INVALID_MAX_VALUE = "validation.invalidMaxValue";
        public static final String INVALID_SIZE_VALUE = "validation.invalidSizeValue";
        public static final String NOT_CONTAIN_WHITE_SPACE = "validation.notContainWhiteSpace";

    }

}
