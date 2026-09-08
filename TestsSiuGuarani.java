import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TestsSiuGuarani {

    // Función que implementa la solución con Kahn
    public static String resolverSiuGuarani(int n, String[] apellidos) {
        List<Integer>[] adj = new ArrayList[26];
        for (int i = 0; i < 26; i++) {
            adj[i] = new ArrayList<>();
        }
        int[] inDegree = new int[26];

        boolean posible = true;

        for (int i = 0; i < n - 1; i++) {
            String s1 = apellidos[i];
            String s2 = apellidos[i + 1];

            int minLen = Math.min(s1.length(), s2.length());
            boolean diferenciaEncontrada = false;

            for (int k = 0; k < minLen; k++) {
                if (s1.charAt(k) != s2.charAt(k)) {
                    int u = s1.charAt(k) - 'a';
                    int v = s2.charAt(k) - 'a';

                    if (!adj[u].contains(v)) {
                        adj[u].add(v);
                        inDegree[v]++;
                    }
                    diferenciaEncontrada = true;
                    break;
                }
            }

            // Caso borde: prefijo más largo antes que uno más corto
            if (!diferenciaEncontrada && s1.length() > s2.length()) {
                posible = false;
                break;
            }
        }

        if (!posible) {
            return "Impossible";
        }

        Queue<Integer> cola = new LinkedList<>();
        for (int i = 0; i < 26; i++) {
            if (inDegree[i] == 0) {
                cola.add(i);
            }
        }

        StringBuilder resultado = new StringBuilder();
        while (!cola.isEmpty()) {
            int u = cola.poll();
            resultado.append((char) ('a' + u));

            for (int v : adj[u]) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    cola.add(v);
                }
            }
        }

        return resultado.length() == 26 ? resultado.toString() : "Impossible";
    }

    // Validador auxiliar: comprueba si la lista queda ordenada con el abecedario dado
    private static boolean esOrdenValido(String abecedario, String[] apellidos) {
        if (abecedario.length() != 26) return false;

        int[] rank = new int[26];
        for (int i = 0; i < 26; i++) {
            rank[abecedario.charAt(i) - 'a'] = i;
        }

        for (int i = 0; i < apellidos.length - 1; i++) {
            String s1 = apellidos[i];
            String s2 = apellidos[i + 1];
            int len = Math.min(s1.length(), s2.length());
            boolean dif = false;

            for (int k = 0; k < len; k++) {
                if (s1.charAt(k) != s2.charAt(k)) {
                    if (rank[s1.charAt(k) - 'a'] > rank[s2.charAt(k) - 'a']) {
                        return false; // Violó el orden
                    }
                    dif = true;
                    break;
                }
            }

            if (!dif && s1.length() > s2.length()) {
                return false;
            }
        }
        return true;
    }

    private static void correrCaso(String nombreTest, int n, String[] apellidos, boolean debeSerPosible) {
        String resultado = resolverSiuGuarani(n, apellidos);

        if (!debeSerPosible) {
            if ("Impossible".equals(resultado)) {
                System.out.printf("[✓ PASÓ] %s -> Correctamente identificado como 'Impossible'\n", nombreTest);
            } else {
                System.out.printf("[✗ FALLÓ] %s -> Se esperaba 'Impossible' pero devolvió: %s\n", nombreTest, resultado);
            }
        } else {
            if (!"Impossible".equals(resultado) && esOrdenValido(resultado, apellidos)) {
                System.out.printf("[✓ PASÓ] %s -> Abecedario válido generado: %s\n", nombreTest, resultado);
            } else {
                System.out.printf("[✗ FALLÓ] %s -> Salida inválida o 'Impossible' inesperado (%s)\n", nombreTest, resultado);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Ejecutando Tests para SIU Guaraní ===");

        // Caso 1: Ejemplo del enunciado
        correrCaso("Caso 1: Ejemplo enunciado (roca, sarmiento, mitre)",
                   3,
                   new String[]{"roca", "sarmiento", "mitre"},
                   true);

        // Caso 2: Ciclo directo simple (a < b y b < a)
        correrCaso("Caso 2: Ciclo de 2 elementos (a < b y b < a)",
                   2,
                   new String[]{"ab", "aa"},
                   false);

        // Caso 3: Ciclo indirecto de 3 elementos (a < b, b < c, c < a)
        correrCaso("Caso 3: Ciclo indirecto (a -> b -> c -> a)",
                   3,
                   new String[]{"a", "b", "ab"}, // Aquí b < a por longitud de prefijo si difiere
                   false);

        // Caso 4: Prefijo más largo antes que prefijo corto (rocas antes que roca)
        correrCaso("Caso 4: Prefijo largo antes de prefijo corto (caso borde)",
                   2,
                   new String[]{"rocas", "roca"},
                   false);

        // Caso 5: Prefijo corto antes que prefijo largo (roca antes que rocas - siempre válido)
        correrCaso("Caso 5: Prefijo corto antes de largo",
                   2,
                   new String[]{"roca", "rocas"},
                   true);

        // Caso 6: N = 1 (una sola palabra, cualquier abecedario es válido)
        correrCaso("Caso 6: Un solo apellido (N = 1)",
                   1,
                   new String[]{"algoritmos"},
                   true);

        // Caso 7: Palabras ya ordenadas en abecedario tradicional
        correrCaso("Caso 7: Orden estándar inglés previo",
                   4,
                   new String[]{"alvarez", "bermudez", "carlos", "diaz"},
                   true);

        System.out.println("=== Fin de la ejecución ===");
    }
}
