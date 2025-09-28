package main;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Medico implements Runnable {
    private final CentroMedico centroMedico;

    private final FilaDeEspera filaPacientes;

    private final String nombre;

    Object lock = new Object();

    private final int id;

    public Medico(CentroMedico centroMedico, String nombre, FilaDeEspera filaPacientes, int id) {
        this.centroMedico = centroMedico;
        this.nombre = nombre;
        this.filaPacientes = filaPacientes;
        this.id = id;
    }


    @Override
    public void run() {
        try {
            while (true) {
                if (centroMedico.tocaVIP() || !tengoPacientes() ) {
                    Paciente paciente = centroMedico.obtenerPacienteVip();
                    atenderPaciente(paciente);
                } else {
                    Paciente paciente = filaPacientes.obtenerPaciente();
                    atenderPaciente(paciente);
                    centroMedico.registrarAtencion();
                }
            }
        } catch (InterruptedException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void atenderPaciente(Paciente paciente) throws InterruptedException {
        Random random = new Random();
        System.out.println(nombre + " comienza a atender a " + paciente.obtenerNombre());

        int tiempoAtencion = random.nextInt(10) + 1;
        Thread.sleep(tiempoAtencion * 2000);

        System.out.println( nombre + " termino con " + paciente.obtenerNombre() + " en " + tiempoAtencion + " segundos.");
    }

    public boolean tengoPacientes() {
        boolean retorno = false;
        if (filaPacientes.obtenerCantidad() == 0){
            retorno = true;
        }
        return retorno;
    }

}
