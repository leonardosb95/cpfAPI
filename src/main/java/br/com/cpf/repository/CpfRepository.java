package br.com.cpf.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cpf.model.Cpf;
@Repository
public interface CpfRepository extends JpaRepository<Cpf, Long> {


	List<Cpf> findById(String cpf);

	Page<Cpf> findByCpf(String cpf, Pageable paginacao);
	
	
	
	

	
	

}
