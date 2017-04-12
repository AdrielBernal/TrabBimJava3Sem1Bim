package br.univel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

	private String[][] xampson;
	private List lista;

	public TableModel(Object o) {
		Class<?> clazz = o.getClass();
		lista = new ArrayList<>();
		for (Field f : clazz.getDeclaredFields()) {
			lista.add(f);
		}
		xampson = new String[lista.size()][10];
		for (int i = 0; i < lista.size(); i++) {
			for (int j = 0; j < 10; j++) {
				xampson[i][j] = "xampson"+i+j;
				System.out.println(xampson[i][j]);
			}

		}

	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return xampson[rowIndex][columnIndex];
	}
	@Override
	public String getColumnName(int column) {
		return (String) lista.get(column);
	}

	public static void main(String[] args) {
		Cliente c = new Cliente();
		new TableModel(c);
	}
}
