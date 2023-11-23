package br.com.prova.gabrielvinicius.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.loja.gabrielvinicius.models.Produto;

public class ProdutoMock {

	public Produto mockEntity() {
		return mockEntity(0);
	}

	public Produto Mock() {
		return mock(0);
	}

	public List<Produto> mockEntityList() {
		List<Produto> produtos = new ArrayList<Produto>();
		for (int i = 1; i < 15; i++) {
			produtos.add(mockEntity(i));
		}
		return produtos;
	}

	public Produto mockEntity(Integer number) {
		Produto produto = new Produto();
		produto.setId(number);
		produto.setNome("Nome Teste " + number);
		produto.setGrupo("Grupo Teste " + number);
		produto.setDescricao("Descricao Teste " + number);
		produto.setPreco((number).doubleValue());
		produto.setEstoque(number);
		produto.setQuantidade(number);
		return produto;
	}

	public Produto mock(Integer number) {
		Produto produto = new Produto();
		produto.setId(number);
		produto.setNome("Nome Teste " + number);
		produto.setGrupo("Grupo Teste " + number);
		produto.setDescricao("Descricao Teste " + number);
		produto.setPreco((number).doubleValue());
		produto.setEstoque(number);;
		produto.setQuantidade(number);
		return produto;
	}
}