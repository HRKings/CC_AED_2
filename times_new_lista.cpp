#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

#define TRUE  1
#define FALSE 0 

#define MODE 1 //0, debug; 1, run

#if MODE == 0
	#define dbg  printf
	#define dbgf printf
#else 
	#define dbg(x)
	#define dbgf(x,y) 
#endif

typedef struct
{
	char  nome[1024];
	char  apelido[1024];
	char  estadio[1024];
	char  tecnico[1024];
	char  liga[1024];
	char  nomeArquivo[512];
	int   capacidade;
	int   fundacaoDia;
	int   fundacaoMes;
	int   fundacaoAno;
	long  paginaTam;
} Time;

Time  values_buff[100];
int buff_qnt = 0;
Time* values[80];
int qnt = 0;
int max_size = 80;

//remove the html tags of a string
char* remove_html_tags(char* str)
{
	dbg("remove_html_tags:\n");
	char* result = str;
	int i = 0;
	int j = 0;
	while (str[i] != '\0') {
		while (str[i] == '<') {
			while (str[i] != '\0' && str[i] != '>') {
				i++;
			}
			if (str[i] == '\0' || str[i + 1] == '\0')
				break;
			i++;
		}
		result[j] = str[i];
		i++;
		j++;
	}
	result[j] = '\0';
	return result;
}

//scan the main string and returns the index of "to" char
int goto_char(int start, char* src, char to)
{
	int i;
	for (i = start; src[i] != '\0' && src[i] != to; i++);
	return i;
}

//catch the value inter "in" and "out" strings, the '.' in "in" string represent
//one or more undefined chars before the next char of "in". This method also needs
//to assign a index start point, and the source string
char* catch_inter_tags(int index, char* in, char* out, char* src, char* result)
{
	dbg("catch_inter_tags:\n");
	if (result != NULL) {
		bool found_in = FALSE;
		int i = index;
		for (; src[i] != '\0'; i++) {
			found_in = TRUE;
			for (int j = 0; in[j] != '\0'; j++) {
				if (in[j] == '.') { i = goto_char(i,src,'>');
					break;
				}
				else if (src[i + j] != in[j]) {
					found_in = FALSE;
					break;
				}
			}
			if (found_in) {
				break;
			}
		}
		if (found_in) {
			//i += strlen(in);
			i++;
			bool found_out = FALSE;
			int k = 0;
			for (; src[i + strlen(out)] != '\0'; i++) {
				found_out = TRUE;
				for (int j = 0; out[j] != '\0'; j++) {
					if (src[i + j] != out[j]) {
						found_out = FALSE;
						break;
					}
				}
				if (found_out) {
					result[k] = '\0';
					break;
				} else {
					result[k] = src[i];
					k++;
				}
			}
		}
	}
	return result;
}

