//package com.gioidev.book.notification;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//
//
///**
// * Created by Vinh on 3/9/2018.
// */
//
//public class MobFirebaseInstanceIDService extends FirebaseInstanceIdService {
//    private String TAG = "MobFirebaseInstanceIDService";
//    private static MobFirebaseInstanceIDService INSTANCE;
//
//    public static MobFirebaseInstanceIDService getInstance(){
//        if(INSTANCE == null) INSTANCE = new MobFirebaseInstanceIDService();
//        return INSTANCE;
//    }
//    @Override
//    public void onTokenRefresh() {
//        // Get updated InstanceID token.
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "Refreshed token: " + refreshedToken);
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);
//    }
//
//    public void sendRegistrationToServer(String refreshedToken) {
//        try {
//            Log.d(TAG, "sendToken/RegId to server: " + refreshedToken);
//            AppsFlyerLib.getInstance().updateServerUninstallToken(MobGameSDK.getApplicationContext(), refreshedToken);
//            Preference.save(MobGameSDK.getApplicationContext() , Constants.SAVE_FCM_KEY , refreshedToken);
//            //TODO tại sao lại mất phần send lên server
//            if (!TextUtils.isEmpty(refreshedToken)) {
//                (new RegisterTokenAndTask(refreshedToken , "")).execute();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//}