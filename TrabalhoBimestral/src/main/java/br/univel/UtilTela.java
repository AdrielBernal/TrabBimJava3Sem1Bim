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
		JPanel painelContente = new JPanel();
		try {
			painelContente = (JPanel) getPainelContente(o);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return painelContente;

	}

	private Container getPainelContente(Object o) throws SQLException {
		int y = 0;
		JPanel contentPane = new JPanel();
		table = new JTable();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = get(o);
		// gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
		// 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		Class<?> clazz = o.getClass();
		listTxtField = new ArrayList<>();
		for (Field f : clazz.getDeclaredFields()) {
			JLabel lblNovaLabel = new JLabel(f.getAnnotation(Coluna.class).nome());
			contentPane.add(lblNovaLabel, createConstraints(0, y));
			y++;
			JTextField JCampo = new JTextField(10);
			contentPane.add(JCampo, createConstraints(0, y));
			listTxtField.add(JCampo);
			y++;
		}

		JButton btnAdicionar = new JButton("Add");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					List<String> list = new ArrayList<>();
					for (JTextField jTextField : listTxtField) {
						list.add(jTextField.getText());
					}
					b.insert(list);
					model = new TableModel(o, b.selectAll());
					table.setModel(model);
					cleanFields();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnAdicionar, createConstraintsButton(0, y));

		JButton btnBuscarTodos = new JButton("Search by ID");
		btnBuscarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model = new TableModel(o, b.selectOne(Integer.parseInt(listTxtField.get(0).getText())));
					table.setModel(model);
					cleanFields();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnBuscarTodos, createConstraintsButton(1, y));

		JButton btnApagar = new JButton("Delete");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					b.delete(Integer.parseInt(listTxtField.get(0).getText()));
					model = new TableModel(o, b.selectAll());
					table.setModel(model);
					cleanFields();
				} catch (NumberFormatException | SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		contentPane.add(btnApagar, createConstraintsButton(2, y));

		JButton btnCreate = new JButton("Create Table");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					b.createTable();
					model = new TableModel(o, b.selectAll());
					table.setModel(model);
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
					model = new TableModel(o, b.selectAll());
					table.setModel(model);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnDrop, createConstraintsButton(4, y));
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, createConstraintsScrollPanel(0, y + 1));
		model = new TableModel(o, b.selectAll());
		table.setModel(model);
		scrollPane.setViewportView(table);
		return contentPane;
	}

	private GridBagConstraints createConstraints(int x, int y) {
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridx = x;
		gbc_textField.gridy = y;
		gbc_textField.fill = GridBagConstraints.BOTH;
		return gbc_textField;
	}

	private GridBagConstraints createConstraintsButton(int x, int y) {
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridx = x;
		gbc_button.gridy = y;
		gbc_button.fill = GridBagConstraints.BOTH;
		return gbc_button;
	}

	private GridBagConstraints createConstraintsScrollPanel(int x, int y) {
		GridBagConstraints gbc_schollPanel = new GridBagConstraints();
		gbc_schollPanel.gridheight = 10;
		gbc_schollPanel.gridwidth = 100;
		gbc_schollPanel.fill = GridBagConstraints.BOTH;
		gbc_schollPanel.gridx = x;
		gbc_schollPanel.gridy = y;

		return gbc_schollPanel;
	}

	public void cleanFields() {
		for (JTextField f : listTxtField) {
			f.setText("");
		}
	}

	public int[] get(Object o) {
		int count = o.getClass().getDeclaredFields().length;
		int[] arrae = new int[count];
		for (int i = 0; i < arrae.length; i++) {
			arrae[i] = 0;
		}
		return arrae;

	}
}
