package contoller;

import client.ClientSocket;
import view.ShowUsersWindow;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ShowUsersController {
    private ShowUsersWindow window;
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    public ShowUsersController(){
        socket = ClientSocket.getSocket();
    }

    public void attachView(ShowUsersWindow window) {
        this.window = window;
    }

}
