package init;

import game.chit.ChitFactory;
import model.MagicRealm;

//
// Game Bootstraps
//
public class MagicRealmApplication {

	public static void main(String[] args) {
		
		MagicRealm lHub = new MagicRealm();
		lHub.start();
		
	}
}
