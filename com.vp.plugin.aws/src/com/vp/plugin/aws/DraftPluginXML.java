package com.vp.plugin.aws;

import java.io.*;
import java.util.*;

public class DraftPluginXML {
	
	public static void main(String[] args) {
		
		StringBuffer lBuffer = new StringBuffer();
		
//		final String lSeparator = "\n";
		final String lSeparator = " ";
		
		// START
		{
			final String lIndent1s = ""; // tab x 0
			final String lIndent1a = "" + "		"; // tab x 0+2
			final String lIndent2s = "	"; // tab x 1
			final String lIndent3s = "		"; // tab x 2
			
			lBuffer.append(lIndent1s).append("<plugin").append("\n");
			lBuffer.append(lIndent1a).append("id=\"com.vp.plugin.aws\"").append("\n");
			lBuffer.append(lIndent1a).append("name=\"AWS Plugin\"").append("\n");
			lBuffer.append(lIndent1a).append("description=\"AWS Plugin\"").append("\n");
			lBuffer.append(lIndent1a).append("provider=\"Visual Paradigm\"").append("\n");
			lBuffer.append(lIndent1a).append("requiredVersion=\"10.2\"").append("\n");
			lBuffer.append(lIndent1a).append("class=\"com.vp.plugin.aws.AWSPlugin\"").append(">").append("\n");
			
			// 2.1 (support all shapes in powerpoint + visio stencil)
			lBuffer.append(lIndent1s).append("<!-- aws plugin version: 2.1 (2015-10-26) -->").append("\n").append("\n");
			
			lBuffer.append(lIndent2s).append("<actionSets>").append("\n");
			
			lBuffer.append(lIndent3s).append("<actionSet id=\"com.vp.plugin.aws.DiagramActionSet\">").append("\n");
		}
		
		final Group[] lGroups = initGroups();
		{
			final String lIndent1s = "			"; // tab x 3
			final String lIndent1a = "";//"			" + "		"; // tab x 3+2
			
			String lPreviousRelativeId;
			// categories
			{
				lBuffer.append(lIndent1s).append("<editorToolbarCategory").append(lSeparator);
				lBuffer.append(lIndent1a).append("id=\"com.vp.plugin.aws.actions.AWSCategory\"").append(lSeparator);
				lBuffer.append(lIndent1a).append("label=\"AWS\"").append(lSeparator);
				lBuffer.append(lIndent1a).append("icon=\"icons/shape/nonservicespecific/AWSCloud.png\"").append(lSeparator);
				lBuffer.append(lIndent1a).append("editorToolbarPath=\"com.vp.diagram.DeploymentDiagram/com.vp.category.Default\"").append("/>").append("\n");
				
				lPreviousRelativeId = "#";
			}
			
			// groups
			for (Group lGroup : lGroups) {
				lBuffer.append(lIndent1s).append("<editorToolbarButtonGroup").append(lSeparator);
				lBuffer.append(lIndent1a).append("id=\"com.vp.plugin.aws.actions."+lGroup.groupShortId+"\"").append(lSeparator);
				lBuffer.append(lIndent1a).append("editorToolbarPath=\"com.vp.diagram.DeploymentDiagram/com.vp.plugin.aws.actions.AWSCategory/"+lPreviousRelativeId+"\"").append("/>").append("\n");
				
				lPreviousRelativeId = "com.vp.plugin.aws.actions."+lGroup.groupShortId;
			}
			
			// links's group
			{
				lBuffer.append(lIndent1s).append("<editorToolbarButtonGroup").append(lSeparator);
				lBuffer.append(lIndent1a).append("id=\"com.vp.plugin.aws.actions.LinksGroup\"").append(lSeparator);
				lBuffer.append(lIndent1a).append("editorToolbarPath=\"com.vp.diagram.DeploymentDiagram/com.vp.plugin.aws.actions.AWSCategory/"+lPreviousRelativeId+"\"").append("/>").append("\n");
				
				lPreviousRelativeId = "com.vp.plugin.aws.actions.LinksGroup";
			}
		}
		
		
		// generate constants
		System.out.println("public static class ShapeTypes {");
		
		// groups - shapes
		Collection<Shape> lShapes = new ArrayList<Shape>();
		for (Group lGroup : lGroups) {
			
			final String lIndent1s = "			"; // tab x 3
			lBuffer.append(lIndent1s).append("<!-- "+ lGroup.groupShortId +" -->").append("\n");
			
			String lGroupId = "com.vp.plugin.aws.actions."+lGroup.groupShortId;
			String lPreviousRelativeId = "#";
			
			System.out.println("\tpublic static class "+ (lGroup.groupShortId.endsWith("Group") ? lGroup.groupShortId.substring(0, lGroup.groupShortId.length()-"Group".length()) : lGroup.groupShortId) +" {");
			System.out.println("\t\tpublic static final String");
			
			int lCount = lGroup.shapes.length;
			int lIndex = -1;
			for (Shape lShape : lGroup.shapes) {
				lIndex++;
				
				lPreviousRelativeId = createShapeActions(lGroupId, lGroup, lShape, lPreviousRelativeId, lBuffer);
				lShapes.add(lShape);
				
				System.out.println("\t\t\t\t" + lShape.imageFileName + " = \"" + lShape.getId() + "\"" + (lIndex==lCount-1 ? ";" : ", "));
			}
			System.out.println("\t}");
			
		}
		// groups - links
		Connector[] lConnectors = initConnectors();
		createLinkActions(lShapes.toArray(new Shape[lShapes.size()]), lConnectors, lBuffer);
		{
			System.out.println("\tpublic static class Links {");
			System.out.println("\t\tpublic static final String");
			
			int lCount = lConnectors.length;
			int lIndex = -1;
			for (Connector lConnector : lConnectors) {
				lIndex++;
				
				System.out.println("\t\t\t\t" + lConnector.id.substring(lConnector.id.lastIndexOf(".")+1) + " = \"" + lConnector.id + "\"" + (lIndex==lCount-1 ? ";" : ", "));
			}
			System.out.println("\t}");
		}
		
		System.out.println("}");
		
		
		// END
		{
			final String lIndent1s = ""; // tab x 0
			final String lIndent2s = "	"; // tab x 1
			final String lIndent3s = "		"; // tab x 2
			
			lBuffer.append(lIndent3s).append("</actionSet>").append("\n");
			lBuffer.append(lIndent2s).append("</actionSets>").append("\n");
			lBuffer.append(lIndent1s).append("</plugin>").append("\n");
		}
		
		File lPluginXml = new File("plugin.xml");
		lPluginXml.delete();
		
		try {
			FileOutputStream lOs = new FileOutputStream(lPluginXml);
			lOs.write(lBuffer.toString().getBytes());
			lOs.close();
			
		} catch (Exception lE) {
			lE.printStackTrace();
		}
		
	}
	
