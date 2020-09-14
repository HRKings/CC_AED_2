#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAXTAM    80

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


Time* array[MAXTAM];   // Elementos da pilha 
int n;               // Quantidade de array.


/**
 * Inicializacoes
 */
void start(){
   n = 0;
}


/**
 * Insere um elemento na primeira posicao da lista e move os demais
 * elementos para o fim da 
 * @param x int elemento a ser inserido.
 */
void inserirInicio(Time* x) {
   int i;

   //validar insercao
   if(n >= MAXTAM){
      printf("Erro ao inserir!");
      exit(1);
   } 

   //levar elementos para o fim do array
   for(i = n; i > 0; i--){
      array[i] = array[i-1];
   }

   array[0] = x;
   n++;
}


/**
 * Insere um elemento na ultima posicao da 
 * @param x int elemento a ser inserido.
 */
void inserirFim(Time* x) {

   //validar insercao
   if(n >= MAXTAM){
      printf("Erro ao inserir!");
      exit(1);
   }

   array[n] = x;
   n++;
}


/**
 * Insere um elemento em uma posicao especifica e move os demais
 * elementos para o fim da 
 * @param x int elemento a ser inserido.
 * @param pos Posicao de insercao.
 */
void inserir(Time* x, int pos) {
   int i;

   //validar insercao
   if(n >= MAXTAM || pos < 0 || pos > n){
      printf("Erro ao inserir!");
      exit(1);
   }

   //levar elementos para o fim do array
   for(i = n; i > pos; i--){
      array[i] = array[i-1];
   }

   array[pos] = x;
   n++;
}


/**
 * Remove um elemento da primeira posicao da lista e movimenta 
 * os demais elementos para o inicio da mesma.
 * @return resp int elemento a ser removido.
 */
Time* removerInicio() {
   int i;
   Time* resp;

   //validar remocao
   if (n == 0) {
      printf("Erro ao remover!");
      exit(1);
   }

   resp = array[0];
   n--;

   for(i = 0; i < n; i++){
      array[i] = array[i+1];
   }

   return resp;
}


/**
 * Remove um elemento da ultima posicao da 
 * @return resp int elemento a ser removido.
 */
Time* removerFim() {

   //validar remocao
   if (n == 0) {
      printf("Erro ao remover!");
      exit(1);
   }

   return array[--n];
}


/**
 * Remove um elemento de uma posicao especifica da lista e 
 * movimenta os demais elementos para o inicio da mesma.
 * @param pos Posicao de remocao.
 * @return resp int elemento a ser removido.
 */
Time* remover(int pos) {
   int i;
   Time* resp;

   //validar remocao
   if (n == 0 || pos < 0 || pos >= n) {
      printf("Erro ao remover!");
      exit(1);
   }

   resp = array[pos];
   n--;

   for(i = pos; i < n; i++){
      array[i] = array[i+1];
   }

   return resp;
}


/**
 * Mostra os array separados por espacos.
 */
void mostrar (){
   int i;

   for(i = 0; i < 10; i++){
      imprimir(array[i]);
   }
}

void swap(int i, int j) {
   Time* temp = array[i];
   array[i] = array[j];
   array[j] = temp;
}

void constroi(int tamHeap){
      for(int i = tamHeap; i > 1 && array[i]->paginaTam > array[i/2]->paginaTam; i /= 2){
         swap(i, i/2);
      }
}

void reconstroi(int tamHeap){
   int i = 1, filho;
   while(i <= (tamHeap/2)){

      if (array[2*i]->paginaTam > array[2*i+1]->paginaTam || 2*i == tamHeap){
         filho = 2*i;
      } else {
         filho = 2*i + 1;
      }

      if(array[i]->paginaTam < array[filho]->paginaTam){
         swap(i, filho);
         i = filho;
      }else{
         i = tamHeap;
      }
   }
}



// Algoritmo de ordenacao
void heapsort() {
   //Alterar o vetor ignorando a posicao zero
   for(int i = n; i > 0; i--){
      array[i] = array[i-1];
   }

   //Contrucao do heap
   for(int tamHeap = 1; tamHeap <= n; tamHeap++){
      constroi(tamHeap);
   }

   //Ordenacao propriamente dita
   int tamHeap = n;
   while(tamHeap > 1){
      swap(1, tamHeap--);
      reconstroi(tamHeap);
   }

   //Alterar o vetor para voltar a posicao zero
   for(int i = 0; i < n; i++){
      array[i] = array[i+1];
   }
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
			sscanf(searchUp(tmp, "anuary", "@"),"%d",&(time->fundacaoAno));
		}else if(contains(tmp, "ebruary")){
			time->fundacaoMes = 2;
			sscanf(searchUp(tmp, "ebruary", "@"),"%d",&(time->fundacaoAno));
		}else if(contains(tmp, "arch")){
			time->fundacaoMes = 3;
			sscanf(searchUp(tmp, "arch", "@"),"%d",&(time->fundacaoAno));
		}else if(contains(tmp, "pril")){
			time->fundacaoMes = 4;	
			sscanf(searchUp(tmp, "pril", "@"),"%d",&(time->fundacaoAno));		
		}else if(contains(tmp, "ay")){
			time->fundacaoMes = 5;	
			sscanf(searchUp(tmp, "ay", "@"),"%d",&(time->fundacaoAno));	
		}else if(contains(tmp, "une")){
			time->fundacaoMes = 6;	
			sscanf(searchUp(tmp, "une", "@"),"%d",&(time->fundacaoAno));		
		}else if(contains(tmp, "uly")){
			time->fundacaoMes = 7;	
			sscanf(searchUp(tmp, "uly", "@"),"%d",&(time->fundacaoAno));	
		}else if(contains(tmp, "ugust")){
			time->fundacaoMes = 8;	
			sscanf(searchUp(tmp, "ugust", "@"),"%d",&(time->fundacaoAno));	
		}else if(contains(tmp, "eptember")){
			time->fundacaoMes = 9;	
			sscanf(searchUp(tmp, "eptember", "@"),"%d",&(time->fundacaoAno));
		}else if(contains(tmp, "ctober")){
			time->fundacaoMes = 10;	
			sscanf(searchUp(tmp, "ctober", "@"),"%d",&(time->fundacaoAno));
		}else if(contains(tmp, "ovember")){
			time->fundacaoMes = 11;	
			sscanf(searchUp(tmp, "ovember", "@"),"%d",&(time->fundacaoAno));
		}else if(contains(tmp, "ecember")){
			time->fundacaoMes = 12;	
			sscanf(searchUp(tmp, "ecember", "@"),"%d",&(time->fundacaoAno));
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

	for(int i =0; i < MAXTAM; i++){
		array[i] = novoTime();
	}

	
	while (arquivo != NULL && strcmp(arquivo, "FIM") != 0)
	{
		Time* t = novoTime();
		lerHtml(arquivo, t);
		if(t->fundacaoAno != 0)
			inserirFim(t);
		scanf("%s", arquivo);
	}

	heapsort();
	mostrar();

	//getchar();
	//getchar();

	return 0;
}