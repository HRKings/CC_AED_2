import java.util.Random;

class alteracao {
   
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
	public static void gerarAlteracao(String s){
		//Cria duas chars aleatorias, de a ate z
		char oldChar = ((char)('a'+ (Math.abs(gerador.nextInt()) % 26)));
		char newChar = ((char)('a'+ (Math.abs(gerador.nextInt()) % 26)));

		char[] chars = new char[s.length()];

		//Verifica cada char e troca ele por sua versao aleatoria
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == oldChar){
				chars[i] = newChar;
			}else{
				chars[i] = s.charAt(i);
			}
		}

		String result = new String(chars);

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
     