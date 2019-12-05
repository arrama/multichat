/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userada;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JTextArea;

/**
 *
 * @author GambleR
 */
public class Reader implements Runnable{
    private final Socket socket;
    private final JTextArea text;
    
    public Reader(Socket socket, JTextArea text){
        this.socket = socket;
        this.text = text;
    }

    @Override
    public void run() {
        String response = "";
        try{
            Scanner networkInput = new Scanner(socket.getInputStream());
            PrintWriter networkOutput = new PrintWriter(socket.getOutputStream());
            
            while(!response.equals("QUIT")){
                response = networkInput.nextLine();
                text.append(response+"\n");
            }
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
