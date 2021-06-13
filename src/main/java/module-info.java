module Task1 {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.logging;
    requires spring.core;
    requires spring.beans;
    requires spring.context;

    opens sample;
    opens sample.MainWindow;
    opens sample.Documents;
    opens sample.Documents.DocumentsType;
    opens sample.Documents.ResourcesType;
    opens sample.DialogFragments;
    opens sample.DialogFragments.BuildersDialog;
    opens sample.Commands;
    opens sample.Commands.Actions;

}