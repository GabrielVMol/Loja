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

import br.com.loja.gabrielvinicius.models.Vendedor;
import br.com.loja.gabrielvinicius.services.VendedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/vendedores")
@SecurityRequirement(name = "security_auth")
@Tag(name = "Vendedor", description = "Endpoints para gerenciamento de Vendedor")
public class VendedorResource {

	@Autowired
	private VendedorService vendedorService;

	@GetMapping("")
	@Operation(summary = "Achar todas os Vendedor", description = "Achar todas os Vendedor",
	tags = {"Vendedor"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = {@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = Vendedor.class)))}),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<List<Vendedor>> listAll() {
		return new ResponseEntity<List<Vendedor>>(vendedorService.listAll(), HttpStatus.OK);
	}

	@GetMapping("/{codigo}")
	@Operation(summary = "Achar o Vendedor", description = "Achar o Vendedor",
	tags = {"Vendedor"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Vendedor.class))),
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Vendedor> getVendedor(@PathVariable("codigo") int id) {
		Optional<Vendedor> vendedor = vendedorService.getVendedor(id);

		if (vendedor.isPresent()) {
			return new ResponseEntity<Vendedor>(vendedor.get(), HttpStatus.OK);
		}
		return new ResponseEntity<Vendedor>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/nome/{nome}")
	@Operation(summary = "Achar o Vendedor por Nome", description = "Achar o Vendedor por Nome",
	tags = {"Vendedor"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Vendedor.class))),
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Vendedor> getVendedorNome(@PathVariable("nome") String nome) {
		Vendedor vendedor = vendedorService.getVendedorNome(nome);
		int id = vendedor.getId();
		return getVendedor(id);
	}

	@PostMapping("/salvar")
	@Operation(summary = "Adcionar um Novo Vendedor", description = "Adcionar um Novo Vendedor",
	tags = {"Vendedor"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Vendedor.class))),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Vendedor> salvarVendedor(@RequestBody Vendedor vendedor) {
		Integer cpf = vendedor.getCpf();
		Double salario = vendedor.getSalario();
		Boolean resposta = vendedorService.validaVendedor(cpf, salario);
		
		if (resposta == true) {
			return new ResponseEntity<Vendedor>(HttpStatus.BAD_REQUEST);
		}
		vendedorService.salvarVendedor(vendedor);
		return new ResponseEntity<Vendedor>(vendedor, HttpStatus.OK);
	}

	@DeleteMapping("/excluir/{codigo}")
	@Operation(summary = "Deletar Vendedor", description = "Deletar Vendedor",
	tags = {"Vendedor"}, responses = {
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Vendedor> excluirVendedor(@PathVariable("codigo") int codigo) {
		Optional<Vendedor> vendedor = vendedorService.getVendedor(codigo);

		if (vendedor.isPresent()) {
			vendedorService.excluirVendedor(vendedor.get());
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<Vendedor>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/editar/{codigo}")
	@Operation(summary = "Atualizar dados do Vendedor", description = "Atualizar dados do Vendedor",
	tags = {"Vendedor"}, responses = {
			@ApiResponse(description = "Atualizado", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Vendedor.class))),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Vendedor> updateVendedor(@PathVariable("codigo") int codigo, @RequestBody Vendedor vendedor){
		
		Optional<Vendedor> optionalVendedor = vendedorService.getVendedor(codigo);
		
		if (optionalVendedor.isPresent()) {
			Vendedor vendedorExistente = optionalVendedor.get();
		
			vendedorExistente.setId(codigo);
			vendedorExistente.setNome(vendedor.getNome());
			vendedorExistente.setCpf(vendedor.getCpf());
			vendedorExistente.setEmail(vendedor.getEmail());
			vendedorExistente.setTelefone(vendedor.getTelefone());
			vendedorExistente.setEndereco(vendedor.getEndereco());
			vendedorService.salvarVendedor(vendedorExistente);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<Vendedor>(HttpStatus.NOT_FOUND);
	}
}