package br.com.loja.gabrielvinicius.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja.gabrielvinicius.excecoes.ExcecaoRecursoNaoEncontrado;
import br.com.loja.gabrielvinicius.excecoes.ObjetoObrigatorioExcecaoNula;
import br.com.loja.gabrielvinicius.models.Produto;
import br.com.loja.gabrielvinicius.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public void salvarProduto(Produto produto) {
		if (produto == null)
			throw new ObjetoObrigatorioExcecaoNula();
		produtoRepository.save(produto);
	}

	public List<Produto> listAll() {
		return produtoRepository.findAll();
	}

	public Optional<Produto> getProduto(int id) {
		if (produtoRepository.findById(id) == null) {
			throw new ObjetoObrigatorioExcecaoNula();
		}
		produtoRepository.findById(id)
				.orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Nenhum registro encontrado para este ID!"));
		return produtoRepository.findById(id);
	}

	public Produto updateProduto(Produto produto) {
		if (produto == null)
			throw new ObjetoObrigatorioExcecaoNula();

		var entidade = produtoRepository.findById(produto.getId())
				.orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Nenhum registro encontrado para este ID!"));
		produtoRepository.save(entidade);

		return entidade;
	}

	public void excluirProduto(Produto produto) {
		if (produto == null)
			throw new ObjetoObrigatorioExcecaoNula();
		produtoRepository.delete(produto);
	}

	public boolean verificaParaExcluir(Integer estoque) {
		if (estoque < 1) {
			return true;
		}
		return false;
	}

	public Boolean validaProduto(int estoque, Double preco) {
		if (estoque >= 0 && preco > 0) {
			return true;
		}
		return false;
	}

	public void atualizaProduto(Produto produto) {
		var estoque = produto.getEstoque() - produto.getQuantidade();
		produto.setEstoque(estoque);
		produtoRepository.save(produto);
	}

	public void atualizaProdutoExclusao(Optional<Produto> produto) {
		var estoque = produto.get().getEstoque() + produto.get().getQuantidade();
		produto.get().setEstoque(estoque);
		produtoRepository.save(produto.get());

	}

	public void atualizaListaProdutoExclusao(List<Produto> produtos) {
		for (Produto produto : produtos) {
			var estoque = produto.getEstoque() + produto.getQuantidade();
			produto.setEstoque(estoque);
			produtoRepository.save(produto);
		}
	}

	public void atualizaListaProduto(List<Produto> produtos) {
		for (Produto produto : produtos) {
			var estoque = produto.getEstoque() - produto.getQuantidade();
			produto.setEstoque(estoque);
			produtoRepository.save(produto);
		}
	}
}