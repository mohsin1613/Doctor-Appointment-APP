package com.project.meetingapp.utilities;

import java.util.HashMap;

public class Constants {
    public static final String KEY_COLLECTION_USERS = "users";
    public static final String KEY_ASSIGN_STAFFS = "assign_staffs";
    public static final String KEY_COLLECTION_APPOINTMENT = "Appointment";
    public static final String KEY_FULL_NAME = "full_name";
    public static final String KEY_DOCTOR_NUM = "doctor_name";
    public static final String KEY_STAFF_NUM = "staff_name";
    public static final String KEY_GRANT_APOINMNT = "grant_appointment";
    public static final String KEY_PATIENT_NUM = "patient_name";
    public static final String KEY_PROBLEM = "problem";
    public static final String KEY_SCHEDULE = "schedule";
    public static final String KEY_SPECIALIZATION = "specialization";
    public static final String KEY_FEE = "visit_fee";
    public static final String KEY_MOBILE_NUM = "mobile_number";
    public static final String KEY_DATE_OF_BIRTH = "date_of_birth";
    public static final String KEY_SEX = "sex";
    public static final String KEY_PAIR_STATUS = "pair_status";
    public static final String KEY_USER_TYPE = "user_type";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_BILL_STATUS = "bill_status";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_APPOINTMENT_ID = "appoinment_id";
    public static final String KEY_PATINT_ID = "patient_id";
    public static final String KEY_DOCTOR_ID = "doctor_id";
    public static final String KEY_STAFF_ID = "staff_id";
    public static final String KEY_FCM_TOKEN = "fcm_token";

    public static final String KEY_PREFERENCE_NAME = "videoMeetingPreference";
    public static final String KEY_IS_SIGNED_IN = "isSignedIn";

    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";

    public static final String REMOTE_MSG_TYPE = "type";
    public static final String REMOTE_MSG_INVITATION = "invitation";
    public static final String REMOTE_MSG_MEETING_TYPE = "meetingType";
    public static final String REMOTE_MSG_INVITER_TOKEN = "inviterToken";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";

    public static final String REMOTE_MSG_INVITATION_RESPONSE = "invitationResponse";
    public static final String REMOTE_MSG_INVITATION_ACCEPTED = "accepted";
    public static final String REMOTE_MSG_INVITATION_REJECTED = "rejected";
    public static final String REMOTE_MSG_INVITATION_CANCELLED = "cancelled";

    public static final String REMOTE_MSG_MEETING_ROOM = "meetingRoom";
    public static final String API_KEY_SERVER = "AAAAS8FI0R4:APA91bEFaF8_MI3-2gpxLacR_iZT2p1-5neeVOuvuOQqP81TT9nXE71quXcfReOgMYRqqbKHDVNyCINE9GHeyQ91Ptb3KkBrdetFECoJMST1UO9HGAHiQf32Cu8L2jlknNqJ6LiQ-8N0";

    public static HashMap<String, String> getRemoteMessageHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(
                Constants.REMOTE_MSG_AUTHORIZATION,
                "key="+ API_KEY_SERVER
        );
        headers.put(Constants.REMOTE_MSG_CONTENT_TYPE, "application/json");

        return headers;
    }
}
