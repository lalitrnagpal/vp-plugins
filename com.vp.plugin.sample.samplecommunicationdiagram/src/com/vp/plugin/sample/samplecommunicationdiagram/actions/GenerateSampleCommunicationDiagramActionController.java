package com.vp.plugin.sample.samplecommunicationdiagram.actions;

import com.vp.plugin.*;
import com.vp.plugin.action.*;
import com.vp.plugin.diagram.*;
import com.vp.plugin.diagram.shape.*;
import com.vp.plugin.model.*;
import com.vp.plugin.model.factory.*;

public class GenerateSampleCommunicationDiagramActionController implements VPActionController {
	
	public void performAction(VPAction aAction) {
		
		DiagramManager lDiagramManager = ApplicationManager.instance().getDiagramManager();
		
		ICommunicationDiagramUIModel lDiagram = (ICommunicationDiagramUIModel) ApplicationManager.instance().getDiagramManager().createDiagram(DiagramManager.DIAGRAM_TYPE_COMMUNICATION_DIAGRAM);
		
		IModelElement lFrame = lDiagram.getRootFrame(true); // get (or create) the root frame of the the communication diagram
		
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
		
		// create a link between Actor and Lifeline
		IInteractionLifeLineLink lLink = lFactory.createInteractionLifeLineLink();
		lLink.getFromEnd().setModelElement(lActor);
		lLink.getToEnd().setModelElement(lLifeline);
		
		lLink.addMessage(lMessage1);
		lLink.addMessage(lMessage2);
		lLink.addMessage(lMessage3);
		lLink.addMessage(lMessage4);
		lLink.addMessage(lMessage5);
		lLink.addMessage(lMessage6);
		
		
		// show the actor/lifeline in diagram
		IShapeUIModel lActorShape = (IShapeUIModel) lDiagramManager.createDiagramElement(lDiagram, lActor);
		IShapeUIModel lLifelineShape = (IShapeUIModel) lDiagramManager.createDiagramElement(lDiagram, lLifeline);
		lActorShape.setLocation(100, 400);
		lLifelineShape.setLocation(400, 100);
		lActorShape.fitSize();
		lLifelineShape.fitSize();
		
		// show the link in diagram
		IDiagramElement lLinkConnector = lDiagramManager.createConnector(lDiagram, lLink, lActorShape, lLifelineShape, null);
		// show the messages' shape (Arrow) on link
		ICommunicationMessageUIModel lMessageShape1 = (ICommunicationMessageUIModel) lDiagramManager.createDiagramElement(lDiagram, ICommunicationDiagramUIModel.SHAPETYPE_COMMUNICATION_MESSAGE);
		ICommunicationReverseMessageUIModel lMessageShape2 = (ICommunicationReverseMessageUIModel) lDiagramManager.createDiagramElement(lDiagram, ICommunicationDiagramUIModel.SHAPETYPE_COMMUNICATION_REVERSE_MESSAGE);
		IShapeUIModel lMessageShape3 = (IShapeUIModel) lDiagramManager.createDiagramElement(lDiagram, ICommunicationDiagramUIModel.SHAPETYPE_COMMUNICATION_MESSAGE);
		IShapeUIModel lMessageShape4 = (IShapeUIModel) lDiagramManager.createDiagramElement(lDiagram, ICommunicationDiagramUIModel.SHAPETYPE_COMMUNICATION_REVERSE_MESSAGE);
		IShapeUIModel lMessageShape5 = (IShapeUIModel) lDiagramManager.createDiagramElement(lDiagram, ICommunicationDiagramUIModel.SHAPETYPE_COMMUNICATION_MESSAGE);
		IShapeUIModel lMessageShape6 = (IShapeUIModel) lDiagramManager.createDiagramElement(lDiagram, ICommunicationDiagramUIModel.SHAPETYPE_COMMUNICATION_REVERSE_MESSAGE);
		
		lMessageShape1.setMetaModelElement(lMessage1);
		lMessageShape2.setMetaModelElement(lMessage2);
		lMessageShape3.setMetaModelElement(lMessage3);
		lMessageShape4.setMetaModelElement(lMessage4);
		lMessageShape5.setMetaModelElement(lMessage5);
		lMessageShape6.setMetaModelElement(lMessage6);
		
		// group the arrows
		lMessageShape1.setMessageIds(new String[] {lMessageShape3.getId(), lMessageShape5.getId()});
		lMessageShape2.setMessageIds(new String[] {lMessageShape4.getId(), lMessageShape6.getId()});
		
		// add the arrows on link
		lLinkConnector.addChild(lMessageShape1);
		lLinkConnector.addChild(lMessageShape2);
		lLinkConnector.addChild(lMessageShape3);
		lLinkConnector.addChild(lMessageShape4);
		lLinkConnector.addChild(lMessageShape5);
		lLinkConnector.addChild(lMessageShape6);
		
		// set location and size of the arrow
		lMessageShape1.setLocation(100+30, 400-80);
		lMessageShape3.setLocation(100+30, 400-80);
		lMessageShape5.setLocation(100+30, 400-80);
		lMessageShape2.setLocation(400-80, 100+30);
		lMessageShape4.setLocation(400-80, 100+30);
		lMessageShape6.setLocation(400-80, 100+30);
		
		lMessageShape1.setSize(60, 60);
		lMessageShape2.setSize(60, 60);
		lMessageShape3.setSize(60, 60);
		lMessageShape4.setSize(60, 60);
		lMessageShape5.setSize(60, 60);
		lMessageShape6.setSize(60, 60);
		
		// set the location and size of the 'caption' of the arrow
		lMessageShape1.resetCaptionSize(); 
		lMessageShape2.resetCaptionSize();
		lMessageShape3.resetCaptionSize();
		lMessageShape4.resetCaptionSize();
		lMessageShape5.resetCaptionSize();
		lMessageShape6.resetCaptionSize();
		
		lMessageShape1.getCaptionUIModel().setX(lMessageShape1.getX()+lMessageShape1.getWidth()/2);
		lMessageShape2.getCaptionUIModel().setX(lMessageShape2.getX()+lMessageShape2.getWidth()/2);
		lMessageShape3.getCaptionUIModel().setX(lMessageShape3.getX()+lMessageShape3.getWidth()/2);
		lMessageShape4.getCaptionUIModel().setX(lMessageShape4.getX()+lMessageShape4.getWidth()/2);
		lMessageShape5.getCaptionUIModel().setX(lMessageShape5.getX()+lMessageShape5.getWidth()/2);
		lMessageShape6.getCaptionUIModel().setX(lMessageShape6.getX()+lMessageShape6.getWidth()/2);
		
		lMessageShape1.getCaptionUIModel().setY(lMessageShape1.getY()-30+(15*0));
		lMessageShape3.getCaptionUIModel().setY(lMessageShape3.getY()-30+(15*1));
		lMessageShape5.getCaptionUIModel().setY(lMessageShape5.getY()-30+(15*2));
		lMessageShape2.getCaptionUIModel().setY(lMessageShape2.getY()-30+(15*0));
		lMessageShape4.getCaptionUIModel().setY(lMessageShape4.getY()-30+(15*1));
		lMessageShape6.getCaptionUIModel().setY(lMessageShape6.getY()-30+(15*2));
		
		lLinkConnector.resetCaption();
		
		// open the diagram
		lDiagramManager.openDiagram(lDiagram);
	}
	
	public void update(VPAction aAction) {
	}
	
}