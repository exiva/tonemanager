package net.exiva.toneapp;

import danger.app.Application;
import danger.app.AppResources;
import danger.app.Bundle;

public class toneapp extends Application implements Resources, Commands {
	static public toneView mWindow;

	public toneapp() {
		mWindow = toneView.create();
		mWindow.show();
	}

	public void launch() {
		mWindow.populateListView();
	}

	public void resume() {
		mWindow.populateListView();
	}
}