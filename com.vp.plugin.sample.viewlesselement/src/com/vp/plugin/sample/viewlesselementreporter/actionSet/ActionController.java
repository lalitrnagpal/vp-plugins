package com.vp.plugin.sample.viewlesselementreporter.actionSet;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ViewManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.sample.viewlesselementreporter.ViewlessElementReporter;

public class ActionController implements VPActionController {

	public void performAction(VPAction action) {
		
		// get the view manager and the parent component for modal the dialog.
		ViewManager viewManager = ApplicationManager.instance().getViewManager();
		Component parentFrame = viewManager.getRootFrame();
		
		IModelElement[] lModelElements = ApplicationManager.instance().getProjectManager().getProject().toAllLevelModelElementArray();
		
		// popup a file chooser for choosing the output file
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {
		
			public String getDescription() {
				return "*.htm";
			}
		
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().toLowerCase().endsWith(".htm");
			}
		
		});
		fileChooser.showSaveDialog(parentFrame);
		if (fileChooser.getSelectedFile()!=null && !fileChooser.getSelectedFile().isDirectory()) {
			// generate
			String result = ViewlessElementReporter.getDefaults().generate(lModelElements, fileChooser.getSelectedFile());
			// show the generation result
			viewManager.showMessageDialog(parentFrame, result);
		}
	}

	public void update(VPAction action) {
	}

}
