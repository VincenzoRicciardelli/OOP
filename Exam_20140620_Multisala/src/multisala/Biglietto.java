package multisala;

public class Biglietto {
	
	private String codiceBiglietto;
	private Proiezione proiezione;
	private int fila;
	private int posto;
	
	public Biglietto(String codiceBiglietto, Proiezione proiezione, int fila, int posto) {
		this.codiceBiglietto = codiceBiglietto;
		this.proiezione = proiezione;
		this.fila = fila;
		this.posto = posto;
	}

	public String getCodiceBiglietto() {
		return codiceBiglietto;
	}

	public Proiezione getProiezione() {
		return proiezione;
	}

	public int getFila() {
		return fila;
	}

	public int getPosto() {
		return posto;
	}
}
