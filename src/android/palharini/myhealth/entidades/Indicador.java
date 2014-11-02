package android.palharini.myhealth.entidades;

public class Indicador {

	private Integer id;
	private Integer idTipo;
	private Integer idUsuario;
	private Double medida;
	private String unidade;
	private String data;
	private String hora;
	
	public Indicador(){}

	public Indicador(Integer id, Integer idTipo, Integer idUsuario, Double medida,
			String unidade, String data, String hora) {
		this.id = id;
		this.idTipo = idTipo;
		this.idUsuario = idUsuario;
		this.medida = medida;
		this.unidade = unidade;
		this.data = data;
		this.hora = hora;
	}
	
	public Indicador(Integer id, Integer idTipo, Double medida,
			String unidade) {
		this.id = id;
		this.idTipo = idTipo;
		this.medida = medida;
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

	public Double getMedida() {
		return medida;
	}

	public void setMedida(Double medida) {
		this.medida = medida;
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