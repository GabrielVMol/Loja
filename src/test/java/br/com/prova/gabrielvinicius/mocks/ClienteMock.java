package br.com.prova.gabrielvinicius.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.loja.gabrielvinicius.models.Cliente;

public class ClienteMock {

	public Cliente mockEntity() {
        return mockEntity(0);
    }
	
	public Cliente Mock() {
		return mock(0);
	}

	public List<Cliente> mockEntityList() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        for (int i = 1; i < 15; i++) {
            clientes.add(mockEntity(i));
        }
        return clientes;
    }
    
    public Cliente mockEntity(Integer number) {
        Cliente cliente = new Cliente();
        cliente.setId(number);
        cliente.setNome("Nome Teste " + number);
        cliente.setCpf(number);
        cliente.setTelefone("Telefone Teste " + number);
        cliente.setEmail("Email Teste " + number);
        cliente.setEndereco("Endereco Teste " + number);
        cliente.setDataCadastro(new Date());
        return cliente;
    }

    public Cliente mock(Integer number) {
        Cliente cliente = new Cliente();
        cliente.setId(number);
        cliente.setNome("Nome Teste " + number);
        cliente.setCpf(number);
        cliente.setTelefone("Telefone Teste " + number);
        cliente.setEmail("Email Teste " + number);
        cliente.setEndereco("Endereco Teste " + number);
        cliente.setDataCadastro(new Date());
        return cliente;
    }
}
