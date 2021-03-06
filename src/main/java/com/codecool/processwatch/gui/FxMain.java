package com.codecool.processwatch.gui;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

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

        //Add Favicon
        Image icon = new Image("/icon-process.png");
        primaryStage.getIcons().add(icon);

        primaryStage.setTitle(TITLE);

        ObservableList<ProcessView> displayList = observableArrayList();
        app = new App(displayList);

        // TODO: Factor out the repetitive code
        //Create TableView and set up the Columns
        var tableView = new TableView<ProcessView>(displayList);
        var pidColumn = new TableColumn<ProcessView, Long>("Process ID");
        pidColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, Long>("pid"));
        var parentPidColumn = new TableColumn<ProcessView, Long>("Parent Process ID");
        parentPidColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, Long>("parentPid"));
        parentPidColumn.setMinWidth(150);
        var userNameColumn = new TableColumn<ProcessView, String>("Owner");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, String>("userName"));
        var processNameColumn = new TableColumn<ProcessView, String>("Name");
        processNameColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, String>("processName"));
        processNameColumn.setMinWidth(200);
        var argsColumn = new TableColumn<ProcessView, String>("Arguments");
        argsColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, String>("args"));
        argsColumn.setMinWidth(200);
        tableView.getColumns().add(pidColumn);
        tableView.getColumns().add(parentPidColumn);
        tableView.getColumns().add(userNameColumn);
        tableView.getColumns().add(processNameColumn);
        tableView.getColumns().add(argsColumn);


        //Set selectionMode to MULTIPLE
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Create a Filtered List for filteredProcesses
        FilteredList<ProcessView> filteredProcess = new FilteredList(displayList, p -> true);//Pass the data to a filtered list

        //Create 2 variables to store the countFilteredProcesses and countRunningProcesses
        AtomicReference<Integer> countFilteredProcesses = new AtomicReference<>(filteredProcess.size());
        AtomicReference<Integer> countRunningProcesses = new AtomicReference<>(displayList.size());

        //Create a Label which we use to display information in the footerPane of our Window
        Label footerPane = new Label(countRunningProcesses.toString() + " process(es) running; " + countFilteredProcesses.toString() + " item(s) displayed");

        //Create a ChoiceBox which will use for categorisation of filter
        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("Process ID", "Parent Process ID", "Owner", "Name", "Arguments");
        choiceBox.setValue("Owner");

        //Create a TextField which will use for filtering the table content
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
            countFilteredProcesses.set(filteredProcess.size());
            footerPane.setText(countRunningProcesses.toString() + " process(es) running; " + countFilteredProcesses.toString() + " item(s) displayed");
        });

        //Create refreshButton and binding methods in case of click
        var refreshButton = new Button("Refresh");
        refreshButton.setOnAction(ignoreEvent -> {
            app.refresh();
            textField.setText("");
            filteredProcess.setPredicate(p -> true);
            countRunningProcesses.set(displayList.size());
            countFilteredProcesses.set(filteredProcess.size());
            footerPane.setText(countRunningProcesses.toString() + " process(es) running; " + countFilteredProcesses.toString() + " item(s) displayed");
        });

        // Create a Sorted List for sortedProcesses
        SortedList<ProcessView> sortedProcess = new SortedList<>(filteredProcess);
        sortedProcess.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedProcess);//Set the table's items using the sorted List

        //Create killButton and binding methods in case of click
        var killButton = new Button("End Process");
        killButton.setOnAction(event -> {
            ObservableList <ProcessView> selectedRows = tableView.getSelectionModel().getSelectedItems();

            for (ProcessView selectedRow: selectedRows){
                Long pid = selectedRow.getPid();
                destroyProcess(pid);
            }
            app.refresh();
            textField.setText("");
            filteredProcess.setPredicate(p -> true);
            countRunningProcesses.set(displayList.size());
            countFilteredProcesses.set(filteredProcess.size());
            footerPane.setText(countRunningProcesses.toString() + " process(es) running; " + countFilteredProcesses.toString() + " item(s) displayed");
        });

        //Create controlPane which will use at top of the Window
        HBox controlPaneLeft = new HBox(refreshButton);
        HBox controlPaneRight = new HBox(choiceBox, textField);
        HBox controlPane = new HBox(controlPaneLeft, controlPaneRight);
        controlPane.setSpacing(30);

        //Create box which will use as main part of the Window
        var box = new VBox();
        box.setSpacing(5);
        box.setPadding(new Insets(10, 10, 10, 10));
        var scene = new Scene(box, 640, 480);
        var elements = box.getChildren();
        elements.addAll(controlPane, tableView, killButton, footerPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void destroyProcess(Long pid) {
        Stream<ProcessHandle> liveProcesses = ProcessHandle.allProcesses();
        liveProcesses
        .filter(ProcessHandle::isAlive)
        .filter(processHandle -> processHandle.pid() == pid)
        .forEach(ProcessHandle::destroy);
        }
}



