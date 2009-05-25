package net.exiva.toneapp;

import danger.app.Application;
import danger.app.Event;

import danger.audio.Tone;
import danger.audio.ToneClass;
import danger.audio.ToneGallery;
import danger.audio.ToneOrigin;
import danger.audio.ToneRights;

import danger.ui.AlertWindow;
import danger.ui.Button;
import danger.ui.DialogWindow;
import danger.ui.file.OpenFileDialog;
import danger.ui.ListView;
import danger.ui.Menu;
import danger.ui.MenuItem;
import danger.ui.ScreenWindow;
import danger.ui.TextField;
import danger.ui.TextInputAlertWindow;

import danger.util.DEBUG;
import danger.util.MetaBitmaps;

import java.io.File;
import java.io.FileInputStream;

import java.util.Vector;

public class toneView extends ScreenWindow implements Resources, Commands {

	public static AlertWindow aConfirm;
	public static Button bImport, bDelete;
	public static byte[] data;
	public static DialogWindow dRangtonez;
	public static File file;
	public static Integer id;
	public static ListView lvRangt0n3z;
	public static OpenFileDialog dOpenFile;
	public static String name;
	public static TextField ringtoneName;
	public static TextInputAlertWindow dRingToneName;

	public toneView() {
	}

	public static toneView create() {
		toneView me = (toneView) Application.getCurrentApp().getResources().getScreen(ID_MAIN_SCREEN, null);
		return me;
	}

	public void onDecoded() {
		aConfirm = getApplication().getAlert(ID_CONFIRM, this);
		dRangtonez = getApplication().getDialog(ID_DEL_RINGTONE, this);
		dRingToneName = getApplication().getTextInputAlert(ID_NAME_RINGTONE, this);
		lvRangt0n3z = (ListView)dRangtonez.getDescendantWithID(ID_RANGT0NEZLIST2);
		ringtoneName = (TextField)dRingToneName.getDescendantWithID(ID_RINGTONE_NAME);

		//dirty little hack to set OS bitmaps on buttons.
		bImport = (Button)this.getDescendantWithID(ID_IMPORT);
		bDelete = (Button)this.getDescendantWithID(ID_DELETE);
		bImport.setBitmap(MetaBitmaps.get(MetaBitmaps.ID_MENU_ICON_RINGTONE));
		bImport.setBitmapAlignment(Button.ALIGN_LEFT);
		bDelete.setBitmap(MetaBitmaps.get(MetaBitmaps.ID_MENU_ICON_TRASH));
		bDelete.setBitmapAlignment(Button.ALIGN_LEFT);

		super.onDecoded();
	}

	public final void adjustActionMenuState(Menu menu) {
		menu.removeAllItems();
		menu.addFromResource(Application.getCurrentApp().getResources(), ID_MAIN_MENU, this);
	}

	public void nameTone(String inName) {
		ringtoneName.setText(inName);
		dRingToneName.show();
	}
	
	public void populateListView() {
		lvRangt0n3z.removeAllItems();
		Vector<String> tones = ToneGallery.getNamesFromGroup("Imported",ToneClass.CUSTOM,ToneRights.DEFAULT);
		for (int i=0; i < tones.size(); i++) {
			String names = tones.get(i);
			int rID = ToneGallery.getIDFromNameAndGroup(names,"Imported");
			MenuItem lvitem = lvRangt0n3z.addItem(names);
			lvitem.setUserData(new Integer(rID));
        }
		invalidate();
	}

	public static void confirmDelete() {
		MenuItem item = lvRangt0n3z.getSelectedItem();
		id = (Integer)item.getUserData();
		String title = item.getTitle();
		aConfirm.setMessage("Are you sure you want to discard "+title);aConfirm.setMessage("Are you sure you want to discard "+title);
		aConfirm.show();
	}

    public static byte[] getBytesFromFile(File file) {
		try {
			FileInputStream is = new FileInputStream(file);
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			is.close();
			return bytes;
		} catch (Exception ex) {
			return null;
		}
	}

	public boolean receiveEvent(Event e) {
		switch (e.type) {
			case EVENT_IMPORT_RINGTONE: {
				dOpenFile = OpenFileDialog.createAndShow(OpenFileDialog.OPEN_OPTIONS_FILTER_OUT_DOT_FILES, null, null, new Event(this, EVENT_LOAD_FILE), null, null);
				return true;
			}
			case EVENT_LOAD_FILE: {
				file = (File)e.argument;
				data = getBytesFromFile(file);
				if (data == null) return true;
				name = file.getName();
				nameTone(name);
				return true;
			}
			case EVENT_TONE_ADD_OK: {
				DEBUG.p("Tone Added.");
				populateListView();
				return true;
			}
			case EVENT_TONE_ADD_FAIL: {
				DEBUG.p("Tone Failed.");
				return true;
			}
			case EVENT_SAVE_RINGTONE: {
				ToneGallery.add(ToneGallery.Type.SEQUENCE, data, ringtoneName.getText(), ringtoneName.getText(), new Event(this, EVENT_TONE_ADD_OK), new Event(this, EVENT_TONE_ADD_FAIL), ToneOrigin.USER, ToneRights.FULL, true);
				return true;
			}
			case EVENT_DELETE_RINGTONE: {
				DEBUG.p("DELETIN!");
				dRangtonez.show();
				return true;
			}
			case EVENT_DELETEIT: {
				ToneGallery.remove(id);
				populateListView();
				return true;
			}
			case ABOUT: {
				AlertWindow about = getApplication().getAlert(ID_ABOUT, this);
				about.show();
				return true;
			}
			default:
			break;
		}
		return super.receiveEvent(e);
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