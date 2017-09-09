# Reflection
  
Simplifies usage of java reflection API.

## Core features
 
- Reflection code with no checked exception hassle. 
- ReflectionInvoker - helper class for easy method invocation via reflection.
- ReflectionException - dedicated unchecked exception to report reflection problems. 
Library code wraps checked exceptions to this exception.
- ReflectionUtils - a collection of helper methods.
- Detailed error messages.
- complex method/field names support via dot-syntax
  
## Extended features  

- DeepComparator - deep object comparator with detailed difference report. 
- DeepComparatorMatcher - a Hamcrest based on DeepComparator  
- ClassGraph - class graph navigation support.  
- ClassGraphFactory - generic object factory. Creates an object of an arbitrary class.   
- ValueFactory - generic value factory.   

## Reflection code with no checked exception hassle
  
      

## ReflectionInvoker

Simplifies constructor and method invocation via reflection. Just compare to snippets of code:

Create object using reflection api

    try {
        Object[] arguments = new Object[] { "John", "Smith", 35 };
        Class[] parameters = new Class[] { String.class, String.class, int.class };
        Constructor<T> constructor = Person.class.getDeclaredConstructor(parameters);
        return constructor.newInstance(arguments);
    }
    catch (InstantiationException e) {
        // ignore
    }
    catch (IllegalAccessException e) {
        e.printStackTrace();
    }
    catch (NoSuchMethodException e) {
        throw new RuntimeException(e);            
    }
    catch (InvocationTargetException e) {
        throw new IllegalStateException("Can't create class.", e);
    }

The same code using ReflectionInvoker

    Person person = ReflectionInvoker.constructor(Person.class)
        .parameters(String.class, String.class, int.class)
        .invoke("John", "Smith", 35);

  Take a look at first code snippet. Each time we need to create class instance we need to write such ugly code. Catch blocks intentionally made different to
show some popular exception handling "methods".  

## ReflectionException

Root unchecked exception for reflection sybsystem. 

## ReflectionUtils
  
Contains static utility methods for reflection API. All methods wraps checked exceptions to unchecked ReflectionException.
  
- method/field finders 
- method/field getter, unlike finders getters throw exception if search unsuccessful
- field setters - set field value
- field getters - get field value
  
## DeepComparator

Compares an arbitrary objects by navigating its internal structure: superclasses, fields, collection items, etc.

### Key features

- provides basic deep comparison infrastructure 
- allows to specify class navigation logic
- allows to specify object comparison logic  
- provides default comparison and navigation logic for base java objects and collections (org.reflection.deepcompare.comparators)

### Usage

    // basic usage
    List<String> diffs = new ConfigurableDeepComparator().compare(object1, object2)

    // with JUnit
    assertThat(actual, DeepComparatorMatcher.deepCompare(expected));

    // modify comparison strategy
    Converter<Class, DeepComparator> compareStrategy = new MyExtensionOfStandardComparators(new MyExtensionOfMapDeepComparator());
    List<String> diffs = new ConfigurableDeepComparator(compareStrategy).compare(object1, object2)

## Known alternatives

  - Springframework spring-lang [ReflectionUtils](http://springframework.org)
  - Apache commons-lang [ReflectionUtils](http://svn.apache.org)
  - Google Guava [ReflectionUtils](http://code.google.com/p/reflections/source/browse/trunk/reflections/src/main/java/org/reflections/ReflectionUtils.java)
  - Reflections library [Reflections](https://github.com/ronmamo/reflections)
