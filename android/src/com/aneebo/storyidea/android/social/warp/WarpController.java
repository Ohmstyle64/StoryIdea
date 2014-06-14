package com.aneebo.storyidea.android.social.warp;

import java.util.HashMap;

import android.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.android.AndroidLauncher;
import com.aneebo.storyidea.android.social.SocialAndroid;
import com.badlogic.gdx.Gdx;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ChatRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;

public class WarpController {
	
	private static WarpController warpController;
	
	public WarpClient warpClient;
	public String message;
	public SocialAndroid sa;
	
	private CircleConnectionListener connectionListener = new CircleConnectionListener();
	private CircleNotficationListener notficationListener = new CircleNotficationListener();
	private CircleChatRequestListener chatListener = new CircleChatRequestListener();
	
	private WarpController(SocialAndroid sa) {
		this.sa = sa;
		initWarp();
		warpClient.addConnectionRequestListener(connectionListener);
		warpClient.addNotificationListener(notficationListener);
		warpClient.addChatRequestListener(chatListener);
	}

	private void initWarp() {
		try {
			WarpClient.initialize(AndroidLauncher.API_KEY, AndroidLauncher.PVT_KEY);
			warpClient = WarpClient.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static WarpController getInstance(SocialAndroid sa) {
		if(warpController==null) {
			warpController = new WarpController(sa);
		}
		return warpController;
	}

	public class CircleConnectionListener implements ConnectionRequestListener {

		@Override
		public void onConnectDone(ConnectEvent arg0) {
			Gdx.app.log(StoryIdea.TITLE, "Connection: "+(arg0.getResult()==WarpResponseResultCode.SUCCESS));
			
		}

		@Override
		public void onDisconnectDone(ConnectEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onInitUDPDone(byte arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public class CircleChatRequestListener implements ChatRequestListener {

		@Override
		public void onSendChatDone(byte arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSendPrivateChatDone(byte arg0) {
			if(arg0!=WarpResponseResultCode.SUCCESS) {
				switch(arg0) {
				case WarpResponseResultCode.AUTH_ERROR :
					break;
				case WarpResponseResultCode.CONNECTION_ERROR :
					break;
				case WarpResponseResultCode.BAD_REQUEST :
					break;
				default :
				}
			}
			else {
			}
				
		}
		
	}
	
	public class CircleNotficationListener implements NotifyListener {

		@Override
		public void onChatReceived(ChatEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGameStarted(String arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGameStopped(String arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMoveCompleted(MoveEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPrivateChatReceived(String arg0, String arg1) {
			final String receivedFrom = arg0;
			int comp = Integer.valueOf(arg1.substring(0, 3));
			final String target = arg1.substring(3);
			switch(comp) {
			case SocialAndroid.MESSAGE :
				message = target;
				break;
			case SocialAndroid.REQUEST :
				Gdx.app.postRunnable(new Runnable() {
					
					@Override
					public void run() {
						AlertDialog.Builder builder = new AlertDialog.Builder(sa.getActivit());
						builder.setTitle("Accept Circle");
						builder.setMessage(target+" wants you to join a circle");
						builder.setPositiveButton("Accept", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								warpClient.sendPrivateChat(receivedFrom, SocialAndroid.RESPONSE_YES+target);
							}
						});
						builder.setNegativeButton("Decline", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								warpClient.sendPrivateChat(receivedFrom, SocialAndroid.RESPONSE_NO+target);
							}
						});
						AlertDialog dialog = builder.create();
						dialog.show();
					}
				});
				break;
			case SocialAndroid.RESPONSE_YES :
				Gdx.app.log(StoryIdea.TITLE, arg0+": YES");
				break;
			case SocialAndroid.RESPONSE_NO :
				Gdx.app.log(StoryIdea.TITLE, arg0+": NO");
				break;
			}
						
		}

		@Override
		public void onRoomCreated(RoomData arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRoomDestroyed(RoomData arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUpdatePeersReceived(UpdateEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUserChangeRoomProperty(RoomData arg0, String arg1,
				HashMap<String, Object> arg2, HashMap<String, String> arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUserJoinedLobby(LobbyData arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUserJoinedRoom(RoomData arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUserLeftLobby(LobbyData arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUserLeftRoom(RoomData arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUserPaused(String arg0, boolean arg1, String arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUserResumed(String arg0, boolean arg1, String arg2) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
