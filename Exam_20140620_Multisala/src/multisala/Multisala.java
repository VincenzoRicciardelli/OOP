package multisala;

import java.util.*;
import java.util.stream.Collectors;

public class Multisala {

	int nSala = 1;
	int codiceProgressivo = 0;
	Map<Integer, Sala> sale = new TreeMap<>();
	List<Sala> listaSale = new LinkedList<>();
	Map<String, Film> films = new TreeMap<>();
	List<Film> listaFilm = new LinkedList<>();
	Map<String, Proiezione> proiezioni = new TreeMap<>();
	Map<String, Biglietto> biglietti = new TreeMap<>();
	Map<String, Biglietto> bigliettiScontati = new TreeMap<>();
	
	public int nuovaSala(int numeroFile, int numeroPostiPerFila) {
		if(nSala <=10) {
			Sala s = new Sala(nSala, numeroFile, numeroPostiPerFila);
			sale.put(nSala, s);
			listaSale.add(s);
			nSala++;
			return nSala-1;
		}
		return -1;
	}

	public Sala cercaSala(int codiceSala){
		if(sale.containsKey(codiceSala))
		{
			Sala s= sale.get(codiceSala);
			return s;
		}
		return null;
	}
	
	public Collection<Sala> elencoSalePerCodice(){
		return sale.values();
	}

	public Collection<Sala> elencoSalePerNumeroDiPosti(){
		Collections.sort(listaSale);
		return listaSale;
	}
	
	public Film nuovoFilm(String titolo, String regista, int anno, int durata){
		if(!films.containsKey(titolo+" "+regista)) {
			Film f = new Film(titolo, regista, anno, durata);
			films.put(titolo+" "+regista, f);
			listaFilm.add(f);
			return f;
		}
		else
		{
			Film f = films.get(titolo+" "+regista);
			return f;
		}
	}
	
	public Collection<Film> elencoFilm(){
		return listaFilm;
	}

	public Proiezione nuovaProiezione(int codiceSala, Film film, String data, String ora, double prezzoIntero, boolean in3D){
		if(sale.containsKey(codiceSala) && film!=null)
		{
			Sala s = sale.get(codiceSala);
			Proiezione p = new Proiezione(sale.get(codiceSala), film, data, ora, prezzoIntero, in3D);
			proiezioni.put(codiceSala+" "+data+" "+ora, p);
			s.addProiezione(p);
			return p;
		}
		else return null;
	}
	
	public Collection<Proiezione> elencoProiezioni(){
		return proiezioni.values();
	}
	
	public Collection<Proiezione> elencoProiezioniInData(String data){
		return proiezioni.values().stream()
				.filter(p -> p.getData().equals(data))
				.collect(Collectors.toList());
	}
	
	public Collection<Proiezione> cercaProiezioni(String daCercare){
		return proiezioni.values().stream()
				.filter(p -> p.getData().equals(daCercare) || p.getOra().equals(daCercare))
				.collect(Collectors.toList());
	}
	
	public Biglietto acquistaBiglietto(Proiezione proiezione, int fila, int posto) throws EccezioneProiezioneCompleta, EccezionePostoOccupato{

		if(proiezione.postiLiberi()==0)
			throw new EccezioneProiezioneCompleta();
		if(proiezione.getBiglietto(fila-1, posto-1)!=null)
			throw new EccezionePostoOccupato();
		String codiceBiglietto = proiezione.getSala().getCodiceSala()+"-"+proiezione.getData()+"-"+proiezione.getOra()+"-"+codiceProgressivo;
		Biglietto b = new Biglietto(codiceBiglietto, proiezione, fila, posto);
		biglietti.put(codiceBiglietto, b);
		proiezione.setBiglietto(fila-1, posto-1, b);
		codiceProgressivo++;
		return b;
	}
	
	public BigliettoScontato acquistaBigliettoScontato(Proiezione proiezione, int fila, int posto, int percentualeSconto, String tipologiaSconto) throws EccezioneProiezioneCompleta, EccezionePostoOccupato{
		
		if(proiezione.postiLiberi()==0)
			throw new EccezioneProiezioneCompleta();
		if(proiezione.getBiglietto(fila-1, posto-1)!=null)
			throw new EccezionePostoOccupato();
		String codiceBiglietto = proiezione.getSala().getCodiceSala()+"-"+proiezione.getData()+"-"+proiezione.getOra()+"-"+codiceProgressivo;
		BigliettoScontato bs = new BigliettoScontato(codiceBiglietto, proiezione, fila, posto, percentualeSconto, tipologiaSconto);
		bigliettiScontati.put(codiceBiglietto, bs);
		proiezione.setBiglietto(fila-1, posto-1, bs);
		codiceProgressivo++;
		return bs;
	}
	
	public Biglietto cercaBiglietto(Proiezione proiezione, int fila, int posto){
		return proiezione.getBiglietto(fila-1, posto-1);
	}
	
	public Collection<Biglietto> acquistaBiglietti(Proiezione proiezione, int numBiglietti) throws EccezioneProiezioneCompleta, EccezionePostiAdiacentiNonDisponibili, EccezionePostoOccupato{
		List<Biglietto> bigliettiMultipli = new LinkedList<>();
		if(proiezione.postiLiberi()==0)
			throw new EccezioneProiezioneCompleta();
		for(int i=0; i<proiezione.getSala().getNumeroFile(); i++)
		{
			int conta=numBiglietti;
			for(int j=0; j<proiezione.getSala().getNumeroPostiPerFila() && conta!=0; j++)
			{
				if(proiezione.getBiglietto(i, j)==null)
				{
					String codiceBiglietto = proiezione.getSala().getCodiceSala()+"-"+proiezione.getData()+"-"+proiezione.getOra()+"-"+codiceProgressivo;
					Biglietto b = new Biglietto(codiceBiglietto, proiezione, i, j);
					proiezione.setBiglietto(i, j, b);
					bigliettiMultipli.add(b);
					conta--;
					codiceProgressivo++;
				}
			}
			if(conta==0)
			{
				return bigliettiMultipli;
			}
		}
		throw new EccezionePostiAdiacentiNonDisponibili();
	}

	public double calcolaIncassoInData(String data){
		double incasso = 0.0;
		for(Proiezione p : proiezioni.values()) {
			if(p.getData().compareTo(data)==0)
				incasso+=p.calcolaIncassoInData();
		}
		return incasso;
	}
	
}



