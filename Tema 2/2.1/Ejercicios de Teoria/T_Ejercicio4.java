package tema2_1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class T_Ejercicio4 {

	static volatile Queue<Integer> colaEnvios;
	static volatile Queue<Integer> colaDevoluciones;

	public static void cliente() {
		while (true) {
			int valor = new Random().nextInt(10);
			colaEnvios.add(valor);
			System.out.format("El cliente ha creado el valor %d\n", valor);

			while (colaDevoluciones.peek() != null) {
				System.out.format("Valor devuelto por el servidor: %d\n", colaDevoluciones.remove());
			}
			//sleep(1);		// Pequeña pausa para poder visualizar los resultados
		}
	}

	public static void servidor() {
		while (true) {
			while (colaEnvios.peek() != null) {
				int valorDevuelto = colaEnvios.remove() + 1;
				colaDevoluciones.add(valorDevuelto);
			}
		}
	}

	public static void main(String[] args) {
		colaEnvios = new LinkedList<Integer>();
		colaDevoluciones = new LinkedList<Integer>();

		createThread("cliente");
		createThread("servidor");

		startThreadsAndWait();
	}

}
