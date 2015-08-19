package android.palharini.myhealth.ws.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import android.palharini.myhealth.ws.ConectaSQL;
import android.palharini.myhealth.ws.entidades.Indicador;

public class IndicadorDAO {

	public boolean cadastrarIndicador (Indicador indicador) {
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryCadastra = "INSERT INTO indicadores VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryCadastra);
			
			ppStat.setInt(1, indicador.getIdTipo());
			ppStat.setInt(2, indicador.getIdUsuario());
			ppStat.setDouble(3, indicador.getMedida1());
			ppStat.setDouble(4, indicador.getMedida2());
			ppStat.setString(5, indicador.getUnidade());
			ppStat.setString(6, indicador.getData());
			ppStat.setString(7, indicador.getHora());
			
			
			ppStat.executeUpdate();
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean atualizarIndicador(Indicador indicador){
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryAtualiza = "UPDATE indicadores SET idTipo = ?, medida1 = ?, medida2 = ?, unidade = ? WHERE id = ?";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryAtualiza);
			
			ppStat.setInt(1, indicador.getIdTipo());
			ppStat.setDouble(2, indicador.getMedida1());
			ppStat.setDouble(3, indicador.getMedida2());
			ppStat.setString(4, indicador.getUnidade());
			ppStat.setInt(5, indicador.getId());
		
			ppStat.executeUpdate();
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean excluirIndicador (Integer id) {
		
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryExclui = "DELETE FROM indicadores WHERE id = ?";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryExclui);
			
			ppStat.setInt(1, id);
			
			ppStat.executeUpdate();
			
			conexao.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public Indicador buscarIndicadorId (Integer id) {
		
		Indicador indicador = null;
		
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryBusca = "SELECT * FROM indicadores WHERE id = ?";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryBusca);
			
			ppStat.setInt(1, id);
			
			ResultSet resultado = ppStat.executeQuery();
			
			if (resultado.next()) {
				indicador = new Indicador();
				
				indicador.setId(resultado.getInt(1));
				indicador.setIdTipo(resultado.getInt(2));
				indicador.setIdUsuario(resultado.getInt(3));
				indicador.setMedida1(resultado.getDouble(4));
				indicador.setMedida2(resultado.getDouble(5));
				indicador.setUnidade(resultado.getString(6));
				indicador.setData(resultado.getString(7));
				indicador.setHora(resultado.getString(8));
				
			}
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return indicador;
	
	}
	
	public Indicador buscarIndicadorTipo (Integer idUsuario, Integer idTipo, Integer limite) {
		
		Indicador indicador = null;
		
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryBusca = "SELECT * FROM indicadores WHERE idUsuario = ? AND idTipo = ? ORDER BY data DESC, hora DESC LIMIT ?";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryBusca);
			
			ppStat.setInt(1, idUsuario);
			ppStat.setInt(2, idTipo);
			ppStat.setInt(3, limite);
			
			ResultSet resultado = ppStat.executeQuery();
			
			if (resultado.next()) {
				indicador = new Indicador();
				
				indicador.setId(resultado.getInt(1));
				indicador.setIdTipo(resultado.getInt(2));
				indicador.setIdUsuario(resultado.getInt(3));
				indicador.setMedida1(resultado.getDouble(4));
				indicador.setMedida2(resultado.getDouble(5));
				indicador.setUnidade(resultado.getString(6));
				indicador.setData(resultado.getString(7));
				indicador.setHora(resultado.getString(8));
				
			}
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return indicador;
	
	}
	
	public ArrayList<Indicador> buscarIndicadoresTipo (Integer idUsuario, Integer idTipo) {
		
		Indicador indicador = null;
		
		ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
		
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryBusca = "SELECT * FROM indicadores WHERE idUsuario = ? AND idTipo = ? ORDER BY data DESC, hora DESC";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryBusca);
			
			ppStat.setInt(1, idUsuario);
			ppStat.setInt(2, idTipo);
			
			ResultSet resultado = ppStat.executeQuery();
			
			while (resultado.next()) {
				indicador = new Indicador();
				
				indicador.setId(resultado.getInt(1));
				indicador.setIdTipo(resultado.getInt(2));
				indicador.setIdUsuario(resultado.getInt(3));
				indicador.setMedida1(resultado.getDouble(4));
				indicador.setMedida2(resultado.getDouble(5));
				indicador.setUnidade(resultado.getString(6));
				indicador.setData(resultado.getString(7));
				indicador.setHora(resultado.getString(8));
				
				indicadores.add(indicador);
			}
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return indicadores;
	
	}
	
	public ArrayList<Indicador> buscarIndicadoresPeriodoTipo (Integer idUsuario, Integer idTipo, String periodo, String dataAtual, Integer difData) {
		
		Indicador indicador = null;
		
		ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
		
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryBusca = "SELECT * FROM indicadores WHERE idUsuario = ? AND idTipo = ? AND DATE_SUB(?,INTERVAL ? " + periodo + ") < data";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryBusca);
			
			ppStat.setInt(1, idUsuario);
			ppStat.setInt(2, idTipo);
			ppStat.setString(3, dataAtual);
			ppStat.setInt(4, difData);
			
			ResultSet resultado = ppStat.executeQuery();
			
			while (resultado.next()) {
				indicador = new Indicador();
				
				indicador.setId(resultado.getInt(1));
				indicador.setIdTipo(resultado.getInt(2));
				indicador.setIdUsuario(resultado.getInt(3));
				indicador.setMedida1(resultado.getDouble(4));
				indicador.setMedida2(resultado.getDouble(5));
				indicador.setUnidade(resultado.getString(6));
				indicador.setData(resultado.getString(7));
				indicador.setHora(resultado.getString(8));
				
				indicadores.add(indicador);
			}
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return indicadores;
	}
	
	public Double buscarMedia1Periodo (Integer idTipo, Integer idUsuario, String periodo, String dataAtual, Integer difData) {
		
		Double media1 = null;
		
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryBusca = "SELECT AVG(medida1) FROM indicadores WHERE idTipo = ? AND idUsuario = ? AND DATE_SUB(?, INTERVAL ? " + periodo + ") = data";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryBusca);
			
			ppStat.setInt(1, idTipo);
			ppStat.setInt(2, idUsuario);
			ppStat.setString(3, dataAtual);
			ppStat.setInt(4, difData);
			
			ResultSet resultado = ppStat.executeQuery();
			
			if (resultado.next()) {		
				media1 = resultado.getDouble(1);
			}
			else{
				return null;
			}
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return media1;
	}
	
	public Double buscarMedia2Periodo (Integer idTipo, Integer idUsuario, String periodo, String dataAtual, Integer difData) {
		
		Double media2 = null;
		
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryBusca = "SELECT AVG(medida2) FROM indicadores WHERE idTipo = ? AND idUsuario = ? AND DATE_SUB(?, INTERVAL ? " + periodo + ") = data";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryBusca);
			
			ppStat.setInt(1, idTipo);
			ppStat.setInt(2, idUsuario);
			ppStat.setString(3, dataAtual);
			ppStat.setInt(4, difData);
			
			ResultSet resultado = ppStat.executeQuery();
			
			if (resultado.next()) {		
				media2 = resultado.getDouble(1);
			}
			else{
				return null;
			}
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return media2;
	}
}