	private static String createShapeActions(
			String lGroupId, 
			Group aGroup, 
			Shape aShape, 
			
			String lPreviousRelativeId, 
			
			StringBuffer lBuffer
	) {
		
//		final String lSeparator = "\n";
		final String lSeparator = " ";
	
		final String lIndent1s = "			"; // tab x 3
		final String lIndent1a = "";//"			" + "		"; // tab x 3+2
		final String lIndent2s = "				";
		final String lIndent2a = "";//"				" + "		";
		
		String lId = "com.vp.plugin.aws.shape."+aGroup.folder+"."+aShape.imageFileName;
		String lShapeType = "com.vp.plugin.aws.shape."+ aGroup.folder + "." + aShape.imageFileName;
		String lClassName = "com.vp.plugin.aws.shape."+ aGroup.folder + "." + aShape.imageFileName + "Controller";
		
		lBuffer.append(lIndent1s).append("<action").append(lSeparator);
		lBuffer.append(lIndent1a).append("id=\""+ lId +"\"").append(lSeparator);
		lBuffer.append(lIndent1a).append("actionType=\"shapeAction\"").append(lSeparator);
		lBuffer.append(lIndent1a).append("label=\""+ aGroup.groupName + " - " + aShape.shapeName +"\"").append(lSeparator);
		lBuffer.append(lIndent1a).append("tooltip=\""+ aGroup.groupName + " - " + aShape.shapeName +"\"").append(lSeparator);
		lBuffer.append(lIndent1a).append("icon=\"icons/shape/"+ aGroup.folder + "/" + aShape.imageFileName +".png\"").append(lSeparator);
		lBuffer.append(lIndent1a).append("editorToolbarPath=\"com.vp.diagram.DeploymentDiagram/com.vp.plugin.aws.actions.AWSCategory/"+ lGroupId+"/"+lPreviousRelativeId +"\"").append(">").append("\n");
		
		lBuffer.append(lIndent2s).append("<shapeCreatorInfo").append(lSeparator);
		lBuffer.append(lIndent2a).append("shapeType=\""+ lShapeType +"\"").append(lSeparator);
		lBuffer.append(lIndent2a).append("defaultWidth=\"70\"").append(lSeparator); // default 70x70 (https://vp.vpository.com/tasifier.jsp#task=20839)
		lBuffer.append(lIndent2a).append("defaultHeight=\"70\"").append(lSeparator);
		lBuffer.append(lIndent2a).append("multilineCaption=\"true\"").append(lSeparator); // support multiline (https://vp.vpository.com/tasifier.jsp#task=20841)
		lBuffer.append(lIndent2a).append("captionStyle=\"outsideSouth\"").append(lSeparator);
		lBuffer.append(lIndent2a).append("resizable=\"true\"").append(lSeparator);
		lBuffer.append(lIndent2a).append("controllerClass=\""+ lClassName +"\"").append("/>").append("\n");
		
		lBuffer.append(lIndent1s).append("</action>").append("\n");
		
		// validate
		// - png
		File lPngs = new File("icons/shape"); // pngs
		File lPngsSub = new File(lPngs, aGroup.folder);
		File lPng = new File(lPngsSub, aShape.imageFileName+".png");
		if (! lPng.exists()) {
			System.err.println(">>> NOT FOUND (PNG): " + lPng);
		}
		if ("group".equals(aGroup.folder)) {
			// group really no svg
		}
		else {
			// - svg
			File lSvgs = new File("resources/shape"); // svgs
			File lSvgsSub = new File(lSvgs, aGroup.folder);
			File lSvg = new File(lSvgsSub, aShape.imageFileName+".svg");
			if (! lSvg.exists()) {
				System.err.println(">>> NOT FOUND (SVG): " + lSvg);
			}
		}
		
		if ("group".equals(aGroup.folder)) {
			// group really implemented their classes manually
		}
		else {
			// class
			{
				final String lCodeIndent1s = ""; // tab x 0
				final String lCodeIndent1a = "" + "	"; // tab x 0+1
				final String lCodeIndent1c = "" + "		"; // tab x 0+2
				
				StringBuffer lR = new StringBuffer();
				lR.append(lCodeIndent1s).append("package com.vp.plugin.aws.shape."+aGroup.folder+";").append("\n");
				lR.append(lCodeIndent1s).append("").append("\n");
				lR.append(lCodeIndent1s).append("import com.vp.plugin.aws.shape.*;").append("\n");
				lR.append(lCodeIndent1s).append("import java.io.*;").append("\n");
				lR.append(lCodeIndent1s).append("").append("\n");
				lR.append(lCodeIndent1s).append("public class "+ aShape.imageFileName+"Controller" +" extends SVGShapeController {").append("\n");
				lR.append(lCodeIndent1a).append("").append("\n");
				lR.append(lCodeIndent1a).append("public "+ aShape.imageFileName+"Controller" +"() {").append("\n");
				lR.append(lCodeIndent1c).append("super(\"resources\"+File.separator+\"shape\"+File.separator+\""+ aGroup.folder+"\"+File.separator+\""+aShape.imageFileName+".svg" +"\");").append("\n");
				lR.append(lCodeIndent1a).append("}").append("\n");
				lR.append(lCodeIndent1s).append("}").append("\n");
				
				
				try {
					FileOutputStream lOs = new FileOutputStream(new File("src/com/vp/plugin/aws/shape/"+aGroup.folder+"/"+aShape.imageFileName+"Controller.java"));
					lOs.write(lR.toString().getBytes());
					lOs.close();
					
				} catch (Exception lE) {
					lE.printStackTrace();
				}
			}
		}
		
		aShape.setId(lId);
		
		return lId;
	}
	private static void createLinkActions(Shape[] aShapes, Connector[] aConnectors, StringBuffer aBuffer) {
		
//		final String lSeparator = "\n";
		final String lSeparator = " ";
	
		final String lIndent1s = "			"; // tab x 3
		final String lIndent1a = "";//"			" + "		"; // tab x 3+2
		final String lIndent2s = "				";
		final String lIndent2a = "";//"				" + "		";
		final String lIndent3s = "					";
		final String lIndent4s = "						";
		
		/*
			<action
					id="com.vp.plugin.aws.connector.general.DottedDirectedLink"
					actionType="connectorAction"
					label="General - Dotted Directed Link"
					tooltip="General - Dotted Directed Link"
					icon="icons/connector/general/dottedDirectedLink.png"
					editorToolbarPath="com.vp.diagram.DeploymentDiagram/com.vp.plugin.aws.shape.compute.ElasticIP">
				<connectorCreatorInfo
						shapeType="com.vp.plugin.aws.connector.general.DottedDirectedLink"
						fromArrowHeadStyle="None"
						fromArrowHeadSize="large"
						toArrowHeadStyle="SolidArrow2"
						toArrowHeadSize="large"
						dashes="7,10"
						connectorStyle="oblique">
					<connectionRules>
		 */
		
		String lPreviousRelativeId = "#";
		
		for (Connector lConnector : aConnectors) {
			aBuffer.append(lIndent1s).append("<action").append(lSeparator);
			aBuffer.append(lIndent1a).append("id=\""+ lConnector.id +"\"").append(lSeparator);
			aBuffer.append(lIndent1a).append("actionType=\"connectorAction\"").append(lSeparator);
			aBuffer.append(lIndent1a).append("label=\""+ lConnector.name +"\"").append(lSeparator);
			aBuffer.append(lIndent1a).append("tooltip=\""+ lConnector.name +"\"").append(lSeparator);
			aBuffer.append(lIndent1a).append("icon=\""+ lConnector.icon +"\"").append(lSeparator);
			aBuffer.append(lIndent1a).append("editorToolbarPath=\"com.vp.diagram.DeploymentDiagram/com.vp.plugin.aws.actions.AWSCategory/com.vp.plugin.aws.actions.LinksGroup/"+ lPreviousRelativeId +"\"").append(">").append("\n");
			
			{
				aBuffer.append(lIndent2s).append("<connectorCreatorInfo").append(lSeparator);
				aBuffer.append(lIndent2a).append("shapeType=\""+ lConnector.id +"\"").append(lSeparator);
				aBuffer.append(lIndent2a).append("fromArrowHeadStyle=\"None\"").append(lSeparator);
				aBuffer.append(lIndent2a).append("fromArrowHeadSize=\"Large\"").append(lSeparator);
				aBuffer.append(lIndent2a).append("toArrowHeadStyle=\""+ lConnector.toArrowStyle +"\"").append(lSeparator);
				aBuffer.append(lIndent2a).append("toArrowHeadSize=\"Large\"").append(lSeparator);
				aBuffer.append(lIndent2a).append("dashes=\""+ lConnector.dashesStyles +"\"").append(lSeparator);
				aBuffer.append(lIndent2a).append("connectorStyle=\"oblique\"").append(">").append("\n");
				
				{
					aBuffer.append(lIndent3s).append("<connectionRules>").append("\n");
					
					{
						int lCount = aShapes.length;
						for (int lFrom = 0; lFrom < lCount; lFrom++) {
							String lFromShape = aShapes[lFrom].getId();
							for (int lTo = lFrom; lTo < lCount; lTo++) {
								String lToShape = aShapes[lTo].getId();
								
								aBuffer.append(lIndent4s).append("<connectionRule fromShapeType=\""+lFromShape+"\" toShapeType=\""+lToShape+"\" bidirection=\"true\"/>").append("\n");
							}
						}
					}
					
					aBuffer.append(lIndent3s).append("</connectionRules>").append("\n");
				}
				aBuffer.append(lIndent2s).append("</connectorCreatorInfo>").append("\n");
			}
			aBuffer.append(lIndent1s).append("</action>").append("\n");
			
			lPreviousRelativeId = lConnector.id;
		}
		
	}
	
