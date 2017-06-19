package it.polito.tdp.movimenti.bean;

public class CircoscrizioneEMovimenti implements Comparable<CircoscrizioneEMovimenti>{
	
	private int circPartenza;
	private int circDestinazione;
	private int numeroMovimenti;
	
	public CircoscrizioneEMovimenti(int circPartenza, int circDestinazione, int numeroMovimenti) {
		super();
		this.circPartenza = circPartenza;
		this.circDestinazione = circDestinazione;
		this.numeroMovimenti = numeroMovimenti;
	}

	public int getCircPartenza() {
		return circPartenza;
	}

	public void setCircPartenza(int circPartenza) {
		this.circPartenza = circPartenza;
	}

	public int getCircDestinazione() {
		return circDestinazione;
	}

	public void setCircDestinazione(int circDestinazione) {
		this.circDestinazione = circDestinazione;
	}

	public int getNumeroMovimenti() {
		return numeroMovimenti;
	}

	public void setNumeroMovimenti(int numeroMovimenti) {
		this.numeroMovimenti = numeroMovimenti;
	}

	@Override
	public int compareTo(CircoscrizioneEMovimenti altra) {
		
		return -(this.numeroMovimenti-altra.numeroMovimenti);
	}

	@Override
	public String toString() {
		return "Circoscrizione "+this.circDestinazione+"-"+this.numeroMovimenti;
	}
	
	
	

}
