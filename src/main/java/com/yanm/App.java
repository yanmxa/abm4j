package com.yanm;

import com.yanm.app.command.CommandExecutor;
import com.yanm.app.event.EventBus;
import com.yanm.app.state.StateRegistry;
import com.yanm.gol.ApplicationComponent;
import com.yanm.gol.components.board.BoardApplicationComponent;
import com.yanm.gol.components.editor.EditorApplicationComponent;
import com.yanm.gol.components.infobar.InfoBarApplicationComponent;
import com.yanm.gol.components.simulator.SimulatorApplicationComponent;
import com.yanm.gol.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        EventBus eventBus = new EventBus();
        StateRegistry stateRegistry = new StateRegistry();
        CommandExecutor commandExecutor = new CommandExecutor(stateRegistry);

        MainView mainView = new MainView(eventBus, commandExecutor);

        ApplicationContext context = new ApplicationContext(eventBus, commandExecutor, stateRegistry, mainView, 20, 12);

        List<ApplicationComponent> components = new LinkedList<>();
        components.add(new EditorApplicationComponent());
        components.add(new SimulatorApplicationComponent());
        components.add(new BoardApplicationComponent());
        components.add(new InfoBarApplicationComponent());

        for (ApplicationComponent component : components) {
            component.initState(context);
        }
        for (ApplicationComponent component : components) {
            component.initComponent(context);
        }

        Scene scene = new Scene(mainView, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
