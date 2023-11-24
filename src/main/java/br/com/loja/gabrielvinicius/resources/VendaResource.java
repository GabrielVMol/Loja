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

import br.com.loja.gabrielvinicius.models.Venda;
import br.com.loja.gabrielvinicius.services.ProdutoService;
import br.com.loja.gabrielvinicius.services.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/vendas")
@SecurityRequirement(name = "security_auth")
@Tag(name = "Venda", description = "Endpoints para gerenciamento de Venda")
public class VendaResource {

	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private ProdutoService produtoService;

	@GetMapping("")
	@Operation(summary = "Achar todas as Venda", description = "Achar todas as Venda", tags = { "Venda" }, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Venda.class))) }),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content) })
	public ResponseEntity<List<Venda>> listAll() {
		return new ResponseEntity<List<Venda>>(vendaService.listAll(), HttpStatus.OK);
	}

	@GetMapping("/{codigo}")
	@Operation(summary = "Achar a Venda", description = "Achar a Venda", tags = { "Venda" }, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = Venda.class))),
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content) })
	public ResponseEntity<Venda> getVenda(@PathVariable("codigo") int id) {
		Optional<Venda> venda = vendaService.getVenda(id);

		if (venda.isPresent()) {
			return new ResponseEntity<Venda>(venda.get(), HttpStatus.OK);
		}
		return new ResponseEntity<Venda>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/salvar")
	@Operation(summary = "Adcionar uma Nova Venda", description = "Adcionar uma Nova Venda", tags = {
			"Venda" }, responses = {
					@ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = Venda.class))),
					@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
					@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
					@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content) })
	public ResponseEntity<Venda> salvarVenda(@RequestBody Venda venda) {
		var cliente = venda.getCliente();
		var vendedor = venda.getVendedor();
		Boolean data = venda.getDataVenda().after(venda.getDataGarantia());
		Boolean verificaQuantidade = vendaService.verificaQuantidade(venda.getProdutos(), venda);

		if (vendaService.validaVenda(cliente, vendedor, data, verificaQuantidade, venda) == true) {
			produtoService.atualizaListaProduto(venda.getProdutos());
			vendaService.atualizaVenda(venda);
			vendaService.salvarVenda(venda);
			return new ResponseEntity<Venda>(venda, HttpStatus.OK);
		}
		return new ResponseEntity<Venda>(HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/excluir/{codigo}")
	@Operation(summary = "Deletar Venda", description = "Deletar Venda", tags = { "Venda" }, responses = {
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content) })
	public ResponseEntity<Venda> excluirVenda(@PathVariable("codigo") int codigo) {
		Optional<Venda> venda = vendaService.getVenda(codigo);

		if (venda.isPresent()) {
			produtoService.atualizaListaProdutoExclusao(venda.get().getProdutos());
			vendaService.excluirVenda(venda.get());
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<Venda>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/editar/{codigo}")
	@Operation(summary = "Atualizar dados da Venda", description = "Atualizar dados da Venda", tags = {
			"Venda" }, responses = {
					@ApiResponse(description = "Atualizado", responseCode = "200", content = @Content(schema = @Schema(implementation = Venda.class))),
					@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
					@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
					@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
					@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content) })
	public ResponseEntity<Venda> updateVenda(@PathVariable("codigo") int codigo, @RequestBody Venda venda) {

		Optional<Venda> optionalVenda = vendaService.getVenda(codigo);

		if (optionalVenda.isPresent()) {
			Venda vendaExistente = optionalVenda.get();

			vendaExistente.setId(codigo);
			vendaExistente.setDataGarantia(venda.getDataGarantia());
			vendaService.salvarVenda(vendaExistente);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<Venda>(HttpStatus.NOT_FOUND);
	}
}