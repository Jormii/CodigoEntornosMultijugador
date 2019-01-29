package tema2_1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class T_Ejercicio5 {

	public static void persona(String nombre) {
		while (true) {
			enterMutex();
			System.out.format("%s: ¡Hola!\n", nombre);
			System.out.format("%s: ¡Qué bonito!\n", nombre);
			System.out.format("%s: ¡Adiós!\n", nombre);
			exitMutex();
			
			System.out.format("%s está dando un paseo\n", nombre);
			sleepRandom(300);
		}
	}

	public static void main(String[] args) {
		createThread("persona", "Fulano");
		createThread("persona", "Mengano");
		createThread("persona", "Zutano");

		startThreadsAndWait();
	}

}
