/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  The ASF licenses this file to You
* under the Apache License, Version 2.0 (the "License"); you may not
* use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.  For additional information regarding
* copyright in this work, please see the NOTICE file in the top level
* directory of this distribution.
*/
package org.apache.abdera.parser.stax;

import java.util.Calendar;
import java.util.Date;

import javax.xml.namespace.QName;

import org.apache.abdera.model.AtomDate;
import org.apache.abdera.model.DateTime;
import org.apache.axiom.om.OMContainer;
import org.apache.axiom.om.OMException;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMXMLParserWrapper;

public class FOMDateTime 
  extends FOMElement 
  implements DateTime {
  
  private static final long serialVersionUID = -6611503566172011733L;

  public FOMDateTime(QName qname) {
    super(qname);
  }
  
  public FOMDateTime(QName qname, Date date) {
    this(qname);
    setDate(date);
  }
  
  public FOMDateTime(QName qname, Calendar calendar) {
    this(qname);
    setCalendar(calendar);
  }
  
  public FOMDateTime(QName qname, String value) {
    this(qname);
    setString(value);
  }
  
  public FOMDateTime(QName qname, long time) {
    this(qname);
    setTime(time);
  }
  
  public FOMDateTime(QName qname, AtomDate atomDate) {
    this(qname);
    setValue(atomDate);
  }
  
  public FOMDateTime(
    String name,
    OMNamespace namespace,
    OMContainer parent,
    OMFactory factory)
      throws OMException {
    super(name, namespace, parent, factory);
  }
  
  public FOMDateTime(
    QName qname, 
    OMContainer parent, 
    OMFactory factory) 
      throws OMException {
    super(qname, parent, factory);
  }

  public FOMDateTime(
    QName qname, 
    OMContainer parent, 
    OMFactory factory,
    OMXMLParserWrapper builder) 
      throws OMException {
    super(qname, parent, factory, builder);
  }
  
  public AtomDate getValue() {
    AtomDate value = null;
    String v = getText();
    if (v != null) {
      value = AtomDate.valueOf(v);
    }
    return value;
  }

  public void setValue(AtomDate dateTime) {
    if (dateTime != null)
      setText(dateTime.getValue());
    else 
      _removeAllChildren();
  }

  public void setDate(Date date) {
    if (date != null)
      setText(AtomDate.valueOf(date).getValue());
    else 
      _removeAllChildren();
  }

  public void setCalendar(Calendar date) {
    if (date != null)
      setText(AtomDate.valueOf(date).getValue());
    else 
      _removeAllChildren();
  }

  public void setTime(long date) {
    setText(AtomDate.valueOf(date).getValue());
  }

  public void setString(String date) {
    if (date != null)
      setText(AtomDate.valueOf(date).getValue());
    else 
      _removeAllChildren();
  }

  public Date getDate() {
    AtomDate ad = getValue();
    return (ad != null) ? ad.getDate() : null;
  }

  public Calendar getCalendar() {
    AtomDate ad = getValue();
    return (ad != null) ? ad.getCalendar() : null;
  }

  public long getTime() {
    AtomDate ad = getValue();
    return (ad != null) ? ad.getTime() : null;
  }

  public String getString() {
    AtomDate ad = getValue();
    return (ad != null) ? ad.getValue() : null;
  }

}
