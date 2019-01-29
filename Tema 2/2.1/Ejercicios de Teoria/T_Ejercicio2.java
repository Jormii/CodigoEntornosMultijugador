package tema2_1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class T_Ejercicio2 {

	public static final int VALORES_A_PRODUCIR = 5;
	public static volatile Queue<Integer> colaValoresProducidos;

	public static void productor() {
		for (int i = 0; i < VALORES_A_PRODUCIR; i++) {
			int valorProducido = new Random().nextInt(VALORES_A_PRODUCIR);
			colaValoresProducidos.add(valorProducido);
			System.out.format("El productor ha producido el valor %d\n", valorProducido);
		}
	}

	public static void consumidor() {
		for (int i = 0; i < VALORES_A_PRODUCIR; i++) {
			while (colaValoresProducidos.isEmpty()) {
				;
			}
			System.out.format("El consumidor ha consumido el valor %d\n", colaValoresProducidos.remove());
		}
	}

	public static void main(String[] args) {
		colaValoresProducidos = new LinkedList<Integer>();

		createThread("productor");
		createThread("consumidor");

		startThreadsAndWait();
	}

}
