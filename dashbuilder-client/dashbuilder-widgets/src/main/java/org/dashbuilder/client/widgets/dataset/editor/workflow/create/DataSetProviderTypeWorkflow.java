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

import org.dashbuilder.client.widgets.dataset.editor.DataSetDefProviderTypeEditor;
import org.dashbuilder.client.widgets.dataset.editor.driver.DataSetDefProviderTypeDriver;
import org.dashbuilder.client.widgets.dataset.editor.workflow.DataSetEditorWorkflow;
import org.dashbuilder.client.widgets.dataset.event.CancelRequestEvent;
import org.dashbuilder.client.widgets.dataset.event.SaveRequestEvent;
import org.dashbuilder.client.widgets.dataset.event.TestDataSetRequestEvent;
import org.dashbuilder.dataprovider.DataSetProviderType;
import org.dashbuilder.dataset.client.DataSetClientServices;
import org.dashbuilder.dataset.def.DataSetDef;
import org.dashbuilder.validations.dataset.DataSetDefValidator;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.uberfire.mvp.Command;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;


/**
 * <p>Data Set Editor workflow presenter for creating a data set definition instance by selecting the provider type.</p>
 * 
 * @since 0.4.0 
 */
@Dependent
public class DataSetProviderTypeWorkflow extends DataSetEditorWorkflow<DataSetDef> {

    DataSetDefProviderTypeEditor providerTypeEditor;
    DataSetDefProviderTypeDriver dataSetDefProviderTypeDriver;

    @Inject
    public DataSetProviderTypeWorkflow(final DataSetClientServices clientServices,
                                       final DataSetDefValidator dataSetDefValidator,
                                       final SyncBeanManager beanManager,
                                       final DataSetDefProviderTypeEditor providerTypeEditor,
                                       final Event<SaveRequestEvent> saveRequestEvent,
                                       final Event<CancelRequestEvent> cancelRequestEvent,
                                       final Event<TestDataSetRequestEvent> testDataSetEvent,
                                       final View view) {
        
        super(clientServices, dataSetDefValidator, beanManager,
                saveRequestEvent, testDataSetEvent, cancelRequestEvent, view);
        
        this.providerTypeEditor = providerTypeEditor;
    }

    @PostConstruct
    public void init() {
        super.init();
    }

    public DataSetProviderTypeWorkflow edit(final DataSetDef def) {
        checkDataSetDefNotNull(def);

        clear();
        this.dataSetDef = def;
        return this;
    }

    public DataSetProviderType getProviderType() {
        return providerTypeEditor.provider().getValue();
    }

    public DataSetProviderTypeWorkflow providerTypeEdition() {
        checkDataSetDefNotNull();

        // Provider type editor driver edition. 
        dataSetDefProviderTypeDriver = beanManager.lookupBean(DataSetDefProviderTypeDriver.class).newInstance();
        dataSetDefProviderTypeDriver.initialize(providerTypeEditor);
        dataSetDefProviderTypeDriver.edit(getDataSetDef());

        this.flushCommand = new Command() {
            @Override
            public void execute() {
                flush(dataSetDefProviderTypeDriver);
            }
        };

        this.stepValidator = new Command() {
            @Override
            public void execute() {
                Iterable<ConstraintViolation<?>> violations = dataSetDefValidator.validateProviderType(getDataSetDef());
                dataSetDefProviderTypeDriver.setConstraintViolations(violations);
                addViolations(violations);
            }
        };

        // Show provider type editor view.
        view.clearView();
        view.add(providerTypeEditor.asWidget());

        return this;
    }

}