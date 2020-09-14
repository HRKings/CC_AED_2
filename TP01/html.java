import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

class html {

	/*
	 * public static String lerHTML(String address) throws Exception { String line =
	 * ""; String resp = "";
	 * 
	 * try { URL url = new URL(address); BufferedReader br = new BufferedReader(new
	 * InputStreamReader(url.openStream())); line = br.readLine(); while (line !=
	 * null) { resp += line; line = br.readLine(); } br.close(); } catch
	 * (MalformedURLException excecao) { excecao.printStackTrace(); } catch
	 * (IOException excecao) { excecao.printStackTrace(); }
	 * 
	 * return resp; }
	 */

	public static String lerHTML(String address) {
		String line = "";
		String resp = "";

		if (address != null && address.length() != 0) {
			try {
				URL url = new URL(address);
				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
				line = br.readLine();
				while (line != null) {
					resp += line;
					line = br.readLine();
				}
				br.close();
			} catch (MalformedURLException excecao) {
				excecao.printStackTrace();
			} catch (IOException excecao) {
				excecao.printStackTrace();
			}
		}

		return resp;
	}

	public static void retornarSolucao(String nome, String url) {
		// a e i o u á é í ó ú à è ì ò ù ã õ â ê î ô û

		int aCount = 0;
		int eCount = 0;
		int iCount = 0;
		int oCount = 0;
		int uCount = 0;

		int a2Count = 0;
		int e2Count = 0;
		int i2Count = 0;
		int o2Count = 0;
		int u2Count = 0;

		int a3Count = 0;
		int e3Count = 0;
		int i3Count = 0;
		int o3Count = 0;
		int u3Count = 0;

		int a4Count = 0;
		int o4Count = 0;

		int a5Count = 0;
		int e5Count = 0;
		int i5Count = 0;
		int o5Count = 0;
		int u5Count = 0;

		int consonantCount = 0;
		int brCount = 0;
		int tableCount = 0;

		String html = lerHTML(url);

		for (int i = 0; i < html.length(); i++) {

			if (html.charAt(i) == 'a') {
				aCount++;
			} else if (html.charAt(i) == 'e') {
				eCount++;
			} else if (html.charAt(i) == 'i') {
				iCount++;
			} else if (html.charAt(i) == 'o') {
				oCount++;
			} else if (html.charAt(i) == 'u') {
				uCount++;
			} else if (html.charAt(i) == 'á') { // á é í ó ú
				a2Count++;
			} else if (html.charAt(i) == 'é') {
				e2Count++;
			} else if (html.charAt(i) == 'í') {
				i2Count++;
			} else if (html.charAt(i) == 'ó') {
				o2Count++;
			} else if (html.charAt(i) == 'ú') {
				u2Count++;
			} else if (html.charAt(i) == 'à') { // ? ? ? ? ?
				a3Count++;
			} else if (html.charAt(i) == 'è') {
				e3Count++;
			} else if (html.charAt(i) == 'ì') {
				i3Count++;
			} else if (html.charAt(i) == 'ò') {
				o3Count++;
			} else if (html.charAt(i) == 'ù') {
				u3Count++;
			} else if (html.charAt(i) == 'ã') { // ? ? 
				a4Count++;
			} else if (html.charAt(i) == 'õ') {
				o4Count++;
			} else if (html.charAt(i) == 'â') { // â ? î ô ?
				a5Count++;
			} else if (html.charAt(i) == 'ê') {
				e5Count++;
			} else if (html.charAt(i) == 'î') {
				i5Count++;
			} else if (html.charAt(i) == 'ô') {
				o5Count++;
			} else if (html.charAt(i) == 'û') {
				u5Count++;
			} else if((html.charAt(i) >= 'b' && html.charAt(i) <= 'z')) {
				consonantCount++;
			}

			if (html.charAt(i) == '<' && html.charAt(i + 1) == 'b' && html.charAt(i + 2) == 'r'
					&& html.charAt(i + 3) == '>') {
				brCount++;
				 i += 3;
			} else if (html.charAt(i) == '<' && html.charAt(i + 1) == 't' && html.charAt(i + 2) == 'a'
					&& html.charAt(i + 3) == 'b' && html.charAt(i + 4) == 'l'
					&& html.charAt(i + 5) == 'e' && html.charAt(i + 6) == '>') {
				tableCount++;
				 i += 6;
			}
		}

		// a(9360) e(11051) i(9618) o(5379) u(1560) á(112) é(146) í(49) ó(93) ú(9) à(6)
		// è(0) ì(0) ò(0) ù(0) ã(248) õ(31) â(7) ê(38) î(0) ô(6) û(0) consoante(59353)
		// <br>(0) <table>(0) Black Mirror

		MyIO.setCharset("UTF-8");

		MyIO.print("a(" + aCount + ") ");
		MyIO.print("e(" + eCount + ") ");
		MyIO.print("i(" + iCount + ") ");
		MyIO.print("o(" + oCount + ") ");
		MyIO.print("u(" + uCount + ") ");


		MyIO.print("á(" + a2Count + ") ");
		MyIO.print("é(" + e2Count + ") ");
		MyIO.print("í(" + i2Count + ") ");
		MyIO.print("ó(" + o2Count + ") ");
		MyIO.print("ú(" + u2Count + ") ");

		MyIO.print("à(" + a3Count + ") ");
		MyIO.print("è(" + e3Count + ") ");
		MyIO.print("ì(" + i3Count + ") ");
		MyIO.print("ò(" + o3Count + ") ");
		MyIO.print("ù(" + u3Count + ") ");

		MyIO.print("ã(" + a4Count + ") ");
		MyIO.print("õ(" + o4Count + ") ");

		MyIO.print("â(" + a5Count + ") ");
		MyIO.print("ê(" + e5Count + ") ");
		MyIO.print("î(" + i5Count + ") ");
		MyIO.print("ô(" + o5Count + ") ");
		MyIO.print("û(" + u5Count + ") ");

		MyIO.print("consoante(" + consonantCount + ") ");
		MyIO.print("<br>(" + brCount + ") ");
		MyIO.print("<table>(" + tableCount + ") ");

		MyIO.println(nome);

	}

	// Funcao que compara iterativamente, char por char, duas string
	public static boolean equals(String a, String b) {
		boolean result = true;

		if ((a != null || b != null) && (a.length() > 0 && b.length() > 0)) {
			if (a.length() != b.length()) {
				result = false;
			} else {
				for (int i = 0; i < a.length(); i++) {
					if (a.charAt(i) != b.charAt(i)) {
						result = false;
						break;
					}
				}
			}
		}

		return result;
	}

	public static boolean isFim(String s) {
		if (s == null) {
			return false;
		}

		if (s.length() == 0) {
			return false;
		}

		return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
	}

	public static void main(String[] args) {
		String[] entrada = new String[1000];
		String[] entradaUrl = new String[1000];
		int numEntrada = 0;

		// Leitura da entrada padrao
		do {
			entrada[numEntrada] = MyIO.readLine();
			if(isFim(entrada[numEntrada]) == false){
				entradaUrl[numEntrada] = MyIO.readLine();
			}
			
		} while (isFim(entrada[numEntrada++]) == false);
		numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

		// Para cada linha de entrada, verificando palindromos
		for (int i = 0; i < numEntrada; i++) {
			retornarSolucao(entrada[i], entradaUrl[i]);
		}
	}
}
