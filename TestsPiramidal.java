import java.util.ArrayList;
import java.util.List;

public class TestsPiramidal {

    // Extraemos la lógica de tu función para poder testearla con cualquier entrada
    public static int resolver(int n, int[] primera_lista) {
        List<Integer>[] segunda_lista = new ArrayList[n + 1];
        for (int j = 1; j <= n; j++) {
            segunda_lista[j] = new ArrayList<>();
        }

        List<Integer> raices = new ArrayList<>();
        for (int k = 1; k <= n; k++) {
            if (primera_lista[k] == -1) {
                raices.add(k);
            } else {
                segunda_lista[primera_lista[k]].add(k);
            }
        }

        int max_mesas = 0;
        for (int raiz : raices) {
            max_mesas = Math.max(max_mesas, dfs(raiz, segunda_lista));
        }

        return max_mesas;
    }

    private static int dfs(int socio, List<Integer>[] adj) {
        int max_subordinado = 0;
        for (int subordinado : adj[socio]) {
            max_subordinado = Math.max(max_subordinado, dfs(subordinado, adj));
        }
        return 1 + max_subordinado;
    }

    // Helper para verificar y mostrar resultados en consola
    private static void correrCaso(String nombreTest, int n, int[] input, int esperado) {
        int obtenido = resolver(n, input);
        if (obtenido == esperado) {
            System.out.printf("[✓ PASÓ] %s -> Salida: %d\n", nombreTest, obtenido);
        } else {
            System.out.printf("[✗ FALLÓ] %s -> Esperado: %d | Obtenido: %d\n", nombreTest, esperado, obtenido);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Ejecutando batería de tests ===");

        // Caso 1: Ejemplo típico enunciado
        // 1 y 5 raíces; 1 -> 2 -> 3; 1 -> 4
        correrCaso("Caso 1: Árbol con múltiples ramas y raíces", 
                   5, new int[]{0, -1, 1, 2, 1, -1}, 3);

        // Caso 2: Solo un socio
        correrCaso("Caso 2: N = 1 (nodo raíz único)", 
                   1, new int[]{0, -1}, 1);

        // Caso 3: Todos son raíces independientes
        correrCaso("Caso 3: Nodos completamente aislados (altura 1)", 
                   4, new int[]{0, -1, -1, -1, -1}, 1);

        // Caso 4: Cadena lineal simple (1 -> 2 -> 3 -> 4)
        correrCaso("Caso 4: Cadena puramente lineal", 
                   4, new int[]{0, -1, 1, 2, 3}, 4);

        // Caso 5: Formato estrella (un jefe con todos los subordinados directos)
        correrCaso("Caso 5: Estrella / Todos reportan a la raíz", 
                   5, new int[]{0, -1, 1, 1, 1, 1}, 2);

        // Caso 6: Dos bosques separados con alturas desiguales
        // Árbol 1: 1 -> 2 (altura 2)
        // Árbol 2: 3 -> 4 -> 5 -> 6 (altura 4)
        correrCaso("Caso 6: Bosques dispares (gana el segundo)", 
                   6, new int[]{0, -1, 1, -1, 3, 4, 5}, 4);

        // Caso 7: Orden invertido en los índices (un jefe tiene índice mayor que el subordinado)
        // 3 es raíz; 2 reporta a 3; 1 reporta a 2 -> 3 -> 2 -> 1 (altura 3)
        correrCaso("Caso 7: Jerarquía en orden de índices no secuencial", 
                   3, new int[]{0, 2, 3, -1}, 3);

        System.out.println("=== Fin de la ejecución ===");
    }
}
