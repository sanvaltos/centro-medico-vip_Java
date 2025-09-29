package main;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class CentroMedico {
    public static final int numMedicos = 4;

    public static final int numCajeros = 2;

    private static final int numPacientes = 50;

    private final Semaphore capacidadSala = new Semaphore(28);

    private final FilaDeEspera[] filasMedicos = new FilaDeEspera[numMedicos];

    private final FilaDeEspera filaPacientesVip = new FilaDeEspera();

    private final FilaDeEspera filaDeAtencion = new FilaDeEspera();

    Object lock = new Object();

    private final Medico[] medicos;

    private final Cajero[] cajeros;


    public CentroMedico() {
        cajeros = new Cajero[numCajeros];
        for (int i = 0; i < numCajeros; i++) {
            cajeros[i] = new Cajero(this, i);
        }

        for (int i = 0; i < numMedicos; i++) {
            filasMedicos[i] = new FilaDeEspera();
        }

        medicos = new Medico[numMedicos];
        for (int i = 0; i < numMedicos; i++) {
            medicos[i] = new Medico(this, "Medico " + (i + 1), filasMedicos[i], i );
        }
    }

    public void iniciar() {
        for (Cajero cajero : cajeros) {
            new Thread(cajero).start();
        }

        for (Medico medico : medicos) {
            new Thread(medico).start();
        }

        Random random = new Random();
        Boolean categoria;
        int consultorio;

        for (int i = 1; i <= numPacientes; i++) {
            categoria = random.nextBoolean();
            consultorio = random.nextInt(numMedicos);
            try {
                Paciente paciente;
                if (categoria) {
                    paciente = new Paciente("Paciente " + i, true, null);
                } else {
                    paciente = new Paciente("Paciente " + i, false, consultorio);
                }
                ingresarPacienteEntrada(paciente);

                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void ingresarPacienteEntrada(Paciente paciente) throws InterruptedException {
        capacidadSala.acquire();

        if (paciente.esVip()) {
            filaPacientesVip.agregarPaciente(paciente);
        } else {
            int medicoAsignado = paciente.obtenerTurno();
            filasMedicos[medicoAsignado].agregarPaciente(paciente);
        }
    }

    public void mandarPaciente(Paciente paciente) {
        filaDeAtencion.agregarPaciente(paciente);
    }

    public Paciente liberarPaciente() throws InterruptedException {
        return filaDeAtencion.obtenerPaciente();
    }

    public Paciente obtenerPacienteVip() throws InterruptedException {
        return filaPacientesVip.obtenerPaciente();
    }

    public boolean hayFilaVip() {
        return filaPacientesVip.obtenerCantidad() > 0;
    }

    public void liberarLugar() {
        capacidadSala.release();
    }

}
