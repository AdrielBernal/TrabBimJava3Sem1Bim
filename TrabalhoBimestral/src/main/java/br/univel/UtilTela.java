package br.univel;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.univel.anotations.Coluna;

public class UtilTela {

	private Banco b;
	private JTable table;
	private List<JTextField> listTxtField;
	private TableModel model;

	public JPanel gerarTela(Object o) {
		b = new Banco(o);
		JPanel painelContente = (JPanel) getPainelContente(o);

		return painelContente;

	}

	private Container getPainelContente(Object o) {
		int y = 0;
		JPanel contentPane = new JPanel();
		table = new JTable();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		Class<?> clazz = o.getClass();
		listTxtField = new ArrayList<>();
		for (Field f : clazz.getDeclaredFields()) {
			JLabel lblNovaLabel = new JLabel(f.getAnnotation(Coluna.class).nome());
			contentPane.add(lblNovaLabel, createConstraints(y, 0));
			y++;
			JTextField JCampo = new JTextField(10);
			contentPane.add(JCampo, createConstraints(y, 0));
			listTxtField.add(JCampo);
			y++;
		}

		JButton btnAdicionar = new JButton("Add");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					b.insert(listTxtField);

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnAdicionar, createConstraintsButton(0, y));

		JButton btnBuscarTodos = new JButton("Search All");
		btnBuscarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model = new TableModel(o, b.selectAll());
					table.setModel(model);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnBuscarTodos, createConstraintsButton(1, y));

		JButton btnApagar = new JButton("Delete");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnApagar, createConstraintsButton(2, y));

		JButton btnCreate = new JButton("Create Table");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					b.createTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnCreate, createConstraintsButton(3, y));

		JButton btnDrop = new JButton("Drop Table");
		btnDrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					b.dropTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnDrop, createConstraintsButton(4, y));
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, createConstraintsScrollPanel(0, y + 1));
		model = new TableModel(o, null);
		table.setModel(model);
		scrollPane.setViewportView(table);
		return contentPane;
	}

	private GridBagConstraints createConstraints(int x, int y) {
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridx = x;
		gbc_textField.gridy = y;
		return gbc_textField;
	}

	private GridBagConstraints createConstraintsButton(int x, int y) {
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridx = x;
		gbc_button.gridy = y;
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		return gbc_button;
	}

	private GridBagConstraints createConstraintsScrollPanel(int x, int y) {
		GridBagConstraints gbc_schollPanel = new GridBagConstraints();
		gbc_schollPanel.gridheight = 10;
		gbc_schollPanel.gridwidth = 5;
		gbc_schollPanel.fill = GridBagConstraints.BOTH;
		gbc_schollPanel.gridx = x;
		gbc_schollPanel.gridy = y;

		return gbc_schollPanel;
	}
}
