package br.univel;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Field;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.univel.anotations.Coluna;

public class UtilTela {

	public JPanel gerarTela(Object o ){
		JPanel painelContente=(JPanel) getPainelContente(o);
		
		return painelContente;
		
	}
	private Container getPainelContente(Object o) {
		int y = 0;
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,0.0,0.0,0.0,0.0,0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		Class<?> clazz = o.getClass();
		for (Field f : clazz.getDeclaredFields()) {
			JLabel lblNovaLabel = new JLabel(f.getAnnotation(Coluna.class).nome());
			contentPane.add(lblNovaLabel, createConstraints(0, y));
			y++;
			JTextField JCampo = new JTextField(10);
			contentPane.add(JCampo, createConstraints(0, y));
			y++;
		}

		return contentPane;
	}

	private GridBagConstraints createConstraints(int x, int y) {
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridx = x;
		gbc_textField.gridy = y;
		return gbc_textField;
	}
}
