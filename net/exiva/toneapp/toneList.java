package net.exiva.toneapp;

import danger.app.Event;

import danger.ui.ListView;

public class toneList extends ListView implements Resources, Commands {

	public toneList() {
	}

	public boolean eventWidgetDown(int inWidget, Event e) {
		switch (inWidget) {
			case Event.DEVICE_WHEEL_BUTTON: {
					toneView.confirmDelete();
			return true;
			}
		}
		return super.eventWidgetDown(inWidget, e);
	}
}