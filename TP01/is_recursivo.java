class is_recursivo {

	public static int isAllVogal (String s, int i){
		int counter = 0;

		if(i < s.length()){
			if(s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u' || s.charAt(i) == 'A' || s.charAt(i) == 'E' || s.charAt(i) == 'I' || s.charAt(i) == 'O' || s.charAt(i) == 'U')
				counter = 1;
			counter += isAllVogal(s, i + 1);
		}

		return counter;
	}

	public static boolean isAllConsoante (String s, int i){
		boolean result = true;

		if(i < s.length()){
			if(s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u' || s.charAt(i) == 'A' || s.charAt(i) == 'E' || s.charAt(i) == 'I' || s.charAt(i) == 'O' || s.charAt(i) == 'U')
				result = false;

			if(s.charAt(i) == '0' || s.charAt(i) == '1' || s.charAt(i) == '2' || s.charAt(i) == '3' || s.charAt(i) == '4' || s.charAt(i) == '5' || s.charAt(i) == '6' || s.charAt(i) == '7' || s.charAt(i) == '8' || s.charAt(i) == '9')
				result = false;

			result = result && isAllConsoante(s, i+ 1);
		}

		return result;
	}

	public static boolean isInt (String s, int i){
		boolean result = true;

		if(i < s.length()){
			if(s.charAt(i) >= 'a' && s.charAt(i) <= 'z' || s.charAt(i) >= 'A' && s.charAt(i) <= 'Z' || s.charAt(i) == '.' || s.charAt(i) == ',')
				result = false;
			
			result = result && isInt(s, i + 1);
		}

		return result;
	}

	public static boolean isDouble (String s, int i){
		boolean result = true;

		if(i < s.length()){
			if(s.charAt(i) >= 'a' && s.charAt(i) <= 'z' || s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
				result = false;

			result = result && isDouble(s, i + 1);
		}

		return result;
	}

	public static int isDouble2 (String s, int i){
		int counter = 0;

		if(i < s.length()){
			if(s.charAt(i) == '.' || s.charAt(i) == ',')
				counter++;

			counter += isDouble2(s, i + 1);
		}

		return counter;
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

	public static void is(String s){

		boolean resp = true;

		if(isDouble2(s, 0) != 1 && isDouble2(s, 0) != 0)
			resp = false;

		if(isAllVogal(s, 0) == s.length()){
			MyIO.print("SIM ");
			  }else{
			MyIO.print("NAO ");  
			  }
	
			  if(isAllConsoante(s, 0)){
			MyIO.print("SIM ");
			  }else{
			MyIO.print("NAO ");  
			  }
	
			  if(isInt(s, 0)){
			MyIO.print("SIM ");
			  }else{
			MyIO.print("NAO ");  
			  }
	
			  if(isDouble(s, 0) && resp){
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
	      is(entrada[i]);
	   }
	}
     }
     