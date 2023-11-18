package br.com.loja.gabrielvinicius.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja.gabrielvinicius.models.Cliente;
import br.com.loja.gabrielvinicius.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public void salvarCliente(Cliente cliente) {
		clienteRepository.save(cliente);
	}
	
	public List<Cliente> listAll(){
		return clienteRepository.findAll();
	}

	public Optional<Cliente> getCliente(int id){
		return clienteRepository.findById(id);
	}
	
	public void excluirCliente(Cliente cliente) {
		clienteRepository.delete(cliente);
	}

    public boolean verificarCpf(Integer cpf) {
    	List<Cliente> clientes = listAll();
        for (Cliente cliente : clientes) {
            if (cliente.getCpf() == cpf){
                return true; 
            }
        }
        return false;
    }

	public boolean verificaEmail(String email) {
		List<Cliente> clientes = listAll();
        for (Cliente cliente : clientes) {
            if (cliente.getEmail() == email){
                return true; 
            }
        }
        return false;
    }

	public Cliente getClienteNome(String nome) {
		List<Cliente> clientes = listAll();
        for (Cliente cliente : clientes) {
        	if(cliente.getNome() == nome) {
        		return cliente;
        		}
        	}
		return null;
	}

	public Boolean validaCliente(Integer cpf, String email) {
		List<Cliente> clientes = listAll();
		for (Cliente c : clientes) {
			if (cpf == c.getCpf()) 
				return true;
		
			if(email == c.getEmail()) 
				return true;
		}
			return false;
		}
}