/*
 * Copyright (c) 2010-2010 LinkedIn, Inc
 * Portions Copyright (c) 2011 Yan Pujante
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.linkedin.glu.provisioner.core.model

/**
 * @author ypujante@linkedin.com */
class LogicNotSystemFilter implements SystemFilter
{
  SystemFilter filter

  def toExternalRepresentation()
  {
    return [(kind): filter.toExternalRepresentation()]
  }

  @Override
  String toDSL()
  {
    return "not{${filter.toDSL()}}".toString()
  }

  def boolean filter(SystemEntry entry)
  {
    return !filter.filter(entry);
  }

  def String getKind()
  {
    return 'not';
  }

  def String toString()
  {
    return "not{${filter.toString()}}"
  }

  boolean equals(o)
  {
    if(this.is(o)) return true;
    if(!(o instanceof LogicNotSystemFilter)) return false;

    LogicNotSystemFilter that = (LogicNotSystemFilter) o;

    if(filter != that.filter) return false;

    return true;
  }

  int hashCode()
  {
    return (filter != null ? filter.hashCode() : 0);
  }
}
