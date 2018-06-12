package com.vp.plugin.aws.shape.group;

import java.awt.*;
import java.awt.geom.*;

import com.vp.plugin.aws.shape.*;
import com.vp.plugin.diagram.*;

public class SecurityGroupController extends GroupShapeController {

	private Stroke _secondStroke;
	public SecurityGroupController() {
		super(null, new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {7, 3}, 0));
		_secondStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {3, 7}, 3);
	}
	
	@Override
	public void initDefaultProperties(IDiagramElement aDiagramElement) {
		super.initDefaultProperties(aDiagramElement);
		
		aDiagramElement.getLineModel().setColor(Color.red);
	}
	
	@Override
	public void drawShape(Graphics2D aGraphics2D, Paint aLineColor, Paint aFillColor, Stroke aStroke, VPShapeInfo aShapeInfo) {
//		super.drawShape(aGraphics2D, aLineColor, aFillColor, aStroke, aShapeInfo);
		
		Rectangle2D lBounds = aShapeInfo.getBounds();
		
		super.drawBorder(aGraphics2D, lBounds, aFillColor, 
				new GroupShapeController.BorderLine(_secondStroke, Color.black), 
				new GroupShapeController.BorderLine(_stroke, aLineColor)
		);
	}

}
