package controller.chain;

import controller.command.CriarTransacaoCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PostTransacaoHandler extends AbstractHandler{
	
	@Override
	protected boolean canHandle(HttpServletRequest request) {
		return request.getMethod().equals("POST")
				&& (request.getPathInfo() == null || request.getPathInfo().equals("/"));
	}

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		new CriarTransacaoCommand().executar(request, response);
	}

}
