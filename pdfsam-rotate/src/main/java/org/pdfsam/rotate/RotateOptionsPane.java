/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 08/apr/2014
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
package org.pdfsam.rotate;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.pdfsam.support.KeyStringValueItem.keyEmptyValue;
import static org.pdfsam.support.KeyStringValueItem.keyValue;
import static org.sejda.eventstudio.StaticStudio.eventStudio;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import org.pdfsam.i18n.DefaultI18nContext;
import org.pdfsam.support.KeyStringValueItem;
import org.pdfsam.support.params.TaskParametersBuildStep;
import org.pdfsam.ui.support.Style;
import org.pdfsam.ui.workspace.RestorableView;
import org.sejda.model.rotation.Rotation;
import org.sejda.model.rotation.RotationType;

/**
 * Panel for the Rotate options
 * 
 * @author Andrea Vacondio
 *
 */
class RotateOptionsPane extends HBox implements TaskParametersBuildStep<RotateParametersBuilder>, RestorableView {

    private ComboBox<KeyStringValueItem<RotationType>> rotationType = new ComboBox<>();
    private ComboBox<KeyStringValueItem<Rotation>> rotation = new ComboBox<>();

    RotateOptionsPane() {
        super(Style.DEFAULT_SPACING);
        this.rotationType.getItems().add(
                keyValue(RotationType.ALL_PAGES, DefaultI18nContext.getInstance().i18n("All pages")));
        this.rotationType.getItems().add(
                keyValue(RotationType.EVEN_PAGES, DefaultI18nContext.getInstance().i18n("Even pages")));
        this.rotationType.getItems().add(
                keyValue(RotationType.ODD_PAGES, DefaultI18nContext.getInstance().i18n("Odd pages")));
        this.rotationType.getSelectionModel().selectFirst();
        this.rotationType.setId("rotationType");

        this.rotation.getItems().add(
                keyValue(Rotation.DEGREES_90, DefaultI18nContext.getInstance().i18n("90 degrees clockwise")));
        this.rotation.getItems().add(
                keyValue(Rotation.DEGREES_180, DefaultI18nContext.getInstance().i18n("180 degrees clockwise")));
        this.rotation.getItems().add(
                keyValue(Rotation.DEGREES_270, DefaultI18nContext.getInstance().i18n("270 degrees clockwise")));
        this.rotation.getSelectionModel().selectFirst();
        this.rotation.setId("rotation");

        getStyleClass().addAll(Style.HCONTAINER.css());
        getStyleClass().addAll(Style.CONTAINER.css());
        getChildren().addAll(new Label(DefaultI18nContext.getInstance().i18n("Rotate ")), this.rotationType,
                this.rotation);
        eventStudio().addAnnotatedListeners(this);
    }

    public void apply(RotateParametersBuilder builder, Consumer<String> onError) {
        builder.rotation(rotation.getSelectionModel().getSelectedItem().getKey());
        builder.rotationType(rotationType.getSelectionModel().getSelectedItem().getKey());
    }

    public void saveStateTo(Map<String, String> data) {
        data.put("rotation",
                Optional.ofNullable(rotation.getSelectionModel().getSelectedItem()).map(i -> i.getKey().toString())
                        .orElse(EMPTY));
        data.put("rotationType",
                Optional.ofNullable(rotationType.getSelectionModel().getSelectedItem()).map(i -> i.getKey().toString())
                        .orElse(EMPTY));
    }

    public void restoreStateFrom(Map<String, String> data) {
        Optional.ofNullable(data.get("rotation")).map(Rotation::valueOf).map(r -> keyEmptyValue(r))
                .ifPresent(r -> this.rotation.getSelectionModel().select(r));
        Optional.ofNullable(data.get("rotationType")).map(RotationType::valueOf).map(r -> keyEmptyValue(r))
                .ifPresent(r -> this.rotationType.getSelectionModel().select(r));
    }
}
