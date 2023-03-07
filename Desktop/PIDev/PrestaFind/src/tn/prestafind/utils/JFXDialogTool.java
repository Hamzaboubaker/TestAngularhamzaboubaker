/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.prestafind.utils;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 *
 * @author User31
 */
public class JFXDialogTool {

    private final JFXDialog dialog = new com.jfoenix.controls.JFXDialog();

    public JFXDialogTool(Region region, StackPane container) {
        dialog.setContent(region);
        dialog.setBackground(Background.EMPTY);
        dialog.setDialogContainer(container);
        dialog.getStyleClass().add("jfx-dialog-overlay-pane");
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
    }

    public void setOnDialogOpened(EventHandler<JFXDialogEvent> action) {
        dialog.setOnDialogOpened(action);
    }

    public void setOnDialogClosed(EventHandler<JFXDialogEvent> action) {
        dialog.setOnDialogClosed(action);
    }

    public void show() {
        dialog.show();
    }

    public void close() {
        dialog.close();
    }
}
