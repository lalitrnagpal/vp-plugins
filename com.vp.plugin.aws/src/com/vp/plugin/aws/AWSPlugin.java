package com.vp.plugin.aws;

import java.io.*;

import com.vp.plugin.*;
import com.vp.plugin.aws.project.*;

public class AWSPlugin implements VPPlugin {
	
	public static class ShapeTypes {
		public static class Groups {
			public static final String
					AutoScalingGroup = "com.vp.plugin.aws.shape.group.AutoScalingGroup", 
					AvailabilityZone = "com.vp.plugin.aws.shape.group.AvailabilityZone", 
					Region = "com.vp.plugin.aws.shape.group.Region", 
					SecurityGroup = "com.vp.plugin.aws.shape.group.SecurityGroup", 
					ElasticBeanstalkContainer = "com.vp.plugin.aws.shape.group.ElasticBeanstalkContainer", 
					EC2InstanceContents = "com.vp.plugin.aws.shape.group.EC2InstanceContents", 
					VPCSubnet = "com.vp.plugin.aws.shape.group.VPCSubnet", 
					ServerContents = "com.vp.plugin.aws.shape.group.ServerContents", 
					VirtualPrivateCloud = "com.vp.plugin.aws.shape.group.VirtualPrivateCloud", 
					AWSCloud = "com.vp.plugin.aws.shape.group.AWSCloud", 
					CorporateDataCenter = "com.vp.plugin.aws.shape.group.CorporateDataCenter";
		}
		public static class Compute {
			public static final String
					EC2 = "com.vp.plugin.aws.shape.compute.EC2", 
					EC2Instance = "com.vp.plugin.aws.shape.compute.EC2Instance", 
					EC2Instances = "com.vp.plugin.aws.shape.compute.EC2Instances", 
					EC2AMI = "com.vp.plugin.aws.shape.compute.EC2AMI", 
					EC2InstanceWithAMI = "com.vp.plugin.aws.shape.compute.EC2InstanceWithAMI", 
					EC2DBOnInstance = "com.vp.plugin.aws.shape.compute.EC2DBOnInstance", 
					EC2InstanceWithCloudWatch = "com.vp.plugin.aws.shape.compute.EC2InstanceWithCloudWatch", 
					EC2ElasticIP = "com.vp.plugin.aws.shape.compute.EC2ElasticIP", 
					EC2ElasticInstance = "com.vp.plugin.aws.shape.compute.EC2ElasticInstance", 
					Lambda = "com.vp.plugin.aws.shape.compute.Lambda", 
					EC2ContainerService = "com.vp.plugin.aws.shape.compute.EC2ContainerService", 
					ElasticBeanstalk = "com.vp.plugin.aws.shape.compute.ElasticBeanstalk", 
					ElasticBeanstalkApplication = "com.vp.plugin.aws.shape.compute.ElasticBeanstalkApplication", 
					ElasticBeanstalkDeployment = "com.vp.plugin.aws.shape.compute.ElasticBeanstalkDeployment";
		}
		public static class Networking {
			public static final String
					VPC = "com.vp.plugin.aws.shape.networking.VPC", 
					VPCRouter = "com.vp.plugin.aws.shape.networking.VPCRouter", 
					VPCInternetGateway = "com.vp.plugin.aws.shape.networking.VPCInternetGateway", 
					VPCCustomerGateway = "com.vp.plugin.aws.shape.networking.VPCCustomerGateway", 
					VPCVPNGateway = "com.vp.plugin.aws.shape.networking.VPCVPNGateway", 
					VPCVPNConnection = "com.vp.plugin.aws.shape.networking.VPCVPNConnection", 
					VPCVPNPeering = "com.vp.plugin.aws.shape.networking.VPCVPNPeering", 
					DirectConnect = "com.vp.plugin.aws.shape.networking.DirectConnect", 
					Route53 = "com.vp.plugin.aws.shape.networking.Route53", 
					Route53HostedZone = "com.vp.plugin.aws.shape.networking.Route53HostedZone", 
					Route53RouteTable = "com.vp.plugin.aws.shape.networking.Route53RouteTable", 
					AutoScaling = "com.vp.plugin.aws.shape.networking.AutoScaling", 
					ElasticLoadBalancing = "com.vp.plugin.aws.shape.networking.ElasticLoadBalancing", 
					ElasticNetworkInstance = "com.vp.plugin.aws.shape.networking.ElasticNetworkInstance";
		}
		public static class Analyitcs {
			public static final String
					EMR = "com.vp.plugin.aws.shape.analytics.EMR", 
					EMRCluster = "com.vp.plugin.aws.shape.analytics.EMRCluster", 
					EMRHDFSCluster = "com.vp.plugin.aws.shape.analytics.EMRHDFSCluster", 
					EMREngine = "com.vp.plugin.aws.shape.analytics.EMREngine", 
					EMREngineMapRM3 = "com.vp.plugin.aws.shape.analytics.EMREngineMapRM3", 
					EMREngineMapRM5 = "com.vp.plugin.aws.shape.analytics.EMREngineMapRM5", 
					EMREngineMapRM7 = "com.vp.plugin.aws.shape.analytics.EMREngineMapRM7", 
					DataPipeline = "com.vp.plugin.aws.shape.analytics.DataPipeline", 
					Kinesis = "com.vp.plugin.aws.shape.analytics.Kinesis", 
					KinesisEnabledApp = "com.vp.plugin.aws.shape.analytics.KinesisEnabledApp", 
					MachineLearning = "com.vp.plugin.aws.shape.analytics.MachineLearning";
		}
		public static class DeveloperTools {
			public static final String
					CodeCommit = "com.vp.plugin.aws.shape.developertools.CodeCommit", 
					CodeDeploy = "com.vp.plugin.aws.shape.developertools.CodeDeploy", 
					CodePipeline = "com.vp.plugin.aws.shape.developertools.CodePipeline";
		}
		public static class ManagementTools {
			public static final String
					CloudWatch = "com.vp.plugin.aws.shape.managementtools.CloudWatch", 
					CloudWatchCluster = "com.vp.plugin.aws.shape.managementtools.CloudWatchCluster", 
					CloudFormation = "com.vp.plugin.aws.shape.managementtools.CloudFormation", 
					CloudFormationTemplate = "com.vp.plugin.aws.shape.managementtools.CloudFormationTemplate", 
					CloudFormationStack = "com.vp.plugin.aws.shape.managementtools.CloudFormationStack", 
					CloudTrail = "com.vp.plugin.aws.shape.managementtools.CloudTrail", 
					Config = "com.vp.plugin.aws.shape.managementtools.Config", 
					ServiceCatalog = "com.vp.plugin.aws.shape.managementtools.ServiceCatalog", 
					OpsWorks = "com.vp.plugin.aws.shape.managementtools.OpsWorks", 
					OpsWorksStack = "com.vp.plugin.aws.shape.managementtools.OpsWorksStack", 
					OpsWorksDeployments = "com.vp.plugin.aws.shape.managementtools.OpsWorksDeployments", 
					OpsWorksLayers = "com.vp.plugin.aws.shape.managementtools.OpsWorksLayers", 
					OpsWorksMonitoring = "com.vp.plugin.aws.shape.managementtools.OpsWorksMonitoring", 
					OpsWorksInstances = "com.vp.plugin.aws.shape.managementtools.OpsWorksInstances", 
					OpsWorksResources = "com.vp.plugin.aws.shape.managementtools.OpsWorksResources", 
					OpsWorksApps = "com.vp.plugin.aws.shape.managementtools.OpsWorksApps", 
					OpsWorksPermissions = "com.vp.plugin.aws.shape.managementtools.OpsWorksPermissions";
		}
		public static class SecurityIdentity {
			public static final String
					IdentityAccessManagement = "com.vp.plugin.aws.shape.securityidentity.IdentityAccessManagement", 
					IdentityAccessManagementAddOn = "com.vp.plugin.aws.shape.securityidentity.IdentityAccessManagementAddOn", 
					IdentityAccessManagementAWSSecurityTokenService = "com.vp.plugin.aws.shape.securityidentity.IdentityAccessManagementAWSSecurityTokenService", 
					IdentityAccessManagementDataEncryptionKey = "com.vp.plugin.aws.shape.securityidentity.IdentityAccessManagementDataEncryptionKey", 
					IdentityAccessManagementEncryptedData = "com.vp.plugin.aws.shape.securityidentity.IdentityAccessManagementEncryptedData", 
					IdentityAccessManagementPermissions = "com.vp.plugin.aws.shape.securityidentity.IdentityAccessManagementPermissions", 
					IdentityAccessManagementRole = "com.vp.plugin.aws.shape.securityidentity.IdentityAccessManagementRole", 
					IdentityAccessManagementLongTermSecurityCredential = "com.vp.plugin.aws.shape.securityidentity.IdentityAccessManagementLongTermSecurityCredential", 
					IdentityAccessManagementTemporarySecurityCredential = "com.vp.plugin.aws.shape.securityidentity.IdentityAccessManagementTemporarySecurityCredential", 
					IdentityAccessManagementMFAToken = "com.vp.plugin.aws.shape.securityidentity.IdentityAccessManagementMFAToken", 
					IdentityAccessManagementAWSSecurityTokenServiceAlternate = "com.vp.plugin.aws.shape.securityidentity.IdentityAccessManagementAWSSecurityTokenServiceAlternate", 
					DirectoryService = "com.vp.plugin.aws.shape.securityidentity.DirectoryService", 
					TrustedAdvisor = "com.vp.plugin.aws.shape.securityidentity.TrustedAdvisor";
		}
		public static class StorageContentDelivery {
			public static final String
					S3 = "com.vp.plugin.aws.shape.storagecontentdelivery.S3", 
					S3Bucket = "com.vp.plugin.aws.shape.storagecontentdelivery.S3Bucket", 
					S3BucketWithObjects = "com.vp.plugin.aws.shape.storagecontentdelivery.S3BucketWithObjects", 
					S3Object = "com.vp.plugin.aws.shape.storagecontentdelivery.S3Object", 
					S3BucketWithIAM = "com.vp.plugin.aws.shape.storagecontentdelivery.S3BucketWithIAM", 
					AmazonGlacier = "com.vp.plugin.aws.shape.storagecontentdelivery.AmazonGlacier", 
					AmazonGlacierArchive = "com.vp.plugin.aws.shape.storagecontentdelivery.AmazonGlacierArchive", 
					AmazonGlacierVault = "com.vp.plugin.aws.shape.storagecontentdelivery.AmazonGlacierVault", 
					CloudFront = "com.vp.plugin.aws.shape.storagecontentdelivery.CloudFront", 
					CloudFrontDownloadDistribution = "com.vp.plugin.aws.shape.storagecontentdelivery.CloudFrontDownloadDistribution", 
					CloudFrontStreamingDistribution = "com.vp.plugin.aws.shape.storagecontentdelivery.CloudFrontStreamingDistribution", 
					CloudFrontEdgeLocation = "com.vp.plugin.aws.shape.storagecontentdelivery.CloudFrontEdgeLocation", 
					StorageGateway = "com.vp.plugin.aws.shape.storagecontentdelivery.StorageGateway", 
					StorageGatewayVirtualTapeLibrary = "com.vp.plugin.aws.shape.storagecontentdelivery.StorageGatewayVirtualTapeLibrary", 
					StorageGatewayNonCachedVolume = "com.vp.plugin.aws.shape.storagecontentdelivery.StorageGatewayNonCachedVolume", 
					StorageGatewayCachedVolume = "com.vp.plugin.aws.shape.storagecontentdelivery.StorageGatewayCachedVolume", 
					EFS = "com.vp.plugin.aws.shape.storagecontentdelivery.EFS", 
					AmazonElasticBlockStore = "com.vp.plugin.aws.shape.storagecontentdelivery.AmazonElasticBlockStore", 
					Volume = "com.vp.plugin.aws.shape.storagecontentdelivery.Volume", 
					Snapshot = "com.vp.plugin.aws.shape.storagecontentdelivery.Snapshot", 
					AWSImportExport = "com.vp.plugin.aws.shape.storagecontentdelivery.AWSImportExport";
		}
		public static class ApplicationServices {
			public static final String
					APIGateway = "com.vp.plugin.aws.shape.applicationservices.APIGateway", 
					AppStream = "com.vp.plugin.aws.shape.applicationservices.AppStream", 
					CloudSearch = "com.vp.plugin.aws.shape.applicationservices.CloudSearch", 
					CloudSearchSDFMetadata = "com.vp.plugin.aws.shape.applicationservices.CloudSearchSDFMetadata", 
					ElasticTranscoder = "com.vp.plugin.aws.shape.applicationservices.ElasticTranscoder", 
					SES = "com.vp.plugin.aws.shape.applicationservices.SES", 
					SESEmail = "com.vp.plugin.aws.shape.applicationservices.SESEmail", 
					SQS = "com.vp.plugin.aws.shape.applicationservices.SQS", 
					SQSQueue = "com.vp.plugin.aws.shape.applicationservices.SQSQueue", 
					SQSMessage = "com.vp.plugin.aws.shape.applicationservices.SQSMessage", 
					SWF = "com.vp.plugin.aws.shape.applicationservices.SWF", 
					SWFWorker = "com.vp.plugin.aws.shape.applicationservices.SWFWorker", 
					SWFDecider = "com.vp.plugin.aws.shape.applicationservices.SWFDecider";
		}
		public static class MobileServices {
			public static final String
					Cognito = "com.vp.plugin.aws.shape.mobileservices.Cognito", 
					DeviceFarm = "com.vp.plugin.aws.shape.mobileservices.DeviceFarm", 
					MobileAnalytics = "com.vp.plugin.aws.shape.mobileservices.MobileAnalytics", 
					SNS = "com.vp.plugin.aws.shape.mobileservices.SNS", 
					SNSEmailNotification = "com.vp.plugin.aws.shape.mobileservices.SNSEmailNotification", 
					SNSHTTPNotification = "com.vp.plugin.aws.shape.mobileservices.SNSHTTPNotification", 
					SNSTopic = "com.vp.plugin.aws.shape.mobileservices.SNSTopic";
		}
		public static class Database {
			public static final String
					RDS = "com.vp.plugin.aws.shape.database.RDS", 
					RDSDBInstance = "com.vp.plugin.aws.shape.database.RDSDBInstance", 
					RDSDBInstanceStandby = "com.vp.plugin.aws.shape.database.RDSDBInstanceStandby", 
					RDSDBInstanceReadReplica = "com.vp.plugin.aws.shape.database.RDSDBInstanceReadReplica", 
					RDSMySQLDBInstance = "com.vp.plugin.aws.shape.database.RDSMySQLDBInstance", 
					RDSOracleDBInstance = "com.vp.plugin.aws.shape.database.RDSOracleDBInstance", 
					RDSMSSQLDBInstance = "com.vp.plugin.aws.shape.database.RDSMSSQLDBInstance", 
					RDSSQLSlave = "com.vp.plugin.aws.shape.database.RDSSQLSlave", 
					RDSPIOP = "com.vp.plugin.aws.shape.database.RDSPIOP", 
					RDSSQLMaster = "com.vp.plugin.aws.shape.database.RDSSQLMaster", 
					RDSPostgreSQLDBInstance = "com.vp.plugin.aws.shape.database.RDSPostgreSQLDBInstance", 
					RDSMySQLDBInstanceAlternate = "com.vp.plugin.aws.shape.database.RDSMySQLDBInstanceAlternate", 
					RDSMSSQLDBInstanceAlternate = "com.vp.plugin.aws.shape.database.RDSMSSQLDBInstanceAlternate", 
					RDSOracleDBInstanceAlternate = "com.vp.plugin.aws.shape.database.RDSOracleDBInstanceAlternate", 
					DynamoDB = "com.vp.plugin.aws.shape.database.DynamoDB", 
					DynamoDBTable = "com.vp.plugin.aws.shape.database.DynamoDBTable", 
					DynamoDBItem = "com.vp.plugin.aws.shape.database.DynamoDBItem", 
					DynamoDBItems = "com.vp.plugin.aws.shape.database.DynamoDBItems", 
					DynamoDBAttribute = "com.vp.plugin.aws.shape.database.DynamoDBAttribute", 
					DynamoDBAttributes = "com.vp.plugin.aws.shape.database.DynamoDBAttributes", 
					DynamoDBGlobalSecondaryIndex = "com.vp.plugin.aws.shape.database.DynamoDBGlobalSecondaryIndex", 
					DynamoDBDomain = "com.vp.plugin.aws.shape.database.DynamoDBDomain", 
					ElastiCache = "com.vp.plugin.aws.shape.database.ElastiCache", 
					ElastiCacheNode = "com.vp.plugin.aws.shape.database.ElastiCacheNode", 
					ElastiCacheRedis = "com.vp.plugin.aws.shape.database.ElastiCacheRedis", 
					ElastiCacheMemCached = "com.vp.plugin.aws.shape.database.ElastiCacheMemCached", 
					RedShift = "com.vp.plugin.aws.shape.database.RedShift", 
					RedShiftSolidStateDisks = "com.vp.plugin.aws.shape.database.RedShiftSolidStateDisks", 
					RedShiftDW1DenseCompute = "com.vp.plugin.aws.shape.database.RedShiftDW1DenseCompute", 
					RedShiftDW2DenseCompute = "com.vp.plugin.aws.shape.database.RedShiftDW2DenseCompute", 
					SimpleDB = "com.vp.plugin.aws.shape.database.SimpleDB";
		}
		public static class EnterpriseApplications {
			public static final String
					WorkDocs = "com.vp.plugin.aws.shape.enterpriseapplications.WorkDocs", 
					WorkMail = "com.vp.plugin.aws.shape.enterpriseapplications.WorkMail", 
					WorkSpaces = "com.vp.plugin.aws.shape.enterpriseapplications.WorkSpaces";
		}
		public static class NonServiceSepcific {
			public static final String
					AWSCloud = "com.vp.plugin.aws.shape.nonservicespecific.AWSCloud", 
					AWSManagementConsole = "com.vp.plugin.aws.shape.nonservicespecific.AWSManagementConsole", 
					VirtualPrivateCloud = "com.vp.plugin.aws.shape.nonservicespecific.VirtualPrivateCloud", 
					Forums = "com.vp.plugin.aws.shape.nonservicespecific.Forums", 
					Client = "com.vp.plugin.aws.shape.nonservicespecific.Client", 
					MobileClient = "com.vp.plugin.aws.shape.nonservicespecific.MobileClient", 
					Multimedia = "com.vp.plugin.aws.shape.nonservicespecific.Multimedia", 
					Internet = "com.vp.plugin.aws.shape.nonservicespecific.Internet", 
					User = "com.vp.plugin.aws.shape.nonservicespecific.User", 
					Users = "com.vp.plugin.aws.shape.nonservicespecific.Users", 
					TraditionalServer = "com.vp.plugin.aws.shape.nonservicespecific.TraditionalServer", 
					CorporateDataCenter = "com.vp.plugin.aws.shape.nonservicespecific.CorporateDataCenter", 
					Disk = "com.vp.plugin.aws.shape.nonservicespecific.Disk", 
					GenericDatabase = "com.vp.plugin.aws.shape.nonservicespecific.GenericDatabase", 
					TapeStorage = "com.vp.plugin.aws.shape.nonservicespecific.TapeStorage";
		}
		public static class OnDemandWorkforce {
			public static final String
					AmazonMechanicalTurk = "com.vp.plugin.aws.shape.ondemandworkforce.AmazonMechanicalTurk", 
					HumanIntelligenceTasks = "com.vp.plugin.aws.shape.ondemandworkforce.HumanIntelligenceTasks", 
					AssignmentTask = "com.vp.plugin.aws.shape.ondemandworkforce.AssignmentTask", 
					Workers = "com.vp.plugin.aws.shape.ondemandworkforce.Workers", 
					Requester = "com.vp.plugin.aws.shape.ondemandworkforce.Requester";
		}
		public static class SDKs {
			public static final String
					Java = "com.vp.plugin.aws.shape.sdks.Java", 
					JavaScript = "com.vp.plugin.aws.shape.sdks.JavaScript", 
					Python = "com.vp.plugin.aws.shape.sdks.Python", 
					PHP = "com.vp.plugin.aws.shape.sdks.PHP", 
					DotNET = "com.vp.plugin.aws.shape.sdks.DotNET", 
					Ruby = "com.vp.plugin.aws.shape.sdks.Ruby", 
					NodeJS = "com.vp.plugin.aws.shape.sdks.NodeJS", 
					IOS = "com.vp.plugin.aws.shape.sdks.IOS", 
					Android = "com.vp.plugin.aws.shape.sdks.Android", 
					AWSToolkitForVisualStudio = "com.vp.plugin.aws.shape.sdks.AWSToolkitForVisualStudio", 
					AWSToolkitForEclipse = "com.vp.plugin.aws.shape.sdks.AWSToolkitForEclipse", 
					AWSToolkitForWindowsPowerShell = "com.vp.plugin.aws.shape.sdks.AWSToolkitForWindowsPowerShell", 
					AWSCLI = "com.vp.plugin.aws.shape.sdks.AWSCLI";
		}
		public static class Links {
			public static final String
					Link = "com.vp.plugin.aws.connector.general.Link", 
					DirectedLink = "com.vp.plugin.aws.connector.general.DirectedLink", 
					DottedLink = "com.vp.plugin.aws.connector.general.DottedLink", 
					DottedDirectedLink = "com.vp.plugin.aws.connector.general.DottedDirectedLink";
		}
	}
	
	
	private static File _pluginDir;

	private AWSProjectListener _projectListener = new AWSProjectListener();
	@Override
	public void loaded(VPPluginInfo aPluginInfo) {
		_pluginDir = aPluginInfo.getPluginDir();
		
		if (ApplicationManager.instance().getProjectManager().getProject() != null) {
			ApplicationManager.instance().getProjectManager().getProject().addProjectListener(_projectListener);
		}
	}

	@Override
	public void unloaded() {
		if (ApplicationManager.instance().getProjectManager().getProject() != null) {
			ApplicationManager.instance().getProjectManager().getProject().removeProjectListener(_projectListener);
		}
	}
	
	
	public static InputStream loadResourcesFile(String aRelativePath) throws FileNotFoundException {
		File lFile = new File(_pluginDir, aRelativePath);
		return new FileInputStream(lFile);
	}

}