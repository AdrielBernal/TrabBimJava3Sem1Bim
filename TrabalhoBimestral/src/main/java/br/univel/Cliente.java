package br.univel;

import java.math.BigDecimal;

import br.univel.anotations.Coluna;
import br.univel.anotations.Tabela;

@Tabela("CLIENTE")
public class Cliente {

	@Coluna(nome = "ID", pk = true)
	private int id;

	@Coluna(nome = "NOME")
	private String nome;

	@Coluna(nome = "ENDERECO")
	private int endereco;

	@Coluna(nome = "bd")
	private BigDecimal bd;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
