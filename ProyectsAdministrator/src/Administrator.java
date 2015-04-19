import java.util.ArrayList;
import java.util.Date;


public class Administrator {

	ArrayList<Proyect> Proyects;
	
	/*estos metodos se basan en que al agrupar las tareas sin importar de que proyecto
	 * viene cada una, es como tener un solo gran proyecto al que se le agregan las 
	 * tareas y ese calcula en orden cuales pertencen a que grupo. Para no perder la 
	 * identificacion de la tarea, le agregue un identificador a la tarea con el numero
	 * de identificacion de su proyeto.
	*/
	public ArrayList<Task> TodayTasks()
	{
		
		Proyect aux = new Proyect("Aux", new Date()); //funcion add de proyect ya ordena por fecha
		for(Proyect p : Proyects)
		{
			for(Task t : p.TodayTasks())
			{
				aux.AddTask(t);
			}
		}
		return aux.TodayTasks();
	}
	public ArrayList<Task> ThreeDayTasks()
	{
		
		Proyect aux = new Proyect("Aux", new Date()); //funcion add de proyect ya ordena por fecha
		for(Proyect p : Proyects)
		{
			for(Task t : p.ThreeDayTasks())
			{
				aux.AddTask(t);
			}
		}
		return aux.ThreeDayTasks();
	}
	public ArrayList<Task> WeekTasks()
	{
		
		Proyect aux = new Proyect("Aux", new Date()); //funcion add de proyect ya ordena por fecha
		for(Proyect p : Proyects)
		{
			for(Task t : p.WeekTasks())
			{
				aux.AddTask(t);
			}
		}
		return aux.WeekTasks();
	}

}
