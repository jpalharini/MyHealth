package android.palharini.myhealth.ws.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.palharini.myhealth.ws.ConectaSQL;
import android.palharini.myhealth.ws.entidades.Usuario;

public class UsuarioDAO {

	public boolean cadastrarUsuario (Usuario usuario) {
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryCadastra = "INSERT INTO usuarios "
					+ "VALUES (null, ?, ?, ?, ?, ?)";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryCadastra, 
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			
			ppStat.setString(1, usuario.getEmail());
			ppStat.setString(2, usuario.getSenha());
			ppStat.setString(3, usuario.getNome());
			ppStat.setString(4, usuario.getDataNascimento());
			ppStat.setInt(5, usuario.getAlvoBPM());
			
			ppStat.executeUpdate();
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean atualizarUsuario (Usuario usuario){
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryAtualiza = 
					"UPDATE usuarios SET "
					+ "email = ?, "
					+ "senha = ?, "
					+ "nome = ?, "
					+ "nasc = ?, "
					+ "alvoBPM = ? "
					+ "WHERE id = ?";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryAtualiza);
			
			ppStat.setString(1, usuario.getEmail());
			ppStat.setString(2, usuario.getSenha());
			ppStat.setString(3, usuario.getNome());
			ppStat.setString(4, usuario.getDataNascimento());
			ppStat.setInt(5, usuario.getAlvoBPM());
			
			ppStat.setInt(6, usuario.getId());
			
			ppStat.executeUpdate();
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean atualizarAlvoBPM (int id, int alvoBPM) {
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryAtualizaAlvoBPM = 
					"UPDATE usuarios SET "
					+ "email = COALESCE(null, email), "
					+ "senha = COALESCE(null, senha), "
					+ "nome = COALESCE(null, nome),"
					+ "nasc = COALESCE(null, nasc),"
					+ "alvoBPM = COALESCE(?, alvoBPM) "
					+ "WHERE id = ?";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryAtualizaAlvoBPM);

			ppStat.setInt(1, alvoBPM);
			ppStat.setInt(2, id);
			
			ppStat.executeUpdate();
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean excluirUsuario (int id) {
		
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryExclui = "DELETE FROM usuarios WHERE id = ?";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryExclui);
			
			ppStat.setInt(1, id);
			
			ppStat.executeQuery();
			
			conexao.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public Usuario buscarUsuario (int id){
		Usuario dados = null;
		
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryBusca = "SELECT * FROM usuarios WHERE id = ?";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryBusca);
			
			ppStat.setInt(1, id);
			
			ResultSet resultado = ppStat.executeQuery();
			
			if (resultado.next()){
				dados = new Usuario();
				
				dados.setId(resultado.getInt(1));
				dados.setEmail(resultado.getString(2));
				dados.setNome(resultado.getString(4));
				dados.setDataNascimento(resultado.getString(5));
				dados.setAlvoBPM(resultado.getInt(6));
			}
			else{
				return null;
			}
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dados;
	}
	
	public Usuario buscarUsuarioEmail (String email){
		Usuario dados = null;
		
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryBusca = "SELECT * FROM usuarios WHERE email = ?";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryBusca);
			
			ppStat.setString(1, email);
			
			ResultSet resultado = ppStat.executeQuery();
			
			if (resultado.next()){
				dados = new Usuario();
				
				dados.setId(resultado.getInt(1));
				dados.setEmail(resultado.getString(2));
				dados.setSenha(resultado.getString(3));
				dados.setNome(resultado.getString(4));
			}
			else{
				return dados;
			}
			
			conexao.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dados;
	}
	
	public Integer buscarIdadeUsuario (Integer id) {
	
		Integer idade = null;
		
		try {
			Connection conexao = ConectaSQL.obtemConexao();
			
			String queryBusca = "SELECT FLOOR(DATEDIFF (CURDATE(), nasc) / 365.25) FROM usuarios WHERE id=?;";
			
			PreparedStatement ppStat = conexao.prepareStatement(queryBusca);
			
			ppStat.setInt(1, id);
			
			ResultSet resultado = ppStat.executeQuery();
			
			if (resultado.next()){
				idade = resultado.getInt(1);
			}
			
			else {
				return null;
			}
			
			conexao.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idade;
	}
}