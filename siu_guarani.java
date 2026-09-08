import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    String firstLine = br.readLine();
    if (firstLine == null || firstLine.trim().isEmpty()) return;
    int n = Integer.parseInt(firstLine.trim());

    String[] apellidos = new String[n];
    for (int i = 0; i < n; i++) {
        apellidos[i] = br.readLine().trim();
    }

    // 1. Inicializar lista de adyacencia y grados de entrada
    List<Integer>[] adj = new ArrayList[26];
    for (int i = 0; i < 26; i++) {
        adj[i] = new ArrayList<>();
    }
    int[] inDegree = new int[26];

    // 2. Comparar apellidos consecutivos para construir las restricciones
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

                // Evitar aristas repetidas que inflarían inDegree incorrectamente
                if (!adj[u].contains(v)) {
                    adj[u].add(v);
                    inDegree[v]++;
                }
                diferenciaEncontrada = true;
                break;
            }
        }

        // Caso borde: un prefijo más largo nunca puede ir antes que uno más corto (ej. "rocas" antes que "roca")
        if (!diferenciaEncontrada && s1.length() > s2.length()) {
            posible = false;
            break;
        }
    }

    if (!posible) {
        System.out.println("Impossible");
        return;
    }

    // 3. Algoritmo de Kahn
    Queue<Integer> cola = new LinkedList<>();

    // Encolar todas las letras sin dependencias (inDegree == 0)
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

    // 4. Si se procesaron las 26 letras, no hay ciclo y el orden es válido
    if (resultado.length() == 26) {
        System.out.println(resultado.toString());
    } else {
        System.out.println("Impossible");
    }
}