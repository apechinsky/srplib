# SRPLIB

  A collection of highly specialized libraries. Each library is designed with Single Responsiblity Principle 
  (http://en.wikipedia.org/wiki/Single_responsibility_principle) in mind. 

## Modules

  - [contract](contract) - method and class contract support
  - conversion - general purpose converter interface and infrastructure
  - support  - support library for other modules
  - model - general purpose model
  - binding - general purpose binding (experimental)
  - validation - genral purpose validation (experimental)

## Structure
  
  Each library includes main module and optional support module. 
  
Main module:
  
  - defines core interfaces and classes
  - does not provide "useful" implementaions 
  - does not provide helper classes, factories, syntax sugar, etc.
    
Optional module:

  - provides useful implementations
  - privides factories and helper classes

  
### [contract]: Contract
  
  Contains helper classes for checking method arguments and class internal state. Encourages contract programming and 
fail-fast support. Designed as single line replacement for if/condition/throw code. 

      // standard check
      if (!file.exist()) {
          throw new IllegalStateException(String.format("File '%s' should exist.", file));
      }

      // check with Assert
      Assert.checkTrue(file.exist(), "File '%s' should exist.", file);
  
  - Argument - class is used to check method arguments (throws IllegalArgumentException).
  - Assert - class is used to check state (throws IllegalStateException).

#### Key features

  - encourages good practices
  - lightweight
  - string formatting
  - expressive and concise

##### Encourages good practices
  
  - fail-fast code (http://en.wikipedia.org/wiki/Fail-fast)   
  - contract programming (http://en.wikipedia.org/wiki/Design_by_contract)
  - informative error messages. No more empty or mesleading error messages. 

##### Lightweight

  Extremely lightweight library. Exports few public classes. Have no external dependencies. Suitable for small mobile 
  as well as large enterprise applications.
  
##### String formatting

  String formatting simplifies error message creation and helps to avoid unneeded string contatenation.
  
    Argument.checkTrue(file.exist(), "File '%s' not found.", file.getAbsolutePath());
    
##### Expressive and concise

  More expressive and concise than standard if/condition/throw code.

Just compare:
  
  Check method argument  
    
      if (parameter == null) {
          throw new IllegalArgumentException("Parameter must not be null.");
      }
      
      Argument.checkNotNull(parameter, "Parameter must not be null.");

  Check internal state

      if (!file.exist()) {
          throw new IllegalStateException(String.format("File '%s' should exist.", file));
      }

      Assert.checkTrue(file.exist(), "File '%s' should exist.", file);

#### Known alternatives

  - Apache commons-lang [Validate](http://code.google.com/p/guava-libraries/source/browse/guava/src/com/google/common/base/Preconditions.java)
  - Springframework [Assert](https://github.com/SpringSource/spring-framework/blob/master/spring-core/src/main/java/org/springframework/util/Assert.java)
  - Google Guava [Preconditions](http://code.google.com/p/guava-libraries/source/browse/guava/src/com/google/common/base/Preconditions.java)
