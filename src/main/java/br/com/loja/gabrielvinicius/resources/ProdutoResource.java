package br.com.loja.gabrielvinicius.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.loja.gabrielvinicius.models.Produto;
import br.com.loja.gabrielvinicius.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/produtos")
@SecurityRequirement(name = "security_auth")
@Tag(name = "Produto", description = "Endpoints para gerenciamento de Produto")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("")
	@Operation(summary = "Achar todas os Produto", description = "Achar todas os Produto",
	tags = {"Produto"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = {@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = Produto.class)))}),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<List<Produto>> listAll() {
		return new ResponseEntity<List<Produto>>(produtoService.listAll(), HttpStatus.OK);
	}

	@GetMapping("/{codigo}")
	@Operation(summary = "Achar o Produto", description = "Achar o Produto",
	tags = {"Produto"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Produto.class))),
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Produto> getProduto(@PathVariable("codigo") int id) {
		Optional<Produto> produto = produtoService.getProduto(id);

		if (produto.isPresent()) {
			return new ResponseEntity<Produto>(produto.get(), HttpStatus.OK);
		}
		return new ResponseEntity<Produto>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/nome/{nome}")
	@Operation(summary = "Achar o Produto por Nome", description = "Achar o Produto por Nome",
	tags = {"Produto"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Produto.class))),
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Produto> getProdutoNome(@PathVariable("nome") String nome) {
		Produto produto = produtoService.getProdutoNome(nome);
		int id = produto.getId();
		return getProduto(id);
	}
	
	@GetMapping("/grupo/{grupo}")
	@Operation(summary = "Achar o Produto por Grupo", description = "Achar o Produto por Grupo",
	tags = {"Produto"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Produto.class))),
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<List<Produto>> getProdutoGrupo(@PathVariable("grupo") String grupo) {
		 return new ResponseEntity<List<Produto>>(produtoService.getProdutoGrupo(grupo), HttpStatus.OK);
	}


	@PostMapping("/salvar")
	@Operation(summary = "Adcionar um Novo Produto", description = "Adcionar um Novo Produto",
	tags = {"Produto"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Produto.class))),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Produto> salvarProduto(@RequestBody Produto produto) {
		Integer id = produto.getId();
		String nome = produto.getNome();
		Boolean resposta = produtoService.validaProduto(nome, id, produto);
	    
	    if (resposta == true) {
			return new ResponseEntity<Produto>(HttpStatus.BAD_REQUEST);
		}
		produtoService.salvarProduto(produto);
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}

	@DeleteMapping("/excluir/{codigo}")
	@Operation(summary = "Deletar Produto", description = "Deletar Produto",
	tags = {"Produto"}, responses = {
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Produto> excluirProduto(@PathVariable("codigo") int codigo) {
		Optional<Produto> produto = produtoService.getProduto(codigo);
		Integer estoque = produto.get().getEstoque();
		
		if (produto.isPresent() && produtoService.verificaParaExcluir(estoque)) {
			produtoService.excluirProduto(produto.get());
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<Produto>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/editar/{codigo}")
	@Operation(summary = "Atualizar dados do Produto", description = "Atualizar dados do Produto",
	tags = {"Produto"}, responses = {
			@ApiResponse(description = "Atualizado", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Produto.class))),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Produto> updateProduto(@PathVariable("codigo") int codigo, @RequestBody Produto produto){
		
		Optional<Produto> optionalProduto = produtoService.getProduto(codigo);
		
		if (optionalProduto.isPresent()) {
			Produto produtoExistente = optionalProduto.get();
		
			produtoExistente.setId(codigo);
			produtoExistente.setNome(produto.getNome());
			produtoExistente.setDescricao(produto.getDescricao());
			produtoExistente.setPreco(produto.getPreco());
			produtoExistente.setEstoque(produto.getEstoque());
			produtoExistente.setGrupo(produto.getGrupo());
			produtoService.salvarProduto(produtoExistente);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<Produto>(HttpStatus.NOT_FOUND);
	}
}