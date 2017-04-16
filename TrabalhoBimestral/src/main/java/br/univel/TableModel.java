package br.univel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.univel.anotations.Coluna;

public class TableModel extends AbstractTableModel {

	private List lista;
	private Class<?> clazz;
	private int colunas;
	private List listaNome;
	private String[][] mat;

	public TableModel(Object o, List list) {
		clazz = o.getClass();
		lista = list;
		getListaNome();
		colunas = 0;
		for (Field f : clazz.getDeclaredFields()) {
			colunas++;
		}
		this.mat = new String[lista != null ? lista.size() : 0][colunas];
		int count = 0;
		for (int i = 0; i < (lista != null ? lista.size() / colunas : 0); i++) {
			for (int j = 0; j < this.colunas; j++) {
				mat[i][j] = lista.get(count).toString();
				count++;
			}
		}

	}

	@Override
	public int getColumnCount() {
		return colunas;
	}

	@Override
	public int getRowCount() {
		return lista != null ? lista.size() / colunas : 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return mat[rowIndex][columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		return (String) listaNome.get(column);
	}

	public void getListaNome() {
		listaNome = new ArrayList<>();
		for (Field f : clazz.getDeclaredFields()) {
			listaNome.add(f.getAnnotation(Coluna.class).nome().toString());
		}
	}

}
