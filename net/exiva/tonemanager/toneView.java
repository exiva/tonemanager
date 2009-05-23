package net.exiva.tonemanager;

import danger.app.*;

import danger.ui.*;
import danger.ui.file.*;

import danger.util.*;

import danger.audio.ToneClass;
import danger.audio.ToneRights;
import danger.audio.ToneGallery;
import danger.audio.Tone;
import danger.audio.ToneOrigin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.*;

public class toneView extends ScreenWindow implements Resources, Commands {

	OpenFileDialog dOpenFile;
	ListView lvRangt0n3z;
	
	public toneView() {
	}

	public static toneView create() {
		toneView me = (toneView) Application.getCurrentApp().getResources().getScreen(ID_MAIN_SCREEN, null);
		return me;
	}

	public void onDecoded() {
		lvRangt0n3z = (ListView)this.getDescendantWithID(ID_RANGT0NEZLIST);
		super.onDecoded();
	}

	public final void adjustActionMenuState(Menu menu) {
		menu.removeAllItems();
		menu.addFromResource(Application.getCurrentApp().getResources(), ID_MAIN_MENU, this);
	}

	public boolean receiveEvent(Event e) {
		switch (e.type) {
			case EVENT_IMPORT_RINGTONE: {
				dOpenFile = OpenFileDialog.createAndShow(OpenFileDialog.OPEN_OPTIONS_FILTER_OUT_DOT_FILES, null, null, new Event(this, EVENT_LOAD_FILE), null, null);
				return true;
			}
			case EVENT_LOAD_FILE: {
				DEBUG.p("Here.");
				File file = (File)e.argument;
				byte[] data = getBytesFromFile(file);
				DEBUG.p("Data: "+data);
				if (data == null) return true;
				String name = file.getName();
				DEBUG.p("Name: "+name);
				addTone(data, name, file.getName(), new Event(this, EVENT_TONE_ADD_OK), null, true);
				// ToneGallery.add(ToneGallery.Type.UNKNOWN, data, name, name, new Event(this, EVENT_TONE_ADD_OK), new Event(this, EVENT_TONE_ADD_FAIL), ToneOrigin.USER, ToneRights.FULL, true);
				return true;
			}
			case EVENT_TONE_ADD_OK: {
				DEBUG.p("Tone Added.");
				return true;
			}
			case EVENT_TONE_ADD_FAIL: {
				DEBUG.p("Tone Failed.");
				return true;
			}
			case ABOUT: {
				// AlertWindow about = getApplication().getAlert(ID_ABOUT, this);
				// about.show();
				// 
				lvRangt0n3z.addItem("Testing...");
				return true;
			}
			default:
			break;
		}
		return super.receiveEvent(e);
	}

    public static void addTone(byte[] data, String name, String productID, Event ok_evt, Event fail_evt, boolean notify)
    {
        ToneGallery.add(ToneGallery.Type.SEQUENCE, data, null, 0, ok_evt, fail_evt, ToneOrigin.USER, ToneRights.FULL, notify);
    }

    public static byte[] getBytesFromFile(File file) {
		try {
			FileInputStream is = new FileInputStream(file);
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			is.close();
			return bytes;
		} catch (Exception ex) {
			DEBUG.p(ex);
			return null;
		}
	}

    public boolean eventWidgetUp(int inWidget, Event e) {
		switch (inWidget) {
			case Event.DEVICE_BUTTON_CANCEL:
			Application.getCurrentApp().returnToLauncher();
			return true;

			case Event.DEVICE_BUTTON_BACK:
			Application.getCurrentApp().returnToLauncher();
			return true;
		}
		return super.eventWidgetUp(inWidget, e);
	}
}