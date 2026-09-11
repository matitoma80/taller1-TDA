import java.util.ArrayList;
import java.util.Scanner;

public class siu_guarani {
    public static void main(String[] args){        
        Scanner sc = new Scanner(System.in);

        //tomo la primera entrada como la cantidad de apellidos
        int cantidad_apellidos = sc.nextInt();

        //creo la lista de apellidos y los ingreso
        String[] apellidos = new String[cantidad_apellidos];
        for (int i = 0; i < cantidad_apellidos; i++){
            apellidos[i] = sc.next();
        }

        sc.close();

        //defino el grafo de nodo letras
        ArrayList<Character>[] grafo = new ArrayList[26];
        for (int i = 0; i < 26; i++){
            grafo[i] = new ArrayList<>();
        }

        //defino la lista de la catidad de aristas dirigidas de cada letra
        int[] aristas = new int[26];

        char[] resultado = new char[26];

        //lleno el grafo de letras
        for (int i = 0; i < cantidad_apellidos - 1; i++){
            String actual = apellidos[i];
            String siguiente = apellidos[i + 1];

            int min_longitud = Math.min(actual.length(), siguiente.length());
            boolean encontrada_diferencia = false;

            for (int k = 0; k < min_longitud; k++){
                char c1 = actual.charAt(k);
                char c2 = siguiente.charAt(k);

                if (c1 != c2){
                    grafo[c1 - 'a'].add(c2);
                    aristas[c2 - 'a']++;
                    encontrada_diferencia = true;
                    break;
                }
            }

            if (!encontrada_diferencia && actual.length() > siguiente.length()){
                System.out.println("Impossible");
                return;
            }
        }

        //ingreso las letras a resultado
        int indice = 0;
        boolean hay_grado0 = true;

        while(hay_grado0){
            int pos = -1;
            for (int i = 0; i < aristas.length; i++) {
                if (aristas[i] == 0){
                    pos = i;
                    break;
                }
            }

            if (pos != -1){
                char letra_encontrada = (char) ('a' + pos);
                resultado[indice] = letra_encontrada;
                indice++;
                aristas[pos] = -1;

                for (char letra_adyacente : grafo[pos]) {
                    aristas[letra_adyacente - 'a']--;
                }
            } else {
                hay_grado0 = false;
            }
        }

        // si quedaron letras sin procesar o con dependencias, hay ciclo
        if (indice < 26) {
            System.out.println("Impossible");
            return;
        }

        System.out.println(new String(resultado));
        return;
    }

}