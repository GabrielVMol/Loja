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
		if (produto == null) throw new ObjetoObrigatorioExcecaoNula();
		produtoRepository.save(produto);
	}

	public List<Produto> listAll() {
		return produtoRepository.findAll();
	}

	public Optional<Produto> getProduto(int id) {
		if (produtoRepository.findById(id) == null) { throw new ObjetoObrigatorioExcecaoNula();}
		produtoRepository.findById(id)
		.orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Nenhum registro encontrado para este ID!"));
		return produtoRepository.findById(id);
	}
	
	
	public Produto updateProduto(Produto produto) {

		if (produto == null) throw new ObjetoObrigatorioExcecaoNula();		
		
		var entidade = produtoRepository.findById(produto.getId())
			.orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Nenhum registro encontrado para este ID!"));
		produtoRepository.save(entidade);
			
		return entidade;
	}

	public void excluirProduto(Produto produto) {
		if (produto == null) throw new ObjetoObrigatorioExcecaoNula();
		produtoRepository.delete(produto);
	}

	public boolean verificarNome(String nome) {
		List<Produto> produtos = listAll();
		for (Produto produto : produtos) {
			if (produto.getNome().equals(nome)) {
				return true;
			}
		}
		return false;
	}

	public boolean verificarId(int id) {
    	List<Produto> produtos = listAll();
        for (Produto produto : produtos) {
            if (produto.getId() == id){
                return true; 
            }
        }
        return false;
    }

	public boolean verificaParaExcluir(Integer estoque) {
		if (estoque > 0) {
			return true;
		}
		return false;
	}

	public void diminuirEstoque(int quantidade, Produto produto) {
			Integer estoque = produto.getEstoque();
            estoque -= quantidade;
	}

	public Boolean validaProduto(String nome, int id, int estoque) {
		List<Produto> produtos = listAll();
		for (Produto p : produtos) {
			if (p.getNome() == nome && p.getId() == id  && p.getEstoque() < 0)
				return true;
		}
			return false;
		}
}