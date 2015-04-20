import java.util.ArrayList;
import java.util.Date;


public class Administrator {

	ArrayList<Proyect> Proyects;
	ArrayList<String> PosibleContext; //restringe que los usuarios no pongan cualquier cosa en contexto
	
	/*estos metodos se basan en que al agrupar las tareas sin importar de que proyecto
	 * viene cada una, es como tener un solo gran proyecto al que se le agregan las 
	 * tareas y ese calcula en orden cuales pertencen a que grupo. Para no perder la 
	 * identificacion de la tarea, le agregue un identificador a la tarea con el numero
	 * de identificacion de su proyeto.
	*/
	public void AddContext(String cont)
	{
		PosibleContext.add(cont);
	}
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
		return aux.getTasks();
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
		return aux.getTasks();
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
		return aux.getTasks();
	}

}
