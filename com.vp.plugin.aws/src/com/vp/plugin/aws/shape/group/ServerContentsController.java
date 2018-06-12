package com.vp.plugin.aws.shape.group;

import java.awt.*;

import com.vp.plugin.aws.shape.*;
import com.vp.plugin.diagram.*;

public class ServerContentsController extends GroupShapeController {

	public ServerContentsController() {
		super(null, null);
	}
	
	@Override
	public void initDefaultProperties(IDiagramElement aDiagramElement) {
		super.initDefaultProperties(aDiagramElement);
		
		((IShapeUIModel) aDiagramElement).getFillColor().setColor1(new Color(243, 243, 243));
		((IShapeUIModel) aDiagramElement).getFillColor().setTransparency(0);
	}

}
