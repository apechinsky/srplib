# Reflection
  
  Very lightweight library for reflection API.
  
## Key features
  - use of dedicated unchecked exception class ReflectionException
  - complex method/field names support via dot-syntax
  - very detailed exception messages
  
## Use cases


## Core classes
 
  - ReflectionException - dedicated class for reflection API errors
  - ReflectionInvoker - simple and clean reflection method invocation.
  - ReflectionUtils - reflection utilities.

## Support classes
 
  - ObjectGraph - object graph traverser. Employs visitor pattern.
  
## Known alternatives

  - Springframework spring-lang [ReflectionUtils](http://springframework.org)
  - Apache commons-lang [ReflectionUtils](http://svn.apache.org)
  - Google Guava [ReflectionUtils](http://code.google.com/p/reflections/source/browse/trunk/reflections/src/main/java/org/reflections/ReflectionUtils.java)

