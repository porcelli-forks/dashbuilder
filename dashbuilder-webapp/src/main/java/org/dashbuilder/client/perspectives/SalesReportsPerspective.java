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
package org.dashbuilder.client.perspectives;

import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.dashbuilder.client.resources.i18n.AppConstants;
import org.dashbuilder.displayer.DisplayerSettings;
import org.dashbuilder.displayer.json.DisplayerSettingsJSONMarshaller;
import org.uberfire.client.annotations.Perspective;
import org.uberfire.client.annotations.WorkbenchPerspective;
import org.uberfire.client.workbench.panels.impl.MultiTabWorkbenchPanelPresenter;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.mvp.impl.DefaultPlaceRequest;
import org.uberfire.workbench.model.PerspectiveDefinition;
import org.uberfire.workbench.model.impl.PartDefinitionImpl;
import org.uberfire.workbench.model.impl.PerspectiveDefinitionImpl;

import static org.dashbuilder.client.sales.SalesOppsDisplayers.*;

/**
 * A sample dashboard perspective
 */
@ApplicationScoped
@WorkbenchPerspective(identifier = "SalesReportsPerspective")
public class SalesReportsPerspective {

    @Inject
    DisplayerSettingsJSONMarshaller jsonHelper;

    @Perspective
    public PerspectiveDefinition buildPerspective() {

        PerspectiveDefinition perspective = new PerspectiveDefinitionImpl(MultiTabWorkbenchPanelPresenter.class.getName());
        perspective.setName(AppConstants.INSTANCE.salesreportspersp_salesreports());

        perspective.getRoot().addPart(new PartDefinitionImpl(createPlaceRequest(OPPS_ALLOPPS_LISTING)));
        perspective.getRoot().addPart(new PartDefinitionImpl(createPlaceRequest(OPPS_COUNTRY_SUMMARY)));
        return perspective;
    }

    private PlaceRequest createPlaceRequest(DisplayerSettings displayerSettings) {
        String json = jsonHelper.toJsonString(displayerSettings);
        Map<String,String> params = new HashMap<String,String>();
        params.put("json", json);
        params.put("edit", "false");
        return new DefaultPlaceRequest("DisplayerScreen", params);
    }
}
