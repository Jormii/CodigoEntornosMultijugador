package EM_Tema5.tema5;

public class Reclutador {

	private final String NOMBRE;
	private CampoEntrenamiento campo;

	public Reclutador(String nombre, CampoEntrenamiento _campo) {
		NOMBRE = nombre;
		campo = _campo;
	}

	public void exec() {
		new Thread(() -> reclutar()).start();
	}

	private void reclutar() {
		while (true) {
			try {
				Thread.sleep((long) (Math.random() * 3000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			String nombreSoldado = Soldado.obtenerNombre();
			String apellidoSoldado = Soldado.obtenerApellido();
			Soldado soldadoReclutado = new Soldado(nombreSoldado + " " + apellidoSoldado);
			campo.anadirSoldadoFormacion(soldadoReclutado);
			System.out.format("El reclutador %s ha reclutado al soldado %s\n", NOMBRE, soldadoReclutado.getNombre());
		}
	}

	// Getters, setters y toString
	public String getNombre() {
		return NOMBRE;
	}
}
