package org.jemmy.fx.control;


import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;


/**
 *
 * @author Alexander Kouznetsov
 */
public class TableViewApp extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        final TableView<Person> tableView = new TableView<Person>();
        tableView.setEditable(true);
        ObservableList<Person> teamMembers = FXCollections.observableArrayList(
                new Person("Mikhail", "Kondratyev"),
                new Person("Sergey", "Grinev"),
                new Person("B", "2"),
                new Person("C", "3"),
                new Person("D", "4"),
                new Person("E", "5"),
                new Person("F", "6"),
                new Person("G", "7"),
                new Person("H", "8"),
                new Person("I", "9"),
                new Person("J", "10"),
                new Person("K", "11"),
                new Person("L", "12"),
                new Person("M", "13"),
                new Person("N", "14"),
                new Person("O", "15"),
                new Person("P", "16"),
                new Person("Q", "17"),
                new Person("R", "18"),
                new Person("S", "19"),
                new Person("T", "20"),
                new Person("U", "21"),
                new Person("Victor", "Johnson")
                );
        tableView.setItems(teamMembers);

        TableColumn<Person, String> firstNameCol = new TableColumn<Person, String>("First Name");
        firstNameCol.setEditable(true);
        TableColumn<Person, String> lastNameCol = new TableColumn<Person, String>("Last Name");
        lastNameCol.setEditable(true);
        
        lastNameCol.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(CellDataFeatures<Person, String> p) {
                return ((Person) p.getValue()).lastNameProperty();
            }
        });

        firstNameCol.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(CellDataFeatures<Person, String> p) {
                return ((Person) p.getValue()).firstNameProperty();
            }
        });

        tableView.getColumns().setAll(firstNameCol, lastNameCol);
        
//        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        
        final Label label = new Label("selected");
        //label.textProperty().bind(tableView.getSelectionModel().getSelectedCells());
        
        tableView.getSelectionModel().getSelectedCells().addListener(new ListChangeListener() {

            public void onChanged(Change change) {
//                System.out.println("change = " + change);
                final ObservableList list = change.getList();
                if (list.isEmpty()) {
                    label.setText("selected = nothing");
                } else {
                    final TablePosition cell = (TablePosition) list.get(list.size() - 1);
                    label.setText("selected = " + cell.getColumn() + ", " + cell.getRow());
                }
            }
        });
        
        tableView.setEditable(true);
        
        Button removeButton = new Button("Remove scrollbars");
        removeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent t) {
                tableView.getItems().retainAll(FXCollections.observableArrayList(
                        tableView.getItems().subList(0, 8)));
            }
        });
        
        VBox vBox = new VBox();
        vBox.getChildren().setAll(tableView, label, removeButton);
        
        Scene scene = new Scene(vBox);
        
        stage.setScene(scene);
        stage.show();
        
        
        
    }
    

    public static final class Person {

        private StringProperty firstName = new SimpleStringProperty();
        private StringProperty lastName = new SimpleStringProperty();

        public void setFirstName(String value) {
            firstName.set(value);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public StringProperty firstNameProperty() {
            return firstName;
        }

        public void setLastName(String value) {
            lastName.set(value);
        }

        public String getLastName() {
            return lastName.get();
        }

        public StringProperty lastNameProperty() {
            return lastName;
        }

        public Person(String firstName, String lastName) {
            setFirstName(firstName);
            setLastName(lastName);
        }
    }
}