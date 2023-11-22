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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.loja.gabrielvinicius.excecoes.ObjetoObrigatorioExcecaoNula;
import br.com.loja.gabrielvinicius.models.Cliente;
import br.com.loja.gabrielvinicius.repositories.ClienteRepository;
import br.com.loja.gabrielvinicius.services.ClienteService;
import br.com.prova.gabrielvinicius.mocks.ClienteMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
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

	    var resultado = service.getCliente(1);
	    
	    assertAll(
	        () -> assertNotNull(resultado),
	        () -> assertNotNull(resultado.get().getId()),
	        () -> assertEquals("Nome Teste 1", resultado.get().getNome()),
	        () -> assertEquals(1, resultado.get().getCpf()),
	        () -> assertEquals("Telefone Teste 1", resultado.get().getTelefone()),
	        () -> assertEquals("Email Teste 1", resultado.get().getEmail()),
	    	() -> assertEquals("Endereco Teste 1", resultado.get().getEndereco()),
	    	() -> assertNotNull(resultado.get().getDataCadastro()));
	}
	
	@Test
	void testListAll() {
		List<Cliente> list = input.mockEntityList(); 
		
		when(repository.findAll()).thenReturn(list);
		
		var cliente = service.listAll();
		
		assertNotNull(cliente);
		assertEquals(14, cliente.size());
		
		var clienteUm = cliente.get(1);
		
		assertAll(
		        () -> assertNotNull(clienteUm),
		        () -> assertNotNull(clienteUm.getId()),
		        () -> assertEquals("Nome Teste 1", clienteUm.getNome()),
		        () -> assertEquals(1, clienteUm.getCpf()),
		        () -> assertEquals("Telefone Teste 1", clienteUm.getTelefone()),
		        () -> assertEquals("Email Teste 1", clienteUm.getEmail()),
		    	() -> assertEquals("Endereco Teste 1", clienteUm.getEndereco()),
		    	() -> assertNotNull(clienteUm.getDataCadastro()));
		
		var clienteQuadro = cliente.get(4);
		
		assertAll(
		        () -> assertNotNull(clienteQuadro),
		        () -> assertNotNull(clienteQuadro.getId()),
		        () -> assertEquals("Nome Teste 4", clienteQuadro.getNome()),
		        () -> assertEquals(4, clienteQuadro.getCpf()),
		        () -> assertEquals("Telefone Teste 4", clienteQuadro.getTelefone()),
		        () -> assertEquals("Email Teste 4", clienteQuadro.getEmail()),
		    	() -> assertEquals("Endereco Teste 4", clienteQuadro.getEndereco()),
		    	() -> assertNotNull(clienteQuadro.getDataCadastro()));
		
		var clienteSete = cliente.get(7);
		
		assertAll(
		        () -> assertNotNull(clienteSete),
		        () -> assertNotNull(clienteSete.getId()),
		        () -> assertEquals("Nome Teste 7", clienteSete.getNome()),
		        () -> assertEquals(7, clienteSete.getCpf()),
		        () -> assertEquals("Telefone Teste 7", clienteSete.getTelefone()),
		        () -> assertEquals("Email Teste 7", clienteSete.getEmail()),
		    	() -> assertEquals("Endereco Teste 7", clienteSete.getEndereco()),
		    	() -> assertNotNull(clienteSete.getDataCadastro()));
	}
	
	
	@Test
	void testSalvarCliente() {
		Cliente entidade = input.mockEntity(1); 
		entidade.setId(1);
		
		var resultado = entidade;
		assertAll(
			() -> assertNotNull(resultado),
	        () -> assertNotNull(resultado.getId()),
	        () -> assertEquals("Nome Teste 1", resultado.getNome()),
	        () -> assertEquals(1, resultado.getCpf()),
	        () -> assertEquals("Telefone Teste 1", resultado.getTelefone()),
	        () -> assertEquals("Email Teste 1", resultado.getEmail()),
	    	() -> assertEquals("Endereco Teste 1", resultado.getEndereco()),
	    	() -> assertNotNull(resultado.getDataCadastro()));
		}

	@Test
	void testUpdateCliente() {
		Cliente entidade = input.mockEntity(1); 
		
		Cliente persistir = entidade;
		persistir.setId(1);
		
		Cliente vo = input.mock(1);
		vo.setId(1);

		when(repository.findById(1)).thenReturn(Optional.of(entidade));
		when(repository.save(entidade)).thenReturn(persistir);
		
		var resultado = service.updateCliente(vo);
		assertAll(
				() -> assertNotNull(resultado),
		        () -> assertNotNull(resultado.getId()),
		        () -> assertEquals("Nome Teste 1", resultado.getNome()),
		        () -> assertEquals(1, resultado.getCpf()),
		        () -> assertEquals("Telefone Teste 1", resultado.getTelefone()),
		        () -> assertEquals("Email Teste 1", resultado.getEmail()),
		    	() -> assertEquals("Endereco Teste 1", resultado.getEndereco()),
		    	() -> assertNotNull(resultado.getDataCadastro()));
	}
	
	
	
	@Test
	void testExcluirCliente() {
		Cliente entidade = input.mockEntity(1); 
		entidade.setId(1);
		
		when(repository.findById(1)).thenReturn(Optional.of(entidade));
		
		service.excluirCliente(entidade);
	}
	
	@Test
	void testClienteNulo() { 
		Exception excecao = assertThrows(ObjetoObrigatorioExcecaoNula.class,
				() -> {service.salvarCliente(null);});
		
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
}