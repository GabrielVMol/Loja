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
import br.com.loja.gabrielvinicius.models.Produto;
import br.com.loja.gabrielvinicius.repositories.ProdutoRepository;
import br.com.loja.gabrielvinicius.services.ProdutoService;
import br.com.prova.gabrielvinicius.mocks.ProdutoMock;

class ProdutoTeste {
	
	ProdutoMock input;
	
	@InjectMocks
	private ProdutoService service;
	
	@Mock
	ProdutoRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new ProdutoMock();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testGetProduto() {
		Produto entidade = input.mockEntity(1);
	    entidade.setId(1);
	    
	    when(repository.findById(1)).thenReturn(Optional.of(entidade));

	    assertProduto(entidade, 1);
	}
	
	@Test
	void testListAll() {
		List<Produto> list = input.mockEntityList(); 
		when(repository.findAll()).thenReturn(list);
		
		assertNotNull(list);
		assertEquals(14, list.size());
		
		assertProdutoList(list);
	}
	
	@Test
	void testSalvarProduto() {
		Produto entidade = input.mockEntity(1); 
		entidade.setId(1);
		
		assertProduto(entidade, 1);
	}

	@Test
	void testUpdateProduto() {
		Produto entidadeOriginal = input.mockEntity(1);
		Produto entidadeAtualizada = input.mock(2);
	    
	    when(repository.findById(1)).thenReturn(Optional.of(entidadeOriginal));
	    when(repository.save(entidadeOriginal)).thenReturn(entidadeOriginal);
	    when(repository.findById(2)).thenReturn(Optional.of(entidadeAtualizada));
	    when(repository.save(entidadeAtualizada)).thenReturn(entidadeAtualizada);

	    Produto resultado = service.updateProduto(entidadeAtualizada);

	    assertAll("Verificar resultados",
	        () -> assertProdutoEquals(entidadeAtualizada, resultado),
	        () -> assertProdutoEquals(entidadeOriginal, entidadeOriginal)
	    );
	}
	
	@Test
	void testExcluirProduto() {
		Produto entidade = input.mockEntity(1); 
		entidade.setId(1);
		
		when(repository.findById(1)).thenReturn(Optional.of(entidade));
		
		service.excluirProduto(entidade);
	}
	
	@Test
	void testSalvarProdutoNulo() { 
		Exception excecao = assertThrows(ObjetoObrigatorioExcecaoNula.class,
				() -> {service.salvarProduto(null);});
		
		String mensagemAtual = excecao.getMessage();
		String MensagemEsperada = "Não é permitido inserir um objeto nulo!";
		  
		
		assertEquals(mensagemAtual,MensagemEsperada);
	}
	
	@Test
	void testExcluirProdutoNulo() { 
		Exception excecao = assertThrows(ObjetoObrigatorioExcecaoNula.class,
				() -> {service.excluirProduto(null);});
		
		String mensagemAtual = excecao.getMessage();
		String MensagemEsperada = "Não é permitido inserir um objeto nulo!";
		  
		
		assertEquals(mensagemAtual,MensagemEsperada);
	}
	
	@Test
	void testUpdateProdutoNulo() {
		Exception excecao = assertThrows(ObjetoObrigatorioExcecaoNula.class, () -> {
			service.updateProduto(null);
		});
		
		String mensagemAtual = excecao.getMessage();
		String MensagemEsperada = "Não é permitido inserir um objeto nulo!";
	
		assertEquals(mensagemAtual,MensagemEsperada);
	}
	
	private void assertProdutoEquals(Produto esperado, Produto atual) {
	    assertAll(
	        () -> assertNotNull(atual),
	        () -> assertNotNull(atual.getId()),
	        () -> assertEquals(esperado.getNome(), atual.getNome()),
	        () -> assertEquals(esperado.getGrupo(), atual.getGrupo()),
	        () -> assertEquals(esperado.getDescricao(), atual.getDescricao()),
	        () -> assertEquals(esperado.getPreco(), atual.getPreco()),
	        () -> assertEquals(esperado.getEstoque(), atual.getEstoque()),
	        () -> assertEquals(esperado.getQuantidade(), atual.getQuantidade()),
	        () -> assertEquals(false, service.validaProduto(atual.getNome(), atual.getId(), atual.getEstoque()))
	    );
	}
	
	private void assertProduto(Produto produto, int numero) {
	    assertAll(
	        () -> assertNotNull(produto),
	        () -> assertNotNull(produto.getId()),
	        () -> assertEquals("Nome Teste " + numero, produto.getNome()),
	        () -> assertEquals("Grupo Teste " + numero, produto.getGrupo()),
	        () -> assertEquals("Descricao Teste " + numero, produto.getDescricao()),
	        () -> assertEquals(numero, produto.getPreco()),
	        () -> assertEquals(numero, produto.getEstoque()),
	        () -> assertEquals(numero, produto.getQuantidade()),
	        () -> assertEquals(false, service.validaProduto(produto.getNome(), produto.getId(), produto.getEstoque()))
	    );
	}
	
	private void assertProdutoList(List<Produto> produtoes) {
		for (Produto produto : produtoes) {
			assertAll(
			        () -> assertNotNull(produto),
			        () -> assertNotNull(produto.getId()));
		}	
	}
}