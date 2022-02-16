
public class Alumno {

	private Integer IDnroLibreta;
	private String apellido;
	private String nombre;
	
	public Alumno(Integer iDnroLibreta, String apellido, String nombre) {
		super();
		IDnroLibreta = iDnroLibreta;
		this.apellido = apellido;
		this.nombre = nombre;
	}
	
	public Integer getIDnroLibreta() {
		return IDnroLibreta;
	}
	public String getApellido() {
		return apellido;
	}
	public String getNombre() {
		return nombre;
	}

	public void setIDnroLibreta(Integer iDnroLibreta) {
		IDnroLibreta = iDnroLibreta;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return IDnroLibreta+";"+apellido+";"+nombre+";";
	}
	
	
}
