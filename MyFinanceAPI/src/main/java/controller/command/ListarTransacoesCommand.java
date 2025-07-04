package controller.command;

import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import dao.TransactionDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Transaction;

public class ListarTransacoesCommand implements Command{
	
	private static final int DEFAULT_PAGE = 0;
	
	@Override
	public void executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pagina = DEFAULT_PAGE;
		
		String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                pagina = Integer.parseInt(pageParam);
                if (pagina < 0) { 
                    pagina = DEFAULT_PAGE;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
		
		TransactionDAO dao = new TransactionDAO();
		List<Transaction> transacoes = dao.listar(pagina);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(new Gson().toJson(transacoes));
		out.flush();
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
