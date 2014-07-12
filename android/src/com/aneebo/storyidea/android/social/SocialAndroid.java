package com.aneebo.storyidea.android.social;

import java.util.List;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

import com.aneebo.storyidea.Constants;
import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.android.AndroidLauncher;
import com.aneebo.storyidea.android.App42Registration;
import com.aneebo.storyidea.android.social.packets.Packet;
import com.aneebo.storyidea.android.social.warp.WarpController;
import com.aneebo.storyidea.social.SocialCodeI;
import com.aneebo.storyidea.users.SocialUser;
import com.aneebo.storyidea.users.UserList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActiveUser;
import com.swarmconnect.SwarmActiveUser.GotFriendsCB;
import com.swarmconnect.SwarmUser;
import com.swarmconnect.delegates.SwarmLoginListener;

public class SocialAndroid implements SocialCodeI {
	public SocialUser meUser;
	
	private AndroidLauncher activity;
	private Array<SocialUser> friends;
	private WarpController warpController;
	private App42Registration app42Registration;
	
	private static SocialAndroid instance;
	
	private Array<String> acceptedUsers;
	private Array<String> pendingUsers;
	
	private int totalInvites;
	private boolean creatingCircle;
	
	private String roomID;

	private SocialAndroid(AndroidLauncher activity) {
		this.activity = activity;
		friends = new Array<SocialUser>(false,20,SocialUser.class);
		acceptedUsers = new Array<String>(false,1,String.class);
		creatingCircle = false;
		totalInvites = 0;
	}
	
	public static SocialAndroid getInstance(AndroidLauncher activity) {
		if(instance==null) {
			instance = new SocialAndroid(activity);
		}
		return instance;
	}
	
	public static SocialAndroid getInstance() {
		return instance;
	}
	
	@Override
	public void login() {
		if(!Swarm.isLoggedIn())	Swarm.showLogin();
	}

	@Override
	public void sendMessage(String str) {
		warpController.warpClient.sendChat(str);
		
	}

	@Override
	public void logout() {
		if(Swarm.isLoggedIn()) Swarm.logOut();
	}

	@Override
	public boolean isLoggedIn() {
		return Swarm.isLoggedIn();
	}

	@Override
	public Array<SocialUser> getFriends() {
		if(Swarm.isLoggedIn()) {
			
			friends.clear();
			
			Swarm.user.getFriends(new GotFriendsCB() {
				
				@Override
				public void gotFriends(List<SwarmUser> arg0) {
					if(arg0 != null) {
						for(final SwarmUser swarmUser : arg0) {
							SocialUser user = new SocialUser(swarmUser.points, swarmUser.userId, swarmUser.username);
							friends.add(user);
						}
					}
					friends.shrink();
				}
			});
		}
		return friends;
	}
	
	@Override
	public void showDashboard() {
		if(Swarm.isInitialized()) Swarm.showDashboard();
	}
	
	@Override
	public void initiate() {
		if(!Swarm.isInitialized()) {
			Swarm.init(activity, AndroidLauncher.MY_APP_D, AndroidLauncher.SWARM_ID, new SwarmLoginListener() {
				
				@Override
				public void userLoggedOut() {
					warpController.warpClient.disconnect();
					meUser = null;
				}
				
				@Override
				public void userLoggedIn(SwarmActiveUser arg0) {
					meUser = new SocialUser(arg0.points,arg0.userId,arg0.username);
					warpController = WarpController.getInstance(instance);
					warpController.warpClient.connectWithUserName(arg0.username);
					App42API.initialize(activity, AndroidLauncher.API_KEY, AndroidLauncher.PVT_KEY);
					App42API.setLoggedInUser(arg0.username);
					app42Registration = new App42Registration(activity);
					getFriends();
				}
				
				@Override
				public void loginStarted() {
				}
				
				@Override
				public void loginCanceled() {
					
				}
			});
		}
	}


	@Override
	public boolean isInitialized() {
		return Swarm.isInitialized();
	}

	@Override
	public String receiveMessage() {
		return warpController.message;
	}
	
	@Override
	public SocialUser getMeUser() {
		return meUser;
	}
	
	public Activity getActivity() {
		return activity;
	}
	
	public void sendRoomID(String roomID) {
		this.roomID = roomID;
		warpController.warpClient.subscribeRoom(roomID);
		if(pendingUsers != null) {
			for(String user : pendingUsers) {
				sendCircleRequest(user, roomID);
			}
		}
	}
	
	// Helper method for sending requests
	private void sendCircleRequest(String username, String roomID) {
		Json json = new Json();
		Packet packet = new Packet(Constants.CIRCLE_REQUEST);
		packet.requester = meUser.getUsername();
		app42Registration.sendMessage(username, json.toJson(packet, Packet.class));
		Gdx.app.log(StoryIdea.TITLE, json.toJson(packet, Packet.class));
	}
	
	public void sendCircleResponse(String username, boolean accepted) {
		Json json = new Json();
		Packet packet = new Packet(Constants.CIRCLE_RESPONSE);
		packet.responder = meUser.getUsername();
		packet.accepted = accepted;
		app42Registration.sendMessage(meUser.getUsername(), json.toJson(packet, Packet.class));
	}
	
	public void addAccepted(String username) {
		acceptedUsers.add(username);
		acceptedUsers.shrink();
		if(acceptedUsers.size >= totalInvites && creatingCircle) {
			
		}
	}
	
	public void Decline() {
		totalInvites = 0;
		creatingCircle = false;
		acceptedUsers.clear();
		Gdx.app.log(StoryIdea.TITLE, "Circle failed to launch");
		activity.toast.setGravity(Gravity.CENTER, 0, 0);
		activity.toast.setText("Your Circle Was Declined! Need to create another circle!");
		activity.toast.show();
	}

	@Override
	public void createCircleRoom(UserList ul) {
		Array<String> temp = new Array<String>(false, 1, String.class);
		temp.add(meUser.getUsername());
		pendingUsers = temp;
		Gdx.app.log(StoryIdea.TITLE, "Creating Room!");
		warpController.warpClient.createTurnRoom(meUser.getUsername(), meUser.getUsername(), pendingUsers.size, null, 30);
		totalInvites = pendingUsers.size;
		creatingCircle = true;
	}
	
	public WarpController getWarpController() {
		return warpController;
	}
}
