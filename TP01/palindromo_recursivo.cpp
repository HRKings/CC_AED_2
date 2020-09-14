#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define bool short
#define true 1
#define false 0
#define NUMENTRADA 1000
#define TAMLINHA 1000

//Funca para verificar se determinada string e a palavra "FIM"
bool isFim(char *s)
{
	return (strlen(s) >= 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

//Funcao para comparar duas strings, iterando por seus caracteres
bool equals(char *a, char *b)
{
	bool res = true;

	for (int i = 0; i < strlen(b); i++)
	{
		if (a[i] != b[i])
		{
			res = false;
		}
	}

	return res;
}

bool verificarPalindromo2(char s[], int index){
	bool resp = true;

	if(index < strlen(s)/2){
		if(s[index] != s[strlen(s) - 2 - index]){
			resp = false;
		}
		resp = resp && verificarPalindromo2(s, index + 1);
	}

    return resp;
}

//Funcao para checar o palindromo
void isPalindromo(char s[])
{
	bool resp = true;

	resp = verificarPalindromo2(s, 0);

	if(strlen(s) == 0)
		resp = true;

	if (resp == true)
	{
		printf("SIM\n");
	}
	else
	{
		printf("NAO\n");
	}
}

int main(int argc, char **argv)
{
	char entrada[NUMENTRADA][TAMLINHA];
	int numEntrada = 0;

	//Leitura da entrada padrao
	do
	{
		fgets(entrada[numEntrada], TAMLINHA, stdin);
	} while (isFim(entrada[numEntrada++]) == false);
	numEntrada--; //Desconsiderar ultima linha contendo a palavra FIM

	//Para cada linha de entrada, compara o palindromo
	for (int i = 0; i < numEntrada; i++)
	{
		isPalindromo(entrada[i]);
	}
}