import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Queue;

public class recorrido_misterioso {
    public String recorrido_misterioso() {
        Scanner sc = new Scanner(System.in);

        int cant_nodos = sc.nextInt();

        //creo las estructuras de datos
        ArrayList<ArrayList<Integer>> conexiones = new ArrayList<>();
        int[] secuencia = new int[cant_nodos];
        int[] posiciones = new int[cant_nodos + 1];
        Queue<Integer> cola = new LinkedList<>();
        boolean[] visitado = new boolean[cant_nodos + 1];

        //inicializo conexiones
        for (int i = 0; i <= cant_nodos; i++){
            conexiones.add(new ArrayList<>());
        }

        //pongo todas las conexiones
        int v;
        int u;
        for (int i = 1; i <= cant_nodos - 1; i++){
            v = sc.nextInt();
            u = sc.nextInt();
            conexiones.get(v).add(u);
            conexiones.get(u).add(v);
        }

        //pongo la secuencia
        for (int j = 0; j < cant_nodos; j++){
            secuencia[j] = sc.nextInt();
        }

        if (secuencia[0] != 1) {
            return "No";
        }

        //pongo posiciones
        for (int k = 0; k < cant_nodos; k++){
            posiciones[secuencia[k]] = k;
        }
    
        for (int i = 1; i <= cant_nodos; i++) {
            Collections.sort(conexiones.get(i), (a, b) -> Integer.compare(posiciones[a], posiciones[b]));
        }

        //encolo el nodo 1
        cola.add(1);
        visitado[1] = true;
        int idx = 0;

        while (!cola.isEmpty()) {
            int actual = cola.poll();

            // Si el nodo que sale de la cola no coincide con la posición actual en secuencia:
            if (secuencia[idx++] != actual) {
                return "No";
            }

            // Encolamos los vecinos no visitados
            for (int vecino : conexiones.get(actual)) {
                if (!visitado[vecino]) {
                    visitado[vecino] = true;
                    cola.add(vecino);
                }
            }
        }
        return "yes";
    }
}
//si tiene conexiones, me fijo que el siguiente en secuencia sea alguno de esos (si no llega a ser devuelvo NO), sino lo dejo seguir

