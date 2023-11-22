package br.com.loja.gabrielvinicius.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja.gabrielvinicius.models.Produto;
import br.com.loja.gabrielvinicius.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public void salvarProduto(Produto produto) {
		produtoRepository.save(produto);
	}

	public List<Produto> listAll() {
		return produtoRepository.findAll();
	}

	public Optional<Produto> getProduto(int id) {
		return produtoRepository.findById(id);
	}

	public void excluirProduto(Produto produto) {
		produtoRepository.delete(produto);
	}

	public boolean verificarNome(String nome) {
		List<Produto> produtos = listAll();
		for (Produto produto : produtos) {
			if (produto.getNome() == nome) {
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

	public void diminuirEstoque(int quantidade, List<Produto> produtos) {
		for (Produto produto : produtos) {
			Integer estoque = produto.getEstoque();
            estoque -= quantidade;
        }
	}

	public Boolean validaProduto(String nome, int id, Produto produto) {
		List<Produto> produtos = listAll();
		for (Produto p : produtos) {
			if (nome == p.getNome() && id == p.getId() && produto.getEstoque() < 0)
				return true;
		}
			return false;
		}
}