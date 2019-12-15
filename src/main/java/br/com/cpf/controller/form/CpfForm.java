package br.com.cpf.controller.form;

import br.com.cpf.model.Cpf;
import br.com.cpf.repository.CpfRepository;

public class CpfForm {

	private String cpf;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Cpf converter(CpfRepository repo) {
		return new Cpf(cpf);
	}

}
