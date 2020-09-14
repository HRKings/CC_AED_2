import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.regex.*;

class CelulaDupla {
	public Time elemento;
	public CelulaDupla ant;
	public CelulaDupla prox;

	public CelulaDupla() {
		this(new Time());
	}

	public CelulaDupla(Time elemento) {
		this.elemento = elemento;
		this.ant = this.prox = null;
	}
}


class ListaTime {
	private CelulaDupla primeiro;
	private CelulaDupla ultimo;
	int mov;

	public ListaTime() {
		primeiro = new CelulaDupla();
		ultimo = primeiro;
		mov = 0;
	}

	public void inserirInicio(Time x) {
		CelulaDupla tmp = new CelulaDupla(x);

		tmp.ant = primeiro;
		tmp.prox = primeiro.prox;
		primeiro.prox = tmp;
		if(primeiro == ultimo){
			ultimo = tmp;
		}else{
			tmp.prox.ant = tmp;
		}
		tmp = null;
	}

	public void inserirFim(Time x) {
		ultimo.prox = new CelulaDupla(x);
		ultimo.prox.ant = ultimo;
		ultimo = ultimo.prox;
	}

	public Time removerInicio() throws Exception {
		if (primeiro == ultimo) {
			throw new Exception("Erro ao remover (vazia)!");
		}

		CelulaDupla tmp = primeiro;
		primeiro = primeiro.prox;
		Time resp = primeiro.elemento;
		tmp.prox = primeiro.ant = null;
		tmp = null;
		return resp;
	}

	public Time removerFim() throws Exception {
		if (primeiro == ultimo) {
			throw new Exception("Erro ao remover (vazia)!");
		} 
		Time resp = ultimo.elemento;
		ultimo = ultimo.ant;
		ultimo.prox.ant = null;
		ultimo.prox = null;
		return resp;
	}

	public void inserir(Time x, int pos) throws Exception {

		int tamanho = tamanho();

		if(pos < 0 || pos > tamanho){
			throw new Exception("Erro ao inserir posicao (" + pos + " / tamanho = " + tamanho + ") invalida!");
		} else if (pos == 0){
			inserirInicio(x);
		} else if (pos == tamanho){
			inserirFim(x);
		} else {
			// Caminhar ate a posicao anterior a insercao
			CelulaDupla i = primeiro;
			for(int j = 0; j < pos; j++, i = i.prox);

			CelulaDupla tmp = new CelulaDupla(x);
			tmp.ant = i;
			tmp.prox = i.prox;
			tmp.ant.prox = tmp.prox.ant = tmp;
			tmp = i = null;
		}
	}

	public Time remover(int pos) throws Exception {
		Time resp;
		int tamanho = tamanho();

		if (primeiro == ultimo){
			throw new Exception("Erro ao remover (vazia)!");

		} else if(pos < 0 || pos >= tamanho){
			throw new Exception("Erro ao remover (posicao " + pos + " / " + tamanho + " invalida!");
		} else if (pos == 0){
			resp = removerInicio();
		} else if (pos == tamanho - 1){
			resp = removerFim();
		} else {
			// Caminhar ate a posicao anterior a insercao
			CelulaDupla i = primeiro.prox;
			for(int j = 0; j < pos; j++, i = i.prox);

			i.ant.prox = i.prox;
			i.prox.ant = i.ant;
			resp = i.elemento;
			i.prox = i.ant = null;
			i = null;
		}

		return resp;
	}

	public void mostrar() {
		int pos = 0;
		for (CelulaDupla i = primeiro.prox; i != null; i = i.prox, pos++) {
				System.out.print("[" + pos +"] ");
				i.elemento.imprimir();
		}
	}

