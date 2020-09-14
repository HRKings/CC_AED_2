import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

class Time {

	private String nome, apelido, estadio, tecnico, liga, nomeArquivo;
	private int capacidade, fundacaoDia, fundacaoMes, fundacaoAno;
	private long paginaTam;

	public Time() {
		nome = apelido = estadio = tecnico = liga = nomeArquivo = "";
		paginaTam = capacidade = fundacaoDia = fundacaoMes = fundacaoAno = 0;
	}

	public void setNome(String novo){
		this.nome = novo;
	}

	public String getNome(){
		return this.nome;
	}


	public void setApelido(String novo){
		this.apelido = novo;
	}

	public String getApelido(){
		return this.apelido;
	}


	public void setEstadio(String novo){
		this.estadio = novo;
	}

	public String getEstadio(){
		return this.estadio;
	}


	public void setTecnico(String novo){
		this.tecnico = novo;
	}

	public String getTecnico(){
		return this.tecnico;
	}


	public void setLiga(String novo){
		this.liga = novo;
	}

	public String getLiga(){
		return this.liga;
	}


	public void setNomeArquivo(String novo){
		this.nome = nomeArquivo;
	}

	public String getNomeArquivo(){
		return this.nomeArquivo;
	}




	public void setCapacidade(int novo){
		this.capacidade = novo;
	}

	public int getCapacidade(){
		return this.capacidade;
	}


	public void setFundacaoDia(int novo){
		this.fundacaoDia = novo;
	}

	public int getFundacaoDia(){
		return this.fundacaoDia;
	}


	public void setFundacaoMes(int novo){
		this.fundacaoMes = novo;
	}

	public int getFundacaoMes(){
		return this.fundacaoMes;
	}


	public void setFundacaoAno(int novo){
		this.fundacaoAno = novo;
	}


	public int getFundacaoAno(){
		return this.fundacaoAno;
	}


	public void setPaginaTam(long novo){
		this.paginaTam = novo;
	}

	public long getPaginaTam(){
		return this.paginaTam;
	}

	// Fim get/sets

	public void imprimir() {
		System.out.println(toString());
	}

	public String toString() {
		String resp = nome + " ## " + apelido + " ## ";
		resp += ((fundacaoDia > 9) ? "" : "0") + fundacaoDia + ((fundacaoMes > 9) ? "/" : "/0") + fundacaoMes + "/"
				+ fundacaoAno + " ## ";
		resp += estadio + " ## " + capacidade + " ## " + tecnico + " ## " + liga + " ## " + nomeArquivo + " ## "
				+ paginaTam + " ## ";
		return resp;
	}

	public Time Clone(){
		Time result = new Time();

		result.setNome(this.nome);
		result.setApelido(this.apelido);
		result.setEstadio(this.estadio);
		result.setTecnico(this.tecnico);
		result.setLiga(this.liga);
		result.setNomeArquivo(this.nomeArquivo);

		result.setFundacaoDia(this.fundacaoDia);
		result.setFundacaoMes(this.fundacaoMes);
		result.setFundacaoAno(this.fundacaoAno);

		result.setPaginaTam(this.paginaTam);

		return result;
	}

	public String removePunctuation(String campo) {
		campo = campo.replace(".", "");
		campo = campo.replace(",", "");
		campo = campo.replace(";", "");
		String resp = "";
		for (int i = 0; i < campo.length(); i++) {
			if (Character.isDigit(campo.charAt(i)))
				resp += campo.charAt(i);
			else
				i = campo.length();
		}

		return resp;
	}

	public int getMes(String campo) {
		int resp = 0;
		if (campo.contains("january") == true) {
			resp = 1;
		} else if (campo.contains("february") == true) {
			resp = 2;
		} else if (campo.contains("march") == true) {
			resp = 3;
		} else if (campo.contains("april") == true) {
			resp = 4;
		} else if (campo.contains("may") == true) {
			resp = 5;
		} else if (campo.contains("june") == true) {
			resp = 6;
		} else if (campo.contains("july") == true) {
			resp = 7;
		} else if (campo.contains("august") == true) {
			resp = 8;
		} else if (campo.contains("september") == true) {
			resp = 9;
		} else if (campo.contains("october") == true) {
			resp = 10;
		} else if (campo.contains("november") == true) {
			resp = 11;
		} else if (campo.contains("december") == true) {
			resp = 12;
		}
		return resp;
	}

