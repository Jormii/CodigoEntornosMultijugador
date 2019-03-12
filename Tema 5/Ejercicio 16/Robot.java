package EM_Tema5.tema5;

public class Robot {

	private int fabricaAsignada;
	private Mecha mecha;

	public Robot(int idFabrica, Mecha _mecha) {
		fabricaAsignada = idFabrica;
		mecha = _mecha;
	}

	public void exec() {
		new Thread(() -> algoritmo()).start();
	}

	private void algoritmo() {
		int nPiezas = Mecha.Pieza.N_PIEZAS.ordinal();
		for (int i = 0; i < nPiezas; ++i) {
			Mecha.Pieza piezaRecogida = recogerPieza(i);
			montarPieza(piezaRecogida);
		}
		System.out.format("***\nSe ha montado el mecha %s\n***\n", mecha);
	}

	private Mecha.Pieza recogerPieza(int idPieza) {
		Mecha.Pieza piezaARecoger = Mecha.Pieza.values()[idPieza];
		return Fabrica.obtenerFabricaPorId(fabricaAsignada).recogerPieza(piezaARecoger);
	}

	private void montarPieza(Mecha.Pieza pieza) {
		try {
			System.out.format("Instalando la pieza %s en el mecha %d\n", pieza, mecha.getId());
			mecha.instalarPieza(pieza);
			Thread.sleep((long)(Math.random() * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
