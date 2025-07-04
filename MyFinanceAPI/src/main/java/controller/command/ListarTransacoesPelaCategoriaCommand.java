package controller.command;

import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import dao.TransactionDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Transaction;

public class ListarTransacoesPelaCategoriaCommand implements Command {

	@Override
	public void executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getPathInfo();

		if (path == null || !path.startsWith("/categoria/") || path.length() <= "/categoria/".length()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Tipo inválido na URL. Formato esperado: /categoria/{valor}");
			return;
		}

		String categoria = path.substring("/categoria/".length());
		TransactionDAO dao = new TransactionDAO();
		List<Transaction> lista = dao.buscarPorCategoria(categoria);
		if (lista != null || !lista.isEmpty()) {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(new Gson().toJson(lista));
			out.flush();
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Nenhuma transação com essa categoria.");
		}

	}

}
