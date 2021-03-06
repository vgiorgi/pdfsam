/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 26/giu/2014
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
package org.pdfsam.merge;

import java.util.Set;

import org.pdfsam.support.params.AbstractPdfOutputParametersBuilder;
import org.pdfsam.support.params.SingleOutputTaskParametersBuilder;
import org.sejda.common.collection.NullSafeSet;
import org.sejda.model.input.PdfMergeInput;
import org.sejda.model.outline.OutlinePolicy;
import org.sejda.model.output.FileTaskOutput;
import org.sejda.model.parameter.MergeParameters;

/**
 * Builder for {@link MergeParameters}
 * 
 * @author Andrea Vacondio
 *
 */
class MergeParametersBuilder extends AbstractPdfOutputParametersBuilder<MergeParameters> implements
        SingleOutputTaskParametersBuilder<MergeParameters> {

    private Set<PdfMergeInput> inputs = new NullSafeSet<>();
    private OutlinePolicy outlinePolicy = OutlinePolicy.RETAIN;
    private boolean blankIfOdd;
    private boolean copyFormFields;
    private FileTaskOutput output;

    void addInput(PdfMergeInput input) {
        this.inputs.add(input);
    }

    void outlinePolicy(OutlinePolicy outlinePolicy) {
        this.outlinePolicy = outlinePolicy;
    }

    void blankPageIfOdd(boolean blankIfOdd) {
        this.blankIfOdd = blankIfOdd;
    }

    void copyFormFields(boolean copyFormFields) {
        this.copyFormFields = copyFormFields;
    }

    public void output(FileTaskOutput output) {
        this.output = output;
    }

    public MergeParameters build() {
        MergeParameters params = new MergeParameters();
        params.setCompress(isCompress());
        params.setOverwrite(isOverwrite());
        params.setVersion(getVersion());
        inputs.forEach(params::addInput);
        params.setOutlinePolicy(outlinePolicy);
        params.setBlankPageIfOdd(blankIfOdd);
        params.setCopyFormFields(copyFormFields);
        params.setOutput(output);
        return params;
    }

}
