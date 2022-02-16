import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Controlador {

	private static Map<Integer, Alumno> listaAlumnos = new TreeMap<Integer, Alumno>();
	private static Map<Integer, Materia> listaMaterias = new TreeMap<Integer, Materia>();
	private static Map<Integer, Inscripciones> listaInscripciones = new TreeMap<Integer, Inscripciones>();
	private static String[] lista;
	private Alumno alumno;
	private Materia materia;
	private Inscripciones inscripcion;
	private Random rand = new Random();
	private Pattern patron;
	private Boolean checkPromo;

	public Boolean getCheckPromo() {
		return checkPromo;
	}

	//Remover
	public Boolean QuitarInscripcion(Integer id) {
		try {
			listaInscripciones.remove(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Boolean QuitarMateria(Integer id) {
		try {
			Inscripciones s = null;
			Integer[] idsRemover = new Integer[listaInscripciones.size()];
			int pos = 0;

			for (Iterator it = listaInscripciones.entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();

				s = (Inscripciones) entry.getValue();
				
				if (s.getNroMateria().equals(id))
					{
					idsRemover[pos] = s.getIdInscrip();
					pos++;
					}
			}
			for(int i = 0; i < pos; i++)
				listaInscripciones.remove(idsRemover[i]);
			
			listaMaterias.remove(id);

		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Error.");
			return false;
		}
		return true;
	}

	public Boolean QuitarAlumno(Integer id) {
		try {
			Inscripciones s = null;
			Integer[] idsRemover = new Integer[listaInscripciones.size()];
			int pos = 0;

			for (Iterator it = listaInscripciones.entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();

				s = (Inscripciones) entry.getValue();
				
				if (s.getNroLibreta().equals(id))
				{
					idsRemover[pos] = s.getIdInscrip();
					pos++;
					}
			}
			for(int i = 0; i < pos; i++)
				listaInscripciones.remove(idsRemover[i]);
			
			listaAlumnos.remove(id);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error.");
			return false;
		}
		return true;
	}

	//Modificar
	public void ModificarMateria(Integer id, String nombre) {
		listaMaterias.get(id).setNombre(nombre);
	}

	public void ModificarAlumno(Integer id, String nombre, String apellido) {
		listaAlumnos.get(id).setNombre(nombre);
		listaAlumnos.get(id).setApellido(apellido);
	}

	public Boolean ModificarInscripcion(Integer id, String nombreMateria, Double p1, Double p2) {
		if (listaInscripciones.containsKey(id)) {
			Integer nroMateria = null;
			Materia s = null;
			for (Iterator it = listaMaterias.entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();
				s = (Materia) entry.getValue();

				if (nombreMateria.equals(s.getNombre()))
					nroMateria = s.getIDnroMateria();
			}
			listaInscripciones.get(id).setNroMateria(nroMateria);
			listaInscripciones.get(id).setNotaParcial1(p1);
			listaInscripciones.get(id).setNotaParcial2(p2);

			return true;
		}
		return false;
	}

	//Agregar
	public Integer AgregarInscripcion(Integer nroLibreta, String materia, Double notaParcial1, Double notaParcial2) {
		Integer nroMateria = null;
		Integer iDnro = rand.nextInt(899) + 100;
		Materia s = null;
		Inscripciones t = null;

		try {
			while (listaAlumnos.containsKey(iDnro))
				iDnro = rand.nextInt(899) + 100;

			for (Iterator it = listaMaterias.entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();
				s = (Materia) entry.getValue();

				if (materia.equals(s.getNombre()))
					nroMateria = s.getIDnroMateria();
			}
			for (Iterator it = listaInscripciones.entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();
				t = (Inscripciones) entry.getValue();

				if (t.getNroLibreta().equals(nroLibreta) && t.getNroMateria().equals(nroMateria))
					throw new Exception();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "El alumno ya se encuentra inscripto.");
			return 0;
		}

		inscripcion = new Inscripciones(iDnro, nroLibreta, nroMateria, notaParcial1, notaParcial2);
		listaInscripciones.put(iDnro, inscripcion);

		return iDnro;
	}

	public Integer AgregarMateria(String nombre) {
		Materia s;

		for (Iterator it = listaMaterias.entrySet().iterator(); it.hasNext();) {
			Entry entry = (Entry) it.next();
			s = (Materia) entry.getValue();

			nombre = Normalizer.normalize(nombre, Normalizer.Form.NFD);
			nombre = nombre.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

			if (s.getNombre().trim().toLowerCase().equals(nombre.trim().toLowerCase()))
				return null;
		}

		Integer iDnro = rand.nextInt(899999) + 100000;

		while (listaMaterias.containsKey(iDnro))
			iDnro = rand.nextInt(899999) + 100000;

		materia = new Materia(iDnro, nombre);
		listaMaterias.put(iDnro, materia);
		return iDnro;
	}

	public Integer AgregarAlumno(String apellido, String nombre) {
		Integer iDnroLibreta;
		try {
			iDnroLibreta = rand.nextInt(8999) + 1000;

			while (listaAlumnos.containsKey(iDnroLibreta))
				iDnroLibreta = rand.nextInt(8999) + 1000;

			alumno = new Alumno(iDnroLibreta, apellido, nombre);
			listaAlumnos.put(iDnroLibreta, alumno);
		} catch (Exception e) {
			return null;
		}
		return iDnroLibreta;
	}

	//Obtener datos
	public String[][] getDatosInscripciones() {

		int pos = 0;
		Inscripciones s = null;

		String[][] datosArray = new String[listaInscripciones.size()][7];

		try {
			for (Iterator it = listaInscripciones.entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();
				s = (Inscripciones) entry.getValue();
				datosArray[pos][0] = String.valueOf(((Inscripciones) s).getIdInscrip());
				datosArray[pos][1] = listaAlumnos.get(s.getNroLibreta()).getApellido() + " "
						+ listaAlumnos.get(s.getNroLibreta()).getNombre();
				datosArray[pos][2] = String.valueOf(((Inscripciones) s).getNroLibreta());
				datosArray[pos][3] = listaMaterias.get(s.getNroMateria()).getNombre();
				datosArray[pos][4] = String.valueOf(((Inscripciones) s).getNotaParcial1());
				datosArray[pos][5] = String.valueOf(((Inscripciones) s).getNotaParcial2());
				datosArray[pos][6] = ((Inscripciones) s).getEstado();

				pos++;
			}
		} catch (NullPointerException np) {
			JOptionPane.showMessageDialog(null,
					"El alumno o materia de la lista, no está registrado/a en la base de datos. \n\nCargue las listas de Materias y Alumnos.");
			datosArray = null;
		}
		return datosArray;
	}

	public String[][] getListaPromocionados(String nombreMateria) {

		String[][] datos = new String[listaInscripciones.size()][4];
		checkPromo = false;
		Integer nroMateria = null;
		int pos = 0;
		Materia s = null;
		Inscripciones p = null;

		for (Iterator it = listaMaterias.entrySet().iterator(); it.hasNext();) {
			Entry entry = (Entry) it.next();
			s = (Materia) entry.getValue();

			if (nombreMateria.equals(s.getNombre()))
				nroMateria = s.getIDnroMateria();
		}

		for (Iterator it = listaInscripciones.entrySet().iterator(); it.hasNext();) {
			Entry entry = (Entry) it.next();
			p = (Inscripciones) entry.getValue();

			if (p.getNroMateria().equals(nroMateria))
				if (p.getEstado().equals("PROMOCIONADO")) {
					checkPromo = true;
					datos[pos][0] = listaAlumnos.get(p.getNroLibreta()).getApellido();
					datos[pos][1] = listaAlumnos.get(p.getNroLibreta()).getNombre();
					datos[pos][2] = p.getNotaParcial1().toString();
					datos[pos][3] = p.getNotaParcial2().toString();
				}

			pos++;
		}

		return datos;
	}

	public String[][] getDatosMaterias() {

		int pos = 0;
		Materia s = null;

		String[][] datosArray = new String[listaMaterias.size()][2];

		for (Iterator it = listaMaterias.entrySet().iterator(); it.hasNext();) {
			Entry entry = (Entry) it.next();
			s = (Materia) entry.getValue();
			datosArray[pos][0] = String.valueOf(((Materia) s).getIDnroMateria());
			datosArray[pos][1] = String.valueOf(((Materia) s).getNombre());

			pos++;
		}
		return datosArray;
	}

	public String[][] getDatosAlumnos() {

		int pos = 0;
		Alumno s = null;

		String[][] datosArray = new String[listaAlumnos.size()][3];

		for (Iterator it = listaAlumnos.entrySet().iterator(); it.hasNext();) {
			Entry entry = (Entry) it.next();
			s = (Alumno) entry.getValue();
			datosArray[pos][0] = String.valueOf(((Alumno) s).getIDnroLibreta());
			datosArray[pos][1] = String.valueOf(((Alumno) s).getApellido());
			datosArray[pos][2] = String.valueOf(((Alumno) s).getNombre());

			pos++;
		}
		return datosArray;
	}

	public String[] getListaMaterias() {

		lista = new String[listaMaterias.size()];
		int pos = 0;
		Materia s = null;

		for (Iterator it = listaMaterias.entrySet().iterator(); it.hasNext();) {
			Entry entry = (Entry) it.next();
			s = (Materia) entry.getValue();
			lista[pos] = s.getNombre();
			pos++;
		}
		return lista;
	}

	//Guardar
	public Boolean GuardarListaMaterias(File archivo) {
		try {
			if (!archivo.exists())
				archivo.createNewFile();
			FileWriter salida = new FileWriter(archivo.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(salida);
			Materia s = null;

			for (Iterator it = listaMaterias.entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();

				s = (Materia) entry.getValue();

				String linea = s.toString();
				bw.write(linea);
				bw.newLine();
				bw.flush();
			}
			bw.close();
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	public Boolean GuardarListaInscripciones(File archivo) {
		try {
			if (!archivo.exists())
				archivo.createNewFile();
			FileWriter salida = new FileWriter(archivo.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(salida);
			Inscripciones s = null;

			for (Iterator it = listaInscripciones.entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();

				s = (Inscripciones) entry.getValue();

				String linea = s.toString();
				bw.write(linea);
				bw.newLine();
				bw.flush();
			}
			bw.close();
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	public Boolean GuardarListaAlumnos(File archivo) {
		try {
			if (!archivo.exists())
				archivo.createNewFile();
			FileWriter salida = new FileWriter(archivo.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(salida);
			Alumno s = null;

			for (Iterator it = listaAlumnos.entrySet().iterator(); it.hasNext();) {
				Entry entry = (Entry) it.next();

				s = (Alumno) entry.getValue();

				String linea = s.toString();
				bw.write(linea);
				bw.newLine();
				bw.flush();
			}
			bw.close();
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	//Cargar
	public Boolean CargarListaAlumnos(File archivo) {

		try {
			patron = Pattern.compile("\\d{4};((\\w)+|(\\w+[\\s]+\\w+)+);((\\w)+|(\\w+[\\s]+\\w+)+);");
			FileReader entrada = new FileReader(archivo.getAbsolutePath());
			BufferedReader miBuffer = new BufferedReader(entrada);
			String linea = "";
			String[] salida = new String[3];

			while ((linea = miBuffer.readLine()) != null) {

				Matcher match = patron.matcher(linea);
				boolean matches = match.matches();

				if (matches)
					salida = linea.split(";");
				else {
					entrada.close();
					miBuffer.close();
					throw new IOException();
				}

				Alumno alumno = new Alumno(Integer.parseInt(salida[0]), salida[1], salida[2]);

				listaAlumnos.put(Integer.parseInt(salida[0]), alumno);

			}
			entrada.close();
			miBuffer.close();

		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "El archivo no tiene el formato correcto.");
			return false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error.");
			return false;
		}
		return true;
	}

	public Boolean CargarListaMaterias(File archivo) {

		try {
			patron = Pattern.compile("\\d{6};((\\w)+|(\\w+[\\s]+\\w+)+);");
			FileReader entrada = new FileReader(archivo.getAbsolutePath());
			BufferedReader miBuffer = new BufferedReader(entrada);
			String linea = "";
			String[] salida = new String[2];

			while ((linea = miBuffer.readLine()) != null) {

				Matcher match = patron.matcher(linea);
				boolean matches = match.matches();

				if (matches)
					salida = linea.split(";");
				else {
					entrada.close();
					miBuffer.close();
					throw new IOException();
				}

				Materia materia = new Materia(Integer.parseInt(salida[0]), salida[1]);

				listaMaterias.put(Integer.parseInt(salida[0]), materia);

			}
			entrada.close();
			miBuffer.close();

		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "El archivo no tiene el formato correcto.");
			return false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error.");
			return false;
		}
		return true;
	}

	public Boolean CargarListaInscripciones(File archivo) {

		try {
			patron = Pattern.compile("\\d{3};\\d{4};\\d{6};([0-9]|\\.)+;([0-9]|\\.)+;");
			FileReader entrada = new FileReader(archivo.getAbsolutePath());
			BufferedReader miBuffer = new BufferedReader(entrada);
			String linea = "s";
			String[] salida = new String[5];

			while ((linea = miBuffer.readLine()) != null) {

				Matcher match = patron.matcher(linea);
				boolean matches = match.matches();

				if (matches)
					salida = linea.split(";");
				else {
					entrada.close();
					miBuffer.close();
					throw new IOException();
				}

				Inscripciones inscripcion = new Inscripciones(Integer.parseInt(salida[0]), Integer.parseInt(salida[1]),
						Integer.parseInt(salida[2]), Double.parseDouble(salida[3]), Double.parseDouble(salida[4]));

				listaInscripciones.put(Integer.parseInt(salida[0]), inscripcion);

			}
			entrada.close();
			miBuffer.close();

		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "El archivo no tiene el formato correcto.");
			return false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error.");
			return false;
		}
		return true;
	}
}
