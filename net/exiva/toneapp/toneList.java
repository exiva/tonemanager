package net.exiva.toneapp;

import danger.app.*;

import danger.ui.*;

import danger.util.DEBUG;
import danger.app.IPCMessage;
import danger.app.Registrar;

public class toneList extends ListView implements Resources, Commands {
	
	public toneList() {
	}

	public boolean eventWidgetDown(int inWidget, Event e) {
		switch (inWidget) {
			case Event.DEVICE_WHEEL_BUTTON: {
					toneView.confirmDelete();
					// toneView.deleteRingTone(title);
			return true;
			}
		}
		return super.eventWidgetDown(inWidget, e);
	}
}