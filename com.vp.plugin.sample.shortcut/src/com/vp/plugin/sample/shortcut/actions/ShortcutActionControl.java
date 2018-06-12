package com.vp.plugin.sample.shortcut.actions;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;

public class ShortcutActionControl implements VPActionController {

	@Override
	public void performAction(VPAction arg0) {
		ApplicationManager.instance().getViewManager().showMessage("Plugin triggered");		
	}

	@Override
	public void update(VPAction arg0) {
		// TODO Auto-generated method stub
		
	}

}
