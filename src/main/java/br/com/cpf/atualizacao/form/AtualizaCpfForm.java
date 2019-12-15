package br.com.cpf.atualizacao.form;

import br.com.cpf.model.Cpf;
import br.com.cpf.repository.CpfRepository;

public class AtualizaCpfForm {

	private String cpf;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Cpf atualizar(Long id, CpfRepository repo) {
		Cpf cpf = repo.getOne(id);
		cpf.setCpf(this.cpf);
		return cpf;
	}

}
