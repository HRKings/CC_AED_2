```java
public boolean contem(int n){
    boolean resp = false;

    for(int i = 0; !resp && i < array.length; i++)
        if(array[i] == n)
            resp = true;

    return resp;
}

public void menor(int[] n){
    int m = n[0];

    for(int i = 1; i < n.length; i++)
        if(n[i] < m)
            m = n[i];

    System.out.println(m);
}
```

- Verifica se é vogal
- A primeira versão
- Extremamente difícil de entender
- Um decrementa depois e o outro antes
- Ele mostra indefinidamente os numeros incrementando de um em um
- Pois ela da um shift nos bits dos numeros, ou seja, os move uma casa para um lado