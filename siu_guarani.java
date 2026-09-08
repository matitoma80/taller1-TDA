import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Queue;

public class siu_guarani {
    public String siu_guarani() {
        Scanner sc = new Scanner(System.in);
        
        int cantidad_estudiantes = sc.nextInt();
        
        //crear una lista de tamaño catidad_estudiantes y cada elemento tiene un apellido
        String[] estudiantes = new String[cantidad_estudiantes];

        for (int i = 0; i < cantidad_estudiantes; i++) {
            estudiantes[i] = sc.next();
        }

        //lista de pares
        List<List<Character>> lista_pares = new ArrayList<>();

        // 26 casilleros (uno por cada letra 'a'-'z')
        List<Character>[] orden = new ArrayList[26];
            for (int i = 0; i < 26; i++) {
                orden[i] = new ArrayList<>();
            }
        
        //DFS (0: no visto, 1: en recursión, 2: procesado)
        int[] visitado = new int[26];

        //contenedor de la respuesta final de 26 letras
        List<Character> resultado = new ArrayList<>(); 

        for (int i = 0; i < cantidad_estudiantes - 1; i++) {
            String p1 = estudiantes[i];
            String p2 = estudiantes[i + 1];

            int minLen = Math.min(p1.length(), p2.length());
            boolean difiere = false;

            for (int j = 0; j < minLen; j++) {
                char c1 = p1.charAt(j);
                char c2 = p2.charAt(j);

                if (c1 != c2) {
                    //encuentro la primera diferencia: c1 debe ir antes que c2
                    List<Character> par = new ArrayList<>();
                    par.add(c1);
                    par.add(c2);
                    lista_pares.add(par);

                    difiere = true;
                    break;
                }
            }

            //si no hubo letras distintas pero la primera palabra es más larga
            if (!difiere && p1.length() > p2.length()) {
                return "Impossible";
            }
        }

        //recorro cada par deducido y lo vuelco a la lista de orden
        for (List<Character> par : lista_pares) {
            char origen = par.get(0);
            char destino = par.get(1);

        //con esto obtengo el indice de la letra
        int u = origen - 'a';

        //agrego la arista dirigida: 'origen' apunta a 'destino'
        orden[u].add(destino);
        }

        // DFS sobre las 26 letras
        for (int i = 0; i < 26; i++) {
            char letra = (char) ('a' + i);
            if (visitado[i] == 0) {
                boolean sinCiclo = dfs(letra, orden, visitado, resultado);
                if (!sinCiclo) {
                    return "Impossible";
                }
            }
        }

        // 4. Invertir y armar el String
        Collections.reverse(resultado);

        StringBuilder respuesta = new StringBuilder();
        for (char c : resultado) {
            respuesta.append(c);
        }

        return respuesta.toString();
    }

    //funcion auxiliar DFS
    private boolean dfs(char actual, List<Character>[] orden, int[] visitado, List<Character> resultado) {
        int u = actual - 'a';
        visitado[u] = 1;

        for (char vecino : orden[u]) {
            int v = vecino - 'a';
            
            // Si nos cruzamos con uno en el camino actual -> ciclo
            if (visitado[v] == 1) {
                return false;
            }
            // Si aún no lo visitamos, seguimos caminando
            if (visitado[v] == 0) {
                if (!dfs(vecino, orden, visitado, resultado)) {
                    return false;
                }
            }
        }

        visitado[u] = 2;
        resultado.add(actual);
        return true;
    }
}
