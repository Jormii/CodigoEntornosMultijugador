package tema2_1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;
import java.util.Random;

public class HE_Ejercicio11 {

	static volatile int valorClienteAProxy;
	static volatile int valorServidorAProxy;
	static volatile int valorProxyACliente;
	static volatile int valorProxyAServidor;
	static volatile boolean peticionCliente = false;
	static volatile boolean peticionProxy = false;
	static volatile boolean respuestaProxy = false;
	static volatile boolean respuestaServidor = false;

	public static void cliente() {
		valorClienteAProxy = new Random().nextInt(10);
		System.out.format("El cliente ha creado el valor %d\n", valorClienteAProxy);
		peticionCliente = true;

		while (!respuestaProxy) {
			;
		}
		System.out.format("El cliente ha recibido del proxy el valor %d\n", valorProxyACliente);
	}

	public static void proxy() {
		while (!peticionCliente) {
			;
		}
		valorProxyAServidor = ++valorClienteAProxy;
		peticionProxy = true;

		while (!respuestaServidor) {
			;
		}
		valorProxyACliente = valorServidorAProxy;
		respuestaProxy = true;
	}

	public static void servidor() {
		while (!peticionProxy) {
			;
		}
		valorServidorAProxy = ++valorProxyAServidor;
		respuestaServidor = true;
	}
	
	public static void main(String[] args) {
		createThread("cliente");
		createThread("proxy");
		createThread("servidor");
		
		startThreadsAndWait();
	}

}
