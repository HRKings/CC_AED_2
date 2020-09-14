#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define bool   short
#define true   1
#define false  0

typedef struct
{
	char nome[1024];
	char *apelido;
	char *estadio;
	char *tecnico;
	char liga[1024];
	char *nomeArquivo;

	int capacidade;
	int fundacaoDia;
	int fundacaoMes;
	int fundacaoAno;

	long paginaTam;

} Time;

void imprimir(Time *time)
{
	printf("%s ## %s ## ", time->nome, time->apelido);
	printf("%02d/%02d/%04d ## ", time->fundacaoDia, time->fundacaoMes, time->fundacaoAno);
	printf("%s ## %i ## ", time->estadio, time->capacidade);
	printf("%s ## %s ## ", time->tecnico, time->liga);
	printf("%s ## %li ##\n", time->nomeArquivo, time->paginaTam);
}


Time* novoTime()
{
	Time* time = (Time*)malloc(sizeof(Time));


		//time->nome = (char *)malloc(1024 * sizeof(char));
		time->apelido = (char *)malloc(1024 * sizeof(char));
		time->estadio = (char *)malloc(1024 * sizeof(char));
		time->tecnico = (char *)malloc(1024 * sizeof(char));
		//time->liga = (char *)malloc(10000 * sizeof(char));
		time->nomeArquivo = (char *)malloc(1024 * sizeof(char));

		time->capacidade = 0;
		time->fundacaoDia = 0;
		time->fundacaoMes = 0;
		time->fundacaoAno = 0;

		time->paginaTam = 0;
	

	return time;
}

//TIPO CELULA ===================================================================
typedef struct Celula {
	Time* elemento;        // Elemento inserido na celula.
	struct Celula* prox; // Aponta a celula prox.
} Celula;

Celula* novaCelula(Time* elemento) {
   Celula* nova = (Celula*) malloc(sizeof(Celula));
   nova->elemento = elemento;
   nova->prox = NULL;
   return nova;
}

//LISTA PROPRIAMENTE DITA =======================================================
Celula* primeiro;
Celula* ultimo;

void start () {
   primeiro = novaCelula(novoTime());
   ultimo = primeiro;
}

void inserirInicio(Time* x) {
   Celula* tmp = novaCelula(x);
   tmp->prox = primeiro->prox;
   primeiro->prox = tmp;
   if (primeiro == ultimo) {                    
      ultimo = tmp;
   }
   tmp = NULL;
}

void inserirFim(Time* x) {
   ultimo->prox = novaCelula(x);
   ultimo = ultimo->prox;
}

Time* removerInicio() {
   if (primeiro == ultimo) {
   }

   Celula* tmp = primeiro;
   primeiro = primeiro->prox;
   Time* resp = primeiro->elemento;
   tmp->prox = NULL;
   free(tmp);
   tmp = NULL;
   return resp;
}

Time* removerFim() {
   if (primeiro == ultimo) {
   } 

   // Caminhar ate a penultima celula:
   Celula* i;
   for(i = primeiro; i->prox != ultimo; i = i->prox);

   Time* resp = ultimo->elemento;
   ultimo = i;
   free(ultimo->prox);
   i = ultimo->prox = NULL;

   return resp;
}

int tamanho() {
   int tamanho = 0;
   Celula* i;
   for(i = primeiro; i != ultimo; i = i->prox, tamanho++);
   return tamanho;
}

void inserir(Time* x, int pos) {

   int tam = tamanho();

   if(pos < 0 || pos > tam){
   } else if (pos == 0){
      inserirInicio(x);
   } else if (pos == tam){
      inserirFim(x);
   } else {
      // Caminhar ate a posicao anterior a insercao
      int j;
      Celula* i = primeiro;
      for(j = 0; j < pos; j++, i = i->prox);

      Celula* tmp = novaCelula(x);
      tmp->prox = i->prox;
      i->prox = tmp;
      tmp = i = NULL;
   }
}

Time* remover(int pos) {
   Time* resp;
   int tam = tamanho();

   if (primeiro == ultimo){
   } else if(pos < 0 || pos >= tam){
   } else if (pos == 0){
      resp = removerInicio();
   } else if (pos == tam - 1){
      resp = removerFim();
   } else {
      // Caminhar ate a posicao anterior a insercao
      Celula* i = primeiro;
      int j;
      for(j = 0; j < pos; j++, i = i->prox);

      Celula* tmp = i->prox;
      resp = tmp->elemento;
      i->prox = tmp->prox;
      tmp->prox = NULL;
      free(tmp);
      i = tmp = NULL;
   }
   return resp;
}

