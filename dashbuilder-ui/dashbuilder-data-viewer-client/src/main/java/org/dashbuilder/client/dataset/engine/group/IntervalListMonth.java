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
package org.dashbuilder.client.dataset.engine.group;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.dashbuilder.model.dataset.group.ColumnGroup;
import org.dashbuilder.model.date.Month;

/**
 * List of the 12-months intervals present in a year.
 */
public class IntervalListMonth extends IntervalList {

    protected Map<Integer,Interval> intervalMap;

    public IntervalListMonth(ColumnGroup columnGroup) {
        super(columnGroup);
        intervalMap = new HashMap<Integer, Interval>();

        Month firstMonth = columnGroup.getFirstMonthOfYear();
        int monthIndex = firstMonth.getIndex();
        Month[] months = Month.getAll();

        for (int i = 0; i < months.length; i++) {
            Month month = months[monthIndex];
            Interval interval = new Interval(month.toString());
            this.add(interval);

            intervalMap.put(monthIndex, interval);
            monthIndex = Month.nextIndex(monthIndex);
        }
    }

    public Interval locateInterval(Object value) {
        Date d = (Date) value;
        return intervalMap.get(d.getMonth());
    }
}
