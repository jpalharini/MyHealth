package android.palharini.myhealth.db.entities;

public class User {

	private int id;
	private String email;
	private String senha;
	private String nome;
	private String dataNascimento;
	private int alvoBPM;
	
	public User(){};

	public User(
			int id, String email, String senha, String nome, String dataNascimento) {
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}
	
	public User(int id, String email, String senha, String nome,
			String dataNascimento, int alvoBPM) {
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.alvoBPM = alvoBPM;
	}

	public User(
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

	public int getAlvoBPM() {
		return alvoBPM;
	}
	public void setAlvoBPM(int alvoBPM) {
		this.alvoBPM = alvoBPM;
	}

}