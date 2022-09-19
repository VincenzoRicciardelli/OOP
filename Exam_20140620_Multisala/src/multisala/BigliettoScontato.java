package multisala;

public class BigliettoScontato extends Biglietto{

	private int percentualeSconto;
	private String tipologiaSconto;

	public BigliettoScontato(String codiceBiglietto, Proiezione proiezione, int fila, int posto, int percentualeSconto,
			String tipologiaSconto) {
		super(codiceBiglietto, proiezione, fila, posto);
		this.percentualeSconto = percentualeSconto;
		this.tipologiaSconto = tipologiaSconto;
	}

	public int getPercentualeSconto() {
		return percentualeSconto;
	}

	public String getTipologiaSconto() {
		return tipologiaSconto;
	}

	public void setPercentualeSconto(int percentualeSconto) {
		this.percentualeSconto = percentualeSconto;
	}

	public void setTipologiaSconto(String tipologiaSconto) {
		this.tipologiaSconto = tipologiaSconto;
	}	
}
