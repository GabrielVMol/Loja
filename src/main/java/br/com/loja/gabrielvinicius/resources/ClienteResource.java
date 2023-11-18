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

import br.com.loja.gabrielvinicius.models.Cliente;
import br.com.loja.gabrielvinicius.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/clientes")
@SecurityRequirement(name = "security_auth")
@Tag(name = "Cliente", description = "Endpoints para gerenciamento de Cliente")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@GetMapping("")
	@Operation(summary = "Achar todas os Cliente", description = "Achar todas os Cliente",
	tags = {"Cliente"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = {@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = Cliente.class)))}),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<List<Cliente>> listAll() {
		return new ResponseEntity<List<Cliente>>(clienteService.listAll(), HttpStatus.OK);
	}

	@GetMapping("/{codigo}")
	@Operation(summary = "Achar o Cliente", description = "Achar o Cliente",
	tags = {"Cliente"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Cliente.class))),
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Cliente> getCliente(@PathVariable("codigo") int id) {
		Optional<Cliente> cliente = clienteService.getCliente(id);

		if (cliente.isPresent()) {
			return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
		}
		return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/nome/{nome}")
	@Operation(summary = "Achar o Cliente por Nome", description = "Achar o Cliente por Nome",
	tags = {"Cliente"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Cliente.class))),
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Cliente> getCliente(@PathVariable("nome") String nome) {
		Cliente cliente = clienteService.getClienteNome(nome);
		int id = cliente.getId();
		return getCliente(id);
	}

	@PostMapping("/salvar")
	@Operation(summary = "Adcionar um Novo Cliente", description = "Adcionar um Novo Cliente",
	tags = {"Cliente"}, responses = {
			@ApiResponse(description = "Sucesso", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Cliente.class))),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Cliente> salvarCliente(@RequestBody Cliente cliente) {
		Integer cpf = cliente.getCpf();
		String email = cliente.getEmail();
		Boolean resposta = clienteService.validaCliente(cpf, email);
	    
	    if (resposta == true) {
			return new ResponseEntity<Cliente>(HttpStatus.BAD_REQUEST);
		}
		clienteService.salvarCliente(cliente);
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@DeleteMapping("/excluir/{codigo}")
	@Operation(summary = "Deletar Cliente", description = "Deletar Cliente",
	tags = {"Cliente"}, responses = {
			@ApiResponse(description = "Sem Conteúdo", responseCode = "204", content = @Content),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Cliente> excluirCliente(@PathVariable("codigo") int codigo) {
		Optional<Cliente> cliente = clienteService.getCliente(codigo);

		if (cliente.isPresent()) {
			clienteService.excluirCliente(cliente.get());
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/editar/{codigo}")
	@Operation(summary = "Atualizar dados do Cliente", description = "Atualizar dados do Cliente",
	tags = {"Cliente"}, responses = {
			@ApiResponse(description = "Atualizado", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Cliente.class))),
			@ApiResponse(description = "Falha na Requisição", responseCode = "400", content = @Content),
			@ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
			@ApiResponse(description = "Não Encontrado", responseCode = "404", content = @Content),
			@ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)})
	public ResponseEntity<Cliente> updateCliente(@PathVariable("codigo") int codigo, @RequestBody Cliente cliente){
		
		Optional<Cliente> optionalCliente = clienteService.getCliente(codigo);
		
		if (optionalCliente.isPresent()) {
			Cliente clienteExistente = optionalCliente.get();
		
			clienteExistente.setId(codigo);
			clienteExistente.setNome(cliente.getNome());
			clienteExistente.setCpf(cliente.getCpf());
			clienteExistente.setEmail(cliente.getEmail());
			clienteExistente.setTelefone(cliente.getTelefone());
			clienteExistente.setEndereco(cliente.getEndereco());
			clienteService.salvarCliente(clienteExistente);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
	}
}