# JAXBUtility
This Utility can be used to convert to JAXB objects to Java Domain/Entity objects without using long setter/ getter conversions.

This utility uses a custom annotation to map elements between the Jaxb object and the java entity object. Classes with long object graphs can be converted very easily. Classes which have objects as properties can also be converted by mappiing the subclass to it's corresponding Jaxb class.
