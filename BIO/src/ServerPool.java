import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerPool {

    private static ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws IOException {
        /*服务器必备*/
        ServerSocket serverSocket = new ServerSocket();
        /*绑定监听端口*/
        serverSocket.bind(new InetSocketAddress(10001));
        System.out.println("server start...");

        try {
            while (true){
                executorService.execute(new ServerTask(serverSocket.accept()));
            }
        } finally {
            serverSocket.close();
        }

    }
    private static class ServerTask implements Runnable{

        private Socket socket = null;

        public ServerTask(Socket socket) {this.socket = socket;}

        @Override
        public void run() {
            /*拿和客户端通讯的输入输出流*/
            try (
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ){
                /*服务器的输入*/
                String userName = ois.readUTF();
                System.out.println("accept client message:"+userName);
                //响应
                oos.writeUTF("Hello"+userName);
                oos.flush();
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}

