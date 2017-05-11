/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filetransferproject;

import static filetransferproject.Server.BUFFER_SIZE;
import static filetransferproject.Server.PORT;
import static filetransferproject.Server.throwException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author HP PAVİLİON 15
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Client c=new Client();
        //c.run();
 
    }
//    public void run() {
//        try {
//            ServerSocket serverSocket = new ServerSocket(PORT);
// 
//            while (true) {
//                Socket s = serverSocket.accept();
//                saveFile(s);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    Socket connectServer(String ip,int port) throws IOException{
        Socket socket = new Socket(ip, port);
        return socket;
    }
    String sendFile(Socket socket,String file_name) throws IOException{
        
        String fileName = null;
 
       try {
            String[] args = null;
            fileName = args[0];
        } catch (Exception e) {
        File file = new File(file_name);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(file.getName());
 
        FileInputStream fis = new FileInputStream(file);
        byte [] buffer = new byte[Server.BUFFER_SIZE];
        Integer bytesRead = 0;
 
        while ((bytesRead = fis.read(buffer)) > 0) {
            oos.writeObject(bytesRead);
            oos.writeObject(Arrays.copyOf(buffer, buffer.length));
        }
        
 
        oos.close();
        ois.close();  
        
                
}return "Dosya Gönderildi";
    }
    
    private void saveFile(Socket socket) throws Exception {

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        FileOutputStream fos = null;
        byte [] buffer = new byte[BUFFER_SIZE];
 
        // Dosyanın adı okunuyor
        Object o = ois.readObject();
 
        if (o instanceof String) {
            fos = new FileOutputStream(o.toString());
        } else {
            throwException("Something is wrong");
        }
        Integer bytesRead = 0;
 
        do {
            o = ois.readObject();
 
            if (!(o instanceof Integer)) {
                throwException("Hata");
            }
 
            bytesRead = (Integer)o;
 
            o = ois.readObject();
 
            if (!(o instanceof byte[])) {
                throwException("Hata");
            }
 
            buffer = (byte[])o;
 
            // 3. Write data to output file.
            fos.write(buffer, 0, bytesRead);
           
        } while (bytesRead == BUFFER_SIZE);
         
        System.out.println("Dosya Gönderildi");
         
        fos.close();
 
        ois.close();
        oos.close();
    }
}
