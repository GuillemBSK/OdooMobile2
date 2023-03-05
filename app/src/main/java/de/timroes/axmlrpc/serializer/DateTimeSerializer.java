package de.timroes.axmlrpc.serializer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.w3c.dom.Element;

import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLUtil;
import de.timroes.axmlrpc.xmlcreator.XmlElement;

/**
 *
 * @author timroes
 */
public class DateTimeSerializer implements Serializer {

	public static final String DEFAULT_DATETIME_FORMAT = "yyyyMMdd'T'HHmmss";
	private final SimpleDateFormat dateFormatter;

	private final boolean accepts_null_input;

	public DateTimeSerializer(boolean accepts_null_input) {
		this.accepts_null_input = accepts_null_input;
		this.dateFormatter = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
	}

	public DateTimeSerializer(boolean accepts_null_input, String datetimeFormat) {
		this.accepts_null_input = accepts_null_input;
		this.dateFormatter = new SimpleDateFormat(datetimeFormat);
	}


	public Object deserialize(String dateStr) throws XMLRPCException {
		try {
			String value = formatStringIfNeeded(dateStr);
			//return javax.xml.bind.DatatypeConverter.
			//parseDateTime(value).getTime();
			Locale l = new Locale("en","US");
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ",l).parse(value);
			return date.getTime();
		} catch (Exception ex) {
			throw new XMLRPCException("Unable to parse given date.", ex);
		}
	}

	@Override
	public Object deserialize(Element content) throws XMLRPCException {
		return null;
	}

	@Override
	public XmlElement serialize(Object object) {
		return XMLUtil.makeXmlTag(SerializerHandler.TYPE_DATETIME,
				dateFormatter.format(object));
	}

}
