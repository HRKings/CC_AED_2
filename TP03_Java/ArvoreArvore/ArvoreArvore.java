import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

class No {
    public Time elemento;
    public No esq, dir;

    No(Time tim) {
        this.elemento = tim;
        this.esq = this.dir = null;
    }
}

class NoInt {
    public int elemento;
    public NoInt esq, dir;
    public ArvoreTime arvoreTime;

    NoInt(int i) {
        this.elemento = i;
        this.arvoreTime = new ArvoreTime();
        this.esq = this.dir = null;
    }
}

class ArvoreInt {
    private NoInt raiz;

    public ArvoreInt() {
        raiz = null;
    }

    public void mostrarPre() {
        mostrarPre(raiz);
    }

    private void mostrarPre(NoInt i) {
        if (i != null) {
            MyIO.println(i.elemento);
            i.arvoreTime.mostrarPre();
            mostrarPre(i.esq); // Elementos da esquerda.
            mostrarPre(i.dir); // Elementos da direita.
        }
    }

    public boolean pesquisar(String x) {
        MyIO.print(x);
        MyIO.print(" raiz ");
        return pesquisar(x, raiz);
    }

    private boolean pesquisar(String x, NoInt i) {
        boolean resp;

        if (i == null) {
            resp = false;

        } else {
            resp = i.arvoreTime.pesquisar(x);

            if (!resp) {
                MyIO.print("ESQ DIR ");
                resp = pesquisar(x, i.esq) || pesquisar(x, i.dir);
            }
        }

        return resp;
    }

    public void construir(int x) throws Exception {
        raiz = construir(x, raiz);
    }

    private NoInt construir(int x, NoInt i) throws Exception {
        if (i == null) {
            i = new NoInt(x);

        } else if (x < i.elemento) {
            i.esq = construir(x, i.esq);

        } else if (x > i.elemento) {
            i.dir = construir(x, i.dir);

        } else {
            throw new Exception("Erro ao inserir!");
        }

        return i;
    }

    public void inserir(Time x) throws Exception {
        inserir(x, raiz);
    }

    private void inserir(Time x, NoInt i) throws Exception {

        if (x.getFundacaoAno() % 15 == i.elemento) {
            i.arvoreTime.inserir(x);

        } else if (x.getFundacaoAno() % 15 < i.elemento) {
            inserir(x, i.esq);

        } else if (x.getFundacaoAno() % 15 > i.elemento) {
            inserir(x, i.dir);

        } else {
            throw new Exception("Erro ao inserir!");
        }
    }
}

class ArvoreTime {
    private No raiz;

    public ArvoreTime() {
        raiz = null;
    }

    public boolean pesquisar(String nomeTime) {
        return pesquisar(nomeTime, raiz);
    }

    private boolean pesquisar(String nomeTime, No i) {
        boolean resp;

        if (i == null) {
            resp = false;

        } else if (nomeTime.equals(i.elemento.getNome())) {
            resp = true;

        } else if (nomeTime.compareTo(i.elemento.getNome()) < 0) {
            MyIO.print("esq ");
            resp = pesquisar(nomeTime, i.esq);

        } else {
            MyIO.print("dir ");
            resp = pesquisar(nomeTime, i.dir);
        }

        return resp;
    }

    public void mostrarPre() {
        mostrarPre(raiz);
    }

    private void mostrarPre(No i) {
        if (i != null) {
            i.elemento.imprimir();
            mostrarPre(i.esq); // Elementos da esquerda.
            mostrarPre(i.dir); // Elementos da direita.
        }
    }

    public void inserir(Time x) throws Exception {
        raiz = inserir(x, raiz);
    }

    private No inserir(Time x, No i) throws Exception {
        if (i == null) {
            i = new No(x);

        } else if (x.getNome().compareTo(i.elemento.getNome()) < 0) {
            i.esq = inserir(x, i.esq);

        } else if (x.getNome().compareTo(i.elemento.getNome()) > 0) {
            i.dir = inserir(x, i.dir);

        } else {
            throw new Exception("Erro ao inserir!");
        }

        return i;
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
                    Matcher matcher2 = pattern2
                            .matcher(matcher.group(1).replace("&#160;", " ").replaceAll("\\<[^>]*>", ""));

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
                try {
                    capacidade = Integer.parseInt(matcher.group(1).replace(",", "").replace(".", "").replace(" ", "")
                            .replaceAll("\\(.*\\)", "").replaceAll("\\<[^>]*>", ""));
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        }
    }

    // Pesquisa o manager, se nao encontrar pesquisa pelo coach ou head coach
    public void pesquisarTecnico() {
        if (html != null) {
            Pattern pattern = Pattern.compile("oach</th><td class=\"agent\">(.*?)</td></tr><tr>", Pattern.DOTALL);
            Pattern pattern2 = Pattern.compile("anager</th><td class=\"agent\">(.*?)</td></tr><tr>", Pattern.DOTALL);

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

class ArvoreArvore {

    public static void main(String[] args) {
        MyIO.setCharset("UTF-8");
        String[] entrada = new String[1000];
        String[] pesquisas = new String[1000];
        int numEntrada = 0;
        int numPesquisa = 0;

        ArvoreInt arvo = null;

        // Leitura da entrada padrao
        do {
            entrada[numEntrada] = MyIO.readLine();
        } while (!entrada[numEntrada++].equals("FIM"));
        numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

        // Leitura da entrada padrao
        do {
            pesquisas[numPesquisa] = MyIO.readLine();
        } while (!pesquisas[numPesquisa++].equals("FIM"));
        numPesquisa--; // Desconsiderar ultima linha contendo a palavra FIM

        arvo = new ArvoreInt();

        try {
            int arr[] = { 7, 3, 11, 1, 5, 9, 12, 0, 2, 4, 6, 8, 10, 13, 14 };
            for (int i = 0; i < arr.length; i++) {
                arvo.construir(arr[i]);
            }
        } catch (Exception e) {
            MyIO.println("" + e);
        }

        for (int i = 0; i < numEntrada; i++) {
            Time time = new Time();
            try {
                time.pesquisarAtributos(entrada[i]);
                arvo.inserir(time);
            } catch (Exception e) {
                MyIO.println("" + e);
            }

        }

        //arvo.mostrarPre();

        for (int i = 0; i < numPesquisa; i++) {
            MyIO.print((arvo.pesquisar(pesquisas[i]) ? "SIM" : "NÃO"));
            MyIO.println("");
        }
    }
}
