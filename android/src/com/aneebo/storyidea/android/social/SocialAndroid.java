package com.aneebo.storyidea.android.social;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;

import com.aneebo.storyidea.StoryIdea;
import com.aneebo.storyidea.android.AndroidLauncher;
import com.aneebo.storyidea.android.social.warp.WarpController;
import com.aneebo.storyidea.circles.CircleList;
import com.aneebo.storyidea.circles.UserCircle;
import com.aneebo.storyidea.social.SocialCodeI;
import com.aneebo.storyidea.users.SocialUser;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActiveUser;
import com.swarmconnect.SwarmApplication;
import com.swarmconnect.SwarmActiveUser.GotCloudDataCB;
import com.swarmconnect.SwarmActiveUser.GotFriendsCB;
import com.swarmconnect.SwarmUser;
import com.swarmconnect.SwarmUser.GotUserStatusCB;
import com.swarmconnect.delegates.SwarmLoginListener;

public class SocialAndroid implements SocialCodeI {

	public static final int MESSAGE = 100;
	public static final int REQUEST = 101;
	public static final int RESPONSE_YES = 102;
	public static final int RESPONSE_NO = 103;

	private static final String KEYS_LIST = "aneebo_keys_list";
	private static final String CIRCLE_LIST = "aneebo_circle_list";
	
	public SocialUser meUser;
	
	private Activity activity;
	private Array<SocialUser> friends;
	private CircleList circleList;
	private WarpController warpController;
	private SwarmActiveUser curUser;
	
	private static SocialAndroid instance;

	//TODO:Remove
	private UserCircle tempCircle;
	
	HashMap<String, String> keysList;
	
	private SocialAndroid(Activity activity) {
		this.activity = activity;
		friends = new Array<SocialUser>(false,20,SocialUser.class);
		keysList = new HashMap<String, String>();
		tempCircle = new UserCircle();
		circleList = new CircleList();
	}
	
	public static SocialAndroid getInstance(Activity activity) {
		if(instance==null)
			instance = new SocialAndroid(activity);
		return instance;
	}
	
	
	@Override
	public void login() {
		if(!Swarm.isLoggedIn())	Swarm.showLogin();
	}

	@Override
	public void sendMessage(UserCircle uc, String str) {
		sendRequest(uc.next(str).getUsername(), MESSAGE+str);
		//saveCircleToCloud(uc);
		Gdx.app.log(StoryIdea.TITLE, "Android: "+str);
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
					curUser = null;
					meUser = null;
				}
				
				@Override
				public void userLoggedIn(SwarmActiveUser arg0) {
					curUser = arg0;
					meUser = new SocialUser(arg0.points,arg0.userId,arg0.username);
					warpController = WarpController.getInstance(instance);
					warpController.warpClient.connectWithUserName(arg0.username);
				}
				
				@Override
				public void loginStarted() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void loginCanceled() {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}

	@Override
	public CircleList getCloudCircles() {
		
		GotCloudDataCB callback = new GotCloudDataCB() {
			
			@Override
			public void gotData(String arg0) {
				if(arg0 != null) {
					Json json = new Json();
					
				}
			}
		};
		
		return circleList;
	}

	@Override
	public void saveCircleToCloud(UserCircle circles) {
		//TODO: Implement this with a call to App42]
		Json json = new Json();
		curUser.saveCloudData(curUser.userId+CIRCLE_LIST, json.toJson(circles, UserCircle.class));
		circleList.addCircle(circles);
	}

	@Override
	public UserCircle getTempCloudCircle() {
		GotCloudDataCB callback = new GotCloudDataCB() {
			
			@Override
			public void gotData(String arg0) {
				if(arg0 != null) {
					Json json = new Json();
					tempCircle = json.fromJson(UserCircle.class, arg0);
				}
			}
		};
		
		return tempCircle;
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
	public void requestToStartCircle(UserCircle uc) {
		Array<SocialUser> users = uc.getActiveUsers();
		for(SocialUser user : users) {
			sendRequest(user.getUsername(), REQUEST+curUser.username);
		}
	}
	
	private void sendRequest(String target, String request) {
		warpController.warpClient.sendPrivateChat(target, request);
	}
	
	@Override
	public SocialUser getMeUser() {
		return meUser;
	}
	
	public Activity getActivit() {
		return activity;
	}

}
