package com.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.blogpessoal.model.Tema;
import com.blogpessoal.repository.TemasRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tema")
public class TemaController {
	
	@Autowired
	private TemasRepository temasRepository;
	
	@GetMapping
	public ResponseEntity<List<Tema>> GetAll(){
		return ResponseEntity.ok(temasRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tema> GetById(@PathVariable long id){
		return temasRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Tema>> GetByName(@PathVariable String nome){
		return ResponseEntity.ok(temasRepository.findAllByDescricaoContainingIgnoreCase(nome));
	}
	
	@PostMapping
	public ResponseEntity<Tema> postTema(@Valid @RequestBody Tema tema){
		return ResponseEntity.status(HttpStatus.CREATED).body(temasRepository.save(tema));
	}
	
	@PutMapping
	public ResponseEntity<Tema> putTema(@Valid @RequestBody Tema tema){
		return ResponseEntity.ok(temasRepository.save(tema));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTema(@PathVariable long id){
		return temasRepository.findById(id)
				.map(resposta -> {
					temasRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})	
				.orElse(ResponseEntity.notFound().build());
	}
}