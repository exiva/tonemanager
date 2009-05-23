package net.exiva.tonemanager;

import danger.app.Application;
import danger.app.AppResources;
import danger.app.Bundle;
import danger.app.Event;
import danger.app.EventType;
import danger.app.SettingsDB;
import danger.app.SettingsDBException;

import danger.audio.Meta;

import danger.ui.AlertWindow;
import danger.ui.DialogWindow;
import danger.ui.NotificationManager;
import danger.ui.MarqueeAlert;

import danger.util.DEBUG;

public class tonemanager extends Application implements Resources, Commands {
	static public toneView mWindow;

	public tonemanager() {
		mWindow = toneView.create();
		mWindow.show();
	}

	public void launch() {
	}

	public void resume() {
	}

	public void suspend() {
	}

	public boolean receiveEvent(Event e) {
		switch (e.type) {
			// case Event.EVENT_AUTO_SYNC_DONE: {
				// restoreData();
				// return true;
			// }
		}
		return (super.receiveEvent(e));
	}
}