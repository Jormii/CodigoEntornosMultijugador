package tema2_1;

import static es.urjc.etsii.code.concurrency.SimpleConcurrent.*;

public class HE_Ejercicio10 {

	static volatile boolean bImpreso;
	static volatile boolean cImpreso;
	static volatile boolean dImpreso;
	static volatile boolean fImpreso;
	static volatile boolean gImpreso;

	public static void proceso1() {
		while (!cImpreso && !fImpreso) {
			;
		}
		System.out.format("A");
		System.out.format("B");
		bImpreso = true;
	}

	public static void proceso2() {
		System.out.format("C");
		cImpreso = true;
		System.out.format("D");
		dImpreso = true;
		while (!bImpreso && !gImpreso) {
			;
		}
		System.out.format("E");
	}

	public static void proceso3() {
		System.out.format("F");
		fImpreso = true;
		while (!dImpreso) {
			;
		}
		System.out.format("G");
		gImpreso = true;
	}

	public static void main(String[] args) {
		bImpreso = false;
		cImpreso = false;
		dImpreso = false;
		fImpreso = false;
		gImpreso = false;
		
		createThread("proceso1");
		createThread("proceso2");
		createThread("proceso3");

		startThreadsAndWait();
	}

}
