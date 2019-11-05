public class Project{
	int enrolledstudents
	int maxstudents
	int projectID
	int numBoys
	int numgirls

	public Project(int projectID, int maxstudents){
		this.projectID = projectID;
		this.maxstudents = maxstudents;
		
	}

	public Project(int projectID, int enrolledstudents, int numBoys){
		this.projectID = projectID;
		this.enrolledstudents = enrolledstudents;
		this.numBoys = numBoys;
    		this.numGirls = numGirls;
    
	}
}
