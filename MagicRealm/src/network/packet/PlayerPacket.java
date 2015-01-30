package network.packet;

import java.io.Serializable;

public class PlayerPacket implements Serializable{

	private static final long serialVersionUID = 193456887118962294L;
	
	private String lNickname;
	
	public PlayerPacket(String aNickname){
		lNickname = aNickname;
	}

	public String getNickname() {
		return lNickname;
	}


}
