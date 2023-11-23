package br.com.loja.gabrielvinicius.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja.gabrielvinicius.excecoes.ExcecaoRecursoNaoEncontrado;
import br.com.loja.gabrielvinicius.excecoes.ObjetoObrigatorioExcecaoNula;
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
		if (venda == null) { throw new ObjetoObrigatorioExcecaoNula();}
		
		Integer cliente =  venda.getCliente().getId();
		Integer vendedor = venda.getVendedor().getId();
		Boolean valida = venda.getDataVenda().after(venda.getDataGarantia());
	    Boolean verificaQuantidade = verificaQuantidade(venda.getProdutos(), venda);
	    
		if (validaVenda(cliente, vendedor, valida, verificaQuantidade)) {
		Double valorTotal = valorTotal(produtoRepository.findAll(), venda);
		venda.setValorTotal(valorTotal);
		vendaRepository.save(venda);
		}
	}
	
	public List<Venda> listAll(){
		return vendaRepository.findAll();
	}

	public Optional<Venda> getVenda(int id){
		if (vendaRepository.findById(id) == null) { throw new ObjetoObrigatorioExcecaoNula();}
		vendaRepository.findById(id)
		.orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Nenhum registro encontrado para este ID!"));
		return vendaRepository.findById(id);
	}
	
	public void excluirVenda(Venda venda) {
		if (venda == null) { throw new ObjetoObrigatorioExcecaoNula();}
		vendaRepository.delete(venda);
	}
	
	public Venda updateVenda(Venda venda) {
		if (venda == null) throw new ObjetoObrigatorioExcecaoNula();		
		
		var entidade = vendaRepository.findById(venda.getId())
			.orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Nenhum registro encontrado para este ID!"));
		vendaRepository.save(entidade);	
		return entidade;
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
			total =+ (produto.getPreco() * produto.getQuantidade());
		}
		return total;
	}
	
	public boolean verificaQuantidade(List<Produto> produtos, Venda venda) {
		for (Produto produto : produtos) {
			if(produto.getEstoque() < produto.getQuantidade()) {
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