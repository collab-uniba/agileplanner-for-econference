package ca.ucalgary.cpsc.agilePlanner.test.planner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import persister.CouldNotLoadProjectException;
import persister.ForbiddenOperationException;
import persister.IndexCardNotFoundException;
import persister.data.Iteration;
import persister.data.Project;
import persister.data.StoryCard;
import persister.data.impl.StoryCardDataObject;
import persister.local.PersisterRally;



import com.rallydev.webservice.v1_02.domain.User;

/**
 * 
 * @author shu & Harminder
 * 
 * This file includes all the junit tests for 
 * "persister.PersisterRally"
 *
 */
public class PersisterRallyTest {
	/**
	 * Rally connection parameters
	 */
	private PersisterRally rallyConnection;
	private String user = "maurer@cpsc.ucalgary.ca";
	private String pass = "p@ssw0rd";
	private String url = "rally1";
	
	/**
	 * Projects in our Rally workspace
	 */

	/**
	 * First project in our Rally workspace
	 */
	private String rallyProjectName = "Test Project";
//	private long prjID = 59816132;
	private persister.data.Project apProject; 
	
	/**
	 * Iterations in the first project
	 */

	
	/**
	 * Backlog in the project
	 */
//	private int numBklogStory = 3;
	private long APBklgID = 2;
	
    /**
     * connect to Rally server
     * @throws Exception may be thrown.
    */
    @Before
	public void init() throws Exception {
    	rallyConnection = new PersisterRally(user, pass, url);
    }
   
       
	/**
	 * Test if the connection to rally server
	 * is successful or not
	 * @throws RemoteException 
	 */
	@Test
	public void getRallyUser() throws RemoteException {
		User userObj = rallyConnection.getCurrentUser();
		assertEquals(userObj.getLoginName(), user);
	}
	
	
	/**
	 * Test acquiring all the projects
	 */
//	@Test
//	public void getProjectNames() {
//		 List<String> projectNamesList = rallyDataStore.getProjectNames();
//		 assertNotNull(projectNamesList);
//		 assertEquals(numPrj, projectNamesList.size());
//		 assertEquals(firstPrjName, projectNamesList.get(0));
//		 assertEquals(secondPrjName, projectNamesList.get(1));
//	}
	

    
    /**
	 * Test creating a project
	 * 
	 * a Rally project cannot be deleted once created.
	 * 
     * @throws CouldNotLoadProjectException 
     * @throws IndexCardNotFoundException 
     * @throws RemoteException 
	 * 
	 */
	@Test
	public void loadProject() throws CouldNotLoadProjectException, RemoteException, IndexCardNotFoundException {
		String prjName = "Test Project";
	
		Project APPrj = rallyConnection.load(prjName);
//		if (APPrj == null) {	
//			/* if the project does not already exist */
//			APPrj = rallyConnection.createProject(prjName);	
//		}
		assertNotNull(APPrj);
		assertEquals(prjName, APPrj.getName());
		
		/* delete the new project */
		/**
		 * Currently, we have no way to delete a project from either Rally
		 * webservice or its online dashboard. 
		 */	
	}
    
	
  /**
	 * Test create and then delete a user story in backlog
    * @throws CouldNotLoadProjectException 
    * @throws IndexCardNotFoundException 
    * @throws RemoteException 
	 * 
	 */
	@Test
	public void createDeleteStoryInBacklog() throws CouldNotLoadProjectException, RemoteException, IndexCardNotFoundException {
		String storyName = "Junit Story in Backlog";
		String storyDesc = "Story created by JUnit for backlog.";
		float actualEffort = 2;
		long parentid = 2;
		
		apProject = rallyConnection.load(rallyProjectName);
		
		StoryCard sc = new StoryCardDataObject();
		sc.setParent(parentid);
		sc.setActualEffort(actualEffort);
		sc.setName(storyName);
		sc.setDescription(storyDesc);
		
		rallyConnection.createStoryCardForRally(sc);
		assertNotNull(sc);
		assertEquals(storyName, sc.getName());
		assertEquals(storyDesc, sc.getDescription());
		
		/* delete the new story */		
		rallyConnection.deleteStoryInRally(sc);		 
//		assertNotNull(APStory);
	}
    
	/**
	 * Test create and then delete a user story in backlog
    * @throws CouldNotLoadProjectException 
    * @throws IndexCardNotFoundException 
    * @throws RemoteException 
	 * 
	 */
	@Test
	public void createDeleteStoryInIteration() throws CouldNotLoadProjectException, RemoteException, IndexCardNotFoundException {
		String storyName = "Junit Story in Iteration";
		String storyDesc = "Story created by JUnit";
		float actualEffort = 2;
		long parentid = 2;
		
		apProject = rallyConnection.load(rallyProjectName);
		List <Iteration> iterationList = apProject.getIterationChildren();	 
		if(iterationList != null && iterationList.size() > 0) {
			parentid = Long.valueOf(iterationList.get(0).getId());
		}
	
		StoryCard sc = new StoryCardDataObject();
		sc.setParent(parentid);
		sc.setActualEffort(actualEffort);
		sc.setName(storyName);
		sc.setDescription(storyDesc);
		
		rallyConnection.createStoryCardForRally(sc);
		assertNotNull(sc);
		assertEquals(storyName, sc.getName());
		assertEquals(storyDesc, sc.getDescription());
		
		/* delete the new story */		
		rallyConnection.deleteStoryInRally(sc);		 		 
//		assertNotNull(APStory);
	}
    