	private static class Group {
		public final String groupShortId; // GroupsGruop, ComputeGroup, etc...
		public final String groupName; // Group, Compute, etc... (Readable, have space)
		public final String folder; // group, compute, etc...
		
		public final Shape[] shapes; // {AutoScalingGroup, AvaiabilityZone}, {EC2, EC2Instance, ...}
		
		public Group(String aGroupShortId, String aGroupName, String aFolder, Shape[] aShapes) {
			this.groupShortId = aGroupShortId;
			this.groupName = aGroupName.replace("&", "&amp;");
			this.folder = aFolder;
			this.shapes = aShapes;
		}
	}
	private static class Shape {
		public final String imageFileName; // image name (without extension), png must exists, svg may not exists.
		public final String shapeName; // Readable, have space
		private String _id;
		
		public Shape(String aImageFileName, String aShapeName) {
			this.imageFileName = aImageFileName;
			this.shapeName = aShapeName.replace("&", "&amp;");
		}
		
		public void setId(String aId) {
			_id = aId;
		}
		public String getId() {
			return _id;
		}
	}
	private static Shape S(String aImageFileName, String aShapeName) {
		return new Shape(aImageFileName, aShapeName);
	}
	
	private static class Connector {
		
		public final String id;
		public final String name;
		public final String icon;
		public final String toArrowStyle;
		public final String dashesStyles;
		