void mostrar() {
   Celula* i;
   for (i = primeiro->prox; i != NULL; i = i->prox) {
      imprimir(i->elemento);
   }
}

bool pesquisar(Time* x) {
   bool retorno = false;
   Celula* i;

   for (i = primeiro->prox; i != NULL; i = i->prox) {
      if(i->elemento == x){
         retorno = true;
         i = ultimo;
      }
   }
   return retorno;
}


void swap(Celula* i, Celula* j) {
   Time* temp = i->elemento;
   i->elemento = j->elemento;
  j->elemento = temp;
}

Celula* andar(int x){
	Celula* c = primeiro;
	for(int i = 0; i < x && c != NULL; i++, c = c->prox);
	return c;
}

void quicksortRec (int esq, int dir) {
   Time* i = andar(esq)->elemento;
   Time* j = andar(dir)->elemento;
   Time* pivo = andar((dir+esq)/2)->elemento;
   while (strcmp(i->apelido, j->apelido) <= 0) {
      while (array[i] < pivo) i++;
      while (array[j] > pivo) j--;
      if (i <= j) {
         swap(i, j);
         i++;
         j--;
      }
   }
   if (esq < j)  quicksortRec(esq, j);
   if (i < dir)  quicksortRec(i, dir);
}

// Algoritmo de ordenacao
void quicksort() {
   quicksortRec(0, tamanho());
}

char *substring(char *padrao, char *entrada)
{
	char *pointer = strstr(entrada, padrao);

	return strdup(pointer);
}

long int indexOf(char *padrao, char *entrada)
{
	char *pointer = strstr(entrada, padrao);

	return pointer - entrada;
}

bool contains(char *string, char *procura)
{
	return strstr(string, procura) != NULL;
}

char *removerTags(char *str)
{
	char *output = (char *)malloc(10000 * sizeof(char));
	int current = 0;

	bool isTag = false;
	bool isSpecial = false;

	for (int i = 0; i < strlen(str); i++)
	{
		if (str[i] == '<')
			isTag = 1;
		else if (str[i] == '>')
		{
			isTag = 0;

			if (i > 0 && current > 0 && output[current-1] != '@' && str[i-1] != 'i' && str[i-1] != '/' && output[current-1])
			{
				output[current] = '@';
				current++;
			}
		}
		else if (isTag == 0 && str[i] == '&')
			isSpecial = 1;
		else if (isTag == 0 && str[i] == ';')
			isSpecial = 0;
		else if (isTag == 0 && isSpecial == 0)
		{
			output[current] = str[i];
			current++;
		}
	}

	return strdup(output);
}

char *searchUp(char *entrada, char *padrao, char *final)
{
	char tmp[1024] = "";
	char *start, *end;

	strcpy(tmp, "");

	if (start = strstr(entrada, padrao))
	{
		start += strlen(padrao);
		if (end = strstr(start, final))
		{
			//tmp = (char *)malloc(end - start + 1);
			memcpy(tmp, start, end - start);
			tmp[end - start] = '\0';
		}
	}

	return strdup(tmp);
	//free(start);
	//free(end);
}

void pegarCapacidade(Time *time, char *linha)
{
	char *tmp = (char *)malloc(1000 * sizeof(char));
	char *numero = (char *)malloc(256 * sizeof(char));

	strcpy(tmp, searchUp(linha, "Capacity@", "@"));

	int j = 0;
	for (int i = 0; i < strlen(tmp); i++)
	{
		if (tmp[i] != ',' && tmp[i] != ' ')
		{
			numero[j] = tmp[i];
			j++;
		}
	}

	sscanf(numero, "%d", &(time->capacidade));	
	//free(tmp);
}

