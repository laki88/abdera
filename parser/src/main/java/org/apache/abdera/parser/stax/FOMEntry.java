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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.xml.namespace.QName;

import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.AtomDate;
import org.apache.abdera.model.Category;
import org.apache.abdera.model.Content;
import org.apache.abdera.model.Control;
import org.apache.abdera.model.DateTime;
import org.apache.abdera.model.Div;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.IRI;
import org.apache.abdera.model.Link;
import org.apache.abdera.model.Person;
import org.apache.abdera.model.Source;
import org.apache.abdera.model.Text;
import org.apache.abdera.model.Content.Type;
import org.apache.abdera.parser.stax.util.FOMHelper;
import org.apache.abdera.util.Constants;
import org.apache.abdera.util.URIHelper;
import org.apache.axiom.om.OMContainer;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMException;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMXMLParserWrapper;

public class FOMEntry
  extends FOMExtensibleElement 
  implements Entry {

  private static final long serialVersionUID = 1L;

  public FOMEntry() {
    super(Constants.ENTRY, new FOMDocument(), (OMFactory)Factory.INSTANCE);
  }
  
  public FOMEntry(
    String name,
    OMNamespace namespace,
    OMContainer parent,
    OMFactory factory)
      throws OMException {
    super(name, namespace, parent, factory);
  }
  
  public FOMEntry(
    QName qname,
    OMContainer parent,
    OMFactory factory) {
      super(qname, parent, factory);
  }
  
  public FOMEntry(
    QName qname,
    OMContainer parent, 
    OMFactory factory,
    OMXMLParserWrapper builder) {
      super(qname, parent, factory, builder);
  }
  
  public FOMEntry(
    OMContainer parent, 
    OMFactory factory) 
      throws OMException {
    super(ENTRY, parent, factory);
  }

  public FOMEntry(
    OMContainer parent, 
    OMFactory factory,
    OMXMLParserWrapper builder) 
      throws OMException {
    super(ENTRY, parent, factory, builder);
  }
  
  public Person getAuthor() {
    return (Person) getFirstChildWithName(AUTHOR);
  }
  
  public List<Person> getAuthors() {
    return _getChildrenAsSet(AUTHOR);
  }

  public void addAuthor(Person person) {
    addChild((OMElement)person);
  }
  
  public Person addAuthor(String name) {
    FOMFactory fomfactory = (FOMFactory) this.factory;
    Person person = fomfactory.newAuthor(this);
    person.setName(name);
    return person;
  }

  public Person addAuthor(String name, String email, String uri) throws URISyntaxException {
    FOMFactory fomfactory = (FOMFactory) this.factory;
    Person person = fomfactory.newAuthor(this);
    person.setName(name);
    person.setEmail(email);
    person.setUri(uri);
    return person;
  }
  
  public List<Category> getCategories() {
    return _getChildrenAsSet(CATEGORY);
  }

  public List<Category> getCategories(String scheme) throws URISyntaxException {
    return FOMHelper.getCategories(this, scheme);
  }

  public void addCategory(Category category) {
    addChild((OMElement)category);
  }

  public Category addCategory(String term) {
    FOMFactory factory = (FOMFactory) this.factory;
    Category category = factory.newCategory(this);
    category.setTerm(term);
    return category;
  }

  public Category addCategory(String scheme, String term, String label) throws URISyntaxException {
    FOMFactory factory = (FOMFactory) this.factory;
    Category category = factory.newCategory(this);
    category.setTerm(term);
    category.setScheme(scheme);
    category.setLabel(label);
    return category;    
  }
  
  @SuppressWarnings("unchecked")
  public Content getContentElement() {
    return (Content)getFirstChildWithName(CONTENT);
  }

  public void setContentElement(Content content) {
    if (content != null) {
      Content element = getContentElement();
      if (element != null) element.discard();
      _setChild(CONTENT, (OMElement)content);
    } else {
      _removeChildren(CONTENT, false);
    }
  }
  
  /**
   * Sets the content for this entry as @type="text"
   */
  public Content setContent(String value) {
    FOMFactory factory = (FOMFactory) this.factory;
    Content content = factory.newContent();
    content.setValue(value);
    setContentElement(content);
    return content;
  }
  
  public Content setContentAsHtml(String value) {
    return setContent(value, Content.Type.HTML);
  }
  
  public Content setContentAsXhtml(String value) {
    return setContent(value, Content.Type.XHTML);
  }
  
  /**
   * Sets the content for this entry
   */
  public Content setContent(String value, Content.Type type) {
    FOMFactory factory = (FOMFactory) this.factory;
    Content content = factory.newContent(type);
    content.setValue(value);
    setContentElement(content);
    return content;
  }
  
  /**
   * Sets the content for this entry
   */
  public Content setContent(Element value) {
    FOMFactory factory = (FOMFactory) this.factory;
    Content content = factory.newContent(
      (value instanceof Div) ? 
        Content.Type.XHTML : 
        Content.Type.XHTML);
    content.setValueElement(value);
    setContentElement(content);
    return content;
  } 
  
  /**
   * Sets the content for this entry
   * @throws MimeTypeParseException 
   */
  public Content setContent(Element element, String mediaType) throws MimeTypeParseException {
    FOMFactory factory = (FOMFactory) this.factory;
    Content content = factory.newContent(new MimeType(mediaType), element);
    setContentElement(content);
    return content;
  }
  
  /**
   * Sets the content for this entry
   * @throws MimeTypeParseException 
   */
  public Content setContent(DataHandler dataHandler) throws MimeTypeParseException {
     return setContent(dataHandler, dataHandler.getContentType());
  }  
  
  /**
   * Sets the content for this entry
   * @throws MimeTypeParseException 
   */
  public Content setContent(DataHandler dataHandler, String mediatype) throws MimeTypeParseException {
    FOMFactory factory = (FOMFactory) this.factory;
    Content content = factory.newContent(new MimeType(mediatype));
    content.setDataHandler(dataHandler);
    setContentElement(content);
    return content;
  }
  
  /**
   * Sets the content for this entry
   * @throws MimeTypeParseException 
   */
  public Content setContent(String value, String mediatype) throws MimeTypeParseException {
    FOMFactory factory = (FOMFactory) this.factory;
    Content content = factory.newContent(new MimeType(mediatype));
    content.setValue(value);
    setContentElement(content);
    return content;
  }

  
  public List<Person> getContributors() {
    return _getChildrenAsSet(CONTRIBUTOR);
  }

  public void addContributor(Person person) {
    addChild((OMElement)person);
  }

  public Person addContributor(String name) {
    FOMFactory fomfactory = (FOMFactory) this.factory;
    Person person = fomfactory.newContributor(this);
    person.setName(name);
    return person;
  }

  public Person addContributor(
    String name, 
    String email, 
    String uri) 
      throws URISyntaxException {
    FOMFactory fomfactory = (FOMFactory) this.factory;
    Person person = fomfactory.newContributor(this);
    person.setName(name);
    person.setEmail(email);
    person.setUri(uri);
    return person;
  }
  
  public IRI getIdElement() {
    return (IRI)getFirstChildWithName(ID);
  }

  public void setIdElement(IRI id) {
    if (id != null)
      _setChild(ID, (OMElement)id);
    else
      _removeChildren(ID, false);
  }

  public URI getId() throws URISyntaxException {
    IRI id = getIdElement();
    return (id != null) ? id.getValue() : null;
  }
  
  public IRI setId(String value) throws URISyntaxException {
    return setId(value, false);
  }
  
  public IRI setId(String value, boolean normalize) throws URISyntaxException {
    if (value == null) {
      _removeChildren(ID, false);
      return null;
    }
    IRI id = getIdElement();
    if (id != null) {
      if (normalize) id.setNormalizedValue(value);
      else id.setValue(value);
      return id;
    } else {
      FOMFactory fomfactory = (FOMFactory) factory;
      IRI iri = fomfactory.newID(this);
      iri.setValue((normalize) ? URIHelper.normalize(value) : value);
      return iri;
    }
  }
  
  public List<Link> getLinks() {
    return _getChildrenAsSet(LINK);
  }

  public List<Link> getLinks(String rel) {
    return FOMHelper.getLinks(this, rel);
  }

  public void addLink(Link link) {
    addChild((OMElement)link);
  }

  public Link addLink(String href) throws URISyntaxException {
    return addLink(href, null);
  }
  
  public Link addLink(String href, String rel) throws URISyntaxException {
    FOMFactory fomfactory = (FOMFactory) factory;
    Link link = fomfactory.newLink(this);
    link.setHref(href);
    if (rel != null) link.setRel(rel);
    return link;    
  }
  
  public Link addLink(
    String href, 
    String rel, 
    String type, 
    String title, 
    String hreflang, 
    long length) 
      throws URISyntaxException, MimeTypeParseException {
    FOMFactory fomfactory = (FOMFactory) factory;
    Link link = fomfactory.newLink(this);
    link.setHref(href);
    link.setRel(rel);
    link.setMimeType(type);
    link.setTitle(title);
    link.setHrefLang(hreflang);
    link.setLength(length);
    return link;
  }
  
  public DateTime getPublishedElement() {
    return (DateTime)getFirstChildWithName(PUBLISHED);
  }

  public void setPublishedElement(DateTime dateTime) {
    if (dateTime != null)
      _setChild(PUBLISHED, (OMElement)dateTime);
    else
      _removeChildren(PUBLISHED, false);
  }

  public Date getPublished() {
    DateTime dte = getPublishedElement();
    return (dte != null) ? dte.getDate() : null;
  }
  
  private DateTime setPublished(AtomDate value) {
    if (value == null) {
      _removeChildren(PUBLISHED, false);
      return null;
    }
    DateTime dte = getPublishedElement();
    if (dte != null) {
      dte.setValue(value);
      return dte;
    } else {
      FOMFactory fomfactory = (FOMFactory) factory;
      DateTime dt = fomfactory.newPublished(this);
      dt.setValue(value);
      return dt;
    }
  }
  
  public DateTime setPublished(Date value) {
    return setPublished((value != null) ? AtomDate.valueOf(value) : null);
  }
  
  public DateTime setPublished(String value) {
    return setPublished((value != null) ? AtomDate.valueOf(value) : null);
  }
  
  @SuppressWarnings("unchecked")
  public Text getRightsElement() {
    return getTextElement(RIGHTS);
  }

  public void setRightsElement(Text text) {
    setTextElement(RIGHTS, text, false);
  }
  
  public Text setRights(String value) {
    FOMFactory factory = (FOMFactory)this.factory;
    Text text = factory.newRights();
    text.setValue(value);
    setRightsElement(text);
    return text;
  }
  
  public Text setRightsAsHtml(String value) {
    return setRights(value, Text.Type.HTML);
  }
  
  public Text setRightsAsXhtml(String value) {
    return setRights(value, Text.Type.XHTML);
  }
  
  public Text setRights(String value, Text.Type type) {
    FOMFactory factory = (FOMFactory)this.factory;
    Text text = factory.newRights(type);
    text.setValue(value);
    setRightsElement(text);
    return text;
  }
  
  public Text setRights(Div value) {
    FOMFactory factory = (FOMFactory)this.factory;
    Text text = factory.newRights(value);
    setRightsElement(text);
    return text;
  }

  public String getRights() {
    return getText(RIGHTS);
  }
  
  public Source getSource() {
    return (Source)getFirstChildWithName(SOURCE);
  }

  public void setSource(Source source) {
    if (source != null) {
      if (source instanceof Feed)
        source = ((Feed)source).getAsSource();
      _setChild(SOURCE, (OMElement)source);
    } else {
      _removeChildren(SOURCE, false);
    }
  }

  @SuppressWarnings("unchecked")
  public Text getSummaryElement() {
    return getTextElement(SUMMARY);
  }

  public void setSummaryElement(Text text) {
    setTextElement(SUMMARY, text, false);
  }
  
  public Text setSummary(String value) {
    FOMFactory factory = (FOMFactory)this.factory;
    Text text = factory.newSummary();
    text.setValue(value);
    setSummaryElement(text);
    return text;
  }
  
  public Text setSummaryAsHtml(String value) {
    return setSummary(value, Text.Type.HTML);
  }
  
  public Text setSummaryAsXhtml(String value) {
    return setSummary(value, Text.Type.XHTML);
  }
  
  public Text setSummary(String value, Text.Type type) {
    FOMFactory factory = (FOMFactory)this.factory;
    Text text = factory.newSummary(type);
    text.setValue(value);
    setSummaryElement(text);
    return text;
  }
  
  public Text setSummary(Div value) {
    FOMFactory factory = (FOMFactory)this.factory;
    Text text = factory.newSummary(value);
    setSummaryElement(text);
    return text;
  }
  
  public String getSummary() {
    return getText(SUMMARY);
  }

  @SuppressWarnings("unchecked")
  public Text getTitleElement() {
    return getTextElement(TITLE);
  }

  public void setTitleElement(Text title) {
    setTextElement(TITLE, title, false);
  }

  public Text setTitle(String value) {
    FOMFactory factory = (FOMFactory)this.factory;
    Text text = factory.newTitle();
    text.setValue(value);
    setTitleElement(text);
    return text;
  }
  
  public Text setTitleAsHtml(String value) {
    return setTitle(value, Text.Type.HTML);
  }
  
  public Text setTitleAsXhtml(String value) {
    return setTitle(value, Text.Type.XHTML);
  }
  
  public Text setTitle(String value, Text.Type type) {
    FOMFactory factory = (FOMFactory)this.factory;
    Text text = factory.newTitle(type);
    text.setValue(value);
    setTitleElement(text);
    return text;
  }
  
  public Text setTitle(Div value) {
    FOMFactory factory = (FOMFactory)this.factory;
    Text text = factory.newTitle(value);
    setTitleElement(text);
    return text;
  }
  
  public String getTitle() {
    return getText(TITLE);
  }
    
  public DateTime getUpdatedElement() {
    return (DateTime)getFirstChildWithName(UPDATED);
  }

  public void setUpdatedElement(DateTime updated) {
    if (updated != null)
      _setChild(UPDATED, (OMElement)updated);
    else 
      _removeChildren(UPDATED, false);
  }

  public Date getUpdated() {
    DateTime dte = getUpdatedElement();
    return (dte != null) ? dte.getDate() : null;
  }
  
  private DateTime setUpdated(AtomDate value) {
    if (value == null) {
      _removeChildren(UPDATED, false);
      return null;
    }
    DateTime dte = getUpdatedElement();
    if (dte != null) {
      dte.setValue(value);
      return dte;
    } else {
      FOMFactory fomfactory = (FOMFactory) factory;
      DateTime dt = fomfactory.newUpdated(this);
      dt.setValue(value);
      return dt;
    }
  }
  
  public DateTime setUpdated(Date value) {
    return setUpdated((value != null) ? AtomDate.valueOf(value) : null);
  }
  
  public DateTime setUpdated(String value) {
    return setUpdated((value != null) ? AtomDate.valueOf(value) : null);
  }

  public Control getControl() {
    return (Control)getFirstChildWithName(CONTROL);
  }

  public void setControl(Control control) {
    if (control != null) 
      _setChild(CONTROL, (OMElement)control);
    else 
      _removeChildren(CONTROL, false);
  }

  public Link getLink(String rel) {
    List<Link> links = getLinks(rel);
    Link link = null;
    if (links.size() > 0) link = links.get(0);
    return link;
  }

  public Link getAlternateLink() {
    return getLink(Link.REL_ALTERNATE);
  }

  public Link getEnclosureLink() {
    return getLink(Link.REL_ENCLOSURE);
  }
  
  public Link getEditLink() {
    return getLink(Link.REL_EDIT);
  }
  
  public String getContent() {
    Content content = getContentElement();
    return (content != null) ? content.getValue() : null;
  }
  
  public Type getContentType() {
    Content content = getContentElement();
    return (content != null) ? content.getContentType() : null;
  }

  public Text.Type getRightsType() {
    Text text = getRightsElement();
    return (text != null) ? text.getTextType() : null;
  }

  public Text.Type getSummaryType() {
    Text text = getSummaryElement();
    return (text != null) ? text.getTextType() : null;
  }

  public Text.Type getTitleType() {
    Text text = getTitleElement();
    return (text != null) ? text.getTextType() : null;
  }

}