		public Connector(String aId, String aName, String aIcon, String aToArrowStyle, String aDashesStyles) {
			this.id = aId;
			this.name = aName;
			this.icon = aIcon;
			this.toArrowStyle = aToArrowStyle;
			this.dashesStyles = aDashesStyles;
		}
	}
	
	private static Group[] initGroups() {

		return new Group[] {
				new Group(
						"GroupsGroup", "Group", "group", 
						new Shape[] {
								S("AutoScalingGroup", "Auto Scaling group"), S("AvailabilityZone", "Availability Zone"), S("Region", "region"), S("SecurityGroup", "security group"), 
								S("ElasticBeanstalkContainer", "Elastic Beanstalk container"), S("EC2InstanceContents", "EC2 instance contents"), S("VPCSubnet", "VPC subnet"), S("ServerContents", "server contents"), 
								S("VirtualPrivateCloud", "virtual private cloud"), S("AWSCloud", "AWS cloud"), S("CorporateDataCenter", "corporate data center")
						}
				), 
				
				// Compute & Networking
				new Group(
						"ComputeGroup", "Compute", "compute", 
						new Shape[] {
								S("EC2", "EC2"), S("EC2Instance", "EC2 Instance"), S("EC2Instances", "EC2 Instances"), S("EC2AMI", "EC2 AMI"), S("EC2InstanceWithAMI", "EC2 Instance with AMI"), S("EC2DBOnInstance", "EC2 DB on Instance"), S("EC2InstanceWithCloudWatch", "EC2 Instance with Cloud Watch"), S("EC2ElasticIP", "EC2 Elastic IP"), S("EC2ElasticInstance", "EC2 Elastic Instance"), 
								S("Lambda", "Lambda"), S("EC2ContainerService", "EC2 Container Service"), S("ElasticBeanstalk", "Elastic Beanstalk"), S("ElasticBeanstalkApplication", "Elastic Beanstalk Application"), S("ElasticBeanstalkDeployment", "Elastic Beanstalk Deployment")
						}
				), 
				new Group(
						"NetworkingGroup", "Networking", "networking", 
						new Shape[] {
								S("VPC", "VPC"), S("VPCRouter", "VPC Router"), S("VPCInternetGateway", "VPC Internet Gateway"), S("VPCCustomerGateway", "VPC Customer Gateway"), S("VPCVPNGateway", "VPC VPN Gateway"), S("VPCVPNConnection", "VPC VPN Connection"), S("VPCVPNPeering", "VPC VPN Peering"), 
								S("DirectConnect", "Direct Connect"), S("Route53", "Route 53"), S("Route53HostedZone", "Route 53 Hosted Zone"), S("Route53RouteTable", "Route 53 Route Table"), 
								S("AutoScaling", "Auto Scaling"), S("ElasticLoadBalancing", "Elastic Load Balancing"), S("ElasticNetworkInstance", "Elastic Network Instance")
						}
				), 
				new Group(
						"AnalyitcsGroup", "Analyitcs", "analytics", 
						new Shape[] {
								S("EMR", "EMR"), S("EMRCluster", "EMR Cluster"), S("EMRHDFSCluster", "EMR HDFS Cluster"), S("EMREngine", "EMR Engine"), S("EMREngineMapRM3", "EMR Engine MapR M3"), S("EMREngineMapRM5", "EMR Engine MapR M5"), S("EMREngineMapRM7", "EMR Engine MapR M7"),  
								S("DataPipeline", "Data Pipeline"), S("Kinesis", "Kinesis"), S("KinesisEnabledApp", "Kinesis Enabled App"), S("MachineLearning", "Machine Learning")
						}
				), 
				
				// Deployment & Management
				new Group(
						"DeveloperToolsGroup", "Developer Tools", "developertools", 
						new Shape[] {
								S("CodeCommit", "Code Commit"), S("CodeDeploy", "Code Deploy"), S("CodePipeline", "Code Pipeline")
						}
				), 
				new Group(
						"ManagementToolsGroup", "Management Tools", "managementtools", 
						new Shape[] {
								S("CloudWatch", "Cloud Watch"), S("CloudWatchCluster", "Cloud Watch Cluster"), 
								S("CloudFormation", "Cloud Formation"), S("CloudFormationTemplate", "Cloud Formation Template"), S("CloudFormationStack", "Cloud Formation Stack"), 
								S("CloudTrail", "Cloud Trail"), 
								S("Config", "Config"), 
								S("ServiceCatalog", "Service Catalog"), 
								S("OpsWorks", "OpsWorks"), S("OpsWorksStack", "OpsWorks Stack"), S("OpsWorksDeployments", "OpsWorks Deployments"), S("OpsWorksLayers", "OpsWorks Layers"), S("OpsWorksMonitoring", "OpsWorks Monitoring"), S("OpsWorksInstances", "OpsWorks Instances"), S("OpsWorksResources", "OpsWorks Resources"), S("OpsWorksApps", "OpsWorks Apps"), S("OpsWorksPermissions", "OpsWorks Permissions")
						}
				), 
				new Group(
						"SecurityIdentityGroup", "Security & Identity", "securityidentity", 
						new Shape[] {
								S("IdentityAccessManagement", "IAM"), 
									S("IdentityAccessManagementAddOn", "IAM Add-On"), 
									S("IdentityAccessManagementAWSSecurityTokenService", "IAM AWS Security Token Service"), 
									S("IdentityAccessManagementDataEncryptionKey", "IAM Data Encryption Key"), 
									S("IdentityAccessManagementEncryptedData", "IAM Encrypted Data"), 
									S("IdentityAccessManagementPermissions", "IAM Permissions"), 
									S("IdentityAccessManagementRole", "IAM Role"), 
									S("IdentityAccessManagementLongTermSecurityCredential", "IAM Long-Term Security Credential"), 
									S("IdentityAccessManagementTemporarySecurityCredential", "IAM Temporary Security Credential"), 
									S("IdentityAccessManagementMFAToken", "IAM MFA Token"), 
									S("IdentityAccessManagementAWSSecurityTokenServiceAlternate", "IAM AWS Security Token Service (Alternate)"), 
								S("DirectoryService", "Directory Service"), S("TrustedAdvisor", "Trusted Advisor")
						} 
				), 
				
				// Storage & Content Delivery
				new Group(
						"StorageContentDeliveryGroup", "Storage & Content Delivery", "storagecontentdelivery", 
						new Shape[] {
								S("S3", "S3"), S("S3Bucket", "S3 Bucket"), S("S3BucketWithObjects", "S3 Bucket with Objects"), S("S3Object", "S3 Object"), S("S3BucketWithIAM", "S3 Bucket with IAM"), 
								S("AmazonGlacier", "Amazon Glacier"), S("AmazonGlacierArchive", "Amazon Glacier Archive"), S("AmazonGlacierVault", "Amazon Glacier Vault"), 
								S("CloudFront", "Cloud Front"), S("CloudFrontDownloadDistribution", "Cloud Front Download Distribution"), S("CloudFrontStreamingDistribution", "Cloud Front Streaming Distribution"), S("CloudFrontEdgeLocation", "Cloud Front Edge Location"), 
								S("StorageGateway", "Storage Gateway"), S("StorageGatewayVirtualTapeLibrary", "Storage Gateway Virtual Tape Library"), S("StorageGatewayNonCachedVolume", "Storage Gateway Non-Cached Volume"), S("StorageGatewayCachedVolume", "Storage Gateway Cached Volume"), 
								S("EFS", "EFS"), 
								S("AmazonElasticBlockStore", "Amazon Elastic Block Store"), S("Volume", "Volume"), S("Snapshot", "Snapshot"), S("AWSImportExport", "AWS Import/Export")
								
								// S3 Bucket instance IAM
						} 
				), 
				
				// Application Service & Mobile Service
				new Group(
						"ApplicationServicesGroup", "Application Services", "applicationservices", 
						new Shape[] {
								S("APIGateway", "API Gateway"), 
								S("AppStream", "App Stream"), 
								S("CloudSearch", "Cloud Search"), S("CloudSearchSDFMetadata", "Cloud Search SDF Metadata"), 
								S("ElasticTranscoder", "Elastic Transcoder"), 
								S("SES", "SES"), S("SESEmail", "SES Email"), 
								S("SQS", "SQS"), S("SQSQueue", "SQS Queue"), S("SQSMessage", "SQS Message"), 
								S("SWF", "SWF"), S("SWFWorker", "SWF Worker"), S("SWFDecider", "SWF Decider")
						} 
				), 
				new Group(
						"MobileServicesGroup", "Mobile Services", "mobileservices", 
						new Shape[] {
								S("Cognito", "Cognito"), 
								S("DeviceFarm", "Device Farm"), 
								S("MobileAnalytics", "Mobile Analytics"), 
								S("SNS", "SNS"), S("SNSEmailNotification", "SNS Email Notification"), S("SNSHTTPNotification", "SNS HTTP Notification"), S("SNSTopic", "SNS Topic")
						} 
				), 
				
				// Database
				new Group(
						"DatabaseGroup", "Database", "database", 
						new Shape[] {
								S("RDS", "RDS"), 
									S("RDSDBInstance", "RDS DB Instance"), S("RDSDBInstanceStandby", "RDS DB Instance Standby (Multi-AZ)"), S("RDSDBInstanceReadReplica", "RDS DB Instance Read Replica"), 
									S("RDSMySQLDBInstance", "RDS MySQL DB Instance"), S("RDSOracleDBInstance", "RDS Oracle DB Instance"), S("RDSMSSQLDBInstance", "RDS MS SQL DB Instance"), 
									S("RDSSQLSlave", "RDS SQL Slave"), S("RDSPIOP", "RDS PIOP"), S("RDSSQLMaster", "RDS SQL Master"), 
									S("RDSPostgreSQLDBInstance", "RDS Postgre SQL DB Instance"), 
									S("RDSMySQLDBInstanceAlternate", "RDS MySQL DB Instance Alternate"), S("RDSMSSQLDBInstanceAlternate", "RDS MS SQL DB Instance Alternate"), S("RDSOracleDBInstanceAlternate", "RDS Oracle DB Instance Alternate"), 
								S("DynamoDB", "DynamoDB"), S("DynamoDBTable", "DynamoDB Table"), S("DynamoDBItem", "DynamoDB Item"), S("DynamoDBItems", "DynamoDB Items"), S("DynamoDBAttribute", "DynamoDB Attribute"), S("DynamoDBAttributes", "DynamoDB Attributes"), S("DynamoDBGlobalSecondaryIndex", "DynamoDB Global Secondary Index"), S("DynamoDBDomain", "DynamoDB Domain"), 
								S("ElastiCache", "ElastiCache"), S("ElastiCacheNode", "ElastiCache Node"), S("ElastiCacheRedis", "ElastiCache Redis"), S("ElastiCacheMemCached", "ElastiCache MemCached"), 
								S("RedShift", "RedShift"), S("RedShiftSolidStateDisks", "RedShift Solid State Disks"), S("RedShiftDW1DenseCompute", "RedShift DW1 Dense Compute"), S("RedShiftDW2DenseCompute", "RedShift DW2 Dense Compute"), 
								S("SimpleDB", "SimpleDB")
						} 
				), 
				
				// Enterprise Application
				new Group(
						"EnterpriseApplicationsGroup", "Enterprise Applications", "enterpriseapplications", 
						new Shape[] {
								S("WorkDocs", "WorkDocs"), S("WorkMail", "WorkMail"), S("WorkSpaces", "WorkSpaces")
						} 
				), 
				
				// Non-service Specific
				new Group(
						"NonServiceSepcificGroup", "Non-Service Sepcific", "nonservicespecific", 
						new Shape[] {
								S("AWSCloud", "AWS Cloud"), S("AWSManagementConsole", "AWS Management Console"), S("VirtualPrivateCloud", "virtual private cloud"), S("Forums", "forums"), 
								S("Client", "client"), S("MobileClient", "mobile client"), S("Multimedia", "multimedia"), S("Internet", "Internet"), S("User", "user"), S("Users", "users"), 
								S("TraditionalServer", "traditional server"), S("CorporateDataCenter", "corporate data center"), S("Disk", "disk"), S("GenericDatabase", "generic database"), S("TapeStorage", "tape storage")
						} 
				), 
				
				// On demand Workforce
				new Group(
						"OnDemandWorkforceGroup", "On-Demand Workforce", "ondemandworkforce", 
						new Shape[] {
								S("AmazonMechanicalTurk", "Amazon Mechanical Turk"), S("HumanIntelligenceTasks", "Human Intelligence Tasks (HIT)"), S("AssignmentTask", "assignement/task"), S("Workers", "workers"), S("Requester", "requester")
						} 
				), 
				
				// SDKs
				new Group(
						"SDKsGroup", "SDKs", "sdks", 
						new Shape[] {
								S("Java", "Java"), S("JavaScript", "JavaScript"), S("Python", "Python (boto)"), S("PHP", "PHP"), S("DotNET", ".NET"), S("Ruby", "Ruby"), S("NodeJS", "Node.js"), 
								S("IOS", "iOS"), S("Android", "Android"), S("AWSToolkitForVisualStudio", "AWS Toolkit for Visual Studio"), S("AWSToolkitForEclipse", "AWS Toolkit for Eclipse"), S("AWSToolkitForWindowsPowerShell", "AWS Toolkit for Windows PowerShell"), S("AWSCLI", "AWS CLI")
						} 
				)
		};
	}
	
	private static Connector[] initConnectors() {
		
		return new Connector[] {
			new Connector("com.vp.plugin.aws.connector.general.Link", "AWS General - Link", "icons/connector/general/link.png", "None", "1,0"), 
			new Connector("com.vp.plugin.aws.connector.general.DirectedLink", "AWS General - Directed Link", "icons/connector/general/directedLink.png", "SolidArrow2", "1,0"), 
			new Connector("com.vp.plugin.aws.connector.general.DottedLink", "AWS General - Dotted Link", "icons/connector/general/dottedLink.png", "None", "7,10"), 
			new Connector("com.vp.plugin.aws.connector.general.DottedDirectedLink", "AWS General - Dotted Directed Link", "icons/connector/general/dottedDirectedLink.png", "SolidArrow2", "7,10" /*null will makes XmlElement#getAttributeAsFloatArray(..) show exceptions*/)
		};
	}

}
