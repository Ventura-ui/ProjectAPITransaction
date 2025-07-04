package controller.chain;

import controller.command.ListarTransacoesPelaCategoriaCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetTransacoesPorCategoriaHandler extends AbstractHandler {

	@Override
    protected boolean canHandle(HttpServletRequest request) {
        return request.getMethod().equals("GET") && request.getPathInfo() != null && request.getPathInfo().startsWith("/categoria/");
    }

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		new ListarTransacoesPelaCategoriaCommand().executar(request, response);
	}

}
