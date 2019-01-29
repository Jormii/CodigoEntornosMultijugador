package tema2_1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;
import java.util.Random;

public class T_Ejercicio3 {

	static final int NOP = -1;
	static final int CREAR_NUMERO = 0;

	static volatile int valorEnviado;
	static volatile int valorDevuelto;
	static volatile int operacionServidor;
	static volatile boolean servidorHaRespondido;

	public static void cliente() {
		valorEnviado = new Random().nextInt(10);
		System.out.format("El cliente ha enviado el valor %d\n", valorEnviado);

		operacionServidor = CREAR_NUMERO;
		while (!servidorHaRespondido) {
			;
		}
		System.out.format("El cliente ha recibido del servidor el valor: %d\n", valorDevuelto);
	}

	public static void servidor() {
		while (operacionServidor == NOP) {
			;
		}

		valorDevuelto = valorEnviado + 1;
		servidorHaRespondido = true;
	}

	public static void main(String[] args) {
		operacionServidor = NOP;
		servidorHaRespondido = false;

		createThread("cliente");
		createThread("servidor");

		startThreadsAndWait();
	}

}
