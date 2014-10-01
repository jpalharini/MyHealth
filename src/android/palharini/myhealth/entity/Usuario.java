package android.palharini.myhealth.entity;

public class Usuario {

	private int id;
	private String login;
	private String senha;
	private String nome;
	private String dataNascimento;
	private int altura;
	private int peso;
	private int maxBPM;
	private int minBPM;
	
	public Usuario(){};

	public Usuario(
			int id, String login, String senha, String nome, String dataNascimento, int altura, int peso, int maxBPM, int minBPM) {
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.altura = altura;
		this.peso = peso;
		this.maxBPM = maxBPM;
		this.minBPM = minBPM;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
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
	public int getAltura() {
		return altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
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