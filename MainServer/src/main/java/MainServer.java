import DBCreating.DBConnection;
import DBCreating.CreateDB;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;


public class MainServer{
        public static void main(String[] args) {
            try(ServerSocket serverSocket = new ServerSocket(4288)) {
                DBConnection.connect();
                CreateDB createDB = new CreateDB();
                while (true) {
                    System.out.println("Ожидаем нового клиента!");
                    Socket socket = serverSocket.accept();
                    ClientThread oneMoreClient = new ClientThread(socket);
                    Thread newClient = new Thread(oneMoreClient);
                    newClient.start();
                    System.out.println("Подключение и направление клиента на новый поток выполнено!");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
}
