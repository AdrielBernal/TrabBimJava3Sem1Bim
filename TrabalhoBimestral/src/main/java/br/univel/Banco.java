package br.univel;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;

public class Banco {

	private Connection con;
	private SqlUtils su;
	private Object pojo;

	public Banco(Object o) {
		pojo = o;
		su = new SqlUtils();
	}

	public void conectar() {

		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String pass = "postgres";

		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException ex) {
			Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void desconectar() {
		if (con != null) {

			try {
				con.close();
			} catch (SQLException ex) {
				Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void createTable() throws SQLException {
		conectar();
		PreparedStatement ps = con.prepareStatement(su.getSqlTable(pojo));
		ps.executeUpdate();
		desconectar();
	}

	public void insert(List<?> lista) throws SQLException {
		conectar();
		PreparedStatement ps = con.prepareStatement(su.getSqlInsert(pojo, lista));
		ps.executeUpdate();
		desconectar();
	}

	public List selectOne(int id) throws SQLException {
		conectar();
		PreparedStatement ps = con.prepareStatement(su.getSqlSelectOne(pojo, id));
		int cont = 1;
		List list = new ArrayList();
		Field[] f = pojo.getClass().getDeclaredFields();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			cont = 1;
			while (cont != f.length + 1) {
				list.add(rs.getObject(cont));
				cont++;
			}

		}
		desconectar();
		return list;
	}

	public List selectAll() {
		conectar();
		PreparedStatement ps;
		List list = new ArrayList();
		try {
			ps = con.prepareStatement(su.getSqlSelectAll(pojo));
			int cont = 1;
			Field[] f = pojo.getClass().getDeclaredFields();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				cont = 1;
				while (cont != f.length + 1) {
					list.add(rs.getObject(cont));
					cont++;
				}

			}

		} catch (SQLException e) {
			return list;
		}
		desconectar();
		return list;
	}

	public void update(String coluna, String valor, int id) throws SQLException {
		conectar();
		PreparedStatement ps = con.prepareStatement(su.getSqlUpdate(pojo, coluna, valor, id));
		desconectar();
	}

	public void dropTable() throws SQLException {
		conectar();
		PreparedStatement ps = con.prepareStatement(su.getSqlDrop(pojo));
		ps.executeUpdate();
		desconectar();
	}

	public void delete(int id) throws SQLException {
		conectar();
		PreparedStatement ps = con.prepareStatement(su.getSqlDelete(pojo, id));
		ps.executeUpdate();
		desconectar();
	}

}
