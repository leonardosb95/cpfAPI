package br.com.cpf.controller.dto;

import org.springframework.data.domain.Page;

import br.com.cpf.model.Cpf;

public class CpfDto {

	private Long id;
	private String cpf;
	
	
	public CpfDto(Cpf cpf) {
		this.id = cpf.getId();
		this.cpf=cpf.getCpf();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public static Page<CpfDto> converter(Page<Cpf> cpfs) {
		return cpfs.map(CpfDto::new);
	}

}
