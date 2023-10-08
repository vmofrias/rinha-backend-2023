package com.ldsk.rinhabackend.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ldsk.rinhabackend.exception.ApiExceptionHandler;
import com.ldsk.rinhabackend.model.Pessoa;
import com.ldsk.rinhabackend.repository.PessoaRepository;
import com.ldsk.rinhabackend.util.Cache;

@RestController
public class PessoaController {
	
	private final Logger log = LoggerFactory.getLogger(PessoaController.class);
	
	private final PessoaRepository pessoaRepository;
	
	public PessoaController(PessoaRepository pessoaRepository) {
		
		this.pessoaRepository = pessoaRepository;
	}
	
	@GetMapping("/pessoas/{id}")
	public ResponseEntity<Pessoa> getPessoaById(@PathVariable UUID id) {
		
		if(Cache.temNosIDs(id.toString())) {
			
			return ResponseEntity.ok(Cache.getPessoa(id.toString()));
		}
		
		Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
	
		if(pessoaOptional.isEmpty()) {
			
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(pessoaOptional.get());
	}
	
    @GetMapping("/pessoas")
    public ResponseEntity<List<Pessoa>> findAllBySearchTerm(@RequestParam(required=true,name= "t") String term) {
        
    	if (term == null || term.isEmpty() || term.isBlank()) {
        
    		return ResponseEntity.badRequest().build();
        }
    	
        log.info("Busca por:  {}",term);

        List<Pessoa> pessoas = pessoaRepository.findAllByTerm(term, PageRequest.of(0, 5));

        return ResponseEntity.status(HttpStatus.OK).body(pessoas);
    }
	
    @GetMapping("/contagem-pessoas")
    public ResponseEntity<String> count() {

        // uso do cache
        log.error("cacheLocalPorId = "+Cache.cacheLocalPorId.size());
        log.error("cacheLocalDeApelidos = "+Cache.cacheLocalDeApelidos.size());

        // uso de erros
        log.error("httpMessageNotReadableException =  "+ApiExceptionHandler.httpMessageNotReadableException);
        log.error("missingServletRequestParameterException = "+ ApiExceptionHandler.missingServletRequestParameterException);

        return ResponseEntity.ok(String.valueOf(pessoaRepository.count()));
    }
	
	@PostMapping("/pessoas")
	public ResponseEntity<Pessoa> addPessoa(@RequestBody Pessoa pessoa) {
		
		if (Cache.temNosApelidos(pessoa.getApelido())) {
			
			return ResponseEntity.unprocessableEntity().build();
	    }
	        	
		Optional<Pessoa> pessoaOptional = pessoaRepository.findByApelido(pessoa.getApelido());
	        	
		if(pessoaOptional.isPresent()) {
	        		
			Cache.add(pessoaOptional.get());
	        		
			return ResponseEntity.unprocessableEntity().build();
		}
	        	
		pessoa.setId(UUID.randomUUID());
		Pessoa savedPessoa = pessoaRepository.save(pessoa);
	        	
		Cache.add(savedPessoa);

	    return ResponseEntity.created(URI.create("/pessoas/"+savedPessoa.getId().toString())).build();
	}
	
}
