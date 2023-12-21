package com.generation.lojadegames.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // instancia em controller JogoRepository.java
import org.springframework.http.HttpStatus; // importa todos status http
import org.springframework.http.ResponseEntity; // importa a RE
import org.springframework.web.bind.annotation.CrossOrigin; // libera acesso às portas
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping; // importa metodo get
import org.springframework.web.bind.annotation.PathVariable; // importa uri variável
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; // fornece endpoint de consulta
import org.springframework.web.bind.annotation.RestController; // indica que é controller

import com.generation.lojadegames.model.Jogo; // importa Jogo.java
import com.generation.lojadegames.repository.JogoRepository; // importa JogoRepository.java

@RestController // indica que é controller
@RequestMapping("/jogos") // fornece endpoint de consulta
@CrossOrigin(origins="*") // libera acesso às portas
public class JogoController {
	
	@Autowired // instancia o repositório, permitindo tudo de JogoRepository.java fique acessível para os métodos
	private JogoRepository repository; // puxa JogoRepository.java e o apelida
	
	// METODOS
	
	// GET PRINCIPAL
	@GetMapping 
	public ResponseEntity<List<Jogo>> getAll(){ // usa RE puxa métodos, importando Jogo.java para criar a lista
		return ResponseEntity.ok((repository.findAll())); // retorna lista de jogos com status OK de RE
	}
	
	// GET ID
	@GetMapping("/{id}") // indica endpoint variável
	public ResponseEntity<Jogo> getById(@PathVariable Long id){ // puxa URI variável do {id}
		return repository.findById(id) // retorna um opcional (nulo ou preenchido) pro id
				.map(resp -> ResponseEntity.ok(resp)) // mostra OK 200
				.orElse(ResponseEntity.notFound().build()); // mostra 400 NOT FOUND
	}
	
	// GET NOME
	@GetMapping("/nome/{nome}") // indica endpoint variável
	public ResponseEntity<List<Jogo>> getByNome(@PathVariable String nome){ // puxa método da Re, para a lista de jogos por nome
		return ResponseEntity.ok(repository.findAllByNomeContainingIgnoreCase(nome)); // retorna mesma consulta do DB
	}
	
	// POST
	@PostMapping // indica que é postagem
	public ResponseEntity<Jogo> post(@RequestBody Jogo jogo){ // puxa lista pro método post, solicita body para criação de objetos
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(jogo)); // retorno CREATED 200 e dentro do body salvo o repositório do jogo
	}
	
	// PUT
	@PutMapping // indica que é update
	public ResponseEntity<Jogo> put(@RequestBody Jogo jogo){ // igual ao post
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(jogo)); // retorna OK 200 e o body salva o repositório do jogo
	}
	
	// DELETE
	@DeleteMapping("/{id}") // indica que é delete por endpoint variável de id
	public void delete(@PathVariable Long id) { // puxa URI variável do {id}
		repository.deleteById(id); // puxa do repositório o ID para deletar
	}
}