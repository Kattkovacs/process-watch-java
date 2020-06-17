package com.codecool.processwatch.gui;

import com.codecool.processwatch.domain.ProcessWatchApp;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * The JavaFX application Window.
 */
public class FxMain extends Application {
    private static final String TITLE = "Process Watch";

    private App app;


    /**
     * Entrypoint for the javafx:run maven task.
     *
     * @param args an array of the command line parameters.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Build the application window and set up event handling.
     *
     * @param primaryStage a stage created by the JavaFX runtime.
     */
    public void start(Stage primaryStage) {
        primaryStage.setTitle(TITLE);

        ObservableList<ProcessView> displayList = observableArrayList();
        app = new App(displayList);

        // TODO: Factor out the repetitive code
        var tableView = new TableView<ProcessView>(displayList);
        var pidColumn = new TableColumn<ProcessView, Long>("Process ID");
        pidColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, Long>("pid"));
        var parentPidColumn = new TableColumn<ProcessView, Long>("Parent Process ID");
        parentPidColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, Long>("parentPid"));
        var userNameColumn = new TableColumn<ProcessView, String>("Owner");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, String>("userName"));
        var processNameColumn = new TableColumn<ProcessView, String>("Name");
        processNameColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, String>("processName"));
        var argsColumn = new TableColumn<ProcessView, String>("Arguments");
        argsColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, String>("args"));
        tableView.getColumns().add(pidColumn);
        tableView.getColumns().add(parentPidColumn);
        tableView.getColumns().add(userNameColumn);
        tableView.getColumns().add(processNameColumn);
        tableView.getColumns().add(argsColumn);


        var refreshButton = new Button("Refresh");
//        refreshButton.setOnAction(ignoreEvent -> System.out.println("Button pressed"));
        refreshButton.setOnAction(ignoreEvent -> app.refresh());

        // Filter feature starts here

        FilteredList<ProcessView> filteredProcess = new FilteredList(displayList, p -> true);//Pass the data to a filtered list
        SortedList<ProcessView> sortedProcess = new SortedList<>(filteredProcess);
        sortedProcess.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedProcess);//Set the table's items using the sorted List

        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("Process ID", "Parent Process ID", "Owner", "Name", "Arguments");
        choiceBox.setValue("Owner");

        TextField textField = new TextField();
        textField.setPromptText("Search here!");
        textField.setOnKeyReleased(keyEvent ->
        {
            switch (choiceBox.getValue())//Switch on choiceBox value
            {
                case "Process ID":
                    filteredProcess.setPredicate(p -> p.getPid().toString().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;
                case "Parent Process ID":
                    filteredProcess.setPredicate(p -> p.getParentPid().toString().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;
                case "Owner":
                    filteredProcess.setPredicate(p -> p.getUserName().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;
                case "Name":
                    filteredProcess.setPredicate(p -> p.getProcessName().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;
                case "Arguments":
                    filteredProcess.setPredicate(p -> p.getArgs().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;

            }
        });

        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {//reset table and textfield when new choice is selected
            if (newVal != null)
            {
                textField.setText("");
                filteredProcess.setPredicate(null);//This is same as saying flPerson.setPredicate(p->true);
            }
        });

        HBox hBox = new HBox(choiceBox, textField);//Add choiceBox and textField to hBox
        hBox.setAlignment(Pos.CENTER);//Center HBox

        //vbox.getChildren().addAll(label, table, hBox);

        var box = new VBox();
        box.setSpacing(5);
        box.setPadding(new Insets(10, 10, 10, 10));
        var scene = new Scene(box, 640, 480);
        var elements = box.getChildren();
        elements.addAll(refreshButton, hBox,
                tableView);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
