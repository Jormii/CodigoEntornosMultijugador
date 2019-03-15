package EM_Tema5.tema5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class CampoEntrenamiento {

	private static final Semaphore SEM_ELEM_LISTA = new Semaphore(0);

	private List<Soldado> soldadosFormacion;
	private BlockingQueue<Soldado> soldadosEntrenados;

	public CampoEntrenamiento(int maxSoldadosEntrenados) {
		soldadosFormacion = new ArrayList<>();
		soldadosEntrenados = new LinkedBlockingQueue<>(maxSoldadosEntrenados);
	}

	public Soldado obtenerSoldado() {
		SEM_ELEM_LISTA.acquireUninterruptibly();

		synchronized (soldadosFormacion) {
			int idx = new Random().nextInt(soldadosFormacion.size());
			return soldadosFormacion.remove(idx);
		}
	}

	public void anadirSoldadoFormacion(Soldado s) {
		synchronized (soldadosFormacion) {
			soldadosFormacion.add(s);
			SEM_ELEM_LISTA.release(1);
		}
	}

	public Soldado reclutarSoldado() {
		Soldado s = null;
		try {
			s = soldadosEntrenados.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return s;
	}

	public void anadirSoldadoEntrenado(Soldado s) {
		try {
			soldadosEntrenados.put(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
