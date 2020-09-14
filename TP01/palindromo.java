class palindromo {
   
	//Funcao que troca iterativamente os caracteres de posicao
	public static String reverseString (String s){
		char[] reverse = new char[s.length()];

		for(int i = 0; i < s.length(); i++){
			reverse[i] = s.charAt(s.length() - 1 - i);
		}

	    String result = new String(reverse);

		return result;
	}
     
	//Funcao que remove espacos, iterando por seus caracteres e copiando o que nao for um espaco para uma nova string
	public static String removeSpaces(String s){
		char[] chars = new char[s.length()];

		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i) != ' '){
				chars[i] = s.charAt(i);
			}
		}

		String result = new String(chars);

		return result;
	}

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
     
	//Funcao que verifica um palindromo, utilizando das funcoes anteriores
	public static void isPalindromo(String s){
		String buffer = removeSpaces(s);

		String buff = reverseString(buffer);

		if(equals(buffer, buff)){
			MyIO.println("SIM");
		}else{
			MyIO.println("NAO");
		}
	}

	public static void main (String[] args){
	   String[] entrada = new String[1000];
	   int numEntrada = 0;

	   //Leitura da entrada padrao
	   do {
	      entrada[numEntrada] = MyIO.readLine();
	   } while (equals(entrada[numEntrada++], "FIM") == false);
	   numEntrada--;   //Desconsiderar ultima linha contendo a palavra FIM
     
	   //Para cada linha de entrada, verificando palindromos
	   for(int i = 0; i < numEntrada; i++){
	      isPalindromo(entrada[i]);
	   }
	}
     }
     