class Celula {
   public int elemento;
   public Celula inf, sup, esq, dir;

   public Celula() {
      this(0, null, null, null, null);
   }

   public Celula(int elemento) {
      this(elemento, null, null, null, null);
   }

   public Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir) {
      this.elemento = elemento;
      this.inf = inf;
      this.sup = sup;
      this.esq = esq;
      this.dir = dir;
   }
}

class Matriz {
   private Celula inicio;
   private int linha, coluna;

   public Matriz() {
      this(3, 3);
   }

   public Matriz(int linha, int coluna) {
      this.linha = linha;
      this.coluna = linha;

      this.inicio = new Celula();

      Celula cur = inicio;
      for (int i = 0; i < linha; cur = cur.inf, i++) {
         Celula cur2 = cur;
         for (int j = 0; j < coluna - 1; cur2 = cur2.dir, j++) {
               cur2.dir = new Celula();
               cur2.dir.esq = cur2;
   
               if (cur2.sup != null) {
                  cur2.dir.sup = cur2.sup.dir;
                  cur2.sup.dir.inf = cur2.dir;
               }
           
         }
         if(i != linha - 1){
            cur.inf = new Celula();
            cur.inf.sup = cur;
         }
         
      }
   }

   public void inserir(int linha, String valores) {
      Celula linhaInit = inicio;
      String[] vals = valores.split(" ");

      for (int i = 0; i < this.linha && i < linha; i++, linhaInit = linhaInit.inf);

      for (int i = 0; linhaInit != null && i < this.coluna && i < vals.length; linhaInit = linhaInit.dir, i++) {
         linhaInit.elemento = Integer.parseInt(vals[i]);
      }
   }

   public Matriz soma(Matriz m) {
      Matriz resultado = null;

      if (this.linha == m.linha && this.coluna == m.coluna) {
         resultado = new Matriz(this.linha, m.coluna);

         for (Celula i = inicio, i2 = m.inicio, r = resultado.inicio; i != null
               && i2 != null; i = i.inf, i2 = i2.inf, r = r.inf) {
            for (Celula j = i, j2 = i2, rc = r; j != null && j2 != null; j = j.dir, j2 = j2.dir) {
               rc.elemento = j.elemento + j2.elemento;
            }
         }
      }

      return resultado;
   }

   public Matriz multiplicacao(Matriz m) {
      Matriz resp = null;

      if (m.isQuadrada()) {
         // ...
      }

      return resp;
   }

   public boolean isQuadrada() {
      return (this.linha == this.coluna);
   }

   public void mostrarDiagonalPrincipal() {
      if (isQuadrada() == true) {
         for (Celula i = inicio; i.dir != null; i = i.dir.inf) {
               System.out.print(i.elemento + " ");
         }
         System.out.println();
      }
   }

   public void mostrarDiagonalSecundaria() {
      if (isQuadrada() == true) {
         Celula i = inicio;
         for(i = inicio; i.dir != null; i = i.dir);
         for(; i.inf != null; i = i.inf.esq){
            System.out.print(i.elemento + " ");
         }
         System.out.println();
      }
   }

   public void mostrar() {
      for (Celula i = inicio; i != null; i = i.inf) {
         for (Celula j = i; j != null; j = j.dir) {
            System.out.print(j.elemento + " ");
         }
         System.out.println();
      }
   }
}

class MatrizTP {
   public static void main(String[] args) {
      int tests = Integer.parseInt(MyIO.readLine());

      for (int i = 0; i < tests; i++) {
         int lin = Integer.parseInt(MyIO.readLine());
         int col = Integer.parseInt(MyIO.readLine());
         Matriz m1 = new Matriz(lin, col);
         for (int j = 0; j < lin; j++) {
            m1.inserir(j, MyIO.readLine());
         }

         lin = Integer.parseInt(MyIO.readLine());;
         col = Integer.parseInt(MyIO.readLine());
         Matriz m2 = new Matriz(lin, col);
         for (int j = 0; j < lin; j++) {
            m2.inserir(j, MyIO.readLine());
         }

         m1.mostrarDiagonalPrincipal();
         m1.mostrarDiagonalSecundaria();

         m1.soma(m2).mostrar();
      }
   }
}