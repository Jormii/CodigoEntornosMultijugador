package EM_Tema5.tema5;

public class Maquina {

	private int fabricaAsignada;
	private Mecha.Pieza piezaAFabricar;
	private boolean running;

	public Maquina(int idFabrica, Mecha.Pieza pieza) {
		fabricaAsignada = idFabrica;
		piezaAFabricar = pieza;
		running = true;
	}

	public void exec() {
		new Thread(() -> algoritmo()).start();
	}

	private void algoritmo() {
		while (running) {
			Mecha.Pieza piezaFabricada = fabricarPieza();
			almacenarPieza(fabricaAsignada, piezaFabricada);
		}
		System.out.format("La maquina de la fabrica %d responsable de la pieza %s se ha detenido\n", fabricaAsignada,
				piezaAFabricar.name());
		Thread.currentThread().interrupt();
	}

	private Mecha.Pieza fabricarPieza() {
		Mecha.Pieza piezaFabricada = piezaAFabricar;
		try {
			System.out.format("La maquina en la fabrica %d esta creando la pieza %s\n", fabricaAsignada, piezaFabricada);
			Thread.sleep((long) (Math.random() * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return piezaFabricada;
	}

	private void almacenarPieza(int idFabrica, Mecha.Pieza pieza) {
		Fabrica.obtenerFabricaPorId(idFabrica).almacenarPieza(pieza);
	}
	
	public void detenerMaquina() {
		running = false;
	}

	// Getters y setters
	public Mecha.Pieza getPieza() {
		return piezaAFabricar;
	}

	public int getFabricaAsignada() {
		return fabricaAsignada;
	}

}
