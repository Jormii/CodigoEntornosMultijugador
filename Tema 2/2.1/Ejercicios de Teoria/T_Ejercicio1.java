package tema2_1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;
import java.util.Random;

public class T_Ejercicio1 {

	public static volatile int valorProducido;
	public static volatile boolean haSidoProducido;
	
	public static void productor() {
		valorProducido = new Random().nextInt(10);	// El numero aleatorio pertenece al intervalo [0, 10)
		System.out.format("El productor ha producido el valor %d\n", valorProducido);
		haSidoProducido = true;
	}
	
	public static void consumidor() {
		while (!haSidoProducido) {
			;
		}
		System.out.format("El consumidor ha consumido el valor %d\n", valorProducido);
	}
	
	public static void main(String[] args) {
		haSidoProducido = false;
		
		createThread("productor");
		createThread("consumidor");
		
		startThreadsAndWait();
	}

}
