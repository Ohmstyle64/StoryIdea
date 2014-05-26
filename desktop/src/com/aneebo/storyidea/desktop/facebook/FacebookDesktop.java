package com.aneebo.storyidea.desktop.facebook;

import com.aneebo.storyidea.facebook.FacebookI;

public class FacebookDesktop implements FacebookI {

	@Override
	public void login() {
		// TODO Auto-generated method stub
		System.out.println("Desktop: Login");
	}

	@Override
	public boolean sendMessage(String str) {
		System.out.println("Desktop: "+str);
		return false;
	}

}
