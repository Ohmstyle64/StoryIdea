package com.aneebo.storyidea.android.social.warp;

import java.util.HashMap;

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
import com.shephertz.app42.gaming.multiplayer.client.listener.TurnBasedRoomListener;

public class WarpController {
	
	private static WarpController warpController;
	
	public WarpClient warpClient;
	public String message;
	public SocialAndroid sa;
	
	private CircleConnectionListener connectionListener = new CircleConnectionListener();
	private CircleNotficationListener notficationListener = new CircleNotficationListener();
	private CircleChatRequestListener chatListener = new CircleChatRequestListener();
	private CircleTurnBasedRoomListener turnBasedRoomListener = new CircleTurnBasedRoomListener();
	
	private WarpController(SocialAndroid sa) {
		this.sa = sa;
		initWarp();
		warpClient.addConnectionRequestListener(connectionListener);
		warpClient.addNotificationListener(notficationListener);
		warpClient.addChatRequestListener(chatListener);
		warpClient.addTurnBasedRoomListener(turnBasedRoomListener);
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
	
	public class CircleTurnBasedRoomListener implements TurnBasedRoomListener {

		@Override
		public void onGetMoveHistoryDone(byte arg0, MoveEvent[] arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSendMoveDone(byte arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStartGameDone(byte arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopGameDone(byte arg0) {
			// TODO Auto-generated method stub
			
		}
		
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
			Gdx.app.log(StoryIdea.TITLE, arg0.getMessage()+"Apples");
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
			
		}

		@Override
		public void onRoomCreated(RoomData arg0) {
			// TODO Auto-generated method stub
			Gdx.app.log(StoryIdea.TITLE, "Room Created!: "+arg0.getName());
			
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
			Gdx.app.log(StoryIdea.TITLE, arg1+" Joined!");
		}

		@Override
		public void onUserLeftLobby(LobbyData arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUserLeftRoom(RoomData arg0, String arg1) {
			// TODO Auto-generated method stub
			Gdx.app.log(StoryIdea.TITLE, arg1+" Left!");
			
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
