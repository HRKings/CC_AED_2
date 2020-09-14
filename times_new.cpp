#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

#define TRUE  1
#define FALSE 0 

#define MODE 1 //0, debug; 1, run

#if MODE == 0
	#define dbg printf
#else 
	#define dbg(x)
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
			Time t;
			int s = novo_time(&t,buffer_aux);
			if (s != -1)
				print_time(&t);
			for (int i = 0; i < 80; i++) {
				buffer_aux[i] = '\0';
			}
		}
	}
	return 0;
}
