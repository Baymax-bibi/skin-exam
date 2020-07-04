package com.skinexam.myapplication.utils;

/**
 * Created by satyawan on 23/03/2015.
 */
public class Constants {

    //public static final String URL="http://skinexams.php-dev.in/api/api_new/";
    //HTTPS://skinexam.com
//    public static final String URL="https://skinexam.com/api/api_new/";
    public static final String URL="https://skinexam.com/clients/";
    //Preferences
    public static final String PACK_PREF_NAME="packPrefName";
    public static final String LOGIN_PREF="loginPrefs";
    public static final String TOKEN = "token";

    //Logout
    public static final String PROFILE_LOGOUT=URL+"logout_json";

    //Registration
    public static final String STUDENT_REGISTER = URL+"api_register.php";
//    public static final String STUDENT_REGISTER = "https://blessing.skinexam.com/clients/api_register.php";
    //Login and pending and recent cases
    public static final String STUDENT_LOGIN = URL + "api_login.php";
//    public static final String STUDENT_LOGIN = "https://blessing.skinexam.com/clients/api_login.php";
    //Get city for registration
    public static final String CITY=URL+"api_country.php";

//    public static final String STUDENT_DASHBOARD = URL + "api_dashboard.php";
    public static final String MYCASE = URL + "api_mycase.php";

    //Edit Profile
    public static final String PROFILE_EDIT = URL + "api_profile.php";

    public static final String STUDENT_PROFILE = URL + "api_profile_step1.php";
    public static final String STUDENT_PROFILE_end = URL + "api_profile.php";

    public static final String BODYPART = URL + "api_body_part.php";
    public static final String PATIENTPREV = URL + "api_pre_used.php";

    public static final String CREATECASE = URL + "api_create_case.php";

    //Checkout
    public static final String ORDER_CHECKOUT = URL + "orderCheckout_json";

    //Change Password
    public static final String CHANGE_PASSWORD=URL+"api_change_password.php";

    //Forgot Password
    public static final String GETEMAIL_JSON=URL + "api_forgetpass.php";

    //Get user Previous Cases
    public static final String STUDENT_TICKET= URL + "student_ticket_json";

    //Get Info about user previous casees


    public static final String TICKET_JSON=URL + "api_ticket_android.php";

    //Get Age For Add Case
    public static final String AGE=URL+"api_age.php";

    //Get Health status for Add Case
    public static final String HEALTH=URL+"api_health_state.php";

    //Get Patient state for Add case
    public static final String STATUS=URL+"api_health_state.php";

    //Add new case
//    public static final String ADD_JSON=URL+"add_json";

    //Get Authorization Code
//    public static final String GET_AUTHORIZATION_CODE=URL+"get_auth_code";

    //Get Payment Methods and Available Comapany
//    public static final String SHOW_PAY_METHODS=URL+"show_paymethods";

    //Paypal Payment
//    public static String PAYPAL_PAYMENT="https://api.sandbox.paypal.com/v1/payments/payment";

    //Dashboard
//    public static final String DASHBOARD="student_dashboard_json";

    // -------- FRAGMENTS
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";
    public static final String LOGIN_TYPE="login_type";
    //public static final String IMG_URL ="http://skinexams.php-dev.in/";
    public static final String IMG_URL = URL + "images/profiles/";
    public static final String FROM_DATE="fromdate";
    public static final String TO_DATE="todate";
    public static final String OFFSET="offset";
    public static final String SUBS_ID="subscription_id";
    public static final String AUTHORIZATION_ID="Authorization_id";
    public static final String FIRST_NAME="firstname";
    public static final String LAST_NAME="lastname";
    public static final String ADDRESS="address";
    public static final String CITY_NAME="city";
    public static final String STATE="state";
    public static final String ZIP_CODE=" zipcode";
    public static final String COUNTRY_ID="country_id";
    public static final String MOB_NO="mob_no";
    public static final String EMAIL="email";
    public static final String CON_PASSWORD="conf_password";
    public static final String SUBS="subs";
    public static final String TEL_NO="tel_no";
    public static final String USER_ID="user_id";
    public static final String SESSION_ID="session_id";
    public static final String OLD_PASSWORD="old_password";
    public static final String NEW_PASSWORD="new_password";
    public static final String CONFIRM_PASSWORD="confirm_password";

    //Add case
    public static final String TITLE="title";
    public static final String IMAGE1="image1";
    public static final String IMAGE2="image2";
    public static final String IMAGE3="image3";
    public static final String SUMMERY="summary";
    public static final String TICKET_ID= "caseid";
    public static final String AGE_DATA ="age";
    public static final String HEALTH_STATUS_ID="health_status_id";
    public static final String PATIENT_STATUS_ID=" patient_state_id";
    public static final String TICKET_DESCRIPTION= "ticket_description";
    public static final String USERBODYPART = "user_body_part_id";

    public static final String PRIVTREAT = "priortreatment";
    public static final String DES_1 = "description1";
    public static final String DES_2 = "description2";
    public static final String DES_3 = "description3";

    public static final String ITCHY = "itchy";
    public static final String CHANGECOLOR = "changing_colors";
    public static final String FEELBUMP = "fells_like_bump";


    //get Authorizaion
    public static final String AUTH_CODE="code";
    public static final String CLIENT_METADATA_ID="client_id";
    public static final String POST_JSON="post_json";

    //for Service Authorize service Header For PHP Authentication
    public static final String Auth_UserName= "webtest";
    public static final String Auth_Password= "webtest";
    public static final String STUDENT_DASHBOARD_ = URL + "api_dash.php";
    public static final String HISTORY = URL + "api_history.php";
}
