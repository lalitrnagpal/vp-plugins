package com.vp.plugin.sample.projectopened;

import java.util.*;

import com.vp.plugin.*;
import com.vp.plugin.model.*;

public class ProjectOpenedPlugin implements VPPlugin {

	public void loaded(VPPluginInfo aInfo) {
		
		IProject lProject = ApplicationManager.instance().getProjectManager().getProject();
		
		lProject.addProjectListener(new IProjectListener() {
			public void projectNewed(IProject aProject) {}
			public void projectRenamed(IProject aProject) {}
			public void projectPreSave(IProject aProject) {}
			public void projectSaved(IProject aProject) {}
			public void projectOpened(IProject aProject) {}
			public void projectAfterOpened(IProject aProject) {
				// Project opened
				
				
				int lModelElementCount = 0;
				{
					/*
					 * I am sorry that, IProject only provide function to get "All Level" model element count.
					 * So that, you need to count the number of root model elements by modelElementIterator()
					 * 
					 * === More Details ===
					 * - what is "All Level"?
					 * There are "Root" and "All Level"
					 * "Root" means the model elements have not 'parent model element'
					 * "All Level" means 'all' model elements even they have parent.
					 * However, the 'all' not really mean all. some model element still not be included in 'all', 
					 * e.g. Class's Attribute/Operation. 
					 * Since those model elements are composite in its parent, so, they are not included in All Level 
					 */
//					aProject.allLevelModelElementCount();
					
					Iterator lIter = aProject.modelElementIterator();
					while (lIter.hasNext()) {
						lIter.next();
						
						lModelElementCount++;
					}
				}
				
				ViewManager lViewManager = ApplicationManager.instance().getViewManager();
				lViewManager.showMessage("hello plugin! there are currently " + lModelElementCount  + " models in this project.");
				
			}
		});
	}

	public void unloaded() {
	}
	
}