	public boolean pesquisar(Time x) {
		boolean resp = false;
		for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
			if(i.elemento == x){
				resp = true;
				i = ultimo;
			}
		}
		return resp;
	}

	public int tamanho() {
		int tamanho = 0; 
		for(CelulaDupla i = primeiro; i != ultimo; i = i.prox, tamanho++);
		return tamanho;
	}

	public void swap(CelulaDupla i, CelulaDupla j) {
		Time temp = i.elemento;
		i.elemento = j.elemento;
		j.elemento = temp;
		mov += 3;
	}

	/**
	 * Algoritmo de ordenacao Quicksort.
	 */
	public void quicksort() {
		quicksort(primeiro, ultimo);
	}

	private CelulaDupla andar(int pos){
		CelulaDupla c = primeiro;
		for(int i = 0; i < pos && c.prox != null; i++, c = c.prox);
		return c;
	}

	private void quicksort(CelulaDupla esq, CelulaDupla dir) {
		CelulaDupla i = esq, j = dir;
		System.out.println(i.elemento.apelido);
		CelulaDupla pivo = dir.ant;
		while (i.elemento.apelido.compareTo(j.elemento.apelido) < 0) {
			while (i.elemento.apelido.compareTo(pivo.elemento.apelido) < 0){
				i = i.prox;
			}

			while (j.elemento.apelido.compareTo(pivo.elemento.apelido) > 0){
				j = j.ant;
			}
				
			if (i.elemento.apelido.compareTo(j.elemento.apelido) < 0) {
				swap(i, j);
				i = i.prox;
				j = j.ant;
			}
		}
		if (esq.elemento.apelido.compareTo(j.elemento.apelido) < 0)
			quicksort(esq, j);
		if (dir.elemento.apelido.compareTo(i.elemento.apelido) > 0)
			quicksort(i, dir);
	}

}

class Time {

	String nome, apelido, estadio, tecnico, liga, nomeArquivo;
	int capacidade, fundacaoDia, fundacaoMes, fundacaoAno;
	long paginaTam;

	String html;

	public Time() {
		nome = "";
		apelido = "";
		estadio = "";
		tecnico = "";
		liga = "";
		nomeArquivo = "";
		capacidade = 0;
		fundacaoDia = 0;
		fundacaoMes = 0;
		fundacaoAno = 0;
		paginaTam = 0;
		html = "";
		// MyIO.setCharset ( "ISO-8859-1" );
		MyIO.setCharset("UTF-8");
	}

	public void setNome(String novo) {
		this.nome = novo;
	}

	public String getNome() {
		return this.nome;
	}

	public void setApelido(String novo) {
		this.apelido = novo;
	}

	public String getApelido() {
		return this.apelido;
	}

	public void setEstadio(String novo) {
		this.estadio = novo;
	}

	public String getEstadio() {
		return this.estadio;
	}

	public void setTecnico(String novo) {
		this.tecnico = novo;
	}

	public String getTecnico() {
		return this.tecnico;
	}

	public void setLiga(String novo) {
		this.liga = novo;
	}

	public String getLiga() {
		return this.liga;
	}

	public void setNomeArquivo(String novo) {
		this.nome = nomeArquivo;
	}

	public String getNomeArquivo() {
		return this.nomeArquivo;
	}

	public void setCapacidade(int novo) {
		this.capacidade = novo;
	}

	public int getCapacidade() {
		return this.capacidade;
	}

	public void setFundacaoDia(int novo) {
		this.fundacaoDia = novo;
	}

	public int getFundacaoDia() {
		return this.fundacaoDia;
	}

	public void setFundacaoMes(int novo) {
		this.fundacaoMes = novo;
	}

	public int getFundacaoMes() {
		return this.fundacaoMes;
	}

	public void setFundacaoAno(int novo) {
		this.fundacaoAno = novo;
	}

	public int getFundacaoAno() {
		return this.fundacaoAno;
	}

	public void setPaginaTam(long novo) {
		this.paginaTam = novo;
	}

	public long getPaginaTam() {
		return this.paginaTam;
	}

	public void pegarHtml(String path) throws Exception {
		nomeArquivo = path;
		Arq.openRead(path, "UTF-8");

		while (!html.contains("vcard")) {
			html = Arq.readLine();
		}

		paginaTam = Arq.length();
	}

