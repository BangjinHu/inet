package example3;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketExample1 {

    public final static int port = 6421;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(30);

        try (ServerSocket serverSocket = new ServerSocket(port)){
            while (true){
                try {
                    Socket connection = serverSocket.accept();
//                    Thread task = new DaytimeThread(connection);
                    Callable<Void> task = new DaytimeThread(connection);
//                    task.start();
                    executorService.submit(task);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("can't start server");
        }
    }

    private static class DaytimeThread implements Callable<Void> {
        private Socket connection;

        public DaytimeThread(Socket connection) {
            this.connection = connection;
        }

//        @Override
//        public void run() {
//            try {
//                Writer out = new OutputStreamWriter(connection.getOutputStream());
//                Date now = new Date();
//                out.write(now.toString() + "\r\n");
//                out.flush();
//            }catch (IOException e){
//                e.printStackTrace();
//            }finally {
//                try {
//                    connection.close();
//                }catch (IOException e){
//
//                }
//            }
//        }


        @Override
        public Void call() throws Exception {
            try {
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                Date now = new Date();
                out.write(now.toString() + "\r\n");
                out.flush();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                }catch (IOException e){

                }
            }
            return null;
        }
    }
}
