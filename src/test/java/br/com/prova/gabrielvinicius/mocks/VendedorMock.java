package br.com.prova.gabrielvinicius.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.loja.gabrielvinicius.models.Vendedor;

public class VendedorMock {

	public Vendedor mockEntity() {
        return mockEntity(0);
    }
	
	public Vendedor Mock() {
		return mock(0);
	}

	public List<Vendedor> mockEntityList() {
        List<Vendedor> vendedores = new ArrayList<Vendedor>();
        for (int i = 1; i < 15; i++) {
            vendedores.add(mockEntity(i));
        }
        return vendedores;
    }
    
    public Vendedor mockEntity(Integer number) {
        Vendedor vendedor = new Vendedor();
        vendedor.setId(number);
        vendedor.setNome("Nome Teste " + number);
        vendedor.setCpf(number);
        vendedor.setTelefone("Telefone Teste " + number);
        vendedor.setEmail("Email Teste " + number);
        vendedor.setEndereco("Endereco Teste " + number);
        vendedor.setDataCadastro(new Date());
        vendedor.setSetor("Setor Teste " + number);
        vendedor.setSalario((number).doubleValue());
        return vendedor;
    }

    public Vendedor mock(Integer number) {
    	Vendedor vendedor = new Vendedor();
        vendedor.setId(number);
        vendedor.setNome("Nome Teste " + number);
        vendedor.setCpf(number);
        vendedor.setTelefone("Telefone Teste " + number);
        vendedor.setEmail("Email Teste " + number);
        vendedor.setEndereco("Endereco Teste " + number);
        vendedor.setDataCadastro(new Date());
        vendedor.setSetor("Setor Teste " + number);
        vendedor.setSalario((number).doubleValue());
        return vendedor;
    }
}