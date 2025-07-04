package controller.command;

import java.io.BufferedReader;
import java.io.PrintWriter;

import com.google.gson.Gson;

import dao.TransactionDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Transaction;

public class AtualizarTransacaoCommand implements Command{
	
	@Override
	public void executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getPathInfo();
		if (path == null || !path.matches("^/\\d+$")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da transação inválido.");
			return;
		}
		
		int id = Integer.parseInt(path.substring(1));
		BufferedReader reader = request.getReader();
		Transaction transacaoAtualizada = new Gson().fromJson(reader, Transaction.class);
		TransactionDAO dao = new TransactionDAO();
		boolean atualizado = dao.atualizar(id, transacaoAtualizada);
		
		if (atualizado) {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(new Gson().toJson(transacaoAtualizada));
			out.flush();
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transação não encontrada.");
		}
	}

}
