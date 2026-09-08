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
        
        //idea: crear una lista de tamaño catidad_estudiantes y cada elemento tiene un apellido
        String[] estudiantes = new String[cantidad_estudiantes];
    
        for (int i = 0; i < cantidad_estudiantes; i++) {
            estudiantes[i] = sc.nextLine();
        }

        int apellido_mas_corto = estudiantes[0].length();
        for (String apellido : estudiantes) {
            if (apellido.length() < apellido_mas_corto){
                apellido_mas_corto = apellido.length();
            }
        }

        ArrayList<ArrayList<String>> orden_por_posiciones = new ArrayList<>();
        //idea: comparar indice a indice los apellidos y crear listas que tengan letras ordenadas

    }
}
