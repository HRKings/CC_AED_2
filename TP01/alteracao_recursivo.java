import java.util.Random;

class alteracao_recursivo {
   
	public static Random gerador;

	//Funcao que compara iterativamente, char por char, duas string
	public static boolean equals(String a, String b){
		boolean result = true;

		if(a.length() != b.length()){
			result = false;
		}else{
			for(int i = 0; i < a.length(); i++){
				if(a.charAt(i) != b.charAt(i)){
					result = false;
					break;
				}
			}
		}

		return result;
	}

	//Funcao que gera a alteracao aleatoria
	public static String criarAltercao(String s, char oldChar, char newChar, int index){
		String resp = "";

		//Verifica cada char e troca ele por sua versao aleatoria
		if(index < s.length()){
			if(s.charAt(index) == oldChar){
				resp += newChar;
			}else{
				resp += s.charAt(index);
			}

			resp += criarAltercao(s, oldChar, newChar, index + 1);
		}

		return resp;
	}

	public static void gerarAlteracao(String s){
		//Cria duas chars aleatorias, de a ate z
		char oldChar = ((char)('a'+ (Math.abs(gerador.nextInt()) % 26)));
		char newChar = ((char)('a'+ (Math.abs(gerador.nextInt()) % 26)));

		String result = "";

		result = criarAltercao(s, oldChar, newChar, 0);

		MyIO.println(result);

	}

	public static void main (String[] args){
	   gerador = new Random();
	   gerador.setSeed(4);

	   String[] entrada = new String[1000];
	   int numEntrada = 0;

	   //Leitura da entrada padrao
	   do {
	      entrada[numEntrada] = MyIO.readLine();
	   } while (equals(entrada[numEntrada++], "FIM") == false);
	   numEntrada--;   //Desconsiderar ultima linha contendo a palavra FIM
     
	   //Para cada linha de entrada, gerando a alteracao
	   for(int i = 0; i < numEntrada; i++){
		gerarAlteracao(entrada[i]);
	   }
	}
     }
     