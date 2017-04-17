package br.univel;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

import br.univel.anotations.Coluna;
import br.univel.anotations.Tabela;

public class SqlUtils {
	public String getSqlTable(Object o) {

		try {
			StringBuilder sb = new StringBuilder();
			{
				String nomeTabela;
				if (o.getClass().isAnnotationPresent(Tabela.class)) {
					Tabela anotacaoTabela = o.getClass().getAnnotation(Tabela.class);
					nomeTabela = anotacaoTabela.value();
				} else {
					nomeTabela = o.getClass().getSimpleName().toUpperCase();
				}
				sb.append("CREATE TABLE ").append(nomeTabela).append(" (");
			}
			Field[] atributos = o.getClass().getDeclaredFields();
			{
				for (int i = 0; i < atributos.length; i++) {
					Field field = atributos[i];
					String nomeColuna;
					String tipoColuna;
					if (field.isAnnotationPresent(Coluna.class)) {
						Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
						if (anotacaoColuna.nome().isEmpty()) {
							nomeColuna = field.getName().toUpperCase();
						} else {
							nomeColuna = anotacaoColuna.nome();
						}
					} else {
						nomeColuna = field.getName().toUpperCase();
					}
					Class<?> tipoParametro = field.getType();
					if (tipoParametro.equals(String.class)) {
						tipoColuna = "VARCHAR(100)";
					} else if (tipoParametro.equals(int.class)) {
						Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
						if (anotacaoColuna.pk()) {
							tipoColuna = "SERIAL";
						} else
							tipoColuna = "INT";
					} else if (tipoParametro.equals(BigDecimal.class)) {
						tipoColuna = "NUMERIC";
					} else {
						tipoColuna = "DESCONHECIDO";
					}
					if (i > 0) {
						sb.append(",");
					}
					sb.append("\n\t").append(nomeColuna).append(' ').append(tipoColuna);
				}
			}
			{
				sb.append(",\n\tPRIMARY KEY( ");
				for (int i = 0, achou = 0; i < atributos.length; i++) {
					Field field = atributos[i];
					if (field.isAnnotationPresent(Coluna.class)) {
						Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
						if (anotacaoColuna.pk()) {
							if (achou > 0) {
								sb.append(", ");
							}
							if (anotacaoColuna.nome().isEmpty()) {
								sb.append(field.getName().toUpperCase());
							} else {
								sb.append(anotacaoColuna.nome());
							}
							achou++;
						}
					}
				}
				sb.append(" )");
			}

			sb.append("\n);");

			return sb.toString();

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public String getSqlInsert(Object o, List lista) {
		StringBuilder sb = new StringBuilder();

		sb.append("INSERT INTO ");
		sb.append(getNomeTabela(o));
		sb.append(" (");
		sb.append(getAtributos(o));
		sb.append(") VALUES (");
		sb.append(getPar(o, lista));
		sb.append(");");
		return sb.toString();
	}

	public String getSqlSelectAll(Object o) {

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT * FROM ");
		sb.append(getNomeTabela(o) + ";");

		return sb.toString().toUpperCase();
	}

	public String getSqlUpdate(Object o, String coluna, String valor, int id) {
		StringBuilder sb = new StringBuilder();

		sb.append("UPDATE ");
		sb.append(getNomeTabela(o));
		sb.append(" SET");
		sb.append(coluna);
		sb.append("= ");
		sb.append(valor);
		sb.append(" WHERE ID=");
		sb.append(id);
		sb.append(";");

		return sb.toString().toUpperCase();
	}

	public String getSqlDelete(Object o, int id) {
		StringBuilder sb = new StringBuilder();

		sb.append("DELETE FROM ");
		sb.append(getNomeTabela(o));
		sb.append(" WHERE ID= ");
		sb.append(id);
		sb.append(";");

		return sb.toString().toUpperCase();
	}

	public String getSqlSelectOne(Object o, int id) {

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT * FROM ");
		sb.append(getNomeTabela(o));
		sb.append(" WHERE ID=" + id + ";");

		return sb.toString().toUpperCase();
	}

	public String getSqlDrop(Object o) {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE ");
		sb.append(getNomeTabela(o));
		sb.append(";");
		return sb.toString();
	}

	private Object getPar(Object o, List valores) {
		StringBuilder sb = new StringBuilder();
		int qtd = o.getClass().getDeclaredFields().length;
		int i = 0;

		for (Field f : o.getClass().getDeclaredFields()) {
			if (i > 1)
				sb.append(",");
			if (f.getType().equals(String.class) && !f.getAnnotation(Coluna.class).pk()) {
				sb.append("'");
				sb.append(valores.get(i));
				sb.append("'");
			} else if (valores.get(i).equals("") && !f.getAnnotation(Coluna.class).pk()) {
				sb.append(0);
			} else {
				valores.get(i);
			}
			i++;
		}
		return sb.toString();
	}

	private Object getAtributos(Object o) {
		StringBuilder sb = new StringBuilder();
		int cont = 0;
		for (Field f : o.getClass().getDeclaredFields()) {
			if (!f.getName().contains("id")) {
				if (cont > 1)
					sb.append(",");
				sb.append(f.getName());
			}
			cont++;
		}
		return sb.toString();
	}

	private Object getNomeTabela(Object o) {
		return o.getClass().getSimpleName();
	}
}
