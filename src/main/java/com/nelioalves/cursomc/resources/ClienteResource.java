package com.nelioalves.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {

		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO) {
		Cliente obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}
	
	/*explicação do metodo
	*** Metodo para alterar uma categoria via PUT***
	1ºMudança no meto da Aula 36 passando o objeto de categoria para categoria DTO)
	2º Criação da anotação @Valid na aula 36 para validar dados da anotações feitas no Objeto ClienteDTO 
	*/
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	/* explicação do metodo
	*** Metodo para buscar todas as categorias***
	1º => Linha 2 do metodo transformar uma lista da classe Cliente para lista da classe ClienteDTO
	2º => list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList()) : este comando... 
	faz a interação como um for no List<Cliente> list = service.findAll() 
	*/
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {

		List<Cliente> list = service.findAll(); 
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());	
		return ResponseEntity.ok().body(listDto);
	}
	
	

	/* explicação do metodo
	*** Metodo para buscar todas as categorias por paginação***
	1º => @RequestParam para transformar os parametros em formato HTTP
	2º => Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj)) já incluso no java 8
	faz a interação como um for no List<Cliente> list = service.findAll() */
	
	@RequestMapping(value="/page",method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page",defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage",defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy",defaultValue="nome") String orderBy ,
			@RequestParam(value="direction",defaultValue="ASC") String direction
			) {

		Page<Cliente> list = service.findPage(page,linesPerPage,orderBy,direction); 
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
	

}
