package contoller;

import view.ShowUsersWindow;

public class ShowUsersController {
    private ShowUsersWindow window;

    public ShowUsersController(){

    }

    public void attachView(ShowUsersWindow window) {
        this.window = window;
    }

}
