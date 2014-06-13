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
import org.dashbuilder.model.date.DayOfWeek;

/**
 * List of the 7-days intervals present in a week.
 */
public class IntervalListDayOfWeek extends IntervalList {

    protected Map<Integer,Interval> intervalMap;

    public IntervalListDayOfWeek(ColumnGroup columnGroup) {
        super(columnGroup);
        intervalMap = new HashMap<Integer, Interval>();

        DayOfWeek firstDay = columnGroup.getFirstDayOfWeek();
        int index = firstDay.getIndex();
        DayOfWeek[] array = DayOfWeek.getAll();

        for (int i = 0; i < array.length; i++) {
            DayOfWeek dayOfWeek = array[index];
            Interval interval = new Interval(dayOfWeek.toString());
            this.add(interval);

            intervalMap.put(index, interval);
            index = DayOfWeek.nextIndex(index);
        }
    }

    public Interval locateInterval(Object value) {
        Date d = (Date) value;
        return intervalMap.get(d.getDay());
    }
}
