import java.util.*;

import multisala.*;

public class Esempio {

	public static void main(String[] args) throws EccezionePostoOccupato, EccezioneProiezioneCompleta, EccezionePostiAdiacentiNonDisponibili {

		Collection<Sala> sale;
		Collection<Film> film;
		Collection<Proiezione> proiezioni;
		Collection<Biglietto> biglietti;
		
		Multisala m = new Multisala();
		
		System.out.println("############# R1. Sale e film #############");

		System.out.println("\nDefinita nuova sala da 10 file, 30 posti per fila");
		int codiceSala = m.nuovaSala(10, 30);
		System.out.println(" Codice sala: "+codiceSala);
		
		System.out.println("\nCerca sala con codice 1");
		
		Sala s1 = m.cercaSala(codiceSala);
		System.out.println(" Codice sala: "+s1.getCodiceSala());
		System.out.println(" Numero di file: "+s1.getNumeroFile());
		System.out.println(" Numero di posti per fila: "+s1.getNumeroPostiPerFila());
				
		System.out.println("\nDefinite altre due sale");
		          m.nuovaSala(10, 20);
		          m.nuovaSala(15, 30);

		System.out.println("\nElenco sale (ordinate per codice di sala crescenti)");
		sale = new LinkedList<Sala>(m.elencoSalePerCodice());
		for(Sala tempSala : sale)
			System.out.println(" Sala "+tempSala.getCodiceSala()+", dim. "+tempSala.getNumeroFile()+"x"+tempSala.getNumeroPostiPerFila()+" ("+(tempSala.getNumeroFile()*tempSala.getNumeroPostiPerFila())+" posti)");
		
		System.out.println("\nElenco sale (ordinate per numero di posti totale decrescente)");
		sale = new LinkedList<Sala>(m.elencoSalePerNumeroDiPosti());
		for(Sala tempSala : sale)
			System.out.println(" Sala "+tempSala.getCodiceSala()+", dim. "+tempSala.getNumeroFile()+"x"+tempSala.getNumeroPostiPerFila()+" ("+(tempSala.getNumeroFile()*tempSala.getNumeroPostiPerFila())+" posti)");
		          
		System.out.println("\nAggiunto nuovo film al catalogo");
		Film f1 = m.nuovoFilm("La grande bellezza", "P. Sorrentino", 2013, 142);
		System.out.println(" "+f1.getTitolo()+" di "+f1.getRegista()+ " ("+f1.getAnno()+"), durata "+f1.getDurata()+" min");

		System.out.println("\nAggiunti altri due film");
		Film f2 = m.nuovoFilm("Mediterraneo", "G. Salvatores", 1991, 96);
        Film f3 = m.nuovoFilm("La vita e' bella", "R. Benigni", 1997, 116);

  		System.out.println("\nElenco film a catalogo (in ordine di inserimento)");
		film = new LinkedList<Film>(m.elencoFilm());
		for(Film tempFilm : film)
			System.out.println(" "+tempFilm.getTitolo()+" di "+tempFilm.getRegista()+ " ("+tempFilm.getAnno()+"), durata "+tempFilm.getDurata()+" min");

		
		
		System.out.println("\n############# R2. Proiezioni #############");
		
		System.out.println("\nNuova proiezione");
		Proiezione p1 = m.nuovaProiezione(1, f1, "20140620","1700",  7.00, false);
		System.out.println(" Sala "+p1.getSala().getCodiceSala()+" "+p1.getData()+" "+p1.getOra()+" "+p1.getFilm().getTitolo()+" "+p1.getPrezzoIntero()+" euro");

		System.out.println("\nAggiunte altre quattro proiezioni");
        				m.nuovaProiezione(1,f1, "20140620","1400",  7.00, false);
		                m.nuovaProiezione(1,f1, "20140620","2000",  8.00, false);
		                m.nuovaProiezione(2,f2, "20140620","2000", 10.00, true);
		                m.nuovaProiezione(3,f3, "20140622","2200",  8.00, false);
		                m.nuovaProiezione(3,f1, "20140621","2000",  8.00, false);
		
		System.out.println("\nElenco proiezioni (ordinate per sala, data e ora crescenti)");
		proiezioni = new LinkedList<Proiezione>(m.elencoProiezioni());
		for(Proiezione tempProiezione : proiezioni)
			System.out.println(" Sala "+tempProiezione.getSala().getCodiceSala()+" "+tempProiezione.getData()+" "+tempProiezione.getOra()+" "+tempProiezione.getFilm().getTitolo()+" "+tempProiezione.getPrezzoIntero()+" euro");
		
		System.out.println("\nElenco proiezioni in data 20140620 (ordinate per sala, data e ora crescenti)");
		proiezioni = new LinkedList<Proiezione>(m.elencoProiezioniInData("20140620"));
		for(Proiezione tempProiezione : proiezioni)
			System.out.println(" Sala "+tempProiezione.getSala().getCodiceSala()+" "+tempProiezione.getData()+" "+tempProiezione.getOra()+" "+tempProiezione.getFilm().getTitolo()+" "+tempProiezione.getPrezzoIntero()+" euro");

		System.out.println("\nRicerca di proiezioni che contengano '1700' (ordinate per sala, data e ora crescenti)");
		proiezioni = new LinkedList<Proiezione>(m.cercaProiezioni("1700"));
		for(Proiezione tempProiezione : proiezioni)
			System.out.println(" Sala "+tempProiezione.getSala().getCodiceSala()+" "+tempProiezione.getData()+" "+tempProiezione.getOra()+" "+tempProiezione.getFilm().getTitolo()+" "+tempProiezione.getPrezzoIntero()+" euro");
		
		System.out.println("\nElenco proiezioni in sala 1 (ordinate per data e ora crescenti)");
		proiezioni = new LinkedList<Proiezione>(s1.elencoProiezioni());
		for(Proiezione tempProiezione : proiezioni)
			System.out.println(" Sala "+tempProiezione.getSala().getCodiceSala()+" "+tempProiezione.getData()+" "+tempProiezione.getOra()+" "+tempProiezione.getFilm().getTitolo()+" "+tempProiezione.getPrezzoIntero()+" euro");

		
		
		System.out.println("\n############# R3. Acquisto biglietto singolo e situazione sala #############");
		
		System.out.println("\nAcquisto biglietto per proiezione");
		Biglietto b1 = m.acquistaBiglietto(p1, 1, 6);
		System.out.println(" Emesso biglietto  "+b1.getCodiceBiglietto()+": Sala "+b1.getProiezione().getSala().getCodiceSala()+" "+b1.getProiezione().getData()+" "+b1.getProiezione().getOra()+" fila "+b1.getFila()+" posto "+b1.getPosto());

		System.out.println("\nAcquisto biglietto scontato per stessa proiezione");
		BigliettoScontato b2 = m.acquistaBigliettoScontato(p1, 2, 3, 50, "Over 65");
		System.out.println(" Emesso biglietto  "+b2.getCodiceBiglietto()+": Sala "+b2.getProiezione().getSala().getCodiceSala()+" "+b2.getProiezione().getData()+" "+b2.getProiezione().getOra()+" fila "+b2.getFila()+" posto "+b2.getPosto());

		System.out.println("\nCerca biglietto");
		Biglietto b3 = m.cercaBiglietto(p1,1,6);
		System.out.println(" Biglietto trovato "+b3.getCodiceBiglietto()+": Sala "+b3.getProiezione().getSala().getCodiceSala()+" "+b3.getProiezione().getData()+" "+b3.getProiezione().getOra()+" fila "+b3.getFila()+" posto "+b3.getPosto());
		
		System.out.println("\nPosti liberi per la proiezione");
		System.out.println(" "+p1.postiLiberi());

		System.out.println("\nSituazione posti in sala per la proiezione");
        System.out.println(p1.situazione());


        
		System.out.println("\n############# R4. Acquisto biglietti multipli ed incasso #############");
        
		System.out.println("\nAcquisto 3 biglietti");
		biglietti  = m.acquistaBiglietti(p1, 3);
		for(Biglietto b : biglietti)
			System.out.println(" Emesso biglietto "+b.getCodiceBiglietto()+": Sala "+b.getProiezione().getSala().getCodiceSala()+" "+b.getProiezione().getData()+" "+b.getProiezione().getOra()+" fila "+b.getFila()+" posto "+b.getPosto());

		System.out.println("\nPosti liberi per la proiezione");
		System.out.println(" "+p1.postiLiberi());

		System.out.println("\nSituazione posti per proiezione");
        System.out.println(p1.situazione());
		
        System.out.println("\nIncasso in data 20140620");
		System.out.println(" "+m.calcolaIncassoInData("20140620")+" euro");
		
	}

}
