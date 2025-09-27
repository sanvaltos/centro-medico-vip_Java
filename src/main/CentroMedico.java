package main;

import java.util.LinkedList;
import java.util.Queue;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class CentroMedico {
    public static final int numMedicos = 4;

    private static final int numPacientes = 50;

    private final Semaphore capacidadSala = new Semaphore(28);

    private final FilaDeEspera[] filasMedicos = new FilaDeEspera[4];

    private final FilaDeEspera filaPacientesVip = new FilaDeEspera();

    Object lock = new Object();

    private final Thread[] medicos;

    public CentroMedico() {
        medicos = new Thread[numMedicos];
        for (int i = 0; i < numMedicos; i++) {
            medicos[i] = new Thread(new Medico(this,"Medico" + (i + 1), filasMedicos[i], i));
        }
    }

    public void iniciar() {
        for (Thread medico : medicos) {
            medico.start();
        }
    }

    public void ingresarPaciente(Paciente paciente, int medicoAsignado) throws InterruptedException {
        capacidadSala.acquire();

        if (paciente.esVip()) {
            filaPacientesVip.agregarPaciente(paciente);
        } else {
            filasMedicos[medicoAsignado].agregarPaciente(paciente);
        }
    }

    public FilaDeEspera getFilaVip() {
        return filaPacientesVip;
    }

}
