package controller.chain;

import controller.command.ListarTransacoesPeloTipoCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetTransacoesPorTipoHandler extends AbstractHandler{
	
	@Override
    protected boolean canHandle(HttpServletRequest request) {
        return request.getMethod().equals("GET") && request.getPathInfo() != null && request.getPathInfo().startsWith("/tipo/");
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        new ListarTransacoesPeloTipoCommand().executar(request, response);
    }

}
