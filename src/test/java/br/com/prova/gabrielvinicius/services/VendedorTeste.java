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
import br.com.loja.gabrielvinicius.models.Vendedor;
import br.com.loja.gabrielvinicius.repositories.VendedorRepository;
import br.com.loja.gabrielvinicius.services.VendedorService;
import br.com.prova.gabrielvinicius.mocks.VendedorMock;

class VendedorTeste {
	VendedorMock input;
	
	@InjectMocks
	private VendedorService service;
	
	@Mock
	VendedorRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new VendedorMock();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testGetVendedor() {
		Vendedor entidade = input.mockEntity(1);
	    entidade.setId(1);
	    
	    when(repository.findById(1)).thenReturn(Optional.of(entidade));

	    assertVendedor(entidade, 1);

	}
	
	@Test
	void testListAll() {
		List<Vendedor> list = input.mockEntityList(); 
		when(repository.findAll()).thenReturn(list);
		
		assertNotNull(list);
		assertEquals(14, list.size());
		
		assertVendedorList(list);
	}
	
	@Test
	void testSalvarVendedor() {
		Vendedor entidade = input.mockEntity(1); 
		entidade.setId(1);
		
		assertVendedor(entidade, 1);
	}
	
	@Test
	void testUpdateVendedor() {
	    Vendedor entidadeOriginal = input.mockEntity(1);
	    Vendedor entidadeAtualizada = input.mock(2);
	    
	    when(repository.findById(1)).thenReturn(Optional.of(entidadeOriginal));
	    when(repository.save(entidadeOriginal)).thenReturn(entidadeOriginal);
	    when(repository.findById(2)).thenReturn(Optional.of(entidadeAtualizada));
	    when(repository.save(entidadeAtualizada)).thenReturn(entidadeAtualizada);

	    Vendedor resultado = service.updateVendedor(entidadeAtualizada);

	    assertAll("Verificar resultados",
	        () -> assertVendedorEquals(entidadeAtualizada, resultado),
	        () -> assertVendedorEquals(entidadeOriginal, entidadeOriginal)
	    );
	}

	
	@Test
	void testExcluirVendedor() {
		Vendedor entidade = input.mockEntity(1); 
		entidade.setId(1);
		
		when(repository.findById(1)).thenReturn(Optional.of(entidade));
		
		service.excluirVendedor(entidade);
	}
	
	@Test
	void testSalvarVendedorNulo() { 
		Exception excecao = assertThrows(ObjetoObrigatorioExcecaoNula.class, () -> {
			service.salvarVendedor(null);});
		
		String mensagemAtual = excecao.getMessage();
		String MensagemEsperada = "Não é permitido inserir um objeto nulo!";
	
		assertEquals(mensagemAtual,MensagemEsperada);
	}
	
	@Test
	void testExcluirVendedorNulo() { 
		Exception excecao = assertThrows(ObjetoObrigatorioExcecaoNula.class, () -> {
			service.excluirVendedor(null);});
		
		String mensagemAtual = excecao.getMessage();
		String MensagemEsperada = "Não é permitido inserir um objeto nulo!";
	
		assertEquals(mensagemAtual,MensagemEsperada);
	}
	
	@Test
	void testUpdateVendedorNulo() {
		Exception excecao = assertThrows(ObjetoObrigatorioExcecaoNula.class, () -> {
			service.updateVendedor(null);});
		
		String mensagemAtual = excecao.getMessage();
		String MensagemEsperada = "Não é permitido inserir um objeto nulo!";
	
		assertEquals(mensagemAtual,MensagemEsperada);
	}
	
	private void assertVendedorEquals(Vendedor esperado, Vendedor atual) {
	    assertAll("Vendedor",
	        () -> assertNotNull(atual),
	        () -> assertNotNull(atual.getId()),
	        () -> assertEquals(esperado.getNome(), atual.getNome()),
	        () -> assertEquals(esperado.getCpf(), atual.getCpf()),
	        () -> assertEquals(esperado.getTelefone(), atual.getTelefone()),
	        () -> assertEquals(esperado.getEmail(), atual.getEmail()),
	        () -> assertEquals(esperado.getEndereco(), atual.getEndereco()),
	        () -> assertNotNull(atual.getDataCadastro()),
	        () -> assertEquals(esperado.getSetor(), atual.getSetor()),
	        () -> assertEquals(esperado.getSalario(), atual.getSalario()),
	        () -> assertEquals(false, service.verificaSalario(atual.getSalario())),
	        () -> assertEquals(false, service.verificarCpf(atual.getCpf())),
	        () -> assertEquals(false, service.verificarEmail(atual.getEmail()))
	    );
	}
	
	private void assertVendedor(Vendedor vendedor, int numero) {
	    assertAll(
	        () -> assertNotNull(vendedor),
	        () -> assertNotNull(vendedor.getId()),
	        () -> assertEquals("Nome Teste " + numero, vendedor.getNome()),
	        () -> assertEquals(numero, vendedor.getCpf()),
	        () -> assertEquals("Telefone Teste " + numero, vendedor.getTelefone()),
	        () -> assertEquals("Email Teste " + numero, vendedor.getEmail()),
	        () -> assertEquals("Endereco Teste " + numero, vendedor.getEndereco()),
	        () -> assertNotNull(vendedor.getDataCadastro()),
	        () -> assertEquals("Setor Teste " + numero, vendedor.getSetor()),
	        () -> assertEquals(numero, vendedor.getSalario()),
	        () -> assertEquals(false, service.verificaSalario(vendedor.getSalario())),
	        () -> assertEquals(false, service.verificarCpf(vendedor.getCpf())),
	        () -> assertEquals(false, service.verificarEmail(vendedor.getEmail()))
	    );
	}
	
	private void assertVendedorList(List<Vendedor> vendedores) {
		for (Vendedor vendedor : vendedores) {
			assertAll(
			        () -> assertNotNull(vendedor),
			        () -> assertNotNull(vendedor.getId()));
		}	
	}
}