	public String getSubstringEntre(String s, String antes, String depois) {
		String resp = "";
		int posInicio, posFim;

		posInicio = s.indexOf(antes) + antes.length();

		if (antes.compareTo(depois) != 0) {
			posFim = s.indexOf(depois);
		} else {
			posFim = s.indexOf(depois, posInicio);
		}

		if (0 <= posInicio && posInicio < posFim && posFim < s.length()) {
			resp = s.substring(posInicio, posFim);
		}

		return resp;
	}

	public String removeTags(String s) {
		return s.replaceAll("<.*>", "").replaceAll("\\s{2,}", " ");
	}

	public void ler(String nomeArquivo) throws Exception {

		FileReader file = new FileReader(nomeArquivo);
		BufferedReader buffer = new BufferedReader(file);
		String html = "";
		String line = buffer.readLine();
		while (line != null) {
			html += line;
			line = buffer.readLine();
		}

		buffer.close();
		file.close();

		html = html.substring(html.indexOf("Full name"));
		html = html.substring(0, html.indexOf("<table style"));
		String campos[] = html.split("<tr>");

		this.nomeArquivo = nomeArquivo;

		for (String campo : campos) {
			// Full name
			if (removeTags(campo).contains("Full name")) {
				campo = removeTags(campo);
				this.nome = campo.substring(9).trim();

				// Nickname(s)
			} else if (removeTags(campo).contains("Nickname(s)")) {
				campo = removeTags(campo);
				this.apelido = campo.substring(11).trim();

				// Founded
			} else if (removeTags(campo).toLowerCase().contains("founded")) {
				campo = removeTags(campo.split("<br />")[0]);
				this.fundacaoMes = this.getMes(campo.toLowerCase());

				if (this.fundacaoMes == 0) {
					this.fundacaoDia = 0;
					campo = campo.substring(7);
					this.fundacaoAno = Integer.parseInt(campo.substring(0, 4));
				} else {
					campo = campo.substring(7);
					String data[] = campo.split(" ");
					if (data.length < 3) {
						this.fundacaoDia = 0;
						this.fundacaoAno = Integer.parseInt(data[1].substring(0, 4));
					} else {
						if (campo.contains(",")) {
							this.fundacaoDia = Integer.parseInt(data[1].replace("th", "").replace(",", ""));
							this.fundacaoAno = Integer.parseInt(data[2].substring(0, 4));
						} else if (Character.isDigit(data[0].charAt(0))) {
							this.fundacaoDia = Integer.parseInt(data[0]);
							this.fundacaoAno = Integer.parseInt(data[2].substring(0, 4));
						} else {
							this.fundacaoDia = 0;
							this.fundacaoAno = Integer.parseInt(data[1].substring(0, 4));
						}
					}
				}

				// Ground
			} else if (removeTags(campo).toLowerCase().contains("ground")) {
				campo = removeTags(campo);
				this.estadio = campo.substring(6).trim();

				// Capacity
			} else if (removeTags(campo).toLowerCase().contains("capacity")) {
				campo = campo.split("<br")[0];
				campo = removeTags(campo.split("</td>")[0].replace(" ", ""));
				this.capacidade = Integer.parseInt(removePunctuation(campo.substring(8).split(";")[0]));

				// Coach
			} else if (removeTags(campo).toLowerCase().contains("coach") || campo.toLowerCase().contains("manager")) {
				campo = removeTags(campo).replace("(es)", "").trim();
				if (campo.toLowerCase().contains("coach")) {
					int index = campo.toLowerCase().indexOf("coach");
					this.tecnico = campo.substring(index + 5).trim();
				} else if (campo.toLowerCase().contains("manager") && this.tecnico.isEmpty()) {
					int index = campo.toLowerCase().indexOf("manager");
					this.tecnico = campo.substring(index + 7).trim();
				}
				// League
			} else if (removeTags(campo).contains("League") && this.liga.isEmpty()) {
				campo = removeTags(campo);
				this.liga = campo.substring(6).trim();
			}
		}

		File f = new File(nomeArquivo);
		this.paginaTam = f.length();
	}
}