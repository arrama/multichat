/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userada;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  




/**
 *
 * @author GambleR
 */
public class MultiThreadChatServer2000 {
   // The server socket.
  private static ServerSocket serverSocket = null;
  // The client socket.
  private static Socket clientSocket = null;

  // This chat server can accept up to maxClientsCount clients' connections.
  private static final int maxClientsCount = 10;

  //Array untuk menyimpang sekumpulan running thread

  private static final clientThread[] ArrayRunningSocket = new clientThread[maxClientsCount];

  
  public static void main(String args[]) {

    // The default port number.
    int portNumber = 2222;
    

   
   
    if (args.length < 1) {
      System.out
          .println("Usage: java MultiThreadChatServer <portNumber>\n"
              + "Now using port number=" + portNumber);
    } else {
      portNumber = Integer.valueOf(args[0]).intValue();
    }

    /*
     * Open a server socket on the portNumber (default 2222). Note that we can
     * not choose a port less than 1023 if we are not privileged users (root).
     */
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      System.out.println(e);
    }

    /*
     * Create a client socket for each connection and pass it to a new client
     * thread.
     */
    while (true) {
      try {
            // blocking menunggu ada client konek
             clientSocket = serverSocket.accept();
             System.out.println("Ada masuk");
            int i = 0;
            for (i = 0; i < maxClientsCount; i++) {
                if (ArrayRunningSocket[i] == null) {
                   ArrayRunningSocket[i] = new clientThread(clientSocket, ArrayRunningSocket);
                   ArrayRunningSocket[i].start();
                   break;
                 //  (ArrayRunningSocket[i] = new clientThread(clientSocket, ArrayRunningSocket)).start();
                 }
             }

        // jika array sudah penuh kirim pesan bahwa jumlah client sudah maksimal
        if (i == maxClientsCount) {
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Server too busy. Try later.");
          os.close();
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}
/*
 * The chat client thread. This client thread opens the input and the output
 * streams for a particular client, ask the client's name, informs all the
 * clients connected to the server about the fact that a new client has joined
 * the chat room, and as long as it receive data, echos that data back to all
 * other clients. When a client leaves the chat room this thread informs also
 * all the clients about that and terminates.
 */
class clientThread extends Thread {

  private BufferedReader is = null;
  private PrintStream os = null;
  private Socket clientSocket = null;
  private final clientThread[] threads;
  private int maxClientsCount;
  private String nama;

  public clientThread(Socket clientSocket, clientThread[] threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
  }


  /*
  public void setNama(String nama){
      this.nama=nama;
  }
  public boolean getNama(String nama){
      boolean cek = false;
      for (int i = 0; i < maxClientsCount; i++) {
        if (ArrayRunningSocket[i] != null ) {
          if (this.nama.equalsIgnoreCase(nama)){
              return true;
          }
        }
      }
      return cek;
  }
   *
   *
   */
  
    
  
  public void run() {
    int maxClientsCount = this.maxClientsCount;
    clientThread[] threads = this.threads;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    LocalDateTime now = LocalDateTime.now();

    try {
      /*
       * Create input and output streams for this client.
       */
      is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      os = new PrintStream(clientSocket.getOutputStream());
      os.println("SAD");
      String name = is.readLine().trim();
      os.println("PASSWORD");
      String password = is.readLine().trim();
      System.out.println("Name : " + name + ", pass : " + password);
      if(login(name,password)){
          
      
      this.nama= name;
      os.println("Hello " + name
          + " to our chat room\n"
                  + "To leave enter /quit in a new line\n");
      for (int i = 0; i < maxClientsCount; i++) {
        if (threads[i] != null && threads[i] != this) {
          threads[i].os.println(dtf.format(now) + "   *** A new user " + name + " entered the chat room !!! ***");
        }
        
         
      }
      while (true) {
        String line = is.readLine();
        System.out.println(line);
        if (line.startsWith("/quit")) {
          break;
        }
        
        else{
           for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null) {
                    threads[i].os.println(dtf.format(now) + "   <" + name + "--> " + line);
                }
            } 
        }
         
      }
      for (int i = 0; i < maxClientsCount; i++) {
        if (threads[i] != null && threads[i] != this) {
          threads[i].os.println("*** The user " + name
              + " is leaving the chat room !!! ***");
        }
      }
      os.println("*** Bye " + name + " ***");

      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
      for (int i = 0; i < maxClientsCount; i++) {
        if (threads[i] == this) {
          threads[i] = null;
        }
      }

      /*
       * Close the output stream, close the input stream, close the socket.
       */
      is.close();
      os.close();
      clientSocket.close();
      
    }
        else{
          os.println("Anda salah masukkan username/password silahkan connect ulang ke server");
          is.close();
          os.close();
          clientSocket.close();
      }
    } catch (IOException e) {
    }
    
  }
  
  private boolean login(String username,String password){
      Connection conn =null;
      Statement st = null;
      ResultSet rs = null;
      String user="root";
      String pass="";
      try{
          //Class.forName("com.mysql.cj.jdbc.Driver");
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pbo",user, pass);
          st = conn.createStatement();
          String query = "select * from user where username='"+username+"' and password='"+password+"';";
          //System.out.println(query);
          rs = st.executeQuery("select * from user where username='"+username+"' and password='"+password+"';");
          while(rs.next()){
              user = rs.getString("username");
              pass = rs.getString("password");
          }
          System.out.println("Username "+ user + " password "+pass);
          st.close();
          conn.close();
      }catch(Exception ex){
          System.out.println("SQLException: " + ex.getMessage());
      }
      if(!isEmpty(user) && !isEmpty(pass)){
          return true;
      }
      return false;
  }
  
  boolean isEmpty(String text){
      return text.length()==0;
  }
    
}
