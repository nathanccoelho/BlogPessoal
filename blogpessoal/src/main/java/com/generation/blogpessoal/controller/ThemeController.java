package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Theme;
import com.generation.blogpessoal.repository.ThemeRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/theme")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ThemeController {
	
	@Autowired
	private ThemeRepository themeRepository;
	
	@GetMapping
	public ResponseEntity<List<Theme>> getAll(){
		return ResponseEntity.ok(themeRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Theme> getById(@PathVariable Long id){
		return themeRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/description/{description}")
	public ResponseEntity<List<Theme>> getByTitle(@PathVariable String descricao){
		return ResponseEntity.ok(themeRepository.findAllByDescriptionContainingIgnoreCase(descricao));
	}
	
	@PostMapping 
	public ResponseEntity<Theme> post(@Valid @RequestBody Theme tema){
		return ResponseEntity.status(HttpStatus.CREATED).body(themeRepository.save(tema));
	}
	
	@PutMapping
	public ResponseEntity<Theme> put (@Valid @RequestBody Theme tema){
		return themeRepository.findById(tema.getId()).map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
				.body(themeRepository.save(tema))).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Theme> tema = themeRepository.findById(id);
		
		if(tema.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		themeRepository.deleteById(id);

	}
	

}