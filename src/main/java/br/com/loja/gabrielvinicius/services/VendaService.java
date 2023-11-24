package br.com.loja.gabrielvinicius.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja.gabrielvinicius.excecoes.ExcecaoRecursoNaoEncontrado;
import br.com.loja.gabrielvinicius.excecoes.ObjetoObrigatorioExcecaoNula;
import br.com.loja.gabrielvinicius.models.Cliente;
import br.com.loja.gabrielvinicius.models.Produto;
import br.com.loja.gabrielvinicius.models.Venda;
import br.com.loja.gabrielvinicius.models.Vendedor;
import br.com.loja.gabrielvinicius.repositories.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private ProdutoService produtoService;

	public void salvarVenda(Venda venda) {
		if (venda == null) {
			throw new ObjetoObrigatorioExcecaoNula();
		}

		var cliente = venda.getCliente();
		var vendedor = venda.getVendedor();
		Boolean valida = venda.getDataVenda().after(venda.getDataGarantia());
		Boolean verificaQuantidade = verificaQuantidade(venda.getProdutos(), venda);
		
		

		if (validaVenda(cliente, vendedor, valida, verificaQuantidade, venda) == true) {
			vendaRepository.save(venda);
		}
	}

	public List<Venda> listAll() {
		return vendaRepository.findAll();
	}

	public Optional<Venda> getVenda(int id) {
		if (vendaRepository.findById(id) == null) {
			throw new ObjetoObrigatorioExcecaoNula();
		}
		vendaRepository.findById(id)
				.orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Nenhum registro encontrado para este ID!"));
		return vendaRepository.findById(id);
	}

	public void excluirVenda(Venda venda) {
		if (venda == null) {
			throw new ObjetoObrigatorioExcecaoNula();
		}
		vendaRepository.delete(venda);
	}

	public Venda updateVenda(Venda venda) {
		if (venda == null)
			throw new ObjetoObrigatorioExcecaoNula();

		var entidade = vendaRepository.findById(venda.getId())
				.orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Nenhum registro encontrado para este ID!"));
		vendaRepository.save(entidade);
		return entidade;
	}

	public boolean verificaId(int id) {
		List<Venda> vendas = listAll();
		for (Venda venda : vendas) {
			if (venda.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public boolean verificaQuantidade(List<Produto> produtos, Venda venda) {
		for (Produto produto : produtos) {
			if (produto.getEstoque() < produto.getQuantidade()) {
				return true;
			}
		}
		return false;
	}

	public boolean validaVenda(Cliente cliente, Vendedor vendedor, Boolean data, Boolean verificaQuantidade,
			Venda venda) {
		if (cliente == null || vendedor == null)
			return false;
		if (data == true || verificaQuantidade == true)
			return false;
		else
			return true;
	}

	public void atualizaVenda(Venda venda) {
		Double total = 0.0;
		for (Produto produto : venda.getProdutos()) {
			total = (produto.getPreco() * produto.getQuantidade());
			produtoService.atualizaProduto(produto);
			venda.setValorTotal(venda.getValorTotal() + total);
		}
	}	
}