package multisala;


public class Proiezione implements Comparable<Proiezione>{

	private Sala sala;
	private Film film;
	private String data;
	private String ora;
	private double prezzoIntero;
	private boolean in3d;
	private Biglietto[][] mappa;
	private int postiLiberi;

	public Proiezione(Sala sala, Film film, String data, String ora, double prezzoIntero, boolean in3d) {
		this.sala = sala;
		this.film = film;
		this.data = data;
		this.ora = ora;
		this.prezzoIntero = prezzoIntero;
		this.in3d = in3d;
		
		mappa = new Biglietto[sala.getNumeroFile()][sala.getNumeroPostiPerFila()];
		postiLiberi = sala.getNumeroTotalePosti();
	}

	public Film getFilm() {
		return film;
	}

	public Sala getSala() {
		return sala;
	}

	public String getData() {
		return data;
	}

	public String getOra() {
		return ora;
	}

	public double getPrezzoIntero() {
		return prezzoIntero;
	}

	public boolean isIn3D() {
		return in3d;
	}
	
	public int postiLiberi(){
		return postiLiberi;
	}

	public String situazione(){
		String situazione = new String();
		for(int i=0; i<sala.getNumeroFile(); i++)
		{
			for(int j=0; j<sala.getNumeroPostiPerFila(); j++)
			{
				if(mappa[i][j]!=null)
					situazione=situazione+" "+"#";
				else
					situazione=situazione+" "+"_";
			}
			situazione=situazione+"\n";
		}
		return situazione;
	}
	
	public int compareTo(Proiezione altra) {
		if(this.getData().compareTo(altra.getData())!=0)
			return this.getData().compareTo(altra.getData());
		return this.getOra().compareTo(altra.getOra());
	}
	
	public Biglietto getBiglietto(int fila, int posto){
		return mappa[fila][posto];
	}

	public void setBiglietto(int fila, int posto, Biglietto biglietto){
		mappa[fila][posto] = biglietto;
		this.postiLiberi--;
	}

	public double calcolaIncassoInData(){
		double incasso = 0;
		for (int f =0; f<sala.getNumeroFile();f++)
			for(int p=0; p<sala.getNumeroPostiPerFila();p++){
				Biglietto tempBiglietto = this.getBiglietto(f, p);
				if(tempBiglietto!=null)
				{
					if (tempBiglietto instanceof BigliettoScontato)
						incasso +=this.prezzoIntero - (this.prezzoIntero*((double)((BigliettoScontato)tempBiglietto).getPercentualeSconto()))/100.0;
					else{
						incasso +=this.prezzoIntero;
					}
				}
			}
		return incasso;
	}
	
}
