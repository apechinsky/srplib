# Conversion
  
  Very lightweight library declaring general purpose converter. Converter is used to encapsulate object conversion algorithm.
  
  Exports few public classes. Have single dependency (srp-contract). Suitable for small mobile as well as large enterprise applications.  

## Use cases

  - simple object conversion
  - adapter implementations. Library helps to structure and simplify type mapping and value conversion code. 
  - binding implementations. Converter infrastructure may minimize handwritten value conversion code.

## Core classes
 
  - Converter - core converter interface.
  - ConverterException - base module exception.
  - ConverterRegistry - converter registry.

## Support classes
 
  - set of basic converters
  - null-value converter (EmptyConverter)
  - logic converters (IfConverter, SwitchConverter)
  - composite converter (ChainConverter)
  - factory of basic converters
  
## Known alternatives

  - Apache commons-collections [Transformer](http://svn.apache.org/viewvc/commons/proper/collections/trunk/src/main/java/org/apache/commons/collections4/Transformer.java)
  - Apache commons-beanutils [Converter](http://svn.apache.org/viewvc/commons/proper/beanutils/trunk/src/main/java/org/apache/commons/beanutils/Converter.java)
  - Google Guava [Function](http://code.google.com/p/guava-libraries/source/browse/guava/src/com/google/common/base/Function.java)
  - Transmorph [IConverter](https://github.com/cchabanois/transmorph/blob/master/src/main/java/net/entropysoft/transmorph/IConverter.java)

