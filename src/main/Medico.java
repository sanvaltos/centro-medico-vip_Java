package main;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Medico implements Runnable {
    private final CentroMedico centroMedico;

    private final FilaDeEspera filaPacientes;

    private final String nombre;

    private final int id;

    public Medico(CentroMedico centroMedico, String nombre, FilaDeEspera filaPacientes, int id) {
        this.centroMedico = centroMedico;
        this.nombre = nombre;
        this.filaPacientes = filaPacientes;
        this.id = id;
    }


    @Override
    public void run() {
        Random random = new Random();
        try {
            while (true) {
                Paciente paciente = filaPacientes.obtenerPaciente();
                System.out.println(nombre + " comienza a atender a " + paciente.obtenerNombre());


                int tiempoAtencion = random.nextInt(10) + 1;
                Thread.sleep(tiempoAtencion * 2000);


                System.out.println( nombre + " termino con " + paciente.obtenerNombre() + " en " +
                        tiempoAtencion + " segundos.");
                
            }
        } catch (InterruptedException exc) {
            throw new RuntimeException(exc);
        }
    }

}
