# TechShopServer
Coursework based on Java. Key technologies: 
* JDBC
* TCP/IP connection between Client and Server
* Multithreading (extending from Thread)
* Requests and Responses are packed in Json by Gson
* All data is stored in MySQL DB.

## Structure of our Server

Server is devided into three modules:
1. JDBC <br>
   It is the module, wich main aim is to work with our DB. Here you can find packages such as DAO, DBCreating, Entities etc.
2. MainServer <br>
   In this module we start out server. Here some lines of code, to understand, how it goes:
   ```java
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
   ```
3. MultithreadingConnection <br>
   There is the last module in my project, it contains ClientThread.class, that extends from Thread and overrides method run. 
