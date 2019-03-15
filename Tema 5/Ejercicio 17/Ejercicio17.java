package EM_Tema5.tema5;

public class Ejercicio17 {

	public static void main(String[] args) {
		CampoEntrenamiento campo = new CampoEntrenamiento(25);

		new Sargento("ANTONIO", campo).exec();
		new Sargento("JOSE", campo).exec();
		new Sargento("MANUEL", campo).exec();

		new Reclutador("FRANCISCO", campo).exec();
		new Reclutador("DAVID", campo).exec();

		new Instructor("JUAN", campo).exec();
		new Instructor("JOSE ANTONIO", campo).exec();
	}

}
