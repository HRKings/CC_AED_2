import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

class Time {

    String nome, apelido, estadio, tecnico, liga, nomeArquivo;
    int capacidade, fundacaoDia, fundacaoMes, fundacaoAno;
    long paginaTam;

    String html;

    public Time ( ){
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
        //MyIO.setCharset ( "ISO-8859-1" );
        MyIO.setCharset ( "UTF-8" );
    }

    /* public void pegarHtml ( String path ) throws Exception{
        //pegar e tentar mostrar o codigo html na tela, a partir da pasta tmp.
        BufferedReader reader = null;
        try {
            reader = new BufferedReader( new FileReader ( path ) );
        } catch ( Exception ioex ){ 
            System.out.println(ioex);
        }

        String buffer = reader.readLine();

        while ( buffer != null ){
            html = html + buffer;
            buffer = reader.readLine();
        }

        reader.close();
    } */

    public void pegarHtml ( String path ) throws Exception{
        nomeArquivo = path;
        Arq.openRead(path, "UTF-8");
        html = Arq.readAll();
        paginaTam = Arq.length();
    }

    public void pesquisarNome (){
        if ( html != null ){

            //Pattern pattern = Pattern.compile("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">(Nome completo).*?<td style=\"vertical-align: top; text-align: left;\">(.*?)</td></tr>", Pattern.DOTALL);
            Pattern pattern = Pattern.compile("Full name.*?</th><td>(.*?)<.*?", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(html);

            if(matcher.find()){
                   nome = matcher.group(1).replaceAll("\\<[^>]*>", "");
            }
        }
    }

    public void pesquisarApelido (){
        if ( html != null ){

            //Pattern pattern = Pattern.compile("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">(Nome completo).*?<td style=\"vertical-align: top; text-align: left;\">(.*?)</td></tr>", Pattern.DOTALL);
            Pattern pattern = Pattern.compile("Nickname.*?</th><td class=\"nickname\">(.*?)</td></tr>", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(html);

            if(matcher.find()){
                    apelido = matcher.group(1).replaceAll("\\<[^>]*>", "");
            }
        }
    }

    public void pesquisarFundacao (){
        if ( html != null ){

            //Pattern pattern = Pattern.compile("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">(Nome completo).*?<td style=\"vertical-align: top; text-align: left;\">(.*?)</td></tr>", Pattern.DOTALL);
            Pattern pattern = Pattern.compile("Founded.*?</th><td>(.*?)<span class=\"noprint\">.*<span class=\"bday dtstart published updated\">(.*?)</span>", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(html);

            if(matcher.find()){
                System.out.println(matcher.group(1).replace("&#160;", " "));
                if(matcher.group(1).replaceAll("\\<[^>]*>", "").length() == 4){
                    fundacaoAno = Integer.parseInt(matcher.group(1).replaceAll("\\<[^>]*>", ""));
                }else{
                    

                    Pattern pattern2 = Pattern.compile("(.*?)\\-(.*?)\\-(.*?)$", Pattern.DOTALL);
                Matcher matcher2 = pattern2.matcher(matcher.group(1).replaceAll("\\<[^>]*>", ""));
                
                Pattern pattern3 = Pattern.compile("(.*)\\s(.*)\\s(.*)$", Pattern.DOTALL);
                Matcher matcher3 = pattern3.matcher(matcher.group(1).replaceAll("\\<[^>]*>", ""));

                if(matcher2.find()){
                    fundacaoAno = Integer.parseInt(matcher2.group(1));
                    fundacaoMes = Integer.parseInt(matcher2.group(2));
                    fundacaoDia = Integer.parseInt(matcher2.group(3));
                }else if(matcher3.find()){
                    fundacaoDia = Integer.parseInt(matcher3.group(1));

                    switch(matcher3.group(2)){
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

                    fundacaoAno = Integer.parseInt(matcher3.group(3));
                }
            }*/
        }
        }
    }

    public void pesquisarEstadio (){
        if ( html != null ){

            //Pattern pattern = Pattern.compile("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">(Nome completo).*?<td style=\"vertical-align: top; text-align: left;\">(.*?)</td></tr>", Pattern.DOTALL);
            Pattern pattern = Pattern.compile("Ground.*?</th><td class=\"label\"><a.*?>(.*?)</td></tr>", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(html);

            if(matcher.find()){
                    estadio = matcher.group(1).replaceAll("\\<[^>]*>", "");
            }
        }
    }

    public void pesquisarCapacidade (){
        if ( html != null ){

            //Pattern pattern = Pattern.compile("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">(Nome completo).*?<td style=\"vertical-align: top; text-align: left;\">(.*?)</td></tr>", Pattern.DOTALL);
            Pattern pattern = Pattern.compile("Capacity.*?</th><td>(.*?)<.*?", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(html);

            if(matcher.find()){
                    capacidade = Integer.parseInt(matcher.group(1).replace(",", "").replace(" ", "").replaceAll("\\(.*\\)", "").replaceAll("\\<[^>]*>", ""));
            }
        }
    }

    public void pesquisarTecnico (){
        if ( html != null ){

            //Pattern pattern = Pattern.compile("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">(Nome completo).*?<td style=\"vertical-align: top; text-align: left;\">(.*?)</td></tr>", Pattern.DOTALL);
            Pattern pattern = Pattern.compile("oach.*?</th><td class=\"agent\">.*?>(.*?)</a></td></tr>", Pattern.DOTALL);
            Pattern pattern2 = Pattern.compile("anager.*?</th><td class=\"agent\">.*?>(.*?)</a></td></tr>", Pattern.DOTALL);

            Matcher matcher = pattern.matcher(html);
            Matcher matcher2 = pattern2.matcher(html);

            if(matcher.find()){
                   tecnico = matcher.group(1).replaceAll("\\<[^>]*>", "");
            }else if(matcher2.find()){
                tecnico = matcher2.group(1).replaceAll("\\<[^>]*>", "");
            }
        }
    }

    public void pesquisarLiga (){
        if ( html != null ){

            //Pattern pattern = Pattern.compile("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">(Nome completo).*?<td style=\"vertical-align: top; text-align: left;\">(.*?)</td></tr>", Pattern.DOTALL);
            Pattern pattern = Pattern.compile("League</th><td>.*?\">(.*?)</a></td></tr>", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(html);

            if(matcher.find()){
                    liga = matcher.group(1);
            }
        }
    }

    public void pesquisarAtributos(String path){
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

    public void imprimir ( ){
        MyIO.print ( nome + " ## ");
        MyIO.print ( apelido + " ## ");

        if(fundacaoDia < 10)
            MyIO.print ( "0" + fundacaoDia + "/");
        else
            MyIO.print ( fundacaoDia + "/");

        if(fundacaoMes < 10)
            MyIO.print ( "0" + fundacaoMes + "/");
        else
            MyIO.print ( fundacaoMes + "/");

        MyIO.print ( fundacaoAno + " ## ");

        MyIO.print ( estadio + " ## ");
        MyIO.print ( capacidade + " ## ");
        MyIO.print ( tecnico + " ## ");
        MyIO.print ( liga + " ## ");
        MyIO.print ( nomeArquivo + " ## ");
        MyIO.println ( paginaTam + " ##");
    }
}


class classe {

    public static void main(String[] args){
        Time tim = new Time();
        String[] entrada = new String[1000];
		int numEntrada = 0;

		// Leitura da entrada padrao
		do {
			entrada[numEntrada] = MyIO.readLine();
		} while (entrada[numEntrada++].equals("FIM") == false);
		numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

		// Para cada linha de entrada, verificando palindromos
		for (int i = 0; i < numEntrada; i++) {
            tim.pesquisarAtributos(entrada[i]);
            tim.imprimir();
		}
    }
}