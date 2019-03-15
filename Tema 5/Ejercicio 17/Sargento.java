package EM_Tema5.tema5;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.util.Iterator;

public class Sargento {

	private static final int SOLDADOS_POR_PELOTON = 10;
	private static final int UMBRAL_RECLUTAMIENTO = 8;
	private static final int PERMISOS = 2; // Para que partan en mision la mitad de los pelotones, redondeado a la baja

	private static Semaphore semPartirMision = new Semaphore(0);

	private final String NOMBRE;
	private CampoEntrenamiento campo;
	private List<Soldado> peloton;

	public Sargento(String nombre, CampoEntrenamiento _campo) {
		NOMBRE = nombre;
		campo = _campo;
		peloton = new ArrayList<>(SOLDADOS_POR_PELOTON);
		semPartirMision.release(); // Anade un permiso al semaforo, indicando que hay un nuevo peloton
	}

	public void exec() {
		new Thread(() -> algoritmo()).start();
	}

	private void algoritmo() {
		while (true) {
			if (pelotonEstaListo()) {
				realizarMision();
				descansar();
			} else {
				Soldado soldadoReclutado = esperarSoldados();
				reclutarSoldado(soldadoReclutado);
			}
		}
	}

	private boolean pelotonEstaListo() {
		return peloton.size() == SOLDADOS_POR_PELOTON;
	}

	private void realizarMision() {
		semPartirMision.acquireUninterruptibly(PERMISOS);
		System.out.format("El peloton del sargento %s parte de mision. Peloton: %s\n", NOMBRE, peloton);

		try {
			Thread.sleep((long) (Math.random() * 5000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (Iterator<Soldado> it = peloton.iterator(); it.hasNext();) {
			Soldado s = it.next();
			if (Math.random() < Soldado.PROBABILIDAD_MUERTE) {
				it.remove();
				System.out.format("El soldado %s del peloton del sargento %s ha muerto\n", s.getNombre(), NOMBRE);
			}
		}

		System.out.format("El peloton del sargento %s ha regresado de la mision\n", NOMBRE);
		semPartirMision.release(PERMISOS);
	}

	private void descansar() {
		try {
			Thread.sleep((long) (Math.random() * 5000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Soldado esperarSoldados() {
		return campo.reclutarSoldado();
	}

	private void reclutarSoldado(Soldado soldado) {
		peloton.add(soldado);
	}

	// Getters, setters y toString
	public String getNombre() {
		return NOMBRE;
	}
}
