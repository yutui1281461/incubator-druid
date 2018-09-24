/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.druid.segment;

import org.apache.druid.query.extraction.ExtractionFn;
import org.apache.druid.query.monomorphicprocessing.RuntimeShapeInspector;
import org.apache.druid.segment.virtual.BaseSingleValueDimensionSelector;

import javax.annotation.Nullable;

public class LongWrappingDimensionSelector extends BaseSingleValueDimensionSelector
{
  private final BaseLongColumnValueSelector selector;
  private final ExtractionFn extractionFn;

  public LongWrappingDimensionSelector(BaseLongColumnValueSelector selector, ExtractionFn extractionFn)
  {
    this.selector = selector;
    this.extractionFn = extractionFn;
  }

  @Override
  protected String getValue()
  {
    if (extractionFn == null) {
      return String.valueOf(selector.getLong());
    } else {
      return extractionFn.apply(selector.getLong());
    }
  }

  @Nullable
  @Override
  public Object getObject()
  {
    return getValue();
  }

  @Override
  public Class classOfObject()
  {
    return String.class;
  }

  @Override
  public void inspectRuntimeShape(RuntimeShapeInspector inspector)
  {
    inspector.visit("selector", selector);
    inspector.visit("extractionFn", extractionFn);
  }
}