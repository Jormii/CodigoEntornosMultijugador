package tema2_1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class T_Ejercicio4b {

	static final int NUMERO_CLIENTES = 5;
	static volatile Queue<Integer>[] colaEnvios;
	static volatile Queue<Integer>[] colaDevoluciones;

	public static void cliente(int id) {
		while (true) {
			int unidades = new Random().nextInt(10);
			int decenas = 10 * id;
			int valor = decenas + unidades;
			colaEnvios[id].add(valor);
			System.out.format("El cliente %d ha creado el valor %d\n", id, valor);

			while (colaDevoluciones[id].peek() != null) {
				System.out.format("Valor devuelto por el servidor al cliente %d: %d\n", id, colaDevoluciones[id].remove());
			}
			// sleep(1);		// Pequeña pausa para poder visualizar los resultados
		}
	}

	public static void servidor() {
		while (true) {
			for (int i = 0; i < NUMERO_CLIENTES; i++) {
				while (colaEnvios[i].peek() != null) {
					int valorDevuelto = colaEnvios[i].remove() + 1;
					System.out.format("El servidor devuelve al cliente %d el valor %d\n", i, valorDevuelto);
					colaDevoluciones[i].add(valorDevuelto);
				}
			}
		}
	}

	public static void main(String[] args) {
		colaEnvios = new Queue[NUMERO_CLIENTES];
		colaDevoluciones = new Queue[NUMERO_CLIENTES];
				
		for (int i = 0; i < NUMERO_CLIENTES; i++) {
			createThread("cliente", i);
			colaEnvios[i] = new LinkedList<Integer>();
			colaDevoluciones[i] = new LinkedList<Integer>();
		}
		createThread("servidor");

		startThreadsAndWait();
	}

}
