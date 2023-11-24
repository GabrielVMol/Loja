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
import br.com.loja.gabrielvinicius.models.Cliente;
import br.com.loja.gabrielvinicius.repositories.ClienteRepository;
import br.com.loja.gabrielvinicius.services.ClienteService;
import br.com.prova.gabrielvinicius.mocks.ClienteMock;

class ClienteTeste {
	
	ClienteMock input;
	
	@InjectMocks
	private ClienteService service;
	
	@Mock
	ClienteRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new ClienteMock();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testGetCliente() {
		Cliente entidade = input.mockEntity(1);
	    entidade.setId(1);
	    
	    when(repository.findById(1)).thenReturn(Optional.of(entidade));

	    assertCliente(entidade, 1);
	}
	
	@Test
	void testListAll() {
		List<Cliente> list = input.mockEntityList(); 
		when(repository.findAll()).thenReturn(list);
		
		assertNotNull(list);
		assertEquals(14, list.size());
		
		assertClienteList(list);
	}
	
	@Test
	void testSalvarCliente() {
		Cliente entidade = input.mockEntity(1); 
		entidade.setId(1);
		
		assertCliente(entidade, 1);
	}

	@Test
	void testUpdateCliente() {
		Cliente entidadeOriginal = input.mockEntity(1);
		Cliente entidadeAtualizada = input.mock(2);
	    
	    when(repository.findById(1)).thenReturn(Optional.of(entidadeOriginal));
	    when(repository.save(entidadeOriginal)).thenReturn(entidadeOriginal);
	    when(repository.findById(2)).thenReturn(Optional.of(entidadeAtualizada));
	    when(repository.save(entidadeAtualizada)).thenReturn(entidadeAtualizada);

	    Cliente resultado = service.updateCliente(entidadeAtualizada);

	    assertAll("Verificar resultados",
	        () -> assertClienteEquals(entidadeAtualizada, resultado),
	        () -> assertClienteEquals(entidadeOriginal, entidadeOriginal)
	    );
	}
	
	@Test
	void testExcluirCliente() {
		Cliente entidade = input.mockEntity(1); 
		entidade.setId(1);
		
		when(repository.findById(1)).thenReturn(Optional.of(entidade));
		
		service.excluirCliente(entidade);
	}
	
	@Test
	void testSalvarClienteNulo() { 
		Exception excecao = assertThrows(ObjetoObrigatorioExcecaoNula.class,
				() -> {service.salvarCliente(null);});
		
		String mensagemAtual = excecao.getMessage();
		String MensagemEsperada = "Não é permitido inserir um objeto nulo!";
		  
		
		assertEquals(mensagemAtual,MensagemEsperada);
	}
	
	@Test
	void testExcluirClienteNulo() { 
		Exception excecao = assertThrows(ObjetoObrigatorioExcecaoNula.class,
				() -> {service.excluirCliente(null);});
		
		String mensagemAtual = excecao.getMessage();
		String MensagemEsperada = "Não é permitido inserir um objeto nulo!";
		  
		
		assertEquals(mensagemAtual,MensagemEsperada);
	}
	
	@Test
	void testUpdateClienteNulo() {
		Exception excecao = assertThrows(ObjetoObrigatorioExcecaoNula.class, () -> {
			service.updateCliente(null);
		});
		
		String mensagemAtual = excecao.getMessage();
		String MensagemEsperada = "Não é permitido inserir um objeto nulo!";
	
		assertEquals(mensagemAtual,MensagemEsperada);
	}
	
	private void assertClienteEquals(Cliente esperado, Cliente atual) {
	    assertAll("Cliente",
	        () -> assertNotNull(atual),
	        () -> assertNotNull(atual.getId()),
	        () -> assertEquals(esperado.getNome(), atual.getNome()),
	        () -> assertEquals(esperado.getCpf(), atual.getCpf()),
	        () -> assertEquals(esperado.getTelefone(), atual.getTelefone()),
	        () -> assertEquals(esperado.getEmail(), atual.getEmail()),
	        () -> assertEquals(esperado.getEndereco(), atual.getEndereco()),
	        () -> assertNotNull(atual.getDataCadastro()),	      
	        () -> assertEquals(true, service.validaCliente(atual.getCpf(), atual.getEmail()))
	    );
	}
	
	private void assertCliente(Cliente cliente, int numero) {
	    assertAll(
	        () -> assertNotNull(cliente),
	        () -> assertNotNull(cliente.getId()),
	        () -> assertEquals("Nome Teste " + numero, cliente.getNome()),
	        () -> assertEquals(numero, cliente.getCpf()),
	        () -> assertEquals("Telefone Teste " + numero, cliente.getTelefone()),
	        () -> assertEquals("Email Teste " + numero, cliente.getEmail()),
	        () -> assertEquals("Endereco Teste " + numero, cliente.getEndereco()),
	        () -> assertNotNull(cliente.getDataCadastro()),
	        () -> assertEquals(true, service.validaCliente(cliente.getCpf(), cliente.getEmail()))
	    );
	}
	
	private void assertClienteList(List<Cliente> clientees) {
		for (Cliente cliente : clientees) {
			assertAll(
			        () -> assertNotNull(cliente),
			        () -> assertNotNull(cliente.getId()));
		}	
	}
}