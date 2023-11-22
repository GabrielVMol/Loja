package br.com.loja.gabrielvinicius.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExcecaoRecursoNaoEncontrado extends RuntimeException{ // Exceção para quando o recurso não é encontrado
	
	private static final long serialVersionUID = 1L;
	
	public ExcecaoRecursoNaoEncontrado(String ex) {
		super(ex);
	}
}