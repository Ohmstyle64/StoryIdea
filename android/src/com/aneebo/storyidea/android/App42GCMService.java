package com.aneebo.storyidea.android;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.aneebo.storyidea.Constants;
import com.aneebo.storyidea.android.social.SocialAndroid;
import com.aneebo.storyidea.android.social.packets.Packet;
import com.aneebo.storyidea.screens.WaitRoom;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class App42GCMService extends IntentService {

	private static final String MESSAGE_KEY = "message";
		
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
	
	public App42GCMService() {
		super("App42GCMService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " +extras.toString());
            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // Post notification of received message.
                handleBundleData(extras);
                
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        App42GCMReceiver.completeWakefulIntent(intent);
	}
	// Handles the bundle data
	private void handleBundleData(Bundle bundle) {
		Json json = new Json();
		final Packet packet = json.fromJson(Packet.class, bundle.getString(MESSAGE_KEY));
		
		String msg = "";
		String title = "";
		
		int type = packet.packetType;
		final SocialAndroid instance = SocialAndroid.getInstance();
		switch(type) {
		case Constants.CIRCLE_REQUEST :
			// Create notification
			title = "Rotsy Circle Request";
			msg = packet.requester + " wants you to join thier circle!";
			//TODO: Work on creating the correct intent so the dialog box appears.
			sendNotification(msg, title, new Intent(this, AndroidLauncher.class));
			
			if(instance != null) {
				instance.getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						AlertDialog.Builder builder = new AlertDialog.Builder(instance.getActivity());
						builder.setMessage(packet.requester +" sent you a circle request, do you wish to accept?")
							.setTitle("Circle Request");
						builder.setPositiveButton("Accept", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								instance.sendCircleResponse(packet.responder, true);
								instance.getWarpController().warpClient.joinRoom(packet.roomID);
								instance.getWarpController().warpClient.subscribeRoom(packet.roomID);
								Gdx.app.postRunnable(new Runnable() {
									
									@Override
									public void run() {
										((Game)Gdx.app.getApplicationListener()).setScreen(new WaitRoom());
										
									}
								});
							}
						});
						builder.setNegativeButton("Decline", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								instance.sendCircleResponse(packet.responder, false);
							}
						});
						
						AlertDialog dialog = builder.create();
						dialog.setCancelable(false);
						dialog.show();
					}
				});
			}
			
			break;
		case Constants.CIRCLE_RESPONSE :
			// Create notification
			title = "Rotsy Circle Reply";
			String reply = "";
			if(packet.accepted) {
				reply = " has accepted your circle request";
				instance.addAccepted(packet.responder);

			}
			else {
				reply = " has declined your circle request";
				instance.Decline();
			}
			msg = packet.responder + reply;
			sendNotification(msg, title,  new Intent(this, AndroidLauncher.class));
			break;
		}
	}
	
	// Create notifications for fail cases
	private void sendNotification(String msg) {
		sendNotification(msg, msg,  new Intent(this, AndroidLauncher.class));
	}
	
	// Create notfications for received messages
	private void sendNotification(String msg, String title, Intent intent) {
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
        .setContentTitle(title)
        .setSmallIcon(R.drawable.swarm_icon)
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(msg))
        .setContentText(msg);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
    	mBuilder.setContentIntent(contentIntent);
        Notification notification = mBuilder.build();
        mNotificationManager.notify(NOTIFICATION_ID, notification);
        Gdx.input.vibrate(500);
	}
	
}
