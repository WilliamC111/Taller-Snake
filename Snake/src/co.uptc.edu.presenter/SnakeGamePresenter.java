import javax.swing.SwingUtilities;

public class SnakeGamePresenter {
    private MenuView view;
    private SnakeModel model;

    public SnakeGamePresenter(MenuView view, SnakeModel model) {
        this.view = view;
        this.model = model;
    }

    public void startGame() {
        view.showMenuView();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuView view = new MenuView();
            view.show();
        });
    }

}
