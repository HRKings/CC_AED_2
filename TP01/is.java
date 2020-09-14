class is {

	public static boolean isAllVogal (String s){
		int counter = 0;

		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u' || s.charAt(i) == 'A' || s.charAt(i) == 'E' || s.charAt(i) == 'I' || s.charAt(i) == 'O' || s.charAt(i) == 'U')
				counter++;
		}

		return (counter == s.length());
	}

	public static boolean isAllConsoante (String s){
		boolean result = true;

		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u' || s.charAt(i) == 'A' || s.charAt(i) == 'E' || s.charAt(i) == 'I' || s.charAt(i) == 'O' || s.charAt(i) == 'U')
				result = false;

			if(s.charAt(i) == '0' || s.charAt(i) == '1' || s.charAt(i) == '2' || s.charAt(i) == '3' || s.charAt(i) == '4' || s.charAt(i) == '5' || s.charAt(i) == '6' || s.charAt(i) == '7' || s.charAt(i) == '8' || s.charAt(i) == '9')
				result = false;
		}

		return result;
	}

	public static boolean isInt (String s){
		boolean result = true;

		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) >= 'a' && s.charAt(i) <= 'z' || s.charAt(i) >= 'A' && s.charAt(i) <= 'Z' || s.charAt(i) == '.' || s.charAt(i) == ',')
				result = false;
		}

		return result;
	}

	public static boolean isDouble (String s){
		boolean result = true;
		int counter = 0;

		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) >= 'a' && s.charAt(i) <= 'z' || s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
				result = false;

			if(s.charAt(i) == '.' || s.charAt(i) == ',')
				counter++;
		}

		if(counter != 1 && counter != 0)
			result = false;

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
	      if(isAllVogal(entrada[i])){
		MyIO.print("SIM ");
	      }else{
		MyIO.print("NAO ");  
	      }

	      if(isAllConsoante(entrada[i])){
		MyIO.print("SIM ");
	      }else{
		MyIO.print("NAO ");  
	      }

	      if(isInt(entrada[i])){
		MyIO.print("SIM ");
	      }else{
		MyIO.print("NAO ");  
	      }

	      if(isDouble(entrada[i])){
		MyIO.println("SIM");
	      }else{
		MyIO.println("NAO");  
	      }
	   }
	}
     }
     