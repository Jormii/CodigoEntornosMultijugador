package tema2_1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class T_Ejercicio7 {

	static final int TOTAL_VISITANTES = 3;
	static volatile HashMap<String, Boolean> personasConRegalo;
	static volatile ArrayList<String> visitantesEnElMuseo;

	public static void persona(String nombre) {
		while (true) {
			enterMutex();
			if (visitantesEnElMuseo.size() == 0 && !personasConRegalo.get(nombre)) {
				System.out.format("[%s recibe un regalo]\n", nombre);
				personasConRegalo.put(nombre, true);
			}
			System.out.format("%s: ¡Hola a las %d personas!\n", nombre, visitantesEnElMuseo.size());
			visitantesEnElMuseo.add(nombre);
			exitMutex();

			for (String p : visitantesEnElMuseo) {
				if (!p.equals(nombre)) {
					boolean tieneRegalo = personasConRegalo.get(p);
					String str = (tieneRegalo) ? "Tengo" : "No tengo";
					System.out.format("%s: %s regalo\n", p, str);
				}
			}

			sleepRandom(100);

			enterMutex();
			System.out.format("%s: ¡Adiós a las %d personas!\n", nombre, visitantesEnElMuseo.size());
			visitantesEnElMuseo.remove(nombre);
			exitMutex();

			sleepRandom(100);
		}
	}

	public static void main(String[] args) {
		visitantesEnElMuseo = new ArrayList<String>(TOTAL_VISITANTES);
		personasConRegalo = new HashMap<>(TOTAL_VISITANTES);
		for (int i = 0; i < TOTAL_VISITANTES; i++) {
			String nombre = Character.toString((char) ('A' + i));
			createThread("persona", nombre);
			personasConRegalo.put(nombre, false);
		}
		startThreadsAndWait();
	}

}
