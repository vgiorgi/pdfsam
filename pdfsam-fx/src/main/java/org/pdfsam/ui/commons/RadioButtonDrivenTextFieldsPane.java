/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 09/giu/2014
 * Copyright 2013-2014 by Andrea Vacondio (andrea.vacondio@gmail.com).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pdfsam.ui.commons;

import static org.pdfsam.support.RequireUtils.requireNotNull;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

import org.pdfsam.ui.support.Style;

/**
 * GridPane containing a {@link RadioButton}s that, when selected, activate the corresponding text field.
 * 
 * @author Andrea Vacondio
 *
 */
public class RadioButtonDrivenTextFieldsPane extends GridPane {

    private int rows = 0;
    private ToggleGroup group;

    public RadioButtonDrivenTextFieldsPane(ToggleGroup group) {
        this.group = group;
        getStyleClass().addAll(Style.GRID.css());
    }

    public void addRow(RadioButton radio, TextField field) {
        requireNotNull(radio, "Cannot add a null radio");
        requireNotNull(field, "Cannot add a null field");
        GridPane.setValignment(radio, VPos.BOTTOM);
        GridPane.setValignment(field, VPos.BOTTOM);
        GridPane.setHalignment(radio, HPos.LEFT);
        GridPane.setHalignment(field, HPos.LEFT);
        GridPane.setFillWidth(field, true);
        field.setPrefWidth(300);
        field.setDisable(true);
        radio.selectedProperty().addListener((o, oldVal, newVal) -> {
            field.setDisable(!newVal);
            if (newVal) {
                field.requestFocus();
            }
        });
        radio.setToggleGroup(group);
        add(radio, 0, rows);
        add(field, 1, rows);
        rows++;

    }

}
