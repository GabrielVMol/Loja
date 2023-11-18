package br.com.loja.gabrielvinicius.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja.gabrielvinicius.models.Produto;
import br.com.loja.gabrielvinicius.models.Venda;
import br.com.loja.gabrielvinicius.repositories.ProdutoRepository;
import br.com.loja.gabrielvinicius.repositories.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	
	public void salvarVenda(Venda venda) {
		if(verificaQuantidade(produtoRepository.findAll(), venda)) {
		Double valorTotal = valorTotal(produtoRepository.findAll(), venda);
		venda.setValorTotal(valorTotal);
		vendaRepository.save(venda);
		}
	}
	
	public List<Venda> listAll(){
		return vendaRepository.findAll();
	}

	public Optional<Venda> getVenda(int id){
		return vendaRepository.findById(id);
	}
	
	public void excluirVenda(Venda venda) {
		vendaRepository.delete(venda);
	}

    public boolean verificarId(int id) {
    	List<Venda> vendaes = listAll();
        for (Venda venda : vendaes) {
            if (venda.getId() == id){
                return true; 
            }
        }
        return false;
    }

	public Double valorTotal(List<Produto> produtos, Venda venda) {	
		Double total = null;
		for (Produto produto : produtos) {
			total =+ (produto.getPreco() * venda.getQuantidade());
		}
		return total;
	}
	
	public boolean verificaQuantidade(List<Produto> produtos, Venda venda) {
		Integer quantidadeVenda = venda.getQuantidade();
		for (Produto produto : produtos) {
			if(produto.getEstoque() < quantidadeVenda) {
				 return true;
			 }
		}
		return false;
	}

	public boolean validaVenda(Integer idCliente, Integer idVendedor, Boolean data, Boolean verificaQuantidade) {
		if(idCliente == null && idVendedor == null
			&& data == true && verificaQuantidade == true) {
			return true;
		}
		return false;
	}
}