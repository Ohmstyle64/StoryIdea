package com.aneebo.storyidea.android.social.packets;

public class Packet {

	// Creates a packet identifier
	public int packetType;
	
	// For request packet
	public String requester;
	
	// For join request packet
	public String roomID;
	
	// For response packet	
	public String responder;
	public boolean accepted;
	
	// For Turn Packet
	public String username;
	
	public Packet(int packetType) {
		this.packetType = packetType;
	}
	
	// Required for Json
	public Packet() {}
}
