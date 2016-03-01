/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sercris.util;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

/**
 *
 * @author Evandro
 */
public class ValidationFields {
 
 private static final String STILE_BORDER_VALIDATION = "-fx-border-color: #FF0000; -fx-border-width: 1.9px;";
 public static final Tooltip toolTip = new Tooltip("Campo obrigatório!");
 
 public static boolean checkEmptyFields(Node... itemToCheck) {
 //used to determinate how many fields failed in validation
 List<Node> failedFields = new ArrayList<>();
 toolTip.setStyle("-fx-background-color: linear-gradient(#FF0000 , #FF6347 );"
 + " -fx-font-weight: bold;");
 hackTooltipStartTiming(toolTip);
 for (Node n : arrayToList(itemToCheck)) {
 // Validate TextFields
 if (n instanceof TextField) {
 TextField textField = (TextField) n;
 textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
 removeToolTipAndBorderColor(textField, toolTip);
 });
 if (textField.getText() == null || textField.getText().trim().equals("")) {
 failedFields.add(n);
 addToolTipAndBorderColor(textField, toolTip);
 } else {
 removeToolTipAndBorderColor(textField, toolTip);
 }
 } // Validate Combo Box
 else if (n instanceof ComboBox) {
 ComboBox comboBox = (ComboBox) n;
 comboBox.valueProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
 removeToolTipAndBorderColor(comboBox, toolTip);
 });
 if (comboBox.getValue() == null) {
 failedFields.add(n);
 addToolTipAndBorderColor(comboBox, toolTip);
 } else {
 removeToolTipAndBorderColor(comboBox, toolTip);
 }
 } // Validate TextArea
 else if (n instanceof TextArea) {
 TextArea textArea = (TextArea) n;
 textArea.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
 removeToolTipAndBorderColor(textArea, toolTip);
 });
 if (textArea.getText() == null || textArea.getText().trim().equals("")) {
 failedFields.add(n);
 addToolTipAndBorderColor(textArea, toolTip);
 } else {
 removeToolTipAndBorderColor(textArea, toolTip);
 }
 }
 else if (n instanceof DatePicker) {
 DatePicker datePicker = (DatePicker) n;
 datePicker.valueProperty().addListener((ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) -> {
 removeToolTipAndBorderColor(datePicker, toolTip);
 });
 if (datePicker.getValue() == null) {
 failedFields.add(n);
 addToolTipAndBorderColor(datePicker, toolTip);
 } else {
 removeToolTipAndBorderColor(datePicker, toolTip);
 }
 }
 //ADD YOUR VALIDATION HERE
 //TODO
 }
 
 return failedFields.isEmpty();
 }
 
 /**
 * *******ADD AND REMOVE STYLES********
 */
 private static void addToolTipAndBorderColor(Node n, Tooltip t) {
 Tooltip.install(n, t);
 n.setStyle(STILE_BORDER_VALIDATION);
 }
 
 public static void removeToolTipAndBorderColor(Node n, Tooltip t) {
 Tooltip.uninstall(n, t);
 n.setStyle(null);
 }
 
 /**
 * ***********ARRAY TO LIST UTILITY************
 */
 private static List<Node> arrayToList(Node[] n) {
 List<Node> listItems = new ArrayList<>();
 for (Node n1 : n) {
 listItems.add(n1);
 }
 return listItems;
 }
 
 /**
 * ***********FORCE TOOL TIP TO BE DISPLAYED FASTER************
 */
 private static void hackTooltipStartTiming(Tooltip tooltip) {
 try {
 Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
 fieldBehavior.setAccessible(true);
 Object objBehavior = fieldBehavior.get(tooltip);
 
 Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
 fieldTimer.setAccessible(true);
 Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);
 
 objTimer.getKeyFrames().clear();
 objTimer.getKeyFrames().add(new KeyFrame(new Duration(5)));
 } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
 System.out.println(e);
 }
 }
}
