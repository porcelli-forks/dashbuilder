/**
 * Copyright (C) 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dashbuilder.client.widgets.dataset.editor.workflow.create;

import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import org.dashbuilder.client.widgets.dataset.editor.attributes.DataSetDefBasicAttributesEditor;
import org.dashbuilder.client.widgets.dataset.editor.driver.CSVDataSetDefAttributesDriver;
import org.dashbuilder.client.widgets.dataset.event.CancelRequestEvent;
import org.dashbuilder.client.widgets.dataset.event.SaveRequestEvent;
import org.dashbuilder.client.widgets.dataset.event.TestDataSetRequestEvent;
import org.dashbuilder.dataprovider.DataSetProviderType;
import org.dashbuilder.dataset.client.DataSetClientServices;
import org.dashbuilder.dataset.client.editor.CSVDataSetDefAttributesEditor;
import org.dashbuilder.dataset.def.CSVDataSetDef;
import org.dashbuilder.validations.dataset.DataSetDefValidator;
import org.jboss.errai.ioc.client.container.SyncBeanManager;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;


/**
 * <p>CSV Data Set Editor workflow presenter for setting data set definition basic attributes.</p>
 * 
 * @since 0.4.0 
 */
@Dependent
public class CSVDataSetBasicAttributesWorkflow extends DataSetBasicAttributesWorkflow<CSVDataSetDef, CSVDataSetDefAttributesEditor> {

    @Inject
    public CSVDataSetBasicAttributesWorkflow(final DataSetClientServices clientServices,
                                              final DataSetDefValidator dataSetDefValidator,
                                              final SyncBeanManager beanManager,
                                              final DataSetDefBasicAttributesEditor basicAttributesEditor,
                                              final Event<SaveRequestEvent> saveRequestEvent,
                                              final Event<TestDataSetRequestEvent> testDataSetEvent,
                                              final Event<CancelRequestEvent> cancelRequestEvent,
                                              final View view) {
        
        super(clientServices, dataSetDefValidator, beanManager, basicAttributesEditor, saveRequestEvent, testDataSetEvent, cancelRequestEvent, view);
    }
    
    @Override
    protected Class<? extends SimpleBeanEditorDriver<CSVDataSetDef, CSVDataSetDefAttributesEditor>> getDriverClass() {
        return CSVDataSetDefAttributesDriver.class;
    }

    @Override
    protected Class<? extends CSVDataSetDefAttributesEditor> getEditorClass() {
        return org.dashbuilder.client.widgets.dataset.editor.csv.CSVDataSetDefAttributesEditor.class;
    }

    @Override
    protected Iterable<ConstraintViolation<?>> validate() {
        return dataSetDefValidator.validatorFor(DataSetProviderType.CSV).validateAttributes(getDataSetDef(), editor.isUsingFilePath());
    }

    @Override
    protected void afterFlush() {
        super.afterFlush();
        if (!editor.isUsingFilePath()) {
            dataSetDef.setFilePath(null);
        } else {
            dataSetDef.setFileURL(null);
        }
    }
}