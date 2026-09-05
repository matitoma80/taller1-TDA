import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class esquema_piramidal {
    // declaro la segunda lista 
    static List<Integer>[] segunda_lista;

    // DFS que calcula la profundidad máxima de un árbol
    static int dfs(int socio) {
        int max_subordinado = 0;
        // recorro los subordinados del socio
        for (int subordinado : segunda_lista[socio]) {
            max_subordinado = Math.max(max_subordinado, dfs(subordinado));
        }
        // devuelvo su cadena mas larga
        return 1 + max_subordinado;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] primera_lista = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            primera_lista[i] = sc.nextInt();
        }

        //creo las una lista para cada socio
        segunda_lista = new ArrayList[n + 1];
        for (int j = 1; j <= n; j++) {
            segunda_lista[j] = new ArrayList<>();
        }
        List<Integer> raices = new ArrayList<>();

        //a cada socio segunda lista [j], le agrego su subordinado
        for (int k = 1; k <= n; k++) {
            if (primera_lista[k] == -1) {
                raices.add(k);
            } else {
                segunda_lista[primera_lista[k]].add(k);
            }
        }

        int max_mesas = 0;
        for (int raiz : raices) {
            max_mesas = Math.max(max_mesas, dfs(raiz));
        }

        System.out.println(max_mesas);
    }
}
