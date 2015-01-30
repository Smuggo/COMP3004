package model;

import javax.swing.JFrame;

import view.ViewManager;
import view.externals.MagicRealmWindow;
import network.NetworkManager;
import network.client.ClientApp;
import network.server.ServerApp;

public class MagicRealm {
	
	ViewManager lViewManager;
	NetworkManager lNetworkManager;
	ViewModel lViewModel;
	
	public MagicRealm(){

		lViewModel = new ViewModel();
		
		lViewManager = new ViewManager(lViewModel);
		lNetworkManager = new NetworkManager();
		
		lViewModel.setViewManager(lViewManager);
	}
	
	public void start(){
		while(true){
			
			
		}
	}
}
