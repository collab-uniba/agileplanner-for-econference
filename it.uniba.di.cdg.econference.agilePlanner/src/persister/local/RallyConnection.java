package persister.local;

import java.awt.Cursor;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.RootPaneContainer;
import javax.xml.rpc.ServiceException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Color;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;

import cards.CardConstants;
import fitintegration.PluginInformation;
import persister.CouldNotLoadProjectException;
import persister.IndexCardNotFoundException;
import persister.Message;
import persister.data.Project;
import persister.data.impl.MessageDataObject;


public class RallyConnection extends PersisterToXML {

	private static PersisterRally persisterRally;
	

	private static String uName;
	private static String uPass;
	private static String url;
	private static Project sp;

	public RallyConnection(String localProjectDirPath, Project project,
			Message msg) throws Exception {
		super(localProjectDirPath, project.getName());
	}

	public RallyConnection(String localProjectDirPath, Message msg)
			throws Exception {
		super(localProjectDirPath, (String) msg.getMessage());

	}

	public static List<String> login(Message msg) throws Exception {
		MessageDataObject message = (MessageDataObject) msg;

		uName = message.getData().get("username");
		uPass = message.getData().get("password");
        url = message.getData().get("url");
		persisterRally = new PersisterRally(uName,
				uPass,url);

		List<String> projectNames = persisterRally.getProjectNames();
		return projectNames;

	}

	public Project synchronizeProject(String projName) throws CouldNotLoadProjectException {

		String projectName = this.getProject().getName();
		boolean newProject = true;
		
		if(persisterRally == null)
			try {
				persisterRally = new PersisterRally(uName,
						uPass,url);
			} catch (RemoteException e1) {
				util.Logger.singleton().error(e1);
			} catch (ServiceException e1) {
				util.Logger.singleton().error(e1);
			} catch (CouldNotLoadProjectException e1) {
				util.Logger.singleton().error(e1);
			}

		List<String> projectNames = persisterRally.getProjectNames();

		
		for (int loop = 0; loop < projectNames.size(); loop++) {

			if (projectName.equals(projectNames.get(loop))) {
				
					sp = this.getProject();


						
					try {
						persisterRally.synchronizeProject(sp);
					} catch (RemoteException e) {
						util.Logger.singleton().error(e);
					} catch (IndexCardNotFoundException e) {
						util.Logger.singleton().error(e);
					} catch (CouldNotLoadProjectException e) {
						util.Logger.singleton().error(e);
					}

						
				newProject = false;
				break;
			}
		}
		if (newProject) {
			createProjectAtRally(this.getProject());
		}
		this.save();
		return project;
	}

	private Project createProjectAtRally(Project project) throws CouldNotLoadProjectException {
		//creates a blank project only
		persisterRally.createProject(project);

		try {
			persisterRally.synchronizeProject(persisterRally.getAPPrjObj());
		} catch (RemoteException e) {
			util.Logger.singleton().error(e);
		} catch (IndexCardNotFoundException e) {
			util.Logger.singleton().error(e);
		}
		return persisterRally.getAPPrjObj();

	}

}
