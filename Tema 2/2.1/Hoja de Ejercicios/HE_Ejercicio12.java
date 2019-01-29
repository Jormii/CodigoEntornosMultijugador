package tema2_1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;

public class HE_Ejercicio12 {

	static final int NUMERO_PETICIONES = 10;
	static volatile Queue<Integer> colaClienteProxy;
	static volatile Queue<Integer> colaProxyServidor;
	static volatile Queue<Integer> colaServidorProxy;
	static volatile Queue<Integer> colaProxyCliente;

	public static void cliente() {
		for (int i = 0; i < NUMERO_PETICIONES; i++) {
			int valor = new Random().nextInt(NUMERO_PETICIONES);
			System.out.format("El cliente envia al proxy el valor %d\n", valor);
			colaClienteProxy.add(valor);
		}
		
		for (int i = 0; i < NUMERO_PETICIONES; i++) {
			while (colaProxyCliente.peek() == null) {
				;
			}
			System.out.format("El cliente recibe del proxy el valor %d\n", colaProxyCliente.remove());
		}
	}

	public static void proxy() {
		for (int i = 0; i < NUMERO_PETICIONES; i++) {
			while (colaClienteProxy.peek() == null) {
				;
			}
			colaProxyServidor.add(colaClienteProxy.remove() + 1);
		}

		for (int i = 0; i < NUMERO_PETICIONES; i++) {
			while (colaServidorProxy.peek() == null) {
				;
			}
			colaProxyCliente.add(colaServidorProxy.remove());
		}
	}

	public static void servidor() {
		for (int i = 0; i < NUMERO_PETICIONES; i++) {
			while (colaProxyServidor.peek() == null) {
				;
			}
			colaServidorProxy.add(colaProxyServidor.remove() + 1);
		}
	}

	public static void main(String[] args) {
		colaClienteProxy = new LinkedList<Integer>();
		colaProxyServidor = new LinkedList<Integer>();
		colaServidorProxy = new LinkedList<Integer>();
		colaProxyCliente = new LinkedList<Integer>();

		createThread("cliente");
		createThread("proxy");
		createThread("servidor");

		startThreadsAndWait();
	}

}
