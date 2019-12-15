package br.com.cpf.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cpf.atualizacao.form.AtualizaCpfForm;
import br.com.cpf.controller.dto.CpfDto;
import br.com.cpf.controller.form.CpfForm;
import br.com.cpf.model.Cpf;
import br.com.cpf.repository.CpfRepository;
import br.com.cpf.util.ValidaCPF;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/cpf")
public class CpfController {

	@Autowired
	private CpfRepository cpfRepository;

	@GetMapping
	@Cacheable(value = "listaDeCpfs")
	public ResponseEntity<Page<CpfDto>> lista(@RequestParam(required = false) String cpf,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {

		if (cpf == null) {
			Page<Cpf> cpfs = cpfRepository.findAll(paginacao);
			
			return ResponseEntity.ok(CpfDto.converter(cpfs));
		} else {
			Page<Cpf> cpfs = cpfRepository.findByCpf(cpf, paginacao);
			
			if (cpfs.getNumberOfElements()>0) {
				return ResponseEntity.ok(CpfDto.converter(cpfs));
			}
			else {
				return ResponseEntity.badRequest().build();
			}
			
			
		}
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeCpfs", allEntries = true)
	public ResponseEntity<CpfDto> cadastrar(@RequestBody @Valid CpfForm form, UriComponentsBuilder uriBuilder) {
		
		boolean valido=ValidaCPF.isCPF(form.getCpf());
		
		if (valido) {
			Cpf cpf = form.converter(cpfRepository);
			cpfRepository.save(cpf);
			URI uri = uriBuilder.path("/cpf/{id}").buildAndExpand(cpf.getId()).toUri();
			return ResponseEntity.created(uri).body(new CpfDto(cpf));
		} else {
			return ResponseEntity.badRequest().build();
			
		}
		
		

		
	}

	@GetMapping("/{id}")
	public ResponseEntity<CpfDto> detalhar(@PathVariable Long id) {
		Optional<Cpf> cpf = cpfRepository.findById(id);
		if (cpf.isPresent()) {
			return ResponseEntity.ok(new CpfDto(cpf.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeCpfs", allEntries = true)
	public ResponseEntity<CpfDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizaCpfForm form) {
		Optional<Cpf> optional = cpfRepository.findById(id);
		if (optional.isPresent()) {
			Cpf cpf = form.atualizar(id, cpfRepository);
			return ResponseEntity.ok(new CpfDto(cpf));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeCpfs", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Cpf> optional = cpfRepository.findById(id);
		if (optional.isPresent()) {
			cpfRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
