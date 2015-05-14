package backend;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Task {
	
	String Name;
	String Description;
	Color Context; 	//Lo puse como string dado que me parecio practico por el momento
	State state;
	Calendar Deadline;
	int WorkingDays;
	int Relevance;
	double Urgency;  	//lo puse double dado que puede ser el resultado de algun procesamiento
	int Progress;
	ArrayList<Task> RequireTasks;
	int ProyectId; // necesario para poder saber de manera facil el color de la tarea (proyeto al que pertenece)
	Color color;  //lo agregue para gemerarlo al iniciar un proyecto y se,le agrega a cada tarea cuando se agrega a un proyecto
	

	////Metodos
	//voy a hacer que hayan varios constructores
	public Task(String name) //pide lo menos posible para crear una tarea
	{                        //dasen: le saque lo de la fecha, que en realidad lo puede pedir despues
		Name=name;
		state= State.Active;
	}
	
	public void AddRequireTask(Task t)
	{
		RequireTasks.add(t);
	}
	public boolean RemoveRequireTask(Task t) 	//Si esta y lo remueve=true, si no esta =false
	{
		boolean r = RequireTasks.contains(t);
		if(r)
			RequireTasks.remove(t);
		return r; 
	}
	
	public void ExtraDay ()
	{
		Deadline.add(Calendar.DAY_OF_MONTH, 1);		
	}
	
	public void ExtraWeek ()
	{
		Deadline.add(Calendar.DAY_OF_MONTH, 7);
	}
	
	////Getters y Setters
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public Color getContext() {
		return Context;
	}
	public void setContext(Color context) {
		Context = context;
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
	public int getWorkingDays() {
		return WorkingDays;
	}
	public void setWorkingDays(int workingDays) {
		WorkingDays = workingDays;
	}
	public int getRelevance() {
		return Relevance;
	}
	public void setRelevance(int relevance) {
		this.Relevance = relevance;
	}
	public double getUrgency() {
		return Urgency;
	}
	public void setUrgency(double urgency) {
		Urgency = urgency;
	}
	public int getProgress() {
		return Progress;
	}
	public void setProgress(int progress) {
		this.Progress = progress;
	}
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
	

}