	public void pesquisarNome() {
		if (html != null) {

			Pattern pattern = Pattern.compile("Full name.*?</th><td>(.*?)<.*?", Pattern.DOTALL);
			Matcher matcher = pattern.matcher(html);

			if (matcher.find()) {
				nome = matcher.group(1).replaceAll("\\<[^>]*>", "");
			}
		}
	}

	public void pesquisarApelido() {
		if (html != null) {

			Pattern pattern = Pattern.compile("Nickname.*?</th><td class=\"nickname\">(.*?)</td></tr>",
					Pattern.DOTALL);
			Matcher matcher = pattern.matcher(html);

			if (matcher.find()) {
				apelido = matcher.group(1).replaceAll("\\<[^>]*>", "");
			}
		}
	}

	// Pesquisa a fundacao e converte para numeros
	public void pesquisarFundacao() {
		if (html != null) {

			Pattern pattern = Pattern.compile(
					"Founded.*?</th><td>(.*?)<span class=\"noprint\">.*<span class=\"bday dtstart published updated\">(.*?)</span>",
					Pattern.DOTALL);
			Matcher matcher = pattern.matcher(html);

			if (matcher.find()) {
				if (matcher.group(1).replaceAll("\\<[^>]*>", "").replace("&#160;", " ").length() == 4) {
					fundacaoAno = Integer.parseInt(matcher.group(1).replace("&#160;", " ")
							.replaceAll("\\<[^>]*>", ""));
				} else {

					Pattern pattern2 = Pattern.compile("(.*)\\s(.*)\\s(.*)$", Pattern.DOTALL);
					Matcher matcher2 = pattern2.matcher(matcher.group(1).replace("&#160;", " ")
							.replaceAll("\\<[^>]*>", ""));

					if (matcher2.find()) {
						if (matcher2.group(1).length() <= 2)
							fundacaoDia = Integer.parseInt(matcher2.group(1));

						if (matcher2.group(1).length() > 2) {
							switch (matcher2.group(1)) {
							case "January":
								fundacaoMes = 1;
								break;

							case "February":
								fundacaoMes = 2;
								break;

							case "March":
								fundacaoMes = 3;
								break;

							case "April":
								fundacaoMes = 4;
								break;

							case "May":
								fundacaoMes = 5;
								break;

							case "June":
								fundacaoMes = 6;
								break;

							case "July":
								fundacaoMes = 7;
								break;

							case "August":
								fundacaoMes = 8;
								break;

							case "September":
								fundacaoMes = 9;
								break;

							case "October":
								fundacaoMes = 10;
								break;

							case "November":
								fundacaoMes = 11;
								break;

							case "Deceber":
								fundacaoMes = 12;
								break;
							}
						} else {
							switch (matcher2.group(2)) {
							case "January":
								fundacaoMes = 1;
								break;

							case "February":
								fundacaoMes = 2;
								break;

							case "March":
								fundacaoMes = 3;
								break;

							case "April":
								fundacaoMes = 4;
								break;

							case "May":
								fundacaoMes = 5;
								break;

							case "June":
								fundacaoMes = 6;
								break;

							case "July":
								fundacaoMes = 7;
								break;

							case "August":
								fundacaoMes = 8;
								break;

							case "September":
								fundacaoMes = 9;
								break;

							case "October":
								fundacaoMes = 10;
								break;

							case "November":
								fundacaoMes = 11;
								break;

							case "Deceber":
								fundacaoMes = 12;
								break;
							}
						}

						fundacaoAno = Integer.parseInt(matcher2.group(3));
					}
				}
			}
		}
	}

	public void pesquisarEstadio() {
		if (html != null) {
			Pattern pattern = Pattern.compile("Ground.*?</th><td class=\"label\"><a.*?>(.*?)</td></tr>",
					Pattern.DOTALL);
			Matcher matcher = pattern.matcher(html);

			if (matcher.find()) {
				estadio = matcher.group(1).replaceAll("\\<[^>]*>", "");
			}
		}
	}