//get the index of a substring in a main string, using a var to assign the 
//search start point 
int index_of(int start, char* src, char* to_find) 
{
	dbg("index_of:\n");
	int index = -1;
	int i = start;
	bool found = FALSE;
	for (; src[i + strlen(to_find)] != '\0'; i++) {
		found = TRUE;
		for (int j = 0; to_find[j] != '\0'; j++) {
			if (src[i + j] != to_find[j]) {
				found = FALSE;
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

//replace the occurrence of a char for another char
char* replace_char(char* src, char in, char out)
{
	for (int i = 0; src[i] != '\0'; i++) {
		if (src[i] == in) {
			src[i] = out;
		}
	}
	return src;
}

//read a archive and pass to "byte_size" the bite count of archive
char* read_archive(char* file_name, long* byte_size)
{
	dbg("read_archive:\n");
	char* content = NULL;
	char* buff = (char*) calloc(120,sizeof(char));
	FILE* arch = fopen(file_name,"r");
	if (arch != NULL) {
		fseek(arch,0,SEEK_END);
		*byte_size = (long) ftell(arch);
		content = (char*) malloc((ftell(arch) + 80) * sizeof(char));
		fseek(arch,0,SEEK_SET);
		if (content != NULL) {
			while (!feof(arch)) {
				fgets(buff,80,arch);
				strcat(content,buff);
				//dbg("readed line: %d\n",i);
			}
			free(buff);
		} else
			dbg("malloc error");
		fclose(arch);
	} else 
		dbg("the archive is null\n");
	return content;
}

//to do
void catch_date(char* exp, int* day, int* month, int* year)
{
	*day = 1;
	*month = 1;
	*year = 1900;
}

//converts a char* into a valid integer value
int parse_int(char* exp)
{
	dbg("parse_int:\n");
	int value = 0;
	int j = 0;
	for (int i = strlen(exp) - 1; i >= 0; i--) {
		if (exp[i] >= '0' && exp[i] <= '9') {
			value += ((int) exp[i] - '0') * (int) pow(10,j);
			j++;
		} else if (exp[i] == ' ')
			break;
	}
	//free(exp);
	return value;
}

//show the Time
void print_time(Time* tm)
{
	printf("%s ## %s ## %02d/%02d/%04d ## %s ## %d ## %s ## %s ## %s ## %ld ##\n",
	tm->nome,
	tm->apelido,
	tm->fundacaoDia,
	tm->fundacaoMes,
	tm->fundacaoAno,
	tm->estadio,
	tm->capacidade,
	tm->tecnico,
	tm->liga,
	tm->nomeArquivo,
	tm->paginaTam);
}

//remove the garbage of a string
char* remove_garbage(char* src)
{
	dbg("remove_garbage:\n");
	char* result = src;
	if (src[strlen(src) - 1] == '>') {
		src[strlen(src) - 1] = '\0';
	}
	int j = 0;
	for (int i = 0; src[i] != '\0'; i++) {
		if (src[i] != '\n') {
			result[j] = src[i];
			j++;
		}
	}
	result[j] = '\0';
	return result;	
}

//get a attribute in wiki by name and start point
char* get_atr(int start, char* name, char* src, char* out)
{
	int i = index_of(start,src,name);
	if (i != -1)
		return remove_garbage
		(remove_html_tags(catch_inter_tags(i,"<td.>","</td>",src,out)));
	return NULL;
}

//verify if the char array is a "null" array
bool is_null_c_array(char* s) 
{
	return (s[0] == '\0');
}

//initialize the char array
void init_array(char* s, int size)
{
	for (int i = 0; i < size; i++) s[i] = '\0';
}

//copy the "in" string to "out" string
void copy_array(char* in, char* out)
{
	for (int i = 0; in[i] != '\0'; i++) out[i] = in[i];
}

//returns a new team
int novo_time(Time* tm,char* site_name)
{
	int status = -1;
	char* site = read_archive(site_name,&tm->paginaTam);

	if (site != NULL) {
		status = 0;

		char tmp[1024]; 

		init_array(tmp        ,1024);
		init_array(tm->nome   ,1024);
		init_array(tm->apelido,1024);
		init_array(tm->estadio,1024);
		init_array(tm->tecnico,1024);
		init_array(tm->liga   ,1024);
		init_array(tm->nomeArquivo,512);

		tm->capacidade  = 0;
		tm->fundacaoDia = 0;
		tm->fundacaoMes = 0;
		tm->fundacaoAno = 0;

		strcpy(tm->nomeArquivo,site_name);

		// for name
		int i      = index_of(0,site,"ull name");
		get_atr(i,"ull name",site,tm->nome);

		//for nick
		get_atr(i,"Nickname(s)",site,tm->apelido);

		//for foundation  
		get_atr(i,"Founded",site,tmp);
		catch_date
		(tmp,&tm->fundacaoDia,&tm->fundacaoMes,&tm->fundacaoAno);

		//for ground
		get_atr(i,"Ground",site,tm->estadio);

		//for capacity
		tm->capacidade = parse_int(get_atr(i,"Capacity",site,tmp));
		
		//get the coach
		get_atr(i,"Coach<",site,tm->tecnico);
		if (is_null_c_array(tm->tecnico))
			get_atr(i,"Manager</t",site,tm->tecnico);
		if (is_null_c_array(tm->tecnico))
			get_atr(i,"Head coach<",site,tm->tecnico);

		//get the league
		get_atr(i,"League",site,tm->liga);
			
		//free(site);
	}
	return status;
}

//insert a element on first position
void list_inserir_inicio(Time* value)
{
	if (qnt < max_size) {
		for (int i = qnt - 1; i >= 0; i--) {
			values[i + 1] = values[i];
		}
		values[0] = value;
		qnt++;
	}
}

//insert a element on final position
void list_inserir_final(Time* value)
{
	if (qnt < max_size) {
		values[qnt] = value;
		qnt++;
	}
}

//remove the element on first position
Time* list_remover_inicio()
{
	Time* tmp;
	if (qnt > 0) {
		tmp = values[0];
		for (int i = 1; i < qnt; i++)
			values[i - 1] = values[i];
		qnt--;
	}
	printf("(R) %s\n",tmp->nome);
	return tmp;
}

//remove the element in final position
Time* list_remover_final()
{
	Time* tmp;
	if (qnt > 0) {
		qnt--;
		tmp = values[qnt];
	}
	printf("(R) %s\n",tmp->nome);
	return tmp;	
}

//insert a element in "index" position
void list_inserir(int index, Time* value)
{
	if (qnt != max_size) {
		if (index >= 0 && index <= qnt) {
			for (int i = qnt - 1; i >= index; i--) {
				values[i + 1] = values[i];
			}
			values[index] = value;
			qnt++;
		}
	}
}

//remove the element on index position
Time* list_remover(int index)
{
	Time* tmp;
	if (qnt != 0) {
		if (index >= 0 && index < qnt) {
			tmp = values[index];
			for (int i = index + 1; i < qnt; i++)
				values[i - 1] = values[i];
			qnt--;
		}
	}
	printf("(R) %s\n",tmp->nome);
	return tmp;
}

//show the list data
void list_show()
{
	for (int i = 0; i < qnt; i++) {
		printf("[%d] ",i);
		print_time(values[i]);
	}
}

//the main method: 
//-read the team file name, build the team, print the team and stops 
//when "FIM" appears
//-after, read the list element qnt, and execute the list commands
int main()
{
	char buffer[80];
	char buffer_aux[80];
	while (true) {
		fgets(buffer,80,stdin);
		int j = 0;
		for (int i = 0; i < strlen(buffer); i++) {
			if (buffer[i] != '\n') { 
				buffer_aux[j] = buffer[i];
				j++;
			}
		}
		buffer_aux[j] = '\0';
		if (strcmp(buffer_aux,"FIM") == 0)
			break;
		else {
			int s = novo_time(&values_buff[buff_qnt],buffer_aux);
			if (s != -1) {
				list_inserir_final(&values_buff[buff_qnt]);
				buff_qnt++;
			}
			for (int i = 0; i < 80; i++) {
				buffer_aux[i] = '\0';
			}
		}
	}
	dbg("segunda parte:\n");
	int op_qnt = 0;
	scanf("%d",&op_qnt);
	dbgf("quantidade de operacoes: %d\n",op_qnt);
	char mnemonic[80];
	char str_p[80];
	int  int_p = 0;
	for (int i = 0; i < op_qnt; i++) {
		init_array(mnemonic,80);
		init_array(str_p,80);
		scanf("%s",mnemonic);
		dbgf("mnemonica: %s\n",mnemonic);
		if (strcmp(mnemonic,"II") == 0) {
			scanf("%s",str_p);
			int s = novo_time(&values_buff[buff_qnt],str_p);
			if (s != -1) {
				list_inserir_inicio(&values_buff[buff_qnt]);
				buff_qnt++;
			}
		} else if (strcmp(mnemonic,"IF") == 0) {
			scanf("%s",str_p);
			int s = novo_time(&values_buff[buff_qnt],str_p);
			if (s != -1) {
				list_inserir_final(&values_buff[buff_qnt]);
				buff_qnt++;
			}
		} else if (strcmp(mnemonic,"I*") == 0) {
			scanf("%d%s",&int_p,str_p);
			int s = novo_time(&values_buff[buff_qnt],str_p);
			if (s != -1) {
				list_inserir(int_p,&values_buff[buff_qnt]);
				buff_qnt++;
			}
		} else if (strcmp(mnemonic,"RI") == 0) {
			list_remover_inicio();
		} else if (strcmp(mnemonic,"RF") == 0) {
			list_remover_final();
		} else if (strcmp(mnemonic,"R*") == 0) {
			scanf("%d",&int_p);
			list_remover(int_p);
		}
	}
	list_show();
	return 0;
}
