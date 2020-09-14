import java.lang.Math;

class Utils
{

//a more simple "parseInt" method that not throw exceptions
int simple_parse_int(String exp)
{
	String buffer = "";
	for (int i = 0; i < exp.length(); i++) {
		if (exp.charAt(i) >= '0' && exp.charAt(i) <= '9') 
			buffer += exp.charAt(i);
	}
	int value = 0;
	try {
		value = Integer.parseInt(buffer);
	} catch (Exception e) {
		;
	}
	return value;
}

//converts the date expression in int array
int[] catch_date(String exp)
{
	int values[] = {1,1,1900};
	String exp_buffer = "";
	for (int i = 0; i < exp.length(); i++) {
		if ((i + 9) < exp.length() && exp.charAt(i) == '&' &&
		exp.charAt(i + 1) == '#' && exp.charAt(i + 2) == '5' && 
		exp.charAt(i + 3) == '9' && exp.charAt(i + 4) == ';' && 
		exp.charAt(i + 5) == '&' && exp.charAt(i + 6) == '#' && 
		exp.charAt(i + 7) == '3' && exp.charAt(i + 8) == '2' && 
		exp.charAt(i + 9) == ';')
			break;
		else if (exp.charAt(i) == '\n') break;
		exp_buffer += exp.charAt(i);
	}

	String exp_terms[] = exp_buffer.split(" ");
	
	if (exp_terms.length == 1)
		values[2] = simple_parse_int(exp_terms[0]);
	else if (exp_terms.length == 3) {
		values[0] = simple_parse_int(exp_terms[0]);
		switch (exp_terms[1]) {
		case "January":
			values[1] = 1;
			break;
		case "February":
			values[1] = 2;
			break;
		case "March":
			values[1] = 3;
			break;
		case "April":
			values[1] = 4;
			break;
		case "May":
			values[1] = 5;
			break;
		case "June":
			values[1] = 6;
			break;
		case "July":
			values[1] = 7;
			break;
		case "August":
			values[1] = 8;
			break;
		case "September":
			values[1] = 9;
			break;
		case "October":
			values[1] = 10;
			break;
		case "November":
			values[1] = 11;
			break;
		case "December":
			values[1] = 12;
			break;
		default:
			values[1] = simple_parse_int(exp_terms[1]);
			break;
		}
		values[2] = simple_parse_int(exp_terms[2]);
	}
	return values;
}

//catch a String and returns the integer value that String contains
int parse_int(String exp)
{
	int value = 0;
	int j = 0;
	int n = 0;
	for (; n < exp.length(); n++) {
		if (!((exp.charAt(n) >= '0' && exp.charAt(n) <= '9') || 
		exp.charAt(n) == ' ' || exp.charAt(n) == ',' || 
		exp.charAt(n) == '.')) {
			break;
		}
	}
	for (int i = n - 1; i >= 0; i--) {
		if (exp.charAt(i) >= '0' && exp.charAt(i) <= '9') {
			value += ((int) exp.charAt(i) - '0') * (int) Math.pow(10,j);
			j++;
		}
	}
	return value;
}

//remove the html tags of a String
String remove_html_tags(String src)
{
	String result = "";
	int i = 0;
	while (i < src.length()) {
		while (src.charAt(i) == '<') {
			while (i < src.length() && src.charAt(i) != '>') {
				i++;
			}
			if (i == src.length() || (i + 1) == src.length())
				break;
			i++;
		}
		result += src.charAt(i);
		i++;
	}
	return result;
}

//receive a String, the start index, the stop trigger and returns the index
//stop trigger in this relation
int goto_char(int start, String src, char to)
{
	int i;
	for (i = start; i < src.length() && src.charAt(i) != to; i++);
	return i;
}

//catch the values inside a "in" and "out" Strings in a source. The method also
//receives a start index for optimization
String catch_inter_tags(int index, String in, String out, String src)
{
	String result = "";
	boolean found_in = false;
	int i = index;
	for (; i < src.length(); i++) {
		found_in = true;
		for (int j = 0; j < in.length(); j++) {
			if (in.charAt(j) == '.') {
				i = goto_char(i,src,'>');
				break;
			} else if (src.charAt(i + j) != in.charAt(j)) {
				found_in = false;
				break;
			}
		}
		if (found_in)
			break;
	}
	if (found_in) {
		i++;
		boolean found_out = false;
		for (; (i + out.length()) < src.length(); i++) {
			found_out = true;
			for (int j = 0; j < out.length(); j++) {
				if (src.charAt(i + j) != out.charAt(j)) {
					found_out = false;
					break;
				}
			}
			if (found_out)
				break;
			else
				result += src.charAt(i);
		}
	}
	return result;
}

//returns the index of a substring, using a var to determine the start index of
//searching
int index_of(int start, String src, String to_find)
{
	int index = -1;
	int i = start;
	boolean found = false;
	for (; (i + to_find.length()) < src.length(); i++) {
		found = true;
		for (int j = 0; j < to_find.length(); j++) {
			if (src.charAt(i + j) != to_find.charAt(j)) {
				found = false;
				break;
			}
		}
		if (found) {
			index = i;
			break;
		}
	}
	return index;
}

//replace a the occurence of a char in String for another char
String replace_char(String src, char in, char out)
{
	String result = "";
	for (int i = 0; i < src.length(); i++) {
		if (src.charAt(i) == in) {
			result += out;
		} else
			result += src.charAt(i);
	}
	return src;
}

//remove the garbage of analyzed String
String remove_garbage(String src)
{
	if (src.charAt(src.length() - 1) == '>') {
		src = src.substring(0,src.length() - 1);
	}
	String result = "";
	for (int i = 0; i < src.length(); i++) {
		if (src.charAt(i) != '\n')
			result += src.charAt(i);
	}
	return result;
}

//read a archive and returns your source
String read_archive(String file_name)
{
	Arq.openRead(file_name,"UTF-8");
	String content = Arq.readAll();
	Arq.close();
	return content;
}

//get a attribute in wiki
String get_atr(int start, String name, String src)
{
	String result = "";
	int i = index_of(start,src,name);
	if (i != -1)
		result = remove_garbage
		(remove_html_tags(catch_inter_tags(i,"<td.>","</td>",src)));
	return result;
}

}

