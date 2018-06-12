package com.vp.plugin.aws.shape.group;

import java.awt.*;

import com.vp.plugin.aws.shape.*;
import com.vp.plugin.diagram.*;

public class AvailabilityZoneController extends GroupShapeController {

	public AvailabilityZoneController() {
		super(null, new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {1, 3, 9, 3}, 0));
	}
	
	@Override
	public void initDefaultProperties(IDiagramElement aDiagramElement) {
		super.initDefaultProperties(aDiagramElement);
		
		aDiagramElement.getLineModel().setColor(new Color(248, 152, 31));
	}

}
