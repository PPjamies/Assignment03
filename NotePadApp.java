package HW_03;

public class NotePadApp {
    public static void main(String[] args) {
    	NotePadModel model = new NotePadModel();
        NotePadView view = new NotePadView();
    	NotePadController controller = new NotePadController(view, model);
    }
}
