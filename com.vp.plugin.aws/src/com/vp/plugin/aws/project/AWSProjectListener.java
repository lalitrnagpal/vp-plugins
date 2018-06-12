package com.vp.plugin.aws.project;

import com.vp.plugin.diagram.*;
import com.vp.plugin.model.*;

public class AWSProjectListener implements IProjectListener {

	@Override
	public void projectAfterOpened(IProject aProject) {
		IDiagramUIModel[] lDiagrams = aProject.toDiagramArray();
		if (lDiagrams != null) {
			for (IDiagramUIModel lDiagram : lDiagrams) {
				if (lDiagram instanceof IDeploymentDiagramUIModel) {
					/*
					 * com.vp.plugin.aws.shape.database.DynamoDBEmailNotification is wrong.
					 * fixed to be
					 * com.vp.plugin.aws.shape.database.DynamoDBTable
					 */
					IDiagramElement[] lShapes = lDiagram.toDiagramElementArray("com.vp.plugin.aws.shape.database.DynamoDBEmailNotification");
					if (lShapes != null) {
						for (IDiagramElement lShape : lShapes) {
							try {
								/*
								 * Try-catch on creating the new shape, because if running in Viewer edition, creating shape is not allowed and UnsupportedException will be thrown.
								 */
								IModelElement lModelElement = lShape.getModelElement();//IModelElementFactory.instance().createPluginModelElement();
								IShapeUIModel lShape2 = (IShapeUIModel) lDiagram.createDiagramElement("com.vp.plugin.aws.shape.database.DynamoDBTable");
								lShape2.setModelElement(lModelElement);
								lShape2.setBounds(lShape.getX(), lShape.getY(), lShape.getWidth(), lShape.getHeight());
								
								{
									IConnectorUIModel[] lConnectors = lShape.toFromConnectorArray();
									if (lConnectors != null) {
										for (IConnectorUIModel lConnector : lConnectors) {
											lConnector.setFromShape(lShape2);
										}
									}
								}
								{
									IConnectorUIModel[] lConnectors = lShape.toToConnectorArray();
									if (lConnectors != null) {
										for (IConnectorUIModel lConnector : lConnectors) {
											lConnector.setToShape(lShape2);
										}
									}
								}
								
								if (lShape.isMasterView()) {
									lShape2.toBeMasterView();
								}
								lShape.deleteViewOnly();
								
							} catch (Exception lE) {
								return;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void projectNewed(IProject aArg0) {
	}

	@Override
	public void projectOpened(IProject aArg0) {
	}

	@Override
	public void projectPreSave(IProject aArg0) {
	}

	@Override
	public void projectRenamed(IProject aArg0) {
	}

	@Override
	public void projectSaved(IProject aArg0) {
	}

}
