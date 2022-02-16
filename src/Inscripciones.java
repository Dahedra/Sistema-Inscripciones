
public class Inscripciones {

	private Integer IdInscrip;
	private Integer nroLibreta;
	private Integer nroMateria;
	private Double notaParcial1;
	private Double notaParcial2;
	private String estado;
	
	public Inscripciones(Integer idInscrip, Integer nroLibreta, Integer nroMateria, Double notaParcial1,
			Double notaParcial2) {
		super();
		IdInscrip = idInscrip;
		this.nroLibreta = nroLibreta;
		this.nroMateria = nroMateria;
		this.notaParcial1 = notaParcial1;
		this.notaParcial2 = notaParcial2;
		
		estado = getEstado();
	}
	public Integer getIdInscrip() {
		return IdInscrip;
	}
	public void setIdInscrip(Integer idInscrip) {
		IdInscrip = idInscrip;
	}
	public Integer getNroLibreta() {
		return nroLibreta;
	}
	public void setNroLibreta(Integer nroLibreta) {
		this.nroLibreta = nroLibreta;
	}
	public Integer getNroMateria() {
		return nroMateria;
	}
	public void setNroMateria(Integer nroMateria) {
		this.nroMateria = nroMateria;
	}
	public Double getNotaParcial1() {
		return notaParcial1;
	}
	public void setNotaParcial1(Double notaParcial1) {
		this.notaParcial1 = notaParcial1;
	}
	public Double getNotaParcial2() {
		return notaParcial2;
	}
	public void setNotaParcial2(Double notaParcial2) {
		this.notaParcial2 = notaParcial2;
	}
	public String getEstado() {
		if(notaParcial1 > 7.99)
			if(notaParcial2 > 7.99)
				return estado = "PROMOCIONADO";
		
		if(notaParcial1 > 5.99)
			if(notaParcial2 > 5.99)
				return estado = "REGULAR";
		
		return estado = "LIBRE";
	}
	@Override
	public String toString() {
		return IdInscrip+";"+nroLibreta+";"+nroMateria+";"+notaParcial1+";"+notaParcial2+";";
	}
	
	
}
