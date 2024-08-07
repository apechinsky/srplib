# 0.9.0
- ConverterRegistry 
  -- converted from class into interface.
  -- added two implementations: ExactConverterRegistry and SupertypeConverterRegistry.
- Add ConversionService. Incapsulates conversion logic.
- Add Registrar. Incapsulates registration parameters and logic.
- Add ConverterConfigurer. Conversion system configurer.
- Gradle migration: 3.5 -> 8.5.
- Replace 'maven' plugin with 'maven-publish'
- Upgrade hamcrest: 1.3 -> 2.2
- Upgrade mockito: 1.9.5 -> 2.21.0

# 0.8.3
- Fixed: DeepComparator. NullPointerException comparing null values
- Add DeepComparatorConfiguration - a configuration for DeepComparator.

# 0.8.2
- Fixed: DeepComparator. IndexOutOfBoundsException if actual collection/array/map length is greater than expected.
- Fixed: DeepComparator compares arrays with standard equals. Changed to ArrayDeepComparator.
- Fixed: ConfigurableNodeValueFactory. A collision in type metadata and value factory definition.

# 0.8.1
- Fixed: DeepComparator doesn't handle interned strings.

# 0.8.0
- Add new module srp-reflection. Module will contain core reflection functionality.
- Add new module srp-reflection-support. Contains extended reflection functionality such as object graph navigation.
- Move org.srplib.reflection package from srp-support to srp-reflection.
- Move org.srplib.objectgraph to package org.srplib.reflection.objectgraph of module srp-reflection-support
- Class graph navigation support (org.srplib.reflection.classgraph). 
- Deep object comparison support (org.srplib.reflection.deepcompare).
- Generic object generation factory (org.srplib.reflection.objectfactory). Experimental.

