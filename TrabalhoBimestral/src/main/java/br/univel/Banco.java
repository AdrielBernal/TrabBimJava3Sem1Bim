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

public class Banco {

	private Connection con;
	private SqlUtils su;
	private Object pojo;

	public Banco(Object o) {
		conectar();
		pojo = o;
		su = new SqlUtils();
	}

	public void conectar() {

		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String pass = "xampson";
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
				Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void createTable() throws SQLException {
		PreparedStatement ps = con.prepareStatement(su.getSqlTable(pojo));
		ps.executeUpdate();

	}

	public void insert(List<?> lista) throws SQLException {
		PreparedStatement ps = con.prepareStatement(su.getSqlInsert(pojo, lista));
		ps.executeUpdate();
	}

	public Object selectOne(int id) throws SQLException {
		PreparedStatement ps = con.prepareStatement(su.getSqlSelectOne(pojo, id));
		return null;
	}

	public List selectAll() throws SQLException {
		int cont = 1;
		List list = new ArrayList();
		Field[] f = pojo.getClass().getDeclaredFields();
		PreparedStatement ps = con.prepareStatement(su.getSqlSelectAll(pojo));
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			cont = 1;
			while (cont != f.length + 1) {
				list.add(rs.getObject(cont));
				cont++;
			}

		}
		return list;
	}

	public void update(String coluna, String valor, int id) throws SQLException {
		PreparedStatement ps = con.prepareStatement(su.getSqlUpdate(pojo, coluna, valor, id));

	}

	public void dropTable() throws SQLException {
		PreparedStatement ps = con.prepareStatement(su.getSqlDrop(pojo));
		ps.executeUpdate();
		desconectar();
	}

	// public static void main(String[] args) {
	// Cliente c = new Cliente();
	// Banco b = new Banco(c);
	// b.conectar();
	// try {
	// b.createTable();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// b.desconectar();
	// }

}
