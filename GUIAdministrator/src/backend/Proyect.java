package backend;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.joda.time.DateTimeComparator;

public class Proyect implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3854624837071197097L;
	String Name;
	int Id;
	String Description;
	State state;
	Calendar Deadline;
	ArrayList<Task> Tasks= new ArrayList<Task>();
	int Progress;
	ArrayList<String> Members = new ArrayList<String>(); //la idea es tener los email de quienes participen del proyecto
	Color color;
	//Metodos
	
	public Color getColor() {
		return color;
	}
	public Proyect(String name)
	{
		Name=name;
		Random r = new Random();
		color=new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
	}
	public void AddTask(Task t) //mantiene ordenada la lista
	{
		
		if(Tasks.isEmpty())
			Tasks.add(t);
		else if(t.getDeadline()==null)
			Tasks.add(t);
		else
		{
			for(int i=0; i<Tasks.size();i++)
			{
				
				if(Tasks.get(i).Deadline==null)
				{
					Tasks.add(i,t);
					break;
				}
				else if(Tasks.get(i).Deadline.compareTo(t.Deadline)<0) //la tarea t viene despues que la evaluada
					continue;
				else if(Tasks.get(i).Deadline.compareTo(t.Deadline)>0) 
					{
						Tasks.add(i, t);
						return;
					}
				else if(Tasks.get(i).Deadline.compareTo(t.Deadline)==0) //estan en la  misma fecha
				{
					if(Tasks.get(i).WorkingDays>=t.WorkingDays) //el tiempo de trabajo de t es menor
						continue;
					else
					{
						Tasks.add(i, t);
						return;
					}
				}
				
			}
			Tasks.add(t);	//si no se incerta entremedio, queda al ultimo
		}
		
	}
	public ArrayList<Task> TodayTasks()
	{
		
		ArrayList<Task> TodayTasks = new ArrayList<Task>();
		for(Task t: Tasks)
		{
			if(DateTimeComparator.getDateOnlyInstance().compare(new Date(), t)==0)
				TodayTasks.add(t);
			
		}
		return TodayTasks;
	}
	public ArrayList<Task> ThreeDayTasks()
	{
		
		ArrayList<Task> TodayTasks = new ArrayList<Task>();
		Calendar c= new GregorianCalendar();
		c.add(Calendar.DAY_OF_MONTH,3);
		Date d = c.getTime();
		
		for(Task t: Tasks)
		{
			if(DateTimeComparator.getDateOnlyInstance(). //plazo de 3 dias
					compare(d ,t.Deadline)>0 &&DateTimeComparator.getDateOnlyInstance().
							compare(new Date() ,t.Deadline)<=0)
				TodayTasks.add(t);
			
		}
		return TodayTasks;
	}
	public ArrayList<Task> WeekTasks()

	{
		
		ArrayList<Task> TodayTasks = new ArrayList<Task>();
		Calendar c= new GregorianCalendar();
		c.add(Calendar.DAY_OF_MONTH,7);
		Date d = c.getTime();
		
		for(Task t: Tasks)
		{
			if(DateTimeComparator.getDateOnlyInstance(). //plazo de 7 dias
					compare(d ,t.Deadline)>0 &&DateTimeComparator.getDateOnlyInstance().
							compare(new Date() ,t.Deadline)<=0)
				TodayTasks.add(t);
			
		}
		return TodayTasks;
	}
	public void AddMember(String s)
	{
		Members.add(s);
	}
	public boolean RemoveMember(String s) 	
	{
		if(Members.contains(s))
			Members.remove(s);
		return Members.contains(s); 
	}
	public ArrayList<Task> ActiveTasks()
	{
		ArrayList<Task> Aux = new ArrayList<Task>();
		for(Task t : Tasks)
		{
			if(t.getState()==State.Active)
				Aux.add(t);
		}		
		return Aux;
	}

	////Getters y Setters
	public ArrayList<Task> getTasks()
	{
		return Tasks;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Calendar getDeadline() {
		return Deadline;
	}
	public void setDeadline(Calendar deadline) {
		Deadline = deadline;
	}
	public int getProgress() {
		return Progress;
	}
	public void setProgress(int progress) {
		Progress = progress;
	}
}

