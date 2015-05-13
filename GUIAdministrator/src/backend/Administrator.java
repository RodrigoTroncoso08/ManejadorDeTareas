package backend;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class Administrator {

	ArrayList<Proyect> Proyects;
	ArrayList<String> PosibleContext; //restringe que los usuarios pongan cualquier cosa en contexto
	ArrayList<Color> PosibleContextColor=new ArrayList<Color>();
	
	public Administrator ()
	{
		Proyects = new ArrayList<Proyect>();
		PosibleContext = new ArrayList<String>();
	}
	public boolean AddProyect (Proyect p)
	{
		boolean b = false;                      //si el nombre del proyecto que se quiere 
		ArrayList<String> ar = ProjectNames();  //ingrresar no existe ya, se ingresa y se 
		for (String s : ar)                     //retorna true, sino no se ingresa y se 
		{                                       //retorna false
			if(s.equals(p.getName()))
				b = true;
		}
		if(!b)
		{
			Proyects.add(p);
			return true;
		}
		else
			return false;
		
	}
	
	public ArrayList<String> ProjectNames ()
	{
		ArrayList<String> r = new ArrayList<String>();
		for(Proyect p : Proyects)
		{
			r.add(p.getName());
		}
		return r;
	}
	
	public Color AddContext(String cont) ////Al agregar un contexto revisa si este ya existe y sino lo crea. Devuelve el color asociado al contexto
	{
		if(PosibleContext.contains(cont))
		{
			return PosibleContextColor.get(PosibleContext.indexOf(cont));
		}
		Random r = new Random();
		PosibleContext.add(cont);
		Color aux = new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
		PosibleContextColor.add(aux);
		return aux;
		
	}
	
	/*estos metodos se basan en que al agrupar las tareas sin importar de que proyecto
	 * viene cada una, es como tener un solo gran proyecto al que se le agregan las 
	 * tareas y ese calcula en orden cuales pertencen a que grupo. Para no perder la 
	 * identificacion de la tarea, le agregue un identificador a la tarea con el numero
	 * de identificacion de su proyeto.
	*/
	public ArrayList<Task> TodayTasks()
	{
		
		Proyect aux = new Proyect("Aux"); //funcion add de proyect ya ordena por fecha
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
		
		Proyect aux = new Proyect("Aux"); //funcion add de proyect ya ordena por fecha
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
		
		Proyect aux = new Proyect("Aux"); //funcion add de proyect ya ordena por fecha
		for(Proyect p : Proyects)
		{
			for(Task t : p.WeekTasks())
			{
				aux.AddTask(t);
			}
		}
		return aux.getTasks();
	}
	public ArrayList<String> getPosibleContext()
	{
		return PosibleContext;	
	}	
	public ArrayList<Proyect> getProyects()
	{
		return Proyects;
	}
}