void pegarData(Time *time, char *linha)
{
	char *tmp = (char *)malloc(1000 * sizeof(char));
	strcpy(tmp, searchUp(linha, "Founded@", "@"));

	if (strlen(tmp) == 4)
        	sscanf(tmp,"%d",&(time->fundacaoAno));
	else{
		if(contains(tmp, "anuary")){
			time->fundacaoMes = 1;
			sscanf(searchUp(tmp, "anuary", "\0"),"%d",&(time->fundacaoAno));
		}else if(contains(tmp, "ebruary")){
			time->fundacaoMes = 2;
			sscanf(searchUp(tmp, "ebruary", "\0"),"%d",&(time->fundacaoAno));
		}else if(contains(tmp, "arch")){
			time->fundacaoMes = 3;
			sscanf(searchUp(tmp, "arch", "\0"),"%d",&(time->fundacaoAno));
		}else if(contains(tmp, "pril")){
			time->fundacaoMes = 4;	
			sscanf(searchUp(tmp, "pril", "\0"),"%d",&(time->fundacaoAno));		
		}else if(contains(tmp, "ay")){
			time->fundacaoMes = 5;	
			sscanf(searchUp(tmp, "ay", "\0"),"%d",&(time->fundacaoAno));	
		}else if(contains(tmp, "une")){
			time->fundacaoMes = 6;	
			sscanf(searchUp(tmp, "une", "\0"),"%d",&(time->fundacaoAno));		
		}else if(contains(tmp, "uly")){
			time->fundacaoMes = 7;	
			sscanf(searchUp(tmp, "uly", "\0"),"%d",&(time->fundacaoAno));	
		}else if(contains(tmp, "ugust")){
			time->fundacaoMes = 8;	
			sscanf(searchUp(tmp, "ugust", "\0"),"%d",&(time->fundacaoAno));	
		}else if(contains(tmp, "eptember")){
			time->fundacaoMes = 9;	
			sscanf(searchUp(tmp, "eptember", "\0"),"%d",&(time->fundacaoAno));
		}else if(contains(tmp, "ctober")){
			time->fundacaoMes = 10;	
			sscanf(searchUp(tmp, "ctober", "\0"),"%d",&(time->fundacaoAno));
		}else if(contains(tmp, "ovember")){
			time->fundacaoMes = 11;	
			sscanf(searchUp(tmp, "ovember", "\0"),"%d",&(time->fundacaoAno));
		}else if(contains(tmp, "ecember")){
			time->fundacaoMes = 12;	
			sscanf(searchUp(tmp, "ecember", "\0"),"%d",&(time->fundacaoAno));
		}
	}
	
	//free(tmp);
}

int lerHtml(char *arquivo, Time *time)
{
	strcpy(time->nomeArquivo, arquivo);
	FILE *file = fopen(arquivo, "r");

	if (file)
	{
		char *init = (char *)malloc(128 * sizeof(char));
		char *line = (char *)malloc(10000 * sizeof(char));

		fgets(init, 49, file);

		while (!contains(init, "<table class=\"infobox vcard\" style=\"width:22em\">"))
		{
			if (strcmp("</html>\n", init) == 0)
				return 1;

			if (contains(init, "put\"><table class=\"infobox vcard\" style=\"width:2"))
				break;

			fgets(init, 49, file);
		}

		fgets(line, 10000, file);

		strcpy(line, removerTags(line));

		//printf("%s\n", line);

		//printf("%s\n", searchUp(line, "Full name@", "@"));
		strcpy(time->nome, searchUp(line, "Full name@", "@"));
		//printf("%s\n", time->nome);
		strcpy(time->apelido, searchUp(line, "Nickname(s)@", "@"));
		//printf("%s\n", time->apelido);
		strcpy(time->estadio, searchUp(line, "Ground@", "@"));
		//printf("%s\n", time->estadio);

		strcpy(time->tecnico, searchUp(line, "oach@", "@"));

		if (strcmp(time->tecnico, "") == 0)
		{
			strcpy(time->tecnico, searchUp(line, "anager@", "@"));
		}

		//printf("%s\n", time->tecnico);

		strcpy(time->liga, searchUp(line, "League@", "@"));
		//printf("%s\n", time->liga);

		pegarCapacidade(time, line);
		pegarData(time, line);
		//printf("%i", time->capacidade);

		fseek(file, 0, SEEK_END);
		time->paginaTam = ftell(file);
		fclose(file);
	}

	return 0;
}

int main()
{
	start();
	char *arquivo = (char *)malloc(256 * sizeof(char));
	char *arquivo2 = (char *)malloc(512 * sizeof(char));

	scanf("%s", arquivo);
	
	while (arquivo != NULL && strcmp(arquivo, "FIM") != 0)
	{
		Time* t = novoTime();
		lerHtml(arquivo, t);
		inserirFim(t);
		scanf("%s", arquivo);
	}

	mostrar();

	//getchar();
	//getchar();

	return 0;
}