package br.com.loja.gabrielvinicius.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja.gabrielvinicius.excecoes.ExcecaoRecursoNaoEncontrado;
import br.com.loja.gabrielvinicius.excecoes.ObjetoObrigatorioExcecaoNula;
import br.com.loja.gabrielvinicius.models.Venda;
import br.com.loja.gabrielvinicius.models.Vendedor;
import br.com.loja.gabrielvinicius.repositories.VendaRepository;
import br.com.loja.gabrielvinicius.repositories.VendedorRepository;

@Service
public class VendedorService {

	@Autowired
	private VendedorRepository vendedorRepository;
	
	@Autowired
	private VendaRepository vendaRepository;
	
	public void salvarVendedor(Vendedor vendedor) {
		if (vendedor == null) throw new ObjetoObrigatorioExcecaoNula();
		vendedorRepository.save(vendedor);
	}
	
	public List<Vendedor> listAll(){
		return vendedorRepository.findAll();
	}

	public Optional<Vendedor> getVendedor(int id){
		vendedorRepository.findById(id)
				.orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Nenhum registro encontrado para este ID!"));
		return vendedorRepository.findById(id);
	}
	
	public void excluirVendedor(Vendedor vendedor) {
		if (vendedor == null) throw new ObjetoObrigatorioExcecaoNula();
		vendedorRepository.delete(vendedor);
	}
	
	public Vendedor updateVendedor(Vendedor vendedor) {

		if (vendedor == null) throw new ObjetoObrigatorioExcecaoNula();		
		
		var entidade = vendedorRepository.findById(vendedor.getId())
			.orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Nenhum registro encontrado para este ID!"));
		vendedorRepository.save(entidade);
			
		return entidade;
	}

    public boolean verificarCpf(Integer cpf) {
    	List<Vendedor> vendedores = listAll();
        for (Vendedor vendedor : vendedores) {
            if (cpf.equals(vendedor.getCpf())){
                return true; 
            }
        }
        return false;
    }
    
	public boolean verificaSalario(Double salario) {
            if (salario < 0){
                return true; 
            }
        return false;
	}
    
    public Double calcularSalario(Vendedor vendedor) {
    	List<Venda> vendas = vendaRepository.findAll();
    	Double totalVendas = vendas.stream().mapToDouble(Venda :: getValorTotal).sum(); 
    	return vendedor.getSalario() + (0.02 * totalVendas);
    }

	public Boolean validaVendedor(Integer cpf, Double salario) {
		List<Vendedor> vendedores = listAll();
		for (Vendedor v : vendedores) {
			if (cpf == v.getCpf() && salario < 0) 
				return true;
		}
			return false;
		}

	public Boolean verificarEmail(String email) {
		List<Vendedor> vendedores = listAll();
        for (Vendedor vendedor : vendedores) {
            if (vendedor.getEmail().equals(email)){
                return true; 
            }
        }
        return false;
    }
}