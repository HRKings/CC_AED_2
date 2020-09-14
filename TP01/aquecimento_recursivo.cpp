#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define bool      short
#define true      1
#define false     0
#define equals(a, b) (((strcmp(a, b) == 0) ? true : false))
#define NUMENTRADA 1000
#define TAMLINHA   1000

bool isMaiuscula (char c){
   return (c >= 'A' && c <= 'Z');
}

bool isFim(char* s){
   return (strlen(s) >= 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

    int contarMaiusculas(char* s, int index){
      int resp = 0;

      if(index < strlen(s)){
         if(isMaiuscula(s[index]) == true){
            resp = 1; 
         }
         resp = resp + contarMaiusculas(s, index + 1);
      }

      return resp;
   }

   int contarLetrasMaiusculas (char* s){
      int resp = 0;

      resp = contarMaiusculas(s, 0);

      return resp;
   }

int main(int argc, char** argv){
   char entrada[NUMENTRADA][TAMLINHA];
   int numEntrada = 0;

   //Leitura da entrada padrao
   do {
      fgets(entrada[numEntrada], TAMLINHA, stdin);
   } while (isFim(entrada[numEntrada++]) == false);
   numEntrada--;   //Desconsiderar ultima linha contendo a palavra FIM

   //Para cada linha de entrada, gerando uma de saida contendo o numero de letras maiusculas da entrada
   for(int i = 0; i < numEntrada; i++){
      printf("%i\n",contarLetrasMaiusculas(entrada[i]));
   }
}
