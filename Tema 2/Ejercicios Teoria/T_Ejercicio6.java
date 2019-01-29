package tema2_1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class T_Ejercicio6 {

	static final int TOTAL_VISITANTES = 15;
	static volatile int visitantesEnElMuseo;

	public static void persona(String nombre) {
		while (true) {
			enterMutex();
			System.out.format("%s: ¡Hola a las %d personas!\n", nombre, visitantesEnElMuseo++);
			exitMutex();

			sleepRandom(100);
			
			enterMutex();
			System.out.format("%s: ¡Adiós a las %d personas!\n", nombre, --visitantesEnElMuseo);
			exitMutex();

			sleepRandom(100);
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < TOTAL_VISITANTES; i++) {
			createThread("persona", Character.toString((char) ('A' + i)));
		}
		startThreadsAndWait();
	}

}
