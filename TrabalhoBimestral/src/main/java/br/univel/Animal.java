package br.univel;

import java.math.BigDecimal;

import br.univel.anotations.Coluna;

public class Animal {
	@Coluna(nome = "ID", pk = true)
	private int id;
	@Coluna(nome = "RACA")
	private String raca;
	@Coluna(nome = "volume")
	private BigDecimal volume;
}
