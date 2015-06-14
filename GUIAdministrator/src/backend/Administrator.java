package backend;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.io.*;

public class Administrator implements  java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3233903846344009237L;
	ArrayList<Proyect> Proyects;
	ArrayList<String> PosibleContextName=new ArrayList<String>(); //restringe que los usuarios pongan cualquier cosa en contexto
	ArrayList<Context> PosibleContext=new ArrayList<Context>();
	public Administrator ()
	{
		Proyects = new ArrayList<Proyect>();
	}
	
	public ArrayList<Context> getPosibleContextColor() {
		return PosibleContext;
	}
	public void setPosibleContextColor(ArrayList<Context> posibleContext) {
		PosibleContext = posibleContext;
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
	
	public Context AddContext(String cont) ////Al agregar un contexto revisa si este ya existe y sino lo crea. Devuelve el color asociado al contexto
	{
		if(PosibleContextName.contains(cont))
		{
			return PosibleContext.get(PosibleContextName.indexOf(cont));
		}
		Random r = new Random();
		PosibleContextName.add(cont);
		Color aux = new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
		Context context = new Context(cont, aux);
		PosibleContext.add(context);
		return context;
		
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
	public ArrayList<Task> AllTasks()
	{
		
		Proyect aux = new Proyect("Aux"); //funcion add de proyect ya ordena por fecha
		for(Proyect p : Proyects)
		{
			for(Task t : p.getTasks())
			{
				aux.AddTask(t);
			}
		}
		return aux.getTasks();
	}
	
	public ArrayList<String> getPosibleContext()
	{
		return PosibleContextName;	
	}	
	public ArrayList<Proyect> getProyects()
	{
		return Proyects;
	}
	private ArrayList<Task> SortUrgency(ArrayList<Task> A)
	{
		//el tipico sort que divide la lista en 2, y esas en 2 y asi y asi y despues ordena...
		if(A.size()<2)
			return A;
		else
		{
			ArrayList<Task> Ar = new ArrayList<Task>();
			int c1=0;
			int c2=0;
			ArrayList<Task> A3 = new ArrayList<Task>();
			ArrayList<Task> A4 = new ArrayList<Task>();
			for(Task t : A)
			{
				if((double)A.indexOf(t)<A.size()/2.0)
					A3.add(t);
				else
					A4.add(t);
			}
			ArrayList<Task> A1 = SortUrgency(A3);
			ArrayList<Task> A2 = SortUrgency(A4);
			Task t1 = A1.get(0);
			Task t2 = A2.get(0);
			while(c1<A1.size() || c2<A2.size())
			{				
				if(c1<A1.size() && c2<A2.size())
				{
					t1 = A1.get(c1);
					t2 = A2.get(c2);
					if(t1.getUrgency()>=t2.getUrgency())
					{
						Ar.add(t1);
						c1++;
					}
					else
					{
						Ar.add(t2);
						c2++;
					}
				}
				else if (c1<A1.size())
				{
					t1 = A1.get(c1);
					Ar.add(t1);
					c1++;
				}
				else if (c2<A2.size())
				{
					t1 = A2.get(c2);
					Ar.add(t2);
					c2++;
				}				
			}
			return Ar;
		}
		
	}
	private ArrayList<Task> OrdenarPorUrgencia()
	{
		//primero se agregan todas las tareas activas a una lista
		ArrayList<Task> Aux1 = new ArrayList<Task>();
		for(Proyect p: Proyects)
		{
			ArrayList<Task> Auxanidado = p.ActiveTasks();
			for(Task t: Auxanidado)
			{
				//justo antes de agregarse se actualiza su urgencia
				t.setUrgency();
				Aux1.add(t);
			}
		}
		//luego se ordenan todas estas tareas según su urgencia		 
		return SortUrgency(Aux1);
	}
	public String[] SugerenciaUrgencia()
	{
		//entrega a lo mas 5 tareas sugeridas, y sus requerimientos
		
		ArrayList<Task> Aux = OrdenarPorUrgencia();
		String s1 ="Tareas sugeridas: \n";
		String s2="";
		if(Aux.size()<5)
		{
			for(Task t : Aux)
			{
				s1 += t.getName() + ", \n";
				ArrayList<Task> tr = t.getRequireTasks();
				if(tr.size()!=0 || tr!= null)
					{	
						String s3 = "Tareas requeridas por " + t.getName() + ":\n";
						boolean okey = false;
						for(Task trt : tr)
						{
							if(trt.getState()==State.Active)
							{	
								s3 += trt.getName() + " (" + Proyects.get(trt.getProyectId()).getName() +")\n";
								okey = true;
							}
						}
						if(okey)
							s2=s3;
					}
			}
		}
		else
		{
			for(int i = 0; i < 5; i++)
			{
				Task t = Aux.get(i);
				s1 += t.getName() + ", \n";
				ArrayList<Task> tr = t.getRequireTasks();
				if(tr.size()!=0 || tr!= null)
					{	
						String s3 = "Tareas requeridas por " + t.getName() + ":\n";
						boolean okey = false;
						for(Task trt : tr)
						{
							if(trt.getState()==State.Active)
							{	
								//aca supongo que el id de los proyectos es el numero en la lista de proyectos
								s3 += trt.getName() + " (" + Proyects.get(trt.getProyectId()).getName() +")\n";
								okey = true;
							}
						}
						if(okey)
							s2=s3;
					}
			}
		}
		return new String[] {s1, s2};
	}
	public String[] SugerenciaPlanificada()
	{
		//este metodo toma las 10 tareas con mas urgencia y las ordena segun cuales se podrían hacer por contexto
		//luego retorna las 5 primeras
		//primero va la tarea de mayor urgencia
		//luego todas las tareas del mismo contexto dentro de las 5 siguientes (ordenadas por urgencia)
		//luego se hace el mismo proceso con la siguiente tarea de mayor urgencia (que no esté en la lista nueva)
		
		//declaramos algunas variables utiles
		ArrayList<Task> Aux = OrdenarPorUrgencia();
		String s1="Tareas sugeridas: \n";
		String s2="";
		ArrayList<Task> last = new ArrayList<Task>();
		
		//solo lo calcula si tiene mas de 10 tareas activas, sino no vale la pena.
		if(Aux.size()>=10)
		{			
			for(int i = 0; i < 5; i++)
			{
				//si la tarea no esta en la lista final, se agrega
				Task t = Aux.get(i);
				if(!last.contains(t))
				{
					last.add(t);
					//luego se agrega cualquiera de las siguientes 5 del mismo contexto y que no esté en la lista final
					for(int j=1; j<=5; j++)
					{
						Task t2 = Aux.get(i+j);
						if(!last.contains(t2) && t2.getContext()==t.getContext())
							last.add(t2);
					}						
				}
			}
			//la iteracion entrega una lista de entre 5 y 10 tareas, por eso ahora seleccionamos las primeras 5, que son las que se entregan
			for(int i =0;i<5;i++)
			{
				Task t = last.get(i);
				s1 += t.getName() + ", \n";
				ArrayList<Task> tr = t.getRequireTasks();
				if(tr.size()!=0 || tr!= null)
					{	
						String s3 = "Tareas requeridas por " + t.getName() + ":\n";
						boolean okey = false;
						for(Task trt : tr)
						{
							if(trt.getState()==State.Active)
							{									
								s3 += trt.getName() + " (" + Proyects.get(trt.getProyectId()).getName() +")\n";
								okey = true;
							}
						}
						if(okey)
							s2=s3;
					}
			}
			
		}
		else
		{
			s1 = "Tienes tan pocas tareas activas que puedes arreglartelas solo. Exito!";
		}
		return new String[] {s1,s2};
	}

	
	
	
	
	
}
