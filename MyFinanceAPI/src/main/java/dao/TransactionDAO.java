package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.ResumoFinanceiro;
import model.Transaction;


public class TransactionDAO {
	private DataSource dataSource;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	private int chamadosPorPagina = 8;

	public TransactionDAO() throws NamingException {
		InitialContext ctx = new InitialContext();
		dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyFinanceDB");
	}
	
	public List<Transaction> listar(int pagina) throws SQLException {
		List<Transaction> transacoes = new ArrayList<>();
		String sql = "SELECT * FROM Transactions ORDER BY id DESC" + " LIMIT ? OFFSET ?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, chamadosPorPagina);
			stmt.setInt(2, chamadosPorPagina * pagina);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Transaction t = new Transaction(rs.getInt("id"), rs.getString("descricao"), rs.getDouble("valor"), rs.getString("tipo"), rs.getString("categoria"), rs.getDate("data_criacao").toString());
				transacoes.add(t);
			}
		}
		return transacoes;
	}
	
	public void inserir(Transaction transacao) throws SQLException {
		String sql = "INSERT INTO Transactions (descricao, valor, tipo, categoria, data_criacao) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, transacao.getDescricao());
			stmt.setDouble(2, transacao.getValor());
			stmt.setString(3, transacao.getTipo());
			stmt.setString(4, transacao.getCategoria());
			try {
                java.util.Date parsedDate = dateFormat.parse(transacao.getData_criacao());
                stmt.setDate(5, new Date(parsedDate.getTime()));
            } catch (ParseException e) {
                throw new SQLException("Erro ao parsear data: " + transacao.getData_criacao(), e);
            }
			stmt.executeUpdate();
		}
	}
	
	public List<Transaction> buscarPorTipo(String tipo) throws Exception {
		List<Transaction> transacoes = new ArrayList<>();
		String sql = "SELECT * FROM Transactions WHERE tipo = ? ORDER BY id DESC";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, tipo);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Transaction transacao = new Transaction();
				transacao.setId(rs.getInt("id"));
				transacao.setDescricao(rs.getString("descricao"));
				transacao.setValor(rs.getDouble("valor"));
				transacao.setTipo(rs.getString("tipo"));
				transacao.setCategoria(rs.getString("categoria"));
				transacao.setData_criacao(rs.getDate("data_criacao").toString());
				transacoes.add(transacao);
			}
		}
		return transacoes;
	}
	
	public List<Transaction> buscarPorCategoria(String categoria) throws Exception {
		List<Transaction> transacoes = new ArrayList<>();
		String sql = "SELECT * FROM Transactions WHERE categoria = ? ORDER BY id DESC";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, categoria);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Transaction transacao = new Transaction();
				transacao.setId(rs.getInt("id"));
				transacao.setDescricao(rs.getString("descricao"));
				transacao.setValor(rs.getDouble("valor"));
				transacao.setTipo(rs.getString("tipo"));
				transacao.setCategoria(rs.getString("categoria"));
				transacao.setData_criacao(rs.getDate("data_criacao").toString());
				transacoes.add(transacao);
			}
		}
		return transacoes;
	}
	
	public List<Transaction> buscarPorCategoriaETipo(String categoria, String tipo) throws Exception {
		List<Transaction> transacoes = new ArrayList<>();
		String sql = "SELECT * FROM Transactions WHERE categoria = ? AND tipo = ? ORDER BY id DESC";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, categoria);
			stmt.setString(2, tipo);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Transaction transacao = new Transaction();
				transacao.setId(rs.getInt("id"));
				transacao.setDescricao(rs.getString("descricao"));
				transacao.setValor(rs.getDouble("valor"));
				transacao.setTipo(rs.getString("tipo"));
				transacao.setCategoria(rs.getString("categoria"));
				transacao.setData_criacao(rs.getDate("data_criacao").toString());
				transacoes.add(transacao);
			}
		}
		return transacoes;
	}
	
	public Transaction buscarPorId(int id) throws Exception {
		Transaction transacao = null;
		String sql = "SELECT * FROM Transactions WHERE id = ?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				transacao = new Transaction();
				transacao.setId(rs.getInt("id"));
				transacao.setDescricao(rs.getString("descricao"));
				transacao.setValor(rs.getDouble("valor"));
				transacao.setTipo(rs.getString("tipo"));
				transacao.setCategoria(rs.getString("categoria"));
				transacao.setData_criacao(rs.getDate("data_criacao").toString());
			}
		}
		return transacao;
	}
	
	public boolean deletar(int id) throws SQLException {
		String sql = "DELETE FROM Transactions WHERE id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
		}
	}
	
	public boolean atualizar(int id, Transaction transacao) throws SQLException {
		String sql = "UPDATE Transactions SET descricao = ?, valor = ?, tipo = ?, categoria = ? WHERE id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, transacao.getDescricao());
			stmt.setDouble(2, transacao.getValor());
			stmt.setString(3, transacao.getTipo());
			stmt.setString(4, transacao.getCategoria());
			stmt.setInt(5, id);
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
		}
	}
	
	public ResumoFinanceiro obterResumo() throws Exception{
		ResumoFinanceiro resumo = new ResumoFinanceiro();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try (Connection conn = dataSource.getConnection();){
			String sqlReceitas = "SELECT COALESCE(SUM(valor), 0) FROM Transactions WHERE tipo = 'receita'";
            stmt = conn.prepareStatement(sqlReceitas);
            rs = stmt.executeQuery();
            if (rs.next()) {
            	resumo.setTotalReceitas(rs.getDouble(1));
            }
            rs.close();
            stmt.close();
            
            
            String sqlDespesas = "SELECT COALESCE(SUM(valor), 0) FROM Transactions WHERE tipo = 'despesa'";
            stmt = conn.prepareStatement(sqlDespesas);
            rs = stmt.executeQuery();
            if (rs.next()) {
            	resumo.setTotalDespesas(rs.getDouble(1));
            }
            rs.close();
            stmt.close();
            
            resumo.setSaldoAtual(resumo.getTotalReceitas() - resumo.getTotalDespesas());
            
            Map<String, Double> despesasPorCategoria = new HashMap<>();
            String sqlDespesasPorCategoria = "SELECT categoria, COALESCE(SUM(valor), 0) FROM Transactions WHERE tipo = 'despesa' GROUP BY categoria";
            stmt = conn.prepareStatement(sqlDespesasPorCategoria);
            rs = stmt.executeQuery();
            while (rs.next()) {
                despesasPorCategoria.put(rs.getString("categoria"), rs.getDouble(2));
            }
            resumo.setDespesasPorCategoria(despesasPorCategoria);
            rs.close();
            stmt.close();

            Map<String, Double> receitasPorCategoria = new HashMap<>();
            String sqlReceitasPorCategoria = "SELECT categoria, COALESCE(SUM(valor), 0) FROM Transactions WHERE tipo = 'receita' GROUP BY categoria";
            stmt = conn.prepareStatement(sqlReceitasPorCategoria);
            rs = stmt.executeQuery();
            while (rs.next()) {
                receitasPorCategoria.put(rs.getString("categoria"), rs.getDouble(2));
            }
            resumo.setReceitasPorCategoria(receitasPorCategoria);
            rs.close();
            stmt.close();
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resumo;
	}
}
