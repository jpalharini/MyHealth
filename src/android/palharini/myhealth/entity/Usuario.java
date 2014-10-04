package android.palharini.myhealth.entity;

public class Usuario {

	private int id;
	private String email;
	private String senha;
	private String nome;
	private String dataNascimento;
	private Double altura;
	private Double peso;
	private int maxBPM;
	private int minBPM;
	
	public Usuario(){};

	public Usuario(
			int id, String email, String senha, String nome, String dataNascimento, Double altura, Double peso, int maxBPM, int minBPM) {
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.altura = altura;
		this.peso = peso;
		this.maxBPM = maxBPM;
		this.minBPM = minBPM;
	}
	
	public Usuario(
			int id, String email, String senha, String nome) {
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.nome = nome;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Double getAltura() {
		return altura;
	}
	public void setAltura(Double altura) {
		this.altura = altura;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public int getMaxBPM() {
		return maxBPM;
	}
	public void setMaxBPM(int maxBPM) {
		this.maxBPM = maxBPM;
	}
	public int getMinBPM() {
		return minBPM;
	}
	public void setMinBPM(int minBPM) {
		this.minBPM = minBPM;
	}

}