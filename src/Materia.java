
public class Materia {

	private Integer IDnroMateria;
	private String nombre;
	
	public Materia(Integer iDnroMateria, String nombre) {
		super();
		IDnroMateria = iDnroMateria;
		this.nombre = nombre;
	}
	public Integer getIDnroMateria() {
		return IDnroMateria;
	}
	public String getNombre() {
		return nombre;
	}
	
	public void setIDnroMateria(Integer iDnroMateria) {
		IDnroMateria = iDnroMateria;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return IDnroMateria+";"+nombre+";";
	}
	
}
