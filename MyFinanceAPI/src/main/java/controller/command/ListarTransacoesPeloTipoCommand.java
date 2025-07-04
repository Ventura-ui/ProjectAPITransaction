package controller.command;

import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import dao.TransactionDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Transaction;

public class ListarTransacoesPeloTipoCommand implements Command {

	@Override
	public void executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getPathInfo();

		if (path == null || !path.startsWith("/tipo/") || path.length() <= "/tipo/".length()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo inválido na URL. Formato esperado: /tipo/{valor}");
			return;
		}

		String tipo = path.substring("/tipo/".length());
		TransactionDAO dao = new TransactionDAO();
		List<Transaction> lista = dao.buscarPorTipo(tipo);
		if (lista != null || !lista.isEmpty()) {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(new Gson().toJson(lista));
			out.flush();
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Nenhuma transação com esse tipo.");
		}
	}
}
