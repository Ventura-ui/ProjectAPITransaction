package controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {
	
	void executar(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
