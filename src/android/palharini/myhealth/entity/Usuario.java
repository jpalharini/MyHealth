package android.palharini.myhealth.entity;

public class Usuario {

	private int id;
	private String nome;
	private String dataNascimento;
	private double altura;
	private double peso;
	private int maxBPM;
	private int minBPM;
	
	public Usuario(){};
	
	public Usuario(
			int id, String nome, String dataNascimento, double altura, double peso) {
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.altura = altura;
		this.peso = peso;
	}

	public Usuario(
			int id, String nome, String dataNascimento, double altura, double peso, int maxBPM, int minBPM) {
		this.id = id;
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
	public double getAltura() {
		return altura;
	}
	public void setAltura(double altura) {
		this.altura = altura;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public int getMaxBPM() {
		return minBPM;
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