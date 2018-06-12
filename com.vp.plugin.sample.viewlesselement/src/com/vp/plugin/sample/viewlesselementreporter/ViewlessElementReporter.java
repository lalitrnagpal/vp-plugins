package com.vp.plugin.sample.viewlesselementreporter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.vp.plugin.VPPlugin;
import com.vp.plugin.VPPluginInfo;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.property.IModelProperty;

public class ViewlessElementReporter implements VPPlugin {
	
	final private String[] ELEMENT_IGNORE_LIST = new String[]{
			"ProjectFillColorModel", 
			"BMMInfluencerCategory",
			"ProjectElementFontModel",
			"BrainstormNoteTag",
			"BRKeyword",
			"DataType",
			"WSDLContainer",
			"BRKeywords",
			"ChartTypeContainer",
			"BMMInfluencerCategoryContainer",
			"BMMBusinessRuleEnforcementLevelContainer",	
			"BMMAssetCategoryContainer",
			"BMMAssessmentCategoryContainer",
			"WSDLContainer",
			"PMStatusContainer",
			"PMDifficultyContainer"	,
			"PMPriorityContainer",
			"PMVersionContainer",
			"PMIterationContainer",
			"PMPhaseContainer",
			"PMDisciplineContainer",
			"PMAuthorContainer",
			"ProjectFormat",
			"BrainstormNoteTagContainer",
			"UeXcelerInfo",
			"DBLookupContainer",
			"ViewpointLookup",
			"PMStatus",
			"PMDifficulty",
			"PMPriority",
			"PMVersion",
			"PMIteration",
			"PMPhase",
			"PMDiscipline",
			"ValueSpecification",
			"ChartType",
			"BMMBusinessRuleEnforcementLevel",
			"BMMAssetCategory",
			"BMMAssessmentCategory",
			"ProjectDefaultLineModel",
			"Stereotype",
			"WFDState",
			"ModelRelationshipContainer",
			"WFDStateContainer",
			"UserStory"
	};
	
	public static final String HTML_TEMPLATE = "<html><head><title>VP Plugin - Viewle</title></head><body>${body}</body></html>";
	
	/** the default instance of this plugin **/
	private static ViewlessElementReporter defaults;
	
	/**
	 * get the default instance of this plugin
	 */
	public static ViewlessElementReporter getDefaults() {
		return defaults;
	}
	
	public ViewlessElementReporter() {
		defaults = this;
	}

	public void loaded(VPPluginInfo arg0) {
	}

	public void unloaded() {
	}
	
	/**
	 * generate the html file to given output path
	 * @param modelElements the model elements of the entire project to generate the html page
	 * @param outputPath the output path of the html page
	 * @return the result of the generation
	 */
	public String generate(IModelElement[] modelElements, File outputPath) {
		// the buffer for storing the viewless elements
		ArrayList<IModelElement> viewlessElements = new ArrayList<IModelElement>();
		
		for (int i = 0; i < modelElements.length; i++) {
			IModelElement modelElement = modelElements[i];
			if (modelElement.getDiagramElements().length == 0)
				viewlessElements.add(modelElement);
		}
		
		// generate the html content
		String content = "<table border=\"1\" width=\"100%\"><tr><th>Element Name</th><th>Element Type</th><th>PM Author</th></tr>";
		for (int i=0;i<viewlessElements.size();i++){
			IModelElement viewlessElement = ((IModelElement)viewlessElements.get(i));
			String type = viewlessElement.getModelType();
			if (!ignoreElemenet(type)){
				String elementName = viewlessElement.getName();
				String pmAuthor = "";
				IModelProperty pmAuthorProp = viewlessElement.getModelPropertyByName(IClass.PROP_PM_AUTHOR);
				if (pmAuthorProp != null){
					String pmAuthorModelElement = pmAuthorProp.getValueAsString();
					if (pmAuthorModelElement != null)
						pmAuthor = pmAuthorModelElement;
				}
				
				content += "<tr><td>"+elementName+"</td><td>"+type+"</td><td>"+pmAuthor+"</td><tr>";	
			}
		}
		content += "</table>";
		
		// write to file
		try {
			FileWriter writer = new FileWriter(outputPath);
			System.out.println(outputPath.getAbsolutePath());
			writer.write(content);
			writer.close();
			return "Success! HTML generated to "+outputPath.getAbsolutePath();
		} catch (IOException e) {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream(128);
			e.printStackTrace(new PrintStream(buffer));
			return buffer.toString();
		}
	}
	
	private boolean ignoreElemenet(String elementType){
		for (int i = 0; i < ELEMENT_IGNORE_LIST.length; i++) {
			if (ELEMENT_IGNORE_LIST[i].equals(elementType)) return true;
		}
		return false;
	}

}
