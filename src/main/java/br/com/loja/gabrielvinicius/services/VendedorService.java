package br.com.loja.gabrielvinicius.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		vendedorRepository.save(vendedor);
	}
	
	public List<Vendedor> listAll(){
		return vendedorRepository.findAll();
	}

	public Optional<Vendedor> getVendedor(int id){
		return vendedorRepository.findById(id);
	}
	
	public void excluirVendedor(Vendedor vendedor) {
		vendedorRepository.delete(vendedor);
	}

    public boolean verificarCpf(Integer cpf) {
    	List<Vendedor> vendedores = listAll();
        for (Vendedor vendedor : vendedores) {
            if (vendedor.getCpf() == cpf){
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

	public Vendedor getVendedorNome(String nome) {
		List<Vendedor> vendedores = listAll();
        for (Vendedor vendedor : vendedores) {
        	if(vendedor.getNome() == nome) {
        		return vendedor;
        		}
        	}
		return null;
	}

	public Boolean validaVendedor(Integer cpf, Double salario) {
		List<Vendedor> vendedores = listAll();
		for (Vendedor v : vendedores) {
			if (cpf == v.getCpf()) 
				return true;
		
			if(salario < 0) 
				return true;
		}
			return false;
		}
}