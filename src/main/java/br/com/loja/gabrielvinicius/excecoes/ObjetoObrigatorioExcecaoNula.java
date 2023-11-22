package br.com.loja.gabrielvinicius.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ObjetoObrigatorioExcecaoNula extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ObjetoObrigatorioExcecaoNula() { 
		super("Não é permitido inserir um objeto nulo!");
	}	
	
	public ObjetoObrigatorioExcecaoNula(String ex) {
		super(ex);
	}
}