    /**
     * Test updating an iteration
	 * @throws CouldNotLoadProjectException 
	 * @throws IndexCardNotFoundException 
	 * @throws RemoteException 
	 * 
	 */
	@Test
	public void updateIteration() throws CouldNotLoadProjectException, RemoteException, IndexCardNotFoundException {
		/**
		 * get the first iteration
		 */
		apProject = rallyConnection.load(rallyProjectName);
		
		rallyConnection.synchronizeProject(apProject);
		Iteration APIterObj;
		assertNotNull(apProject.getIterationChildren());
		assertTrue(apProject.getIterationChildren().size() != 0);		
		APIterObj = apProject.getIterationChildren().get(0);
		assertNotNull(APIterObj);
		
		String oldIterName = APIterObj.getName();
		rallyConnection.setAPPrjObj(apProject);
		/* change the first iteration name */
		String newIterName = "changed Iteration Name by JUnit";
		APIterObj.setName(newIterName);
		APIterObj = (Iteration)rallyConnection.updateCard(APIterObj);
		assertNotNull(APIterObj);
		assertEquals(newIterName, APIterObj.getName());
			
		/* revert the first iteration name */
		APIterObj.setName(oldIterName);
		APIterObj = (Iteration)rallyConnection.updateCard(APIterObj);
		assertNotNull(APIterObj);
		assertEquals(oldIterName, APIterObj.getName());
	}
	
	/**
	 * Test updating a user story
    * @throws CouldNotLoadProjectException 
    * @throws IndexCardNotFoundException 
    * @throws RemoteException 
	 * 
	 */
	@Test
	public void updateStory() throws CouldNotLoadProjectException, RemoteException, IndexCardNotFoundException {
		/**
		 * get the first story  
		 */
		apProject = rallyConnection.load(rallyProjectName);
		rallyConnection.synchronizeProject(apProject);
		List <Iteration> iters = apProject.getIterationChildren();	
		StoryCard APStoryObj = null; 
		if(iters != null && iters.size() > 0) {
			assertNotNull(iters.get(1).getStoryCardChildren());
			assertTrue(iters.get(0).getStoryCardChildren().size() > 0);
			APStoryObj = iters.get(0).getStoryCardChildren().get(0);
		}		
		assertNotNull(APStoryObj);
		rallyConnection.setAPPrjObj(apProject);
		/* change the first story name */
		String oldName = APStoryObj.getName();
		String newName = "changed Story Name by JUnit";
		APStoryObj.setName(newName);
		APStoryObj = (StoryCard)rallyConnection.updateCard(APStoryObj);
		assertNotNull(APStoryObj);	
		assertEquals(newName, APStoryObj.getName());
		
		/* revert the first story name */
		APStoryObj.setName(oldName);
		APStoryObj = (StoryCard)rallyConnection.updateCard(APStoryObj);
		assertNotNull(APStoryObj);	
		assertEquals(oldName, APStoryObj.getName());
	}
//    
	/**
	 * move a story between iterations, and between
	 * an iteration and the backlog.
	 * 
    * @throws CouldNotLoadProjectException 
    * @throws IndexCardNotFoundException 
    * @throws RemoteException 
	 * 
	 */
	@Test
	public void updateStoryParent() throws CouldNotLoadProjectException, RemoteException, IndexCardNotFoundException {
		/**
		 * get the first story  
		 */
		apProject = rallyConnection.load(rallyProjectName);
		rallyConnection.synchronizeProject(apProject);
		List <Iteration> iters = apProject.getIterationChildren();	
		StoryCard APStoryObj = null; 
		long iter1ID = 0;
		long iter2ID = 0;
		if(iters != null && iters.size() > 1) {
			iter1ID = iters.get(0).getId();
			iter2ID = iters.get(1).getId();
			APStoryObj = iters.get(0).getStoryCardChildren().get(0);
		}		
		assertNotNull(APStoryObj);
		
		rallyConnection.setAPPrjObj(apProject);
		//rallyConnection.setAPPrjObj(apProject);
		/* change the first story parent to the second iteration */
		APStoryObj.setParent(iter2ID);
		assertNotNull(rallyConnection.updateCard(APStoryObj));
		assertEquals(APStoryObj.getParent(), iter2ID);
		
		/* change the first story parent to the backlog */
		APStoryObj.setParent(APBklgID);
		assertNotNull(rallyConnection.updateCard(APStoryObj));
		assertEquals(APBklgID, APStoryObj.getParent());
		rallyConnection.setAPPrjObj(apProject);
		/* change the first story parent back to the first iteration */
		APStoryObj.setParent(iter1ID);
		assertNotNull(rallyConnection.updateCard(APStoryObj));
		assertEquals(APStoryObj.getParent(), iter1ID);
		
	}

	@Test
	public void synch() throws CouldNotLoadProjectException, RemoteException, IndexCardNotFoundException,ForbiddenOperationException{
		
		int numBefore;
		apProject = rallyConnection.load(rallyProjectName);
		rallyConnection.synchronizeProject(apProject);
		
		numBefore = apProject.getBacklog().getStoryCardChildren().size();
		
		
		/////////////New story created at rally///////////////
		StoryCard sc = new StoryCardDataObject();
		sc.setParent(2);
		sc.setActualEffort(0);
		sc.setName("test");
		sc.setDescription("");

		rallyConnection.createStoryCardForRally(sc);

		rallyConnection.synchronizeProject(apProject);

		Assert.assertEquals(numBefore+1, apProject.getBacklog().getStoryCardChildren().size());

		///////////////Story Deleted at Rally//////////////////////////
        
		
		rallyConnection.setAPPrjObj(apProject);
		rallyConnection.deleteCardInRally(sc.getId());

		rallyConnection.synchronizeProject(apProject);

		Assert.assertEquals(numBefore, apProject.getBacklog().getStoryCardChildren().size());

	}
	
	public static junit.framework.Test suite(){
		return new JUnit4TestAdapter(PersisterRallyTest.class);
	}
}
