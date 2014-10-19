package android.palharini.myhealth.entidades;

public class Preferencias {

	private int idUsuario;
	private boolean lembretePeso;
	private String horaLembretePeso;
	private boolean lembreteBPM;
	private String horaAcorda;
	
	public Preferencias (){}
	
	public Preferencias(int idUsuario, boolean lembretePeso,
			String horaLembretePeso, boolean lembreteBPM, String horaAcorda) {
		this.idUsuario = idUsuario;
		this.lembretePeso = lembretePeso;
		this.horaLembretePeso = horaLembretePeso;
		this.lembreteBPM = lembreteBPM;
		this.horaAcorda = horaAcorda;
	}
	
	public Preferencias(int idUsuario, boolean lembretePeso, boolean lembreteBPM) {
		this.idUsuario = idUsuario;
		this.lembretePeso = lembretePeso;
		this.lembreteBPM = lembreteBPM;
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

	public String getHoraLembretePeso() {
		return horaLembretePeso;
	}

	public void setHoraLembretePeso(String horaLembretePeso) {
		this.horaLembretePeso = horaLembretePeso;
	}

	public boolean isLembreteBPM() {
		return lembreteBPM;
	}

	public void setLembreteBPM(boolean lembreteBPM) {
		this.lembreteBPM = lembreteBPM;
	}

	public String getHoraLembreteBPM() {
		return horaAcorda;
	}

	public void setHoraLembreteBPM(String horaAcorda) {
		this.horaAcorda = horaAcorda;
	}
	
}