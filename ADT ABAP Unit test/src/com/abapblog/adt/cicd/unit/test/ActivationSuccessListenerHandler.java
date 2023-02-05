package com.abapblog.adt.cicd.unit.test;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.sap.adt.activation.ui.IActivationSuccessListener;
import com.sap.adt.communication.exceptions.CommunicationException;
import com.sap.adt.tools.abapsource.abapunit.AbapUnitTask;
import com.sap.adt.tools.abapsource.abapunit.IAbapUnitResult;
import com.sap.adt.tools.abapsource.abapunit.IAbapUnitService;
import com.sap.adt.tools.abapsource.abapunit.TestItem;
import com.sap.adt.tools.abapsource.abapunit.TestRunException;
import com.sap.adt.tools.abapsource.abapunit.services.IAdtServicesFactory;
import com.sap.adt.tools.abapsource.internal.abapunit.AdtServicesFactory;
//import com.sap.adt.tools.abapsource.abapunit.services.IAdtServicesFactory;
//import com.sap.adt.tools.abapsource.internal.abapunit.AdtServicesFactory;
import com.sap.adt.tools.core.IAdtObjectReference;

@SuppressWarnings("restriction")
public class ActivationSuccessListenerHandler implements IActivationSuccessListener {
	private static final String ADT_OBJECT_NAME_PREFIX = "/sap/bc/adt/vit/wb/object_type/devck/object_name/";

	@Override
	public void onActivationSuccess(List<IAdtObjectReference> adtObject, IProject project) {
		String packageName = "$ASE_DRYNDAR"; // change it here
		AndreasGautschWayOfAtcCall(project, packageName);
		secondWayofABAPUnitCall(project, packageName);
	}

	private void AndreasGautschWayOfAtcCall(IProject project, String packageName) {

		boolean flag = false;

		IAdtServicesFactory servicesFactory = AdtServicesFactory.createInstance();
		IAbapUnitService abapUnitService = servicesFactory.createAbapUnitService(project.getName(), flag);
		AbapUnitTask task = new AbapUnitTask(packageName);

		String testobjectUrl = ADT_OBJECT_NAME_PREFIX + packageName;
		TestItem itemObject = new TestItem(testobjectUrl, testobjectUrl);
		task.addTestItem(itemObject);
		try {
			IAbapUnitResult abapUnitResult = abapUnitService.executeUnitTests(task, false, packageName);
// then he does the extraction of the results
// https://github.com/andau/abapCI/blob/e91674bd0c108e33e248f12c3534e7b0f3a44db9/eclipsePlugin/abapCI/src/abapci/handlers/AbapUnitHandler.java#L47
		} catch (TestRunException e) {
			if (e.getCause() instanceof CommunicationException) {
			} else {
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (Exception ex) {
		}
	}

	public void secondWayofABAPUnitCall(IProject project, String packageName) {
		AbapUnitTask task = new AbapUnitTask(packageName);

		String testobjectUrl = ADT_OBJECT_NAME_PREFIX + packageName;
		TestItem itemObject = new TestItem(testobjectUrl, testobjectUrl);
		task.addTestItem(itemObject);
//		The code below cannot work now as the access to AbapUnitFactory and other classe/interfaces is restrictede
//		AbapUnitFactory abapUnitFactory = new AbapUnitFactory();
//		IAbapUnitRunner abapUnitRunner = abapUnitFactory.createRunner();
//		IAbapUnitMonitor abapUnitMonitor = abapUnitFactory.createMonitorForDisplay();
//		abapUnitRunner.executeUnitTests(project, task, abapUnitMonitor, null, null);
	}

}
