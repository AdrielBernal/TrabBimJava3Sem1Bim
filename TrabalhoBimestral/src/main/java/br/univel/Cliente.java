package br.univel;

import br.univel.anotations.Coluna;
import br.univel.anotations.Tabela;

@Tabela("CLIENTE")
public class Cliente {

	@Coluna(pk = true)
	private int id;

	@Coluna(nome = "NOME")
	private String nome;

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
