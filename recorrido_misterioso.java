import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class recorrido_misterioso {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int cant_nodos = sc.nextInt();

        int[][] aristas = new int[cant_nodos-1][2];
        int[] secuencia = new int[cant_nodos];

        //pongo todas las aristas
        for (int i = 0; i <= cant_nodos-1; i++){
            aristas[i][0] = sc.nextInt();
            aristas[i][1] = sc.nextInt();
        }

        //pongo la secuencia
        for (int j = 0; j <= cant_nodos; j++){
            secuencia[j] = sc.nextInt();
        }

        ArrayList<Integer> conexiones = new ArrayList<>();

        //recorro la secuencia
        for (int k = 0; k <= cant_nodos; k++){
            //recorro las aristas
            for (int l = 0; l <= cant_nodos-1; l++){
                if (secuencia[k] == aristas[l][0])
                    conexiones.add(aristas[l][1]);
                else if (secuencia[k] == aristas[l][1])
                    conexiones.add(aristas[l][0]);
            if (conexiones.size() != 0)
            //si tiene conexiones, me fijo que el siguiente en secuencia sea alguno de esos (si no llega a ser devuelvo NO), sino lo dejo seguir            
            }
        }
    }
}
