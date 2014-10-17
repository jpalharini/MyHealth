package android.palharini.myhealth.entidades;

public class Preferencias {

	private int idUsuario;
	private boolean lembretePeso;
	private String horaLembrete;
	private boolean alvoBPM;
	private String horaAcorda;
	
	public Preferencias (){}
	
	public Preferencias(int idUsuario, boolean lembretePeso,
			String horaLembrete, boolean alvoBPM, String horaAcorda) {
		this.idUsuario = idUsuario;
		this.lembretePeso = lembretePeso;
		this.horaLembrete = horaLembrete;
		this.alvoBPM = alvoBPM;
		this.horaAcorda = horaAcorda;
	}
	
	public Preferencias(int idUsuario, boolean lembretePeso, boolean alvoBPM) {
		this.idUsuario = idUsuario;
		this.lembretePeso = lembretePeso;
		this.alvoBPM = alvoBPM;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean isLembretePeso() {
		return lembretePeso;
	}

	public void setLembretePeso(boolean lembretePeso) {
		this.lembretePeso = lembretePeso;
	}

	public String getHoraLembrete() {
		return horaLembrete;
	}

	public void setHoraLembrete(String horaLembrete) {
		this.horaLembrete = horaLembrete;
	}

	public boolean isAlvoBPM() {
		return alvoBPM;
	}

	public void setAlvoBPM(boolean alvoBPM) {
		this.alvoBPM = alvoBPM;
	}

	public String getHoraAcorda() {
		return horaAcorda;
	}

	public void setHoraAcorda(String horaAcorda) {
		this.horaAcorda = horaAcorda;
	}
	
}