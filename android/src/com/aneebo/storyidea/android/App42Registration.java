package com.aneebo.storyidea.android;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import com.aneebo.storyidea.StoryIdea;
import com.badlogic.gdx.Gdx;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.push.PushNotificationService;

public class App42Registration {
	
    private static final String PROPERTY_REG_ID = "registration_id";
	
	private GoogleCloudMessaging gcm;
	private String regID;
	private Context context;

	public App42Registration(Context context) {
		this.context = context;
		gcm = GoogleCloudMessaging.getInstance(context);
		registrarGCM();
	}
	
	private void registrarGCM() {
		regID = getRegistrationID(context);
		
		if(regID.isEmpty()) {
			registerInBackground();
		}
	}
	private void registerInBackground() {
		Gdx.app.postRunnable(new Runnable() {
			
			@Override
			public void run() {
	            String msg = "";
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(context);
	                }
	                regID = gcm.register(AndroidLauncher.GOOGLE_PROJECT_ID);
	                msg = "Device registered, registration ID=" + regID;

	                // You should send the registration ID to your server over HTTP,
	                // so it can use GCM/HTTP or CCS to send messages to your app.
	                // The request to your server should be authenticated if your app
	                // is using accounts.
	                sendRegistrationIdToBackend();

	                // For this demo: we don't need to send it because the device
	                // will send upstream messages to a server that echo back the
	                // message using the 'from' address in the message.

	                // Persist the regID - no need to register again.
	                storeRegistrationId(context, regID);
	            } catch (IOException ex) {
	                msg = "Error :" + ex.getMessage();
	                // If there is an error, don't just keep trying to register.
	                // Require the user to click a button again, or perform
	                // exponential back-off.
	            }
	            Gdx.app.log(StoryIdea.TITLE, msg);
			}
		});
		

	}
	
	private void sendRegistrationIdToBackend() {
		App42API.buildPushNotificationService().storeDeviceToken(App42API.getLoggedInUser(), regID, new App42CallBack() {
			
			@Override
			public void onSuccess(Object arg0) {
				Gdx.app.log(StoryIdea.TITLE, " ..... Registration Success ....");  
			}
			
			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				Gdx.app.log(StoryIdea.TITLE, " ..... Registration Failed ....");
				Gdx.app.log(StoryIdea.TITLE, "storeDeviceToken :  Exception : on startup " +arg0);
			}
		});
				
	}
	
	
	public void sendMessage(String target, String message) {
		PushNotificationService pushService = App42API.buildPushNotificationService();
		pushService.sendPushMessageToUser(target, message, new App42CallBack() {
			
			@Override
			public void onSuccess(Object arg0) {
				Gdx.app.log(StoryIdea.TITLE, "Success!");
			}
			
			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				Gdx.app.log(StoryIdea.TITLE, "Failure!");
				
			}
		});
	}
	
	private String getRegistrationID(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
	        Gdx.app.log(StoryIdea.TITLE, "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing regID is not guaranteed to work with the new
	    // app version.
	    int registeredVersion = prefs.getInt(StoryIdea.VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	    	Gdx.app.log(StoryIdea.TITLE, "App Version Change.");
	        return "";
	    }
	    return registrationId;
		
	}

	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the regID in your app is up to you.
	    return context.getSharedPreferences(AndroidLauncher.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}

	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Gdx.app.log(StoryIdea.TITLE, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(StoryIdea.VERSION, appVersion);
	    editor.commit();
	}
	
}
