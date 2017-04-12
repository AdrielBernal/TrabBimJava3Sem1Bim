package br.univel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Principal {
	private Connection con;

	public Principal() throws SQLException {
//		conectar();
		Cliente o = new Cliente();
		SqlUtils sq = new SqlUtils();
//		PreparedStatement ps = con.prepareStatement(sq.getSqlTable(o));
//		ps.executeUpdate();
//		 System.out.println(sq.getSqlTable(o));
		 System.out.println(sq.getSqlInsert(o));
//		 System.out.println(sq.getSqlSelectAll(o));
//		 System.out.println(sq.getSqlSelectOne(o));
//		 System.out.println(sq.getSqlDelete(o));
//		 System.out.println(sq.getSqlUpdate(o, "NOME"));
//		desconectar();
	}

	public void conectar() {
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String pass = "univel";
		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException ex) {
			Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
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

	public static void main(String[] args) {
		try {
			new Principal();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
