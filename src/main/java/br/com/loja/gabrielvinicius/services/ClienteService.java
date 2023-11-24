package br.com.loja.gabrielvinicius.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja.gabrielvinicius.excecoes.ExcecaoRecursoNaoEncontrado;
import br.com.loja.gabrielvinicius.excecoes.ObjetoObrigatorioExcecaoNula;
import br.com.loja.gabrielvinicius.models.Cliente;
import br.com.loja.gabrielvinicius.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public void salvarCliente(Cliente cliente) {
	if (cliente == null) { throw new ObjetoObrigatorioExcecaoNula();}
		clienteRepository.save(cliente);
	}
	
	public List<Cliente> listAll(){
		return clienteRepository.findAll();
	}

	public Optional<Cliente> getCliente(int id){
		if (clienteRepository.findById(id) == null) { throw new ObjetoObrigatorioExcecaoNula();}		
		return clienteRepository.findById(id);
	}
	
	public Cliente updateCliente(Cliente cliente) {

		if (cliente == null) throw new ObjetoObrigatorioExcecaoNula();		
		
		var entidade = clienteRepository.findById(cliente.getId())
			.orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Nenhum registro encontrado para este ID!"));
		clienteRepository.save(entidade);
			
		return entidade;
	}
	
	public void excluirCliente(Cliente cliente) {
		if (cliente == null) { throw new ObjetoObrigatorioExcecaoNula();}
		clienteRepository.findById(cliente.getId())
				.orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Nenhum registro encontrado para este ID!"));
		clienteRepository.delete(cliente);
	}

	public boolean validaCliente(Integer cpf, String email) {
		List<Cliente> clientes = listAll();
		for (Cliente c: clientes) {
			if(cpf.equals(c.getCpf()) || email.equals(c.getEmail())){
				return false;
			}
		}
		return true;
	}
}