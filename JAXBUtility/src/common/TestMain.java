package common;

import generated.JaxbOuter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * The Class TestMain.
 * 
 * @author Sourav
 */
public class TestMain {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws JAXBException
	 *             the JAXB exception
	 */
	public static void main(String[] args) throws JAXBException {
		MockEntity entity = new MockEntity();
		entity.setA("Foo");
		entity.setB("Bar");
		entity.setC(50L);

		JaxbOuter jaxbObject = (JaxbOuter) JAXBUtil.convertObject(entity, null,
				false);

		// Marshalling
		JAXBContext jaxbContext = JAXBContext.newInstance(JaxbOuter.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(jaxbObject, System.out);
	}

}
