package br.com.prova.gabrielvinicius.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.loja.gabrielvinicius.excecoes.ObjetoObrigatorioExcecaoNula;
import br.com.loja.gabrielvinicius.models.Venda;
import br.com.loja.gabrielvinicius.repositories.VendaRepository;
import br.com.loja.gabrielvinicius.services.VendaService;
import br.com.prova.gabrielvinicius.mocks.ClienteMock;
import br.com.prova.gabrielvinicius.mocks.ProdutoMock;
import br.com.prova.gabrielvinicius.mocks.VendaMock;
import br.com.prova.gabrielvinicius.mocks.VendedorMock;

class VendaTeste {
	
	VendaMock inputVenda;

	VendedorMock inputVendedor;
	ClienteMock inputCliente;
	ProdutoMock inputProduto;
	
	@InjectMocks
	private VendaService service;
	
	@Mock
	VendaRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		inputVenda = new VendaMock();
		inputCliente = new ClienteMock();
		inputProduto = new ProdutoMock();
		inputVendedor = new VendedorMock();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testGetVenda() {
		Venda entidade = gerarEntidade(1); 
	    
	    when(repository.findById(1)).thenReturn(Optional.of(entidade));

	    assertVenda(entidade, 1);
	}
	
	@Test
	void testListAll() {
		List<Venda> list = inputVenda.mockEntityList(); 
		when(repository.findAll()).thenReturn(list);
		
		assertNotNull(list);
		assertEquals(14, list.size());
		
		assertVendaList(list);
	}
	
	@Test
	void testSalvarVenda() {
		Venda entidade = gerarEntidade(1); 
		
		assertVenda(entidade, 1);
	}

	@Test
	void testUpdateVenda() {
		Venda entidadeOriginal = gerarEntidade(1);  
		Venda entidadeAtualizada = gerarEntidade(2);
	    
	    when(repository.findById(1)).thenReturn(Optional.of(entidadeOriginal));
	    when(repository.save(entidadeOriginal)).thenReturn(entidadeOriginal);
	    when(repository.findById(2)).thenReturn(Optional.of(entidadeAtualizada));
	    when(repository.save(entidadeAtualizada)).thenReturn(entidadeAtualizada);

	    Venda resultado = service.updateVenda(entidadeAtualizada);

	    assertAll("Verificar resultados",
	        () -> assertVendaEquals(entidadeAtualizada, resultado),
	        () -> assertVendaEquals(entidadeOriginal, entidadeOriginal)
	    );
	}
	
	@Test
	void testExcluirVenda() {
		Venda entidade = gerarEntidade(1);
		
		when(repository.findById(1)).thenReturn(Optional.of(entidade));
		
		service.excluirVenda(entidade);
	}
	
	@Test
	void testSalvarVendaNulo() { 
		Exception excecao = assertThrows(ObjetoObrigatorioExcecaoNula.class,
				() -> {service.salvarVenda(null);});
		
		String mensagemAtual = excecao.getMessage();
		String MensagemEsperada = "Não é permitido inserir um objeto nulo!";
		  
		
		assertEquals(mensagemAtual,MensagemEsperada);
	}
	
	@Test
	void testExcluirVendaNulo() { 
		Exception excecao = assertThrows(ObjetoObrigatorioExcecaoNula.class,
				() -> {service.excluirVenda(null);});
		
		String mensagemAtual = excecao.getMessage();
		String MensagemEsperada = "Não é permitido inserir um objeto nulo!";
		  
		
		assertEquals(mensagemAtual,MensagemEsperada);
	}
	
	@Test
	void testUpdateVendaNulo() {
		Exception excecao = assertThrows(ObjetoObrigatorioExcecaoNula.class, () -> {
			service.updateVenda(null);
		});
		
		String mensagemAtual = excecao.getMessage();
		String MensagemEsperada = "Não é permitido inserir um objeto nulo!";
	
		assertEquals(mensagemAtual,MensagemEsperada);
	}
	
	private void assertVendaEquals(Venda esperado, Venda atual) {
		
	    assertAll(
	        () -> assertNotNull(atual),
	        () -> assertNotNull(atual.getId()),
	        () -> assertNotNull(atual.getCliente()),
	        () -> assertNotNull(atual.getVendedor()),
	        () -> assertNotNull(atual.getProdutos()),
	        () -> assertNotNull(atual.getDataVenda()),
	        () -> assertNotNull(atual.getDataGarantia()),
	        () -> assertEquals(false, service.validaVenda(atual.getCliente().getId(), atual.getVendedor().getId(), atual.getDataVenda().before(atual.getDataGarantia()), service.verificaQuantidade(atual.getProdutos(), atual)))
	    	);
	}
	
	private void assertVenda(Venda venda, int numero) {
	    assertAll(
	        () -> assertNotNull(venda),
	        () -> assertNotNull(venda.getId()),
	        () -> assertNotNull(venda.getCliente()),
	        () -> assertNotNull(venda.getVendedor()),
	        () -> assertNotNull(venda.getProdutos()),
	        () -> assertNotNull(venda.getDataVenda()),
	        () -> assertNotNull(venda.getDataGarantia()),
	        () -> assertEquals(false, service.validaVenda(venda.getCliente().getId(), venda.getVendedor().getId(), venda.getDataVenda().before(venda.getDataGarantia()), service.verificaQuantidade(venda.getProdutos(), venda)))
	    );
	}
	
	private void assertVendaList(List<Venda> vendaes) {
		for (Venda venda : vendaes) {
			assertAll(
			        () -> assertNotNull(venda),
			        () -> assertNotNull(venda.getId()));
		}	
	}
	
	private Venda gerarEntidade(int i) {
		Venda entidade = inputVenda.mockEntity(i);
		var cliente = inputCliente.mockEntity(i);
		var vendedor = inputVendedor.mockEntity(i);
		var list = inputProduto.mockEntityList();
		entidade.setCliente(cliente);
		entidade.setVendedor(vendedor);
		entidade.setProdutos(list);
	    entidade.setId(i);
	    
	    return entidade;
	}
}