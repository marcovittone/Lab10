package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers(Map<Integer,River> mappa) {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				River r = new River(res.getInt("id"), res.getString("name"));
				rivers.add(r);
				mappa.put(res.getInt("id"), r);
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}

	public List<Flow> getMisurazioniDatoFiume(River r) {
		
		final String sql = "SELECT day,flow "
				+ "FROM flow "
				+ "WHERE flow.river = ?";

		List<Flow> misurazioni = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, r.getId());
			
			ResultSet res = st.executeQuery();

			while (res.next()) {
				misurazioni.add(new Flow(res.getDate("day").toLocalDate(), res.getDouble("flow"),r));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return misurazioni;
	}

	public void getMediaFlow(River r) {
		
		final String sql = "SELECT AVG(flow) AS media "
				+ "FROM flow "
				+ "WHERE flow.river = ?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, r.getId());
			
			ResultSet res = st.executeQuery();
			
			res.next();
			
			r.setFlowAvg(res.getDouble(1));

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

	}

	public LocalDate getDataPrimaMisurazione(River r) {
		
		final String sql = "SELECT MIN(DATE(DAY)) "
				+ "FROM flow "
				+ "WHERE flow.river = ?";
		
		LocalDate l;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, r.getId());
			
			ResultSet res = st.executeQuery();
			
			res.next();
			
			l = res.getDate(1).toLocalDate();

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return l;
	}
	
public LocalDate getDataUltimaMisurazione(River r) {
		
		final String sql = "SELECT MAX(DATE(DAY)) "
				+ "FROM flow "
				+ "WHERE flow.river = ?";
		
		LocalDate l;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, r.getId());
			
			ResultSet res = st.executeQuery();
			
			res.next();
			
			l = res.getDate(1).toLocalDate();

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return l;
	}
}
