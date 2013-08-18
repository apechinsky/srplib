# SRPLIB

  A collection of highly specialized libraries. Each library is designed with Single Responsiblity Principle 
  (http://en.wikipedia.org/wiki/Single_responsibility_principle) in mind. 

## Structure
  
  Each library includes main module and optional support module. 
  
Main module:
  
  - defines core interfaces and classes
  - does not provide "useful" implementaions 
  - does not provide helper classes, factories, syntax sugar, etc.
    
Optional moudle:

  - provides useful implementations
  - privides factories and helper classes

## Modules

  - contract - method and class contract support
  - conversion - general purpose converter interface and infrastructure
  - support  - support library for other modules
  - model - general purpose model
  - binding - general purpose binding (experimental)
  - validation - genral purpose validation (experimental)
  
### Contract
  
  Contains helper classes for checking method arguments and class internal state. 

#### Features
  
  - very lightweight. Exports few public classes. Have no external dependencies.
  - supports string formatting.
  - more expressive and concise than standard if/condition/throw code.

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

