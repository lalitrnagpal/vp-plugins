package com.vp.plugin.sample.diagramtoolbar.actions;

import com.vp.plugin.*;
import com.vp.plugin.diagram.*;
import com.vp.plugin.model.*;
import com.vp.plugin.model.factory.*;

public class CreateSingletonClassActionController implements VPShapeModelCreationController {
	
	public String getShapeType() {
		return IClassDiagramUIModel.SHAPETYPE_CLASS;
	}
	
	public void shapeCreated(IShapeUIModel aShape) {
		IDiagramUIModel lDiagram = ApplicationManager.instance().getDiagramManager().getActiveDiagram();
		
		// create model
		IClass lClass = createSingletonClass(lDiagram);
		
		aShape.setModelElement(lClass);
	}
	
	private IClass createSingletonClass(IDiagramUIModel aDiagram) {
		IClass lClass; // class will be created in this action
		{
			IModelElement[] lPeers; 
			IModelElement lOwner = aDiagram.getParentModel();
			if (lOwner != null) {
				// create Class in Owner
				lPeers = lOwner.toChildArray(IModelElementFactory.MODEL_TYPE_CLASS);
				lClass = (IClass) lOwner.createChild(IModelElementFactory.MODEL_TYPE_CLASS);
			}
			else {
				// create Class in Root
				lPeers = ApplicationManager.instance().getProjectManager().getProject().toModelElementArray(IModelElementFactory.MODEL_TYPE_CLASS);
				lClass = IModelElementFactory.instance().createClass();
			}
			
			String lClassName = "SingletonClass";
			int lIndex = 2;
			boolean lLoop = true;
			while (lLoop) {
				lLoop = false;
				int lCount = lPeers == null ? 0 : lPeers.length;
				for (int i = 0; i < lCount; i++) {
					String lName = lPeers[i].getNickname();
					if (lClassName.equals(lName)) {
						lClassName = "SingletonClass" + lIndex;
						lIndex++;
						lLoop = true;
						i = lCount;
					}
				}
			}
			lClass.setNickname(lClassName);
		}
		
		{
			// create constructor: "private SingletonClass()"
			IOperation lOperation = lClass.createOperation();
			lOperation.setNickname(lClass.getName());
			lOperation.setVisibility(IOperation.VISIBILITY_PRIVATE); // private
			{
				// set 'constructor' in different 'languages' need to use different CodeDetail
				// e.g. 
				//  for Java, need not to set
				//  for .Net (C#/VB), need to set 'constructor' in DotNetCodeDetail (as below)
				IDotNetOperationCodeDetail lCodeDetail = IModelElementFactory.instance().createDotNetOperationCodeDetail();
				lOperation.setDotnetCodeDetail(lCodeDetail);
				
				lCodeDetail.setMethodKind(IDotNetOperationCodeDetail.METHOD_KIND_CONSTRUCTOR); // 'constructor' in .Net
			}
		}
		{
			// create attribute: "private static final SingletonClass _instance = new SingletonClass()"
			IAttribute lAttribute = lClass.createAttribute();
			lAttribute.setNickname("_instance");
			lAttribute.setVisibility(IAttribute.VISIBILITY_PRIVATE); // private
			lAttribute.setScope(IAttribute.SCOPE_CLASSIFIER); // static
			{
				// to set 'final' in different 'languages' require the use of different CodeDetail
				// e.g. 
				//  for Java, set 'final' in JavaCodeDetail (as below)
				//  for .Net (C#/VB), set 'final' in DotNetCodeDetail
				IJavaAttributeCodeDetail lCodeDetail = IModelElementFactory.instance().createJavaAttributeCodeDetail();
				lAttribute.setJavaDetail(lCodeDetail);
				
				lCodeDetail.setJavaFinal(true); // 'final' in Java
			}
			lAttribute.setType(lClass); // type
			lAttribute.setInitialValue("new " + lClass.getName() + "()"); // new the class
		}
		{
			// create operation: "public static SingletonClass instance()"
			IOperation lOperation = lClass.createOperation();
			lOperation.setNickname("instance");
			lOperation.setVisibility(IOperation.VISIBILITY_PUBLIC); // public
			lOperation.setScope(IOperation.SCOPE_CLASSIFIER); // static
			lOperation.setReturnType(lClass);
		}
		return lClass;
	}
}