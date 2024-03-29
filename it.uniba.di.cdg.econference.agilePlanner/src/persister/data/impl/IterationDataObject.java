package persister.data.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import persister.data.IndexCardWithChildren;
import persister.data.Iteration;
import persister.data.StoryCard;



public class IterationDataObject implements Iteration, Serializable {

    private static final long serialVersionUID = 1899377217425325768L;
    private boolean rallyID;
    private IndexCardWithChildren cardWithKidz = new IndexCardWithChildrenDataObject();

    public IterationDataObject() {
        setId(0);
        setParent(0);
        setName("Default Iteration");
        setDescription("no description");
        setWidth(DEFAULT_WIDTH);
        setHeight(DEFAULT_HEIGHT);
        setLocationX(DEFAULT_LOCATION_X);
        setLocationY(DEFAULT_LOCATION_Y);
        setRotationAngle(0F);
        setStatus(STATUS_DEFINED);
        setRallyID(false);
    }

    public IterationDataObject(String name, String description, int width, int height, int locationX, int locationY, float availableEffort,
            Timestamp startDate, Timestamp endDate, float rotationAngle, boolean rallyID) {
        setId(0);
        setParent(0);
        setName(name);
        setDescription(description);
        setWidth(width);
        setHeight(height);
        setLocationX(locationX);
        setLocationY(locationY);
        setAvailableEffort(availableEffort);
        setRotationAngle(rotationAngle);
        setRallyID(rallyID);
        if (startDate != null) {
            setStartDate(startDate);
        }
        else {
            setStartDate(new Timestamp(System.currentTimeMillis()));
        }
        if (endDate != null) {
            setEndDate(endDate);
        }
        else {
            setEndDate(new Timestamp(System.currentTimeMillis()));
        }

        setStatus(STATUS_DEFINED);
    }

    public IterationDataObject(long id, long parent) {
        this();
        setId(id);
        setParent(parent);
    }

    public IterationDataObject clone() {
        IterationDataObject clone = new IterationDataObject();
        clone.setCardWithKidz(getCardWithKidz().clone());
        clone.setParent(getParent());
        clone.setStatus(getStatus());
        clone.setIdRecievedFromRally(isIdRecievedFromRally());
        clone.setDescription(getDescription());
        clone.setAvailableEffort(getAvailableEffort());
        clone.setStartDate((Timestamp) getStartDate().clone());
        clone.setEndDate((Timestamp) getEndDate().clone());
        clone.setRallyID(getRallyID());
        return clone;
    }

    

    @Override
    public String toString() {
        String iteration = "###################################\n" + "#            Iteration            #\n"
                + "###################################\n\n" + "ID:\t\t" + getId() + "\n" + "Parent:\t\t" + getParent() + "\n" + "Name:\t\t"
                + getName() + "\n" + "Description:\t" + getDescription() + "\n" + "Size:\t\t" + getWidth() + " x " + getHeight() + "\n"
                + "Location:\t(" + getLocationX() + " , " + getLocationY() + ")\n" + "Start date:\t" + getStartDate() + "\n" + "End date:\t"
                + getEndDate() + "\n" + "Available:\t" + getAvailableEffort() + "\n" + "Completed:\t" + getStatus() + "\n\n";
        return iteration;
    }

    // DOES NOT UPDATE PARENT-CHILD RELATIONS!
    public void update(Iteration iteration) {
        setAvailableEffort(iteration.getAvailableEffort());
        setStatus(iteration.getStatus());
        setDescription(iteration.getDescription());
        setEndDate(iteration.getEndDate());
        setHeight(iteration.getHeight());
        setWidth(iteration.getWidth());
        setLocationX(iteration.getLocationX());
        setLocationY(iteration.getLocationY());
        setName(iteration.getName());
        setRotationAngle(iteration.getRotationAngle());
        setRallyID(iteration.getRallyID());
    }

	public boolean getRallyID() {
		return this.rallyID;
	}

	public void setRallyID(boolean rallyID) {
		this.rallyID=rallyID;
		
	}
	
	public boolean equals(Object other){
		Iteration iteration = (Iteration) other;
		return 
			getId() == iteration.getId()
		&&	getStoryCardChildren().equals(iteration.getStoryCardChildren())
		&&	super.equals(other)
		;
	}

	public void addStoryCard(StoryCard storycard) {
		cardWithKidz.addStoryCard(storycard);
	}

