package repositorio;

import com.mycompany.sistema.reservas.dominio.modelo.Reserva;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author valeriaestefaniagongoratorres
 */
public class ReservaArchivoRepository implements ReservaRepository {

    // Nombre del archivo donde se van a guardar las reservas
    private static final String ARCHIVO = "reservas.txt";

    @Override
    public void guardar(Reserva reserva) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO, true))) {

            // Guardamos los datos de la reserva separados por ";"
            writer.println(
                reserva.getId() + 
                reserva.getCliente().getNombre() +  
                reserva.getEstado()
            );

            System.out.println("[Repositorio] Reserva guardada en archivo.");

        } catch (IOException e) {

            // Si ocurre un problema con el archivo,
            // mostramos un error y detenemos la operación.
            throw new RuntimeException(
                "Error al guardar la reserva en el archivo.",
                e
            );
        }
    }
}