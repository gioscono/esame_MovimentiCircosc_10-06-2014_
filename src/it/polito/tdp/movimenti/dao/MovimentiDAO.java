package it.polito.tdp.movimenti.dao;

import it.polito.tdp.movimenti.bean.CircoscrizioneEMovimenti;
import it.polito.tdp.movimenti.bean.Movimento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MovimentiDAO {
	
	public List<Movimento> getAllMovimenti() {
		String sql = "SELECT * FROM movimenti_intraurbani";

		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);

			List<Movimento> movimenti = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {

				Calendar cal = Calendar.getInstance() ;
				cal.clear();
				cal.set(
						res.getInt("ANNO_REGISTRAZIONE"),
						res.getInt("MESE_REGISTRAZIONE")-1,
						res.getInt("GIORNO_REGISTRAZIONE")
						) ;
				
				
				Movimento mov = new Movimento(
						cal.getTime(),
						res.getInt("CIRCOSCRIZIONE_PROVENIENZA"),
						res.getInt("CIRCOSCRIZIONE_DESTINAZIONE"),
						res.getInt("TOTALE_EVENTI")
						);
				movimenti.add(mov);

			}

			st.close();
			conn.close();

			return movimenti;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error", e);
		}

	}
	public List<Integer> getAllCircoscrizioni(){
		
		final String sql = "select distinct CIRCOSCRIZIONE_PROVENIENZA " + 
				           "from movimenti_intraurbani ";
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);

			List<Integer> circoscrizioni = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				circoscrizioni.add(res.getInt("CIRCOSCRIZIONE_PROVENIENZA"));
			}
			conn.close();
			return circoscrizioni;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error", e);
		}	
	}
	
	public List<CircoscrizioneEMovimenti> getAllMovimentiFrom(int partenza){
		
		final String sql = "SELECT CIRCOSCRIZIONE_DESTINAZIONE, sum(TOTALE_EVENTI) as cambi " + 
							"FROM movimenti_intraurbani " + 
							"WHERE CIRCOSCRIZIONE_PROVENIENZA=? " + 
							"group by CIRCOSCRIZIONE_DESTINAZIONE ";
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, partenza);
			List<CircoscrizioneEMovimenti> movimenti = new ArrayList<>();
			
			ResultSet res = st.executeQuery();
		
			while (res.next()) {
				movimenti.add(new CircoscrizioneEMovimenti(partenza, res.getInt("CIRCOSCRIZIONE_DESTINAZIONE"), res.getInt("cambi")));
			}
			conn.close();
			return movimenti;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error", e);
		}	
				
		
		
	}
	public static void main(String[] args) {
		MovimentiDAO self = new MovimentiDAO() ;
		
		List<Movimento> movs = self.getAllMovimenti() ;
		for(Movimento m : movs) {
			
			System.out.format("%d-%d: %d (%4$tY-%4$tm-%4$td)\n", m.getCircoscrizioneProvenienza(),
					m.getCircoscrizioneDestinazione(), m.getNumeroEventi(),
					m.getRegistrazione()) ;
		}
	}


}
