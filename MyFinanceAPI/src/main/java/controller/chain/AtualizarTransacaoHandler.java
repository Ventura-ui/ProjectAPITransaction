package controller.chain;

import controller.command.AtualizarTransacaoCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AtualizarTransacaoHandler extends AbstractHandler{
	
	@Override
	protected boolean canHandle(HttpServletRequest request) {
		return request.getMethod().equals("PUT") && request.getPathInfo() != null && request.getPathInfo().matches("^/\\d+$");
	}

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		new AtualizarTransacaoCommand().executar(request, response);
	}

}
