package com.yanm.gol.components.infobar;

import com.yanm.ApplicationContext;
import com.yanm.gol.ApplicationComponent;
import com.yanm.gol.components.editor.Editor;
import com.yanm.gol.components.editor.EditorState;
import javafx.application.Application;

public class InfoBarApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext context) {
        EditorState editorState = context.getStateRegistry().getState(EditorState.class);

        InfoBarViewModel infoBarViewModel = new InfoBarViewModel();
        editorState.getCursorPosition().listen(infoBarViewModel.getCusorPosition()::set);
        editorState.getDrawMode().listen(infoBarViewModel.getCurrentDrawMode()::set);

        InfoBar infoBar = new InfoBar(infoBarViewModel);
        context.getMainView().setBottom(infoBar);
    }

    @Override
    public void initState(ApplicationContext context) {

    }
}
