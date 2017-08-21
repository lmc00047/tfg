package tutorial.com.cuidadores;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Clase encargada de la recepción y creación de una hebra para cada uno de los clientes que se
 * conectan al servidor.
 */
public class hiloServidor extends Thread
{
    int PUERTO=2510;
    ServerSocket sc;
    Socket so;
    static DataInputStream dis;

    //SERVIDOR
    public hiloServidor()
    {
    }
    @Override
    public void run()
    {
        try
        {
            sc = new ServerSocket(PUERTO);
            while (true)
            {
                System.out.println("Esperando conexion Servidor TCP");
                Socket socket = sc.accept();
                System.out.println("CONECTADOOO");
                hiloServer nuevoHilo = new hiloServer(socket);
                nuevoHilo.start();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            try
            {
                sc.close();
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }
}
