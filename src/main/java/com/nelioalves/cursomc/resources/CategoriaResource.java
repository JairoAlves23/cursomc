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
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {

		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	/*explicação do metodo
	*** Metodo para incluir uma categoria via post***
	1º => trabalha junto com Repositories/Service/Resource
	2º =>method tem quer ser post para enviar a requisição =>method=RequestMethod.POST
	3º =>@RequestBody Categoria obj => para enviar os dados Json no objeto
	4º => objeto URI uri é a assinatura para o envio da requisição
	5º => ResponseEntity<Void> não retornar nada no metodo 
	6ºMudança no meto da Aula 36 passando o objeto de categoria para categoria DTO)
	7º Criação da anotação @Valid na aula 36 para validar dados da anotações feitas no Objeto CategoriaDTO 
	*/
		
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO) {
		Categoria obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}

	/*explicação do metodo
	*** Metodo para alterar uma categoria via PUT***
	1ºMudança no meto da Aula 36 passando o objeto de categoria para categoria DTO)
	2º Criação da anotação @Valid na aula 36 para validar dados da anotações feitas no Objeto CategoriaDTO 
	*/
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id) {
		Categoria obj = service.fromDTO(objDTO);
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
	1º => Linha 2 do metodo transformar uma lista da classe Categoria para lista da classe CategoriaDTO
	2º => list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList()) : este comando... 
	faz a interação como um for no List<Categoria> list = service.findAll() */
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {

		List<Categoria> list = service.findAll(); 
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj))
				.collect(Collectors.toList());	
		return ResponseEntity.ok().body(listDto);
	}
	
	

	/* explicação do metodo
	*** Metodo para buscar todas as categorias por paginação***
	1º => @RequestParam para transformar os parametros em formato HTTP
	2º => Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj)) já incluso no java 8
	faz a interação como um for no List<Categoria> list = service.findAll() */
	
	@RequestMapping(value="/page",method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page",defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage",defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy",defaultValue="nome") String orderBy ,
			@RequestParam(value="direction",defaultValue="ASC") String direction
			) {

		Page<Categoria> list = service.findPage(page,linesPerPage,orderBy,direction); 
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
	
	
	

}
