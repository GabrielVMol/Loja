package br.com.prova.gabrielvinicius.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.loja.gabrielvinicius.models.Cliente;
import br.com.loja.gabrielvinicius.models.Produto;
import br.com.loja.gabrielvinicius.models.Venda;
import br.com.loja.gabrielvinicius.models.Vendedor;

public class VendaMock {
	
	private Cliente clienteMock;
	private Vendedor vendedorMock;
	private List<Produto> produtosMocks;

	public Venda mockEntity(Integer number) {
        return mockEntity(number,clienteMock, vendedorMock, produtosMocks);
    }
	
	public Venda Mock(Integer number) {
		return mock(number,clienteMock, vendedorMock, produtosMocks);
	}

	public List<Venda> mockEntityList() {
        List<Venda> vendas = new ArrayList<Venda>();
        for (int i = 1; i < 15; i++) {
            vendas.add(mockEntity(i));
        }
        return vendas;
    }
    
    public Venda mockEntity(Integer number, Cliente cliente, Vendedor vendedor, List<Produto> list) {
    	 Venda venda = new Venda();
         venda.setId(number);
         venda.setCliente(cliente);
         venda.setVendedor(vendedor);
         venda.setProdutos(list);
         venda.setDataVenda(new Date());
         venda.setDataGarantia(new Date());
         return venda;
    }


	public Venda mock(Integer number, Cliente cliente, Vendedor vendedor, List<Produto> list) {   	
    	
		Venda venda = new Venda();
        venda.setId(number);
        venda.setCliente(cliente);
        venda.setVendedor(vendedor);
        venda.setProdutos(list);
        venda.setDataVenda(new Date());
        venda.setDataGarantia(new Date());
        return venda;
    }
}