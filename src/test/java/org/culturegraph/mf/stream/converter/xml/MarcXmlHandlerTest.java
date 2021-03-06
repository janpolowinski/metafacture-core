/*
 *  Copyright 2014 Deutsche Nationalbibliothek
 *
 *  Licensed under the Apache License, Version 2.0 the "License";
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.culturegraph.mf.stream.converter.xml;

import static org.mockito.Mockito.verify;

import org.culturegraph.mf.framework.StreamReceiver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * @author Christoph Böhme
 *
 */
public final class MarcXmlHandlerTest {

	private static final String CONTROLFIELD = "controlfield";
	private static final String NAMESPACE = "http://www.loc.gov/MARC21/slim";

	private MarcXmlHandler marcXmlHandler;

	@Mock
	private StreamReceiver receiver;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		marcXmlHandler = new MarcXmlHandler();
		marcXmlHandler.setReceiver(receiver);
	}

	@After
	public void cleanup() {
		marcXmlHandler.closeStream();
	}

	@Test
	public void shouldFindTagAttributeAtSecondPositionInControlFieldElement()
			throws SAXException {
		final AttributesImpl attributes = new AttributesImpl();
		attributes.addAttribute(NAMESPACE, "id", "id", "CDATA", "id-1");
		attributes.addAttribute(NAMESPACE, "tag", "tag", "CDATA", "001");

		final String fieldValue = "1234";

		marcXmlHandler.startElement(NAMESPACE, CONTROLFIELD, "", attributes);
		marcXmlHandler.characters(fieldValue.toCharArray(), 0, fieldValue.length());
		marcXmlHandler.endElement(NAMESPACE, CONTROLFIELD, "");

		verify(receiver).literal("001", fieldValue);
	}

}
