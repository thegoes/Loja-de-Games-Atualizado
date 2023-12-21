package com.generation.lojadegames.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.lojadegames.model.Categoria;
import com.generation.lojadegames.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CategoriaController {
	
	@Autowired // 
	private CategoriaRepository repository;
	
	// METODOS
	// GET PRINCIPAL
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll(){ // puxa tudo da lista de categoria
		return ResponseEntity.ok(repository.findAll()); // retorna lista de categoria com status OK
	}
	
	// GET ID
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id){ // indica que o ID é variável
		return repository.findById(id) // retorna opcional com status de OK ou NOT FOUND
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	// GET TITULO
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Categoria>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.findByTituloContainingIgnoreCase(titulo)); // retorna lista de títulos igual a de consulta do DB em repository 
	}

	// POST
	@PostMapping
	public ResponseEntity<Categoria> post(@RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(categoria)); // retorna status CREATED e salvamos no DB em repository a categoria através do corpo
	}
	
	// PUT
	@PutMapping
	public ResponseEntity<Categoria> put(@RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(categoria)); // retorna status OK e salvamos no DB em repository a categoria através do corpo
	}
	
	// DELETE
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
}