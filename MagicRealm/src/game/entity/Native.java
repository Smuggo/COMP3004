package game.entity;

import game.GameManager;
import game.environment.hex.Clearing;
import game.item.Armour;
import game.item.Treasure;
import game.item.Weapon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import config.Config.CharacterImageType;
import config.Config.DwellingType;
import config.Config.NativeGroup;

// Represents a Garrison Native Group


//CHAPEL (Order)
	//2 Treasures
	//2 Great axes (H) 1 Great sword (H) 1 Morning star (H) 1 Crossbow (H) — — — 2
	//3 warhorses
//HOUSE (Soldiers)
	//2 Treasures
	//3 Short swords (L) 2 Thrusting swords (L) 2 Staffs (L) 3 — 2 — —
//INN (Rogues)
	//2 Treasures — — — — — — 
	//6 workhorses
//GUARD house (Guard) 2 — 2 Maces (M) 2 Axes (M) 1 Broadsword (M) 1 1 — — — 
public class Native implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2499880216230145229L;

	private NativeGroup nativeGroup;
	
	private ArrayList<Treasure> treasures;
	private ArrayList<Weapon> weapons;
	private ArrayList<Armour> armours;
	//ArrayList<Horse> horses;
	
	private Clearing lClearing;
	private DwellingType lDwellingType;
	
	CharacterImageType characterChit;
	
	public Native(CharacterImageType cIT, Clearing c) {
		characterChit = cIT;
		lClearing = c;
	}
	
	// Testing
	public Native(NativeGroup n, ArrayList<Weapon> w) {
		nativeGroup = n;
		weapons = w;
	}
	
	public void draw(GameManager aManager, Graphics g, Player aPlayer) {
		if (lClearing != null) {
			int x = (int) lClearing.getRotPosition().getX();
			int y = (int) lClearing.getRotPosition().getY();

			g.drawImage(getScaledImage(aManager.getCharacterImage(characterChit), 50, 50), x - 25, y - 25, Color.WHITE, null);
		}
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
		BufferedImage symbol = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = symbol.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return symbol;
	}
	
	public NativeGroup getNativeGroup() {
		return nativeGroup;
	}
	
	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public void removeWeapon(Weapon obj) {
		weapons.remove(obj);	
	}
}
