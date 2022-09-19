package multisala;

import java.util.*;

public class Sala implements Comparable<Sala>{

	private int numeroFile;
	private int numeroPostiPerFila;
	private int codiceSala;
	List<Proiezione> proiezioni = new LinkedList<>();

	public Sala(int codiceSala, int numeroFile, int numeroPostiPerFila) {
		this.codiceSala = codiceSala;
		this.numeroFile = numeroFile;
		this.numeroPostiPerFila = numeroPostiPerFila;
	}

	public int getCodiceSala() {
		return codiceSala;
	}

	public int getNumeroFile() {
		return numeroFile;
	}

	public int getNumeroPostiPerFila() {
		return numeroPostiPerFila;
	}
	
	public int getNumeroTotalePosti() {
		return numeroFile*numeroPostiPerFila;
	}
	public Collection<Proiezione> elencoProiezioni(){
		Collections.sort(proiezioni);
		return proiezioni;
	}

	public void addProiezione(Proiezione p) {
		proiezioni.add(p);
	}
	
	public int compareTo(Sala altra) {
		return (altra.getNumeroTotalePosti())-(this.getNumeroTotalePosti());
	}
}