	public float getActualEffort() {
		return cardWithKidz.getActualEffort();
	}

	public float getAvailableEffort() {
		return cardWithKidz.getAvailableEffort();
	}

	public float getBestCaseEstimate() {
		return cardWithKidz.getBestCaseEstimate();
	}

	public String getDescription() {
		return cardWithKidz.getDescription();
	}

	public Timestamp getEndDate() {
		return cardWithKidz.getEndDate();
	}

	public int getHeight() {
		return cardWithKidz.getHeight();
	}

	public long getId() {
		return cardWithKidz.getId();
	}

	public int getLocationX() {
		return cardWithKidz.getLocationX();
	}

	public int getLocationY() {
		return cardWithKidz.getLocationY();
	}

	public float getMostlikelyEstimate() {
		return cardWithKidz.getMostlikelyEstimate();
	}

	public String getName() {
		return cardWithKidz.getName();
	}

	public long getParent() {
		return cardWithKidz.getParent();
	}

	public float getRemainingEffort() {
		return cardWithKidz.getRemainingEffort();
	}

	public float getRotationAngle() {
		return cardWithKidz.getRotationAngle();
	}

	public Timestamp getStartDate() {
		return cardWithKidz.getStartDate();
	}

	public String getStatus() {
		return cardWithKidz.getStatus();
	}

	public StoryCard getStoryCard(int i) {
		return cardWithKidz.getStoryCard(i);
	}

	public List<StoryCard> getStoryCardChildren() {
		return cardWithKidz.getStoryCardChildren();
	}

	public int getWidth() {
		return cardWithKidz.getWidth();
	}

	public float getWorstCaseEstimate() {
		return cardWithKidz.getWorstCaseEstimate();
	}

	public boolean isCompleted() {
		return cardWithKidz.isCompleted();
	}

	public boolean isIdRecievedFromRally() {
		return cardWithKidz.isIdRecievedFromRally();
	}

	public boolean isStarted() {
		return cardWithKidz.isStarted();
	}

	public void removeStoryCard(StoryCard storycard) {
		cardWithKidz.removeStoryCard(storycard);
	}

	public void setActualEffort(float actual) {
		cardWithKidz.setActualEffort(actual);
	}

	public void setAvailableEffort(float availableEffort) {
		cardWithKidz.setAvailableEffort(availableEffort);
	}

	public void setBestCaseEstimate(float bestcase) {
		cardWithKidz.setBestCaseEstimate(bestcase);
	}

	public void setDescription(String description) {
		cardWithKidz.setDescription(description);
	}

	public void setEndDate(Timestamp enddate) {
		cardWithKidz.setEndDate(enddate);
	}

	public void setHeight(int height) {
		cardWithKidz.setHeight(height);
	}

	public void setId(long id) {
		cardWithKidz.setId(id);
	}

	public void setIdRecievedFromRally(boolean idRecievedFromRally) {
		cardWithKidz.setIdRecievedFromRally(idRecievedFromRally);
	}

	public void setLocationX(int locationX) {
		cardWithKidz.setLocationX(locationX);
	}

	public void setLocationY(int locationY) {
		cardWithKidz.setLocationY(locationY);
	}

	public void setMostlikelyEstimate(float mostlikely) {
		cardWithKidz.setMostlikelyEstimate(mostlikely);
	}

	public void setName(String name) {
		cardWithKidz.setName(name);
	}

	public void setParent(long id) {
		cardWithKidz.setParent(id);
	}

	public void setRotationAngle(float angle) {
		cardWithKidz.setRotationAngle(angle);
	}

	public void setStartDate(Timestamp startdate) {
		cardWithKidz.setStartDate(startdate);
	}

	public void setStatus(String status) {
		cardWithKidz.setStatus(status);
	}

	public void setStoryCardChildren(List<StoryCard> storycards) {
		cardWithKidz.setStoryCardChildren(storycards);
	}

	public void setWidth(int width) {
		cardWithKidz.setWidth(width);
	}

	public void setWorstCaseEstimate(float worstcase) {
		cardWithKidz.setWorstCaseEstimate(worstcase);
	}

	public int storyCardSize() {
		return cardWithKidz.storyCardSize();
	}

	protected IndexCardWithChildren getCardWithKidz() {
		return cardWithKidz;
	}

	protected void setCardWithKidz(IndexCardWithChildren cardWithKidz) {
		this.cardWithKidz = cardWithKidz;
	}

}
