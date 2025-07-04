package controller;

import java.util.HashMap;
import java.util.Map;

import controller.command.AtualizarTransacaoCommand;
import controller.command.BuscarTransacaoPorIdCommand;
import controller.command.BuscarTransacoesPorCategoriaETipoCommand;
import controller.command.Command;
import controller.command.CriarTransacaoCommand;
import controller.command.DeletarTransacaoCommand;
import controller.command.ListarTransacoesCommand;
import controller.command.ListarTransacoesPelaCategoriaCommand;
import controller.command.ListarTransacoesPeloTipoCommand;
import controller.command.ObterResumoFinanceiroCommand;
import jakarta.servlet.http.HttpServletRequest;

public class TransactionDispatcher {

	private final Map<String, Command> rotas = new HashMap<>();

	public TransactionDispatcher() {
		rotas.put("GET:/paginated", new ListarTransacoesCommand());
		rotas.put("POST:/", new CriarTransacaoCommand());
		rotas.put("GET:/id", new BuscarTransacaoPorIdCommand());
		rotas.put("PUT:/id", new AtualizarTransacaoCommand());
		rotas.put("DELETE:/id", new DeletarTransacaoCommand());
		rotas.put("GET:/tipo", new ListarTransacoesPeloTipoCommand());
		rotas.put("GET:/resumo", new ObterResumoFinanceiroCommand());
		rotas.put("GET:/categoria", new ListarTransacoesPelaCategoriaCommand());
		rotas.put("GET:/categoriaetipo", new BuscarTransacoesPorCategoriaETipoCommand());
	}

	public Command resolver(HttpServletRequest request) {
		String method = request.getMethod();
		String path = request.getPathInfo();
		if (path == null || path.equals("/")) {
			return rotas.get(method + ":/");
		} else if (path.equals("/paginated")) {
			return rotas.get(method + ":/paginated");
		} else if (path.matches("^/\\d+$")) {
			return rotas.get(method + ":/id");
		}else if (path.startsWith("/tipo/")) {
			return rotas.get(method + ":/tipo");
		}else if (path.startsWith("/categoria/")) { 
			return rotas.get(method + ":/categoria");
		} else if (path.startsWith("/categoriaetipo/") && path.contains("+")) { 
			return rotas.get(method + ":/categoriaetipo");
		} else if (path.equals("/resumo")) { 
            return rotas.get(method + ":/resumo");
        }
		return null;
	}

}
