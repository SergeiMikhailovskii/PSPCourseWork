package contoller;

import base.BaseController;
import constants.Actions;
import view.ShowAllUserPropertiesWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class ShowAllUserPropertiesController extends BaseController<ShowAllUserPropertiesWindow> {
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void getAllUserProperties() {
        Vector<Vector> rowData = new Vector<>();
        sendDataToServer(Actions.GET_ALL_USER_PROPERTIES);
        sendDataToServer(String.valueOf(id));
        int count = Integer.parseInt(getDataFromServer());
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String res = getDataFromServer();
            rows.add(res);
        }
        for (String row : rows) {
            String[] arr = row.split(" ");
            Vector<Object> object = new Vector<>(Arrays.asList(arr));
            rowData.add(object);
        }
        System.out.println(rowData.size() + " SIZE");
        view.setTable(rowData);
    }
}
