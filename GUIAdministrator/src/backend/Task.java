package backend;

import java.awt.Color;
import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.management.monitor.Monitor;
import org.joda.time.DateTime;
import org.joda.time.Days;


public class Task implements Comparable<Task>, java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5448777867464402073L;
	String Name;
	String Description;
	Context context; 	//Lo puse como string dado que me parecio practico por el momento
	State state;
	Calendar Deadline;
	int WorkingDays;
	int Relevance;
	double Urgency;  	//lo puse double dado que puede ser el resultado de algun procesamiento
	int Progress;
	ArrayList<Task> RequireTasks;
	int ProyectId; // necesario para poder saber de manera facil el color de la tarea (proyeto al que pertenece)
	Color color;  //lo agregue para gemerarlo al iniciar un proyecto y se,le agrega a cada tarea cuando se agrega a un proyecto
	Boolean Change;
	Boolean[] check;
	

	////Metodos
	//voy a hacer que hayan varios constructores
	public Task(String name) //pide lo menos posible para crear una tarea
	{                        //dasen: le saque lo de la fecha, que en realidad lo puede pedir despues
		Name=name;
		check = new Boolean[2];  //2 controles, en nodebutton y en guibase
		check[0]=false;
		check[1]=false;
		state= State.Active;
		Change = false;
		Thread clock = new Thread(new Runnable() {
			
			
			public void run() {
				// TODO Auto-generated method stub
				
				while(true)
				{
					//System.out.println("entro");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(Deadline==null)
						continue;
					Calendar aux=Calendar.getInstance();
					int year = aux.get(Calendar.YEAR);
					int day = aux.get(Calendar.DAY_OF_YEAR);
					int yprueba = Deadline.get(Calendar.YEAR);
					int prueba = Deadline.get(Calendar.DAY_OF_YEAR);
					
					if((Deadline.get(Calendar.DAY_OF_YEAR)<day && Deadline.get(Calendar.YEAR)==year)||Deadline.get(Calendar.YEAR)<year)
					{
						setState(State.Delayed);
						Change = true;
						
						}
					try {
						TimeUnit.DAYS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		clock.start();
	}
	
	
	public int getProyectId() {
		return ProyectId;
	}


	public void setProyectId(int proyectId) {
		ProyectId = proyectId;
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
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
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
	public double setUrgency() {
		//explico un poco el metodo, para que generaciones futuras que vengan a arreglar el codigo lo entiendan
		//la Urgencia es negativa, mientras más cercano a 0, más urgente
		//se toma la diferencia en dias entre la fecha de termino y hoy y eso se divide por la relevancia elevada a 0.9
		//así la relevancia es importante pero no tanto como los días.
		//luego se pondera lo que falta (100-progreso)/100 por la cantidad de dias (WD)
		//y a eso se le aplica logaritmo para que sea importante pero no tanto.
		//finalmente se divide la Urgencia por ese termino
		//si una tarea le quedan muchos WorkingDays para terminarse, entonces el logaritmo es mas grande y la urgencia mas cercana a 0
		
		//el unico motivo por el cual es un metodo double es para poder cortarlo en los try and catch. #MalasPracticas
		if(state!=State.Active)
		{
			Urgency=1.0;
			return Urgency;
		}
		//si la tarea no esta activa se retorna 1
		double rel = Math.pow(Relevance, 0.9);
		double U = Urgency;
		try{
		Date finals = Deadline.getTime();
		Date ahora = new Date();
		int days = Days.daysBetween(new DateTime(ahora), new DateTime(finals)).getDays();
		double dias = (double)days;
		U = -1.0*(dias+1.0)/rel;
		}
		catch(Exception e)
		{
			Urgency = 1.0;
			return Urgency;
		}
		//si la tarea no tiene fecha, se retorna 1 (se espera que solo haya errores si no tiene fecha)
		Urgency = U;
		try{
		double logaritmo = Math.log((WorkingDays*((100-Progress)/100))); 
		//si working days es 0, o la tarea esta completada (progress=100) entonces esto debiese tirar un error 
		U = Urgency/logaritmo;
		}
		catch(Exception e)
		{
			return Urgency;
		}
		//en caso de que tire error, no se consideran ni los WD ni el progreso para calcular la urgencia
		Urgency = U;
		return Urgency;
	}
	public ArrayList<Task> getRequireTasks() {
		return RequireTasks;
	}
	public void setRequireTasks(ArrayList<Task> requireTasks) {
		RequireTasks = requireTasks;
	}
	public void setColor(Color color) {
		this.color = color;
	}	
	public void CheckDate()
	{
		Calendar c = Calendar.getInstance();
		if(c.compareTo(Deadline)>0)
		{
			state= State.Delayed;
			Change=true;
			check[0]=false;
			check[1]=false;
		}
		else if(state!=State.Active)
		{
			state= State.Active;
			Change=true;
			check[0]=false;
			check[1]=false;
		}
	}

	@Override
	public int compareTo(Task o) {
		// TODO Auto-generated method stub
		return getDeadline().compareTo(o.getDeadline());
	}
	public boolean getChange() {
		return Change;
	}
	public void setChange(Boolean change) {
		Change = change;
	}
	public void isCheck(int index)
	{
		check[index]=true;
		for(int i =0; i<check.length; i++)
			if(!check[i])
				return ;
		for(int i =0; i<check.length; i++)
			check[i]=false;
				
	}
	
	

}
