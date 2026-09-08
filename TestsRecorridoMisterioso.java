import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TestsRecorridoMisterioso {

    // Extraemos la lógica central para que sea testeable directamente con datos en memoria
    public static String verificarRecorrido(int cant_nodos, int[][] aristas, int[] secuencia) {
        if (secuencia[0] != 1) {
            return "No";
        }

        List<List<Integer>> conexiones = new ArrayList<>();
        for (int i = 0; i <= cant_nodos; i++) {
            conexiones.add(new ArrayList<>());
        }

        for (int[] arista : aristas) {
            int v = arista[0];
            int u = arista[1];
            conexiones.get(v).add(u);
            conexiones.get(u).add(v);
        }

        int[] posiciones = new int[cant_nodos + 1];
        for (int k = 0; k < cant_nodos; k++) {
            posiciones[secuencia[k]] = k;
        }

        // Ordenamos los vecinos según el orden en que aparecen en 'secuencia'
        for (int i = 1; i <= cant_nodos; i++) {
            Collections.sort(conexiones.get(i), (a, b) -> Integer.compare(posiciones[a], posiciones[b]));
        }

        Queue<Integer> cola = new LinkedList<>();
        boolean[] visitado = new boolean[cant_nodos + 1];

        cola.add(1);
        visitado[1] = true;
        int idx = 0;

        while (!cola.isEmpty()) {
            int actual = cola.poll();

            if (secuencia[idx++] != actual) {
                return "No";
            }

            for (int vecino : conexiones.get(actual)) {
                if (!visitado[vecino]) {
                    visitado[vecino] = true;
                    cola.add(vecino);
                }
            }
        }

        return "Yes";
    }

    private static void correrCaso(String nombreTest, int n, int[][] aristas, int[] secuencia, String esperado) {
        String obtenido = verificarRecorrido(n, aristas, secuencia);
        if (obtenido.equalsIgnoreCase(esperado)) {
            System.out.printf("[✓ PASÓ] %s -> Salida: %s\n", nombreTest, obtenido);
        } else {
            System.out.printf("[✗ FALLÓ] %s -> Esperado: %s | Obtenido: %s\n", nombreTest, esperado, obtenido);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Ejecutando Tests de Recorrido BFS ===");

        // Caso 1: Ejemplo típico válido
        // Árbol: 1-2, 1-3, 2-4
        // Nivel 0: {1} -> Nivel 1: {2, 3} -> Nivel 2: {4}
        correrCaso("Caso 1: BFS canónico válido",
                   4,
                   new int[][]{{1, 2}, {1, 3}, {2, 4}},
                   new int[]{1, 2, 3, 4},
                   "Yes");

        // Caso 2: Mismo árbol pero orden de hijos invertido (sigue siendo BFS válido)
        correrCaso("Caso 2: Orden alternativo entre hermanos válido",
                   4,
                   new int[][]{{1, 2}, {1, 3}, {2, 4}},
                   new int[]{1, 3, 2, 4},
                   "Yes");

        // Caso 3: Secuencia inválida (salta a un nieto antes de visitar a un hijo/hermano)
        // Visita el 4 (hijo de 2) antes que el 3 (hijo de 1)
        correrCaso("Caso 3: Visita nieto antes de terminar nivel (inválido)",
                   4,
                   new int[][]{{1, 2}, {1, 3}, {2, 4}},
                   new int[]{1, 2, 4, 3},
                   "No");

        // Caso 4: No empieza en el nodo 1
        correrCaso("Caso 4: Inicio distinto a nodo 1",
                   3,
                   new int[][]{{1, 2}, {2, 3}},
                   new int[]{2, 1, 3},
                   "No");

        // Caso 5: Árbol trivial (N = 1, sin aristas)
        correrCaso("Caso 5: Nodo único N = 1",
                   1,
                   new int[][]{},
                   new int[]{1},
                   "Yes");

        // Caso 6: Cadena lineal 1 - 2 - 3 - 4
        correrCaso("Caso 6: Cadena lineal",
                   4,
                   new int[][]{{1, 2}, {2, 3}, {3, 4}},
                   new int[]{1, 2, 3, 4},
                   "Yes");

        // Caso 7: Estrella con centro en 1
        // Todos son vecinos directos de 1, cualquier orden posterior es válido
        correrCaso("Caso 7: Estrella con permutación cualquiera",
                   5,
                   new int[][]{{1, 2}, {1, 3}, {1, 4}, {1, 5}},
                   new int[]{1, 5, 2, 4, 3},
                   "Yes");

        System.out.println("=== Fin de la ejecución ===");
    }
}