package android.palharini.myhealth.ws.entidades;

public class Preferencias {

	private int id;
	private int idUsuario;
	private boolean lembretePeso;
	private String horaLembretePeso;
	private boolean lembreteBPM;
	private String horaLembreteBPM;
	
	public Preferencias (){}
	
	public Preferencias(int id, int idUsuario, boolean lembretePeso,
			String horaLembretePeso, boolean lembreteBPM, String horaLembreteBPM) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.lembretePeso = lembretePeso;
		this.horaLembretePeso = horaLembretePeso;
		this.lembreteBPM = lembreteBPM;
		this.horaLembreteBPM = horaLembreteBPM;
	}

	public Preferencias(int idUsuario, boolean lembretePeso, boolean lembreteBPM) {
		this.idUsuario = idUsuario;
		this.lembretePeso = lembretePeso;
		this.lembreteBPM = lembreteBPM;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return horaLembreteBPM;
	}

	public void setHoraLembreteBPM(String horaLembreteBPM) {
		this.horaLembreteBPM = horaLembreteBPM;
	}
	
}
