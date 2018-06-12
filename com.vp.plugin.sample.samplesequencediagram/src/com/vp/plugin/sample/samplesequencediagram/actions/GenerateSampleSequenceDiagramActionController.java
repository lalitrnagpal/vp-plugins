package com.vp.plugin.sample.samplesequencediagram.actions;

import java.awt.*;
import java.awt.event.*;

import com.vp.plugin.*;
import com.vp.plugin.action.*;
import com.vp.plugin.diagram.*;
import com.vp.plugin.model.*;
import com.vp.plugin.model.factory.*;

public class GenerateSampleSequenceDiagramActionController implements VPContextActionController {
	
	public void performAction(VPAction aAction, VPContext aContext, ActionEvent aE) {
		IInteractionDiagramUIModel lDiagram = (IInteractionDiagramUIModel) aContext.getDiagram();
		
		IModelElement lFrame = lDiagram.getRootFrame(true); // get (or create) the root frame of the the sequence diagram
		
		// create actor/lifeline inside the root frame.
		IInteractionActor lActor = (IInteractionActor) lFrame.createChild(IModelElementFactory.MODEL_TYPE_INTERACTION_ACTOR);
		IInteractionLifeLine lLifeline = (IInteractionLifeLine) lFrame.createChild(IModelElementFactory.MODEL_TYPE_INTERACTION_LIFE_LINE);
		lActor.setNickname("User");
		lLifeline.setNickname("ATM");
		
		// create messages between actor and lifeline
		IModelElementFactory lFactory = IModelElementFactory.instance();
		// 1: Insert card
		IMessage lMessage1 = lFactory.createMessage();
		lMessage1.setFrom(lActor);
		lMessage1.setTo(lLifeline);
		lMessage1.setNickname("Insert card");
		// 2: Display welcome...
		IMessage lMessage2 = lFactory.createMessage();
		lMessage2.setFrom(lLifeline);
		lMessage2.setTo(lActor);
		lMessage2.setNickname("Display welcome...");
		lMessage2.setActionType(lFactory.createActionTypeReturn()); // Return Message
		// 3: Enter pin...
		IMessage lMessage3 = lFactory.createMessage();
		lMessage3.setFrom(lActor);
		lMessage3.setTo(lLifeline);
		lMessage3.setNickname("Enter pin...");
		// 4: Show services...
		IMessage lMessage4 = lFactory.createMessage();
		lMessage4.setFrom(lLifeline);
		lMessage4.setTo(lActor);
		lMessage4.setNickname("Show services...");
		lMessage4.setActionType(lFactory.createActionTypeReturn()); // Return Message
		// 5: Click "Withdraw Money" and enter the...
		IMessage lMessage5 = lFactory.createMessage();
		lMessage5.setFrom(lActor);
		lMessage5.setTo(lLifeline);
		lMessage5.setNickname("Click \"Withdraw Money\" and enter the...");
		// 6: Dispense...
		IMessage lMessage6 = lFactory.createMessage();
		lMessage6.setFrom(lLifeline);
		lMessage6.setTo(lActor);
		lMessage6.setNickname("Dispense...");
		lMessage6.setActionType(lFactory.createActionTypeReturn()); // Return Message
		
		
		DiagramManager lDiagramManager = ApplicationManager.instance().getDiagramManager();
		// show the actor/lifeline in diagram
		IShapeUIModel lActorShape = (IShapeUIModel) lDiagramManager.createDiagramElement(lDiagram, lActor);
		IShapeUIModel lLifelineShape = (IShapeUIModel) lDiagramManager.createDiagramElement(lDiagram, lLifeline);
		lActorShape.setLocation(100, 40);
		lLifelineShape.setLocation(400, 40);
		lActorShape.fitSize();
		lLifelineShape.fitSize();
		
		// show the messages in diagram
		IDiagramElement lMessageConnector1 = lDiagramManager.createConnector(lDiagram, lMessage1, lActorShape, lLifelineShape, new Point[] {new Point(100, 120), new Point(400, 120)});
		IDiagramElement lMessageConnector2 = lDiagramManager.createConnector(lDiagram, lMessage2, lLifelineShape, lActorShape, new Point[] {new Point(100, 150), new Point(400, 150)});
		IDiagramElement lMessageConnector3 = lDiagramManager.createConnector(lDiagram, lMessage3, lActorShape, lLifelineShape, new Point[] {new Point(100, 180), new Point(400, 180)});
		IDiagramElement lMessageConnector4 = lDiagramManager.createConnector(lDiagram, lMessage4, lLifelineShape, lActorShape, new Point[] {new Point(100, 210), new Point(400, 210)});
		IDiagramElement lMessageConnector5 = lDiagramManager.createConnector(lDiagram, lMessage5, lActorShape, lLifelineShape, new Point[] {new Point(100, 240), new Point(400, 240)});
		IDiagramElement lMessageConnector6 = lDiagramManager.createConnector(lDiagram, lMessage6, lLifelineShape, lActorShape, new Point[] {new Point(100, 270), new Point(400, 270)});
		
		lActorShape.setHeight(270);
		lLifelineShape.setHeight(270);
		
		lMessageConnector1.resetCaption();
		lMessageConnector2.resetCaption();
		lMessageConnector3.resetCaption();
		lMessageConnector4.resetCaption();
		lMessageConnector5.resetCaption();
		lMessageConnector6.resetCaption();
	}
	
	public void update(VPAction aAction, VPContext aContext) {
	}
	
}