class Time
{
	String nome        = "";
	String apelido     = "";
	String estadio     = "";
	String tecnico     = "";
	String liga        = "";
	String nomeArquivo = "";
	int capacidade  = 0;
	int fundacaoDia = 0;
	int fundacaoMes = 0;
	int fundacaoAno = 0;
	long paginaTam  = 0;

	Time()
	{
		;
	}

	Time(String page_name)
	{
		ler(page_name);
	}

	//prints the team data
	void imprimir()
	{
		MyIO.print(nome + " ## " + apelido + " ## ");
		System.out.printf
		("%02d/%02d/%04d ## ",fundacaoDia,fundacaoMes,fundacaoAno);
		MyIO.print(estadio + " ## " + capacidade + " ## " + tecnico + 
		" ## " + liga + " ## " + nomeArquivo + " ## " + paginaTam + " ##\n");
	}

	//read a wiki and assemble the team variables
	void ler(String site_name) {
		Utils util = new Utils();
		nomeArquivo = site_name;
		String site = util.read_archive(site_name);
		paginaTam = (long) site.length();
		int i = util.index_of(0,site,"ull name");

		//for name
		setNome(util.get_atr(i,"ull name",site));

		//for nick
		setApelido(util.get_atr(i,"Nickname(s)",site));

		//for foundation
		String tmp = util.get_atr(i,"Founded",site);
		int tmp_vec[] = util.catch_date(tmp);
		setFundacaoDia(tmp_vec[0]);
		setFundacaoMes(tmp_vec[1]);
		setFundacaoAno(tmp_vec[2]);

		//for ground
		setEstadio(util.get_atr(i,"Ground",site));

		//for capacity
		setCapacidade(util.parse_int(util.get_atr(i,"Capacity",site)));

		//for manager
		setTecnico(util.get_atr(i,"Coach<",site));
		if (getTecnico() == "")		
			setTecnico(util.get_atr(i,"Manager<",site));
		if (getTecnico() == "")
			setTecnico(util.get_atr(i,"Head coach<",site));

		//for league
		setLiga(util.get_atr(i,"League",site));
	}

	/**
	Time clone()
	{
		Time tm = new Time();
		tm.ler(nomeArquivo);
		return tm;
	}
	*/

	String getNome()
	{
		return nome;
	}

	void setNome(String nome)
	{
		this.nome = nome;
	}

	String getApelido()
	{
		return apelido;
	}

	void setApelido(String apelido)
	{
		this.apelido = apelido;
	}

	String getEstadio()
	{
		return estadio;
	}

	void setEstadio(String estadio)
	{
		this.estadio = estadio;
	}

	String getTecnico()
	{
		return tecnico;
	}

	void setTecnico(String tecnico)
	{
		this.tecnico = tecnico;
	}

	String getLiga()
	{
		return liga;
	}

	void setLiga(String liga)
	{
		this.liga = liga;
	}

	String getNomeArquivo()
	{
		return nomeArquivo;
	}

	int getCapacidade()
	{
		return capacidade;
	}
	
