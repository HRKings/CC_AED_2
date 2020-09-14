class ciframento_recursivo {


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

	//Funcao que gera a cifra de cesar
	public static String criarCiframento(String s, int index){
		String resp = "";

		//Para cada char da string, seu valor e somado a 3 e copiado para uma nova string
		if(index < s.length()){
			resp += (char) (s.charAt(index) + 3);
			resp += criarCiframento(s, index + 1);
		}
		
		return resp;
	}

	public static void gerarCiframento(String s){
		String result = "";

		result = criarCiframento(s, 0);


		MyIO.println(result);
	}

	public static void main (String[] args){
	   String[] entrada = new String[1000];
	   int numEntrada = 0;

	   //Leitura da entrada padrao
	   do {
	      entrada[numEntrada] = MyIO.readLine();
	   } while (equals(entrada[numEntrada++], "FIM") == false);
	   numEntrada--;   //Desconsiderar ultima linha contendo a palavra FIM
     
	   //Para cada linha de entrada, gerando a cifra
	   for(int i = 0; i < numEntrada; i++){
		   gerarCiframento(entrada[i]);
	   }
	}
     }
     