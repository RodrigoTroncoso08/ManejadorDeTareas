import java.util.ArrayList;
import java.util.Date;


public class Task {
	
	String name;
	String Description;
	String Context; 	//Lo puse como string dado que me parecio practico por el momento
	State state;
	Date Deadline;
	int WorkingDays;
	int relevance;
	double Urgency;  	//lo puse double dado que puede ser el resultado de algun procesamiento
	int progress;
	ArrayList<Task> RequireTasks;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getContext() {
		return Context;
	}
	public void setContext(String context) {
		Context = context;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Date getDeadline() {
		return Deadline;
	}
	public void setDeadline(Date deadline) {
		Deadline = deadline;
	}
	public int getWorkingDays() {
		return WorkingDays;
	}
	public void setWorkingDays(int workingDays) {
		WorkingDays = workingDays;
	}
	public int getRelevance() {
		return relevance;
	}
	public void setRelevance(int relevance) {
		this.relevance = relevance;
	}
	public double getUrgency() {
		return Urgency;
	}
	public void setUrgency(double urgency) {
		Urgency = urgency;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public void AddRequireTask(Task t)
	{
		RequireTasks.add(t);
	}
	public boolean RemoveRequireTask(Task t) 	//Si esta y lo remueve=true, si no esta =false
	{
		if(RequireTasks.contains(t))
			RequireTasks.remove(t);
		return RequireTasks.contains(t); 
	}
	
	
	

}