	void setCapacidade(int capacidade)
	{
		this.capacidade = capacidade;
	}

	int getFundacaoDia()
	{
		return fundacaoDia;
	}

	void setFundacaoDia(int dia)
	{
		this.fundacaoDia = dia;
	}

	int getFundacaoMes()
	{
		return fundacaoMes;
	}
	
	void setFundacaoMes(int mes)
	{
		this.fundacaoMes = mes;
	}

	int getFundacaoAno()
	{
		return fundacaoAno;
	}

	void setFundacaoAno(int ano)
	{
		this.fundacaoAno = ano;
	}

	long getPaginaTam()
	{
		return paginaTam;
	}
}
class TimeList
{
	Time values[];
	int maxSize;
	int qnt = 0;
	final String separator;

	TimeList(int max_size, String separator) 
	{
		values = new Time[max_size];
		maxSize = max_size;
		this.separator = separator;
	}

	public void insertInit(Time value) throws Exception 
	{
		if (qnt < maxSize) {
			for (int i = qnt - 1; i >= 0; i--) {
				values[i + 1] = values[i];
			}
			values[0] = value;
			qnt++;
		} else
			throw new Exception
			("error: II: List doesnt have empty space");
	}

	public void insertFinal(Time value) throws Exception
	{
		if (qnt < maxSize) {
			values[qnt] = value;
			qnt++;
		} else
			throw new Exception
			("error: IF: List doesnt have empty space");
	}

	public Time removeInit() throws Exception
	{
		Time tmp;
		if (qnt > 0) {
			tmp = values[0];
			for (int i = 1; i < qnt; i++)
				values[i - 1] = values[i];
			qnt--;
		} else
			throw new Exception
			("error: RI: List is empty");
		MyIO.println("(R) " + tmp.getNome());
		return tmp;
	}

	public Time removeFinal() throws Exception
	{
		Time tmp;
		if (qnt > 0) {
			qnt--;
			tmp = values[qnt];
		} else
			throw new Exception
			("error: RF: List is empty");
		MyIO.println("(R) " + tmp.getNome());
		return tmp;
	}

	public void insert(int index, Time value) throws Exception 
	{
		if (qnt != maxSize) {
			if (index >= 0 && index <= qnt) {
				for (int i = qnt - 1; i >= index; i--) {
					values[i + 1] = values[i];
				}
				values[index] = value;
				qnt++;
			} else
				throw new Exception
				("error: I*: Invalid index");

		} else
			throw new Exception
			("error: I*: List doesnt have empty space");
	}

	public Time remove(int index) throws Exception 
	{
		Time tmp = new Time();
		if (qnt != 0) {
			if (index >= 0 && index < qnt) {
				tmp = values[index];
				for (int i = index + 1; i < qnt; i++)
					values[i - 1] = values[i];
				qnt--;
			} else
				throw new Exception
				("error: I*: Invalid index");
		} else
			throw new Exception
			("error: R*: List is empty");
		MyIO.println("(R) " + tmp.getNome());
		return tmp;
	}

	public void show() 
	{
		for (int i = 0; i < qnt; i++) {
			MyIO.print("[" + i + "] ");
			values[i].imprimir();
		}
	}

}


class lista_times
{
	//the main method: 
	//-read the team file name, build the team, print the team and stops 
	//when "FIM" appears
	//-after, read the list element qnt, and execute the list commands
	public static void main(String args[])
	{
		MyIO.setCharset("UTF-8");
		String buffer = new String();
		TimeList list = new TimeList(80,"\n");
		while (true)
		{
			buffer = MyIO.readLine();
			if (buffer.equals("FIM"))
				break;
			else {
				Time tm = new Time();
				tm.ler(buffer);
				try {
					list.insertFinal(tm);
				} catch (Exception e) {
					MyIO.println("" + e);
				}
			}	
		}
		int qnt = Integer.parseInt(MyIO.readLine());
		try {		
			for (int i = 0; i < qnt; i++) {
				String exp[] = MyIO.readLine().split(" ");
				switch (exp[0]) {
				case "II":
					list.insertInit(new Time(exp[1]));
					break;
				case "IF":
					list.insertFinal(new Time(exp[1]));
					break;					
				case "I*":
					list.insert
					(Integer.parseInt(exp[1]),new Time(exp[2]));
					break;
				case "RI":
					list.removeInit();
					break;
				case "RF":
					list.removeFinal();
					break;
				case "R*":
					list.remove(Integer.parseInt(exp[1]));
					break;
				default:

					break;
				}
			}
		} catch (Exception e) {
			MyIO.println("" + e);;
		}
		list.show();
	}
}
