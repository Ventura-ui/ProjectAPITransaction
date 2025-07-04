package controller.command;

import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import dao.TransactionDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Transaction;

public class BuscarTransacoesPorCategoriaETipoCommand implements Command {

    @Override
    public void executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getPathInfo();

        if (path == null || !path.startsWith("/categoriaetipo/") || !path.contains("+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de URL inválido. Esperado: /categoriaetipo/{categoria}+{tipo}");
            return;
        }

        String params = path.substring("/categoriaetipo/".length());
        String[] parts = params.split("\\+");

        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetros de categoria e/ou tipo inválidos. Esperado: /categoriaetipo/{categoria}+{tipo}");
            return;
        }

        String categoria = parts[0];
        String tipo = parts[1];
        TransactionDAO dao = new TransactionDAO();
        List<Transaction> lista = dao.buscarPorCategoriaETipo(categoria, tipo);

        if (lista != null && !lista.isEmpty()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(lista));
            out.flush();
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Nenhuma transação encontrada para a categoria '" + categoria + "' e tipo '" + tipo + "'.");
        }
    }
}
