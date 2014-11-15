package android.palharini.myhealth.entidades;

public class Indicador {

	private Integer id;
	private Integer idTipo;
	private Integer idUsuario;
	private Double medida1;
	private Double medida2;
	private String unidade;
	private String data;
	private String hora;
	
	public Indicador(){}

	public Indicador(Integer id, Integer idTipo, Integer idUsuario, Double medida1, Double medida2,
			String unidade, String data, String hora) {
		this.id = id;
		this.idTipo = idTipo;
		this.idUsuario = idUsuario;
		this.medida1 = medida1;
		this.medida2 = medida2;
		this.unidade = unidade;
		this.data = data;
		this.hora = hora;
	}
	
	public Indicador(Integer id, Integer idTipo, Double medida1, Double medida2,
			String unidade) {
		this.id = id;
		this.idTipo = idTipo;
		this.medida1 = medida1;
		this.medida2 = medida2;
		this.unidade = unidade;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Double getMedida1() {
		return medida1;
	}

	public void setMedida1(Double medida1) {
		this.medida1 = medida1;
	}
	
	public Double getMedida2() {
		return medida2;
	}

	public void setMedida2(Double medida2) {
		this.medida2 = medida2;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	@Override
	public String toString() {
		return this.data + " " + this.hora;
	}
	
}