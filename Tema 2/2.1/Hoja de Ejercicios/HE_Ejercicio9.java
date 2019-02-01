package tema2_1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class HE_Ejercicio9 {

	private static final int N_FRAGMENTOS = 10;
	private static final int N_HILOS = 3;

	private static volatile int[] fichero;
	private static volatile int fragmentoADescargar;
	private static volatile int fragmentosDescargados; // Para el apartado b)

	private static int descargaDatos(int numFragmento) {
		sleepRandom(1000);
		return numFragmento * 2;
	}

	private static void mostrarFichero() {
		println("--------------------------------------------------");
		print("File = [");
		for (int i = 0; i < N_FRAGMENTOS; i++) {
			print(fichero[i] + ",");
		}
		println("]");
	}

	public static void downloader(int id) {
		while (true) {
			enterMutex();
			int idxFragmento = fragmentoADescargar++;
			exitMutex();

			if (idxFragmento >= N_FRAGMENTOS) {
				break;
			}
			System.out.format("El hilo %d va a descargar el fragmento %d\n", id, idxFragmento);

			fichero[idxFragmento] = descargaDatos(idxFragmento);
			System.out.format("El hilo %d ha descargado el fragmento %d\n", id, idxFragmento);
			
			// Principio apartado b)
			enterMutex();
			fragmentosDescargados++;

			if (fragmentosDescargados == N_FRAGMENTOS) {
				System.out.format("El hilo %d ha descargado el ultimo fragmento\n", id);
				mostrarFichero();
			}
			exitMutex();
			// Fin apartado b)
		}
	}

	public static void main(String[] args) {
		fichero = new int[N_FRAGMENTOS];
		fragmentoADescargar = 0;
		fragmentosDescargados = 0;

		for (int i = 0; i < N_HILOS; i++) {
			createThread("downloader", i);
		}
		startThreadsAndWait();

		// Principio apartado a)
		// mostrarFichero();
		// Fin apartado a)
	}

}