	public void pesquisarCapacidade() {
		if (html != null) {
			Pattern pattern = Pattern.compile("Capacity.*?</th><td>(.*?)<.*?", Pattern.DOTALL);
			Matcher matcher = pattern.matcher(html);

			if (matcher.find()) {
				capacidade = Integer.parseInt(matcher.group(1).replace(",", "").replace(".", "")
						.replace(" ", "").replaceAll("\\(.*\\)", "")
						.replaceAll("\\<[^>]*>", ""));
			}
		}
	}

	// Pesquisa o manager, se nao encontrar pesquisa pelo coach ou head coach
	public void pesquisarTecnico() {
		if (html != null) {
			Pattern pattern = Pattern.compile("oach</th><td class=\"agent\">(.*?)</td></tr><tr>",
					Pattern.DOTALL);
			Pattern pattern2 = Pattern.compile("anager</th><td class=\"agent\">(.*?)</td></tr><tr>",
					Pattern.DOTALL);

			Matcher matcher = pattern.matcher(html);
			Matcher matcher2 = pattern2.matcher(html);

			if (matcher2.find()) {
				tecnico = matcher2.group(1).replaceAll("\\<[^>]*>", "");
			} else if (matcher.find()) {
				tecnico = matcher.group(1).replaceAll("\\<[^>]*>", "");
			}
		}
	}

	public void pesquisarLiga() {
		if (html != null) {
			Pattern pattern = Pattern.compile("League</th><td>.*?\">(.*?)</a></td></tr>", Pattern.DOTALL);
			Matcher matcher = pattern.matcher(html);

			if (matcher.find()) {
				liga = matcher.group(1);
			}
		}
	}

	// Abre o arquivo e pesquisa os atributos
	public void pesquisarAtributos(String path) {
		try {
			pegarHtml(path);
		} catch (Exception e) {
			System.out.println(e);
		}

		pesquisarNome();
		pesquisarApelido();
		pesquisarFundacao();
		pesquisarEstadio();
		pesquisarCapacidade();
		pesquisarTecnico();
		pesquisarLiga();
	}

	// Imprime na tela os atributos separados por ##
	public void imprimir() {
		MyIO.print(nome + " ## ");
		MyIO.print(apelido + " ## ");

		if (fundacaoDia < 10)
			MyIO.print("0" + fundacaoDia + "/");
		else
			MyIO.print(fundacaoDia + "/");

		if (fundacaoMes < 10)
			MyIO.print("0" + fundacaoMes + "/");
		else
			MyIO.print(fundacaoMes + "/");

		MyIO.print(fundacaoAno + " ## ");

		MyIO.print(estadio + " ## ");
		MyIO.print(capacidade + " ## ");
		MyIO.print(tecnico + " ## ");
		MyIO.print(liga + " ## ");
		MyIO.print(nomeArquivo + " ## ");
		MyIO.println(paginaTam + " ##");
	}
}

class Quicksort {

	static ListaTime lista = null;

	public static void main(String[] args) {

		String[] entrada = new String[1000];
		String[] pesquisa = new String[1000];
		int numEntrada = 0;

		// Leitura da entrada padrao
		do {
			entrada[numEntrada] = MyIO.readLine();
		} while (!entrada[numEntrada++].equals("FIM"));
		numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

		lista = new ListaTime();

		for (int i = 0; i < numEntrada; i++) {
			Time time = new Time();
			try {
				time.pesquisarAtributos(entrada[i]);
				lista.inserirFim(time);
			} catch (Exception e) {
				// MyIO.println("" + e);
			}
		}

		long comeco = new Date().getTime();
		lista.quicksort();
		long fim = new Date().getTime();

		Arq.openWriteClose("66978_quicksort.txt", "UTF-8",
				"66978\t" + (fim - comeco) / 1000.0 + "\t" + lista.mov);

		lista.mostrar();
	}
}
