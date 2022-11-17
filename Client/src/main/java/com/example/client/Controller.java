package com.example.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

    public class Controller implements Initializable
    {
        private DataOutputStream out;
        private DataInputStream in;
        private Socket socket;
        @FXML
        TextArea textArea;
        @FXML
        TextField message;
        public void sendMessage(ActionEvent actionEvent) {
            String msg = message.getText()+"\n";
            textArea.appendText(msg);
            message.clear();
            try {
                out.writeUTF(msg );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            try {
                socket=new Socket("127.0.0.1",8888);
                in=new DataInputStream(socket.getInputStream());
                out=new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException("Соединение с сервером невозможно");
            }
            Thread clientThread = new Thread(()->{
                String msg;
                while (true){
                    try {
                        msg = in.readUTF();
                        textArea.appendText(msg);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }


            });
            clientThread.start();


        }

    }
