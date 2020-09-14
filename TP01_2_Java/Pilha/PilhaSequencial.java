import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

class PilhaTime {
	private Time[] array;
	private int n;
     
	public PilhaTime () {
	   this(6);
	}
     
	/**
	 * Construtor da classe.
	 * @param tamanho Tamanho da lista.
	 */
	public PilhaTime (int tamanho){
	   array = new Time[tamanho];
	   n = 0;
	}
     

	/**
	 * Insere um elemento na pilha.
	 * @param x int elemento a ser inserido.
	 * @throws Exception Se a pilha estiver cheia.
	 */
	public void empilhar(Time x) throws Exception {
     
	   //validar insercao
	   if(n >= array.length){
	      throw new Exception("Erro ao empilhar!");
	   }
     
	   array[n] = x;
	   n++;
	}
     
	/**
	 * Remove um elemento da pilha.
	 * @return resp int elemento a ser removido.
	 * @throws Exception Se a pilha estiver vazia.
	 */
	public Time desempilhar() throws Exception {
     
	   //validar remocao
	   if (n == 0) {
	      throw new Exception("Erro ao desempilhar!");
	   }
     
	   return array[--n];
	}

	/**
	 * Mostra os elementos da lista separados por espacos.
	 */
	public void mostrar (){
	   for(int i = 0; i < n; i++){
	      System.out.print("[" + i +"] ");
	      array[i].imprimir();
	   }
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

    public void pegarHtml(String path) throws Exception {
        nomeArquivo = path;
        Arq.openRead(path, "UTF-8");

        while(!html.contains("vcard")){
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

            Pattern pattern = Pattern.compile("Nickname.*?</th><td class=\"nickname\">(.*?)</td></tr>", Pattern.DOTALL);
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
                    fundacaoAno = Integer.parseInt(matcher.group(1).replace("&#160;", " ").replaceAll("\\<[^>]*>", ""));
                } else {

                    Pattern pattern2 = Pattern.compile("(.*)\\s(.*)\\s(.*)$", Pattern.DOTALL);
                    Matcher matcher2 = pattern2.matcher(matcher.group(1).replace("&#160;", " ").replaceAll("\\<[^>]*>", ""));

                    if (matcher2.find()) {
                        if(matcher2.group(1).length()  <= 2)
                            fundacaoDia = Integer.parseInt(matcher2.group(1));

                            if(matcher2.group(1).length() > 2){
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
                            }else{
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
                capacidade = Integer.parseInt(matcher.group(1).replace(",", "").replace(".", "").replace(" ", "")
                        .replaceAll("\\(.*\\)", "").replaceAll("\\<[^>]*>", ""));
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

class PilhaSequencial {

	static PilhaTime pilha = null;

	public static void operacoesLista(String acao) {
		Time time = new Time();
		String[] oper = acao.split(" ");

		try {
			if (oper[0].equals("I")) {
				time.pesquisarAtributos(oper[1]);
				pilha.empilhar(time);
			} else if (oper[0].equals("R")) {
				Time tmp = pilha.desempilhar();
				System.out.println("(R) " + tmp.getNome());
			}
		} catch (Exception e) {
			MyIO.println("" + e);
		}
	}

	public static void main (String[] args){
	        MyIO.setCharset("UTF-8");
		String[] entrada = new String[1000];
		String[] operacoes = null;
		int numEntrada = 0;
     
		//Leitura da entrada padrao
		do {
		   entrada[numEntrada] = MyIO.readLine();
		} while (!entrada[numEntrada++].equals("FIM"));
		numEntrada--;   //Desconsiderar ultima linha contendo a palavra FIM
	  
		int numOperacoes = Integer.parseInt(MyIO.readLine());
		operacoes = new String[numOperacoes];

		for (int i = 0; i < numOperacoes; i++) {
			operacoes[i] = MyIO.readLine();
		}

		pilha = new PilhaTime(numEntrada + numOperacoes);

		for (int i = 0; i < numEntrada; i++) {
			Time time = new Time();
			try {
				time.pesquisarAtributos(entrada[i]);
				pilha.empilhar(time);
			} catch (Exception e) {
				MyIO.println("" + e);
			}
			
		}

		for (int i = 0; i < numOperacoes; i++) {
			operacoesLista(operacoes[i]);
		}

		pilha.mostrar();
	}
}
