package io.mosip.testrig.adminui.utility;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import io.mosip.testrig.adminui.dbaccess.DBManager;
import io.mosip.testrig.adminui.fw.util.AdminTestUtil;
import io.mosip.testrig.adminui.testcase.*;


public class TestRunner {
	private static final Logger logger = Logger.getLogger(TestRunner.class);
	static TestListenerAdapter tla = new TestListenerAdapter();
	
	public static String jarUrl = TestRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static String uin="";
	public static String perpetualVid="";
	public static String onetimeuseVid="";
	public static String temporaryVid="";
	
	static TestNG testNg;
	
	public static void main(String[] args) throws Exception {
//admin user = AdminTestUtil.generateUIN();
//    	
//    	if (uin != null) {
//    		perpetualVid = AdminTestUtil.generateVID(uin, "perpetual");
//    		onetimeuseVid = AdminTestUtil.generateVID(uin, "onetimeuse");
//    		temporaryVid= AdminTestUtil.generateVID(uin, "temporary");
//    	}
		AdminTestUtil.initialize();
		DBManager.clearMasterDbData();
//		BaseTestCase.currentModule = GlobalConstants.MASTERDATA;
//		BaseTestCase.setReportName("masterdata-" + localLanguageList.get(i));
		//startTestRunner();
		
		testNg=new TestNG();
		//testNg.addListener(MyListener.class);
		String listExcludedGroups=JsonUtil.JsonObjParsing(Commons.getTestData(),"setExcludedGroups");
		testNg.setExcludedGroups(listExcludedGroups);
		testNg.setTestClasses(new Class[] {
				
	CenterTest.class,HolidaysTest.class,TemplateTest.class
				
				,
				
				DeviceTest.class,MachineTest.class
				,BlockListTest.class,CenterTypeTest.class,
				DeviceSpecificationTest.class,DeviceTypesTest.class,
				MachineSpecificationTest.class,MachineTypesTest.class,
				DynamicFieldTest.class,DocumentCategoriesTest.class,DocumentTypes.class,
				
			BulkUploadTest.class
		
		});
	//	testNg.addListener(tla);
		testNg.run();
		
		
	}
	public static String getGlobalResourcePath() {
		if (checkRunType().equalsIgnoreCase("JAR")) {
			return new File(jarUrl).getParentFile().getAbsolutePath().toString();
		} else if (checkRunType().equalsIgnoreCase("IDE")) {
			String path = new File(TestRunner.class.getClassLoader().getResource("").getPath()).getAbsolutePath()
					.toString();
			if (path.contains("test-classes"))
				path = path.replace("test-classes", "classes");
			return path;
		}
		return "Global Resource File Path Not Found";
	}
    
	public static String getResourcePath() {
		if (checkRunType().equalsIgnoreCase("JAR")) {
			return new File(jarUrl).getParentFile().getAbsolutePath();
		} else if (checkRunType().equalsIgnoreCase("IDE")) {
			String path = new File(TestRunner.class.getClassLoader().getResource("").getPath()).getAbsolutePath()
					.toString();
			if (path.contains("test-classes"))
				path = path.replace("test-classes", "classes");
			return path;
		}
		return "Global Resource File Path Not Found";
	}
	
	public static String checkRunType() {
		if (TestRunner.class.getResource("TestRunner.class").getPath().toString().contains(".jar"))
			return "JAR";
		else
			return "IDE";
	}
	

}