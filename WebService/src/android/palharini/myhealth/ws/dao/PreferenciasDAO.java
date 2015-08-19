package android.palharini.myhealth.ws.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.palharini.myhealth.ws.ConectaSQL;
import android.palharini.myhealth.ws.entidades.Preferencias;

public class PreferenciasDAO {

	public boolean cadastrarPreferencias (Preferencias preferencias) {
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryCadastra = "INSERT INTO preferencias VALUES (null, ?, ?, ?, ?, ?)";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryCadastra);
			
			ppStat.setInt(1, preferencias.getIdUsuario());
			ppStat.setBoolean(2, preferencias.isLembretePeso());
			ppStat.setString(3, preferencias.getHoraLembretePeso());
			ppStat.setBoolean(4, preferencias.isLembreteBPM());
			ppStat.setString(5, preferencias.getHoraLembreteBPM());
			
			ppStat.executeUpdate();
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean atualizarPreferencias(Preferencias preferencias){
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryAtualiza = "UPDATE preferencias"
					+ " SET lembretePeso = ?, horaLembretePeso = ?, lembreteBPM = ?, horaLembreteBPM = ?"
					+ " WHERE idUsuario = ?";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryAtualiza);
			
			ppStat.setBoolean(1, preferencias.isLembretePeso());
			ppStat.setString(2, preferencias.getHoraLembretePeso());
			ppStat.setBoolean(3, preferencias.isLembreteBPM());
			ppStat.setString(4, preferencias.getHoraLembreteBPM());
			ppStat.setInt(5, preferencias.getIdUsuario());
		
			ppStat.executeUpdate();
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean excluirPreferencias (int idUsuario) {
		
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryExclui = "DELETE FROM preferencias WHERE idUsuario = ?";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryExclui);
			
			ppStat.setInt(1, idUsuario);
			
			ppStat.executeQuery();
		
			conexao.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public Preferencias buscarPreferencias (int idUsuario){
		Preferencias prefs = null;
		
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryBusca = "SELECT * FROM preferencias WHERE idUsuario = ?";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryBusca);
			
			ppStat.setInt(1, idUsuario);
			
			ResultSet resultado = ppStat.executeQuery();
			
			if (resultado.next()){
				prefs = new Preferencias();

				prefs.setId(resultado.getInt(1));
				prefs.setIdUsuario(resultado.getInt(2));
				prefs.setLembretePeso(resultado.getBoolean(3));
				prefs.setHoraLembretePeso(resultado.getString(4));
				prefs.setLembreteBPM(resultado.getBoolean(5));
				prefs.setHoraLembreteBPM(resultado.getString(6));
			}
			else{
				return prefs;
			}
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prefs;
	}
}